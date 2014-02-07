/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 /**
 * Copyright © 2014 EMBL - European Bioinformatics Institute
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

package uk.ac.ebi.emma.manager;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import uk.ac.ebi.emma.util.HibernateUtil;

/**
 *
 * @author mrelac
 */
public abstract class AbstractManager {
    protected final Logger logger = Logger.getLogger(this.getClass());
    protected SessionFactory sessionFactory;
    protected String username = null;

    public AbstractManager() {
        sessionFactory = HibernateUtil.getSessionFactory();
        logger.debug("Instantiating new AbstractManager for " + this.getClass() + ". sessionFactory is " + sessionFactory.toString());
    }
    
    protected Session getCurrentSession(){
        if (username == null)
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        
        return sessionFactory.getCurrentSession();
    }

}
