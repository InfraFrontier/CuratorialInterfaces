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
    public Background getBackground(int background_key) throws HibernateException {
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

}
