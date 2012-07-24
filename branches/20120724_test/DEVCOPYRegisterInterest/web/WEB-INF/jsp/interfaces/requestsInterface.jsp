<%-- 
    Document   : requestsInterface
    Created on : 16-Jul-2008, 11:09:51
    Author     : phil
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/datetime-1.0" prefix="dt" %>

<c:set var="keyRef" value='${returnedOut}'></c:set>
<%session.setAttribute( "urlQstring", "?"+request.getQueryString() );%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style type="text/css">@import url(../css/emmastyle.css);</style>
        <script type="text/javascript" src="../js/buttoncontrols.js"></script>
        <title>EMMA Requests Interface</title>
        <script type="text/javascript">
            function fulfillTrigger(){
                alert("dispatching mail");
            }
            
        </script>
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
        <p><center><input type="button" alt="Insert a new request using this interface" value="Insert Request" onclick="javascript:parent.location='/apps/RegisterInterest/requestFormView.emma?new=y&status=insert&user=<c:out value="${fn:toUpperCase(UserName)}" />'"/></center></p>
        <p>&nbsp;</p>
        <%-- DATAGRID BEGINS<c:out value="${row.count}"/> :  --%>
        
        <c:if test="${keyRef['searchSize'] != 0}">
            <table align="center" style="text-align: left; width: 80%;" border="1"
                   cellpadding="1" cellspacing="1" class="datagrid">
                <tbody>
                    <tr>
                        <th style="vertical-align: top; text-align: left;">EMMA
                        ID</th>
                        <th style="vertical-align: top; text-align: left;">Request ID</th>
                        <th style="vertical-align: top; text-align: left;">Repository</th>
                        <th style="vertical-align: top; text-align: left;">Strain
                        name</th>
                        <th style="vertical-align: top; text-align: left;">Scientists&nbsp;name</th>
                        <th style="vertical-align: top; text-align: left;">Country of origin</th>
                        <th style="vertical-align: top; text-align: left;">Request
                        status</th>
                        <th style="vertical-align: top; text-align: left;">ROI/Order</th>
                        <th style="vertical-align: top; text-align: left;">Request date</th>
                        <c:if test="${sessionScope.displayShip=='Y'}"><th style="vertical-align: top; text-align: left;">Shipped date</th></c:if>
                        <th style="vertical-align: top; text-align: left;">Edit</th>
                    </tr>
                   
                    <c:forEach var="reqs" items='${keyRef["results"]}' varStatus="row">
                  
                        <tr class="<c:choose>
                                <c:when test="${row.count % 2 == 0}" > evenRow</c:when>
                                <c:otherwise>oddRow </c:otherwise>
                            </c:choose>">
                            <td align="left" valign="top">${reqs[1]}</td>
                            <td align="left" valign="top">${reqs[0]}</td>
                            <td align="left" valign="top">${reqs[2]}</td>
                            <td align="left" valign="top"><c:set var="namePost" value='${fn:substringAfter(reqs[3],"<")}'></c:set>
                                <c:set var="namePre" value='${fn:substringBefore(namePost,">")}'></c:set>
                                <c:choose>
                                    <c:when test="${not empty namePre}"><%--${fn:substringBefore(reqs[3],"<")}<sup><c:out value="${namePre}"/></sup>${fn:substringAfter(reqs[3],">")}--%>
                                        <c:set var="NameStrip" value="${fn:replace(reqs[3],'<','<sup||')}"/>
                                        <c:set var="NameStrip" value="${ fn:replace(NameStrip,'>','</sup>')}"/>
                                        <c:set var="NameStrip" value="${ fn:replace(NameStrip,'||','>')}"/>
                                        ${NameStrip}
                                    </c:when>
                                    <c:otherwise>${reqs[3]}</c:otherwise>
                            </c:choose></td>
                            <td align="left" valign="top"><a href="mailto:${reqs[4]}">${reqs[5]} ${reqs[6]}</a>&nbsp<a href="mailto:${reqs[4]}"><img src="../images/email.gif" border="0" with="17" height="10" align="absmiddle"></a></td>
                            <td align="left" valign="top">${reqs[14]}</td>
                            <td align="left" valign="top">
                                <c:choose>
                                    <c:when  test="${reqs[7]} eq 'IN_PR'" >
                                        In progress
                                    </c:when>
                                    <c:when  test="${reqs[7]} eq 'CANC'" >
                                        Cancelled
                                    </c:when>
                                    <c:when  test="${reqs[7]} eq 'SHIP'" >
                                        Shipped
                                    </c:when>
                                    <c:when  test="${reqs[7]} eq 'TO_PR'" >
                                        To process
                                    </c:when>
                                    <c:otherwise>
                                    ${reqs[7]}
                                    </c:otherwise>
                                </c:choose> 
                            </td>
                            <td align="left" valign="top"><c:choose>
                                    <c:when test="${reqs[10] == 1}">ROI</c:when>
                                    <c:otherwise>Order</c:otherwise>
                            </c:choose></td>
                            <td align="left" valign="top">${fn:substringBefore(reqs[12]," ")}</td>
                            <c:if test="${sessionScope.displayShip=='Y'}"><td align="left" valign="top">${fn:substringBefore(reqs[15]," ")}</td></c:if>
                            <td align="left" valign="top"><input <c:if test="${fn:toUpperCase(UserName) != reqs[2] && fn:toUpperCase(UserName) ne 'SUPER'}"> class="disabled" disabled</c:if> type="button" value="Edit" name="${reqs[0]}" onClick="parent.location='requestsUpdateInterface.emma?Edit=${reqs[0]}&strainID=${reqs[11]}&archID=${reqs[13]}'" />
                                <c:choose>
                                    <c:when test="${reqs[17] == 'ARCHD'}"></c:when><%-- fn:contains(reqs[16],'Strain can now be ordered mail sent:') || removed to allow multiple fulfillment e-mails to be sent --%>
                                    <c:otherwise>
                                <c:if test="${reqs[10] == 1}"><c:if test="${reqs[7]  == 'IN_PR' || reqs[7] == 'TO_PR' }">
                                        <input type="radio" name="fulfillorder" id="fulfillorder" onClick="if(confirm('Are you sure you wish to trigger an e-mail?')) parent.location='reqFulfillment.emma?reqID=${reqs[0]}&strainID=${reqs[11]}&fulfill=y';else this.checked=false;" ${disableRadio}/>&nbsp;Fulfill order   
                                    </c:if></c:if>
                                        
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        
                    </c:forEach>
                    
                </tbody>
            </table>
            
            <%-- DATAGRID ENDS --%>
        </c:if>
        <br><br>
        <%-- include consistent footer/navigation --%>
        <%@ include file="footer_inc.html" %>
        <center>Report generated :: <dt:format pattern="yyyy-MM-dd HH:mm"><dt:currentTime/></dt:format></center>
    </body>
</html>
