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

/**
 *
 * @author phil
 */
public class MutationStrain {
    private Integer mut_id = 0;                                                 // first half of primary key
    private Integer str_id_str = 0;                                             // second half of primary key
    
    // CLASS INSTANCES
    private Mutation mutation;                                                  // (was mutationsDAO)
    private Strain strain;                                                      // (was strainsDAO)

    public Integer getMut_id() {
        return mut_id;
    }

    public void setMut_id(Integer mut_id) {
        this.mut_id = mut_id;
    }

    public Integer getStr_id_str() {
        return str_id_str;
    }

    public void setStr_id_str(Integer str_id_str) {
        this.str_id_str = str_id_str;
    }

    public Mutation getMutation() {
        return mutation;
    }

    public void setMutation(Mutation mutation) {
        this.mutation = mutation;
    }

    public Strain getStrain() {
        return strain;
    }

    public void setStrain(Strain strain) {
        this.strain = strain;
    }

}
