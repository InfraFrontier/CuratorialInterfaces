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
<c:set var="bibDetails" value='${returnedOut}'></c:set>
<% String EditStrain = request.getParameter("EditStrain");%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <style type="text/css">@import url(../css/emmastyle.css);</style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>EMMA Bibliographic Details List Interface</title>
    </head>
    <body>
        <span id="loginHeader">Bibliographic details list sub-window - Logged in as user <c:out value="${fn:toUpperCase(sessionScope.SPRING_SECURITY_LAST_USERNAME)}"/></span>
        <br /><br />
        <form:form> 
            <table border="0" width="85%" align="center" >
                
                <c:forEach var="it" begin="0" end="${(sessionScope.bibCount) - 1}" varStatus="status">
                    
                    <c:if test="${it==0}">
                        <c:forEach var="bibRef" items="${bibDetails['BibliosDAO0']}">
                        
                            <tr>
                                <td valign="top">${fn:substring(bibRef["title"],0,60)}... <font class="abrv">(abbrv)</font></td>
                                <td valign="top">${bibRef["journal"]}</td>
                                <td valign="top">${bibRef["author1"]}</td>
                                <td align="center" valign="top"><input type="button" value="Edit" name="${bibRef["id_biblio"]}" onClick="parent.location='bibliosUpdateInterface.emma?action=edit&Single=Y&bibid=${bibRef["id_biblio"]}&EditStrain=<%= EditStrain%>'" /></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    
                    <c:if test="${it==1}">
                        <c:forEach var="bibRef" items="${bibDetails.BibliosDAO1}">
                        
                            <tr>
                                <td valign="top">${fn:substring(bibRef["title"],0,60)}... <font class="abrv">(abbrv)</font></td>
                                <td valign="top">${bibRef["journal"]}</td>
                                <td valign="top">${bibRef["author1"]}</td>
                                <td align="center" valign="top"><input type="button" value="Edit" name="${bibRef["id_biblio"]}" onClick="parent.location='bibliosUpdateInterface.emma?action=edit&Single=Y&bibid=${bibRef["id_biblio"]}&EditStrain=<%= EditStrain%>'" /></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    
                    <c:if test="${it==2}">
                        <c:forEach var="bibRef" items="${bibDetails.BibliosDAO2}">
                        
                            <tr>
                                <td valign="top">${fn:substring(bibRef["title"],0,60)}... <font class="abrv">(abbrv)</font></td>
                                <td valign="top">${bibRef["journal"]}</td>
                                <td valign="top">${bibRef["author1"]}</td>
                                <td align="center" valign="top"><input type="button" value="Edit" name="${bibRef["id_biblio"]}" onClick="parent.location='bibliosUpdateInterface.emma?action=edit&Single=Y&bibid=${bibRef["id_biblio"]}&EditStrain=<%= EditStrain%>'" /></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    
                    <c:if test="${it==3}">
                        <c:forEach var="bibRef" items="${bibDetails.BibliosDAO3}">
                        
                            <tr>
                                <td valign="top">${fn:substring(bibRef["title"],0,60)}... <font class="abrv">(abbrv)</font></td>
                                <td valign="top">${bibRef["journal"]}</td>
                                <td valign="top">${bibRef["author1"]}</td>
                                <td align="center" valign="top"><input type="button" value="Edit" name="${bibRef["id_biblio"]}" onClick="parent.location='bibliosUpdateInterface.emma?action=edit&Single=Y&bibid=${bibRef["id_biblio"]}&EditStrain=<%= EditStrain%>'" /></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    
                    <c:if test="${it==4}">
                        <c:forEach var="bibRef" items="${bibDetails.BibliosDAO4}">
                        
                            <tr>
                                <td valign="top">${fn:substring(bibRef["title"],0,60)}... <font class="abrv">(abbrv)</font></td>
                                <td valign="top">${bibRef["journal"]}</td>
                                <td valign="top">${bibRef["author1"]}</td>
                                <td align="center" valign="top"><input type="button" value="Edit" name="${bibRef["id_biblio"]}" onClick="parent.location='bibliosUpdateInterface.emma?action=edit&Single=Y&bibid=${bibRef["id_biblio"]}&EditStrain=<%= EditStrain%>'" /></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    
                    <c:if test="${it==5}">
                        <c:forEach var="bibRef" items="${bibDetails.BibliosDAO5}">
                            <tr>
                                <td valign="top">${fn:substring(bibRef["title"],0,60)}... <font class="abrv">(abbrv)</font></td>
                                <td valign="top">${bibRef["journal"]}</td>
                                <td valign="top">${bibRef["author1"]}</td>
                                <td align="center" valign="top"><input type="button" value="Edit" name="${bibRef["id_biblio"]}" onClick="parent.location='bibliosUpdateInterface.emma?action=edit&Single=Y&bibid=${bibRef["id_biblio"]}&EditStrain=<%= EditStrain%>'" /></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    
                     <c:if test="${it==6}">
                        <c:forEach var="bibRef" items="${bibDetails.BibliosDAO6}">
                            <tr>
                                <td valign="top">${fn:substring(bibRef["title"],0,60)}... <font class="abrv">(abbrv)</font></td>
                                <td valign="top">${bibRef["journal"]}</td>
                                <td valign="top">${bibRef["author1"]}</td>
                                <td align="center" valign="top"><input type="button" value="Edit" name="${bibRef["id_biblio"]}" onClick="parent.location='bibliosUpdateInterface.emma?action=edit&Single=Y&bibid=${bibRef["id_biblio"]}&EditStrain=<%= EditStrain%>'" /></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    
                     <c:if test="${it==7}">
                        <c:forEach var="bibRef" items="${bibDetails.BibliosDAO7}">
                            <tr>
                                <td valign="top">${fn:substring(bibRef["title"],0,60)}... <font class="abrv">(abbrv)</font></td>
                                <td valign="top">${bibRef["journal"]}</td>
                                <td valign="top">${bibRef["author1"]}</td>
                                <td align="center" valign="top"><input type="button" value="Edit" name="${bibRef["id_biblio"]}" onClick="parent.location='bibliosUpdateInterface.emma?action=edit&Single=Y&bibid=${bibRef["id_biblio"]}&EditStrain=<%= EditStrain%>'" /></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    
                     <c:if test="${it==8}">
                        <c:forEach var="bibRef" items="${bibDetails.BibliosDAO8}">
                            <tr>
                                <td valign="top">${fn:substring(bibRef["title"],0,60)}... <font class="abrv">(abbrv)</font></td>
                                <td valign="top">${bibRef["journal"]}</td>
                                <td valign="top">${bibRef["author1"]}</td>
                                <td align="center" valign="top"><input type="button" value="Edit" name="${bibRef["id_biblio"]}" onClick="parent.location='bibliosUpdateInterface.emma?action=edit&Single=Y&bibid=${bibRef["id_biblio"]}&EditStrain=<%= EditStrain%>'" /></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    
                     <c:if test="${it==9}">
                        <c:forEach var="bibRef" items="${bibDetails.BibliosDAO9}">
                            <tr>
                                <td valign="top">${fn:substring(bibRef["title"],0,60)}... <font class="abrv">(abbrv)</font></td>
                                <td valign="top">${bibRef["journal"]}</td>
                                <td valign="top">${bibRef["author1"]}</td>
                                <td align="center" valign="top"><input type="button" value="Edit" name="${bibRef["id_biblio"]}" onClick="parent.location='bibliosUpdateInterface.emma?action=edit&Single=Y&bibid=${bibRef["id_biblio"]}&EditStrain=<%= EditStrain%>'" /></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    
                     <c:if test="${it==10}">
                        <c:forEach var="bibRef" items="${bibDetails.BibliosDAO10}">
                            <tr>
                                <td valign="top">${fn:substring(bibRef["title"],0,60)}... <font class="abrv">(abbrv)</font></td>
                                <td valign="top">${bibRef["journal"]}</td>
                                <td valign="top">${bibRef["author1"]}</td>
                                <td align="center" valign="top"><input type="button" value="Edit" name="${bibRef["id_biblio"]}" onClick="parent.location='bibliosUpdateInterface.emma?action=edit&Single=Y&bibid=${bibRef["id_biblio"]}&EditStrain=<%= EditStrain%>'" /></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    
            </c:forEach> </table>
        </form:form>
        

    </body>
</html>
