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
 * @author phil, mrelac
 */
public class MutationStrain {
    private Integer mutation_key = 0;                                          // first half of primary key (foreign key to mutations table) (was mut_id)
    private Integer strain_key = 0;                                            // second half of primary key (foreign key to strains table) (was str_id_str)
    
    // CLASS INSTANCES
    private Mutation mutation;                                                  // (was mutationsDAO)
    private Strain   strain;                                                    // (was strainsDAO)

    public Integer getMutation_key() {
        return mutation_key;
    }

    public void setMutation_key(Integer mutation_key) {
        this.mutation_key = mutation_key;
    }

    public Integer getStrain_key() {
        return strain_key;
    }

    public void setStrain_key(Integer strain_key) {
        this.strain_key = strain_key;
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
