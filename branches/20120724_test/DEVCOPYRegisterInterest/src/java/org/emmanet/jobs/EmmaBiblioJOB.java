/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.jobs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import uk.ac.ebi.cdb.webservice.*;
import java.util.List;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.emmanet.model.BibliosDAO;
import org.emmanet.model.BibliosManager;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 *
 * @author ckchen
 * @author  philw
 * modified BY PHILW 08MAR2012 to run in Spring using quartz scheduling
 */
public class EmmaBiblioJOB extends QuartzJobBean {

    /**
     * @param args the command line arguments
     */
    private String wsdlLocation;
    private String schedulerMsg = "";
    private MailSender mailSender;
    private String[] cc;

    /**
     * @return the mailSender
     */
    public MailSender getMailSender() {
        return mailSender;
    }

    /**
     * @param mailSender the mailSender to set
     */
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * @return the cc
     */
    public String[] getCc() {
        return cc;
    }

    /**
     * @param cc the cc to set
     */
    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public class FetchBiblio {

        //   @WebServiceRef(wsdlLocation=)
        //     static WSCitationImplService service = new WSCitationImplService();
        public String title;
        public String author1;
        public String author2;
        public String journal;
        public String volume;
        public String issue;
        public String pages;
        public String paperid;
        public int year;
    }

    public BibliosDAO check_pmid_exists(int pmid) {
        BibliosManager bm = new BibliosManager();
        BibliosDAO bdao = (BibliosDAO) bm.getPubmedIDByID(pmid);
        if (bdao == null) {
            System.out.println("PMID " + pmid + " does not exist in database ... exiting");
            schedulerMsg = (new StringBuilder()).append(schedulerMsg).append("\nPMID ").append(pmid).append(" does not exist in database ... exiting\n").toString();
        }
        return bdao;
    }

    public void check_for_updates() {
        //   try {
        // select the pubmed ids of not updated biblios
        BibliosManager bm = new BibliosManager();
        BibliosDAO bdao = new BibliosDAO();
        List rset = bm.getPubmedID();
        int pmid=0;
        String spmid;
        int counter = 0;
        for (Iterator it = rset.listIterator(); it.hasNext();) {
            bdao = (BibliosDAO) it.next();
            counter++;
            //pmid = Integer.parseInt(bdao.getPubmed_id());
            spmid = bdao.getPubmed_id();
            // removeleading non-digits of a pmid
            Pattern replace = Pattern.compile("[^\\d+]");
            Matcher matcher = replace.matcher(spmid);
            String goodPmid = null;
            while (matcher.find()) {
                goodPmid = matcher.replaceAll("");
                System.out.println("Corrected '" + spmid + "' as " + "'" + goodPmid + "'");
                schedulerMsg = (new StringBuilder()).append(schedulerMsg).append("\nCorrected ").append(spmid).append(" as ").append(goodPmid).append("\n").toString();
                pmid = Integer.parseInt(goodPmid);
                bdao.setId_biblio(pmid);
                bm.save(bdao);
            }
            FetchBiblio paper = new FetchBiblio();
            if(pmid >=0){
            paper = fetchPaper(pmid);
            }
            updateBibliographicReferences(paper, bdao);
        }
        System.out.println("\nUpdated " + counter + " biblios records");
        schedulerMsg = (new StringBuilder()).append(schedulerMsg).append("\nUpdated ").append(counter).append(" biblios records").toString();
    }

