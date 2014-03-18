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
public class ProjectStrain {

    private Integer cvProject_key = null;                                       // first half of primary key (foreign key to cv_projects table) (was project_id)
    private Integer strain_key = null;                                          // second half of primary key (foreign key to strains table) (was str_id_str)
    
    // CLASS INSTANCES
    private CvProject cvProject;                                                // (was cvProjectsDAO)
    private Strain    strain;                                                   // (was strainsDAO)

    public Integer getCvProject_key() {
        return cvProject_key;
    }

    public void setCvProject_key(Integer cvProject_key) {
        this.cvProject_key = cvProject_key;
    }

    public Integer getStrain_key() {
        return strain_key;
    }

    public void setStrain_key(Integer strain_key) {
        this.strain_key = strain_key;
    }

    public CvProject getCvProject() {
        return cvProject;
    }

    public void setCvProject(CvProject cvProject) {
        this.cvProject = cvProject;
    }

    public Strain getStrain() {
        return strain;
    }

    public void setStrain(Strain strain) {
        this.strain = strain;
    }

}
