package org.emmanet.controllers;

/**
 *
 * @author phil
 */
import java.io.UnsupportedEncodingException;
import java.net.BindException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.emmanet.jobs.WebRequests;
import org.emmanet.model.BackgroundManager;
import org.emmanet.model.CVRtoolsDAO;
import org.emmanet.model.LabsDAO;
import org.emmanet.model.PeopleDAO;
import org.emmanet.model.PeopleManager;
import org.emmanet.model.StrainsDAO;
import org.emmanet.model.StrainsManager;
import org.emmanet.model.SubmissionsDAO;
import org.emmanet.model.SubmissionsManager;
import org.emmanet.util.Encrypter;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;
import org.json.simple.*;

public class SubmissionFormController extends AbstractWizardFormController {

    private List cvDAO;
    private HttpSession session;
    private JSONObject obj;
    private List JSONobjects = new LinkedList();
    private WebRequests wr;

    public SubmissionFormController() {
        setCommandName("command");
        setAllowDirtyBack(true);
        setAllowDirtyForward(true);
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) {

        StrainsDAO sd = new StrainsDAO();
        SubmissionsDAO sda = new SubmissionsDAO();//#################
        WebRequests wr = new WebRequests();

        //////////////////////////////////////////////
        BackgroundManager bm = new BackgroundManager();
        String getprev = "";
        ////////////////////////////////////////////

        session = request.getSession(true);
//total steps in wizard for use in view
        int iPageCount = getPageCount() - 1;
        session.setAttribute("totalStepCount", "" + iPageCount);

        sda.setCvDAO(wr.isoCountries());
        sd.setPeopleDAO(new PeopleDAO());
        sd.getPeopleDAO().setLabsDAO(new LabsDAO());

        if (request.getParameter("getprev") != null && !request.getParameter("getprev").isEmpty()) {
            Encrypter encrypter = new Encrypter();
            try {
                // Encrypt
                String encrypted = encrypter.encrypt(request.getParameter("getprev"));
                // System.out.println("ENCRYPTEDSTRING==" + encrypted);

                // Decrypt
                // String decrypted = encrypter.decrypt(encrypted);

                //System.out.println("DECRYPTEDSTRING==" + decrypted);
            } catch (Exception e) {
            }


            sda = new SubmissionsDAO();

            SubmissionsManager sm = new SubmissionsManager();

            /*/decode
            try {
            getprev = java.net.URLDecoder.decode(request.getParameter("getprev"), "UTF-8");
            } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RequestFormController.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            System.out.println("decoded==" + request.getParameter("getprev") + "--" + getprev);
            getprev = encrypter.decrypt(request.getParameter("getprev"));
            int idDecrypt = Integer.parseInt(getprev);
            System.out.println("decrypted id_str==" + idDecrypt);

            sda = sm.getSubByID(idDecrypt);///decrypt here

            sda.setCvDAO(wr.isoCountries());

        }
        session.setAttribute("backgroundsDAO", bm.getBackgrounds());
        sda.setBgDAO(bm.getBackgrounds());
        return sda;
    }

    @Override
    protected Map referenceData(HttpServletRequest request,
            Object command,
            Errors errors,
            int page)
            throws Exception {
        //StrainsDAO sd = (StrainsDAO) command;
        SubmissionsDAO sda = (SubmissionsDAO) command;
        SubmissionsManager sm = new SubmissionsManager();
        Map refData = new HashMap();
        int pageCount = page;
        session.setAttribute("pageCount", "" + pageCount);
        session.setAttribute("userdaos", null);
        PeopleDAO pd;
        PeopleManager pm;
        List people;
        List peopleDAOs;

        switch (page) {
            case 0: //if page 1 , do summat
                sda.setStep("0");
//sm.save(sda);
                break;
            case 1: //if page 2 do summat
//sm.save(sda);
                sda.setStep("1");
                break;
            case 2:

                if (request.getParameter("getprev") != null && !request.getParameter("getprev").isEmpty()) {
                    System.out.println("getprev==" + request.getParameter("getprev"));
                    //sda = sm.getSubByID(Integer.parseInt(request.getParameter("getprev")));
                    //command.equals(sda);
                    // this.formBackingObject(request);
                }
                if (sda.getSubmitter_email()/*.getPeopleDAO().getEmail()*/ != null) {
                    WebRequests wr = new WebRequests();
                    SubmissionsDAO prevSub = new SubmissionsDAO();
                    List submitterDAO = new LinkedList();
                    System.out.println(" email for prevsub = " + sda.getSubmitter_email().toString());
                    /*prevSub*/ submitterDAO = sm.getSubsByEmail(sda.getSubmitter_email().toString());
                    if (/*prevSub*/submitterDAO != null) {
                        for (Iterator it = submitterDAO.listIterator(); it.hasNext();) {
                            prevSub = (SubmissionsDAO) it.next();
                            System.out.println("TIMESTAMP :: " + prevSub.getTimestamp());

                            Encrypter encrypter = new Encrypter();

                            // Encrypt
                            String encrypted = encrypter.encrypt(prevSub.getId_sub());


                            try {
                                encrypted = java.net.URLEncoder.encode(encrypted, "UTF-8");
                            } catch (UnsupportedEncodingException ex) {
                                Logger.getLogger(RequestFormController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            prevSub.setEncryptedId_sub(encrypted);
                            prevSub.setCvDAO(wr.isoCountries());
                            request.setAttribute("previousSub", prevSub);
                        }
                    }
                    //request.setAttribute("previousSub", /*prevSub*/);
                    //LOOK TO PULL USER/LAB DATA TO POPULATE STRAINS
                    pd = new PeopleDAO();
                    pm = new PeopleManager();
                    people = pm.getPeopleByEMail(sda.getSubmitter_email()/*sd.getPeopleDAO().getEmail()*/);
                    peopleDAOs = new LinkedList();
                    if (people.isEmpty()) {
                        //OK no email address registered most likely a new user so let them submit their details
                    } else {
                        //OK email exists and likely user so present a view of user'lab details to choose from
                        //NEED TO PULL LAST SUBMISSION FROM DATABASE SUBMISSIONS TABLE USING E-MAIL SUBMITTER ADDRESS
                        //RETURN TO USER TO CHOOSE WHETHER TO RE-USE DATA.
                        // sda=submissions manager.getlast submission.

                        System.out.println("PERSON LIST SIZE " + people.size());
                        JSONobjects = new LinkedList();
                        for (Iterator it = people.listIterator(); it.hasNext();) {
                            pd = (PeopleDAO) it.next();
                            System.out.println("A U T H O R I T Y  : :  " + pd.getLabsDAO().getAuthority());
                            peopleDAOs.add(pd);
                        }
                        /*session*/ request.setAttribute("userdaos", peopleDAOs);

                        // refData.put("user", peopleDAOs);
                    }
                }

                System.out.println("STEP==" + sda.getStep());
                sda.setStep("2");
                //sm.save(sda);
                break;
            case 3:


                if (sda.getProducer_email()/*.getPeopleDAO().getEmail()*/ != null) {
                    //LOOK TO PULL USER/LAB DATA TO POPULATE STRAINS
                    pd = new PeopleDAO();
                    pm = new PeopleManager();
                    people = pm.getPeopleByEMail(sda.getProducer_email()/*.getPeopleDAO().getEmail()*/);
                    peopleDAOs = new LinkedList();
                    if (people.isEmpty()) {
                        //OK no email address registered most likely a new user so let them submit their details
                    } else {
                        //OK email exists and likely user so present a view of user'lab details to choose from
                        System.out.println("PERSON LIST SIZE " + people.size());
                        JSONobjects = new LinkedList();
                        for (Iterator it = people.listIterator(); it.hasNext();) {
                            pd = (PeopleDAO) it.next();
                            peopleDAOs.add(pd);
                        }
                        session.setAttribute("pidaos", peopleDAOs);
                        //refData.put("pi", peopleDAOs);
                    }
                }
                sda.setStep("3");
                sm.save(sda);
                break;

            case 4:


                if (sda.getShipper_email()/*.getPeopleDAO().getEmail()*/ != null) {
                    //LOOK TO PULL USER/LAB DATA TO POPULATE STRAINS
                    pd = new PeopleDAO();
                    pm = new PeopleManager();
                    people = pm.getPeopleByEMail(sda.getShipper_email()/*.getPeopleDAO().getEmail()*/);
                    peopleDAOs = new LinkedList();
                    if (people.isEmpty()) {
                        //OK no email address registered most likely a new user so let them submit their details
                    } else {
                        //OK email exists and likely user so present a view of user'lab details to choose from
                        System.out.println("PERSON LIST SIZE " + people.size());
                        JSONobjects = new LinkedList();
                        for (Iterator it = people.listIterator(); it.hasNext();) {
                            pd = (PeopleDAO) it.next();
                            peopleDAOs.add(pd);
                        }
                        session.setAttribute("pidaos", peopleDAOs);
                        //refData.put("pi", peopleDAOs);
                    }
                }
                sda.setStep("4");
                sm.save(sda);
                break;


            case 5:

                sda.setStep("5");
                sm.save(sda);
                break;

            case 6:
                sda.setStep("6");
                sm.save(sda);
                break;
            case 7:
                //sda.getBiblioRefs();

                sda.setStep("7");
                sm.save(sda);
                break;

            case 8:
                sda.setStep("8");
                sm.save(sda);
                break;

            case 9:

                sda.setStep("9");
                sm.save(sda);
                break;

            case 10:

                CVRtoolsDAO rt = new CVRtoolsDAO();
                StrainsManager smg = new StrainsManager();
                List rtl = new LinkedList();
                List CVRtoolsDAO = new LinkedList();
                rtl = smg.getRTools();//.getRToolsDAO();

                for (Iterator it = rtl.listIterator(); it.hasNext();) {
                    rt = (CVRtoolsDAO) it.next();
                    if (!rt.getDescription().isEmpty()) {
                        CVRtoolsDAO.add(rt);
                    }
                }


                session.setAttribute("CVRToolsDAO", CVRtoolsDAO);
                DateFormat dateFormat = new SimpleDateFormat("yyyy");

                Date date = new Date();
                session.setAttribute("startYear", dateFormat.format(date));
                sda.setStep("10");
                sm.save(sda);
                break;
            case 11:
                sda.setStep("11");
                break;

        }

        //  }
        return refData;
    }

    @Override
    protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response,
            Object command, org.springframework.validation.BindException be) throws Exception {

        System.out.println("COMMAND==" + command.toString());
        SubmissionsDAO sd = (SubmissionsDAO) command;

        if (this.isFinishRequest(request)) {
            //SAVE IT
//DEAL WITH INTEGER FIELDS THAT MAY NOT HAVE BEEN SET HAVING '' VALUES
            SubmissionsManager sm = new SubmissionsManager();
            System.out.println("AT THE POINT OF SAVING SUBMISSION");
            sm.save(sd);
        }

        return new ModelAndView("/success");

    }

