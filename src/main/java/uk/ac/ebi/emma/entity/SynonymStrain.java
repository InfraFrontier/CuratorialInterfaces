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
public class SynonymStrain {
    private Integer synonymStrain_key = 0;                                     // primary key (was id_syn)
    
    private String name;
    
    // FOREIGN KEYS
    private int strain_key;                                                    // foreign key to strains table (was str_id_str)
    
    private Date   last_change;                                                 // date last changed
    private String username;                                                    // changed by username

    public Integer getSynonymStrain_key() {
        return synonymStrain_key;
    }

    public void setSynonymStrain_key(Integer synonymStrain_key) {
        this.synonymStrain_key = synonymStrain_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStrain_key() {
        return strain_key;
    }

    public void setStrain_key(int strain_key) {
        this.strain_key = strain_key;
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

}
