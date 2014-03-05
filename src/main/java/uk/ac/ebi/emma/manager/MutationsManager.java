/**
 * Copyright © 2008 EMBL - European Bioinformatics Institute
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

/**
 *
 * @author phil, mrelac
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Component;
import uk.ac.ebi.emma.Exception.PersistFailedException;
import uk.ac.ebi.emma.entity.Mutation;
import uk.ac.ebi.emma.util.Filter;
import uk.ac.ebi.emma.util.Utils;

@Component
public class MutationsManager extends AbstractManager {

    /**
     * Delete mutation.
     * @param mutation the to be deleted
     * @throws HibernateException if a hibernate error occurs
     */
    public void delete(Mutation mutation) throws HibernateException {
        try {
            getCurrentSession().beginTransaction();
            getCurrentSession().delete(mutation);
            getCurrentSession().getTransaction().commit();

        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
    }
    
    /**
     * Delete mutation.
     * 
     * @param id_mutation the primary key of the mutation to be deleted
     * @throws HibernateException if a hibernate error occurs
     */
    public void delete(int id_mutation) throws HibernateException {
        delete(getMutation(id_mutation));
    }
    
    /**
     * Returns the number of mutations for the given background foreign key
     * 
     * @param bg_id_bg mutations table's foreign key to the backgrounds table
     * @return the number of mutations for the given background foreign key
     * @throws HibernateException if a hibernate error occurs
     */
    public int getBackgroundIDCount(int bg_id_bg) throws HibernateException {
        int count = 0;
        try {
            getCurrentSession().beginTransaction();
            count = (int)getCurrentSession().createSQLQuery("SELECT COUNT(bg_id_bg) FROM mutations WHERE bg_id_bg = ?")
                    .setParameter(0, bg_id_bg)
                    .uniqueResult();
            getCurrentSession().getTransaction().commit();

        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
        
        return count;
    }
    
    /**
     * Returns the <code>Mutation</code> matching <code>id_mutation</code>
     * @param id_mutation the desired mutation id
     * 
     * @return the <code>Mutation</code> matching <code>id_mutation</code>.
     * @throws HibernateException if a hibernate error occurs
     */
    public Mutation getMutation(int id_mutation) throws HibernateException {
        Mutation mutation = null;
        try {
            getCurrentSession().beginTransaction();
            mutation = (Mutation)getCurrentSession().createQuery("FROM Mutation m WHERE id_mutation = ?")
                    .setParameter(0, id_mutation)
                    .uniqueResult();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
        
        return mutation;
    }
    
    /**
     * Returns the <code>Mutation</code> matching <code>id_strain</code>
     * @param id_strain the desired strain id
     * 
     * @return the <code>Mutation</code> matching <code>id_strain</code>.
     * @throws HibernateException if a hibernate error occurs
     */
    public List getMutationsByStrainID(int id_strain) throws HibernateException {
        List<Mutation> mutations = null;
        try {
            getCurrentSession().beginTransaction();
            mutations = getCurrentSession().createQuery("FROM MutationStrain m WHERE str_id_str = ?")
                    .setParameter(0, id_strain)
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
        
        return mutations;
    }
    
    /**
     * Returns a <code>List&lt;String&gt;</code> of distinct mutation subtypes suitable
     * for autocomplete sourcing.
     * 
     * @return a <code>List&lt;String&gt;</code> of distinct mutation subtypes suitable
     *         for autocomplete sourcing.
     */
    public List<String> getMutationSubtypes() {
        List<String> targetList = new ArrayList();
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession().createSQLQuery("SELECT DISTINCT sub_type FROM mutations ORDER BY sub_type").list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        if (sourceList != null) {
            Iterator iterator = sourceList.iterator();
            while (iterator.hasNext()) {
                String value = (String)iterator.next();
                targetList.add(value);
            }
        }
            
        return targetList;
    }
    
    /**
     * Returns a <code>List&lt;String&gt;</code> of distinct mutation types suitable
     * for autocomplete sourcing.
     * 
     * @return a <code>List&lt;String&gt;</code> of distinct mutation types suitable
     *         for autocomplete sourcing.
     */
    public List<String> getMutationTypes() {
        List<String> targetList = new ArrayList();
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession().createSQLQuery("SELECT DISTINCT main_type FROM mutations ORDER BY main_type").list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        if (sourceList != null) {
            Iterator iterator = sourceList.iterator();
            while (iterator.hasNext()) {
                String value = (String)iterator.next();
                targetList.add(value);
            }
        }
            
        return targetList;
    }
    
    /**
     * Given a <code>Filter</code> object describing any/all of the following
     * fields:
     * <ul>
     * <li>mutation ID (may be a comma-separated list)</li>
     * <li>mutation type</li>
     * <li>mutation subtype</li>
     * <li>strain ID (may be a comma-separated list)</li>
     * <li>background ID (may be a comma-separated list)</li>
     * <li>allele ID (may be a comma-separated list)</li>
     * this method performs a query, ANDing all non-empty fields in a WHERE
     * clause against the mutations table (joined to the strains, alleles, and
     * backgrounds tables for strain, allele, and background Ids).
     * 
     * The result is a <code>List&lt;Mutation&gt;</code> of
     * qualifying results. A list is always returned, even if there are no results.
     * 
     * @param filter values to filter by
     * @return a list of matching <code>Mutation</code> instances.
     * @throws NumberFormatException if ids are not numeric (commas and whitespace are OK)
     */
    public List<Mutation> getFilteredMutationsList(Filter filter) throws NumberFormatException {
        String mutationIdWhere = "";
        String mutationTypeWhere = "";
        String mutationSubtypeWhere = "";
        String strainIdWhere = "";
        String alleleIdWhere = "";
        String backgroundIdWhere = "";
        
        List<Mutation> targetList = new ArrayList();
        
        String queryString = "SELECT * FROM mutations m\nWHERE (1 = 1)\n";     
        
        if ((filter.getMutationId() != null) && ( ! filter.getMutationId().isEmpty())) {
            String mutationIds = Utils.cleanIntArray(filter.getMutationId());
            if (Utils.isValidIntArray(mutationIds)) {
                mutationIdWhere = "  AND (m.id IN (" + mutationIds + "))\n";
                queryString += mutationIdWhere;
            }
        }
        if ((filter.getMutationType() != null) && ( ! filter.getMutationType().isEmpty())) {
            mutationTypeWhere = "  AND (m.main_type = :mutationType)\n";
            queryString += mutationTypeWhere;
        }
        if ((filter.getMutationSubtype() != null) && ( ! filter.getMutationSubtype().isEmpty())) {
            mutationSubtypeWhere = "  AND (m.sub_type = :mutationSubtype)\n";
            queryString += mutationSubtypeWhere;
        }
        if ((filter.getStrainId() != null) && ( ! filter.getStrainId().isEmpty())) {
            String strainIds = Utils.cleanIntArray(filter.getStrainId());
            if (Utils.isValidIntArray(strainIds)) {
                strainIdWhere = "  AND (m.str_id_str in (" + strainIds + "))\n";
                queryString += strainIdWhere;
            }
        }
        if ((filter.getAlleleId() != null) && ( ! filter.getAlleleId().isEmpty())) {
            String alleleIds = Utils.cleanIntArray(filter.getAlleleId());
            if (Utils.isValidIntArray(alleleIds)) {
                alleleIdWhere = "  AND (m.alls_id_allel in (" + alleleIds + "))\n";
                queryString += alleleIdWhere;
            }
        }
        if ((filter.getBackgroundId() != null) && ( ! filter.getBackgroundId().isEmpty())) {
            String backgroundIds = Utils.cleanIntArray(filter.getBackgroundId());
            if (Utils.isValidIntArray(backgroundIds)) {
                backgroundIdWhere = "  AND (m.bg_id_bg IN (" + backgroundIds + "))\n";
                queryString += backgroundIdWhere;
            }
        }

        queryString += "ORDER BY m.main_type, m.sub_type\n";
        
        try {
            getCurrentSession().beginTransaction();
            SQLQuery query = getCurrentSession().createSQLQuery(queryString);
            
            if ( ! mutationTypeWhere.isEmpty())
                query.setParameter("mutationType", filter.getMutationType());
            if ( ! mutationSubtypeWhere.isEmpty())
                query.setParameter("mutationSubtype", filter.getMutationSubtype());
                
            targetList = query.addEntity(Mutation.class).list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
            
        return targetList;
    }
    
    /**
     * Saves the given <code>Mutation</code> instance
     * @param mutation the <code>Mutation</code> instance to be saved
     * @throws PersistFailedException if save fails
     */
    public void save(Mutation mutation) throws PersistFailedException {
        mutation.setLast_change(new Date());
        mutation.setUsername(username);
        try {
            getCurrentSession().beginTransaction();
            getCurrentSession().saveOrUpdate(mutation);
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw new PersistFailedException("Failed to save mutation. Reason: " + e.getLocalizedMessage());
        }
    } 
    

    // LEGACY METHODS
    
    
    /**
     * This is a legacy method. Callers of this method should instead call
     * getMutation(id) directly.
     * 
     * When all EMMA code has been ported and references to this method removed,
     * getMutByID() may be removed.
     * 
     * @param id
     * @return the mutation matching <em>id</em>
     * @deprecated Please use getMutation() instead.
     */
    @Deprecated
    public Mutation getMutByID(int id) {
        return getMutation(id);
    }

    /**
     * This is a legacy method. Callers of this method should instead call
     * getMutationsByStrainId(id_strain) directly.
     * 
     * When all EMMA code has been ported and references to this method removed,
     * getMutationIDsByStrain() may be removed.
     * 
     * @param strID
     * @return the mutation matching <em>id</em>
     * @deprecated Please use getMutationsByStrainID() instead.
     */
    @Deprecated
    public List getMutationIDsByStrain(int strID) {
        return getMutationsByStrainID(strID);
    }
}
