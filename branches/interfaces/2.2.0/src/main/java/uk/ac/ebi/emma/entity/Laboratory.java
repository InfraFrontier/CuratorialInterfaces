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
public class Laboratory {
    private Integer id_labo = 0;                                                // primary key
    
    private String addr_line_1;
    private String addr_line_2;
    private String authority;
    private String base_url;
    private String code;
    private String country;
    private String country_code;
    private String dept;
    private String name;
    private String postcode;
    private String province;
    private String town;
    
    private Date last_change;                                                   // date last changed
    private String username;                                                    // changed by username

    public Integer getId_labo() {
        return id_labo;
    }

    public void setId_labo(Integer id_labo) {
        this.id_labo = id_labo;
    }

    public String getAddr_line_1() {
        return addr_line_1;
    }

    public void setAddr_line_1(String addr_line_1) {
        this.addr_line_1 = addr_line_1;
    }

    public String getAddr_line_2() {
        return addr_line_2;
    }

    public void setAddr_line_2(String addr_line_2) {
        this.addr_line_2 = addr_line_2;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
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
