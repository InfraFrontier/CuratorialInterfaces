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

import java.io.Serializable;

/**
 *
 * @author phil, mrelac
 */
public class RToolStrain implements Serializable {

    private int rtls_id;                                                        // first half of primary key
    private int str_id_str;                                                     // second half of primary key
    
    // CLASS INSTANCES
    private CvRTool cvrtool;
    private Strain strain;

    public int getRtls_id() {
        return rtls_id;
    }

    public void setRtls_id(int rtls_id) {
        this.rtls_id = rtls_id;
    }

    public int getStr_id_str() {
        return str_id_str;
    }

    public void setStr_id_str(int str_id_str) {
        this.str_id_str = str_id_str;
    }

    public CvRTool getCvrtool() {
        return cvrtool;
    }

    public void setCvrtool(CvRTool cvrtool) {
        this.cvrtool = cvrtool;
    }

    public Strain getStrain() {
        return strain;
    }

    public void setStrain(Strain strain) {
        this.strain = strain;
    }

}
