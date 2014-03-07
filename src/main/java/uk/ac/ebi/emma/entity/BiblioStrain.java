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
 * @author phil, mrelac
 */
public class BiblioStrain {
    private Integer biblio_key = 0;                                            // first half of primary key (foreign key to biblios table) (was bib_id_biblio)
    private Integer strain_key = 0;                                            // second half of primary key (foreign key to strains table) (was str_id_str)
    
    // CLASS INSTANCES
    private Biblio biblio;                                                      // (was bibliosDAO)
    private Strain strain;                                                      // (was strainsDAO)

    public Integer getBiblio_key() {
        return biblio_key;
    }

    public void setBiblio_key(Integer biblio_key) {
        this.biblio_key = biblio_key;
    }

    public Integer getStrain_key() {
        return strain_key;
    }

    public void setStrain_key(Integer strain_key) {
        this.strain_key = strain_key;
    }

    public Biblio getBiblio() {
        return biblio;
    }

    public void setBiblio(Biblio biblio) {
        this.biblio = biblio;
    }

    public Strain getStrain() {
        return strain;
    }

    public void setStrain(Strain strain) {
        this.strain = strain;
    }

}
