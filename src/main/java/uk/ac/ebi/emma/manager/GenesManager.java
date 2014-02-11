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
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

// DECIDE ON A JSON TECHNOLOGY. Gson? json-simple?
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import uk.ac.ebi.emma.entity.Allele;
import uk.ac.ebi.emma.entity.Gene;
import uk.ac.ebi.emma.entity.GeneSynonym;
import uk.ac.ebi.emma.util.Filter;
import uk.ac.ebi.emma.util.Utils;

/**
 *
 * @author phil, mrelac
 */
@Component
public class GenesManager extends AbstractManager {

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
     * Saves the given <code>Gene</code> instance
     * @param gene the <code>Gene</code> instance p0to be saved
     */
    public void save(Gene gene) {
        Integer centimorgan = Utils.tryParseInt(gene.getCentimorgan());
        gene.setCentimorgan(centimorgan == null ? null : centimorgan.toString());   // Centimorgans are numeric, nullable in the database, so re-map any non-numeric values to null.
    
        // 08-Feb-14 (mrelac) After spending a day trying unsuccessfully to get
        // hibernate 4.3.1 to generate timestamps, I am abandoning that approach
        // and instead have added an 'isDirty' Transient property to Gene and
        // SynonymGene that will indicate when to set the last_modified timestamp.
        // It's a bit of overhead, but Hibernate has proven it is unreliable for
        // automatic timestamp insertion.
        
        // Set the timestamp and username for every dirty GeneSynonym.
        Date now = new Date();
        Set<GeneSynonym> synonyms = gene.getSynonyms();
        if ((synonyms != null) && ( ! synonyms.isEmpty())) {
            for (GeneSynonym synonym : synonyms) {
                if (synonym.isIsDirty()) {
                    synonym.setUsername(username);
                    synonym.setLast_change(now);
                }
            }
        }
        gene.setLast_change(new Date());
        gene.setUsername(username);
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
     * @param gene the <code>Gene</code> object to be deleted
     */
    public void delete(Gene gene) {
        try {
            getCurrentSession().beginTransaction();
            getCurrentSession().delete(gene);
            getCurrentSession().getTransaction().commit();

        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
    }
    
    /**
     * Deletes the <code>Gene</code> object identified by <b>id</b>.
     * @param id the <code>Gene</code> primary key of object to be deleted
     */
    public void delete(int id) {
        delete(getGene(id));
    }

    /**
     * Returns a <code>List&lt;String&gt;</code> of distinct gene ids suitable for
     * autocomplete sourcing.
     * @return a <code>List&lt;String&gt;</code> of gene ids suitable for
     *         autocomplete sourcing.
     */
    public List<String> getGeneIds() {
        List<Gene> genesList = getGenes();
        List<String> targetList = new ArrayList();
        
        if (genesList != null) {
            for (Gene gene : genesList) {
                String sId_gene = Integer.toString(gene.getId_gene());
                targetList.add(sId_gene);
            }
        }
        
        logger.debug("targetList count = " + targetList.size());
        return targetList;
    }

    /**
     * Returns a distinct filtered list of gene names suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the gene name (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct gene names filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     */
    public List<String> getNames(String filterTerm) {
        List<String> targetList = new ArrayList();
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession().createSQLQuery("SELECT DISTINCT name FROM genes WHERE name LIKE ?").setParameter(0, "%" + filterTerm + "%").list();
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
     * Returns a distinct filtered list of gene names suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the gene name (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct gene symbols filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     */
    public List<String> getSymbols(String filterTerm) {
        List<String> targetList = new ArrayList();
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession().createSQLQuery("SELECT DISTINCT symbol FROM genes WHERE symbol LIKE ?").setParameter(0, "%" + filterTerm + "%").list();
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
            sourceList = getCurrentSession().createSQLQuery("SELECT DISTINCT chromosome FROM genes WHERE chromosome IS NOT NULL ORDER BY CAST(chromosome AS unsigned) ASC").list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        if (sourceList != null) {
            Iterator iterator = sourceList.iterator();
            while (iterator.hasNext()) {
                String chromosome = (String)iterator.next();
                targetList.add(chromosome);
            }
        }
            
        return targetList;
    }

    /**
     * Returns a distinct filtered list of gene mgi references suitable for autocomplete
     * sourcing.
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
            sourceList = getCurrentSession().createSQLQuery("SELECT DISTINCT mgi_ref FROM genes WHERE mgi_ref LIKE ? ORDER BY CAST(mgi_ref AS unsigned) ASC").setParameter(0, "%" + filterTerm + "%").list();
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
     * Returns the <code>Gene</code> object matching <code>id_gene</code>
     * @param id_gene the gene id to match
     * @return the <code>Gene</code> object matching <code>id_gene</code>.
     */
    public Gene getGene(int id_gene) {
        Gene gene = null;
        try {
            getCurrentSession().beginTransaction();
            gene = (Gene)getCurrentSession().createQuery("FROM Gene g WHERE id_gene = ?")
                    .setParameter(0, id_gene)
                    .uniqueResult();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
        }
        
        return remapNulls(gene);
    }
    
    /**
     * Returns the first <code>Gene</code> object matching <code>name</code>
     * @param name the gene name to match
     * @return the first <code>Gene</code> object matching <code>id_gene</code>,
     * if found; null otherwise.
     */
    public Gene getGene(String name) {
        List<Gene> genesList = null;
        Gene gene = null;
        
        if ((name == null) || (name.trim().isEmpty()))
            return gene;
        
        try {
            getCurrentSession().beginTransaction();
            genesList = getCurrentSession().createQuery("FROM Gene g WHERE name = ?")
                    .setParameter(0, name)
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
        }
        
        if ((genesList != null) && ( ! genesList.isEmpty())) {
            gene = remapNulls(genesList.get(0));
        }
        
        return gene;
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
     * @param genesList the list to be transformed
     * @return the transformed JSON string
     */
    public static String toJSON(List<Gene> genesList) {
        JSONArray jsonList = new JSONArray();
        for (Gene gene : genesList) {
            JSONObject jsonGene = new JSONObject();
            jsonGene.put("centimorgan",         gene.getCentimorgan() == null ? "" : gene.getCentimorgan());
            jsonGene.put("chromosome",          gene.getChromosome() == null ? "" : gene.getChromosome());
            jsonGene.put("cytoband",            gene.getCytoband() == null ? "" : gene.getCytoband());
            jsonGene.put("ensembl_ref",         gene.getEnsembl_ref() == null ? "" : gene.getEnsembl_ref());
            jsonGene.put("founder_line_number", gene.getFounder_line_number() == null ? "" : gene.getFounder_line_number());
            jsonGene.put("id_gene",             Integer.toString(gene.getId_gene()));
            jsonGene.put("mgi_ref",             gene.getMgi_ref() == null ? "" : gene.getMgi_ref());
            jsonGene.put("name",                gene.getName() == null ? "" : gene.getName());
            jsonGene.put("plasmid_construct",   gene.getPlasmid_construct() == null ? "" : gene.getPlasmid_construct());
            jsonGene.put("promoter",            gene.getPromoter()== null ? "" : gene.getPromoter());
            jsonGene.put("species",             gene.getSpecies() == null ? "" : gene.getSpecies());
            jsonGene.put("symbol",              gene.getSymbol() == null ? "" : gene.getSymbol());
            
            if ((gene.getSynonyms() != null) && (gene.getSynonyms().size() > 0)) {
                JSONArray synonyms = new JSONArray();
                Iterator<GeneSynonym> iterator = gene.getSynonyms().iterator();
                while (iterator.hasNext()) {
                    GeneSynonym geneSynonym = iterator.next();
                    JSONObject synonym = new JSONObject();
                    synonym.put("id_syn", Integer.toString(geneSynonym.getId_syn()));
                    synonym.put("name",   geneSynonym.getName());
                    synonym.put("symbol", geneSynonym.getSymbol());
                    synonyms.add(synonym);
                }
                jsonGene.put("synonyms", synonyms);
            }
            
            jsonList.add(jsonGene);
        }
        

        // Gson dosn't reserve space for fields with null values!!!!
////////        Gson gson = new Gson();
////////            String s = gson.toJson(genesDAOList);
////////            System.out.println(s);
////////        return s;
        
        return jsonList.toString();
    }
    
//    public static String toJSON(Gene gene) {
//        return new Gson().toJson(gene);
//    }
    
    /**
     * Returns a <code>List&lt;Allele&gt;</code> of allele records matching
     * <code>id_gene</code>.
     * @param id_gene the gene id to match
     * @return  a <code>List&lt;Allele&gt;</code> of allele records matching
     * <code>id_gene</code>.
     */
    public List<Allele> getBoundAlleles(int id_gene) {
        List<Allele> allelesList = null;
        try {
            getCurrentSession().beginTransaction();
            // NOTE: Allele's id_gene parameter is defined as a STRING! It would be
            // more appropriate to change it to an int, but might well break existing code.
            allelesList = getCurrentSession().createQuery(
                    "FROM Allele WHERE gen_id_gene = ?").setParameter(0, Integer.toString(id_gene)).list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
        }

        return allelesList;
    }
    
    /**
     * Finds the <code>GeneSynonym</code> identified by <code>
     * id_syn_genesTarget</code> by searching the gene's <code>GeneSynonym
     * </code> collection. Returns the object if found; null otherwise.
     * @param gene gene <code>Gene</code>containing the <code>GeneSynonym
     * </code> collection
     * @param id_syn the id_syn to match
     * @return The object if found; null otherwise.
     */
    public static GeneSynonym findGeneSynonym(Gene gene, int id_syn) {
        if (gene.getSynonyms() == null)
            return null;
        Iterator<GeneSynonym> geneSynonymIterator = gene.getSynonyms().iterator();
        while (geneSynonymIterator.hasNext()) {
            GeneSynonym geneSynonym = geneSynonymIterator.next();
            if (geneSynonym.getId_syn() == id_syn) {
                return geneSynonym;
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
            GeneSynonym geneSynonym = new GeneSynonym();
            geneSynonym.setLast_change(new Date());
            geneSynonym.setUsername(username);
            geneSynonym.setGene(gene);
            gene.getSynonyms().add(geneSynonym);
            geneSynonym.setIsDirty(true);
            save(gene);
            
            return geneSynonym;
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
            GeneSynonym geneSynonym = findGeneSynonym(gene, id_syn);
            if (geneSynonym != null) {
                gene.getSynonyms().remove(geneSynonym);
                
                try {
                    getCurrentSession().beginTransaction();
                    getCurrentSession().delete(geneSynonym);
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
     * @param gene the instance to remap
     * @return the same instance, with nulls remapped to empty strings.
     */
    private Gene remapNulls(Gene gene) {
        // Re-map null fields to empty strings.
        if (gene != null) {
            if (gene.getCentimorgan() == null)
                gene.setCentimorgan("");
            if (gene.getChromosome()== null)
                gene.setChromosome("");
            if (gene.getCytoband()== null)
                gene.setCytoband("");
            if (gene.getEnsembl_ref()== null)
                gene.setEnsembl_ref("");
            if (gene.getFounder_line_number()== null)
                gene.setFounder_line_number("");
            if (gene.getMgi_ref()== null)
                gene.setMgi_ref("");
            if (gene.getName() == null)
                gene.setName("");
            if (gene.getPlasmid_construct()== null)
                gene.setPlasmid_construct("");
            if (gene.getPromoter()== null)
                gene.setPromoter("");
            if (gene.getSpecies()== null)
                gene.setSpecies("");
            if (gene.getSymbol() == null)
                gene.setSymbol("");
            if (gene.getUsername()== null)
                gene.setUsername("");
        }
        
        return gene;
    }
    
    
}
