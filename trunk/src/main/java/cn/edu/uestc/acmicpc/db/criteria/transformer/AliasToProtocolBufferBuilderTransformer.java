package cn.edu.uestc.acmicpc.db.criteria.transformer;

import org.hibernate.HibernateException;
import org.hibernate.property.ChainedPropertyAccessor;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.hibernate.property.Setter;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Result transformer that allows to transform a result to
 * protocol buffer which will be populated via setter
 * methods or fields matching the alias names.
 * <p/>
 * <pre>
 * DetachedCriteria criteria = DetachedCriteria.forClass(Article.class, "article");
 *
 * criteria.setProjection(Projections.projectionList()
 *   .add(Projections.property("article.articleId"), "articleId")
 *   .add(Projections.property("article.title"), "title")
 * );
 * criteria.setResultTransformer(new AliasToProtocolBufferBuilderTransformer(ArticleDtoProtos.ArticleDto.class));
 * 	</pre>
 *
 * 	see {@link org.hibernate.transform.AliasToBeanResultTransformer}
 */
public class AliasToProtocolBufferBuilderTransformer extends AliasedTupleSubsetResultTransformer {

  private static final long serialVersionUID = 5545202313945944033L;

  /**
   * Result class type (inherit from {@link com.google.protobuf.GeneratedMessage}
   */
  private final Class resultClass;
  /**
   * Builder class
   */
  private final Class builderClass;
  /**
   * newBuilder() method in resultClass
   */
  private final Method newBuilderMethod;
  /**
   * build() method in builderClass
   */
  private final Method buildMethod;

  private boolean isInitialized;
  private String[] aliases;
  private Setter[] setters;

  public AliasToProtocolBufferBuilderTransformer(Class resultClass) {
    if ( resultClass == null ) {
      throw new IllegalArgumentException( "resultClass cannot be null" );
    }
    isInitialized = false;
    this.resultClass = resultClass;
    try {
      this.newBuilderMethod = resultClass.getMethod("newBuilder");
    } catch (NoSuchMethodException e) {
      throw new IllegalArgumentException( "resultClass should have a newBuilder() method" );
    }
    this.builderClass = this.newBuilderMethod.getReturnType();
    try {
      this.buildMethod = this.newBuilderMethod.getReturnType().getMethod("build");
    } catch (NoSuchMethodException e) {
      throw new IllegalArgumentException( "resultClass should have a Build subclass and it need a build() method" );
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
    return false;
  }

  public Object transformTuple(Object[] tuple, String[] aliases) {
    Object builder;

    try {
      if ( ! isInitialized ) {
        initialize( aliases );
      }
      else {
        check( aliases );
      }

      try {
        builder = newBuilderMethod.invoke(null);
      } catch (InvocationTargetException e) {
        throw new HibernateException( "Could not invoke newBuilder() method: " + resultClass.getName() );
      }

      for ( int i = 0; i < aliases.length; i++ ) {
        if ( setters[i] != null ) {
          setters[i].set( builder, tuple[i], null );
        }
      }
    }
    catch ( IllegalAccessException e ) {
      e.printStackTrace();
      throw new HibernateException( "Could not instantiate resultclass: " + resultClass.getName() );
    }

    try {
      return buildMethod.invoke(builder);
    } catch (IllegalAccessException e) {
      throw new HibernateException( "Could not instantiate resultclass: " + resultClass.getName() );
    } catch (InvocationTargetException e) {
      throw new HibernateException( "Could not instantiate resultclass: " + resultClass.getName() );
    }
  }

  private void initialize(String[] aliases) {
    PropertyAccessor propertyAccessor = new ChainedPropertyAccessor(
        new PropertyAccessor[] {
            PropertyAccessorFactory.getPropertyAccessor(builderClass, null),
            PropertyAccessorFactory.getPropertyAccessor( "field" )
        }
    );
    this.aliases = new String[ aliases.length ];
    setters = new Setter[ aliases.length ];
    for ( int i = 0; i < aliases.length; i++ ) {
      String alias = aliases[ i ];
      if ( alias != null ) {
        this.aliases[ i ] = alias;
        setters[ i ] = propertyAccessor.getSetter( builderClass, alias );
      }
    }
    isInitialized = true;
  }

  private void check(String[] aliases) {
    if ( ! Arrays.equals(aliases, this.aliases) ) {
      throw new IllegalStateException(
          "aliases are different from what is cached; aliases=" + Arrays.asList( aliases ) +
              " cached=" + Arrays.asList( this.aliases ) );
    }
  }
}