    protected ModelAndView processCancel(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception {

        //where is the cancel page?
        return new ModelAndView("/publicSubmission/submissionForm");
    }

    @Override
    protected void validatePage(Object command, Errors errors, int page) {

        SubmissionFormValidator validator = (SubmissionFormValidator) getValidator();
        //StrainsDAO sd = (StrainsDAO) command;
        SubmissionsDAO sd = (SubmissionsDAO) command;

        //page is 0-indexed
        switch (page) {
            case 0: //if page 1 , go validate with validatePage1Form
                //validator.validatePage1Form(command, errors);
                break;
            case 1: //if page 2 , go validate with validatePage2Form
                //validator.validatePage2Form(command, errors);
                validator.validateSubmissionForm1(sd, errors);
                break;
            case 2:
                //validator.validateSubmissionForm2(sd, errors);
                break;
            case 3:
                //validator.validateSubmissionForm3(sd, errors);
                break;
            case 4:
                //validator.validateSubmissionForm4(sd, errors);
                break;
            case 5:
                //validator.validateSubmissionForm5(sd, errors);
                break;
            case 6:
                //validator.validateSubmissionForm6(sd, errors);
                break;
            case 7:
                //validator.validateSubmissionForm7(sd, errors);
                break;
            case 8:
                //validator.validateSubmissionForm8(sd, errors);
                break;
            case 9:
                validator.validateSubmissionForm9(sd, errors);
                break;
            case 10:
                //validator.validateSubmissionForm10(sd, errors);
                break;
            case 11:
                //validator.validateSubmissionForm11(sd, errors);
                break;
        }
    }

    public List getCvDAO() {
        return cvDAO;
    }

    public void setCvDAO(List cvDAO) {
        this.cvDAO = cvDAO;
    }

    public SubmissionsDAO cleanInts(SubmissionsDAO sd) {


        return null;
    }

    public void PeopleData(HttpServletRequest request, String email, boolean returnJSON) {
        session = request.getSession(true);
        PeopleDAO pd = new PeopleDAO();
        PeopleManager pm = new PeopleManager();
        List people = pm.getPeopleByEMail(email);
        List peopleDAOs = new LinkedList();
        obj = new JSONObject();
        if (people.isEmpty()) {
            //Do nothing
        } else {
            System.out.println("PEOPLEDATA FUNCTION CALLED AND THE PERSON LIST SIZE IS " + people.size());
            JSONobjects = new LinkedList();
            for (Iterator it = people.listIterator(); it.hasNext();) {
                pd = (PeopleDAO) it.next();
                peopleDAOs.add(pd);
                if (returnJSON) {
                    //return a JSON String

                    obj.put("id", pd.getId_per());
                    obj.put("title", pd.getTitle());
                    obj.put("firstname", pd.getFirstname());
                    obj.put("surname", pd.getSurname());
                    obj.put("email", pd.getEmail());
                    obj.put("phone", pd.getPhone());
                    obj.put("fax", pd.getFax());
                    obj.put("ilar", pd.getIlar_code());
                    //laboratory details
                    obj.put("institution", pd.getLabsDAO().getName());
                    obj.put("dept", pd.getLabsDAO().getName());
                    obj.put("address1", pd.getLabsDAO().getAddr_line_1());
                    obj.put("address2", pd.getLabsDAO().getAddr_line_2());
                    obj.put("town", pd.getLabsDAO().getTown());
                    obj.put("county", pd.getLabsDAO().getProvince());
                    obj.put("postcode", pd.getLabsDAO().getPostcode());
                    obj.put("country", pd.getLabsDAO().getCountry());
obj.put("authority", pd.getLabsDAO().getAuthority());
                    System.out.println(obj.toString());
                    //return obj.toString();
                }


            }
            session.setAttribute("pidaos", peopleDAOs);
        }

    }
}
