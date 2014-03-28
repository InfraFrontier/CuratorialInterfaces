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

// DECIDE ON A JSON TECHNOLOGY. Gson? json-simple?
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import uk.ac.ebi.emma.Exception.PersistFailedException;
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
     * @throws HibernateException if a hibernate error occurs
     */
    public List<Gene> getGenes() throws HibernateException {
        List<Gene> genesList = null;
        
        try {
            getCurrentSession().beginTransaction();
            genesList = getCurrentSession()
                    .createQuery("FROM Gene")
                    .list();
            getCurrentSession().getTransaction().commit();
            logger.info("current hibernate session is " + (getCurrentSession().isOpen() ? "OPENED" : "CLOSED"));
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
        
        return genesList;
    }

    /**
     * Saves the given <code>Gene</code> instance
     * @param gene the <code>Gene</code> instance to be saved
     * @throws PersistFailedException if save fails
     */
    public void save(Gene gene) throws PersistFailedException {
        Integer centimorgan = Utils.tryParseInt(gene.getCentimorgan());
        gene.setCentimorgan(centimorgan == null ? null : centimorgan);          // Centimorgans are numeric, nullable in the database, so re-map any non-numeric values to null.
    
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
        gene.setLast_change(now);
        gene.setUsername(username);
        try {
            getCurrentSession().beginTransaction();
            getCurrentSession().saveOrUpdate(gene);
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw new PersistFailedException("Failed to save gene. Reason: " + e.getLocalizedMessage());
        }
    }
    
    /**
     * Deletes the named <code>Gene</code> object.
     * @param gene the <code>Gene</code> object to be deleted
     * @throws HibernateException if a hibernate error occurs
     */
    public void delete(Gene gene) throws HibernateException {
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
     * @throws HibernateException if a hibernate error occurs
     */
    public void delete(int id) throws HibernateException {
        delete(getGene(id));
    }

    /**
     * Returns a <code>List&lt;String&gt;</code> of distinct gene ids suitable for
     * autocomplete sourcing.
     * @return a <code>List&lt;String&gt;</code> of gene ids suitable for
     *         autocomplete sourcing.
     * @throws HibernateException if a hibernate error occurs
     */
    public List<String> getGeneIds() throws HibernateException {
        List<Gene> genesList = getGenes();
        List<String> targetList = new ArrayList();
        
        if (genesList != null) {
            for (Gene gene : genesList) {
                String sGene_key = Integer.toString(gene.getGene_key());
                targetList.add(sGene_key);
            }
        }
        
        logger.debug("targetList count = " + targetList.size());
        return targetList;
    }

    /**
     * Returns the full list of gene_key, name, symbol, and mgiReference from persistent storage.
     * @return  the full list of genes (gene_key, name, symbol, and mgiReference only) from persistent storage.
     * @throws HibernateException if a hibernate error occurs
     * 
     * NOTE: This query is instantaneous versus using HSQL, which is noticeably slower.
     */
    public List<Gene> getGeneNames() throws HibernateException {
        List<Gene> genesList = new ArrayList();
        
        try {
            getCurrentSession().beginTransaction();
            Object o = getCurrentSession()
                    .createSQLQuery("SELECT id_gene AS gene_key, name, symbol, mgi_ref AS mgiReference FROM genes")
                    .list();
            List list = (List)o;
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Object[] row = (Object[])it.next();
                Integer id = (Integer)row[0];
                String name = (String)row[1];
                String symbol = (String)row[2];
                String mgiReference = (String)row[3];
                Gene gene = new Gene();
                gene.setGene_key(id);
                gene.setName(name);
                gene.setSymbol(symbol);
                gene.setMgiReference(mgiReference);
                genesList.add(gene);
            }
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
        
        return genesList;
    }

    /**
     * Returns a distinct filtered list of gene names suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the gene name (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct gene names filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * @throws HibernateException if a hibernate error occurs
     */
    public List<String> getNames(String filterTerm) throws HibernateException {
        List<String> targetList = new ArrayList();
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT name FROM genes WHERE name LIKE :name")
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
     * Returns a distinct filtered list of gene symbols suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the gene symbol (used in sql LIKE clause)
     * @return a <code>List&lt;String&gt;</code> of distinct gene symbols filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * @throws HibernateException if a hibernate error occurs
     */
    public List<String> getSymbols(String filterTerm) throws HibernateException {
        List<String> targetList = new ArrayList();
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT symbol FROM genes WHERE symbol LIKE :symbol")
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
     * Returns a <code>List&lt;String&gt;</code> of distinct gene chromosomes suitable
     * for autocomplete sourcing.
     * @return a <code>List&lt;String&gt;</code> of distinct gene chromosomes suitable
     *         for autocomplete sourcing.
     * @throws HibernateException if a hibernate error occurs
     */
    public List<String> getChromosomes() throws HibernateException {
        List<String> targetList = new ArrayList();
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT chromosome FROM genes WHERE chromosome IS NOT NULL ORDER BY CAST(chromosome AS unsigned) ASC")
                    .list();
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
     * Returns a distinct list of centimorgans suitable for autocomplete sourcing.
     * @return a list of centimorgans suitable for autocomplete sourcing.
     */
    public List<String> getCentimorgans() throws HibernateException {
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT centimorgan FROM genes ORDER BY centimorgan")
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        return sourceList;
    }
    
    /**
     * Returns a distinct list of cytobands suitable for autocomplete sourcing.
     * @param filterTerm the filter term for the cytobands name (used in sql LIKE clause)
     * @return a list of cytobands suitable for autocomplete sourcing.
     */
    public List<String> getCytobands(String filterTerm) throws HibernateException {
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT cytoband FROM genes WHERE cytoband LIKE :cytoband ORDER BY cytoband")
                    .setParameter("cytoband", "%" + filterTerm + "%")
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        return sourceList;
    }
    
    /**
     * Returns a distinct list of plasmid constructs suitable for autocomplete sourcing.
     * @param filterTerm the filter term for the plasmid constructs name (used in sql LIKE clause)
     * @return a list of plasmid constructs suitable for autocomplete sourcing.
     */
    public List<String> getPlasmidConstructs(String filterTerm) throws HibernateException {
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT plasmid_construct FROM genes WHERE plasmid_construct LIKE :plasmidConstruct ORDER BY plasmid_construct")
                    .setParameter("plasmidConstruct", "%" + filterTerm + "%")
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        return sourceList;
    }
    
    /**
     * Returns a distinct list of promoters suitable for autocomplete sourcing.
     * @param filterTerm the filter term for the promoters name (used in sql LIKE clause)
     * @return a list of promoters suitable for autocomplete sourcing.
     */
    public List<String> getPromoters(String filterTerm) throws HibernateException {
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT promoter FROM genes WHERE promoter LIKE :promoter ORDER BY promoter")
                    .setParameter("promoter", "%" + filterTerm + "%")
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        return sourceList;
    }
    
    /**
     * Returns a distinct list of founder line numbers suitable for autocomplete sourcing.
     * @param filterTerm the filter term for the founder line numbers name (used in sql LIKE clause)
     * @return a list of founder line numbers suitable for autocomplete sourcing.
     */
    public List<String> getFounderLineNumbers(String filterTerm) throws HibernateException {
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT founder_line_number FROM genes WHERE founder_line_number LIKE :founderlinenumber ORDER BY founder_line_number")
                    .setParameter("founderlinenumber", "%" + filterTerm + "%")
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        return sourceList;
    }
    
    /**
     * Returns a distinct list of speciess suitable for autocomplete sourcing.
     * @param filterTerm the filter term for the speciess name (used in sql LIKE clause)
     * @return a list of speciess suitable for autocomplete sourcing.
     */
    public List<String> getSpecies(String filterTerm) throws HibernateException {
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT species FROM genes WHERE species LIKE :species ORDER BY species")
                    .setParameter("species", "%" + filterTerm + "%")
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        return sourceList;
    }

    /**
     * Returns a distinct filtered list of gene mgi references suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the gene name (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct gene mgi references filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * @throws HibernateException if a hibernate error occurs
     */
    public List<String> getMGIReferences(String filterTerm) throws HibernateException {
        List<String> targetList = new ArrayList();
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT mgi_ref FROM genes WHERE mgi_ref LIKE :mgiReference ORDER BY CAST(mgi_ref AS unsigned) ASC")
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
     * Returns the <code>Gene</code> object matching <code>gene_key</code>
     * @param gene_key the gene id to match
     * @return the <code>Gene</code> object matching <code>gene_key</code>.
     * @throws HibernateException if a hibernate error occurs
     */
    public Gene getGene(Integer gene_key) throws HibernateException {
        if (gene_key == null)
            return null;
        
        Gene gene = null;
        try {
            getCurrentSession().beginTransaction();
            gene = (Gene)getCurrentSession()
                    .createQuery("FROM Gene g WHERE gene_key = :gene_key")
                    .setParameter("gene_key", gene_key)
                    .uniqueResult();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
        
        return gene;
    }
    
    /**
     * Returns the first <code>Gene</code> object matching <code>name</code>
     * @param name the gene name to match
     * @return the first <code>Gene</code> object matching <code>name</code>,
     * if found; null otherwise.
     * @throws HibernateException if a hibernate error occurs
     */
    public Gene getGene(String name) throws HibernateException {
        List<Gene> genesList = null;
        Gene gene = null;
        
        if ((name == null) || (name.trim().isEmpty()))
            return gene;
        
        try {
            getCurrentSession().beginTransaction();
            genesList = getCurrentSession()
                    .createQuery("FROM Gene g WHERE name = :name")
                    .setParameter("name", name)
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
        
        if ((genesList != null) && ( ! genesList.isEmpty())) {
            gene = genesList.get(0);
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
     * <li>MGI reference</li></ul>
     * this method performs a query, ANDing all non-empty fields in a WHERE
     * clause against the genes table. The result is a <code>List&lt;Gene
     * &gt;</code> of qualifying results. A list is always returned, even if
     * there are no results.
     * 
     * @param filter values to filter by
     * @return a list of <code>Gene</code>.
     * @throws NumberFormatException if primary keys are not numeric (commas and whitespace are OK),
     *         HibernateException if a hibernate error occurs
     */
    public List<Gene> getFilteredGenesList(Filter filter) throws NumberFormatException, HibernateException {
        String chromosomeWhere = "";
        String geneIdWhere = "";
        String geneNameWhere = "";
        String geneSymbolWhere = "";
        String mgiReferenceWhere = "";
        List<Gene> targetList = new ArrayList();
        int gene_key = -1;
        
        String queryString = "SELECT * FROM genes\nWHERE (1 = 1)";
        if ((filter.getChromosome() != null) && ( ! filter.getChromosome().isEmpty())) {
            chromosomeWhere = "  AND (chromosome = :chromosome)\n";
            queryString += chromosomeWhere;
        }
        if ((filter.getGene_key() != null) && ( ! filter.getGene_key().isEmpty())) {
            String geneIds = Utils.cleanIntArray(filter.getGene_key());
            if (Utils.isValidIntArray(geneIds)) {
                geneIdWhere = "  AND (id_gene IN (" + geneIds + "))\n";
                queryString += geneIdWhere;
            }
        }
        if ((filter.getGeneName() != null) && ( ! filter.getGeneName().isEmpty())) {
            geneNameWhere = "  AND (name LIKE :name)\n";
            queryString += geneNameWhere;
        }
        if ((filter.getGeneSymbol() != null) && ( ! filter.getGeneSymbol().isEmpty())) {
            geneSymbolWhere = "  AND (symbol LIKE :symbol)\n";
            queryString += geneSymbolWhere;
        }
        if ((filter.getGeneMgiReference()!= null) && ( ! filter.getGeneMgiReference().isEmpty())) {
            mgiReferenceWhere = "  AND (mgi_ref LIKE :mgiReference)\n";
            queryString += mgiReferenceWhere;
        }
        queryString += "ORDER BY name\n";
        
        try {
            getCurrentSession().beginTransaction();
            SQLQuery query = getCurrentSession().createSQLQuery(queryString);
            if ( ! chromosomeWhere.isEmpty())
                query.setParameter("chromosome", filter.getChromosome());
            if ( ! geneNameWhere.isEmpty())
                query.setParameter("name", "%" + filter.getGeneName() + "%");
            if ( ! geneSymbolWhere.isEmpty())
                query.setParameter("symbol", "%" + filter.getGeneSymbol() + "%");
            if ( ! mgiReferenceWhere.isEmpty())
                query.setParameter("mgiReference", "%" + filter.getGeneMgiReference() + "%");
                
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
            jsonGene.put("ensemblReference",    gene.getEnsemblReference()== null ? "" : gene.getEnsemblReference());
            jsonGene.put("founderLineNumber",   gene.getFounderLineNumber()== null ? "" : gene.getFounderLineNumber());
            jsonGene.put("gene_key",           Integer.toString(gene.getGene_key()));
            jsonGene.put("mgiReference",        gene.getMgiReference()== null ? "" : gene.getMgiReference());
            jsonGene.put("name",                gene.getName() == null ? "" : gene.getName());
            jsonGene.put("plasmidConstruct",    gene.getPlasmidConstruct()== null ? "" : gene.getPlasmidConstruct());
            jsonGene.put("promoter",            gene.getPromoter()== null ? "" : gene.getPromoter());
            jsonGene.put("species",             gene.getSpecies() == null ? "" : gene.getSpecies());
            jsonGene.put("symbol",              gene.getSymbol() == null ? "" : gene.getSymbol());
            
            if ((gene.getSynonyms() != null) && (gene.getSynonyms().size() > 0)) {
                JSONArray synonyms = new JSONArray();
                Iterator<GeneSynonym> iterator = gene.getSynonyms().iterator();
                while (iterator.hasNext()) {
                    GeneSynonym geneSynonym = iterator.next();
                    JSONObject synonym = new JSONObject();
                    synonym.put("geneSynonym_key", Integer.toString(geneSynonym.getGeneSynonym_key()));
                    synonym.put("name",             geneSynonym.getName());
                    synonym.put("symbol",           geneSynonym.getSymbol());
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
     * <code>gene_key</code>.
     * @param gene_key the gene primary key to match
     * @return  a <code>List&lt;Allele&gt;</code> of allele records matching
     * <code>gene_key</code>.
     * @throws HibernateException if a hibernate error occurs
     */
    public List<Allele> getBoundAlleles(int gene_key) throws HibernateException {
        List<Allele> allelesList = null;
        try {
            getCurrentSession().beginTransaction();
            allelesList = getCurrentSession()
                    .createQuery("FROM Allele WHERE gene_key = :gene_key")
                    .setParameter("gene_key", Integer.toString(gene_key))
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        return allelesList;
    }
    
    /**
     * Looks for the <code>GeneSynonym</code> identified by <code>
     * geneSynonym_key</code> by searching the gene's <code>GeneSynonym
     * </code> collection. Returns the object if found; null otherwise.
     * @param gene gene <code>Gene</code>containing the <code>GeneSynonym
     * </code> collection
     * @param geneSynonym_key the geneSynonym key to match
     * @return The object if found; null otherwise.
     * @throws HibernateException if a hibernate error occurs
     */
    public static GeneSynonym findGeneSynonym(Gene gene, int geneSynonym_key) throws HibernateException {
        if (gene.getSynonyms() == null)
            return null;
        Iterator<GeneSynonym> geneSynonymIterator = gene.getSynonyms().iterator();
        while (geneSynonymIterator.hasNext()) {
            GeneSynonym geneSynonym = geneSynonymIterator.next();
            if (geneSynonym.getGeneSynonym_key() == geneSynonym_key) {
                return geneSynonym;
            }
        }
        
        return null;
    }
    
    /**
     * Adds a new synonym to the gene identified by <code>gene</code>.
     * @param gene the <code>Gene</code> instance to which the new synonym is to be added
     * @return the new <code>GeneSynonym</code> instance.
     * @exception PersistFailedException if save fails
     */
    public GeneSynonym addSynonym(Gene gene) throws PersistFailedException {
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
     * Deletes the synonym identified by the primary key <code>geneSynonym_key</code> from
     * the <code>Gene</code> object identified by <code>gene</code>.
     * @param gene the gene geneSynonym_key which the synonym is to be deleted
     * @param geneSynonym_key the primary key of the gene synonym to be deleted
     * @throws HibernateException if a hibernate error occurs
     */
    public void deleteSynonym(Gene gene, int geneSynonym_key) throws HibernateException {
        if (gene == null)
            return;
        
        synchronized(gene) {
            GeneSynonym geneSynonym = findGeneSynonym(gene, geneSynonym_key);
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
}
