<%-- 
    Document   : poUpdateInterface
    Created on : 06-Oct-2008, 17:31:22
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
<%--
<c:set var="keyRef" value='${command}'></c:set> ["archiveDAO"]  <c:set var="ArchiveDAO" value='${ArchiveDAO}'></c:set>
keyRef["strainsDAO"]--%>
<c:set var="keyRef" value='${command}'></c:set>

<c:set var="ArchiveDAO" value='${keyRef["archiveDAO"]}'></c:set>
<c:set var="LabsDAO" value='${ArchiveDAO["labsDAO"]}'></c:set>
<c:set var="strainAccess" value='${StrainsDAO["str_access"]}'></c:set>
<c:set var="WebRequestsDAO" value='${keyRef["wrDAO"]}'></c:set>

<spring:bind path="command.*"></spring:bind>

<%
//read in option list from file source
    java.io.BufferedReader inSources = new java.io.BufferedReader(new java.io.FileReader("/nfs/panda/emma/tmp/archlist"));
    String strInSources;
    String strOutSources = "";
    while ((strInSources = inSources.readLine()) != null) {
        strOutSources = strOutSources + strInSources;
    }

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style type="text/css">@import url(../css/emmastyle.css);</style>
        <style type="text/css">@import url(../css/calendar-blue.css);</style>
        <script type="text/javascript" src="../js/calendar.js"></script>
        <script type="text/javascript" src="../js/calendar-en.js"></script>
        <script type="text/javascript" src="../js/calendar-setup.js"></script>
        <script type="text/javascript" src="../js/buttoncontrols.js"></script>
        <script type="text/javascript" src="../js/quickid.js"></script>
        <title>EMMA Project Office Update Interface</title>
    </head>
    <body>
        <span id="loginHeader">PO update for strain ${keyRef["id_str"]} - Logged in as user <c:out value="${fn:toUpperCase(sessionScope.SPRING_SECURITY_LAST_USERNAME)}"/></span>
        <spring:bind path="command.*"> </spring:bind>
        <br />  <br />
        <%-- include consistent navigation --%>
        <%@ include file="navigation_inc.html" %>
        <br />  <br />
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
        <c:if test="${fn:toUpperCase(sessionScope.SPRING_SECURITY_LAST_USERNAME) eq 'SUPER'}"><form><table border="0" width="85%" align="center"><tr><td align="right"><input type="text" name="searchbyid" id="searchbyid" value='' size="4" />&nbsp;&nbsp;<input type=button onclick="reloadNewID();" id="searchByID" value="Load ID" name="searchByID"></td></tr></table></form></c:if>
                        <form:form>
                            <%-- START OF STRAIN DATAGRID --%>
            <table border="0" width="85%" align="center" class="interface">

                <tbody>
                    <tr>
                        <td><b>${keyRef["emma_id"]} (${keyRef["name"]})</b></td>
                        <td>Archive center: ${LabsDAO["code"]} ${LabsDAO["country"]}</td>
                        <td>Status: <c:choose><c:when test="${keyRef['str_status'] == 'ARCHD'}">Archived</c:when><c:when test="${keyRef['str_status'] == 'ACCD'}">Accepted</c:when>
                                <c:when test="${keyRef['str_status'] == 'ARRD'}">Arrived</c:when>
                                <c:when test="${keyRef['str_status'] == 'ARING'}">Archiving</c:when><c:when test="${keyRef['str_status'] == 'EVAL'}">Evaluated</c:when>
                                <c:when test="${keyRef['str_status'] == 'RJCTD'}">Rejected</c:when>
                                <c:otherwise>${keyRef['str_status']}</c:otherwise>
                            </c:choose>
                        </td>
                        <td>Access:  <c:choose><c:when test="${keyRef['str_access'] == 'P'}">Public</c:when><c:when test="${keyRef['str_access'] == 'R'}">Retracted</c:when>
                                <c:when test="${keyRef['str_access'] == 'C'}">Confidential</c:when> 
                                <c:otherwise></c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <%--  <td>Grace period: </td>
                        <td><c:choose><c:when test="${keyRef['str_access'] == 'C'}">
                                    <req:setAttribute name="pattern">yyyy-MM-dd HH:mm:ss.S</req:setAttribute>
                                    <c:set var="date" value='${keyRef["gp_release"]}'></c:set>
                                    <dt:format pattern="dd MMMMM yyyy">
                                        <dt:parse patternId="pattern">
                                            <c:out value="${date}"></c:out>
                                        </dt:parse>
                                    </dt:format>
                                </c:when>
                                <c:otherwise>None, publicly available.</c:otherwise>
                            </c:choose>
                        </td>--%>
                        <td>Repository</td>

                        <td colspan="3">  <spring:bind path="command.archiveDAO.lab_id_labo">
                                <select name="<c:out value='${status.expression}'/>" id="<c:out value='${status.expression}'/>">
                                    <%-- NOW USING LIST GENERATED OVERNIGHT FOR ARCHIVE VALUES<option value="">Please select</option>
                                     <option <c:if test="${ArchiveDAO['lab_id_labo'] eq '1'}" >selected </c:if>value="1">CNR, Consiglio Nazionale delle Ricerche (CNR)</option>
                                     <option <c:if test="${ArchiveDAO['lab_id_labo'] eq '2'}" >selected </c:if>value="2">Institut de Transgenose, INTRAGENE (CNRS)</option>
                                     <option <c:if test="${ArchiveDAO['lab_id_labo'] eq '3'}" >selected </c:if>value="3">Helmholtz Zentrum Muenchen - German Research Center for Environmental Health (GmbH) (GSF)</option>
                                     <option <c:if test="${ArchiveDAO['lab_id_labo'] eq '4'}" >selected </c:if>value="4">MRC, Medical Research Council (MRC)</option>
                                     <option <c:if test="${ArchiveDAO['lab_id_labo'] eq '5'}" >selected </c:if>value="5">Karolinska Institutet (KI)</option>
                                     <option <c:if test="${ArchiveDAO['lab_id_labo'] eq '6'}" >selected </c:if>value="6">FCG, Gulbenkian Institute of Science (FCG)</option>
                                     <option <c:if test="${ArchiveDAO['lab_id_labo'] eq '1961'}" >selected </c:if>value="1961">Wellcome Trust Sanger Institute (SANG)</option>
                                     <option <c:if test="${ArchiveDAO['lab_id_labo'] eq '1962'}" >selected </c:if>value="1962">ICS, Institut Clinique de la Souris (ICS)</option>
                                     <option <c:if test="${ArchiveDAO['lab_id_labo'] eq '1963'}" >selected </c:if>value="1963">CNB-CSIC, Centro Nacional de Biotecnologia (CNB)</option>
                                     <option <c:if test="${ArchiveDAO['lab_id_labo'] eq '2272'}" >selected </c:if>value="2272">B.S.R.C. Alexander Fleming (BSRC)</option>--%>
                                    <c:if test="${not empty ArchiveDAO['lab_id_labo'] }" >
                                        <c:set var="labid" value="${ArchiveDAO['lab_id_labo']}"/>
                                    </c:if>

                                    ${labid}
                                    <c:if test="${not empty ArchiveDAO['lab_id_labo'] }" >
                                        <%

                                            strOutSources = strOutSources.replace("<option value=\"\" selected>", "<option value=\"\">");
                                            strOutSources = strOutSources.replace("<option value=\"" + pageContext.getAttribute("labid").toString() + "\">", "<option value=\"" + pageContext.getAttribute("labid").toString() + "\" selected>");

                                        %>
                                    </c:if>
                                    <%= strOutSources%>
                                </select>
                            </spring:bind>

                        </td>
                    </tr>
                    <tr>
                        <td>Strain Status</td>
                        <td> 
                            <spring:bind path="command.str_status"><select name="<c:out value='${status.expression}'/>">
                                    <option value="">Please select</option>
                                    <option <c:if test="${keyRef['str_status'] eq 'ACCD'}" >selected </c:if>value="ACCD">ACCD</option>
                                    <option <c:if test="${keyRef['str_status'] eq 'RJCTD'}" >selected </c:if>value="RJCTD">RJCTD</option>
                                    <option <c:if test="${keyRef['str_status'] eq 'EVAL'}" >selected </c:if>value="EVAL">EVAL</option>
                                    <option <c:if test="${keyRef['str_status'] eq 'ARRD'}" >selected </c:if>value="ARRD">ARRD</option>
                                    <option <c:if test="${keyRef['str_status'] eq 'ARING'}" >selected </c:if>value="ARING">ARING</option>
                                    <option <c:if test="${keyRef['str_status'] eq 'ARCHD'}" >selected </c:if>value="ARCHD">ARCHD</option>
                                    <option <c:if test="${keyRef['str_status'] eq 'TNA'}" >selected </c:if>value="TNA">TNA</option>
                                </select></spring:bind>
                            </td>
                            <td> Evaluation date: </td>
                            <td><spring:bind path="command.archiveDAO.evaluated"><input type="text"  size="22" name="<c:out value='${status.expression}'/>"   id="<c:out value='${status.expression}'/>" value="<c:out value='${status.value}' />" <%--<c:choose><c:when test="${empty status.value}" >--%>> <img src="../images/cal.gif"  id="EvalDate" border="0">
                                <script type="text/javascript">
                                    Calendar.setup(
                                    {
                                        inputField  : "<c:out value='${status.expression}'></c:out>",
                                        ifFormat    : "%Y-%m-%d",
                                        button      : "EvalDate"     
                                    }
                                );
                                </script>
                                <%-- </c:when></c:choose>--%>
                            </spring:bind></td>
                    </tr>
                    <tr>
                        <td>Edit Strain access:</td>
                        <td><%-- Going to have to hard code strain access values as can only retrieve list from database
            if returned as a hashmap fine to display but not to bind to object --%>

                            <spring:bind path="command.str_access"><select name="<c:out value='${status.expression}'/>"  id="<c:out value='${status.expression}'/>" class="ifSelect">
                                    <option <c:if test="${empty keyRef['str_access']}" >selected </c:if>value=''>Please select</option>
                                    <option <c:if test="${keyRef['str_access'] == 'P'}" >selected </c:if>value="P">(P) Unrestricted access</option>
                                    <option <c:if test="${keyRef['str_access'] == 'C'}" >selected </c:if>value="C">(C) Confidential (2 years grace period)</option>
                                    <option <c:if test="${keyRef['str_access'] == 'R'}" >selected </c:if>value="R">(R) Retracted</option>
                                    <option <c:if test="${keyRef['str_access'] == 'N'}" >selected </c:if>value="N">(N) Not for distribution</option>
                                </select>
                            </spring:bind>

                            <%-- END OF STRAIN DATAGRID   --%>
                        </td>
                        <td>Grace period: </td>
                        <td> <%-- <spring:nestedPath path="command.archiveDAO"> --%>

                            <spring:bind path="command.gp_release"><input type="text"  size="22" name="<c:out value='${status.expression}'/>"   id="<c:out value='${status.expression}'/>" value="<c:out value='${status.value}' />" <%--<c:choose><c:when test="${empty status.value}" >--%>> <img src="../images/cal.gif"  id="GPDate" border="0">
                                <script type="text/javascript">
                                    Calendar.setup(
                                    {
                                        inputField  : "<c:out value='${status.expression}'></c:out>",
                                        ifFormat    : "%Y-%m-%d",
                                        button      : "GPDate"     
                                    }
                                );
                                </script>
                                <%--</c:when></c:choose>--%></spring:bind>

                        </tr>
                        <tr><td colspan="4"> <p style="text-align: center;" class="left">
                                    <input name="update" value="Update" class="button" type="submit">
                                    <input name="Clear updates" value="Clear updates" class="button" type="reset">
                            </td>
                        </tr>
                    </tbody>
                </table>

        </form:form>
        <c:if test="${not empty WebRequestsDAO}">
            <c:forEach var="element" items="${WebRequestsDAO}"  varStatus="row">

                <c:if test="${element.application_type == 'ta_only' || element.application_type == 'ta_or_request'}"><c:set var="theader" value='<table border="0" width="85%" align="center"  border="1" cellpadding="1" cellspacing="1" class="datagrid">
                                                                                                                            <thead><tr><th colspan="4"><b>Applications for Transnational Access</b></th></tr></thead>'/>
                    <c:set var="tfooter" value=" </table>"/>
                </c:if>
            </c:forEach>  


            ${theader}  
            <%--  <table border="0" width="85%" align="center"  border="1" cellpadding="1" cellspacing="1" class="datagrid">
              <thead><tr><th colspan="4"><b>Applications for Transnational Access</b></th></tr></thead>--%>
            <c:forEach var="wr" items="${WebRequestsDAO}"  varStatus="row">
                <c:choose>
                    <c:when test="${wr.application_type == 'ta_only' || wr.application_type == 'ta_or_request'}">
                    <tr class="<c:choose><c:when test="${row.count % 2 == 0}" >evenRow</c:when>
                            <c:otherwise>oddRow</c:otherwise>
                        </c:choose>" valign="top">
                        <td>${wr.id_req}</td>
                        <td> <c:if test="${wr.application_type == 'ta_only'}">TA Only</c:if><c:if test="${wr.application_type == 'ta_or_request'}">TA or Request</c:if></td>
                        <td>${wr.sci_title}.&nbsp;${wr.sci_firstname}&nbsp;${wr.sci_surname}&nbsp;-&nbsp;${wr.con_institution}</td>
                        <td><input name="update" value="Update" class="button" type="button" onClick="parent.location='requestsUpdateInterface.emma?ta=yes&Edit=${wr.id_req}&strainID=${wr.str_id_str}&archID=${wr.str_id_str}'"></td>
                    </tr>
                </c:when>
                <c:otherwise>

                </c:otherwise>
            </c:choose>
        </c:forEach>
        ${tfooter}
    </c:if>
    <br><br>
    <%-- include consistent footer/navigation --%>
    <%@ include file="footer_inc.html" %>
</body>
</html>
