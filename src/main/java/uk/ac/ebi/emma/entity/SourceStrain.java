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
public class SourceStrain {
    private Integer strain_key = null;;                                         // first half of primary key (foreign key to strains table) (was str_id_str)
    private Integer cvSource_key = null;                                        // second half of primary key (foreign key to cv_sources table) (was sour_id)

    // FOREIGN KEYS
    private Integer laboratory_key = null;                                      // foreign key to laboratories table (was lab_id_labo)

    // CLASS INSTANCES
    private Laboratory laboratory;                                              // (was labsDAO)
    private CvSource   cvSource;                                                // (was cvsourcesDAO)

    public Integer getCvSource_key() {
        return cvSource_key;
    }

    public void setCvSource_key(Integer cvSource_key) {
        this.cvSource_key = cvSource_key;
    }

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

    public CvSource getCvSource() {
        return cvSource;
    }

    public void setCvSource(CvSource cvSource) {
        this.cvSource = cvSource;
    }

}
