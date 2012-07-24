<%-- 
    Document   : cryopreservationHistoryController
    Created on : 18-Sep-2009, 14:28:06
    Author     : phil
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/datetime-1.0" prefix="dt" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/request-1.0" prefix="req" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="keyRef" value='${command}'></c:set>


<spring:bind path="command.*"></spring:bind>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <style type="text/css">@import url(../css/emmastyle.css);</style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EMMA Cryopreservation History Update Interface</title>
    </head>
    <body>
        
        <span id="loginHeader">Cryopreservation history update sub-window - Logged in as user <c:out value="${fn:toUpperCase(sessionScope.SPRING_SECURITY_LAST_USERNAME)}"/></span>
        <br /><br />
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
        <%-- START OF STRAIN DATAGRID --%>
        <form:form>
            <table border="0" width="85%" align="center" >
                <tr>
                    <td colspan="4" class="sectHead"><b> Work done at EMMA archiving centre</b></td>
                    
                </tr>
                <tr>
                    <td valign="top"><b>Breeding:</b> </td>
                    <td colspan="3"><pre><spring:bind path="command.breeding"><textarea name="<c:out value='${status.expression}'/>" value="<c:out value='${status.value}'/>"  rows="16" cols="95"><c:out value='${status.value}'/></textarea></spring:bind></pre></td>
                </tr>
                <tr>
                    <td colspan="4">&nbsp;</td>
                    
                </tr>
                <tr>
                    <td colspan="4"><b>Archiving method used:</b></td>
                    
                </tr>
                <tr>
                    <td  width="25%"><spring:bind path="command.archiving_method_id"><input type="radio"  name="<c:out value='${status.expression}'/>" value="1" <c:if test="${status.value=='1'}">checked</c:if>/></spring:bind></td>
                    <td width="25%">Embryo freezing / IVF</td>
                    <td width="25%">&nbsp;</td>
                    <td width="25%">&nbsp;</td>
                    
                </tr>
                <tr>
                    <td><spring:bind path="command.archiving_method_id"><input type="radio"  name="<c:out value='${status.expression}'/>" value="2" <c:if test="${status.value=='2'}">checked</c:if>/></spring:bind></td>
                    <td>Embryo freezing / natural mating</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>          <td><spring:bind path="command.archiving_method_id"><input type="radio"  name="<c:out value='${status.expression}'/>" value="3"<c:if test="${status.value=='3'}">checked</c:if> /></spring:bind></td>
                    <td>Sperm freezing (classical protocol)</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>          <td><spring:bind path="command.archiving_method_id"><input type="radio"  name="<c:out value='${status.expression}'/>" value="4" <c:if test="${status.value=='4'}">checked</c:if>/></spring:bind></td>
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
                    <td><spring:bind path="command.males"><input type="radio"  name="<c:out value='${status.expression}'/>" value="homozygous" <c:if test="${status.value=='homozygous'}">checked</c:if>/></spring:bind></td>
                    <td>Homozygous</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td><spring:bind path="command.males"><input type="radio"  name="<c:out value='${status.expression}'/>" value="heterozygous" <c:if test="${status.value=='heterozygous'}">checked</c:if>/></spring:bind></td>
                    <td>Heterozygous</td>
                    <td>Male background:&nbsp
                        <spring:bind path="command.male_bg_id">
                            <select name="<c:out value='${status.expression}'/>">
                                <option value="0">Please select...</option>
                                <jsp:include page="background_inc.jsp" flush="true" >
                                    <jsp:param name="bgname" value="${command.male_bg_id}"></jsp:param>
                                </jsp:include>
                                
                            </select>
                    </spring:bind></td>
                    <td>Other - please specify: <spring:bind path="command.male_bg_id">&nbsp;<input type="text" name="<c:out value='${status.expression}'/>_new" size="20" value=""></input></spring:bind></td>
                </tr>
                <tr><td><spring:bind path="command.males"><input type="radio"  name="<c:out value='${status.expression}'/>" value="wild-type" <c:if test="${status.value=='wild-type'}">checked</c:if>/></spring:bind></td>
                    <td>Wild-type</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="4"><div style="margin-left: 30px;"><b>Females</b></div></td>
                    
                </tr>
                <tr> <td><spring:bind path="command.females"><input type="radio"  name="<c:out value='${status.expression}'/>" value="homozygous"<c:if test="${status.value=='homozygous'}">checked</c:if> /></spring:bind></td>
                    <td>Homozygous</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr><td><spring:bind path="command.females"><input type="radio"  name="<c:out value='${status.expression}'/>" value="heterozygous" <c:if test="${status.value=='heterozygous'}">checked</c:if>/></spring:bind></td>
                    <td>Heterozygous</td>
                    <td>Female background:&nbsp;  
                        <spring:bind path="command.female_bg_id">
                            <select name="<c:out value='${status.expression}'/>">
                                <option value="0">Please select...</option>
                                <jsp:include page="background_inc.jsp" flush="true" >
                                    <jsp:param name="bgname" value="${command.female_bg_id}"></jsp:param>
                                </jsp:include>
                            </select>
                    </spring:bind></td>
                    <td>Other - please specify: <spring:bind path="command.female_bg_id">&nbsp;<input type="text" name="<c:out value='${status.expression}'/>_new" size="20" value=""></input></spring:bind></td>
                </tr>
                <tr><td><spring:bind path="command.females"><input type="radio"  name="<c:out value='${status.expression}'/>" value="wild-type" <c:if test="${status.value=='wild-type'}">checked</c:if>/></spring:bind></td>
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
                    <td><spring:bind path="command.embryo_state"><input type="radio"  name="<c:out value='${status.expression}'/>" value="2-cell" <c:if test="${status.value=='2-cell'}">checked</c:if>/></spring:bind></td>
                    <td>2-cell</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td></td>
                </tr>
                <tr>
                    <td><spring:bind path="command.embryo_state"><input type="radio"  name="<c:out value='${status.expression}'/>" value="8-cell" <c:if test="${status.value=='8-cell'}">checked</c:if>/></spring:bind></td>
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
                    <td valign="top"><c:choose><c:when test="${command.strainsDAO.mutant_viable=='not_known'}">Not known</c:when><c:when test="${command.strainsDAO.mutant_viable=='yes'}">Yes</c:when><c:when test="${command.strainsDAO.mutant_viable=='no'}">No</c:when><c:otherwise><%---DO NOTHING--%></c:otherwise></c:choose></td>
                    <td valign="top">Maintained background:</td>
                    <td valign="top">${command.strainsDAO.backgroundDAO.name}</td>
                </tr>
                <tr>
                    <td valign="top">Homozygous mice fertile:</td>
                    <td valign="top"><c:choose><c:when test="${command.strainsDAO.mutant_fertile=='not_known'}">Not known</c:when><c:when test="${command.strainsDAO.mutant_fertile=='yes'}">Yes</c:when><c:when test="${command.strainsDAO.mutant_fertile=='no'}">No</c:when><c:otherwise><%---DO NOTHING--%></c:otherwise></c:choose></td>
                    <td valign="top">Breeding history:</td>
                    <td valign="top">${command.strainsDAO.maintenance}</td>
                </tr>
                <tr>
                    <td valign="top">Homozygous matings required:</td>
                    <td valign="top"><c:choose><c:when test="${command.strainsDAO.require_homozygous=='not_known'}">Not known</c:when><c:when test="${command.strainsDAO.require_homozygous=='yes'}">Yes</c:when><c:when test="${command.strainsDAO.require_homozygous=='no'}">No</c:when><c:otherwise><%---DO NOTHING--%></c:otherwise></c:choose></td>
                    <td valign="top">&nbsp;</td>
                    <td valign="top">&nbsp;</td>
                </tr>
                <tr>
                    <td valign="top">Strain immunocompromised:</td>
                    <td valign="top"><c:choose><c:when test="${command.strainsDAO.immunocompromised=='not_known'}">Not known</c:when><c:when test="${command.strainsDAO.immunocompromised=='yes'}">Yes</c:when><c:when test="${command.strainsDAO.immunocompromised=='no'}">No</c:when><c:otherwise><%---DO NOTHING--%></c:otherwise></c:choose></td>
                    <td valign="top">&nbsp;</td>
                    <td valign="top">&nbsp;</td>
                </tr>
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