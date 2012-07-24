/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.jobs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.emmanet.model.BackgroundManager;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.apache.velocity.app.VelocityEngine;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.emmanet.util.RunShell;

public class rssJOB extends QuartzJobBean {

    private VelocityEngine velocityEngine;
    private Format formatter;
    private String searchURL = "";
    private String rssLogo = "";
    private String fileLocation = "";
    private String filename = "";
    private String shellPath = "";
    private String shellScript = "";

    public String generateRssFeed() {

        String feed = "";
        // Create hashmap for velocity templates
        Map model = new HashMap();
        model.put("searchURL", searchURL);
        model.put("rssLogo", rssLogo);

        //create and add timestamp to map
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        Date date = new Date();
        String fmtDate = formatter.format(date);
        model.put("rssTimestamp", fmtDate);

        feed = (new StringBuilder()).append(feed).append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                "org/emmanet/util/velocitytemplates/rssHeader-Template.vm", model)).toString();

//insert body values from new rss input file.

        try {
            BufferedReader in = new BufferedReader(new FileReader("/nfs/panda/emma/tmp/rssItems"));
            String str;

            while ((str = in.readLine()) != null) {
                feed = (new StringBuilder()).append(feed).append(str).toString();
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        feed = (new StringBuilder()).append(feed).append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                "org/emmanet/util/velocitytemplates/rssFooter-Template.vm", model)).toString();
        System.out.println();
        System.out.print(feed);
        System.out.println();

//write to file now
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(getFileLocation() + getFilename(), false));
            out.write(feed);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RunShell rs = new RunShell();
        String[] run = {getShellPath(), getShellScript()};
        rs.execute(run);
        return feed;
    }

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        generateRssFeed();
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public String getSearchURL() {
        return searchURL;
    }

    public void setSearchURL(String searchURL) {
        this.searchURL = searchURL;
    }

    public String getRssLogo() {
        return rssLogo;
    }

    public void setRssLogo(String rssLogo) {
        this.rssLogo = rssLogo;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getShellScript() {
        return shellScript;
    }

    public void setShellScript(String shellScript) {
        this.shellScript = shellScript;
    }

    public String getShellPath() {
        return shellPath;
    }

    public void setShellPath(String shellPath) {
        this.shellPath = shellPath;
    }
}
