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
     */
    public Strain getStrain(int strain_key) throws HibernateException {
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

}
