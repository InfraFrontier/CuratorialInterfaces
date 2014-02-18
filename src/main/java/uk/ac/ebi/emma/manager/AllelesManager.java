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

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import uk.ac.ebi.emma.entity.Allele;
import uk.ac.ebi.emma.entity.Gene;
import uk.ac.ebi.emma.entity.GeneSynonym;
import uk.ac.ebi.emma.util.Filter;
import uk.ac.ebi.emma.util.Utils;

/**
 *
 * @author mrelac
 */
public class AllelesManager extends AbstractManager {
    private GenesManager genesManager = new GenesManager();
    
     public List getAlleles() {
        Session session = getCurrentSession();
        session.beginTransaction();
        List strains = null;
        try {
            strains = session.createQuery(
                    "FROM Allele").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strains;
    }
     
     //get alleles by id, use SQLQuery and return list.
     
    public List getAllelesByID(String alleleIDs) {
        Session session = getCurrentSession();
        session.beginTransaction();
        List alleles = null;
        try {
            alleles = session.createSQLQuery(
                    "SELECT * FROM alleles where id_allel IN (?)").setParameter(0, alleleIDs).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return alleles;
    }
          
    public Allele getAlleleByID(int id_allel) {
        Session session = getCurrentSession();
        session.beginTransaction();
        Allele ad = null;
        try {
            ad = (Allele) session.get(Allele.class,
                    id_allel);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return ad;
    }
     
     
    
    public void save(Allele aDAO) {
        /************** FIXME FIXME FIXME WHERE IS USERNAME AND TIMESTAMP? *******************/
        /************** FIXME FIXME FIXME WHERE IS USERNAME AND TIMESTAMP? *******************/
        /************** FIXME FIXME FIXME WHERE IS USERNAME AND TIMESTAMP? *******************/
        /************** FIXME FIXME FIXME WHERE IS USERNAME AND TIMESTAMP? *******************/
        Session session = getCurrentSession();
        session.beginTransaction();

        try {
            session.saveOrUpdate(aDAO);
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }    
    
//    public static String toJSON(Allele allele) {
//        return new Gson().toJson(allele);
//    }
    
//    /**
//     * Transforms a <code>List&lt;Gene&gt;</code> to a JSON string.
//     * @param allelesList the list to be transformed
//     * @return the transformed JSON string
//     */
//    public static String toJSON(List<Allele> allelesList) {
//        JSONArray jsonList = new JSONArray();
//        for (Allele allele : allelesList) {
//            JSONObject jsonAllele = new JSONObject();
//            
//            
//            
//            jsonAllele.put("symbol",      allele.getAlls_form() == null ? "" : allele.getAlls_form());
//            jsonAllele.put("gen_id_gene", allele.getGen_id_gene() == null ? "" : allele.getGen_id_gene());
//            jsonAllele.put("id_allel",    Integer.toString(allele.getId_allel()));
//            jsonAllele.put("mgi_ref",     allele.getMgi_ref() == null ? "" : allele.getMgi_ref());
//            jsonAllele.put("name",        allele.getName() == null ? "" : allele.getName());
//            jsonAllele.put("strainId",    allele.getStrainID() == null ? "" : allele.getStrainID());
//            
//            if (allele.getGene() != null) {
//                    JSONObject jsonGene = GenesManager.toJSON(allele.getGene());
//                    jsonGene.put("gene", jsonGene);
//                    
//                    
//                    
//                    
//                    jsonGene.put("symbol", geneSynonym.getSymbol());
//                    jsonGene.put("id_syn", Integer.toString(geneSynonym.getId_syn()));
//                    synonyms.add(jsonGene);
//                }
//                jsonAllele.put("synonyms", synonyms);
//            }
//            
//            if ((allele.getSynonyms() != null) && (allele.getSynonyms().size() > 0)) {
//                JSONArray synonyms = new JSONArray();
//                Iterator<GeneSynonym> iterator = allele.getSynonyms().iterator();
//                while (iterator.hasNext()) {
//                    GeneSynonym geneSynonym = iterator.next();
//                    JSONObject synonym = new JSONObject();
//                    synonym.put("id_syn", Integer.toString(geneSynonym.getId_syn()));
//                    synonym.put("name",   geneSynonym.getName());
//                    synonym.put("symbol", geneSynonym.getSymbol());
//                    synonyms.add(synonym);
//                }
//                jsonAllele.put("synonyms", synonyms);
//            }
//            
//            jsonList.add(jsonAllele);
//        }
//        
//
//        // Gson dosn't reserve space for fields with null values!!!!
//////////        Gson gson = new Gson();
//////////            String s = gson.toJson(genesDAOList);
//////////            System.out.println(s);
//////////        return s;
//        
//        return jsonList.toString();
//    }
    
    
    
    
    
    
    
    /**
     * Deletes the named <code>Allele</code> object.
     * @param allele the <code>Allele</code> object to be deleted
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
     * Deletes the <code>Allele</code> object identified by <b>id</b>.
     * @param id the <code>Allele</code> primary key of object to be deleted
     */
    public void delete(int id) {
        delete(getAllele(id));
    }
    
    /**
     * Returns the <code>Allele</code> object matching <code>id_allele</code>
     * @param id_allele the allele id to match
     * @return the <code>Allele</code> object matching <code>id_allele</code>.
     */
    public Allele getAllele(int id_allele) {
        Allele allele = null;
        try {
            getCurrentSession().beginTransaction();
            allele = (Allele)getCurrentSession().createQuery("FROM Allele g WHERE id_allele = ?")
                    .setParameter(0, id_allele)
                    .uniqueResult();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
        }
        
        return remapNulls(allele);
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
            sourceList = getCurrentSession().createSQLQuery("SELECT DISTINCT name FROM alleles WHERE name LIKE ?").setParameter(0, "%" + filterTerm + "%").list();
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
            sourceList = getCurrentSession().createSQLQuery("SELECT DISTINCT alls_form FROM alleles WHERE alls_form LIKE ?").setParameter(0, "%" + filterTerm + "%").list();
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
     * @throws NumberFormatException if allele id(s) and/or gene id(s) are not numeric.
     * NOTE: a comma-separated list (with optional whitespace between the commas) containing one or more numbers is valid
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

        if ((filter.getAlleleId() != null) && ( ! filter.getAlleleId().isEmpty())) {
            String alleleIds = Utils.cleanIntArray(filter.getAlleleId());
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
        if ((filter.getGeneId() != null) && ( ! filter.getGeneId().isEmpty())) {
            String geneIds = Utils.cleanIntArray(filter.getGeneId());
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

    
    // PRIVATE METHODS


    /**
     * Remaps null fields to empty strings suitable for use in the client.
     * @param allele the instance to remap
     * @return the same instance, with nulls remapped to empty strings.
     */
    private Allele remapNulls(Allele allele) {
        // Re-map null fields to empty strings.
        if (allele != null) {
            if (allele.getMgi_ref() == null)
                allele.setMgi_ref("");
            if (allele.getName() == null)
                allele.setName("");
            if (allele.getStrainID() == null)
                allele.setStrainID("");
            if (allele.getSymbol() == null)
                allele.setSymbol("");
            if (allele.getUsername() == null)
                allele.setUsername("");
        }
        
        return allele;
    }
    
}
