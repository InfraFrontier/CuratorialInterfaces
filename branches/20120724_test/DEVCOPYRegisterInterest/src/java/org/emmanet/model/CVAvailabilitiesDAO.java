/*
 * CVAvailabilitiesDAO.java
 *
 * Created on 04 January 2008, 10:19
 *
 */

package org.emmanet.model;

/**
 *
 * @author Phil Wilkinson
 */
public class CVAvailabilitiesDAO {
    
    private int id;
    private String code;
    private String description;
    private int in_stock;
    private int to_distr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIn_stock() {
        return in_stock;
    }

    public void setIn_stock(int in_stock) {
        this.in_stock = in_stock;
    }

    public int getTo_distr() {
        return to_distr;
    }

    public void setTo_distr(int to_distr) {
        this.to_distr = to_distr;
    }
}