    public void updateBibliographicReferences(FetchBiblio paper, BibliosDAO bdao) {
        
        if(paper.issue != null){
        System.out.println("Updating for PMID " + paper.paperid + "...");
        schedulerMsg = (new StringBuilder()).append(schedulerMsg).append("\nUpdating for PMID ").append(paper.paperid).append("...").toString();
//System.out.println("PAPER ISSUE= " + paper.issue);
        String vol = "";
        if (paper.issue != null && !paper.issue.isEmpty()) {
            vol = paper.volume + "(" + paper.issue + ")";
        } else {
            vol = paper.volume;
        }

        bdao.setTitle(paper.title);
        bdao.setAuthor1(paper.author1);
        bdao.setAuthor2(paper.author2);
        bdao.setYear("" + paper.year);
        bdao.setJournal(paper.journal);
        bdao.setVolume(/*paper.volume*/vol);
        bdao.setPages(paper.pages);
        bdao.setUsername("EMMA");
        bdao.setUpdated("Y");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        //System.out.println(dateFormat.format(date));
        bdao.setLast_change(dateFormat.format(date));



        BibliosManager bm = new BibliosManager();
        bm.save(bdao);
    }
}
    public FetchBiblio fetchPaper(int pmid) {
        FetchBiblio paper = new FetchBiblio();
        wsdlLocation = getWsdlLocation();//"http://www.ebi.ac.uk/webservices/citexplore/v1.0/service?wsdl";
        WSCitationImplService service = new WSCitationImplService();
        try {
            WSCitationImpl port = service.getWSCitationImplPort();
            //System.out.println(" Invoking searchCitations operation on wscitationImpl port ");
            ResultListBean resultListBean = port.searchCitations("EXT_ID:" + pmid, "core", 0, "");
            System.out.println("\nNumber of hits:\t" + resultListBean.getHitCount());
            schedulerMsg = (new StringBuilder()).append(schedulerMsg).append("Number of hits:\t").append(resultListBean.getHitCount()).toString();
            //System.out.println("Off set:\t" + resultListBean.getOffSet());
            List<ResultBean> resultBeanCollection = resultListBean.getResultBeanCollection();
            for (ResultBean resultBean : resultBeanCollection) {
                Citation citation = resultBean.getCitation();
                int size = citation.getAuthorCollection().size();
                int counter = -1;
                StringBuilder otherAuthors = new StringBuilder();
                for (Author author : citation.getAuthorCollection()) {
                    counter++;
                    String fullname = author.getFullName();
                    if (counter == 0) {
                        paper.author1 = fullname;
                    } else if (counter + 1 < size) {
                        otherAuthors.append(fullname).append(", ");
                    } else {
                        otherAuthors.append(fullname);
                    }
                }
                paper.paperid = "" + pmid;
                paper.title = citation.getTitle();
                paper.year = citation.getJournalIssue().getYearOfPublication();
                paper.journal = citation.getJournalIssue().getJournal().getISOAbbreviation();
                paper.volume = citation.getJournalIssue().getVolume();
                paper.issue = citation.getJournalIssue().getIssue();
                paper.pages = citation.getPageInfo();
                paper.author2 = otherAuthors.toString();
            }
        } catch (QueryException_Exception qex) {
            System.out.printf("Caught QueryException_Exception: %s\n", qex.getFaultInfo().getMessage());
            schedulerMsg = (new StringBuilder()).append(schedulerMsg).append("\nCaught QueryException_Exception: %s\n").append(qex.getFaultInfo().getMessage()).toString();
        }
        return paper;
    }

    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
        schedulerMsg = (new StringBuilder()).append(schedulerMsg).append("EmmaBiblioJOB Kicking Off").append("\n\n").toString();
        System.out.println("EmmaBiblioJOB Kicking Off");
        check_for_updates();
        /* if (pmid != null) {
        FetchBiblio paper = new FetchBiblio(pmid, conn);
        } else {
        FetchBiblio paper = new FetchBiblio(conn);
        }*/
        System.out.println(schedulerMsg);
        webmasterJobMessage();
    }

    /**
     * @return the wsdlLocation
     */
    public String getWsdlLocation() {
        return wsdlLocation;
    }

    /**
     * @param wsdlLocation the wsdlLocation to set
     */
    public void setWsdlLocation(String wsdlLocation) {
        this.wsdlLocation = wsdlLocation;
    }

    public void webmasterJobMessage() {
        SimpleMailMessage msg = new SimpleMailMessage();
        String[] cc = {"koscieln@ebi.ac.uk", "webmaster@emmanet.org"};
        msg.setTo(cc);
        msg.setSubject("Cron - Update Biblios Job Report");
        msg.setText(schedulerMsg);
        try {
            getMailSender().send(msg);
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
