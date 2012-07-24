<%--
    Document   : strainUpdateInterface
    Created on : 06-Oct-2008, 12:53:55
    Author     : phil
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/datetime-1.0" prefix="dt" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/request-1.0" prefix="req" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%
            java.io.BufferedReader inSources = new java.io.BufferedReader(new java.io.FileReader("/nfs/panda/emma/tmp/nslist"));
            String strIn;
            String strOut = "";

            while ((strIn = inSources.readLine()) != null) {
                strOut = strOut + strIn;
            }
%>
<spring:bind path="command.*"></spring:bind>
<c:set var="keyRef" value='${command}'></c:set>
<c:set var="rtoolsDAO" value='${keyRef["rtoolsDAO"]}'></c:set>
<c:set var="cvrtoolsDAO" value='${CVRtoolsDAO}'></c:set><%-- TODO     Cannot pull back data atm from linked tables despite records being displayed in log. --%>
<c:set var="peopleDAO" value='${keyRef["peopleDAO"]}'></c:set>
<c:set var="labsDAO" value='${peopleDAO["labsDAO"]}'></c:set>
<c:set var="archiveDAO" value='${keyRef["archiveDAO"]}'></c:set>
<c:set var="Repository" value='${archiveDAO["labsDAO"]}'></c:set>
<c:set var="syn_strainsDAO" value='${keyRef["syn_strainsDAO"]}'></c:set>
<c:set var="sources_StrainsDAO" value='${keyRef["sources_StrainsDAO"]}'></c:set>

<c:set var="mutationsStrainsDAO" value='${keyRef["mutationsStrainsDAO"]}'></c:set>
<c:set var="mutationsDAO" value='${MutationsDAO}'></c:set><%-- TODO     Cannot pull back data atm from linked tables despite records being displayed in log. --%>
<c:set var="residuesDAO" value='${keyRef["residuesDAO"]}'></c:set>

<c:set var="bibliosstrainsDAO" value='${keyRef["bibliosstrainsDAO"]}'></c:set>
<c:set var="backgroundDAO" value='${keyRef["backgroundDAO"]}'></c:set>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style type="text/css">@import url(../css/emmastyle.css);</style>
    <script type="text/javascript" src="../js/popWin.js"></script>
    <script type="text/javascript" src="../js/buttoncontrols.js"></script>
    <script type="text/javascript" src="../js/quickid.js"></script>
    <script type="text/javascript" src="../js/synonymDiv.js"></script>
    <title>EMMA Strain Update Interface</title>
</head>
<body>
<span id="loginHeader">Strains Update Interface for strain ${keyRef["id_str"]} - Logged in as user <c:out value="${fn:toUpperCase(sessionScope.SPRING_SECURITY_LAST_USERNAME)}"/></span>

<br/><br/>
<%-- include consistent navigation --%>
<%@ include file="navigation_inc.html" %>
<center>
    <c:if test="${not empty status.errorMessages}">
        <c:forEach var="error" items="${status.errorMessages}">
            <font color="red"><c:out value="${error}" escapeXml="false" /> </font>
            <br />
        </c:forEach>
    </c:if>
    
    <c:if test="${not empty message}">
        <font color="green"><c:out value="${message}" /></font>
        <c:set var="message" value="" scope="session" />
    </c:if>
