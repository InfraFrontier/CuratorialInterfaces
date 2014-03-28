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
import uk.ac.ebi.emma.entity.EsCellLine;
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
     * @param mutation_key the primary key of the mutation to be deleted
     * @throws HibernateException if a hibernate error occurs
     */
    public void delete(int mutation_key) throws HibernateException {
        Mutation mutation = new Mutation();
        mutation.setMutation_key(mutation_key);
        delete(mutation);
    }
    
    /**
     * Returns the number of mutations for the given background foreign key
     * 
     * @param background_key mutations table's foreign key to the backgrounds table
     * @return the number of mutations for the given background foreign key
     * @throws HibernateException if a hibernate error occurs
     */
    public int getBackgroundIDCount(int background_key) throws HibernateException {
        int count = 0;
        try {
            getCurrentSession().beginTransaction();
            count = (int)getCurrentSession().createSQLQuery("SELECT COUNT(bg_id_bg) FROM mutations WHERE bg_id_bg = background_key")
                    .setParameter("background_key", background_key)
                    .uniqueResult();
            getCurrentSession().getTransaction().commit();

        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
        
        return count;
    }
    
    /**
     * Returns a distinct list of es cell types suitable for autocomplete sourcing. The
     * values are taken from the es_cell_lines table
     * @param filterTerm the filter term for the es cell type name (used in sql LIKE clause)
     * @return a list of es cell types suitable for autocomplete sourcing. The
     * values are taken from the es_cell_lines table
     */
    public List<EsCellLine> getEsCellTypes(String filterTerm) throws HibernateException {
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createQuery("FROM EsCellLine WHERE name LIKE :name ORDER BY name")
                    .setParameter("name", "%" + filterTerm.trim() + "%")
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        return sourceList;
    }
    
    /**
     * Returns a distinct list of genotypes suitable for autocomplete sourcing.
     * @param filterTerm the filter term for the genotype name (used in sql LIKE clause)
     * @return a list of genotypes suitable for autocomplete sourcing.
     */
    public List<String> getGenotypes(String filterTerm) throws HibernateException {
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT genotype FROM mutations WHERE genotype LIKE :genotype ORDER BY genotype")
                    .setParameter("genotype", "%" + filterTerm.trim() + "%")
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        return sourceList;
    }
    
    /**
     * Returns a distinct list of causes suitable for autocomplete sourcing.
     * @param filterTerm the filter term for the causes (used in sql LIKE clause)
     * @return a list of causes suitable for autocomplete sourcing.
     */
    public List<String> getCauses(String filterTerm) throws HibernateException {
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT mu_cause FROM mutations WHERE mu_cause LIKE :mu_cause ORDER BY mu_cause")
                    .setParameter("mu_cause", "%" + filterTerm.trim() + "%")
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        return sourceList;
    }
    
    /**
     * Returns a distinct list of dominance names suitable for autocomplete sourcing.
     * @param filterTerm the filter term for the dominance name (used in sql LIKE clause)
     * @return a list of dominance names suitable for autocomplete sourcing.
     */
    public List<String> getDominance(String filterTerm) throws HibernateException {
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT dominance FROM mutations WHERE dominance LIKE :dominance ORDER BY dominance")
                    .setParameter("dominance", "%" + filterTerm.trim() + "%")
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        return sourceList;
    }
    
    /**
     * Returns the <code>Mutation</code> matching <code>mutation_key</code>
     * @param mutation_key the desired mutation primary key
     * 
     * @return the <code>Mutation</code> matching <code>mutation_key</code>.
     * @throws HibernateException if a hibernate error occurs
     */
    public Mutation getMutation(Integer mutation_key) throws HibernateException {
        if (mutation_key == null)
            return null;
        
        Mutation mutation = null;
        try {
            getCurrentSession().beginTransaction();
            mutation = (Mutation)getCurrentSession()
                    .createQuery("FROM Mutation m LEFT JOIN FETCH m.strains WHERE m.mutation_key = :mutation_key")
                    .setParameter("mutation_key", mutation_key)
                    .uniqueResult();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
        
        return mutation;
    }
    
    /**
     * Returns the <code>Mutation_strain_key matching <code>strain_key</code>
     * @param strain_key the desired strain primary key
     * 
     * @return the <code>Mutation</code> matching <code>strain_key</code>.
     * @throws HibernateException if a hibernate error occurs
     */
    public List getMutationsByStrainID(int strain_key) throws HibernateException {
        List<Mutation> mutations = null;
        try {
            getCurrentSession().beginTransaction();
            mutations = getCurrentSession()
                    .createQuery("FROM MutationStrain m WHERE strain_key = :strain_key")
                    .setParameter("strain_key", strain_key)
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
        
        return mutations;
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
        String mutationKeyWhere = "";
        String mutationTypeWhere = "";
        String mutationSubtypeWhere = "";
        String strainKeyWhere = "";
        String alleleKeyWhere = "";
        String backgroundKeyWhere = "";
        String geneKeyWhere = "";
        String geneSymbolWhere = "";
        
        List<Mutation> targetList = new ArrayList();
        
        String queryString = "SELECT m.*, GROUP_CONCAT(ms.str_id_str) AS strain_keys\n"
                           + "FROM mutations m\n"
                           + "LEFT OUTER JOIN mutations_strains ms ON ms.mut_id = m.id\n"
                           + "JOIN alleles a ON a.id_allel = m.alls_id_allel\n"
                           + "JOIN genes g ON g.id_gene = a.gen_id_gene\n"
                           + "WHERE (1 = 1)\n";     
        
        if ((filter.getMutation_key() != null) && ( ! filter.getMutation_key().isEmpty())) {
            String mutationIds = Utils.cleanIntArray(filter.getMutation_key());
            if (Utils.isValidIntArray(mutationIds)) {
                mutationKeyWhere = "  AND (m.id IN (" + mutationIds + "))\n";
                queryString += mutationKeyWhere;
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
        if ((filter.getStrain_key() != null) && ( ! filter.getStrain_key().isEmpty())) {
            String strainIds = Utils.cleanIntArray(filter.getStrain_key());
            if (Utils.isValidIntArray(strainIds)) {
                strainKeyWhere = "  AND (m.str_id_str IN (" + strainIds + "))\n";
                queryString += strainKeyWhere;
            }
        }
        if ((filter.getAllele_key() != null) && ( ! filter.getAllele_key().isEmpty())) {
            String alleleIds = Utils.cleanIntArray(filter.getAllele_key());
            if (Utils.isValidIntArray(alleleIds)) {
                alleleKeyWhere = "  AND (m.alls_id_allel in (" + alleleIds + "))\n";
                queryString += alleleKeyWhere;
            }
        }
        if ((filter.getBackground_key() != null) && ( ! filter.getBackground_key().isEmpty())) {
            String backgroundIds = Utils.cleanIntArray(filter.getBackground_key());
            if (Utils.isValidIntArray(backgroundIds)) {
                backgroundKeyWhere = "  AND (m.bg_id_bg IN (" + backgroundIds + "))\n";
                queryString += backgroundKeyWhere;
            }
        }
        if ((filter.getGene_key()!= null) && ( ! filter.getGene_key().isEmpty())) {
            String geneIds = Utils.cleanIntArray(filter.getGene_key());
            if (Utils.isValidIntArray(geneIds)) {
                geneKeyWhere = "  AND (g.id_gene IN (" + geneIds + "))\n";
                queryString += geneKeyWhere;
            }
        }
        if ((filter.getGeneSymbol() != null) && ( ! filter.getGeneSymbol().isEmpty())) {
            geneSymbolWhere = "  AND (g.symbol = :geneSymbol)\n";
            queryString += geneSymbolWhere;
        }

        queryString += 
                "GROUP BY ms.mut_id\n"
              + "ORDER BY m.main_type, m.sub_type\n";
        
        try {
            getCurrentSession().beginTransaction();
            SQLQuery query = getCurrentSession().createSQLQuery(queryString);
            
            if ( ! mutationTypeWhere.isEmpty())
                query.setParameter("mutationType", filter.getMutationType());
            if ( ! mutationSubtypeWhere.isEmpty())
                query.setParameter("mutationSubtype", filter.getMutationSubtype());
            if ( ! geneSymbolWhere.isEmpty())
                query.setParameter("geneSymbol", filter.getGeneSymbol());
            List resultSet = query.addEntity(Mutation.class).addScalar("strain_keys").list();
            
            if (resultSet != null) {
                for (Object result : resultSet) {
                    Object[] row = (Object[]) result;
                    Mutation mutation = (Mutation)row[0];
                    mutation.setStrain_keys((row[1] == null ? "" : row[1].toString()));     // Add strain_keys to transient Mutation instance.
                    targetList.add(mutation);
                }
            }
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
        
        return targetList;
    }
    
    /**
     * Returns a <code>List&lt;String&gt;</code> of distinct sex suitable
     * for autocomplete sourcing.
     * 
     * @return a <code>List&lt;String&gt;</code> of distinct sex suitable
     *         for autocomplete sourcing.
     */
    public List<String> getSex() {
        List<String> targetList = new ArrayList();
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT sex FROM mutations ORDER BY sex")
                    .list();
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
     * Returns a <code>List&lt;String&gt;</code> of distinct mutation subtypes suitable
     * for autocomplete sourcing.
     * 
     * @return a <code>List&lt;String&gt;</code> of distinct mutation subtypes suitable
     *         for autocomplete sourcing.
     */
    public List<String> getSubtypes() {
        List<String> targetList = new ArrayList();
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT sub_type FROM mutations ORDER BY sub_type")
                    .list();
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
    public List<String> getTypes() {
        List<String> targetList = new ArrayList();
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT main_type FROM mutations ORDER BY main_type")
                    .list();
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
     * Saves the given <code>Mutation</code> instance
     * @param mutation the <code>Mutation</code> instance to be saved
     * @throws PersistFailedException if save fails
     */
    public void save(Mutation mutation) throws PersistFailedException {
        mutation.setLast_change(new Date());
        mutation.setUsername(username);
        
        mutation.setAllele(null);
        mutation.setBackground(null);
        
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
     * getMutationsByStrainId(strain_key) directly.
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
