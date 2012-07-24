/*
 * Creates an option list daily for inclusion in jsp pages where needed
 */
package org.emmanet.jobs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.emmanet.model.BackgroundManager;
import org.emmanet.model.LaboratoriesManager;
import org.emmanet.model.LabsDAO;
import org.emmanet.model.StrainsManager;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author phil
 */
public class BackgroundListJOB extends QuartzJobBean {

    private String fileLocation="/nfs/panda/emma/tmp/";// = "/tmp/";
    private BackgroundManager bm = new BackgroundManager();
    private StrainsManager sm = new StrainsManager();
    private LaboratoriesManager lm = new LaboratoriesManager();

    public void generateBGList() {
        try {
            /* BufferedWriter out = new BufferedWriter(new FileWriter(fileLocation + "bglist"));
            List backGrounds = bm.getBackgrounds();
            out.write("<option value=\"0\" selected>Please select...</option>\n");
            for (Iterator it = backGrounds.listIterator(); it.hasNext();) {
            Object[] o = (Object[]) it.next();
            String id_bg = o[0].toString();
            String name = o[1].toString();
            out.write("<option value=\"" + id_bg + "\">" + name + "</option>\n");*/

            List backGrounds = bm.getBackgrounds();
            BufferedWriter out = new BufferedWriter(new FileWriter(fileLocation + "bgNamesList.emma", false));
            for (Iterator it = backGrounds.listIterator(); it.hasNext();) {
                Object[] o = (Object[]) it.next();
                out.write(o[0].toString() + "||");
                out.write(o[1].toString());
                out.newLine();
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateFundingSourceList() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileLocation + "fslist"));

            List backGrounds = bm.getCVSources();

            out.write("<option value=\"0\" selected>Please select...</option>\n");
            for (Iterator it = backGrounds.listIterator(); it.hasNext();) {
                Object[] o = (Object[]) it.next();
                String id = o[0].toString();
                String code = o[1].toString();
                String desc = o[2].toString();
                out.write("<option value=\"" + id + "\">(" + code + ") " + desc + "</option>\n");
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateReqFundingSourceList() {
        //Generates the list used for request related funding sources
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileLocation + "reqfslist"));

            List backGrounds = sm.getReqRelatedFundingSource();

            out.write("<option value=\"0\" selected>Please select...</option>\n");
            for (Iterator it = backGrounds.listIterator(); it.hasNext();) {
                Object[] o = (Object[]) it.next();
                String id = o[0].toString();
                String code = o[1].toString();
                String desc = o[2].toString();
                out.write("<option value=\"" + id + "\">(" + code + ") " + desc + "</option>\n");
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateProjectList() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileLocation + "plist"));

            List projects = bm.getCVProjects();

            out.write("<option value=\"0\" selected>Please select...</option>\n");
            for (Iterator it = projects.listIterator(); it.hasNext();) {
                Object[] o = (Object[]) it.next();
                String id = o[0].toString();
                String code = o[1].toString();
                String desc = o[2].toString();
                out.write("<option value=\"" + id + "\">" + code + " (" + desc + ")</option>\n");
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateCodeInternalList() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileLocation + "cilist"));

            List codeInt = sm.getCodeInternal();

            out.write("<option value=\"\" selected>Please select...</option>\n");
            for (Iterator it = codeInt.listIterator(); it.hasNext();) {
                Object o = it.next();
                String code = o.toString();
                out.write("<option value=\"" + code + "\">" + code + "</option>\n");
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateNameStatusList() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileLocation + "nslist"));

            List nameStatus = sm.getNameStatusList();

            out.write("<option value=\"\" selected>Please select...</option>\n");
            for (Iterator it = nameStatus.listIterator(); it.hasNext();) {
                Object o = it.next();
                String code = o.toString();
                out.write("<option value=\"" + code + "\">" + code + "</option>\n");
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateArchivesList() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileLocation + "archlist"));

            List archives = lm.getArchivesByCode();

            out.write("<option value=\"\" selected>Please select...</option>\n");
            for (Iterator it = archives.listIterator(); it.hasNext();) {
                LabsDAO ld = (LabsDAO) it.next();

                out.write("<option value=\"" + ld.getId_labo() + "\">" + ld.getName() + " (" + ld.getCode() + ")</option>\n");
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        generateBGList();
        generateFundingSourceList();
        generateProjectList();
        generateReqFundingSourceList();
        generateCodeInternalList();
        generateNameStatusList();
        generateArchivesList();

    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
}
