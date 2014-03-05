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

/**
 *
 * @author mrelac
 */
public class Allele {
    
    private Integer id_allele = 0;                                              // primary key
    
    private String mgi_ref;
    private String name;
    private String symbol;
    
    // FOREIGN KEYS
    private Integer gen_id_gene;                                                // foreign key to genes table
    private Set<Mutation> mutations;                                            // foreign key to mutations table

    // CLASS INSTANCES
    private Gene gene;
    
    private Date last_change;                                                   // date last changed
    private String username;                                                    // changed by username

    public Integer getId_allele() {
        return id_allele;
    }

    public void setId_allele(Integer id_allele) {
        this.id_allele = id_allele;
    }

    public String getMgi_ref() {
        return mgi_ref;
    }

    public void setMgi_ref(String mgi_ref) {
        this.mgi_ref = mgi_ref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getGen_id_gene() {
        return gen_id_gene;
    }

    public void setGen_id_gene(Integer gen_id_gene) {
        this.gen_id_gene = gen_id_gene;
    }

    public Set<Mutation> getMutations() {
        return mutations;
    }

    public void setMutations(Set<Mutation> mutations) {
        this.mutations = mutations;
    }

    public Gene getGene() {
        return gene;
    }

    public void setGene(Gene gene) {
        this.gene = gene;
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
