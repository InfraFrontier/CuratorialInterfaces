<%-- 
    Document   : strainDetails_inc
    Created on : 05-Aug-2008, 17:23:40
    Author     : phil
--%>
<%-- 
        TODO: RESOLVE FUGLEY HACK 
        THIS JSP FILE IS USED TO DISPLAY DETAILS OF STRAIN FROM
        STRAINSDAO AS WEB REQUEST DETAILS NEED TO BIND TO WEBREQUESTDAO
        AND CANNOT SEEM TO BRING STRAIN INFO AND WEB REQUEST INFO AT SAME TIME
        MAY NEED TO USE ANOTHER CONTROLLER BUT TIME IS SHORT
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/datetime-1.0" prefix="dt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%
            String id = request.getParameter("strainID");

%>

<c:set var="keyRef" value='${returnedOut}'></c:set>


 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Included Strain Details Page</title>
        <style type="text/css">@import url(../css/emmastyle.css);</style>
    </head>
    <body>
        <c:forEach var="str"  items='${keyRef["strain"]}' >
            <br/><br/>
            <table border="0" width="100%">
                <tbody>
                    <tr>
                        <td colspan="4" class="sectHead">Strain details for strain ${str["emma_id"]}</td>
                    </tr>
                    <tr>
                        <td valign="top"><b>Emma ID:</b></td>
                        <td valign="top">${str["emma_id"]}</td>
                        <td valign="top"><b>Strain name:</b></td>
                        <td valign="top"> <c:set var="namePost" value='${fn:substringAfter(str["name"],"<")}'></c:set>
                            <c:set var="namePre" value='${fn:substringBefore(namePost,">")}'></c:set>
                            <c:choose>
                                <c:when test="${not empty namePre}">
                                    <%--${fn:substringBefore(str["name"],"<")}<sup><c:out value="${namePre}"/></sup>${fn:substringAfter(str["name"],">")}--%>
                                    <c:set var="NameStrip" value="${fn:replace(str['name'],'<','<sup||')}"/>
                                    <c:set var="NameStrip" value="${ fn:replace(NameStrip,'>','</sup>')}"/>
                                    <c:set var="NameStrip" value="${ fn:replace(NameStrip,'||','>')}"/>
                                    ${NameStrip}
                                </c:when>
                                <c:otherwise>${str["name"]}</c:otherwise>
                            </c:choose>
                        </td>
                        
                        
                    </tr>
                    <tr>
                        <td valign="top"><b>Archive centre:</b></td>
                        <td valign="top"><%--<c:forEach var="centr"  items='${keyRef["centre"]}' >--%>${keyRef["centre"]}<%--</c:forEach>--%></td>
                        <td valign="top"><b>Available to order whilst archiving:</b></td>
                        <td valign="top"><c:if test="${empty str['available_to_order']}" >no</c:if>${str["available_to_order"]}</td>
                        
                    </tr>
                    <tr>
                        
                        <td valign="top"><b>Strain status:</b></td>
                        <td valign="top">${str["str_status"]}</td>
                        <td valign="top"><b>Strain access:</b></td>
                        <td valign="top">${str["str_access"]}</td>
                        
                    </tr>
                    <tr>
                        <%-- <td  valign="top">
                            <b> Current stock:</b>
                        </td>
                        <td valign="top" >                            
                            <c:set var="splitter" value='${fn:split(keyRef["cvAvail"],"||")}'></c:set>
                            <c:forEach var="value" items="${splitter}">
                            ${value}<br />
                                
                            </c:forEach>
                            
                        </td>--%>
                        <td valign="top"><b>MTA file:</b>
                        </td>
                        <td><c:if test="${not empty str['mta_file']}" ><a href='${keyRef["mtaPath"]}${str["mta_file"]}' target="_top">
                                <img src="../images/pdf_icon.gif" width="28" height="29" alt="Download MTA file ${keyRef["mtaPath"]}${str["mta_file"]}" border="0"/>
                            </a></c:if>
                            <c:if test="${empty str['mta_file']}">N/A</c:if>
                        </td>
                        <td><b>Request PDF:</b></td>
                        <td> <c:if test="${not empty sessionScope.reqID}" ><a href='../pdfView.emma?id=${sessionScope.reqID}&pdfView=y&type=req' target="_blank">
                                <img src="../images/pdf_icon.gif" width="28" height="29" alt="Download Request file IN PDF format" border="0"/>
                            </a></c:if>
                        <c:if test="${empty sessionScope.reqID}">Unable to display PDF View</c:if></td>
                    </tr>
                    <tr>
                        <td><b>Grace period expiry:</b></td>
                        <td ><c:choose><c:when test="${not empty keyRef['gp_release']}">${keyRef['gp_release']}</c:when><c:otherwise>No release date specified.</c:otherwise></c:choose></td>
                        <td valign="top">&nbsp;<%-- <b>Submitted on: </b>--%></td>
                        <td valign="top">&nbsp;<%--${fn:substringBefore(keyRef["submitted"]," ")}<%--${str["date_published"]}--%></td>
                    </tr>
                </tbody>
            </table>
        </c:forEach>
    </body>
</html>
