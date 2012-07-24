<%-- 
    Document   : archiveUpdateInterface
    Created on : 10-Sep-2008, 14:30:57
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

<c:set var="keyRef" value='${command}'></c:set>

<c:set var="ArchiveDAO" value='${keyRef["archiveDAO"]}'></c:set>
<c:set var="LabsDAO" value='${ArchiveDAO["labsDAO"]}'></c:set>
<c:set var="syn_strainsDAO" value='${keyRef["syn_strainsDAO"]}'></c:set>

<%--<c:set var="availabilitiesDAO" value='${keyRef.AvailabilitiesStrainsDAO}'></c:set>--%>

<spring:bind path="command.*"></spring:bind>

<%
            String id = request.getParameter("EditArch");
            String reqID = request.getParameter("Edit");

            java.io.BufferedReader inSources = new java.io.BufferedReader(new java.io.FileReader("/nfs/panda/emma/tmp/fslist"));///nfs/panda/emma
///nfs/panda/emma/nfs/panda/emma

            String strInSources;
            String strOutSources = "";

            while ((strInSources = inSources.readLine()) != null) {
                strOutSources = strOutSources + strInSources;
            }
            java.io.BufferedReader inProjects = new java.io.BufferedReader(new java.io.FileReader("/nfs/panda/emma/tmp/plist"));///nfs/panda/emma

            String strInProjects;
            String strOutProjects = "";

            while ((strInProjects = inProjects.readLine()) != null) {
                strOutProjects = strOutProjects + strInProjects;
            }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" >
    <style type="text/css">@import url(../css/emmastyle.css);</style>
    <style type="text/css">@import url(../css/calendar-blue.css);</style>
    <script type="text/javascript" src="../js/calendar.js"></script>
    <script type="text/javascript" src="../js/calendar-en.js"></script>
    <script type="text/javascript" src="../js/calendar-setup.js"></script>
    <script type="text/javascript" src="../js/buttoncontrols.js"></script>
    <script type="text/javascript" src="../js/synonymDiv.js"></script>
    <script type="text/javascript" src="../js/quickid.js"></script>
    <script type="text/javascript" src="../js/popWin.js"></script>
    <script type="text/javascript" src="../js/jquery.js"></script>
    <script type="text/javascript" src="../js/autocomplete/autocomplete.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/autocomplete/autocomplete.css">
    <title>EMMA Archive Update Interface</title>
      <script type="text/javascript">
        $(document).ready(function(){
            $("#archiveDAOmale_bg_id").autocomplete("../ajaxReturn.emma",{ mustMatch:1,max:100,extraParams:{query:"bg",list:"cryo"}});

            $('#archiveDAOmale_bg_id').result(function(event, data, formatted) {
                
                if (data) {

                    document.forms[0].archiveDAOmale_bg_id = formatted; 

                }
            });
 });
    </script>
