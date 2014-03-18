/**
 * Copyright © 2013 EMBL - European Bioinformatics Institute
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
import javax.persistence.Transient;

/**
 *
 * @author mrelac
 */
public class GeneSynonym {
    private Integer geneSynonym_key = null;                                     // primary key (was id_syn)
    
    // CLASS INSTANCES
    private Gene gene;                                                          // (was genes)
    
    private String name;
    private String symbol;
    
    private Date   last_change;                                                 // date last changed
    private String username;                                                    // changed by username
    
    private boolean isDirty;

    public Integer getGeneSynonym_key() {
        return geneSynonym_key;
    }

    public void setGeneSynonym_key(Integer geneSynonym_key) {
        this.geneSynonym_key = geneSynonym_key;
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

    public Gene getGene() {
        return gene;
    }

    public void setGene(Gene gene) {
        this.gene = gene;
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
