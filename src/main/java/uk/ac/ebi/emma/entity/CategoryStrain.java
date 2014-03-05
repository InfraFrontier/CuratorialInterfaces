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
public class CategoryStrain implements Serializable {

    private Integer cat_id_cat = 0;                                             // first half of primary key
    private Integer str_id_str = 0;                                             // second half of primary key
    
    // CLASS INSTANCES
    private Category category;                                                  // (was categoriesDAO)
    private Strain strain;                                                      // (was strainsDAO)

    public Integer getCat_id_cat() {
        return cat_id_cat;
    }

    public void setCat_id_cat(Integer cat_id_cat) {
        this.cat_id_cat = cat_id_cat;
    }

    public Integer getStr_id_str() {
        return str_id_str;
    }

    public void setStr_id_str(Integer str_id_str) {
        this.str_id_str = str_id_str;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Strain getStrain() {
        return strain;
    }

    public void setStrain(Strain strain) {
        this.strain = strain;
    }

}
