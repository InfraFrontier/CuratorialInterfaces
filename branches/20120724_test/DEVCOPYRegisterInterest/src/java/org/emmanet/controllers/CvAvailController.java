/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package org.emmanet.controllers;

import java.io.PrintStream;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.emmanet.model.*;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class CvAvailController extends SimpleFormController
{
    private ArchiveManager am;
    public static final String MAP_KEY = "returnedOut";
    private Map returnedOut;
    private String listView;
    private AvailabilitiesStrainsDAO asd;
    private AvailabilitiesStrainsDAO_1 asd1;
    private CVAvailabilitiesDAO cvaDAO;
    private int strainID;
    private String requestsSuccessView;

    public CvAvailController()
    {
        am = new ArchiveManager();
        returnedOut = new HashMap();
        asd = new AvailabilitiesStrainsDAO();
        asd1 = new AvailabilitiesStrainsDAO_1();
        cvaDAO = new CVAvailabilitiesDAO();
    }

    protected Object formBackingObject(HttpServletRequest request)
    {
        javax.servlet.http.HttpSession session = request.getSession(true);
        if(request.getParameter("EditArch") != null)
        {
            strainID = Integer.parseInt(request.getParameter("EditArch"));
            List as = am.getStrainAvail(strainID);
            returnedOut.put("size", Integer.valueOf(as.size()));
            int i = 0;
            for(Iterator it = as.listIterator(); it.hasNext();)
            {
                System.out.println(as.size());
                Object o[] = (Object[])(Object[])it.next();
                String availid = o[1].toString();
                int availID = Integer.parseInt(availid);
                cvaDAO = am.getCVAvail(availID);
                returnedOut.put((new StringBuilder()).append("cvaDAO").append(i).toString(), cvaDAO);
                i++;
            }

        }
        return returnedOut;
    }

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
        throws ServletException, Exception
    {
        System.out.println("ON SUBMIT IS ACTIVE NOW ######");
        strainID = Integer.parseInt(request.getParameter("EditArch"));
        if(request.getParameter("EditArch") != null)
            strainID = Integer.parseInt(request.getParameter("EditArch"));
        if(request.getParameter("updateAvail") != null)
        {
            am.delete(strainID);
            System.out.println("This is an update now");
            int availability_C;
            if(request.getParameter("availability_C") == null)
                availability_C = 0;
            else
                availability_C = Integer.parseInt(request.getParameter("availability_C"));
            int availability_E;
            if(request.getParameter("availability_E") == null)
                availability_E = 0;
            else
                availability_E = Integer.parseInt(request.getParameter("availability_E"));
            int availability_O;
            if(request.getParameter("availability_O") == null)
                availability_O = 0;
            else
                availability_O = Integer.parseInt(request.getParameter("availability_O"));
            int availability_S;
            if(request.getParameter("availability_S") == null)
                availability_S = 0;
            else
                availability_S = Integer.parseInt(request.getParameter("availability_S"));
            int availability_L;
            if(request.getParameter("availability_L") == null)
                availability_L = 0;
            else
                availability_L = Integer.parseInt(request.getParameter("availability_L"));
            int availability_R;
            if(request.getParameter("availability_R") == null)
            {
                availability_R = 0;
            } else
            {
                availability_R = Integer.parseInt(request.getParameter("availability_R"));
                System.out.println((new StringBuilder()).append("Availability_R equals #### ").append(availability_R).toString());
            }
            int distro_C;
            if(request.getParameter("distro_C") == null)
                distro_C = 0;
            else
                distro_C = Integer.parseInt(request.getParameter("distro_C"));
            int distro_E;
            if(request.getParameter("distro_E") == null)
                distro_E = 0;
            else
                distro_E = Integer.parseInt(request.getParameter("distro_E"));
            int distro_O;
            if(request.getParameter("distro_O") == null)
                distro_O = 0;
            else
                distro_O = Integer.parseInt(request.getParameter("distro_O"));
            int distro_S;
            if(request.getParameter("distro_S") == null)
                distro_S = 0;
            else
                distro_S = Integer.parseInt(request.getParameter("distro_S"));
            int distro_L;
            if(request.getParameter("distro_L") == null)
                distro_L = 0;
            else
                distro_L = Integer.parseInt(request.getParameter("distro_L"));
            int distro_R;
            if(request.getParameter("distro_R") == null)
                distro_R = 0;
            else
                distro_R = Integer.parseInt(request.getParameter("distro_R"));
            if(availability_C == 1 && distro_C == 1)
            {
                int availID = am.getCVAvailID(distro_C, availability_C, "C");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if(availability_C == 1 && distro_C == 0)
            {
                int availID = am.getCVAvailID(distro_C, availability_C, "C");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if(availability_C == 0 && distro_C == 1)
            {
                int availID = am.getCVAvailID(distro_C, availability_C, "C");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if(availability_E == 1 && distro_E == 1)
            {
                int availID = am.getCVAvailID(distro_E, availability_E, "E");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if(availability_E == 1 && distro_E == 0)
            {
                int availID = am.getCVAvailID(distro_E, availability_E, "E");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if(availability_E == 0 && distro_E == 1)
            {
                int availID = am.getCVAvailID(distro_E, availability_E, "E");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if(availability_O == 1 && distro_O == 1)
            {
                int availID = am.getCVAvailID(distro_O, availability_O, "O");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if(availability_O == 1 && distro_O == 0)
            {
                int availID = am.getCVAvailID(distro_O, availability_O, "O");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if(availability_O == 0 && distro_O == 1)
            {
                int availID = am.getCVAvailID(distro_O, availability_O, "O");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if(availability_S == 1 && distro_S == 1)
            {
                int availID = am.getCVAvailID(distro_S, availability_S, "S");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if(availability_S == 1 && distro_S == 0)
            {
                int availID = am.getCVAvailID(distro_S, availability_S, "S");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if(availability_S == 0 && distro_S == 1)
            {
                int availID = am.getCVAvailID(distro_S, availability_S, "S");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if(availability_L == 1 && distro_L == 1)
            {
                int availID = am.getCVAvailID(distro_L, availability_L, "L");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if(availability_L == 1 && distro_L == 0)
            {
                int availID = am.getCVAvailID(distro_L, availability_L, "L");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if(availability_L == 0 && distro_L == 1)
            {
                int availID = am.getCVAvailID(distro_L, availability_L, "L");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if(availability_R == 1 && distro_R == 1)
            {
                int availID = am.getCVAvailID(distro_R, availability_R, "R");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if(availability_R == 1 && distro_R == 0)
            {
                int availID = am.getCVAvailID(distro_R, availability_R, "R");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if(availability_R == 0 && distro_R == 1)
            {
                int availID = am.getCVAvailID(distro_R, availability_R, "R");
                asd.setAvail_id(availID);
                save(strainID);
            }
        }
        return new ModelAndView((new StringBuilder()).append(getSuccessView()).append("?EditArch=").append(strainID).toString(), "returnedOut", returnedOut);
    }

    public boolean save(int strainID)
    {
        List strainAvail = am.getStrainAvail(strainID);
        List availabilityIDs = new ArrayList();
        String availid;
        for(Iterator it = strainAvail.listIterator(); it.hasNext(); availabilityIDs.add(availid))
        {
            Object ob[] = (Object[])(Object[])it.next();
            availid = ob[1].toString();
        }

        int index = availabilityIDs.indexOf((new StringBuilder()).append("").append(asd.getAvail_id()).append("").toString());
        if(index == -1)
        {
            System.out.println(index);
            asd1.setStr_id_str(strainID);
            asd1.setAvail_id(asd.getAvail_id());
            am.save(asd1);
            System.out.println((new StringBuilder()).append("returning true ").append(asd.getAvail_id()).toString());
        } else
        {
            System.out.println((new StringBuilder()).append("returning false, index value is ").append(index).append(" avail-d== ").append(asd.getAvail_id()).toString());
        }
        return true;
    }

    public String getListView()
    {
        return listView;
    }

    public void setListView(String listView)
    {
        this.listView = listView;
    }

    public String getRequestsSuccessView()
    {
        return requestsSuccessView;
    }

    public void setRequestsSuccessView(String requestsSuccessView)
    {
        this.requestsSuccessView = requestsSuccessView;
    }


}




/*package org.emmanet.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.emmanet.model.ArchiveManager;
import org.emmanet.model.AvailabilitiesStrainsDAO;
//import org.emmanet.model.AvailabilitiesStrainsDAO_1;
import org.emmanet.model.CVAvailabilitiesDAO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.validation.BindException;

public class CvAvailController extends SimpleFormController {

    private ArchiveManager am = new ArchiveManager();
    public static final String MAP_KEY = "returnedOut";
    private Map returnedOut = new HashMap();
    private String listView;
    private AvailabilitiesStrainsDAO asd = new AvailabilitiesStrainsDAO();
    private AvailabilitiesStrainsDAO asd1 = new AvailabilitiesStrainsDAO();
    private CVAvailabilitiesDAO cvaDAO = new CVAvailabilitiesDAO();
    private int strainID;
    private String requestsSuccessView;

    @Override
    protected Object formBackingObject(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
        if (request.getParameter("EditArch") != null) {
            strainID = Integer.parseInt(request.getParameter("EditArch"));

            List as = am.getStrainAvail(strainID);

            returnedOut.put("size", as.size());
            int i = 0;
            for (Iterator it = as.listIterator(); it.hasNext();) {

                System.out.println(as.size());
                Object[] o = (Object[]) it.next();
                String availid = o[1].toString();

                int availID = Integer.parseInt(availid);
                cvaDAO = (CVAvailabilitiesDAO) am.getCVAvail(availID);

                returnedOut.put("cvaDAO" + i, cvaDAO);
                i++;
            }
        }

        return returnedOut;
    }

    // SAVE
    @Override
    protected ModelAndView onSubmit(
            HttpServletRequest request,
            HttpServletResponse response,
            Object command,
            BindException errors)
            throws ServletException, Exception {
        // asd = (AvailabilitiesStrainsDAO) command;
        System.out.println("ON SUBMIT IS ACTIVE NOW ######");
        strainID = Integer.parseInt(request.getParameter("EditArch"));
        if (request.getParameter("EditArch") != null) {
            strainID = Integer.parseInt(request.getParameter("EditArch"));
        }

        if (request.getParameter("updateAvail") != null) {
            am.delete(strainID);

            System.out.println("This is an update now");
            //update code
            int availability_C;
            int availability_E;
            int availability_O;
            int availability_S;
            int availability_L;
            int availability_R;

            int distro_C;
            int distro_E;
            int distro_O;
            int distro_S;
            int distro_L;
            int distro_R;

            if (request.getParameter("availability_C") == null) {
                availability_C = 0;
            } else {
                availability_C = Integer.parseInt(request.getParameter("availability_C"));
            }
            if (request.getParameter("availability_E") == null) {
                availability_E = 0;
            } else {
                availability_E = Integer.parseInt(request.getParameter("availability_E"));
            }
            if (request.getParameter("availability_O") == null) {
                availability_O = 0;
            } else {
                availability_O = Integer.parseInt(request.getParameter("availability_O"));
            }
            if (request.getParameter("availability_S") == null) {
                availability_S = 0;
            } else {
                availability_S = Integer.parseInt(request.getParameter("availability_S"));
            }
            if (request.getParameter("availability_L") == null) {
                availability_L = 0;
            } else {
                availability_L = Integer.parseInt(request.getParameter("availability_L"));
            }
            if (request.getParameter("availability_R") == null) {
                availability_R = 0;
            } else {
                availability_R = Integer.parseInt(request.getParameter("availability_R"));
                System.out.println("Availability_R equals #### " + availability_R);
            }

            if (request.getParameter("distro_C") == null) {
                distro_C = 0;
            } else {
                distro_C = Integer.parseInt(request.getParameter("distro_C"));
            }
            if (request.getParameter("distro_E") == null) {
                distro_E = 0;
            } else {
                distro_E = Integer.parseInt(request.getParameter("distro_E"));
            }
            if (request.getParameter("distro_O") == null) {
                distro_O = 0;
            } else {
                distro_O = Integer.parseInt(request.getParameter("distro_O"));
            }
            if (request.getParameter("distro_S") == null) {
                distro_S = 0;
            } else {
                distro_S = Integer.parseInt(request.getParameter("distro_S"));
            }
            if (request.getParameter("distro_L") == null) {
                distro_L = 0;
            } else {
                distro_L = Integer.parseInt(request.getParameter("distro_L"));
            }
            if (request.getParameter("distro_R") == null) {
                distro_R = 0;
            } else {
                distro_R = Integer.parseInt(request.getParameter("distro_R"));
            }

            int availID;

            if (availability_C == 1 && distro_C == 1) {
                //both 1 therefore
                availID = am.getCVAvailID(distro_C, availability_C, "C");
                asd.setAvail_id(availID);
                //this.save();
                save(strainID);
            }
            if (availability_C == 1 && distro_C == 0) {

                availID = am.getCVAvailID(distro_C, availability_C, "C");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if (availability_C == 0 && distro_C == 1) {

                availID = am.getCVAvailID(distro_C, availability_C, "C");
                asd.setAvail_id(availID);
                save(strainID);
            }

            if (availability_E == 1 && distro_E == 1) {
                //both 1 therefore
                availID = am.getCVAvailID(distro_E, availability_E, "E");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if (availability_E == 1 && distro_E == 0) {
                availID = am.getCVAvailID(distro_E, availability_E, "E");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if (availability_E == 0 && distro_E == 1) {

                availID = am.getCVAvailID(distro_E, availability_E, "E");
                asd.setAvail_id(availID);
                save(strainID);
            }

            if (availability_O == 1 && distro_O == 1) {
                //both 1 therefore
                availID = am.getCVAvailID(distro_O, availability_O, "O");
                asd.setAvail_id(availID);
                 save(strainID);
            }
            if (availability_O == 1 && distro_O == 0) {

                availID = am.getCVAvailID(distro_O, availability_O, "O");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if (availability_O == 0 && distro_O == 1) {

                availID = am.getCVAvailID(distro_O, availability_O, "O");
                asd.setAvail_id(availID);
                save(strainID);
            }


            if (availability_S == 1 && distro_S == 1) {
                //both 1 therefore
                availID = am.getCVAvailID(distro_S, availability_S, "S");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if (availability_S == 1 && distro_S == 0) {

                availID = am.getCVAvailID(distro_S, availability_S, "S");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if (availability_S == 0 && distro_S == 1) {

                availID = am.getCVAvailID(distro_S, availability_S, "S");
                asd.setAvail_id(availID);
                save(strainID);
            }

            if (availability_L == 1 && distro_L == 1) {
                //both 1 therefore
                availID = am.getCVAvailID(distro_L, availability_L, "L");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if (availability_L == 1 && distro_L == 0) {

                availID = am.getCVAvailID(distro_L, availability_L, "L");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if (availability_L == 0 && distro_L == 1) {

                availID = am.getCVAvailID(distro_L, availability_L, "L");
                asd.setAvail_id(availID);
                save(strainID);
            }

            if (availability_R == 1 && distro_R == 1) {
                //both 1 therefore
                availID = am.getCVAvailID(distro_R, availability_R, "R");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if (availability_R == 1 && distro_R == 0) {

                availID = am.getCVAvailID(distro_R, availability_R, "R");
                asd.setAvail_id(availID);
                save(strainID);
            }
            if (availability_R == 0 && distro_R == 1) {

                availID = am.getCVAvailID(distro_R, availability_R, "R");
                asd.setAvail_id(availID);
                save(strainID);

            }
        }
        return new ModelAndView(getSuccessView() + "?EditArch=" + strainID, MAP_KEY, returnedOut);
    }

    public boolean save(int strainID) {

        //check if combination of str_id_str and availability id exists first
        List strainAvail = am.getStrainAvail(strainID);
        List availabilityIDs = new ArrayList();
        for (Iterator it = strainAvail.listIterator(); it.hasNext();) {

            Object[] ob = (Object[]) it.next();
            String availid = ob[1].toString();
            availabilityIDs.add(availid);

        }

        int index = availabilityIDs.indexOf("" + asd.getAvail_id() + "");
        if (index == -1) {
            //can't find it in the list so must be new from web form
            System.out.println(index);
            // AvailabilitiesStrainsDAO asd2 = new AvailabilitiesStrainsDAO();
            asd1.setStr_id_str(strainID);
            asd1.setAvail_id(asd.getAvail_id());

            //asd.setCvavailDAO(cvaDAO);
            //  asd2.setStrainsDAO(sDAO);
            am.save(asd1);
            //am.save(strainID, asd.getAvail_id());
            System.out.println("returning true " + asd.getAvail_id());
        } else {
            System.out.println("returning false, index value is " + index + " avail-d== " + asd.getAvail_id());
        }


        return true;
    }

    public String getListView() {
        return listView;
    }

    public void setListView(String listView) {
        this.listView = listView;
    }

    public String getRequestsSuccessView() {
        return requestsSuccessView;
    }

    public void setRequestsSuccessView(String requestsSuccessView) {
        this.requestsSuccessView = requestsSuccessView;
    }
}*/

