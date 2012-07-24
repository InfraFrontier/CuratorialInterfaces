<%-- 
    Document   : peopleUpdateInterface
    Created on : 04-Dec-2008, 10:14:08
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

<spring:bind path="command.*"></spring:bind>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <style type="text/css">@import url(../css/emmastyle.css);</style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EMMA Person Update Interface</title>
    </head>
    <body>
        <span id="loginHeader">Person update sub-window - Logged in as user <c:out value="${fn:toUpperCase(sessionScope.SPRING_SECURITY_LAST_USERNAME)}"/></span>
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
                    <td colspan="4" class="sectHead">
                        <b>Shipping contact</b><br />
                    </td>
                </tr>
                <tr>
                    <td>Title:</td>
                    <td>
                        <spring:bind path="command.title">
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
                        <spring:bind path="command.labsDAO.name">
                            <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                        </spring:bind>
                    </td>
                </tr>
                
                <tr>
                    <td>Firstname:</td>
                    <td>
                        <spring:bind path="command.firstname">
                            <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                        </spring:bind>
                    </td>
                    <td>Department:</td>
                    <td>
                        <spring:bind path="command.labsDAO.dept">
                            <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                        </spring:bind>
                    </td>
                </tr>
                
                <tr>
                    <td>Surname:</td>
                    <td>
                        <spring:bind path="command.surname">
                            <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                        </spring:bind>
                        
                    </td>
                    <td>Address Line 1:</td>
                    <td>
                        <spring:bind path="command.labsDAO.addr_line_1">
                            <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                        </spring:bind>
                    </td>
                </tr>
                
                <tr>
                    <td>Email:</td>
                    <td>
                        <spring:bind path="command.email">
                            <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                        </spring:bind>
                        
                    </td>
                    <td>Address Line 2:</td>
                    <td>
                        <spring:bind path="command.labsDAO.addr_line_2">
                            <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                        </spring:bind>
                    </td>
                </tr>
                
                <tr>
                    <td>Phone:</td>
                    <td>
                        <spring:bind path="command.phone">
                            <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                        </spring:bind>
                    </td>
                    <td>Town:</td>
                    <td><spring:bind path="command.labsDAO.town">
                        <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                        </spring:bind>
                    </td>
                </tr>
                
                <tr>
                    <td>Fax:</td>
                    <td>
                        <spring:bind path="command.fax">
                            <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                        </spring:bind>
                    </td>
                    <td>County/Province:</td>
                    <td>
                        <spring:bind path="command.labsDAO.province">
                            <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                        </spring:bind>
                    </td>
                </tr>
                
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>Country:</td>
                    <td>
                        <spring:bind path="command.labsDAO.country">
                            <input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' />
                        </spring:bind>
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
