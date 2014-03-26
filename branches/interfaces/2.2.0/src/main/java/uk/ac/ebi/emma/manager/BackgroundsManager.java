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
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Component;
import uk.ac.ebi.emma.entity.Background;
import uk.ac.ebi.emma.util.Filter;
import uk.ac.ebi.emma.util.Utils;

/**
 *
 * @author mrelac
 */
@Component
public class BackgroundsManager extends AbstractManager {
    
    
    /**
     * Deletes the named <code>Background</code> object.
     * @param background the <code>Background</code> object to be deleted
     * @throws HibernateException if a hibernate error occurs
     */
    public void delete(Background background) throws HibernateException {
        try {
            getCurrentSession().beginTransaction();
            getCurrentSession().delete(background);
            getCurrentSession().getTransaction().commit();

        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
    }
    
    /**
     * Deletes the <code>Background</code> object identified by <b>id</b>.
     * @param id the <code>Background</code> primary key of object to be deleted
     * @throws HibernateException if a hibernate error occurs
     */
    public void delete(int id) throws HibernateException {
        delete(getBackground(id));
    }
    
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
            getCurrentSession().getTransaction().commit();
            
            Object[] row = (Object[])o;
            Integer id = (Integer)row[0];
            String name = (String)(row[1] == null ? "" : row[1]);
            String symbol = (String)(row[2] == null ? "" : row[2]);
            background = new Background();
            background.setBackground_key(id);
            background.setName(name);
            background.setSymbol(symbol);
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
    
    /**
     * Given a <code>Filter</code> object describing any/all of the following
     * fields:
     * <ul>
     * <li>background ID (may be a comma-separated list)</li>
     * <li>background name</li>
     * <li>background symbol</li>
     * <li>curated (may 'Y', 'N', or null/empty)</li>
     * <li>inbred (may 'Y', 'N', or null/empty)</li>
     * this method performs a query, ANDing all non-empty fields in a WHERE
     * clause against the backgrounds table.
     * 
     * The result is a <code>List&lt;Background&gt;</code> of
     * qualifying results. A list is always returned, even if there are no results.
     * 
     * @param filter values to filter by
     * @return a list of matching <code>Background</code> instances.
     * @throws NumberFormatException if ids are not numeric (commas and whitespace are OK)
     */
    public List<Background> getFilteredBackgroundsList(Filter filter) throws NumberFormatException {
        String backgroundKeyWhere = "";
        String backgroundNameWhere = "";
        String backgroundSymbolWhere = "";
        String backgroundIsCuratedWhere = "";
        String backgroundIsInbredWhere = "";
        
        List<Background> targetList = new ArrayList();
        
        String queryString = "SELECT b.*\n"
                           + "FROM backgrounds b\n"
                           + "WHERE (1 = 1)\n";     
        
        if ((filter.getBackground_key() != null) && ( ! filter.getBackground_key().isEmpty())) {
            String backgroundIds = Utils.cleanIntArray(filter.getBackground_key());
            if (Utils.isValidIntArray(backgroundIds)) {
                backgroundKeyWhere = "  AND (b.id_bg IN (" + backgroundIds + "))\n";
                queryString += backgroundKeyWhere;
            }
        }
        if ((filter.getBackgroundName() != null) && ( ! filter.getBackgroundName().isEmpty())) {
            backgroundNameWhere = "  AND (b.name LIKE :backgroundName)\n";
            queryString += backgroundNameWhere;
        }
        if ((filter.getBackgroundSymbol() != null) && ( ! filter.getBackgroundSymbol().isEmpty())) {
            backgroundSymbolWhere = "  AND (b.symbol LIKE :backgroundSymbol)\n";
            queryString += backgroundSymbolWhere;
        }
        if ((filter.getBackgroundIsCurated()!= null) && ( ! filter.getBackgroundIsCurated().isEmpty())) {
            switch (filter.getBackgroundIsCurated()) {
                case "Y":
                case "y":
                    backgroundIsCuratedWhere = "  AND (b.curated = 'Y')\n";
                    break;
                    
                case "N":
                case "n":
                    backgroundIsCuratedWhere = "  AND (b.curated = 'N')\n";
                    break;
                    
                default:
                    backgroundIsCuratedWhere = "  AND (((b.curated != 'Y') && (b.curated != 'N')) || (b.curated IS NULL))\n";
                    break;
            }
            queryString += backgroundIsCuratedWhere;
        }
        if ((filter.getBackgroundIsInbred()!= null) && ( ! filter.getBackgroundIsInbred().isEmpty())) {
            switch (filter.getBackgroundIsInbred()) {
                case "Y":
                case "y":
                    backgroundIsInbredWhere = "  AND (b.inbred = 'Y')\n";
                    break;
                    
                case "N":
                case "n":
                    backgroundIsInbredWhere = "  AND (b.inbred = 'N')\n";
                    break;
                    
                default:
                    backgroundIsInbredWhere = "  AND (((b.inbred != 'Y') && (b.inbred != 'N')) || (b.inbred IS NULL))\n";
                    break;
            }
            queryString += backgroundIsInbredWhere;
        }

        queryString += "ORDER BY b.name, b.symbol\n";
        
        try {
            getCurrentSession().beginTransaction();
            SQLQuery query = getCurrentSession().createSQLQuery(queryString);
            
            if ( ! backgroundNameWhere.isEmpty())
                query.setParameter("backgroundName", "%" + filter.getBackgroundName() + "%");
            if ( ! backgroundSymbolWhere.isEmpty())
                query.setParameter("backgroundSymbol", "%" + filter.getBackgroundSymbol() + "%");
            
            targetList = query.addEntity(Background.class).list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
        
        return targetList;
    }

    /**
     * Returns a distinct filtered list of background names suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the background name (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct background names filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * @throws HibernateException if a hibernate error occurs
     */
    public List<String> getNames(String filterTerm) throws HibernateException {
        List<String> targetList = new ArrayList();
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT name FROM backgrounds WHERE name LIKE :name")
                    .setParameter("name", "%" + filterTerm + "%")
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        if (sourceList != null) {
            Iterator iterator = sourceList.iterator();
            while (iterator.hasNext()) {
                String name = (String)iterator.next();
                targetList.add(name);
            }
        }
            
        return targetList;
    }

    /**
     * Returns a distinct filtered list of background symbols suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the background symbol (used in sql LIKE clause)
     * @return a <code>List&lt;String&gt;</code> of distinct background symbols filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * @throws HibernateException if a hibernate error occurs
     */
    public List<String> getSymbols(String filterTerm) throws HibernateException {
        List<String> targetList = new ArrayList();
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT symbol FROM backgrounds WHERE symbol LIKE :symbol")
                    .setParameter("symbol", "%" + filterTerm + "%")
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        if (sourceList != null) {
            Iterator iterator = sourceList.iterator();
            while (iterator.hasNext()) {
                String symbol = (String)iterator.next();
                targetList.add(symbol);
            }
        }
            
        return targetList;
    }
    
}