</head>
<body>
<span id="loginHeader">Archive update for strain <%= id%> - Logged in as user <c:out value="${fn:toUpperCase(sessionScope.SPRING_SECURITY_LAST_USERNAME)}"/></span>        
<spring:bind path="command.*"> </spring:bind>
<br/><br/>
<%-- include consistent navigation --%>
<%@ include file="navigation_inc.html" %>
<%-- PDFURL= ${keyRef["pdfURL"]}--%>
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
<br>
<c:if test="${fn:toUpperCase(sessionScope.SPRING_SECURITY_LAST_USERNAME) eq 'SUPER'}"> <form><table border="0" width="85%" align="center"><tr><td align="right"><input type="text" name="searchbyid" id="searchbyid" value='' size="4" />&nbsp;&nbsp;<input type=button onclick="reloadNewID();" id="searchByID" value="Load ID" name="searchByID">
<%-- <a href='#' onClick="javascript:gmyWin=openWindow('cryopreservationUpdateInterface.emma?cryoArchID=${command.archive_id}',gmyWin);return false;">Cryopreservation History</a>--%></td></tr></table></form></c:if>
<form:form>
    <%-- START OF STRAIN DATAGRID DAO --%>
    <table border="0" width="85%" align="center">
        
        <tbody>
            <tr>
                <td colspan="4" class="sectHead">
                    <b>Strain details</b><br />
                </td>
            </tr>
            <tr>
                <td><b>${keyRef["emma_id"]} (<c:set var="namePost" value='${fn:substringAfter(keyRef["name"],"<")}'></c:set>
                        <c:set var="namePre" value='${fn:substringBefore(namePost,">")}'></c:set>
                        <c:choose>
                            <c:when test="${not empty namePre}"><%--${fn:substringBefore(keyRef["name"],"<")}<sup><c:out value="${namePre}"/></sup>${fn:substringAfter(keyRef["name"],">")}--%>
                                <c:set var="NameStrip" value="${fn:replace(keyRef['name'],'<','<sup||')}"/>
                                <c:set var="NameStrip" value="${ fn:replace(NameStrip,'>','</sup>')}"/>
                                <c:set var="NameStrip" value="${ fn:replace(NameStrip,'||','>')}"/>
                                ${NameStrip}
                            </c:when>
                            <c:otherwise>${keyRef["name"]}</c:otherwise>
                </c:choose>)</b></td>
                <td>&nbsp;</td>
                <td>Strain status:</td>
                <td><%--<c:choose><c:when test="${keyRef['str_status'] == 'ARCHD'}">Archived</c:when><c:when test="${keyRef['str_status'] == 'ACCD'}">Accepted</c:when>
                                <c:when test="${keyRef['str_status'] == 'ARRD'}">Arrived</c:when>
                                <c:when test="${keyRef['str_status'] == 'ARING'}">Archiving</c:when><c:when test="${keyRef['str_status'] == 'EVAL'}">Evaluated</c:when>
                                <c:when test="${keyRef['str_status'] == 'RJCTD'}">Rejected</c:when>
                                <c:otherwise>${keyRef['str_status']}</c:otherwise>
                </c:choose>--%>${keyRef['str_status']}</td> 
            </tr>
            <tr>
                <td>Code Internal:</td>
                <td>
                    <spring:bind path="command.code_internal">
                        <c:out value='${status.value}'/>
                    </spring:bind>
                </td>
                <td>Synonym:</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty syn_strainsDAO}">
                            <c:forEach var="synonym" items='${syn_strainsDAO}' varStatus="row">
                                <c:out value='${synonym["name"]}' /><br/>
                            </c:forEach>
                            <%--<spring:bind path="command.syn_strainsDAO.name">
                                       
                                    </spring:bind>--%>
                        </c:when>
                        <c:otherwise>
                            No synonym 
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td>Strain access: <c:choose><c:when test="${keyRef['str_access'] == 'P'}">Public</c:when><c:when test="${keyRef['str_access'] == 'R'}">Retracted</c:when><c:when test="${keyRef['str_access'] == 'C'}">Confidential</c:when>
                        <c:when test="${keyRef['str_access'] == 'N'}">Not for distribution</c:when><c:otherwise>${keyRef['str_access']}</c:otherwise>
                </c:choose></td>
                <%-- <td>Archive center: ${LabsDAO["code"]} ${LabsDAO["country"]}</td>--%>
                <td>Grace period: <c:choose><c:when test="${keyRef['str_access'] == 'C'}">
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
                </td>
                <td>MTA file:<br />
                    Submission PDF: 
                </td>
                <td><c:choose>
                        <c:when test="${not empty keyRef['mta_file']}" ><a href='${( sessionScope.mtaPath)}${keyRef["mta_file"]}'>
                                <img src="../images/pdf_icon.gif" width="28" height="29" alt="Download MTA file ${( sessionScope.mtaPath)}${keyRef["mta_file"]}" border="0"/>
                            </a>
                        </c:when>
                        <c:otherwise> 
                            N/A
                        </c:otherwise>
                    </c:choose>
                    <br />  
                    <%--
                    <c:choose><c:when test="${(not empty sessionScope.pdfUrl)}"><a href="<c:out value="${(sessionScope.pdfUrl)}"/>"><img src="../images/pdf_icon.gif" align="absmiddle" alt="PDF submission document, click to download" border="0"/></a></c:when><c:otherwise>Not available</c:otherwise></c:choose>
                --%>
                <a href='../pdfView.emma?id=${keyRef["id_str"]}&pdfView=y&type=sub' target="_blank">
                                <img src="../images/pdf_icon.gif" width="28" height="29" alt="Download Request file IN PDF format" border="0" align="absmiddle"/></td>
            </tr>
            <tr>
                <td>Archive center: ${LabsDAO["code"]} ${LabsDAO["country"]}</td>
                <td colspan="3">&nbsp;</td>
            </tr>
            
        </tbody>
    </table>
    
    <center>
        <IFRAME  HEIGHT="200" SRC="cvavail_inc.emma?EditArch=<%=id%>" WIDTH=85%  id="cvavail" FRAMEBORDER=0 scrolling="no">
            
        </IFRAME>
    </center>  
    
    <%-- END OF STRAIN DATAGRID --%>
    
            <%-- START OF ARCHIVE DATAGRID
            
            Returned from StrainsDAO==== ${StrainsDAO["pheno_text"]}
            --%>
    <%--h status: <spring:bind path="command.strainsDAO.health_status"><input type="text" name="<c:out value='${status.expression}'/>"  value="<c:out value='${status.value}' />" /></spring:bind>--%>
    <table border="0" width="85%" align="center">
        <tbody>
        <c:choose>
            <c:when test="${not empty keyRef.projectsDAO}">    
                <c:forEach var="proj" items="${keyRef.projectsDAO}">
                    <c:set var="projectid" value="${proj['project_id']}"/>
                </c:forEach>
            </c:when>
            <c:otherwise> <c:set var="projectid" value=""/></c:otherwise>
        </c:choose>
        <%
            strOutProjects = strOutProjects.replace("<option value=\"0\" selected>", "<option value=\"0\">");
            strOutProjects = strOutProjects.replace("<option value=\"" + pageContext.getAttribute("projectid").toString() + "\">", "<option value=\"" + pageContext.getAttribute("projectid").toString() + "\" selected>");
        %> 
        <c:forEach var="fsource" items="${keyRef.sources_StrainsDAO}">
            <c:set var="sourid" value="${fsource['sour_id']}"/>
            <!-- source id=${sourid} -->
        </c:forEach>
        <%
            strOutSources = strOutSources.replace("<option value=\"0\" selected>", "<option value=\"0\">");
            strOutSources = strOutSources.replace("<option value=\"" + pageContext.getAttribute("sourid").toString() + "\">", "<option value=\"" + pageContext.getAttribute("sourid").toString() + "\" selected>");
        %>
        <tr>
            <td>Project: </td>
            <td>
            <select name="<c:out value='projectID'/>"><%= strOutProjects%>
            </select>
            <td>Funding source: </td>
            <td>
                <select name="sourceID"><%= strOutSources%>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="4">
                &nbsp;
            </td>
        </tr>
        <tr>
            <td colspan="4" class="sectHead">
                <b>Archive details</b><br />
            </td>
        </tr>
        <tr>
            <td>Submitted:&nbsp;</td>
            <td>
                <spring:bind path="command.archiveDAO.submitted">
                    <%-- ALWAYS SET FIELD AS READONLY --%>
                    <%--<input type="text"  size="22" name="<c:out value='${status.expression}'/>"  value="--%><c:out value='${status.value}' /><%--">--%>
                </spring:bind>
            </td>
            <td>Evaluated:</td>
            <td><spring:bind path="command.archiveDAO.evaluated"><c:out value='${status.value}' /></spring:bind></td>
        </tr>
        <tr>
            <td valign="top" colspan="2">Available to order whilst archiving: </td>
            <td valign="top">
            <%--<c:choose>
                                <c:when test="${keyRef['available_to_order']=='yes'}">
                                  ${keyRef["available_to_order"]}
                                </c:when>
                                <c:otherwise> 
                                    <spring:bind path="command.available_to_order">
                                        Select to make strain available<br /> (before archiving is complete)&nbsp;&nbsp;<input type="checkbox" name="<c:out value='${status.expression}'/>" value="yes" />
                                    </spring:bind>
                                </c:otherwise>
            </c:choose>--%>
            Yes: <spring:bind path="command.available_to_order"><input type="radio" <c:if test="${keyRef['available_to_order']=='yes'}">checked="checked" </c:if> name="<c:out value='${status.expression}'/>" value="yes" />&nbsp;&nbsp;No: <input type="radio" <c:if test="${keyRef['available_to_order']=='no' || empty keyRef['available_to_order']}">checked="checked" </c:if> name="<c:out value='${status.expression}'/>" value="no" /></spring:bind>
                                                                       </td>
            <td>&nbsp;</td>
        </tr>
        
        <tr>
            <td>Mice Arrived<%--Received by centre--%>:&nbsp;</td>
            <td><div id="ArrvHelpDesc" class="helpdiv">Mice arrived from submitting scientist OR pups born from rederivation if frozen material was provided <br />(e.g. from Wellcome Trust Knockout mouse resource) OR germline transmission (e.g. in EUCOMM/Eumodic projects).</div>
                <spring:bind path="command.archiveDAO.received">
                    <input onmouseover="ShowContent('ArrvHelpDesc'); return true;"  onmouseout="HideContent('ArrvHelpDesc'); return true;" type="text"  size="22" name="<c:out value='${status.expression}'/>"   id="<c:out value='${status.expression}'/>" value="<c:out value='${status.value}' />" > <img src="../images/cal.gif"  id="ArchDate" border="0">
                    <script type="text/javascript">
                        Calendar.setup(
                        {
                            inputField  : "<c:out value='${status.expression}'></c:out>",
                            ifFormat    : "%Y-%m-%d",
                            button      : "ArchDate"     
                        }
                    );
                    </script>
                    &nbsp;Send e-mail? <select name="sendEmailArch">
                        <option selected value="no">No</option>
                        <option value="yes">Yes</option>
                    </select>
                </spring:bind><img src="../images/email.gif" onmouseover="ShowContent('ArrvMsgDiv'); return true;"  onmouseout="HideContent('ArrvMsgDiv'); return true;" border="0" with="17" height="10" align="absmiddle">
                <div id="ArrvMsgDiv" class="syndiv">
                    <pre><textarea name="${fn:toLowerCase(keyRef['MSGcontentArrv'])}" id="${fn:toLowerCase(keyRef['MSGcontentArrv'])}"  rows="16" cols="95">${keyRef["MSGcontentArrv"]}</textarea></pre>
                </div>
            </td>
            <td class="boxout">Frozen material arrived:</td>
            <td class="boxout">
                
                <spring:bind path="command.archiveDAO.wt_received">
                    <input type="text"  size="22" name="<c:out value='${status.expression}'/>"   id="<c:out value='${status.expression}'/>" value="<c:out value='${status.value}' />" > <img src="../images/cal.gif"  id="wtRec" border="0">
                    <script type="text/javascript">
                        Calendar.setup(
                        {
                            inputField  : "<c:out value='${status.expression}'></c:out>",
                            ifFormat    : "%Y-%m-%d",
                            button      : "wtRec"     
                        }
                    );
                    </script>
                </spring:bind>
            </td>
        </tr>
        <tr> 
            <td valign="top">Archiving started:</td>
            <td valign="top">
                <spring:bind path="command.archiveDAO.freezing_started">
                    <input type="text"  size="22" name="<c:out value='${status.expression}'/>"   id="<c:out value='${status.expression}'/>" value="<c:out value='${status.value}' />" > <img src="../images/cal.gif"  id="FrzDate" border="0">
                    <script type="text/javascript">
                        Calendar.setup(
                        {
                            inputField  : "<c:out value='${status.expression}'></c:out>",
                            ifFormat    : "%Y-%m-%d",
                            button      : "FrzDate"     
                        }
                    );
                    </script>
                    &nbsp;Send e-mail? <select name="sendEmailFreeze">
                        <option selected value="no">No</option>
                        <option value="yes">Yes</option>
                    </select>
                    
                </spring:bind><img src="../images/email.gif" onmouseover="ShowContent('frzMsgDiv'); return true;"  onmouseout="HideContent('frzMsgDiv'); return true;" border="0" with="17" height="10" align="absmiddle">
                <div id="frzMsgDiv" class="syndiv">
                    <pre><textarea name="${fn:toLowerCase(keyRef['MSGcontentFrz'])}" id="${fn:toLowerCase(keyRef['MSGcontentFrz'])}"  rows="16" cols="95">${keyRef["MSGcontentFrz"]}</textarea></pre>
                </div>
            </td>
            <td class="boxout">Rederivation started:</td>
            <td class="boxout">
                
                <spring:bind path="command.archiveDAO.wt_rederiv_started">
                    <input type="text"  size="22" name="<c:out value='${status.expression}'/>"   id="<c:out value='${status.expression}'/>" value="<c:out value='${status.value}' />" > <img src="../images/cal.gif"  id="wtReD" border="0">
                    <script type="text/javascript">
                        Calendar.setup(
                        {
                            inputField  : "<c:out value='${status.expression}'></c:out>",
                            ifFormat    : "%Y-%m-%d",
                            button      : "wtReD"     
                        }
                    );
                    </script>
                </spring:bind>
                
            </td>
        </tr>
        <tr>
            <td valign="top">Archiving completed:&nbsp;</td>
            <td valign="top">
                <spring:bind path="command.archiveDAO.archived">
                    <input type="text"  size="22" name="<c:out value='${status.expression}'/>"   id="<c:out value='${status.expression}'/>" value="<c:out value='${status.value}' />" > <img src="../images/cal.gif"  id="ArchivedDate" border="0">
                    <script type="text/javascript">
                        Calendar.setup(
                        {
                            inputField  : "<c:out value='${status.expression}'></c:out>",
                            ifFormat    : "%Y-%m-%d",
                            button      : "ArchivedDate"     
                        }
                    );
                    </script>
                    &nbsp;Send e-mail? <select name="sendEmailComplete">
                        <option selected value="no">No</option>
                        <option value="yes">Yes</option>
                    </select>
                </spring:bind><img src="../images/email.gif"  onmouseover="ShowContent('CompMsgDiv'); return true;"  onmouseout="HideContent('CompMsgDiv'); return true;" border="0" with="17" height="10" align="absmiddle">
                <div id="CompMsgDiv" class="syndiv">
                    <pre><textarea name="${fn:toLowerCase(keyRef['MSGcontentComp'])}" id="${fn:toLowerCase(keyRef['MSGcontentComp'])}"  rows="16" cols="95">${keyRef["MSGcontentComp"]}</textarea></pre>
                </div>
            </td> 
            
            <td valign="top">Notes:&nbsp</td>     
            <td valign="top"> <spring:bind path="command.archiveDAO.notes">
                <textarea name="<c:out value='${status.expression}'/>" id="<c:out value='${status.expression}'/>"  rows="4" cols="50" maxlength="200"><c:out value='${status.value}' /></textarea>
                </spring:bind>
            </td> 
        </tr>
        <c:choose>
            <c:when test="${ArchiveDAO['lab_id_labo']=='1961' || keyRef['per_id_per'] =='8374'}">
                <tr>
                    <td class="boxoutB">Frozen Sanger material arrived:</td>
                    <td class="boxoutB">
                        <spring:bind path="command.archiveDAO.frozen_sanger_embryos_arrived">
                            <input type="text"  size="22" name="<c:out value='${status.expression}'/>"   id="<c:out value='${status.expression}'/>" value="<c:out value='${status.value}' />" > 
                            <img src="../images/cal.gif"  id="frozensang"  border="0">
                            <script type="text/javascript">
                                Calendar.setup(
                                {
                                    inputField  : "<c:out value='${status.expression}'></c:out>",
                                    ifFormat    : "%Y-%m-%d",
                                    button      : "frozensang"     
                                }
                            );
                            </script>
                        </spring:bind>
                    </td>
                    <td colspan="2">&nbsp;</td>
                </tr>
            </c:when>
            <c:otherwise>
                <spring:bind path="command.archiveDAO.frozen_sanger_embryos_arrived">
                    <input type="hidden"  name="<c:out value='${status.expression}'/>"   id="<c:out value='${status.expression}'/>" value="" >
                </spring:bind>
            </c:otherwise>
        </c:choose>
        <%-- START OF CRYOPRESERVATION HISTORY
            <c:if test="${fn:toUpperCase(sessionScope.SPRING_SECURITY_LAST_USERNAME) eq 'SUPER'}"> 
        
        
        <tr>
            <td class="sectHead" colspan="4">Cryopreservation History</td>
        </tr>
        <tr>
            <td colspan="4"><br /><a href='#' onClick="javascript:gmyWin=openWindow('cryopreservationUpdateInterface.emma?cryoArchID=${command.archive_id}',gmyWin);return false;">Edit cryopreservation history</a>.<br /><br /></td>
        </tr>--%>
        <%-- #####################################################################################--%>
    </table>  
    
    <table border="0" width="85%" align="center" >
        <tr>
            <td class="sectHead" colspan="4"><a href="javascript: void(0)" onclick="javascript:showlayer('cryopres');return false;">+-</a> Cryopreservation History</td>
        </tr>
    </table>
    <br/><br/>
    <table  id="cryopres" style="display: none;"  border="0" width="85%" align="center" >
        <tr>
            <td colspan="4" class="sectHead"><b> Work done at EMMA archiving centre</b></td>
            
        </tr>
        <tr>
            <td valign="top"><b>Breeding:</b> </td>
            <td colspan="3"><pre><spring:bind path="command.archiveDAO.breeding"><textarea name="<c:out value='${status.expression}'/>" value="<c:out value='${status.value}'/>"  rows="16" cols="95"><c:out value='${status.value}'/></textarea></spring:bind></pre></td>
        </tr>
        <tr>
            <td colspan="4">&nbsp;</td>
            
        </tr>
        <tr>
            <td colspan="4"><b>Archiving method used:</b></td>
            
        </tr>
        <tr>
            <td  width="25%"><spring:bind path="command.archiveDAO.archiving_method_id"><input type="radio"  name="<c:out value='${status.expression}'/>" value="1" <c:if test="${status.value=='1'}">checked</c:if>/></spring:bind></td>
            <td width="25%">Embryo freezing / IVF</td>
            <td width="25%">&nbsp;</td>
            <td width="25%">&nbsp;</td>
            
        </tr>
        <tr>
            <td><spring:bind path="command.archiveDAO.archiving_method_id"><input type="radio"  name="<c:out value='${status.expression}'/>" value="2" <c:if test="${status.value=='2'}">checked</c:if>/></spring:bind></td>
            <td>Embryo freezing / natural mating</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>          <td><spring:bind path="command.archiveDAO.archiving_method_id"><input type="radio"  name="<c:out value='${status.expression}'/>" value="3"<c:if test="${status.value=='3'}">checked</c:if> /></spring:bind></td>
            <td>Sperm freezing (classical protocol)</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>          <td><spring:bind path="command.archiveDAO.archiving_method_id"><input type="radio"  name="<c:out value='${status.expression}'/>" value="4" <c:if test="${status.value=='4'}">checked</c:if>/></spring:bind></td>
            <td>Sperm freezing / JAX protocol</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        
        <tr>
            <td colspan="4">&nbsp;</td>
            
        </tr>      
        
        <tr>
            <td colspan="4"><b>Animals used for archiving: </b></td>
            
        </tr>
        <tr>
            <td colspan="4"><div style="margin-left: 30px;"><b>Males</b></div></td>
            
        </tr>
        <tr>
            <td><spring:bind path="command.archiveDAO.males"><input type="radio"  name="<c:out value='${status.expression}'/>" value="homozygous" <c:if test="${status.value=='homozygous'}">checked</c:if>/></spring:bind></td>
            <td>Homozygous</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td><spring:bind path="command.archiveDAO.males"><input type="radio"  name="<c:out value='${status.expression}'/>" value="heterozygous" <c:if test="${status.value=='heterozygous'}">checked</c:if>/></spring:bind></td>
            <td>Heterozygous</td>
            <td>Male background:&nbsp<%--<c:out value='${status.expression}'/>--%>
                <spring:bind path="command.archiveDAO.male_bg_id">
                    <select name="<c:out value='${status.expression}'/>">
                        <option value="0">Please select...</option>
                        <jsp:include page="background_inc.jsp" flush="true" >
                            <jsp:param name="bgname" value="${command.archiveDAO.male_bg_id}"></jsp:param>
                        </jsp:include>
                        
                    </select>
                    <%--<input type="text"  name="archiveDAOmale_bg_id" id="archiveDAOmale_bg_id" value="" size="30"  />--%>
            </spring:bind></td>
            <td>Other - please specify: <spring:bind path="command.archiveDAO.male_bg_id">&nbsp;<input type="text" name="<c:out value='${status.expression}'/>_new" size="20" value=""></input></spring:bind></td>
        </tr>
        <tr><td><spring:bind path="command.archiveDAO.males"><input type="radio"  name="<c:out value='${status.expression}'/>" value="wild-type" <c:if test="${status.value=='wild-type'}">checked</c:if>/></spring:bind></td>
            <td>Wild-type</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td colspan="4"><div style="margin-left: 30px;"><b>Females</b></div></td>
            
        </tr>
        <tr> <td><spring:bind path="command.archiveDAO.females"><input type="radio"  name="<c:out value='${status.expression}'/>" value="homozygous"<c:if test="${status.value=='homozygous'}">checked</c:if> /></spring:bind></td>
            <td>Homozygous</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr><td><spring:bind path="command.archiveDAO.females"><input type="radio"  name="<c:out value='${status.expression}'/>" value="heterozygous" <c:if test="${status.value=='heterozygous'}">checked</c:if>/></spring:bind></td>
            <td>Heterozygous</td>
            <td>Female background:&nbsp;  
                <spring:bind path="command.archiveDAO.female_bg_id">
                    <select name="<c:out value='${status.expression}'/>">
                        <option value="0">Please select...</option>
                        <jsp:include page="background_inc.jsp" flush="true" >
                            <jsp:param name="bgname" value="${command.archiveDAO.female_bg_id}"></jsp:param>
                        </jsp:include>
                    </select>
            </spring:bind></td>
            <td>Other - please specify: <spring:bind path="command.archiveDAO.female_bg_id">&nbsp;<input type="text" name="<c:out value='${status.expression}'/>_new" size="20" value=""></input></spring:bind></td>
        </tr>
        <tr><td><spring:bind path="command.archiveDAO.females"><input type="radio"  name="<c:out value='${status.expression}'/>" value="wild-type" <c:if test="${status.value=='wild-type'}">checked</c:if>/></spring:bind></td>
            <td>Wild-type</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        
        <tr>
            <td colspan="4">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="4"><b>Embryos archived:</b></td>
            
        </tr>
        <tr>
            <td><spring:bind path="command.archiveDAO.embryo_state"><input type="radio"  name="<c:out value='${status.expression}'/>" value="2-cell" <c:if test="${status.value=='2-cell'}">checked</c:if>/></spring:bind></td>
            <td>2-cell</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td></td>
        </tr>
        <tr>
            <td><spring:bind path="command.archiveDAO.embryo_state"><input type="radio"  name="<c:out value='${status.expression}'/>" value="8-cell" <c:if test="${status.value=='8-cell'}">checked</c:if>/></spring:bind></td>
            <td>8-cell</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td colspan="4">&nbsp;</td>
        </tr>                
        <tr>
            <td colspan="4" class="sectHead"><b> Information from submitter</b></td>
        </tr>
        <tr>
            <td colspan="4">(Please check for discrepancies and edit in Strains interface, if necessary, after double-checking with submitter OR inform EMMA curator)</td>
            
        </tr>
        <tr>
            <td valign="top">Homozygous mice viable:</td>
            <td valign="top"><c:choose><c:when test="${command.mutant_viable=='not_known'}">Not known</c:when><c:when test="${command.mutant_viable=='yes'}">Yes</c:when><c:when test="${command.mutant_viable=='no'}">No</c:when><c:otherwise><%---DO NOTHING--%></c:otherwise></c:choose></td>
            <td valign="top">Maintained background:</td>
            <td valign="top">${command.backgroundDAO.name}</td>
        </tr>
        <tr>
            <td valign="top">Homozygous mice fertile:</td>
            <td valign="top"><c:choose><c:when test="${command.mutant_fertile=='not_known'}">Not known</c:when><c:when test="${command.mutant_fertile=='yes'}">Yes</c:when><c:when test="${command.mutant_fertile=='no'}">No</c:when><c:otherwise><%---DO NOTHING--%></c:otherwise></c:choose></td>
            <td valign="top">Breeding history:</td>
            <td valign="top">${command.maintenance}</td>
        </tr>
        <tr>
            <td valign="top">Homozygous matings required:</td>
            <td valign="top"><c:choose><c:when test="${command.require_homozygous=='not_known'}">Not known</c:when><c:when test="${command.require_homozygous=='yes'}">Yes</c:when><c:when test="${command.require_homozygous=='no'}">No</c:when><c:otherwise><%---DO NOTHING--%></c:otherwise></c:choose></td>
            <td valign="top">&nbsp;</td>
            <td valign="top">&nbsp;</td>
        </tr>
        <tr>
            <td valign="top">Strain immunocompromised:</td>
            <td valign="top"><c:choose><c:when test="${command.immunocompromised=='not_known'}">Not known</c:when><c:when test="${command.immunocompromised=='yes'}">Yes</c:when><c:when test="${command.immunocompromised=='no'}">No</c:when><c:otherwise><%---DO NOTHING--%></c:otherwise></c:choose></td>
            <td valign="top">&nbsp;</td>
            <td valign="top">&nbsp;</td>
        </tr>
        </table>     
        <%-- END OF CRYOPRESERVATION HISTORY </c:if> --%>
                    
        <%-- SUBMIT/CLEAR BUTTONS--%>
        <table  border="0" width="85%" align="center" >
        <tr>
            <td colspan="4"> <p style="text-align: center;" class="left">
                    <input name="update" value="Update" class="button" type="submit">
                    <input name="Clear updates" value="Clear updates" class="button" type="reset">
            </p></td>
        </tr>
        </tbody>
    </table>
</form:form>  
<%-- END OF ARCHIVE DATAGRID --%>

<br><br>
<%-- include consistent footer/navigation --%>
<%@ include file="footer_inc.html" %>
</body>
</html>
