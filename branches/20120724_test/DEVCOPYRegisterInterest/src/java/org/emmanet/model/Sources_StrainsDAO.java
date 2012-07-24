/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.emmanet.model;

/**
 *
 * @author phil
 */
public class Sources_StrainsDAO {
    
private int str_id_str;
private int sour_id;
private int lab_id_labo;

//Relationship mapping
private LabsDAO labsDAO;
private StrainsDAO strainsDAO;
//private CVSourcesDAO cvsourcesDAO;


    public int getStr_id_str() {
        return str_id_str;
    }

    public void setStr_id_str(int str_id_str) {
        this.str_id_str = str_id_str;
    }

    public int getSour_id() {
        return sour_id;
    }

    public void setSour_id(int sour_id) {
        this.sour_id = sour_id;
    }

    public int getLab_id_labo() {
        return lab_id_labo;
    }

    public void setLab_id_labo(int lab_id_labo) {
        this.lab_id_labo = lab_id_labo;
    }

    public LabsDAO getLabsDAO() {
        return labsDAO;
    }

    public void setLabsDAO(LabsDAO labsDAO) {
        this.labsDAO = labsDAO;
    }

    public StrainsDAO getStrainsDAO() {
        return strainsDAO;
    }

    public void setStrainsDAO(StrainsDAO strainsDAO) {
        this.strainsDAO = strainsDAO;
    }

   /* public CVSourcesDAO getCvsourcesDAO() {
        return cvsourcesDAO;
    }

    public void setCvsourcesDAO(CVSourcesDAO cvsourcesDAO) {
        this.cvsourcesDAO = cvsourcesDAO;
    }
*/
}
