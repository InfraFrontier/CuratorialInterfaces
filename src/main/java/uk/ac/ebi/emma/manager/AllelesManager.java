/**
 * Copyright © 2009-2013 EMBL - European Bioinformatics Institute
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
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Component;
import uk.ac.ebi.emma.Exception.PersistFailedException;
import uk.ac.ebi.emma.entity.Allele;
import uk.ac.ebi.emma.util.Filter;
import uk.ac.ebi.emma.util.Utils;

/**
 *
 * @author mrelac
 */
@Component
public class AllelesManager extends AbstractManager {

    /**
     * Delete allele.
     * 
     * @param allele the allele to be deleted
     */
    public void delete(Allele allele) {
        try {
            getCurrentSession().beginTransaction();
            getCurrentSession().delete(allele);
            getCurrentSession().getTransaction().commit();

        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
    }
    
    /**
     * Delete allele.
     * 
     * @param allele_key the primary key of the allele to be deleted
     */
    public void delete(int allele_key) {
        delete(getAllele(allele_key));
    }
    
    /**
     * Returns the <code>Allele</code> object matching <code>allele_key</code>
     * @param allele_key the allele primary key to match
     * @return the <code>Allele</code> object matching <code>allele_key</code>.
     */
    public Allele getAllele(int allele_key) {
        Allele allele = null;
        try {
            getCurrentSession().beginTransaction();
            allele = (Allele)getCurrentSession()
                    .createQuery("FROM Allele a WHERE allele_key = :allele_key")
                    .setParameter("allele_key", allele_key)
                    .uniqueResult();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
        }
        
        return allele;
    }
    
    /**
     * Returns a distinct filtered list of allele names suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the allele name (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct gene names filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     */
    public List<String> getNames(String filterTerm) {
        List<String> targetList = new ArrayList();
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT name FROM alleles WHERE name LIKE :name")
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
     * Returns a distinct filtered list of allele symbols suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the allele symbol (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct gene symbols filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     */
    public List<String> getSymbols(String filterTerm) {
        List<String> targetList = new ArrayList();
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT alls_form FROM alleles WHERE alls_form LIKE :symbol")
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
    
    /**
     * Given a <code>Filter</code> object describing any/all of the following
     * fields:
     * <ul>
     * <li>allele ID</li>
     * <li>allele name</li>
     * <li>allele symbol</li>
     * <li>Allele MGI reference</li></ul>
     * <li>gene ID</li>
     * <li>gene name</li>
     * <li>gene symbol</li>,
     * this method performs a query, ANDing all non-empty fields in a WHERE
     * clause against the alleles table (joined to the genes table for gene name
     * and gene symbol). The result is a <code>List&lt;Allele&gt;</code> of
     * qualifying results. A list is always returned, even if there are no results.
     * 
     * @param filter values to filter by
     * @return a list of <code>Allele</code>.
     * @throws NumberFormatException if ids are not numeric (commas and whitespace are OK)
     */
    public List<Allele> getFilteredAllelesList(Filter filter) throws NumberFormatException {
        String alleleIdWhere = "";
        String alleleNameWhere = "";
        String alleleSymbolWhere = "";
        String geneIdWhere = "";
        String geneNameWhere = "";
        String geneSymbolWhere = "";
        String alleleMgiReferenceWhere = "";
        List<Allele> targetList = new ArrayList();
        
        String queryString = "SELECT * FROM alleles a\nJOIN genes g ON g.id_gene = a.gen_id_gene\nWHERE (1 = 1)\n";

        if ((filter.getAllele_key() != null) && ( ! filter.getAllele_key().isEmpty())) {
            String alleleIds = Utils.cleanIntArray(filter.getAllele_key());
            if (Utils.isValidIntArray(alleleIds)) {
                alleleIdWhere = "  AND (a.id_allel IN (" + alleleIds + "))\n";
                queryString += alleleIdWhere;
            }
        }
        if ((filter.getAlleleName() != null) && ( ! filter.getAlleleName().isEmpty())) {
            alleleNameWhere = "  AND (a.name LIKE :alleleName)\n";
            queryString += alleleNameWhere;
        }
        if ((filter.getAlleleSymbol() != null) && ( ! filter.getAlleleSymbol().isEmpty())) {
            alleleSymbolWhere = "  AND (a.alls_form LIKE :alleleSymbol)\n";
            queryString += alleleSymbolWhere;
        }
        if ((filter.getAlleleMgiReference()!= null) && ( ! filter.getAlleleMgiReference().isEmpty())) {
            alleleMgiReferenceWhere = "  AND (a.mgi_ref LIKE :alleleMgiReference)\n";
            queryString += alleleMgiReferenceWhere;
        }
        if ((filter.getGene_key() != null) && ( ! filter.getGene_key().isEmpty())) {
            String geneIds = Utils.cleanIntArray(filter.getGene_key());
            if (Utils.isValidIntArray(geneIds)) {
                geneIdWhere = "  AND (a.gen_id_gene IN (" + geneIds + "))\n";
                queryString += geneIdWhere;
            }
        }
        if ((filter.getGeneName() != null) && ( ! filter.getGeneName().isEmpty())) {
            geneNameWhere = "  AND (g.name LIKE :geneName)\n";
            queryString += geneNameWhere;
        }
        if ((filter.getGeneSymbol() != null) && ( ! filter.getGeneSymbol().isEmpty())) {
            geneSymbolWhere = "  AND (g.symbol LIKE :geneSymbol)\n";
            queryString += geneSymbolWhere;
        }
        queryString += "ORDER BY a.name\n";
        
        try {
            getCurrentSession().beginTransaction();
            SQLQuery query = getCurrentSession().createSQLQuery(queryString);
            if ( ! alleleNameWhere.isEmpty())
                query.setParameter("alleleName", "%" + filter.getAlleleName() + "%");
            if ( ! alleleSymbolWhere.isEmpty())
                query.setParameter("alleleSymbol", "%" + filter.getAlleleSymbol() + "%");
            if ( ! alleleMgiReferenceWhere.isEmpty())
                query.setParameter("alleleMgiReference", "%" + filter.getAlleleMgiReference()+ "%");
            if ( ! geneNameWhere.isEmpty())
                query.setParameter("geneName", "%" + filter.getGeneName() + "%");
            if ( ! geneSymbolWhere.isEmpty())
                query.setParameter("geneSymbol", "%" + filter.getGeneSymbol() + "%");
                
            targetList = query.addEntity(Allele.class).list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
            
        return targetList;
    }
    
    /**
     * Returns a distinct filtered list of allele mgi references suitable for
     * autocomplete sourcing.
     * 
     * @param filterTerm the filter term for the gene name (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct gene mgi references filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     */
    public List<String> getMGIReferences(String filterTerm) {
        List<String> targetList = new ArrayList();
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT mgi_ref FROM alleles WHERE mgi_ref LIKE :mgiReference ORDER BY CAST(mgi_ref AS unsigned) ASC")
                    .setParameter("mgiReference", "%" + filterTerm + "%")
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        if (sourceList != null) {
            Iterator iterator = sourceList.iterator();
            while (iterator.hasNext()) {
                String mgiReference = (String)iterator.next();
                targetList.add(mgiReference);
            }
        }
            
        return targetList;
    }
    
    /**
     * Saves the given <code>Allele</code> instance
     * @param allele the <code>Allele</code> instance to be saved
     * @throws PersistFailedException if save fails
     */
    public void save(Allele allele) throws PersistFailedException {
        allele.setLast_change(new Date());
        allele.setUsername(username);
        allele.setGene_key(allele.getGene().getGene_key());
        try {
            getCurrentSession().beginTransaction();
            getCurrentSession().saveOrUpdate(allele);
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw new PersistFailedException("Failed to save allele. Reason: " + e.getLocalizedMessage());
        }
    }    
}
