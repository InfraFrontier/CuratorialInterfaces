/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.model;

import java.io.Serializable;

/**
 *
 * @author phil
 */
public class RToolsDAO implements Serializable {

    private int str_id_str;
    private int rtls_id;
    private StrainsDAO strainsDAO;
    private CVRtoolsDAO cvrtoolsDAO;
   

    public int getStr_id_str() {
        return str_id_str;
    }

    public void setStr_id_str(int str_id_str) {
        this.str_id_str = str_id_str;
    }

    public int getRtls_id() {
        return rtls_id;
    }

    public void setRtls_id(int rtls_id) {
        this.rtls_id = rtls_id;
    }

    public StrainsDAO getStrainsDAO() {
      return strainsDAO;
    }

    public void setStrainsDAO(StrainsDAO strainsDAO) {
       this.strainsDAO = strainsDAO;
    }

    public CVRtoolsDAO getCvrtoolsDAO() {
      return  cvrtoolsDAO;
    }

    public void setCvrtoolsDAO(CVRtoolsDAO cvrtoolsDAO) {
      this.cvrtoolsDAO = cvrtoolsDAO;
    }


}
