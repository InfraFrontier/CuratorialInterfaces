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
public class CvAvailability {
    
    private Integer cvAvailability_key = 0;                                    // primary key (was id)
    
    private String code;
    private String description;
    private Integer in_stock;
    private Integer to_distr;

    public Integer getCvAvailability_key() {
        return cvAvailability_key;
    }

    public void setCvAvailability_key(Integer cvAvailability_key) {
        this.cvAvailability_key = cvAvailability_key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIn_stock() {
        return in_stock;
    }

    public void setIn_stock(Integer in_stock) {
        this.in_stock = in_stock;
    }

    public Integer getTo_distr() {
        return to_distr;
    }

    public void setTo_distr(Integer to_distr) {
        this.to_distr = to_distr;
    }

}
