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
public class AvailabilityStrain implements Serializable {
    private Integer cvAvailibility_key = 0;                                    // first half of primary key (foreign key to cv_availibilities table) (was avail_id)
    private Integer strain_key = 0;                                            // second half of primary key (foreign key to strains table) (was str_id_str)
    
    // CLASS INSTANCES
    private CvAvailability availability;                                        // (was cvavailDAO)
    private Strain         strain;                                              // (was strainsDAO)

    public Integer getCvAvailibility_key() {
        return cvAvailibility_key;
    }

    public void setCvAvailibility_key(Integer cvAvailibility_key) {
        this.cvAvailibility_key = cvAvailibility_key;
    }

    public Integer getStrain_key() {
        return strain_key;
    }

    public void setStrain_key(Integer strain_key) {
        this.strain_key = strain_key;
    }

    public CvAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(CvAvailability availability) {
        this.availability = availability;
    }

    public Strain getStrain() {
        return strain;
    }

    public void setStrain(Strain strain) {
        this.strain = strain;
    }
    
}
