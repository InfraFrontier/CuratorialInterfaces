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
    private Integer str_id_str = 0;                                             // primary key

    // FOREIGN KEYS
    private Integer lab_id_labo;                                                // foreign key to laboratories table
    private Integer sour_id;                                                    // foreign key to sources table

    // CLASS INSTANCES
    private Laboratory laboratory;                                              // (was labsDAO)
    private CvSource cvSource;                                                    // (was cvsourcesDAO)

    public Integer getSour_id() {
        return sour_id;
    }

    public void setSour_id(Integer sour_id) {
        this.sour_id = sour_id;
    }

    public Integer getLab_id_labo() {
        return lab_id_labo;
    }

    public void setLab_id_labo(Integer lab_id_labo) {
        this.lab_id_labo = lab_id_labo;
    }

    public Integer getStr_id_str() {
        return str_id_str;
    }

    public void setStr_id_str(Integer str_id_str) {
        this.str_id_str = str_id_str;
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
