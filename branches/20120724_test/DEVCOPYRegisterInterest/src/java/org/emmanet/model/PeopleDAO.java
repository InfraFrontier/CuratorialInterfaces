/*
 * LabsDAO.java
 *
 * Created on 21 February 2008, 10:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.emmanet.model;

/**
 *
 * @author phil
 */
public class PeopleDAO {
    private String id_per;
    private String title;
    private String surname;
    private String firstname;
    private String phone;
    private String fax;
    private String email;
    private String username;
    private String last_change;
    private String lab_id_labo;
    private String ilar_code;
    //relationship mapping
    private LabsDAO labsDAO;
    
    
    public String getId_per() {
        return id_per;
    }

    public void setId_per(String id_per) {
        this.id_per = id_per;
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLast_change() {
        return last_change;
    }

    public void setLast_change(String last_change) {
        this.last_change = last_change;
    }

    public String getLab_id_labo() {
        return lab_id_labo;
    }

    public void setLab_id_labo(String lab_id_labo) {
        this.lab_id_labo = lab_id_labo;
    }

    public LabsDAO getLabsDAO() {
        return labsDAO;
    }

    public void setLabsDAO(LabsDAO labsDAO) {
        this.labsDAO = labsDAO;
    }

    /**
     * @return the ilar_code
     */
    public String getIlar_code() {
        return ilar_code;
    }

    /**
     * @param ilar_code the ilar_code to set
     */
    public void setIlar_code(String ilar_code) {
        this.ilar_code = ilar_code;
    }


}
