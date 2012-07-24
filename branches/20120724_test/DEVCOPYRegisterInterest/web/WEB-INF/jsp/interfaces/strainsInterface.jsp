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
    <style type="text/css">@import url(../css/emmastyle.css);</style>
    <script type="text/javascript" src="../js/synonymDiv.js"></script>
    <script type="text/javascript" src="../js/buttoncontrols.js"></script>
    <title>EMMA Strains Interface</title>
</head>
<body>
<c:set var="UserName" value='${keyRef["UserName"]}'></c:set>
<span id="loginHeader">Logged in as user <c:out value="${fn:toUpperCase(UserName)}" /> </span><br/><br/>
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
            <th style="vertical-align: top; text-align: center;">Internal Name</th>
            <th style="vertical-align: top; text-align: center;">Scientists Surname</th>
            <th style="vertical-align: top; text-align: center;">Strain Status</th>
            <th style="vertical-align: top; text-align: center;">Strain Access</th>
            <th style="vertical-align: top; text-align: center;">Available to order</th>
            <th style="vertical-align: top; text-align: center;">Update Archive</th>
            <th style="vertical-align: top; text-align: center;">Update Strains</th>
            <th style="vertical-align: top; text-align: center;">Update Requests</th>
            <th style="vertical-align: top; text-align: center;">Update PO</th>
            <th style="vertical-align: top; text-align: center;">EMMA ID</th>
        </tr>
    </thead>
    <tbody>
    <%-- SET CURATION USERNAME TO SUPER TO ALLOW BUTTON HIDING --%>
    <c:if test="${UserName == 'curator'}">
        <c:set var="CuratorUserName" value='super'/>
    </c:if>
    <c:forEach var="str" items='${keyRef["results"]}' varStatus="row">
    
        <tr class="<c:choose>
                <c:when test="${row.count % 2 == 0}" > evenRow</c:when>
                <c:otherwise>oddRow </c:otherwise>
            </c:choose>" valign="top">
        <c:set var="strainsDAO" value='${str}'></c:set>
        <c:set var="archiveDAO" value='${strainsDAO["archiveDAO"]}'></c:set>
        <c:set var="labsDAO" value='${archiveDAO["labsDAO"]}'></c:set>
        <c:set var="peopleDAO"  value='${strainsDAO["peopleDAO"]}'></c:set>
        <c:set var="syn_strainsDAO"  value='${strainsDAO["syn_strainsDAO"]}'></c:set>
        <c:set var="fmtsubdate" value="${syn_archiveDAO['submitted']}"></c:set>
        <td>${strainsDAO["emma_id"]}</td>
        <td>${fn:substringBefore(archiveDAO["submitted"]," ")}<%--${archiveDAO["submitted"]}--%></td> 
        <td>${labsDAO["code"]}</td>
        <td <c:if test="${not empty syn_strainsDAO}">onmouseover="ShowContent('synDiv${strainsDAO['emma_id']}'); return true;"  onmouseout="HideContent('synDiv${strainsDAO['emma_id']}'); return true;"</c:if>><c:set var="namePost" value='${fn:substringAfter(strainsDAO["name"],"<")}'></c:set>
            <c:set var="namePre" value='${fn:substringBefore(namePost,">")}'></c:set>
            <c:choose>
                <%--<c:when test="${not empty namePre}">${fn:substringBefore(strainsDAO["name"],"<")}<sup><c:out value="${namePre}"/></sup>${fn:substringAfter(strainsDAO["name"],">")}</c:when>--%>
                <c:when test="${not empty namePre}">
                    <c:set var="NameStrip" value="${fn:replace(strainsDAO['name'],'<','<sup||')}"/>
                    <c:set var="NameStrip" value="${ fn:replace(NameStrip,'>','</sup>')}"/>
                    <c:set var="NameStrip" value="${ fn:replace(NameStrip,'||','>')}"/>
                    ${NameStrip}
                </c:when>           
                <c:otherwise>${strainsDAO["name"]}</c:otherwise>
            </c:choose><%--${strainsDAO["name"]}--%>
                                    
            <c:if test="${not empty syn_strainsDAO}">
            
                <div 
                id="synDiv${strainsDAO['emma_id']}" class="syndiv">Synonym<br >
                <c:forEach var="synonym" items='${syn_strainsDAO}' varStatus="row">
                    <c:set var="namePost" value='${fn:substringAfter(synonym["name"],"<")}'></c:set>
                    <c:set var="namePre" value='${fn:substringBefore(namePost,">")}'></c:set>
                    <c:choose>
                        <c:when test="${not empty namePre}"><%--${fn:substringBefore(synonym["name"],"<")}<sup><c:out value="${namePre}"/></sup>${fn:substringAfter(synonym["name"],">")}--%>
                            <c:set var="NameStrip" value="${fn:replace(strainsDAO['name'],'<','<sup||')}"/>
                            <c:set var="NameStrip" value="${ fn:replace(NameStrip,'>','</sup>')}"/>
                            <c:set var="NameStrip" value="${ fn:replace(NameStrip,'||','>')}"/>
                            ${NameStrip}
                        </c:when>
                        <c:otherwise>${synonym["name"]}</c:otherwise>
                    </c:choose>
                    <br/>
                </c:forEach>
                </div> 
            </c:if>
            <%--   
                                <c:if test="${not empty syn_strainsDAO}"><div 
                                        id="synDiv${strainsDAO['emma_id']}" class="syndiv">Synonym<br >
                                        <c:set var="namePost" value='${fn:substringAfter(syn_strainsDAO["name"],"<")}'></c:set>
                                        <c:set var="namePre" value='${fn:substringBefore(namePost,">")}'></c:set>
                                        <c:choose>
                                            <c:when test="${not empty namePre}">${fn:substringBefore(syn_strainsDAO["name"],"<")}<sup><c:out value="${namePre}"/></sup>${fn:substringAfter(syn_strainsDAO["name"],">")}</c:when>
                                            <c:otherwise>${syn_strainsDAO["name"]}</c:otherwise>
                                        </c:choose>
        </div></c:if>--%></td>
        <td>${strainsDAO["code_internal"]}</td>
        <td>${peopleDAO["surname"]}</td> 
        <td>${strainsDAO["str_status"]}</td>
        <td>${strainsDAO["str_access"]}</td>
        <td>${strainsDAO["available_to_order"]}</td>
        <c:set var="buttonStatus" value=''></c:set>
        <c:set var="PObuttonStatus" value=''></c:set>
        <c:if test="${UserName ne 'super' || 'curator'}"><c:set var="PObuttonStatus" value='class="disabled" disabled'></c:set></c:if>
        
        <c:if test="${CuratorUserName == 'super'}">
            <c:set var="UserName" value='super'/>
        </c:if>
        
        
        <c:if test="${fn:toUpperCase(UserName) ne labsDAO['code'] && fn:toUpperCase(UserName) ne 'SUPER'}"><c:set var="buttonStatus" value=' class="disabled" disabled '></c:set></c:if>
        <%--<td><c:if test="${empty strainsDAO['available_to_order']}" >no</c:if>${strainsDAO["available_to_order"]}</td>--%>
        <td align="center" valign="top"><input ${buttonStatus} type="button" value="Edit Archive" name="Edit archive record ${strainsDAO["id_str"]}" onClick="parent.location='archiveUpdateInterface.emma?EditArch=${strainsDAO["id_str"]}'" /></td>
        <td align="center" valign="top"><input ${buttonStatus} type="button" value="Edit Strains" name="Edit strain record for ${strainsDAO["id_str"]}" onClick="parent.location='strainsUpdateInterface.emma?EditStrain=${strainsDAO["id_str"]}'" /></td>
        <td align="center" valign="top"><input ${buttonStatus} type="button" value="Edit Requests" name="Requests for ${strainsDAO["id_str"]}" onClick="parent.location='requestsInterface.emma?emmaID=${strainsDAO["emma_id"]}&listReqs=y&sr=Search+requests'" /></td>
        <td align="center" valign="top"><input ${PObuttonStatus} type="button" value="Edit PO" name="PO edit for ${strainsDAO["id_str"]}" onClick="parent.location='poUpdateInterface.emma?poEdit=${strainsDAO["id_str"]}'" /></td>
        <td>${strainsDAO["emma_id"]}</td>
        
        </tr>
        
        <c:if test="${CuratorUserName == 'super'}">
            <c:set var="UserName" value='curator'/>
        </c:if>
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
