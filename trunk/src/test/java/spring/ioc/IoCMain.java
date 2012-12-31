package spring.ioc;

import junit.framework.TestCase;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Created with IntelliJ IDEA.
 * User: mzry1992
 * Date: 13-1-1
 * Time: 上午12:00
 * To change this template use File | Settings | File Templates.
 */
public class IoCMain extends TestCase {

    ClassPathResource resource;
    BeanFactory factory;

    protected void setUp() throws Exception {
        resource = new ClassPathResource("beans.xml");
        factory = new XmlBeanFactory(resource);
    }

    protected void tearDown() throws Exception {
    }

    public void testIoc() {
        IoCService service = (IoCService)factory.getBean("iocService");
        service.display();
    }
}
