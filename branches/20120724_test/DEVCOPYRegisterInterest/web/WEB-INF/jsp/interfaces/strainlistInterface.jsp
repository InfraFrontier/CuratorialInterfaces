<%-- x
    Document   : strainsInterface
    Created on : 16-Jul-2008, 11:10:06
    Author     : phil
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<c:set var="keyRef" value='${returnedOut}'></c:set>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <style type="text/css">@import url(../css/emmastyle.css);</style>
            <script type="text/javascript" src="../js/buttoncontrols.js"></script>
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
                    <th style="vertical-align: top; text-align: center;">Available to order whilst archiving</th>
                    <th style="vertical-align: top; text-align: center;">Update Archive</th>
                    <th style="vertical-align: top; text-align: center;">Update Requests</th>
                    <th style="vertical-align: top; text-align: center;">Update Strains</th>
                    <th style="vertical-align: top; text-align: center;">Update PO</th>
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
                        <td>${str[1]}</td>
                        <td>${str[2]}</td>
                        <td>${str[3]}</td>
                        <td>${str[4]}</td>
                        <td>${str[5]}</td>
                        <td>${str[6]}</td>
                        <td><c:if test="${empty str[7]}" >no</c:if>${str[7]}</td>
                        <td align="center" valign="top"><input type="button" value="Edit Archive" name="${str[8]}" onClick="parent.location='archiveUpdateInterface.emma?EditArch=${str[8]}'" /></td>
                        <td align="center" valign="top"><input type="button" value="Edit Strains" name="${str[0]}" onClick="parent.location='strainsUpdateInterface.emma?EditStrain=${str[8]}'" /></td>
                        <td align="center" valign="top"><input type="button" value="Edit Request" name="${str[8]}" onClick="parent.location='requestsInterface.emma?emmaID=${str[0]}&listReqs=y&sr=Search+requests'" /></td>
                        <td align="center" valign="top"><input type="button" value="Edit PO" name="${str[8]}" onClick="parent.location='poUpdateInterface.emma?poEdit=${str[8]}'" /></td>
                        
                        <td><%--${str[0]}--%></td>
                    </tr>
                </c:forEach>
                
            </tbody>
        </table>
        
        </c:if>
     <%-- DATAGRID ENDS --%>
     <br><br>
            <%-- include consistent footer/navigation --%>
            <%@ include file="footer_inc.html" %>
    </body>
</html>
