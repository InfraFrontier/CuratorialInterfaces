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



package uk.ac.ebi.emma.util;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author mrelac
 */
public class DBUtils {
    protected static Logger logger = Logger.getLogger(DBUtils.class);
    static SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

    /**
     * Returns the full list of genes from persistent storage.
     * @param table the table whose maximum field lengths is to be queried
     * @return  the full list of genes from persistent storage.
     */
    public static HashMap<String, Integer> getMaxColumnLengths(String table) {
        HashMap<String, Integer> maxColumnLengthsHash = new HashMap();
        
        if (table == null)
            return maxColumnLengthsHash;
        
        Session session = sessionFactory.getCurrentSession();
        List maxColumnLengthsList = null;
        try {
            session.beginTransaction();
            String query = 
                    "SELECT COLUMN_NAME, CHARACTER_MAXIMUM_LENGTH\n"
                  + "FROM information_schema.COLUMNS\n"
                  + "WHERE TABLE_NAME = ?";
            maxColumnLengthsList = session.createSQLQuery(query).setParameter(0, table).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        
        Iterator iterator = maxColumnLengthsList.listIterator();
        while (iterator.hasNext()) {
            Object[] result = (Object[])iterator.next();
            String columnName = (String)result[0];
            BigInteger maxLength = (BigInteger)result[1];
            if (maxLength != null)
                maxColumnLengthsHash.put(columnName, maxLength.intValue());     // Only columns with lengths have non-null values.
        }
        
        return maxColumnLengthsHash;
    }
    
    
}
