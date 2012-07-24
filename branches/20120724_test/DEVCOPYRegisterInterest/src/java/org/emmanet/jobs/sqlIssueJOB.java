/*
 * sqlIssueJOB.java
 *
 * Created on 20 February 2008, 15:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.emmanet.jobs;

import java.util.List;
import org.emmanet.model.WebRequestsDAO;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author phil
 */
public class sqlIssueJOB extends QuartzJobBean {
    
    
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        
         WebRequestsDAO webReq;
                WebRequests    wr = new WebRequests();
                
               List check = wr.requestByID(3);
               System.out.println("Checking retrieval of request ID 3 from database: result size is :: " + check.size());
    }
    

    
}
