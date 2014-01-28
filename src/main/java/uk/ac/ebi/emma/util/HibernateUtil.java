/**
 * HibernateUtil.java
 *
 * Created 24 January 2014 (mrelac).
 *
 * Utility class to create the hibernate session factory. This action is effectively
 * Hibernate initialization. As of Hibernate 4.3.0, the method to fetch the session
 * factory has changed (see
 * http://stackoverflow.com/questions/11464258/hibernate-and-data-insertion-into-database-fails).
 * 
 * Rather than use hibernate.cfg.xml to inject the database credentials, we
 * use a properties file. NOTE: even though the credentials may be specified in
 * a bean, hibernate is not bean-aware, and I could find no way for them to be
 * automatically injected when specified in the sessionFactory bean.
 * 
 * There are two ways to fetch the sessionFactory:
 * <ul><li>Provide a hibernate.properties file containing the hibernate 
 * configuration (including database credentials) on the classpath. Then just
 * invoke the static method <code>HibernateUtils.getSessionFactory()</code></li>
 * <li>Provide your own <code>Properties</code> file by invoking the static
 * method <code>HibernateUtils.setHibernateProperties(Properties properties)</code>, 
 * then invoke the static method <code>HibernateUtils.getSessionFactory().</code></li>
 */

package uk.ac.ebi.emma.util;

import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 *
 * @author mrelac
 */
public class HibernateUtil {
    protected static final Logger logger = Logger.getLogger(HibernateUtil.class);
    private static SessionFactory sessionFactory = null;
    private static Properties hibernateProperties = null;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            initializeSessionFactory();
        }
        return sessionFactory;
    }
    

    // GETTERS AND SETTERS
    
    
    public static Properties getHibernateProperties() {
        return hibernateProperties;
    }

    public static void setHibernateProperties(Properties hibernateProperties) {
        HibernateUtil.hibernateProperties = hibernateProperties;
        initializeSessionFactory();
    }
    
    
    // PRIVATE METHODS
    
    
    /**
     * Initialize the sessionFactory. If the user provided one by calling
     * setHibernateProperties, use it; otherwise, look for a properties file
     * named <b>hibernate.properties</b> on the classpath. If no properties file is
     * found, currently (24-Jan-2014), the session will be created and returned
     * but cannot be used as there is no other way to provide database credentials
     * than through the properties file.
     */
    private static void initializeSessionFactory() {        
        Configuration configuration = new Configuration();
        if (hibernateProperties == null) {
            Resource resource = new ClassPathResource("/hibernate.properties");
            try {
                hibernateProperties = PropertiesLoaderUtils.loadProperties(resource);
            } catch (IOException e) { }
        }
        
        if (hibernateProperties != null)
            configuration.addProperties(hibernateProperties);
        configuration.configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                                            .applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }

}
