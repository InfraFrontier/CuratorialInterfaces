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
    private Integer id_per = 0;                                                 // primary key
    
    private String email;
    private String fax;
    private String firstname;
    private String phone;
    private String surname;
    private String title;
    
    // FOREIGN KEYS
    private Integer id_ilar;                                                    // foreign key to ilar table
    private Integer lab_id_labo;                                                // foreign key to laboratories table
    
    // CLASS INSTANCES
    private Ilar ilar;                                                          // (was ilarDAO)
    private Laboratory laboratory;                                              // (was labsDAO)
    
    private Date last_change;                                                   // date last changed
    private String username;                                                    // changed by username

    public Integer getId_per() {
        return id_per;
    }

    public void setId_per(Integer id_per) {
        this.id_per = id_per;
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

    public Integer getId_ilar() {
        return id_ilar;
    }

    public void setId_ilar(Integer id_ilar) {
        this.id_ilar = id_ilar;
    }

    public Integer getLab_id_labo() {
        return lab_id_labo;
    }

    public void setLab_id_labo(Integer lab_id_labo) {
        this.lab_id_labo = lab_id_labo;
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
