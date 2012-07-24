/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.model;

/**
 *
 * @author phil
 */
public class ProjectsStrainsDAO {

    private int str_id_str;
    private int project_id;
    private StrainsDAO strainsDAO;
    private CVProjectsDAO cvProjectsDAO;

    public int getStr_id_str() {
        return str_id_str;
    }

    public void setStr_id_str(int str_id_str) {
        this.str_id_str = str_id_str;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public StrainsDAO getStrainsDAO() {
        return strainsDAO;
    }

    public void setStrainsDAO(StrainsDAO strainsDAO) {
        this.strainsDAO = strainsDAO;
    }

    public CVProjectsDAO getCvProjectsDAO() {
        return cvProjectsDAO;
    }

    public void setCvProjectsDAO(CVProjectsDAO cvProjectsDAO) {
        this.cvProjectsDAO = cvProjectsDAO;
    }
}
