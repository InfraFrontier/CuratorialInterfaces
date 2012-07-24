<%-- 
    Document   : bibliosUpdateInterface
    Created on : 08-Dec-2008, 14:24:24
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
        <script type="text/javascript" >
                
            function openWin(ref) {
                window.open("${sessionScope.pubmedUrl}"+ref+"${sessionScope.pubmedUrlTail}", "PubMed Reference Check");
                return false;
            }
        </script>
        <title>EMMA Bibliographic Details Update Interface</title>
    </head>
    <body>
        <span id="loginHeader">Bibliographic details update sub-window - Logged in as user <c:out value="${fn:toUpperCase(sessionScope.SPRING_SECURITY_LAST_USERNAME)}"/></span>
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
                    <td>Journal:</td>
                    <td><spring:bind path="command.journal"><input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' /></spring:bind></td>
                    <td>Volume:</td>
                    <td><spring:bind path="command.volume"><input size="5" type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' /></spring:bind></td>
                </tr>
                
                <tr>
                    <td>Title:</td>
                    <td><spring:bind path="command.title"><textarea  name="<c:out value='${status.expression}'/>"  cols='50' rows='4'>${status.value}</textarea></spring:bind></td>
                    <td>Pages:</td>
                    <td><spring:bind path="command.pages"><input size="5" type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' /></spring:bind></td>
                </tr>    
                
                <tr>
                    <td>Author:</td>
                    <td><spring:bind path="command.author1"><input type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' /></spring:bind></td>
                    <td>Additional authors:</td>
                    <td><spring:bind path="command.author2"><textarea  name="<c:out value='${status.expression}'/>"  cols='50' rows='8'>${status.value}</textarea></spring:bind></td>
                </tr>    
                
                <tr>
                    <td>Year:</td>
                    <td><spring:bind path="command.year"><input size="4" type="text" name="<c:out value='${status.expression}'/>" value='${status.value}' /></spring:bind></td>
                    <td>PubMed identifier</td><%-- .bibliosDAO --%>
                    <td><spring:bind path="command.pubmed_id"><input type="text" id="bib" name="<c:out value='${status.expression}'/>" value='${status.value}' /> <a href="" onClick="openWin(document.forms[0].bib.value)">Test if exists</a></spring:bind></td>
                </tr> 
                
                <tr>
                    <td>Notes:</td>
                    <td><spring:bind path="command.notes"><textarea  name="<c:out value='${status.expression}'/>"  cols='50' rows='8'>${status.value}</textarea></spring:bind></td>
                    <td colspan="2">
                        <p style="text-align: center;" class="left">
                            <input name="update" value="Update" class="button" type="submit">
                            <input name="Clear updates" value="Clear updates" class="button" type="reset">
                        </p>
                    </td>
                </tr> 
            </table>
            <%-- Test for not null id_biblio thus indicating a new addition and if not setting a value for updated field and reset username to current editing user --%>
            <c:if test="${not empty keyRef['id_biblio']}">
                <spring:bind path="command.updated"><input type="hidden" name="<c:out value='${status.expression}'/>" value='Y' /></spring:bind>
            </c:if>
            <spring:bind path="command.username"><input type="hidden" name="<c:out value='${status.expression}'/>" value='${fn:toUpperCase(sessionScope.SPRING_SECURITY_LAST_USERNAME)}' /></spring:bind>
        </form:form>
    </body>
</html>
