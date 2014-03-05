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
    private Integer id_syn = 0;                                                 // primary key
    
    private String name;
    
    // FOREIGN KEYS
    private int str_id_str;                                                     // foreign key to strains table
    
    private String username;                                                    // changed by username
    private Date last_change;                                                   // date last changed

    public Integer getId_syn() {
        return id_syn;
    }

    public void setId_syn(Integer id_syn) {
        this.id_syn = id_syn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStr_id_str() {
        return str_id_str;
    }

    public void setStr_id_str(int str_id_str) {
        this.str_id_str = str_id_str;
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
