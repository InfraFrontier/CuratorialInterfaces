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
    private Integer avail_id = 0;                                               // first half of primary key
    private Integer str_id_str = 0;                                             // second half of primary key
    
    // CLASS INSTANCES
    private CvAvailability availability;                                        // (was cvavailDAO)
    private Strain strain;                                                      // (was strainsDAO)

    public Integer getAvail_id() {
        return avail_id;
    }

    public void setAvail_id(Integer avail_id) {
        this.avail_id = avail_id;
    }

    public Integer getStr_id_str() {
        return str_id_str;
    }

    public void setStr_id_str(Integer str_id_str) {
        this.str_id_str = str_id_str;
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
