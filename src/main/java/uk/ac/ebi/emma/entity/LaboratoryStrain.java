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
public class LaboratoryStrain {
    private Integer laboratory_key = null;                                      // first half of primary key (foreign key to laboratories table) (was lab_id_labo)
    private Integer strain_key = null;                                          // second half of primary key (foreign key to strains table) (was strain_key)
    
    // CLASS INSTANCES
    private Laboratory laboratory;                                              // (was labsDAO)
    private Strain     strain;                                                  // (was strainsDAO)

    public Integer getLaboratory_key() {
        return laboratory_key;
    }

    public void setLaboratory_key(Integer laboratory_key) {
        this.laboratory_key = laboratory_key;
    }

    public Integer getStrain_key() {
        return strain_key;
    }

    public void setStrain_key(Integer strain_key) {
        this.strain_key = strain_key;
    }

    public Laboratory getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(Laboratory laboratory) {
        this.laboratory = laboratory;
    }

    public Strain getStrain() {
        return strain;
    }

    public void setStrain(Strain strain) {
        this.strain = strain;
    }

}