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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

// DECIDE ON A JSON TECHNOLOGY. Gson? json-simple?
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import uk.ac.ebi.emma.entity.Allele;
import uk.ac.ebi.emma.entity.Gene;
import uk.ac.ebi.emma.entity.GeneSynonym;
import uk.ac.ebi.emma.util.Filter;
import uk.ac.ebi.emma.util.Utils;




/**
 *
 * @author phil, mrelac
 */
public class GenesManager  extends AbstractManager {
//    protected static Logger logger = Logger.getLogger(GenesManager.class);

    /**
     * Returns the full list of genes from persistent storage.
     * @return  the full list of genes from persistent storage.
     */
    public List<Gene> getGenes() {
        List<Gene> genesList = null;
        Session session = getCurrentSession();
        try {
            session.beginTransaction();
            genesList = session.createQuery("FROM Gene").list();
            session.getTransaction().commit();
            logger.info("current hibernate session is " + (getCurrentSession().isOpen() ? "OPENED" : "CLOSED"));
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        
        return genesList;
    }
    
    /**
     * Saves the given <code>Gene</code> gene
     * @param gene the <code>Gene</code> to be saved
     */
    public void save(Gene gene) {
        Integer centimorgan = Utils.tryParseInt(gene.getCentimorgan());
        gene.setCentimorgan(centimorgan == null ? null : centimorgan.toString());   // Centimorgans are numeric, nullable in the database, so re-map any non-numeric values to null.
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTimestamp = dateFormat.format(new Date());
        gene.setLast_change(currentTimestamp);
        // FIXME FIXME FIXME ***************** USE USER LOGIN NAME ***************  FIXME FIXME FIXME
        gene.setUsername("EMMA");
        try {
            getCurrentSession().beginTransaction();
            getCurrentSession().saveOrUpdate(gene);
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
    }
    
    /**
     * Deletes the named <code>Gene</code> object.
     * @param gDAO the <code>Gene</code> object to be deleted
     */
    public void delete(Gene gDAO) {
        try {
            getCurrentSession().beginTransaction();
            getCurrentSession().delete(gDAO);
            getCurrentSession().getTransaction().commit();

        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
    }

    /**
     * Returns a <code>List&lt;String&gt;</code> of distinct gene ids suitable for
     * autocomplete sourcing.
     * @return a <code>List&lt;String&gt;</code> of gene ids suitable for
     *         autocomplete sourcing.
     */
    public List<String> getGeneIds() {
        List<Gene> genesDAOList = getGenes();
        List<String> targetList = new ArrayList();
        
        if (genesDAOList != null) {
            for (Gene genesDAO : genesDAOList) {
                String sId_gene = Integer.toString(genesDAO.getId_gene());
                targetList.add(Utils.wrap(sId_gene, "\""));
            }
        }
        
        logger.debug("targetList count = " + targetList.size());
        return targetList;
    }
    
    /**
     * Returns a <code>List&lt;String&gt;</code> of distinct gene names suitable for
     * autocomplete sourcing.
     * @return a <code>List&lt;String&gt;</code> of distinct gene names suitable for
     *         autocomplete sourcing.
     */
    public List<String> getNames() {
        List<String> targetList = new ArrayList();
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession().createSQLQuery("SELECT DISTINCT name FROM genes").list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        if (sourceList != null) {
            Iterator iterator = sourceList.iterator();
            while (iterator.hasNext()) {
                String name = (String)iterator.next();
                targetList.add(Utils.wrap(name, "\""));
            }
        }
            
        return targetList;
    }
    
    /**
     * Returns a <code>List&lt;String&gt;</code> of distinct gene symbols suitable
     * for autocomplete sourcing.
     * @return a <code>List&lt;String&gt;</code> of distinct gene symbols suitable
     *         for autocomplete sourcing.
     */
    public List<String> getSymbols() {
        List<String> targetList = new ArrayList();
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession().createSQLQuery("SELECT DISTINCT symbol FROM genes").list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        if (sourceList != null) {
            Iterator iterator = sourceList.iterator();
            while (iterator.hasNext()) {
                String symbol = (String)iterator.next();
                targetList.add(Utils.wrap(symbol, "\""));
            }
        }
            
        return targetList;
    }
    
    /**
     * Returns a <code>List&lt;String&gt;</code> of distinct gene chromosomes suitable
     * for autocomplete sourcing.
     * @return a <code>List&lt;String&gt;</code> of distinct gene chromosomes suitable
     *         for autocomplete sourcing.
     */
    public List<String> getChromosomes() {
        List<String> targetList = new ArrayList();
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession().createSQLQuery("SELECT DISTINCT chromosome FROM genes ORDER BY CAST(chromosome AS unsigned) ASC").list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        if (sourceList != null) {
            Iterator iterator = sourceList.iterator();
            while (iterator.hasNext()) {
                String chromosome = (String)iterator.next();
                targetList.add(Utils.wrap(chromosome, "\""));
            }
        }
            
        return targetList;
    }
    
    /**
     * Returns a <code>List&lt;String&gt;</code> of distinct MGI references suitable
     * for autocomplete sourcing.
     * @return a <code>List&lt;String&gt;</code> of distinct MGI references suitable
     *         for autocomplete sourcing.
     */
    public List<String> getMGIReferences() {
        List<String> targetList = new ArrayList();
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession().createSQLQuery("SELECT DISTINCT mgi_ref FROM genes ORDER BY CAST(mgi_ref AS unsigned) ASC").list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        if (sourceList != null) {
            Iterator iterator = sourceList.iterator();
            while (iterator.hasNext()) {
                String mgiRef = (String)iterator.next();
                targetList.add(Utils.wrap(mgiRef, "\""));
            }
        }
            
        return targetList;
    }
    
    /**
     * Returns the <code>Gene</code> object matching <code>id_gene</code>
     * @param id_gene the gene id to match
     * @return the <code>Gene</code> object matching <code>id_gene</code>.
     */
    public Gene getGene(int id_gene) {
        Gene genesDAO = null;
        try {
            getCurrentSession().beginTransaction();
            genesDAO = (Gene)getCurrentSession().createQuery("FROM Gene g WHERE id_gene = ?")
                    .setParameter(0, id_gene)
                    .uniqueResult();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
        }
        
        return remapNulls(genesDAO);
    }
    
    /**
     * Returns the first <code>Gene</code> object matching <code>name</code>
     * @param name the gene name to match
     * @return the first <code>Gene</code> object matching <code>id_gene</code>,
     * if found; null otherwise.
     */
    public Gene getGene(String name) {
        List<Gene> genesDAOList = null;
        Gene genesDAO = null;
        
        if ((name == null) || (name.trim().isEmpty()))
            return genesDAO;
        
        try {
            getCurrentSession().beginTransaction();
            genesDAOList = getCurrentSession().createQuery("FROM Gene g WHERE name = ?")
                    .setParameter(0, name)
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
        }
        
        if ((genesDAOList != null) && ( ! genesDAOList.isEmpty())) {
            genesDAO = remapNulls(genesDAOList.get(0));
        }
        
        return genesDAO;
    }
    
    /**
     * Given a <code>Filter</code> object describing any/all of the following
     * fields:
     * <ul><li>gene chromosome</li>
     * <li>gene ID</li>
     * <li>gene name</li>
     * <li>gene symbol</li>
     * <li>MGI reference</li></ul>,
     * this method performs a query, ANDing all non-empty fields in a WHERE
     * clause against the genes table. The result is a <code>List&lt;Gene
     * &gt;</code> of qualifying results. A list is always returned, even if
     * there are no results.
     * 
     * @param filter values to filter by
     * @return a list of <code>Gene</code>.
     */
    public List<Gene> getFilteredGenesList(Filter filter) {
        String chromosomeWhere = "";
        String geneIdWhere = "";
        String geneNameWhere = "";
        String geneSymbolWhere = "";
        String mgiReferenceWhere = "";
        List<Gene> targetList = new ArrayList();
        int geneId = -1;
        
        String queryString = "SELECT * FROM genes\nWHERE (1 = 1)";
        if ((filter.getChromosome() != null) && ( ! filter.getChromosome().isEmpty())) {
            chromosomeWhere = "  AND (chromosome = :chromosome)\n";
            queryString += chromosomeWhere;
        }
        Integer iGeneId = Utils.tryParseInt(filter.getGeneId());
        if ((iGeneId != null) && (iGeneId.intValue() > 0)) {
            geneId = iGeneId.intValue();
            geneIdWhere = "  AND (id_gene = :id_gene)\n";
            queryString += geneIdWhere;
        }
        if ((filter.getGeneName() != null) && ( ! filter.getGeneName().isEmpty())) {
            geneNameWhere = "  AND (name LIKE :name)\n";
            queryString += geneNameWhere;
        }
        if ((filter.getGeneSymbol() != null) && ( ! filter.getGeneSymbol().isEmpty())) {
            geneSymbolWhere = "  AND (symbol LIKE :symbol)\n";
            queryString += geneSymbolWhere;
        }
        if ((filter.getMgiReference()!= null) && ( ! filter.getMgiReference().isEmpty())) {
            mgiReferenceWhere = "  AND (mgi_ref LIKE :mgi_ref)\n";
            queryString += mgiReferenceWhere;
        }
        queryString += "ORDER BY name\n";
        
        try {
            getCurrentSession().beginTransaction();
            SQLQuery query = getCurrentSession().createSQLQuery(queryString);
            if ( ! chromosomeWhere.isEmpty())
                query.setParameter("chromosome", filter.getChromosome());
            if ( ! geneIdWhere.isEmpty())
                query.setParameter("id_gene", geneId);
            if ( ! geneNameWhere.isEmpty())
                query.setParameter("name", "%" + filter.getGeneName() + "%");
            if ( ! geneSymbolWhere.isEmpty())
                query.setParameter("symbol", "%" + filter.getGeneSymbol() + "%");
            if ( ! mgiReferenceWhere.isEmpty())
                query.setParameter("mgi_ref", "%" + filter.getMgiReference() + "%");
                
            targetList = query.addEntity(Gene.class).list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
            
        return targetList;
    }

    /**
     * Transforms a <code>List&lt;Gene&gt;</code> to a JSON string.
     * @param genesDAOList the list to be transformed
     * @return the transformed JSON string
     */
    public static String toJSON(List<Gene> genesDAOList) {
        JSONArray jsonList = new JSONArray();
        for (Gene gene : genesDAOList) {
            JSONObject jsonGeneDAO = new JSONObject();
            jsonGeneDAO.put("centimorgan",         gene.getCentimorgan() == null ? "" : gene.getCentimorgan());
            jsonGeneDAO.put("chromosome",          gene.getChromosome() == null ? "" : gene.getChromosome());
            jsonGeneDAO.put("cytoband",            gene.getCytoband() == null ? "" : gene.getCytoband());
            jsonGeneDAO.put("ensembl_ref",         gene.getEnsembl_ref() == null ? "" : gene.getEnsembl_ref());
            jsonGeneDAO.put("founder_line_number", gene.getFounder_line_number() == null ? "" : gene.getFounder_line_number());
            jsonGeneDAO.put("id_gene",             Integer.toString(gene.getId_gene()));
            jsonGeneDAO.put("mgi_ref",             gene.getMgi_ref() == null ? "" : gene.getMgi_ref());
            jsonGeneDAO.put("name",                gene.getName() == null ? "" : gene.getName());
            jsonGeneDAO.put("plasmid_construct",   gene.getPlasmid_construct() == null ? "" : gene.getPlasmid_construct());
            jsonGeneDAO.put("promoter",            gene.getPromoter()== null ? "" : gene.getPromoter());
            jsonGeneDAO.put("species",             gene.getSpecies() == null ? "" : gene.getSpecies());
            jsonGeneDAO.put("symbol",              gene.getSymbol() == null ? "" : gene.getSymbol());
            
            if ((gene.getSynonyms() != null) && (gene.getSynonyms().size() > 0)) {
                JSONArray synonyms = new JSONArray();
                Iterator<GeneSynonym> iterator = gene.getSynonyms().iterator();
                while (iterator.hasNext()) {
                    GeneSynonym syn_genesDAO = iterator.next();
                    JSONObject synonym = new JSONObject();
                    synonym.put("id_syn", Integer.toString(syn_genesDAO.getId_syn()));
                    synonym.put("name",   syn_genesDAO.getName());
                    synonym.put("symbol", syn_genesDAO.getSymbol());
                    synonyms.add(synonym);
                }
                jsonGeneDAO.put("synonyms", synonyms);
            }
            
            jsonList.add(jsonGeneDAO);
        }
        

        // Gson dosn't reserve space for fields with null values!!!!
////////        Gson gson = new Gson();
////////            String s = gson.toJson(genesDAOList);
////////            System.out.println(s);
////////        return s;
        
        return jsonList.toString();
    }
    
    public static String toJson(Gene genesDAO) {
        return new Gson().toJson(genesDAO);
    }
    
    /**
     * Returns a <code>List&lt;Allele&gt;</code> of allele records matching
     * <code>id_gene</code>.
     * @param id_gene the gene id to match
     * @return  a <code>List&lt;Allele&gt;</code> of allele records matching
     * <code>id_gene</code>.
     */
    public List<Allele> getBoundAlleles(int id_gene) {
        List<Allele> allelesDAOList = null;
        try {
            getCurrentSession().beginTransaction();
            // NOTE: Allele's id_gene parameter is defined as a STRING! It would be
            // more appropriate to change it to an int, but might well break existing code.
            allelesDAOList = getCurrentSession().createQuery(
                    "FROM Allele WHERE gen_id_gene = ?").setParameter(0, Integer.toString(id_gene)).list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
        }

        return allelesDAOList;
    }
    
    /**
     * Finds the <code>GeneSynonym</code> identified by <code>
     * id_syn_genesTarget</code> by searching the genesDAO's <code>GeneSynonym
     * </code> collection. Returns the object if found; null otherwise.
     * @param genesDAO the <code>Gene</code>containing the <code>GeneSynonym
     * </code> collection
     * @param id_syn the id_syn to match
     * @return The object if found; null otherwise.
     */
    public static GeneSynonym findSyn_genesDAO(Gene genesDAO, int id_syn) {
        if (genesDAO.getSynonyms() == null)
            return null;
        Iterator<GeneSynonym> syn_genesIterator = genesDAO.getSynonyms().iterator();
        while (syn_genesIterator.hasNext()) {
            GeneSynonym itSyn_genesDAO = syn_genesIterator.next();
            if (itSyn_genesDAO.getId_syn() == id_syn) {
                return itSyn_genesDAO;
            }
        }
        
        return null;
    }
    
    /**
     * Adds a new synonym to the gene identified by <code>gene</code>.
     * @param gene the <code>Gene</code> instance to which the new synonym is to be added
     * @return the new <code>GeneSynonym</code> instance.
     */
    public GeneSynonym addSynonym(Gene gene) {
        synchronized(gene) {
            Set<GeneSynonym> syn_genesDAOSet = gene.getSynonyms();
            if (syn_genesDAOSet == null) {
                syn_genesDAOSet = new LinkedHashSet();
                gene.setSynonyms(syn_genesDAOSet);
            }
            GeneSynonym syn_genesDAO = new GeneSynonym();
            syn_genesDAO.setLast_change(new Date());
            syn_genesDAO.setUsername("EMMA");
            syn_genesDAO.setGene(gene);
            gene.getSynonyms().add(syn_genesDAO);
            save(gene);
            
            return syn_genesDAO;
        }
    }
    
    /**
     * Deletes the synonym identified by the primary key <code>id_syn</code> from
     * the <code>Gene</code> object identified by <code>gene</code>.
     * @param gene the gene from which the synonym is to be deleted
     * @param id_syn the primary key of the synonym to be deleted
     */
    public void deleteSynonym(Gene gene, int id_syn) {
        if (gene == null)
            return;
        
        synchronized(gene) {
            GeneSynonym syn_genesDAO = findSyn_genesDAO(gene, id_syn);
            if (syn_genesDAO != null) {
                gene.getSynonyms().remove(syn_genesDAO);
                
                try {
                    getCurrentSession().beginTransaction();
                    getCurrentSession().delete(syn_genesDAO);
                    getCurrentSession().getTransaction().commit();
                } catch (HibernateException e) {
                    getCurrentSession().getTransaction().rollback();
                }
            }
        }
    }
    
    
    // PRIVATE METHODS


    /**
     * Remaps null fields to empty strings suitable for use in the client.
     * @param genesDAO the instance to remap
     * @return the same instance, with nulls remapped to empty strings.
     */
    private Gene remapNulls(Gene genesDAO) {
        // Re-map null fields to empty strings.
        if (genesDAO != null) {
            if (genesDAO.getCentimorgan() == null)
                genesDAO.setCentimorgan("");
            if (genesDAO.getChromosome()== null)
                genesDAO.setChromosome("");
            if (genesDAO.getCytoband()== null)
                genesDAO.setCytoband("");
            if (genesDAO.getEnsembl_ref()== null)
                genesDAO.setEnsembl_ref("");
            if (genesDAO.getFounder_line_number()== null)
                genesDAO.setFounder_line_number("");
            if (genesDAO.getLast_change()== null)
                genesDAO.setLast_change("");
            if (genesDAO.getMgi_ref()== null)
                genesDAO.setMgi_ref("");
            if (genesDAO.getName() == null)
                genesDAO.setName("");
            if (genesDAO.getPlasmid_construct()== null)
                genesDAO.setPlasmid_construct("");
            if (genesDAO.getPromoter()== null)
                genesDAO.setPromoter("");
            if (genesDAO.getSpecies()== null)
                genesDAO.setSpecies("");
            if (genesDAO.getSymbol() == null)
                genesDAO.setSymbol("");
            if (genesDAO.getUsername()== null)
                genesDAO.setUsername("");
        }
        
        return genesDAO;
    }
    
    
}
