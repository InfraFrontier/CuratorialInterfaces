/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 /**
 * Copyright © 2013 EMBL - European Bioinformatics Institute
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License.  
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.ebi.emma.infrastructure;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import uk.ac.ebi.emma.entity.Gene;
import uk.ac.ebi.emma.util.HibernateUtils;

/**
 *
 * @author mrelac
 */
@RunWith(BlockJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"/testApplicationContext.xml"})
public class SpringHibernateConfigurationTest {
    protected final Logger logger = Logger.getLogger(this.getClass());

    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getSessionFactory method, of class HibernateUtils.
     */
    @Test
    public void testGetSessionFactoryUsingDefaultProperties() {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        SessionFactory sessionFactory1 = HibernateUtils.getSessionFactory();
        assertEquals(sessionFactory, sessionFactory1);
        Session session = sessionFactory.getCurrentSession();
        assertNotNull(session);
        session = sessionFactory.getCurrentSession();
        assertNotNull(session);
        
        logger.info("Got session. Fetching genes.");
        List<Gene> genes = getGenes(session);
         logger.info("Read " + genes.size() + " genes.");
         
        assertFalse(session.isOpen());
        // Don't close the factory. Doing so causes subsequent factory requests to fail.
        // See http://stackoverflow.com/questions/11901130/org-springframework-orm-hibernate4-hibernatesystemexception-unknown-service-req
//        sessionFactory.close();
//        assertTrue(sessionFactory.isClosed());
    }

    /**
     * Test of getSessionFactory method, of class HibernateUtils.
     */
    @Test
    public void testGetSessionFactoryUsingCustomProperties() {
        Resource resource = new ClassPathResource("/hibernate.test.properties");
        Properties hibernateProperties = null;
        try {
            hibernateProperties = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            fail("Unable to load hibernate.test.properties file.");
        }
        HibernateUtils.setHibernateProperties(hibernateProperties);
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        assertNotNull(session);
        session = sessionFactory.getCurrentSession();
        assertNotNull(session);
        
        logger.info("Got session. Fetching genes.");
        List<Gene> genes = getGenes(session);
        logger.info("Read " + genes.size() + " genes.");
         
        assertFalse(session.isOpen());
        
        // Don't close the factory. Doing so causes subsequent factory requests to fail.
        // See http://stackoverflow.com/questions/11901130/org-springframework-orm-hibernate4-hibernatesystemexception-unknown-service-req
//        sessionFactory.close();
//        assertTrue(sessionFactory.isClosed());
    }
    
    private List<Gene> getGenes(Session session) {
        List<Gene> genesList = null;
        try {
            session.beginTransaction();
            genesList = session.createSQLQuery("SELECT * FROM genes LIMIT 10").list();
            assertEquals(genesList.size(), 10);
            logger.info("genesList createSQLQuery succeeded. Expected 10 records. Actual # Records: " + genesList.size());
            genesList = session.createQuery("FROM Gene").list().subList(0, 10);
            assertEquals(genesList.size(), 10);
            logger.info("genesList createSQLQuery succeeded. Expected 10 records. Actual # Records: " + genesList.size());
            logger.info("Before commit, session is " + (session.isOpen() ? " opened" : "closed") + ".");
            session.isOpen();
            session.getTransaction().commit();
            logger.info("After commit, session is " + (session.isOpen() ? " opened" : "closed") + ".");
        } catch (HibernateException e) {
            logger.fatal("getGenes() failed: " + e.getLocalizedMessage());
            session.getTransaction().rollback();
            throw e;
        }
        
        return genesList;
    }
    
}
