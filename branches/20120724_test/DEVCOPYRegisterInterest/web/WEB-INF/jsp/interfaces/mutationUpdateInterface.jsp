<%-- 
    Document   : mutationUpdateInterface
    Created on : 28-Nov-2008, 14:53:29
    Author     : phil   ${keyRef["main_type"]}--${keyRef["sub_type"]}
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

<spring:bind path="command.*"></spring:bind>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <style type="text/css">@import url(../css/emmastyle.css);</style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EMMA Mutation Update Interface</title>
    </head>
    <body>
        <span id="loginHeader">Mutations update sub-window - Logged in as user <c:out value="${fn:toUpperCase(sessionScope.SPRING_SECURITY_LAST_USERNAME)}"/></span>
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
        <form:form>    
            <%-- START OF STRAIN DATAGRID --%>
            <table border="0" width="85%" align="center" >   
                
                <c:choose>
                    <c:when test="${(sessionScope.view) == 'Edit'}">
                        <tr>
                            <td>Main type:</td>
                            <td><spring:bind path="command.main_type">
                                <select  name="<c:out value='${status.expression}'/>">
                                    <option value=""></option>
                                        <c:choose>
                                            <c:when test="${status.value  == 'CH'}" >
                                                <option value="CH" selected>CH</option>
                                            </c:when>
                                            <c:otherwise> 
                                                <option value="CH">CH</option> 
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${status.value  == 'IN'}" >
                                                <option value="IN" selected>IN</option>
                                            </c:when>
                                            <c:otherwise> 
                                                <option value="IN">IN</option> 
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${status.value  == 'SP'}" >
                                                <option value="SP" selected>SP</option>
                                            </c:when>
                                            <c:otherwise> 
                                                <option value="SP">SP</option> 
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${status.value  == 'TG'}" >
                                                <option value="TG" selected>TG</option>
                                            </c:when>
                                            <c:otherwise> 
                                                <option value="TG">TG</option> 
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${status.value  == 'TM'}" >
                                                <option value="TM" selected>TM</option>
                                            </c:when>
                                            <c:otherwise> 
                                                <option value="TM">TM</option> 
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${status.value  == 'XX'}" >
                                                <option value="XX" selected>XX</option>
                                            </c:when>
                                            <c:otherwise> 
                                                <option value="XX">XX</option> 
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </spring:bind> 
                                
                            
                            </td>
                            <td>Sub type: </td>
                            <td><spring:bind path="command.sub_type">
                                <select  name="<c:out value='${status.expression}'/>">
                                    <option value=""></option>
                                        <c:choose>
                                            <c:when test="${status.value  == 'CH'}" >
                                                <option value="CH" selected>CH</option>
                                            </c:when>
                                            <c:otherwise> 
                                                <option value="CH">CH</option> 
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${status.value  == 'CM'}" >
                                                <option value="CM" selected>CM</option>
                                            </c:when>
                                            <c:otherwise> 
                                                <option value="CM">CM</option> 
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${status.value  == 'DEL'}" >
                                                <option value="DEL" selected>DEL</option>
                                            </c:when>
                                            <c:otherwise> 
                                                <option value="DEL">DEL</option> 
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${status.value  == 'DUP'}" >
                                                <option value="DUP" selected>DUP</option>
                                            </c:when>
                                            <c:otherwise> 
                                                <option value="DUP">DUP</option> 
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${status.value  == 'GT'}" >
                                                <option value="GT" selected>GT</option>
                                            </c:when>
                                            <c:otherwise> 
                                                <option value="GT">GT</option> 
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${status.value  == 'KI'}" >
                                                <option value="KI" selected>KI</option>
                                            </c:when>
                                            <c:otherwise> 
                                                <option value="KI">KI</option> 
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${status.value  == 'KO'}" >
                                                <option value="KO" selected>K0</option>
                                            </c:when>
                                            <c:otherwise> 
                                                <option value="KO">KO</option> 
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${status.value  == 'OTH'}" >
                                                <option value="OTH" selected>OTH</option>
                                            </c:when>
                                            <c:otherwise> 
                                                <option value="OTH">OTH</option> 
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${status.value  == 'PM'}" >
                                                <option value="PM" selected>PM</option>
                                            </c:when>
                                            <c:otherwise> 
                                                <option value="PM">PM</option> 
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${status.value  == 'XX'}" >
                                                <option value="XX" selected>XX</option>
                                            </c:when>
                                            <c:otherwise> 
                                                <option value="XX">XX</option> 
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${status.value  == 'Xray'}" >
                                                <option value="Xray" selected>Xray</option>
                                            </c:when>
                                            <c:otherwise> 
                                                <option value="Xray">Xray</option> 
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                            </spring:bind></td>
                        </tr>
                        
                        <tr>
                            <td>Gene symbol:</td><td><spring:bind path="command.allelesDAO.genesDAO.symbol"><input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' size="10" /></spring:bind></td>
                            <td>Gene name:</td><td><spring:bind path="command.allelesDAO.genesDAO.name"><input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' size="50" /></spring:bind></td>                            
                        </tr>
                        <tr>
                            <td>Gene MGI ID:</td><td><spring:bind path="command.allelesDAO.genesDAO.mgi_ref"><input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' size="10" /></spring:bind></td>
                            <td>Gene chromosome:</td><td><spring:bind path="command.allelesDAO.genesDAO.chromosome"><input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' size="10" /></spring:bind></td>                            
                        </tr>
                        <tr>
                            <td>Allele symbol:</td><td><spring:bind path="command.allelesDAO.alls_form"><input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' size="10" /></spring:bind></td>
                            <td>Allele name:</td><td><spring:bind path="command.allelesDAO.name"><input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' size="50" /></spring:bind></td>                            
                        </tr>        
                       <tr>
                            <td>Allele MGI ID:</td><td><spring:bind path="command.allelesDAO.mgi_ref"><input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' size="10" /></spring:bind></td>
                            <td>Dominance:</td><td><spring:bind path="command.dominance"><input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' size="50" /></spring:bind></td>                            
                       </tr>                                
                       <tr>
                       <td>Background:</td>
                       <td>
                           <c:set var="nameParam" value="${command.backgroundDAO.name}"/>
                        <spring:bind path="command.bg_id_bg">
                        <select  name="<c:out value='${status.expression}'/>">
                               <jsp:include page="background_inc.jsp" flush="true" >
                            <jsp:param name="bgname" value="${nameParam}"></jsp:param>
                        </jsp:include>
                           </select>
                       </spring:bind>
                           <%--<c:if test="${not empty command.backgroundDAO}"><spring:bind path="command.backgroundDAO.name"><input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' size="10" /></spring:bind></c:if>--%></td>
                       </tr>
                       <%-- MAIN TYPE=TM  --%>
                       <tr>
                            <td>ES cell line:</td><td><spring:bind path="command.tm_esline"><input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' size="50" /></spring:bind></td>
                       </tr>
                       <%-- MAIN TYPE=CHROMOSOME ANOMALY --%>
                       <tr>
                            <td>Anomaly name:</td><td><spring:bind path="command.ch_ano_name"><input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' size="10" /></spring:bind></td>
                            <td>Anomaly description:</td><td><spring:bind path="command.ch_ano_desc"><input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' size="50" /></spring:bind></td>                            
                       </tr>
                       <%-- MAIN TYPE=TG --%>
                       <tr>
                            <td>Promoter:</td><td><spring:bind path="command.allelesDAO.genesDAO.promoter"><input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' size="50" /></spring:bind></td>
                            <td>Founder line number:</td><td><spring:bind path="command.allelesDAO.genesDAO.founder_line_number"><input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' size="50" /></spring:bind></td>                            
                       </tr>
                       <%-- MAIN TYPE=INDUCED --%>
                       <tr>
                            <td>Mutagen used:</td><td><spring:bind path="command.mu_cause"><input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' size="50" /></spring:bind></td>
                       </tr>                       
                </c:when>
                <c:otherwise>
                    <c:forEach var="muts" items='${keyRef}' varStatus="row">
                        <tr>
                            <td>Strain ID: ${muts["str_id_str"]}</td>
                            <td>Mutation ID: ${muts["mut_id"]}</td>
                            <td>Mutation Main type: ${muts.mutationsDAO.main_type} Mutation sub type: ${muts.mutationsDAO.sub_type}</td>
                            <td><input type="button" value="Edit ID ${muts["mut_id"]}" name="${muts["mut_id"]}" onClick="parent.location='mutationUpdateInterface.emma?Edit=${muts["mut_id"]}&action='" /><br/></td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
                
            </c:choose>
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
