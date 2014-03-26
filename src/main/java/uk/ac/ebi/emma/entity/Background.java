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
 * @author mrelac
 */
public class Background {
    private Integer background_key = null;                                      // primary key (was id_bg)

    private String curated;
    private String inbred;
    private String name;
    private String notes;
    private String species;
    private String symbol;
    
    @Transient
    private String mutation_keys;                                               // foreign key(s) to mutations table (new for EMMA2) (comma-separated)
    
    private Date   last_change;                                                 // date last changed
    private String username;                                                    // changed by username

    public Integer getBackground_key() {
        return background_key;
    }

    public void setBackground_key(Integer background_key) {
        this.background_key = background_key;
    }

    public String getCurated() {
        return curated;
    }

    public void setCurated(String curated) {
        this.curated = curated;
    }

    public String getInbred() {
        return inbred;
    }

    public void setInbred(String inbred) {
        this.inbred = inbred;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public String getMutation_keys() {
        return mutation_keys;
    }

    public void setMutation_keys(String mutation_keys) {
        this.mutation_keys = mutation_keys;
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