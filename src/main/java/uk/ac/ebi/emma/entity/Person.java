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

import java.util.Date;

/**
 *
 * @author phil, mrelac
 */
public class Person {
    private Integer person_key = 0;                                            // primary key (was id_per)
    
    private String email;
    private String fax;
    private String firstname;
    private String phone;
    private String surname;
    private String title;
    
    // FOREIGN KEYS
    private Integer ilar_key;                                                  // foreign key to ilar table (was id_ilar)
    private Integer laboratory_key;                                            // foreign key to laboratories table (was lab_id_labo)
    
    // CLASS INSTANCES
    private Ilar ilar;                                                          // (was ilarDAO)
    private Laboratory laboratory;                                              // (was labsDAO)
    
    private Date   last_change;                                                 // date last changed
    private String username;                                                    // changed by username

    public Integer getPerson_key() {
        return person_key;
    }

    public void setPerson_key(Integer person_key) {
        this.person_key = person_key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIlar_key() {
        return ilar_key;
    }

    public void setIlar_key(Integer ilar_key) {
        this.ilar_key = ilar_key;
    }

    public Integer getLaboratory_key() {
        return laboratory_key;
    }

    public void setLaboratory_key(Integer laboratory_key) {
        this.laboratory_key = laboratory_key;
    }

    public Ilar getIlar() {
        return ilar;
    }

    public void setIlar(Ilar ilar) {
        this.ilar = ilar;
    }

    public Laboratory getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(Laboratory laboratory) {
        this.laboratory = laboratory;
    }

    public Date getLast_change() {
        return last_change;
    }

    public void setLast_change(Date last_change) {
        this.last_change = last_change;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
