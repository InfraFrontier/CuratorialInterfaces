<%-- 
    Document   : backgroundChooser
    Created on : Mar 07, 2014
    Author     : mrelac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.dataTables.css" />
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.0.js" type="text/javascript" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/js/jquery-ui.min.js" type="text/javascript" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js" type="text/javascript" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/js/json2.js" type="text/javascript" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/js/utils.js" type="text/javascript" charset="UTF-8"></script>

        <script>
            var urlRoot = "${pageContext.request.contextPath}/curation/backgroundChooser";

            $(document).ready(function() {
                $('#tabResults > tbody > tr')
                    .on('dragstart', handleDragStart)
                $('#tabResults').dataTable()
            });
            
            function handleDragStart(e) {
                var background_key = $(this).data('background_key');
                e.originalEvent.dataTransfer.setData('text', background_key);
            }
            
        </script>
        
        <title>Background Chooser</title>
    </head>
    
    <body>
        <div id="divResults" title="Background Chooser">
        
            <table id="tabResults" style="border: 1px solid black; display: block">
                <thead>
                    <c:choose>
                        <c:when test="${fn:length(backgroundsList) > 0}">
                            <tr style="border: 1px solid black">
                                <th>Background ID</th>
                                <th>Background Name</th>
                                <th>Background Symbol</th>
                            </tr>
                        </c:when>
                    </c:choose>
                </thead>
                <tbody>
                    <c:forEach var="background" items="${backgroundsList}" varStatus="status">
                        <tr draggable="true" data-background_key="${background.background_key}">
                            <td style="border: 1px solid black">${background.background_key}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(background.name)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(background.symbol)}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>