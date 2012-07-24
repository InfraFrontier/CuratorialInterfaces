<%-- 
    Document   : background_inc.jsp
    Created on : 12-May-2009, 16:26:45
    Author     : phil
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/datetime-1.0" prefix="dt" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/request-1.0" prefix="req" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.io.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%
//OK resigned to creating a scriptlet to read in values from output file
 String bgname = request.getParameter("bgname");
            String selected = "";
            String optionDisplay="";
            String optionDisplayClose="";
             if(request.getParameter("optionDisplay") != null){
                     optionDisplay = request.getParameter("optionDisplay");  
optionDisplay ="<form:option value=\"";
                optionDisplayClose="</form:option>";                          
             }else {
                optionDisplay ="<option value=\"";
                optionDisplayClose="</option>";
             }
            try {
                BufferedReader in = new BufferedReader(new FileReader("/nfs/panda/emma/tmp/bgNamesList.emma"));
                String str;

                while ((str = in.readLine()) != null) {
                   int i = str.indexOf("||");
                   String id = str.substring(0, i);
                   str = str.replace(id + "||", "");
                   String param = request.getParameter("bgname");
                    if (id.equals(param)){
                        selected="selected";
                    } else {
                       selected="";
                    }
                //   System.out.println("<option value=\""+ id + "\"  "+selected +">"+ str+"</option>");
%>

<%=optionDisplay %><%= id %>" <%= selected %>><%= str%><%=optionDisplayClose %>

<%


                }

                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

%>