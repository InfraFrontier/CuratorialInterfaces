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
import uk.ac.ebi.emma.entity.Strain;

/**
 *
 * @author phil, mrelac
 */
@Component
public class StrainsManager extends AbstractManager {
    
    /**
     * Returns the <code>Strain</code> object matching <code>strain_key</code>
     * @param strain_key the strain primary key to match
     * @return the <code>Strain</code> object matching <code>strain_key</code>.
     * @throws HibernateException if a hibernate error occurs
     * @deprecated This method is really slow thanks to hibernate and/or misconfiguration. Use getStrainName if you want just the id and name.
     */
    @Deprecated
    public Strain getStrain(Integer strain_key) throws HibernateException {
        if (strain_key == null)
            return null;
        
        Strain strain = null;
        try {
            getCurrentSession().beginTransaction();
            strain = (Strain)getCurrentSession()
                    .createQuery("FROM Strain s WHERE id_str = :strain_key")
                    .setParameter("strain_key", strain_key)
                    .uniqueResult();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
        
        return strain;
    }
    
    /**
     * Returns the strain_key and name from persistent storage.
     * @param strain_key the strain primary key
     * @return  the strain (strain_key and name only) from persistent storage,
     *          if the strain key exists; null otherwise
     * @throws HibernateException if a hibernate error occurs
     * 
     * NOTE: This query is instantaneous versus using HSQL, which is noticeably slower.
     */
    public Strain getStrainName(int strain_key) throws HibernateException {
        Strain strain = null;
        try {
            getCurrentSession().beginTransaction();
            Object o = getCurrentSession()
                    .createSQLQuery("SELECT id_str AS strain_key, name FROM strains WHERE id_str = :strain_key")
                    .setParameter("strain_key", strain_key)
                    .uniqueResult();
            getCurrentSession().getTransaction().commit();
            
            Object[] row = (Object[])o;
            Integer id = (Integer)row[0];
            String name = (String)row[1];
            strain = new Strain();
            strain.setStrain_key(id);
            strain.setName(name);

        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
        
        return strain;
    }
    
    /**
     * Returns the full list of strain_key and name from persistent storage.
     * @return  the full list of strains (strain_key and name only) from persistent storage.
     * @throws HibernateException if a hibernate error occurs
     * 
     * NOTE: This query is instantaneous versus using HSQL, which is noticeably slower.
     */
    public List<Strain> getStrainNames() throws HibernateException {
        List<Strain> strainsList = new ArrayList();
        
        try {
            getCurrentSession().beginTransaction();
            Object o = getCurrentSession()
                    .createSQLQuery("SELECT id_str AS strain_key, name FROM strains")
                    .list();
            List list = (List)o;
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Object[] row = (Object[])it.next();
                Integer id = (Integer)row[0];
                String name = (String)row[1];
                Strain strain = new Strain();
                strain.setStrain_key(id);
                strain.setName(name);
                strainsList.add(strain);
            }
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
        
        return strainsList;
    }
    
}
