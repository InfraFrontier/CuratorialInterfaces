package org.emmanet.controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.BindException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author phil
 * 
 * 
 *    <item>
<title>#####################</title>
<link>#FULL LINK##</link>
<description>################</description>
<pubDate>##DATE FROM CODE##</pubDate>
</item>

 */
public class rssNewsController extends SimpleFormController {

    private String fileName;
    private String fileLocation;
    private String title;
    private String link;
    private String description;
    private String publishDate;
    private SimpleDateFormat formatter;
    private String NEW_LINE = System.getProperty("line.separator");

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    //   return new ModelAndView(getSuccessView());
  //  }

/*    protected ModelAndView onSubmit(
            HttpServletRequest request,
            HttpServletResponse response,
            Object command,
            BindException errors)
            throws ServletException, Exception {*/

        StringEscapeUtils esc = new StringEscapeUtils();
        
        setTitle(esc.escapeXml(request.getParameter("title")));
        setLink(esc.escapeXml(request.getParameter("link")));
        setDescription(esc.escapeXml(request.getParameter("description")));

        //create timestamp
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        Date date = new Date();
        publishDate = formatter.format(date);
        String item = "";
        item = (new StringBuilder()).append("<item>" + NEW_LINE).append("<title>").append(title).append("</title>" + NEW_LINE).append("<link>").append(link).append("</link>" + NEW_LINE).append("<description>").append(description).append("</description>" + NEW_LINE).append("<pubDate>").append(publishDate).append("</pubDate>" + NEW_LINE).append("</item>" + NEW_LINE).toString();
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(getFileLocation() + getFileName(), true));
            out.write(item);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView(getSuccessView());
        
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
}
