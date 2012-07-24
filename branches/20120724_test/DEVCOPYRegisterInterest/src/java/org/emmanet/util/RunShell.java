/*
 * RunShell.java
 *
 * Created on 18 April 2008, 16:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.emmanet.util;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author phil
 */
public class RunShell {

    private String path;
    private String runFile;
    Process proc;

    public void execute(String[] args) {
        /* I guess there will be permission errors when running from user Tomcat
         * Will have to grant permissions on directories this code needs to access.
         * Take note of where code runs from, have to specifically give path.
         */
        path = args[0];
        runFile = args[1];

        File fPath = new File(path);
        String[] envp = null;
        System.out.println(path + runFile);
        // String cd[] = {"sh", "-c", "cd ~"};//+ ">  /data/web/TESTsubmissions/out.txt"// + path 

        //String pwd[] = {"sh", "-c", "/usr/bin/pwd > /data/web/TESTsubmissions/out.txt"};
        String[] chmod = {"chmod", "777", path + runFile};

        // String[] runscript = {"sh", "-c", "cd " + path, "./" + runFile + "> /data/web/TESTsubmissions/jdata.out"};//path + 

        String[] runscript2 = {"sh", "-c", path + runFile};//path +

        Runtime rtime = Runtime.getRuntime();
        try {
            // proc = rtime.exec(cd);
            //  proc = rtime.exec(pwd);
            //System.out.println("######### RunShell has cd'd to specified path #########");
            //rtime.exec(chmod); // Set the authorities for testing
            //  proc = rtime.exec(runscript); // Run the script with redirection

            proc = rtime.exec(runscript2,envp,fPath); // Run the script with redirection

            try {
                if (proc.waitFor() != 0) {
                    System.err.println("exit value = " +
                            proc.exitValue());
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("######### RunShell has run specified file #########");
        } catch (IOException e) {
            e.printStackTrace();
        }

        rtime = null;
    }
}
