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

/**
 *
 * @author phil
 */
public class Ilar {
    private Integer ilar_key = 0;                                              // primary key (was id)
    
    private String investigator;
    private String labcode;
    private String organisation;
    private String status;

    public Integer getIlar_key() {
        return ilar_key;
    }

    public void setIlar_key(Integer ilar_key) {
        this.ilar_key = ilar_key;
    }

    public String getInvestigator() {
        return investigator;
    }

    public void setInvestigator(String investigator) {
        this.investigator = investigator;
    }

    public String getLabcode() {
        return labcode;
    }

    public void setLabcode(String labcode) {
        this.labcode = labcode;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