</center>
<br />
<c:if test="${fn:toUpperCase(sessionScope.SPRING_SECURITY_LAST_USERNAME) eq 'SUPER'}"><form><table border="0" width="85%" align="center"><tr><td align="right"><input type="text" name="searchbyid" id="searchbyid" value='' size="4" />&nbsp;&nbsp;<input type=button onclick="reloadNewID();" id="searchByID" value="Load ID" name="searchByID"></td></tr></table></form><br/>
<%--<a href='#' onClick="javascript:gmyWin=openWindow('cryopreservationUpdateInterface.emma?cryoArchID=${command.archive_id}',gmyWin);return false;">Cryopreservation History</a>--%></c:if>
<form:form>
    <%-- START OF STRAIN DATAGRID --%>
    
    <table border="0" width="85%" align="center" >
        
        <tr>
            <td>EMMA ID:</td>
            <td>${keyRef["emma_id"]}</td>
            <td>Available to order whilst archiving: </td>
            <td><%--<c:choose><c:when test='${keyRef["available_to_order"] eq "yes"}'>yes${KeyRef["available_to_order"]}</c:when><c:otherwise> <spring:bind path="command.available_to_order"> Select to make strain available <input type="checkbox" name="<c:out value='${status.expression}'/>" value="yes" /></spring:bind></c:otherwise></c:choose>--%>
            Yes: <spring:bind path="command.available_to_order"><input type="radio" <c:if test="${keyRef['available_to_order']=='yes'}">checked="checked" </c:if> name="<c:out value='${status.expression}'/>" value="yes" />&nbsp;&nbsp;No: <input type="radio" <c:if test="${keyRef['available_to_order']=='no' || empty keyRef['available_to_order']}">checked="checked" </c:if> name="<c:out value='${status.expression}'/>" value="no" /></spring:bind></td>
        </tr>
        <tr>
            <td valign="top">International Strain Name:</td>
            <td valign="top">
                <spring:bind path="command.name">
                    <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' size="70" />
                </spring:bind>
            </td>
            <td>Code Internal:</td>
            <td>
                <spring:bind path="command.code_internal">
                    <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' size="50" />
                </spring:bind>
            </td>
        </tr>
        <tr>
            <td valign="top">Synonym:</td>
            <td valign="top">
                <c:choose>
                    <c:when test="${not empty syn_strainsDAO}">
                        <c:forEach var="synonym" items='${syn_strainsDAO}' varStatus="row">
                            <spring:bind path="command.syn_strainsDAO">
                                <input type="text" name="${status.expression}" value='${synonym.name}' size="50"  disabled/>
                            </spring:bind>
                            <br/>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        No synonym 
                    </c:otherwise>
                </c:choose>
            </td>
            
            <td>MTA File:
                <br />
            Submission File: </td>
            <td>
                <c:choose>
                    <c:when test="${not empty keyRef['mta_file']}" ><a href='${( sessionScope.mtaPath)}${keyRef["mta_file"]}'>
                            <img src="../images/pdf_icon.gif" width="28" height="29" alt="Download MTA file ${( sessionScope.mtaPath)}${keyRef["mta_file"]}" border="0" align="absmiddle">
                        </a>
                    </c:when>
                    <c:otherwise>
                        N/A
                    </c:otherwise>
                </c:choose>
                <br />
                <%--<c:choose>
                    <c:when test="${(not empty sessionScope.pdfUrl)}"><a href="<c:out value="${(sessionScope.pdfUrl)}"/>"><img src="../images/pdf_icon.gif" align="absmiddle" alt="PDF submission document, click to download" border="0"></a></c:when><c:otherwise>Not available</c:otherwise>
                </c:choose>--%>
                     <%--<c:if test="${not empty sessionScope.reqID}" >--%><a href='../pdfView.emma?id=${keyRef["id_str"]}&pdfView=y&type=sub' target="_blank">
                                <img src="../images/pdf_icon.gif" width="28" height="29" alt="Download Request file IN PDF format" border="0" align="absmiddle"/>
                            </a><%--</c:if>--%>
            </td>
        </tr>
        <tr>
            <td>Repository:</td>
            <td><c:if test="${not empty Repository['code']}">${Repository["code"]}, ${Repository["country"]}</c:if></td>
            <td>Grace period expiry date:</td>
            <td> <c:choose><c:when test="${keyRef['str_access'] == 'C'}">
                        <req:setAttribute name="pattern">yyyy-MM-dd HH:mm:ss.S</req:setAttribute>
                        <c:set var="date" value='${keyRef["gp_release"]}'></c:set>
                        <dt:format pattern="dd MMMMM yyyy">
                            <dt:parse patternId="pattern">
                                <c:out value="${date}"></c:out>
                            </dt:parse>
                        </dt:format>
                    </c:when>
                    <c:otherwise>None, publicly available.</c:otherwise>
            </c:choose></td>
        </tr>
        <tr>
            <td>Strain status:</td>
            <td><c:choose><c:when test="${keyRef['str_status'] == 'ARCHD'}">Archived</c:when><c:when test="${keyRef['str_status'] == 'ACCD'}">Accepted</c:when>
                    <c:when test="${keyRef['str_status'] == 'ARRD'}">Arrived</c:when>
                    <c:when test="${keyRefDAO['str_status'] == 'ARING'}">Archiving</c:when><c:when test="${keyRef['str_status'] == 'EVAL'}">Evaluated</c:when>
                    <c:when test="${keyRef['str_status'] == 'RJCTD'}">Rejected</c:when>
                    <c:otherwise>${keyRef['str_status']}</c:otherwise>
                </c:choose>
                <%-- &nbsp;&nbsp;&nbsp;Edit status:&nbsp;
                        <spring:bind path="command.str_status"><select name="<c:out value='${status.expression}'/>">
                                <option value="">Please select</option>
                                <option <c:if test="${keyRef['str_status'] eq 'ACCD'}" >selected </c:if>value="ACCD">ACCD</option>
                                <option <c:if test="${keyRef['str_status'] eq 'RJCTD'}" >selected </c:if>value="RJCTD">RJCTD</option>
                                <option <c:if test="${keyRef['str_status'] eq 'EVAL'}" >selected </c:if>value="EVAL">EVAL</option>
                                <option <c:if test="${keyRef['str_status'] eq 'ARRD'}" >selected </c:if>value="ARRD">ARRD</option>
                                <option <c:if test="${keyRef['str_status'] eq 'ARING'}" >selected </c:if>value="ARING">ARING</option>
                                <option <c:if test="${keyRef['str_status'] eq 'ARCHD'}" >selected </c:if>value="ARCHD">ARCHD</option>
                                <option <c:if test="${keyRef['str_status'] eq 'TNA'}" >selected </c:if>value="TNA">TNA</option>
                </select></spring:bind>REMOVED AFTER EMMA IT MEETING 28092010  --%>                  
                
            </td>
            <td>Strain access:</td>
            <td><c:choose><c:when test="${keyRef['str_access'] == 'P'}">Public</c:when><c:when test="${keyRef['str_access'] == 'R'}">Retracted</c:when><c:when test="${keyRef['str_access'] == 'C'}">Confidential</c:when>
                    <c:when test="${keyRef['str_access'] == 'N'}">Not for distribution</c:when><c:otherwise>${keyRef['str_access']}</c:otherwise>
            </c:choose></td>
        </tr>
        <tr>
            <td colspan="4" class="sectHead">
                <b>Producer</b><br />
            </td>
        </tr>
        <tr>
            <td>Title:</td>
            <td>
                <spring:bind path="command.peopleDAO.title">
                    <select  name="<c:out value='${status.expression}'/>">
                        <option value=""></option>
                        <c:choose>
                            <c:when test="${status.value  == 'Mr'}" >
                                <option value="Mr" selected>Mr</option>
                            </c:when>
                            <c:otherwise>
                                <option value="Mr">Mr</option>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${status.value   == 'Mrs'}" >
                                <option value="Mrs" selected>Mrs</option>
                            </c:when>
                            <c:otherwise>
                                <option value="Mrs">Mrs</option>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${status.value   == 'Ms'}" >
                                <option value="Ms" selected>Ms</option>
                            </c:when>
                            <c:otherwise>
                                <option value="Ms">Ms</option>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${status.value   == 'Prof'}" >
                                <option value="Prof" selected>Prof</option>
                            </c:when>
                            <c:otherwise>
                                <option value="Prof">Prof</option>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${status.value   == 'Dr'}" >
                                <option value="Dr" selected>Dr</option>
                            </c:when>
                            <c:otherwise>
                                <option value="Dr">Dr</option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </spring:bind>
            </td>
            <td>Institution:</td>
            <td>
                <spring:bind path="command.peopleDAO.labsDAO.name">
                    <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                </spring:bind>
            </td>
        </tr>
        
        <tr>
            <td>Firstname:</td>
            <td>
                <spring:bind path="command.peopleDAO.firstname">
                    <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                </spring:bind>
            </td>
            <td>Department:</td>
            <td>
                <spring:bind path="command.peopleDAO.labsDAO.dept">
                    <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                </spring:bind>
            </td>
        </tr>
        
        <tr>
            <td>Surname:</td>
            <td>
                <spring:bind path="command.peopleDAO.surname">
                    <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                </spring:bind>
                
            </td>
            <td>Address Line 1:</td>
            <td>
                <spring:bind path="command.peopleDAO.labsDAO.addr_line_1">
                    <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                </spring:bind>
            </td>
        </tr>
        
        <tr>
            <td>Email:</td>
            <td>
                <spring:bind path="command.peopleDAO.email">
                    <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                </spring:bind>
                
            </td>
            <td>Address Line 2:</td>
            <td>
                <spring:bind path="command.peopleDAO.labsDAO.addr_line_2">
                    <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                </spring:bind>
            </td>
        </tr>
        
        <tr>
            <td>Phone:</td>
            <td>
                <spring:bind path="command.peopleDAO.phone">
                    <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                </spring:bind>
            </td>
            <td>Town:</td>
            <td><spring:bind path="command.peopleDAO.labsDAO.town">
                <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                </spring:bind>
            </td>
        </tr>
        
        <tr>
            <td>Fax:</td>
            <td>
                <spring:bind path="command.peopleDAO.fax">
                    <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                </spring:bind>
            </td>
            <td>County/Province:</td>
            <td>
                <spring:bind path="command.peopleDAO.labsDAO.province">
                    <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                </spring:bind>
            </td>
        </tr>
        
        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>Country:</td>
            <td>
                <spring:bind path="command.peopleDAO.labsDAO.country">
                    <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                </spring:bind>
            </td>
        </tr>
        
        <%--- START OF SHIPPING CONTACT INFORMATION --%>
        <%-- TODO NOT PULLING CONTACT DATA NEED TO DEVISE WAY OF DOUBLE LINKING --%>
        <tr>
            <td colspan="4" class="sectHead">
                <b>Shipping contact &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="" onClick="javascript:gmyWin=openWindow('peopleUpdateInterface.emma?contactID=${(sessionScope.con_id)}', gmyWin);return false;">Edit record</a></b><br />
            </td>
        </tr>
        <tr>
            <td>Title:</td>
            <td>
                <%-- <spring:bind path="command.peopleDAO.title">--%>
                <select  name="con_title" disabled>
                    ${(sessionScope.con_title)}
                    <option value=""></option>
                    <c:choose>
                        <c:when test="${sessionScope.con_title  == 'Mr'}" >
                            <option value="Mr" selected>Mr</option>
                        </c:when>
                        <c:otherwise>
                            <option value="Mr">Mr</option>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${sessionScope.con_title   == 'Mrs'}" >
                            <option value="Mrs" selected>Mrs</option>
                        </c:when>
                        <c:otherwise>
                            <option value="Mrs">Mrs</option>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${sessionScope.con_title   == 'Ms'}" >
                            <option value="Ms" selected>Ms</option>
                        </c:when>
                        <c:otherwise>
                            <option value="Ms">Ms</option>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${sessionScope.con_title   == 'Prof'}" >
                            <option value="Prof" selected>Prof</option>
                        </c:when>
                        <c:otherwise>
                            <option value="Prof">Prof</option>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${sessionScope.con_title   == 'Dr'}" >
                            <option value="Dr" selected>Dr</option>
                        </c:when>
                        <c:otherwise>
                            <option value="Dr">Dr</option>
                        </c:otherwise>
                    </c:choose>
                </select>
                
            </td>
            <td>Institution:</td>
            <td>
                
                <input type="text" name="con_institution" value='${sessionScope.con_institution}' disabled/>
                
            </td>
        </tr>
        
        <tr>
            <td>Firstname:</td>
            <td>
                
                <input type="text" name="con_firstname" value='${sessionScope.con_firstname}' disabled/>
                
            </td>
            <td>Department:</td>
            <td>
                
                <input type="text" name="con_department" value='${sessionScope.con_department}' disabled/>
                
            </td>
        </tr>
        
        <tr>
            <td>Surname:</td>
            <td>
                
                <input type="text" name="con_surname>" value='${sessionScope.con_surname}' disabled/>
                
                
            </td>
            <td>Address Line 1:</td>
            <td>
                
                <input type="text" name="con_addr_line_1" value='${sessionScope.con_address1}' disabled/>
                
            </td>
        </tr>
        
        <tr>
            <td>Email:</td>
            <td>
                
                <input type="text" name="con_email" value='${sessionScope.con_email}' disabled/>
                
                
            </td>
            <td>Address Line 2:</td>
            <td>
                
                <input type="text" name="con_addr_line_2" value='${sessionScope.con_address2}' disabled/>
                
            </td>
        </tr>
        <tr>
            <td>Phone:</td>
            <td>
                <input type="text" name="con_phone" value='${sessionScope.con_phone}' disabled/>
            </td>
            <td>Town:</td>
            <td>
                <input type="text" name="con_town" value='${sessionScope.con_town}' disabled/>
            </td>
        </tr>
        <tr>
            <td>Fax:</td>
            <td>
                <input type="text" name="con_fax" value='${sessionScope.con_fax}' disabled/>
            </td>
            <td>County/Province:</td>
            <td>
                <input type="text" name="con_province" value='${sessionScope.con_province}' disabled/>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>Country:</td>
            <td>
                <input type="text" name="con_country" value='${sessionScope.con_country}' disabled/>
            </td>
        </tr>
    </table>
    <%--- END OF SHIPPING CONTACT INFORMATION --%>

    <%-- START OF MUTATION(S ) --%>
    <table border="0" width="85%" align="center" >
        <tr>
            <td class="sectHead" colspan="4"><a href="javascript: void(0)" onclick="javascript:showlayer('mutations');return false;">+-</a> Mutation(s)</td>
        </tr>
    </table>
    
    <table  id="mutations" style="display: none;"  border="0" width="85%" align="center" >
        <tr>
            <td colspan="4"><br /><c:choose><c:when test="${not empty mutationsStrainsDAO}"><a href="" onClick="javascript:gmyWin=openWindow('mutationUpdateInterface.emma?action=edit&EditStrain=${keyRef["id_str"]}', gmyWin);return false;">Edit Mutations</a></c:when><c:otherwise>No mutations recorded. <a href="mutationUpdateInterface.emma?action=add&EditStrain=${keyRef["id_str"]}">Add</a></c:otherwise></c:choose><br /><br /></td>
        </tr>
        <tr>
            <td  colspan="4">Factor Multiple Mutations:
                
                <spring:bind path="command.reporting_count">
                    <select  name="<c:out value='${status.expression}'/>">
                        <option value=""></option>
                        <option value="1.0" <c:if test="${status.value=='1.0'}">selected</c:if>>1</option>
                        <option value="2.0" <c:if test="${status.value=='2.0'}">selected</c:if>>2</option>
                    </select>
                </spring:bind>
            </td>
        </tr>
    </table>
    <%-- END OF MUTATION(S ) --%>

    <%-- START OF Strain genetic description --%>
    <table border="0" width="85%" align="center" >
        <tr>
            <td colspan="4" class="sectHead">
                <a href="javascript: void(0)" onclick="javascript:showlayer('genStrainDesc');return false;">+-</a>  <b>General Strain Description</b>
            </td>
        </tr>
    </table>
    
    <table  id="genStrainDesc" style="display: none;"  border="0" width="85%" align="center" >
        
        <tr>
            <td valign="top">
                Genetic background used to maintain strain:
            </td>
            <td>
                <%-- just to test, replace with a drop down later --%>
                <c:set var="nameParam" value="${backgroundDAO['id_bg']}"/>

                <%--
                bg id=${command['bg_id_bg']}
                bg id from dao=${backgroundDAO['id_bg']}
                
                CODE BELOW IS AN ATTEMPT TO FIX AN ISSUE THAT OCCURS IF
                THE BG_ID_BG IN STRAINSDAO HAS NO CORRESPONDING ENTRY IN THE BACKGROUNDS
                DAO.IF THIS IS THE CASE THEN THE BG_ID_BG GETS WIPED OUT BY THE DROPLIST SELECTION OF 
                'PLEASE SELECT' VALUE 0 OR ''.CODE GIVES A VALUE OF THE STRAINSDAO BG_ID_BG TO THE
                PLEASE SELECT OPTION WHICH GETS CHANGED IF A USER SELECTS A VALUE FROM 
                THIS LIST DURING A GENUINE BACKGROUND CHANGE.
                --%>
                <c:set var="exisitngBg_id_bgFromStrains" value="${command['bg_id_bg']}"/>

                <spring:bind path="command.bg_id_bg">
                    <select  name="<c:out value='${status.expression}'/>">
                        <option value="${exisitngBg_id_bgFromStrains}">Please select...</option>
                        <jsp:include page="background_inc.jsp" flush="true" >
                            <jsp:param name="bgname" value="${command['bg_id_bg']}"></jsp:param>
                        </jsp:include>
                    </select>
                </spring:bind>
            </td>
        </tr>
        
        <tr >
            <td valign="top">
                Phenotype:
            </td>
            <td><spring:bind path="command.pheno_text"><textarea  name="<c:out value='${status.expression}'/>"  cols='50' rows='8'>${status.value}</textarea></spring:bind></td>
            <td valign="top">Genotype:</td>
            <td valign="top"><c:if test="${not empty keyRef['charact_gen']}"><spring:bind path="command.charact_gen"><textarea  name="<c:out value='${status.expression}'/>"  cols='50' rows='8'>${status.value}</textarea></spring:bind></c:if></td>
        </tr>
        <tr>
            <td valign="top">
                Health status:
            </td>
            <td valign="top">
                <spring:bind path="command.health_status">
                    <input type="text" maxlength="5" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                </spring:bind>
            </td>
            <td>&nbsp;<%--Phenotype:--%></td>
            <td>&nbsp;<%--<c:if test="${not empty residuesDAO}"><spring:bind path="command.residuesDAO.char_phenotyping"><textarea  name="<c:out value='${status.expression}'/>"  cols='50' rows='8'>${status.value}</textarea></spring:bind></c:if>--%></td>
        </tr>
        
        <%-- END OF Strain genetic description --%>

                <%-- START OF Relevant bibliographic/database references--%>
                <%--
                <tr>
                    <td valign="top">
                        Human model:
                    </td>
                    <td><spring:bind path="command.human_model_desc"><textarea  name="<c:out value='${status.expression}'/>"  cols='50' rows='8'>${status.value}</textarea></spring:bind></td>
                    <td>
                        &nbsp;
                    </td>
                    <td>
                        &nbsp;
                    </td>
                </tr>
        --%>
    </table>
    <%-- END OF Relevant bibliographic/database references--%>

    <%-- Start of Breeding procedures/sanitary status of strain --%>
    <table border="0" width="85%" align="center" >
        <tr>
            <td colspan="4" class="sectHead"><a href="javascript: void(0)" onclick="javascript:showlayer('strainBreed');return false;">+-</a> <b>Strain breeding procedures and sanitary status.</b></td>
        </tr>
    </table>
    
    <table  id="strainBreed" style="display: none;"  border="0" width="85%" align="center" >
        <tr>
            <td  valign="top">Breeding History</td>
            <td><spring:bind path="command.maintenance"><textarea  name="<c:out value='${status.expression}'/>"  cols='50' rows='8'>${status.value}</textarea></spring:bind></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>Homozygous mice viable: </td>
            <td><spring:bind path="command.mutant_viable">
            <input <c:if test="${status.value=='yes'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="yes" type="radio"> Yes
                <input <c:if test="${status.value=='no'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="no" type="radio"> No
                <input <c:if test="${status.value=='not_known'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="not_known" type="radio"> Not known
                </spring:bind></td>
            <td  valign="top">Homozygous mice fertile:</td>
            <td><spring:bind path="command.mutant_fertile">
            <input <c:if test="${status.value=='yes'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="yes" type="radio"> Yes
                <input <c:if test="${status.value=='no'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="no" type="radio"> No
                <input <c:if test="${status.value=='not_known'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="not_known" type="radio"> Not known
                </spring:bind></td>
        </tr>
        <tr>
            <td>Homozygous mating:</td>
            <td><spring:bind path="command.require_homozygous">
            <input <c:if test="${status.value=='yes'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="yes" type="radio"> Yes
                <input <c:if test="${status.value=='no'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="no" type="radio"> No
                <input <c:if test="${status.value=='not_known'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="not_known" type="radio"> Not known
                </spring:bind></td>
            <td>Immunocompromised:</td>
            <td><spring:bind path="command.immunocompromised">
            <input <c:if test="${status.value=='yes'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="yes" type="radio"> Yes
                <input <c:if test="${status.value=='no'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="no" type="radio"> No
                <input <c:if test="${status.value=='not_known'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="not_known" type="radio"> Not known
                </spring:bind></td>
        </tr>
        <tr>
            <td valign="top">Strain Sanitary Status:</td>
            <td  valign="top"><c:if test="${not empty residuesDAO}"><spring:bind path="command.residuesDAO.current_sanitary_status"><textarea  name="<c:out value='${status.expression}'/>"  cols='50' rows='8'>${status.value}</textarea></spring:bind></c:if></td>
            <td>Animal Husbandry:</td>
            <td><c:if test="${not empty residuesDAO}"><spring:bind path="command.residuesDAO.animal_husbandry"><textarea  name="<c:out value='${status.expression}'/>"  cols='50' rows='8'>${status.value}</textarea></spring:bind></c:if></td>
        </tr>
    </table>
    <%-- End of Breeding procedures/sanitary status of strain --%>
            
            
                        <%-- START OF CRYOPRESERVATION HISTORY NOT NEEDED IN THIS INTERFACE
            <c:if test="${fn:toUpperCase(sessionScope.SPRING_SECURITY_LAST_USERNAME) eq 'SUPER'}">
                <table border="0" width="85%" align="center" >
                    <tr>
                        <td class="sectHead" colspan="4"><a href="javascript: void(0)" onclick="javascript:showlayer('cryopres');return false;">+-</a> Cryopreservation History</td>
                    </tr>
                </table>
                
                <table  id="cryopres" style="display: none;"  border="0" width="85%" align="center" >
                    <tr>
                        <td colspan="4"><br /><a href='#' onClick="javascript:gmyWin=openWindow('cryopreservationUpdateInterface.emma?cryoArchID=${command.archive_id}',gmyWin);return false;">Edit cryopreservation history</a>.<br /><br /></td>
                    </tr>
                </table>
            </c:if>
            END OF CRYOPRESERVATION HISTORY --%>
            

    <%-- Start of strain characterisation --%>
    <table border="0" width="85%" align="center" >
        <tr>
            <td colspan="4" class="sectHead"><a href="javascript: void(0)" onclick="javascript:showlayer('strainCharac');return false;">+-</a> <b>Strain characterisation.</b></td>
        </tr>
    </table>
    <table  id="strainCharac" style="display: none;"  border="0" width="85%" align="center" >
        <tr>
            <td colspan="2">By genotyping:</td>
            <td colspan="2">
                <c:if test="${not empty residuesDAO}"><spring:bind path="command.residuesDAO.char_genotyping"><textarea  name="<c:out value='${status.expression}'/>"  cols='70' rows='4'>${status.value}</textarea></spring:bind></c:if>
            </td>
        </tr>
        <tr>
            <td  colspan="2">By phenotyping:</ td>
            <td colspan="2"><c:if test="${not empty residuesDAO}"><spring:bind path="command.residuesDAO.char_phenotyping"><textarea  name="<c:out value='${status.expression}'/>"  cols='70' rows='4'>${status.value}</textarea></spring:bind></c:if>
            </td>
        </tr>
        <tr>
            <td  colspan="2">By other means:</td>
            <td colspan="2"><c:if test="${not empty residuesDAO}"><spring:bind path="command.residuesDAO.char_other"><textarea  name="<c:out value='${status.expression}'/>"  cols='70' rows='4'>${status.value}</textarea></spring:bind></c:if>
            </td>
        </tr>
    </table>
    <%-- End of strain characterisation --%>

    <%-- Start of scientific interest --%>
    <table border="0" width="85%" align="center" >
        <tr>
            <td colspan="4" class="sectHead"><a href="javascript: void(0)" onclick="javascript:showlayer('sciInt');return false;">+-</a> <b>Scientific interest.</b></td>
        </tr>
    </table>
    
    <table  id="sciInt" style="display: none;"  border="0" width="85%" align="center" >
        <tr>
            <td>
                Models human condition:
            </td>
            <td>
            <spring:bind path="command.human_model">
                <input <c:if test="${status.value=='yes'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="yes" type="radio"> Yes
                    <input <c:if test="${status.value=='no'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="no" type="radio"> No
                    <input <c:if test="${status.value=='not_known'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="not_known" type="radio"> Not known
                </spring:bind>
                </td>
            <td>
                OMIM ID(s):
            </td>
            <td>
                <c:if test="${not empty residuesDAO}"><spring:bind path="command.residuesDAO.omim_ids"><input type="text" size="50" name="<c:out value='${status.expression}'/>" value='${status.value}' /></spring:bind></c:if><c:if test="${empty residuesDAO}">None</c:if>
            </td>
        </tr>
        <tr>
            <td valign="top">
                Human condition description:
            </td>
            <td valign="top">
                <spring:bind path="command.human_model_desc"><textarea  name="<c:out value='${status.expression}'/>"  cols='50' rows='4'>${status.value}</textarea></spring:bind>
            </td>
            <td valign="top">
                Research Tools
            </td>
            <td valign="top"><c:choose><c:when  test="${(sessionScope.rtCount) >= 1}"><a href="" onClick="javascript:gmyWin=openWindow('rtoolsUpdateInterface.emma?action=edit&EditStrain=${keyRef["id_str"]}', gmyWin);return false;">Edit research tools</a></c:when><c:otherwise>No research tools recorded. <a href="" onClick="javascript:gmyWin=openWindow('rtoolsUpdateInterface.emma?action=add&EditStrain=${keyRef["id_str"]}', gmyWin);return false;">Add</a></c:otherwise></c:choose>
            </td>
        </tr>
    </table>
    <%-- End of scientific interest --%>

    <%-- Start of bibliographic details --%>
    <table border="0" width="85%" align="center" >
        <tr>
            <td colspan="4" class="sectHead"><a href="javascript: void(0)" onclick="javascript:showlayer('bibDiv');return false;">+-</a> <b>Bibliographic details.</b></td>
        </tr>
    </table>
    
    <table  id="bibDiv" style="display: none;"  border="0" width="85%" align="center" >
        <tr>
            <td valign="top" colspan="4"><c:choose><c:when test="${(sessionScope.bibCount) >= 1}"><a href="javascript:;" onClick="javascript:gmyWin=openWindow('biblios<c:choose><c:when test="${(sessionScope.bibCount) > 1}">List</c:when><c:otherwise>Update</c:otherwise></c:choose>Interface.emma?action=edit&EditStrain=${keyRef["id_str"]}', gmyWin);return false;">Edit bibliographic details</a></c:when><c:otherwise>No bibliographic references recorded. <a href="" onClick="javascript:gmyWin=openWindow('bibliosUpdateInterface.emma?action=add&EditStrain=${keyRef["id_str"]}', gmyWin);return false;">Add</a></c:otherwise></c:choose>
            </td>
        </tr>
    </table>
    <%-- End of bibliographic details --%>


    <%-- Start of additional information --%>
    <table border="0" width="85%" align="center" >
        <tr>
            <td colspan="4" class="sectHead"><a href="javascript: void(0)" onclick="javascript:showlayer('additionalInfo');return false;">+-</a> <b>Additional information</b></td>
        </tr>
    </table>
    
    <table  id="additionalInfo" style="display: none;"  border="0" width="85%" align="center" >
        <c:if test="${ not empty command.residuesDAO}" >
            <tr>
                <td>6 month requests: </td>
                <td>
                    <spring:bind path="command.residuesDAO.number_of_requests"><input type="text" size="5" name="<c:out value='${status.expression}'/>" value='${status.value}' /></spring:bind>
                </td>
                
                <td>Deposited with other institution/company: </td>
                <td>
                
                <spring:bind path="command.residuesDAO.deposited_elsewhere">
                    <input <c:if test="${status.value=='yes'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="yes" type="radio"> Yes
                        <input <c:if test="${status.value=='no'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="no" type="radio"> No
                        <input <c:if test="${status.value=='not_known'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="not_known" type="radio"> Not known
                    </spring:bind>
                    
                    </td>
            </tr>
            <tr>
                <td>Other labs. producing: </td>
                <td>
                    <spring:bind path="command.residuesDAO.other_labos">
                        <input <c:if test="${status.value=='yes'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="yes" type="radio"> Yes
                            <input <c:if test="${status.value=='no'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="no" type="radio"> No
                            <input <c:if test="${status.value=='not_known'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="not_known" type="radio"> Not known
                        </spring:bind>
                        
                    <td>Intellectual property rights: </td>
                <td>
                
                <spring:bind path="command.residuesDAO.ip_rights">
                    <input <c:if test="${status.value=='yes'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="yes" type="radio"> Yes
                        <input <c:if test="${status.value=='no'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="no" type="radio"> No
                        <input <c:if test="${status.value=='not_known'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="not_known" type="radio"> Not known
                    </spring:bind>
                    
                    </td>
            </tr>
            
            
            <tr>
                <td colspan="4">IP description:
                <spring:bind path="command.residuesDAO.ipr_description"><input type="text" size="60" name="<c:out value='${status.expression}'/>" value='${status.value}' /></spring:bind></td>
            </tr>
            
            
            </tr>
            <tr>
                <td>Mutant construction techniques used:</td>
                
                <td colspan="3"><spring:bind path="command.residuesDAO.crelox"><input <c:if test="${status.value=='yes'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="<c:out value='${status.value}'/>" type="checkbox"></spring:bind> Cre recombinase-loxP technology
                    <spring:bind path="command.residuesDAO.flp"><input <c:if test="${status.value=='yes'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="<c:out value='${status.value}'/>" type="checkbox"></spring:bind> FLP recombinase technology
                    <spring:bind path="command.residuesDAO.tet"><input <c:if test="${status.value=='yes'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="<c:out value='${status.value}'/>" type="checkbox"></spring:bind> TET-system technology
                    
                    </td>
            </tr>
            <tr>
                <td>Estimated shipping: </td>
                <td>Month: <spring:bind path="command.residuesDAO.when_mice_month"><input type="text" size="2" name="<c:out value='${status.expression}'/>" value="<c:out value='${status.value}'/>"></spring:bind>
                Year: <spring:bind path="command.residuesDAO.when_mice_year"><input type="text" size="4" name="<c:out value='${status.expression}'/>" value="<c:out value='${status.value}'/>"></spring:bind></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td colspan="4">Males/Females <b>&#9792;&#9794;</b>:
                    
                    <spring:bind path="command.residuesDAO.when_how_many_males">
                        <input type="text" size="6" name="<c:out value='${status.expression}'/>" value="<c:out value='${status.value}'/>">
                    </spring:bind>
                    <spring:bind path="command.residuesDAO.when_how_many_females">
                        <input type="text" size="6" name="<c:out value='${status.expression}'/>" value="<c:out value='${status.value}'/>">
                    </spring:bind>
                </td>
            </tr>
            <tr>
                <td valign="top">Delayed release: </td>
                <td valign="top"><c:if test="${not empty residuesDAO && not empty residuesDAO['delayed_wanted']}"><spring:bind path="command.residuesDAO.delayed_wanted">
                <input <c:if test="${status.value=='yes'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="yes" type="radio"> Yes
                    <input <c:if test="${status.value=='no'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="no" type="radio"> No</spring:bind></c:if></td>
                <td valign="top"><c:if test="${not empty residuesDAO && residuesDAO['delayed_wanted'] != 'no'}">Delay reason: </c:if></td>
                <td valign="top"><c:if test="${not empty residuesDAO && residuesDAO['delayed_wanted'] != 'no'}"><spring:bind path="command.residuesDAO.delayed_description"><textarea  name="<c:out value='${status.expression}'/>"  cols='60' rows='4'>${status.value}</textarea></spring:bind></c:if></td>
            </tr>
            
        </c:if>
        <%-- End of additional information --%>
        <tr>
            <td>MTA file: </td>
            <td>
                <spring:bind path="command.mta_file"><input type="text" size="50" name="<c:out value='${status.expression}'/>" value='${status.value}' /></spring:bind>
            </td>
            <td valign="top">MGI ID:</td>
            <td valign="top"><%--<c:if test="${not empty keyRef['mgi_ref']}">--%><spring:bind path="command.mgi_ref"><input type='text'  name="<c:out value='${status.expression}'/>"  value='${status.value}' size='10' maxsize='10'/></spring:bind><%--</c:if>--%></td>
        </tr>
        <tr>
        <td>Exclusive owner:</td>
        <td>
        <spring:bind path="command.exclusive_owner">
            <input <c:if test="${status.value=='yes'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="yes" type="radio"> Yes
                <input <c:if test="${status.value=='no'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="no" type="radio"> No
                <input <c:if test="${status.value=='not_known'}">checked="checked"</c:if> name="<c:out value='${status.expression}'/>" value="not_known" type="radio"> Not known
            </spring:bind></td>
        <td><%--<c:if test="${residues.exclusive_owner == 'no'}">--%>Exclusive owner details:<%--</c:if>--%></td>
        <td><%--<c:if test="${residues.exclusive_owner == 'no'}">--%>
            <spring:bind path="command.ex_owner_description"><textarea  name="<c:out value='${status.expression}'/>"  cols='80' rows='4'>${status.value}</textarea></spring:bind>
        <%--</c:if>--%></td>
        
        
        
        <tr>
            <td valign="top">Additional Owner:</td>
            <td valign="top">
                <%--
                        ALTER TABLE `strains` ADD `additional_owner` VARCHAR( 250 ) NULL DEFAULT NULL AFTER `exclusive_owner` ;--%>
            <%-- <c:if test="${not empty keyRef['additional_owner']}">--%><spring:bind path="command.additional_owner"><textarea  name="<c:out value='${status.expression}'/>"  cols='50' rows='8' maxlength="250">${status.value}</textarea></spring:bind><%--</c:if>--%></td>
            <td valign="top">Name status:</td>
            <td valign="top">
                <c:if test="${not empty keyRef['name_status']}">
                    <c:set var="name_status" value="${keyRef['name_status']}"/>
                </c:if>
                <c:if test="${empty keyRef['name_status']}">
                    <c:set var="name_status" value=""/>
                </c:if>
                <%
            strOut = strOut.replace("<option value=\"\" selected>", "<option value=\"\">");
            strOut = strOut.replace("<option value=\"" + pageContext.getAttribute("name_status").toString() + "\">", "<option value=\"" + pageContext.getAttribute("name_status").toString() + "\" selected>");
                %> 
                <spring:bind path="command.name_status">
                    <select name="<c:out value='${status.expression}'/>">
                        <%=strOut%>
                    </select>
                </spring:bind>
                
            </td>
        </tr>
    </table>
    <table border="0" width="85%" align="center" >
        <tr>
            <td colspan="4">
                <p style="text-align: center;" class="left">
                    <input name="update" value="Update" class="button" type="submit">
                    <input name="Clear updates" value="Clear updates" class="button" type="reset">
                </p>
            </td>
        </tr>
    </table>
</form:form>
<br><br>
<%-- include consistent footer/navigation --%>
<%@ include file="footer_inc.html" %>
</body>
</html>
<%--

<tr>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>


--%>