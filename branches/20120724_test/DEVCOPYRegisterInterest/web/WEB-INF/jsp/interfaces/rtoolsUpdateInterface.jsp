<%-- 
    Document   : rtoolsUpdateInterface.emma
    Created on : 05-Dec-2008, 10:12:42
    Author  .RToolsDAO    : phil
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
        <title>EMMA Research Tools Update Interface</title>
    </head>
    <body>
        <span id="loginHeader">Research tools update sub-window - Logged in as user <c:out value="${fn:toUpperCase(sessionScope.SPRING_SECURITY_LAST_USERNAME)}"/></span>
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
            <table border="0" width="85%" align="center" >
                <tr>             
                    <td width="25%" align="undefined" valign="top">Research tools:</td>
                    <td width="25%" align="undefined" valign="top">
                        <spring:bind path="command.rtls_id">
                            <%--<select name="rTools"  id="rTools" class="ifSelect">
                            <option selected value=''>Please select</option>
                            <c:forEach var="rtools" items='${keyRef["rtools"]}'>
                                <c:choose>
                                    <c:when  test="${empty rtools[1]}" >  
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${rtools[0]}">${rtools[2]} (${rtools[1]})</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            </select>--%>
                            
                            
                            <select name="<c:out value='${status.expression}'/>" id="<c:out value='${status.expression}'/>" class="ifSelect">
                                
                                <option <c:if test="${empty command.rtls_id}" >selected </c:if> value=''>Please select</option>
                                <option <c:if test="${command.rtls_id   == 1}" >selected </c:if> value="1">Cre recombinase expressing strain (Cre)</option>
                                <option <c:if test="${command.rtls_id   == 2}" >selected </c:if>value="2">Strain with loxP-flanked sequences (loxP)</option>
                                <option <c:if test="${command.rtls_id   == 3}" >selected </c:if>value="3">FLP recombinase expressing strain (FLP)</option>
                                <option <c:if test="${command.rtls_id   == 4}" >selected </c:if>value="4">Strain with FRT-flanked sequences (FRT)</option>
                                <option <c:if test="${command.rtls_id   == 5}" >selected </c:if>value="5">Strain with Tet expression system (TET)</option>
                                <option <c:if test="${command.rtls_id   == 6}" >selected </c:if>value="6">Lexicon strains from Wellcome Trust (LEX)</option>
                                <option <c:if test="${command.rtls_id   == 7}" >selected </c:if>value="7">Deltagen strains from Wellcome Trust (DEL)</option>
                                <option <c:if test="${command.rtls_id   == 9}" >selected </c:if>value="9">Strains generated from EUCOMM ES cell resource (EUC)</option>
                            </select>
                        </spring:bind>
                        <br />
                    </td>
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
