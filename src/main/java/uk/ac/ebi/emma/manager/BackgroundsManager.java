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

package uk.ac.ebi.emma.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Component;
import uk.ac.ebi.emma.entity.Background;

/**
 *
 * @author mrelac
 */
@Component
public class BackgroundsManager extends AbstractManager {
    
    /**
     * Returns the <code>Background</code> object matching <code>id_background</code>
     * @param background_key the background primary key to match
     * @return the <code>Background</code> object matching <code>id_background</code>.
     * @throws HibernateException if a hibernate error occurs
     */
    public Background getBackground(Integer background_key) throws HibernateException {
        if (background_key == null)
            return null;
        
        Background background = null;
        try {
            getCurrentSession().beginTransaction();
            background = (Background)getCurrentSession()
                    .createQuery("FROM Background b WHERE background_key = :background_key")
                    .setParameter("background_key", background_key)
                    .uniqueResult();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
        
        return background;
    }
    
    /**
     * Returns the background_key, name, and symbol from persistent storage.
     * @param background_key the background primary key
     * @return  the background (background_key, name, and symbol only) from persistent storage,
     *          if the background key exists; null otherwise
     * @throws HibernateException if a hibernate error occurs
     * 
     * NOTE: This query is instantaneous versus using HSQL, which is noticeably slower.
     */
    public Background getBackgroundName(int background_key) throws HibernateException {
        Background background = null;
        try {
            getCurrentSession().beginTransaction();
            Object o = getCurrentSession()
                    .createSQLQuery("SELECT id_bg AS background_key, name, symbol FROM backgrounds WHERE id_bg = :background_key")
                    .setParameter("background_key", background_key)
                    .uniqueResult();
            
            Object[] row = (Object[])o;
            Integer id = (Integer)row[0];
            String name = (String)(row[1] == null ? "" : row[1]);
            String symbol = (String)(row[2] == null ? "" : row[2]);
            background = new Background();
            background.setBackground_key(id);
            background.setName(name);
            background.setSymbol(symbol);

            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
        
        return background;
    }

    /**
     * Returns the full list of background_key, name, and symbol from persistent storage.
     * @return  the full list of backgrounds (background_key, name, and symbol only) from persistent storage.
     * @throws HibernateException if a hibernate error occurs
     * 
     * NOTE: This query is instantaneous versus using HSQL, which is noticeably slower.
     */
    public List<Background> getBackgroundNames() throws HibernateException {
        List<Background> backgroundsList = new ArrayList();
        
        try {
            getCurrentSession().beginTransaction();
            Object o = getCurrentSession()
                    .createSQLQuery("SELECT id_bg AS background_key, name, symbol FROM backgrounds")
                    .list();
            List list = (List)o;
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Object[] row = (Object[])it.next();
                Integer id = (Integer)row[0];
                String name = (String)row[1];
                String symbol = (String)row[2];
                Background background = new Background();
                background.setBackground_key(id);
                background.setName(name);
                background.setSymbol(symbol);
                backgroundsList.add(background);
            }
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
        
        return backgroundsList;
    }
    
}
