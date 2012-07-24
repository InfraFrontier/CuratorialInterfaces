/*
 * AvailabilitiesStrainsDAO.java
 *
 * Created on 07 January 2008, 14:49
 *
 */

package org.emmanet.model;

import java.io.Serializable;

/**
 *
 * @author phil
 */
public class AvailabilitiesStrainsDAO_1 implements Serializable{
    private int str_id_str;
    private int avail_id;


    public int getStr_id_str() {
        return str_id_str;
    }

    public void setStr_id_str(int str_id_str) {
        this.str_id_str = str_id_str;
    }

    public int getAvail_id() {
        return avail_id;
    }

    public void setAvail_id(int avail_id) {
        this.avail_id = avail_id;
    }


    
}
