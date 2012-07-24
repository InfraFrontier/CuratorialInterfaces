/*

 */
package org.emmanet.model;

/**
 *
 * @author phil
 */
public class Sources_RequestsDAO {

    private int req_id_req;
    private int sour_id;
    //Relationship mapping
    private WebRequestsDAO webrequestsDAO;
    private CVSourcesDAO cvsourcesDAO;

    public int getReq_id_req() {
        return req_id_req;
    }

    public void setReq_id_req(int req_id_req) {
        this.req_id_req = req_id_req;
    }

    public int getSour_id() {
        return sour_id;
    }

    public void setSour_id(int sour_id) {
        this.sour_id = sour_id;
    }

    public CVSourcesDAO getCvsourcesDAO() {
        return cvsourcesDAO;
    }

    public void setCvsourcesDAO(CVSourcesDAO cvsourcesDAO) {
        this.cvsourcesDAO = cvsourcesDAO;
    }

    public WebRequestsDAO getWebrequestsDAO() {
        return webrequestsDAO;
    }

    public void setWebrequestsDAO(WebRequestsDAO webrequestsDAO) {
        this.webrequestsDAO = webrequestsDAO;
    }
}
