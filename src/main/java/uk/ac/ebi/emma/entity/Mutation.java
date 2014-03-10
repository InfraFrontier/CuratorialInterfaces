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

package uk.ac.ebi.emma.entity;

import java.util.Date;

/**
 *
 * @author phil, mrelac
 */
public class Mutation {
    private Integer mutation_key = 0;                                          // primary key (was id_mutation)
    
    private String cause;                                                       // mutation cause (was mu_cause)
    private String chromosome;                                                  // chromosome
    private String chromosomeAnnotatedDescription;                              // chromosome annotated description (was ch_ano_desc)
    private String chromosomeAnnotatedName;                                     // chromosome annotated name (was ch_ano_name)
    private String dominance;                                                   // dominance
    private String genotype;                                                    // genotype
    private String knockinAlter;                                                // knock-in alter (was ki_alter)
    private String sex;                                                         // sex
    private String subtype;                                                     // subtype (was sub_type)
    private String targetedMutationEsLine;                                      // targeted mutation es cell line (was tm_esline)
    private String type;                                                        // type (was main_type)
    
    // FOREIGN KEYS
    private Integer allele_key;                                                 // foreign key to alleles table (was alls_id_allel)
    private Integer replacedAllele_key;                                         // foreign key to alleles table for replaced allele (was alls_id_allel_replaced)
    private Integer background_key;                                             // foreign key to backgrounds table (was bg_id_bg)
    private Integer strain_key;                                                 // foreign key to strains table (was str_id_str)

    // CLASS INSTANCES
    private Allele     allele;                                                  // (was allelesDAO)
    private Background background;                                              // (was backgroundDAO)
    private Strain     strain;                                                  // (didn't exist before EMMA2)
    private Allele     replacedAllele;                                          // (didn't exist before EMMA2)
    
    private Date   last_change;                                                 // date last changed
    private String username;                                                    // changed by username

    public Integer getMutation_key() {
        return mutation_key;
    }

    public void setMutation_key(Integer mutation_key) {
        this.mutation_key = mutation_key;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public String getChromosomeAnnotatedDescription() {
        return chromosomeAnnotatedDescription;
    }

    public void setChromosomeAnnotatedDescription(String chromosomeAnnotatedDescription) {
        this.chromosomeAnnotatedDescription = chromosomeAnnotatedDescription;
    }

    public String getChromosomeAnnotatedName() {
        return chromosomeAnnotatedName;
    }

    public void setChromosomeAnnotatedName(String chromosomeAnnotatedName) {
        this.chromosomeAnnotatedName = chromosomeAnnotatedName;
    }

    public String getDominance() {
        return dominance;
    }

    public void setDominance(String dominance) {
        this.dominance = dominance;
    }

    public String getGenotype() {
        return genotype;
    }

    public void setGenotype(String genotype) {
        this.genotype = genotype;
    }

    public String getKnockinAlter() {
        return knockinAlter;
    }

    public void setKnockinAlter(String knockinAlter) {
        this.knockinAlter = knockinAlter;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getTargetedMutationEsLine() {
        return targetedMutationEsLine;
    }

    public void setTargetedMutationEsLine(String targetedMutationEsLine) {
        this.targetedMutationEsLine = targetedMutationEsLine;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAllele_key() {
        return allele_key;
    }

    public void setAllele_key(Integer allele_key) {
        this.allele_key = allele_key;
    }

    public Integer getReplacedAllele_key() {
        return replacedAllele_key;
    }

    public void setReplacedAllele_key(Integer replacedAllele_key) {
        this.replacedAllele_key = replacedAllele_key;
    }

    public Integer getBackground_key() {
        return background_key;
    }

    public void setBackground_key(Integer background_key) {
        this.background_key = background_key;
    }

    public Integer getStrain_key() {
        return strain_key;
    }

    public void setStrain_key(Integer strain_key) {
        this.strain_key = strain_key;
    }

    public Allele getAllele() {
        return allele;
    }

    public void setAllele(Allele allele) {
        this.allele = allele;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public Strain getStrain() {
        return strain;
    }

    public void setStrain(Strain strain) {
        this.strain = strain;
    }

    public Allele getReplacedAllele() {
        return replacedAllele;
    }

    public void setReplacedAllele(Allele replacedAllele) {
        this.replacedAllele = replacedAllele;
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

}
