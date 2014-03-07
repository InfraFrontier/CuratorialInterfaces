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
 * 
 * NOTE: Prior to EMMA2 this class was called org.emmanet.model.RToolsDAO.
 */

package uk.ac.ebi.emma.entity;

import java.io.Serializable;

/**
 *
 * @author phil, mrelac
 */
public class RtoolStrain implements Serializable {

    private int cvRtool_key;                                                   // first half of primary key (foreign key to cv_rtools table) (was rtls_id)
    private int strain_key;                                                    // second half of primary key (foreign key to strains table) (was str_id_str)
    
    // CLASS INSTANCES
    private CvRtool cvRtool;                                                    // (was cvrtoolsDAO)
    private Strain  strain;                                                     // (was strainsDAO)

    public int getCvRtool_key() {
        return cvRtool_key;
    }

    public void setCvRtool_key(int cvRtool_key) {
        this.cvRtool_key = cvRtool_key;
    }

    public int getStrain_key() {
        return strain_key;
    }

    public void setStrain_key(int strain_key) {
        this.strain_key = strain_key;
    }

    public CvRtool getCvRtool() {
        return cvRtool;
    }

    public void setCvRtool(CvRtool cvRtool) {
        this.cvRtool = cvRtool;
    }

    public Strain getStrain() {
        return strain;
    }

    public void setStrain(Strain strain) {
        this.strain = strain;
    }

}
