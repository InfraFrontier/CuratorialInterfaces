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
    private Integer id_mutation = 0;                                            // primary key
    
    private String ch_ano_desc;                                                 // chromosome annotated description
    private String ch_ano_name;                                                 // chromosome annotated name
    private String chromosome;                                                  // chromosome
    private String dominance;                                                   // dominance
    private String genotype;                                                    // genotype
    private String ki_alter;                                                    // knock-in alter
    private String main_type;                                                   // type
    private String mu_cause;                                                    // mutation cause
    private String sex;                                                         // sex
    private String sub_type;                                                    // subtype
    private String tm_esline;                                                   // targeted mutation es cell line
    
    // FOREIGN KEYS
    private Integer alls_id_allel;                                              // foreign key to alleles table
    private Integer alls_id_allel_replaced;                                     // foreign key to alleles table for replaced allele
    private Integer bg_id_bg;                                                   // foreign key to backgrounds table
    private Integer str_id_str;                                                 // foreign key to strains table

    // CLASS INSTANCES
    private Allele allele;
    private Background background;
    private Strain strain;
    private Allele replacedAllele;
    
    private Date last_change;                                                   // date last changed
    private String username;                                                    // changed by username

    public Integer getId_mutation() {
        return id_mutation;
    }

    public void setId_mutation(Integer id_mutation) {
        this.id_mutation = id_mutation;
    }

    public String getMain_type() {
        return main_type;
    }

    public void setMain_type(String main_type) {
        this.main_type = main_type;
    }

    public String getSub_type() {
        return sub_type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }

    public String getDominance() {
        return dominance;
    }

    public void setDominance(String dominance) {
        this.dominance = dominance;
    }

    public String getTm_esline() {
        return tm_esline;
    }

    public void setTm_esline(String tm_esline) {
        this.tm_esline = tm_esline;
    }

    public String getCh_ano_name() {
        return ch_ano_name;
    }

    public void setCh_ano_name(String ch_ano_name) {
        this.ch_ano_name = ch_ano_name;
    }

    public String getCh_ano_desc() {
        return ch_ano_desc;
    }

    public void setCh_ano_desc(String ch_ano_desc) {
        this.ch_ano_desc = ch_ano_desc;
    }

    public String getMu_cause() {
        return mu_cause;
    }

    public void setMu_cause(String mu_cause) {
        this.mu_cause = mu_cause;
    }

    public Integer getAlls_id_allel() {
        return alls_id_allel;
    }

    public void setAlls_id_allel(Integer alls_id_allel) {
        this.alls_id_allel = alls_id_allel;
    }

    public Integer getBg_id_bg() {
        return bg_id_bg;
    }

    public void setBg_id_bg(Integer bg_id_bg) {
        this.bg_id_bg = bg_id_bg;
    }

    public Integer getStr_id_str() {
        return str_id_str;
    }

    public void setStr_id_str(Integer str_id_str) {
        this.str_id_str = str_id_str;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getGenotype() {
        return genotype;
    }

    public void setGenotype(String genotype) {
        this.genotype = genotype;
    }

    public String getKi_alter() {
        return ki_alter;
    }

    public void setKi_alter(String ki_alter) {
        this.ki_alter = ki_alter;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLast_change() {
        return last_change;
    }

    public void setLast_change(Date last_change) {
        this.last_change = last_change;
    }

    public Integer getAlls_id_allel_replaced() {
        return alls_id_allel_replaced;
    }

    public void setAlls_id_allel_replaced(Integer alls_id_allel_replaced) {
        this.alls_id_allel_replaced = alls_id_allel_replaced;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
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


}
