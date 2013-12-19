package cn.edu.uestc.acmicpc.db.dao.base;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Base DAO Implementation for <strong>Hibernate 4</strong>.
 */
@Transactional
@Repository
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BaseDAO {

  private static final Logger LOGGER = LogManager.getLogger(BaseDAO.class);

  @Autowired
  private SessionFactory sessionFactory;

  /**
   * Get current database session.
   *
   * @return if the IoC works, return current session, otherwise open a new session
   */
  protected Session getSession() {
    Session session;
    try {
      session = sessionFactory.getCurrentSession();
    } catch (HibernateException e) {
      LOGGER.error(e);
      session = sessionFactory.openSession();
    }
    return session;
  }
}
