/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.controllers;

import java.io.File;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.emmanet.model.ArchiveDAO;
import org.emmanet.model.ArchiveManager;
import org.emmanet.model.BibliosManager;
import org.emmanet.model.LabsDAO;
import org.emmanet.model.PeopleDAO;
import org.emmanet.model.PeopleManager;
import org.emmanet.model.StrainsDAO;
import org.emmanet.model.StrainsManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.validation.BindException;

/**
 *
 * @author phil
 */
public class strainsUpdateInterfaceFormController extends SimpleFormController {

    private StrainsManager sm = new StrainsManager();
    private ArchiveManager am = new ArchiveManager();
    private PeopleManager pm = new PeopleManager();
    private BibliosManager bm = new BibliosManager();
    private Integer strainID;
    private String mtaPath;
    private String baseURL;
    private String recentBaseURL;
    private String serverPDFLocation;
    private String serverPDFLocationTail;
    private String serverPDFRecentLocation;
    private String thisisthebase;
    private String tmpDir;

    @Override
    protected Object formBackingObject(HttpServletRequest request) {

      //  if (request.getParameter("EditStrain") != null) {
            strainID = Integer.parseInt(request.getParameter("EditStrain"));

     /*       /// START OF SUBMISSION FILE RETRIEVAL
            int archID = sm.getArchID(strainID);

            ArchiveDAO ad = am.getReqByID(archID);
            String subFile = "sub_";
            String date = ad.getSubmitted();

            if (date != null) {
                String strippedDate = date.replace("-", "");
                strippedDate = strippedDate.replace(":", "");
                strippedDate = strippedDate.replace(".", "");
                strippedDate = strippedDate.replace(" ", "");

                //get year to determine search directory
                String pdfYearDir = "PDF-";
                pdfYearDir = pdfYearDir + strippedDate.substring(0, 4);
                //Take out leading 2 digits of year
                strippedDate = strippedDate.substring(2, strippedDate.length() - 1);
                //server location of pdf to search for
                String locatePDF = getServerPDFLocation() + pdfYearDir + getServerPDFLocationTail();
                String pdfsources[] = {locatePDF, getServerPDFRecentLocation()};
                Boolean fileFound = false;
                String pdfUrl = "";
                subFile = subFile + strippedDate;
                //ok lets search for...
                for (int i = 0; i < pdfsources.length; i++) {
                    File dir = new File(pdfsources[i]);
                    locatePDF = pdfsources[i];
                    System.out.println("SEARCHING DIRECTORY " + locatePDF + " FOR FILE " + subFile);
                    if (i == 1) {
                        System.out.println("Setting base url to (i=1 then) :: " + getRecentBaseURL());
                        thisisthebase = getRecentBaseURL();
                    } else if (i == 0) {
                        System.out.println("Setting base url to (i=0 then):: " + getRecentBaseURL() + pdfYearDir);
                        thisisthebase = getBaseURL() + pdfYearDir + this.getServerPDFLocationTail();
                    }
                    String[] children = dir.list();
                    if (children == null) {
                        // Either dir does not exist or is not a directory
                        System.out.println("It appears that the directory " + locatePDF +
                                " doesn't exist.");
                    } else {
                        System.out.println("Directory exists::" + locatePDF);
                        for (int ii = 0; ii < children.length; ii++) {
                            // Get filename of file or directory
                            String filename = children[ii];
                            System.out.println(filename + " located");
                            System.out.println(subFile);
                            if (filename.startsWith(subFile)) {
                                subFile = filename;
                                fileFound = true;
                            } else {
                                System.out.println("No file with this name " + subFile + " here");
                            }

                            if (fileFound) {
                                System.out.println("OK file found let's stop the search");
                                break;//stop searching now
                            }
                        }
                    }
                    if (fileFound) {
                        break;
                    }
                }//end of pdfsources loop

                if (fileFound) {
                    //We have a pdf file located
                    pdfUrl = null;
                    // pdfUrl = getBaseURL() + subFile;
                    pdfUrl = thisisthebase + subFile;
                    //System.out.println("THIS IS THE URL TO USE@@@@@@@@@@@@@@" + pdfUrl);
                    am.setPdfURL(pdfUrl);
                    //System.out.println(pdfFile);
                    ad.setPdfURL(pdfUrl);

                } else {
                    System.out.println("No file can be found with the name " + subFile);
                }
            }
            //TODO YUK THIS IS ORRIBLE ANOTHER FUGLEY HACK ADD URL TO SESSION COOKIE ENABLING RETRIEVAL IN JSP PAGE
            HttpSession session = request.getSession(true);

            session.setAttribute("pdfUrl", ad.getPdfURL());

        /// END OF SUBMISSION FILE RETRIEVAL*/
        //}
        // SET IN SESSION THE MTAPATH
        HttpSession session = request.getSession(true);
        session.setAttribute("mtaPath", this.getMtaPath());
        //contact person display in http header

        StrainsDAO sd = sm.getStrainByID(strainID);
        String contactID = sd.getPer_id_per_contact();
        PeopleDAO pd = pm.getPerson(contactID);
        LabsDAO ld = pd.getLabsDAO();
     
       

        session.setAttribute("con_id", contactID);
        session.setAttribute("con_title", pd.getTitle());
        session.setAttribute("con_firstname", pd.getFirstname());
        session.setAttribute("con_surname", pd.getSurname());
        session.setAttribute("con_email", pd.getEmail());
        session.setAttribute("con_phone", pd.getPhone());
        session.setAttribute("con_fax", pd.getFax());
        session.setAttribute("con_institution", ld.getName());
        session.setAttribute("con_department", ld.getDept());
        session.setAttribute("con_address1", ld.getAddr_line_1());
        session.setAttribute("con_address2", ld.getAddr_line_2());
        session.setAttribute("con_town", ld.getTown());
        session.setAttribute("con_province", ld.getProvince());
        session.setAttribute("con_postcode", ld.getPostcode());
        session.setAttribute("con_country", ld.getCountry());

        // Create file containing background names from list
        // Needed to populate droplist in strainsUpdateInterface.emma

        /*  List backgroundNames = sm.getBackgrounds();
        
        try {
        BufferedWriter out = new BufferedWriter(new FileWriter(this.getTmpDir() + "bgNamesList.emma", false));
        for (Iterator it = backgroundNames.listIterator(); it.hasNext();) {
        Object[] o =(Object[]) it.next();
        out.write(o[0].toString()  + "||" );
        out.write(o[1].toString());
        out.newLine();
        }
        
        out.close();
        } catch (IOException e) {
        }
         */

        //NOW SET NUMBER OF BIBLIOGRAPHIC REFS
        session.setAttribute("bibCount", bm.BibliosStrainCount(strainID));
        //NOW SET NUMBER OF RTOOLS REFS
        session.setAttribute("rtCount", sm.getRToolsCount(strainID));


        // SET IN SESSION THE MTAPATH
        // HttpSession session = request.getSession(true);
        //session.setAttribute("mtaPath", this.getMtaPath());
        return sd;//sm.getStrainByID(strainID);
    //  return new ModelAndView(getSuccessView() + "?EditArch=" + strainID, MAP_KEY,sd);
    }
    // SAVE
    @Override
    protected ModelAndView onSubmit(
            HttpServletRequest request,
            HttpServletResponse response,
            Object command,
            BindException errors)
            throws ServletException, Exception {
        StrainsDAO sDAO = (StrainsDAO) command;
        //BackgroundDAO bDAO=(BackgroundDAO) sDAO.getBackgroundDAO();
//line below prevents java.sql.SQLException: Incorrect integer value: '' for column 'mgi_ref' at row 1
//errors caused by an empty string value
        if (sDAO.getMgi_ref().equals("")) {
            sDAO.setMgi_ref(null);
        }
           System.out.println("before Residues char genotype is " +  sDAO.getResiduesDAO().getChar_genotyping());
          // sDAO.setBackgroundDAO(bDAO);
        sm.save(sDAO);
        System.out.println("after Residues char genotype is " +  sDAO.getResiduesDAO().getChar_genotyping());
        System.out.println("saved");
        request.getSession().setAttribute(
                "message",
                getMessageSourceAccessor().getMessage("Message",
                "Your update submitted successfully"));

        return new ModelAndView(getSuccessView() + "?EditStrain=" + strainID);
   // ModelAndView mav = showForm(request, response, errors);
    // return mav;//

    }

    public String getMtaPath() {
        return mtaPath;
    }

    public void setMtaPath(String mtaPath) {
        this.mtaPath = mtaPath;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getRecentBaseURL() {
        return recentBaseURL;
    }

    public void setRecentBaseURL(String recentBaseURL) {
        this.recentBaseURL = recentBaseURL;
    }

    public String getServerPDFLocation() {
        return serverPDFLocation;
    }

    public void setServerPDFLocation(String serverPDFLocation) {
        this.serverPDFLocation = serverPDFLocation;
    }

    public String getServerPDFLocationTail() {
        return serverPDFLocationTail;
    }

    public void setServerPDFLocationTail(String serverPDFLocationTail) {
        this.serverPDFLocationTail = serverPDFLocationTail;
    }

    public String getServerPDFRecentLocation() {
        return serverPDFRecentLocation;
    }

    public void setServerPDFRecentLocation(String serverPDFRecentLocation) {
        this.serverPDFRecentLocation = serverPDFRecentLocation;
    }

    public String getTmpDir() {
        return tmpDir;
    }

    public void setTmpDir(String tmpDir) {
        this.tmpDir = tmpDir;
    }
}
