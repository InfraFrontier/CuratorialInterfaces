/**
 * Copyright © 2014 EMBL - European Bioinformatics Institute
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

package uk.ac.ebi.emma.entity;

import java.util.Date;
import java.util.Set;
import javax.persistence.Transient;

/**
 *
 * @author phil, mrelac
 */
public class Gene {
    
    private Integer gene_key = 0;                                              // primary key (was id_gene)
    
    private Integer centimorgan;
    private String chromosome;
    private String cytoband;
    private String ensemblReference;                                            // (was ensembl_ref)
    private String founderLineNumber;                                           // (was founder_line_number)
    private String mgiReference;                                                // (was mgi_ref)
    private String name;
    private String plasmidConstruct;                                            // (was plasmid_construct)
    private String promoter;
    private String species;
    private String symbol;
    
    // COLLECTIONS
    private Set<Allele>      alleles;
    private Set<GeneSynonym> synonyms;
    
    private Date   last_change;                                                 // date last changed
    private String username;                                                    // changed by username
    
    private boolean isDirty = false;

    public Integer getGene_key() {
        return gene_key;
    }

    public void setGene_key(Integer gene_key) {
        this.gene_key = gene_key;
    }

    public Integer getCentimorgan() {
        return centimorgan;
    }

    public void setCentimorgan(Integer centimorgan) {
        this.centimorgan = centimorgan;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public String getCytoband() {
        return cytoband;
    }

    public void setCytoband(String cytoband) {
        this.cytoband = cytoband;
    }

    public String getEnsemblReference() {
        return ensemblReference;
    }

    public void setEnsemblReference(String ensemblReference) {
        this.ensemblReference = ensemblReference;
    }

    public String getFounderLineNumber() {
        return founderLineNumber;
    }

    public void setFounderLineNumber(String founderLineNumber) {
        this.founderLineNumber = founderLineNumber;
    }

    public String getMgiReference() {
        return mgiReference;
    }

    public void setMgiReference(String mgiReference) {
        this.mgiReference = mgiReference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlasmidConstruct() {
        return plasmidConstruct;
    }

    public void setPlasmidConstruct(String plasmidConstruct) {
        this.plasmidConstruct = plasmidConstruct;
    }

    public String getPromoter() {
        return promoter;
    }

    public void setPromoter(String promoter) {
        this.promoter = promoter;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Set<Allele> getAlleles() {
        return alleles;
    }

    public void setAlleles(Set<Allele> alleles) {
        this.alleles = alleles;
    }

    public Set<GeneSynonym> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(Set<GeneSynonym> synonyms) {
        this.synonyms = synonyms;
    }

    public Date getLast_change() {
        return last_change;
    }

    public void setLast_change(Date last_change) {
        this.last_change = last_change;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Transient
    public boolean isIsDirty() {
        return isDirty;
    }

    @Transient
    public void setIsDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }

}
