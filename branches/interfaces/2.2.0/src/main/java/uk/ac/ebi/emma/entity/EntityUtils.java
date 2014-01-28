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



package uk.ac.ebi.emma.entity;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import static org.hibernate.cfg.Environment.verifyProperties;
import uk.ac.ebi.emma.manager.AbstractManager;

/**
 *
 * @author mrelac
 */


public class EntityUtils extends AbstractManager {

    /**
     * Returns a map of the maximum column lengths of <code>String</code> typed
     * columns belonging to <code>table</code>.
     * @param table the table whose maximum field lengths is to be queried
     * @return a map of the maximum column lengths of <code>String</code> typed
     * columns belonging to <code>table</code>
     */
    public HashMap<String, Integer> getMaxColumnLengths(String table) {
        HashMap<String, Integer> maxColumnLengthsHash = new HashMap();
        verifyProperties(new HashMap());
        if (table == null)
            return maxColumnLengthsHash;
        
        List maxColumnLengthsList = null;
        try {
            getCurrentSession().beginTransaction();
            String query = 
                    "SELECT COLUMN_NAME, CHARACTER_MAXIMUM_LENGTH\n"
                  + "FROM information_schema.COLUMNS\n"
                  + "WHERE TABLE_NAME = ?";
            maxColumnLengthsList = getCurrentSession().createSQLQuery(query).setParameter(0, table).list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
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
