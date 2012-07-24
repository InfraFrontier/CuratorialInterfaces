<%-- 
    Document   : strainsInterface
    Created on : 16-Jul-2008, 11:10:06
    Author     : phil
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/datetime-1.0" prefix="dt" %>

<c:set var="keyRef" value='${returnedOut}'></c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="../js/buttoncontrols.js"></script>
        <style type="text/css">@import url(../css/emmastyle.css);</style>
        <title>EMMA Strains Interface</title>
    </head>
    <body>
        <c:set var="UserName" value='${keyRef["UserName"]}'></c:set>
        <span id="loginHeader">Logged in as user <c:out value="${fn:toUpperCase(UserName)}" /> </span>
        <br/><br/>
        <%-- include consistent navigation --%>
        <%@ include file="navigation_inc.html" %>
        <p class="searchHeader" id="searchHeader">${keyRef["searchSize"]} record<c:choose><c:when  test="${keyRef['searchSize'] ==1}" ></c:when><c:otherwise>s</c:otherwise></c:choose> returned
            <c:set var="search" value='${keyRef["searchDescription"]}'></c:set>
            <c:choose>
                <c:when  test="${empty search}" >  
                </c:when>
                <c:otherwise>
                    using search criteria ${search}
                </c:otherwise>
            </c:choose>
            &nbsp;Report generated :: <dt:format pattern="yyyy-MM-dd HH:mm"><dt:currentTime/></dt:format>
        </p>
        <p>&nbsp;</p>
        <c:if test="${keyRef['searchSize'] != 0}">
            <%-- DATAGRID BEGINS --%>
            <table align="center" style="text-align: left; width: 80%;" border="1"
                   cellpadding="1" cellspacing="1" class="datagrid">
                <thead>
                    <tr>
                        <th style="vertical-align: top; text-align:center;">EMMA ID</th>
                        <th style="vertical-align: top; text-align: center;">Submitted</th>
                        <th style="vertical-align: top; text-align: center;">Repository</th>
                        <th style="vertical-align: top; text-align: center;">Strain Name</th>
                        <th style="vertical-align: top; text-align: center;">Scientists Surname</th>
                        <th style="vertical-align: top; text-align: center;">Strain Status</th>
                        <th style="vertical-align: top; text-align: center;">Strain Access</th>
                        <%--<th style="vertical-align: top; text-align: center;">Available to order</th>--%>
                        <th style="vertical-align: top; text-align: center;">Update Archive</th>
                        <th style="vertical-align: top; text-align: center;">EMMA ID</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="str" items='${keyRef["results"]}' varStatus="row">
                        <tr class="<c:choose>
                                <c:when test="${row.count % 2 == 0}" > evenRow</c:when>
                                <c:otherwise>oddRow </c:otherwise>
                            </c:choose>">
                            <td>${str[0]}</td>
                            <td>${fn:substringBefore(str[1]," ")}</td>
                            <td>${str[2]}</td>
                            <td><c:set var="namePost" value='${fn:substringAfter(str[3],"<")}'></c:set>
                                <c:set var="namePre" value='${fn:substringBefore(namePost,">")}'></c:set>
                                <c:choose>
                                    <c:when test="${not empty namePre}"><%--${fn:substringBefore(str[3],"<")}<sup><c:out value="${namePre}"/></sup>${fn:substringAfter(str[3],">")}--%>
                                        <c:set var="NameStrip" value="${fn:replace(str[3],'<','<sup||')}"/>
                                        <c:set var="NameStrip" value="${ fn:replace(NameStrip,'>','</sup>')}"/>
                                        <c:set var="NameStrip" value="${ fn:replace(NameStrip,'||','>')}"/>
                                        ${NameStrip}
                                    </c:when>
                            <c:otherwise>${str[3]}</c:otherwise></c:choose></td>
                            <td>${str[4]}</td>
                            <td>${str[5]}</td>
                            <td>${str[6]}</td>
                            <%-- <td><c:if test="${empty str[7]}" >no</c:if>${str[7]}</td>--%>
                            <td align="center" valign="top"><input type="button" value="Edit Archive" name="${str[8]}" onClick="parent.location='archiveUpdateInterface.emma?EditArch=${str[8]}'" /></td>
                            
                            <td>${str[0]}</td>
                        </tr>
                    </c:forEach>
                    
                </tbody>
            </table>
            
        </c:if>
        <%-- DATAGRID ENDS --%>
        <br><br>
        <%-- include consistent footer/navigation --%>
        <%@ include file="footer_inc.html" %>
<center>Report generated :: <dt:format pattern="yyyy-MM-dd HH:mm"><dt:currentTime/></dt:format></center>
    </body>
</html>
