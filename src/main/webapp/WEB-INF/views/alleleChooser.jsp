<%-- 
    Document   : alleleChooser
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
            var urlRoot = "${pageContext.request.contextPath}/curation/alleleChooser";

            $(document).ready(function() {
                $('#tabResults > tbody > tr')
                    .on('dragstart', handleDragStart);
                $('#tabResults').dataTable();
            });
            
            function lookupMgi(mgiReference) {
                window.open("http://www.informatics.jax.org/marker?id=MGI:" + mgiReference, "MgiWindow");
            }
            
            function handleDragStart(e) {
                var allele_key = $(this).data('allele_key');
                e.originalEvent.dataTransfer.setData('text/allele', allele_key);
            }
            
        </script>
        
        <title>Allele Chooser</title>
    </head>
    
    <body>
        <div id="divResults" title="Allele Chooser">
        
            <table id="tabResults" style="border: 1px solid black; display: block">
                <thead>
                    <c:choose>
                        <c:when test="${fn:length(allelesList) > 0}">
                            <tr style="border: 1px solid black">
                                <th>&nbsp;</th>
                                <th>Allele ID</th>
                                <th>Allele Name</th>
                                <th>Allele Symbol</th>
                                <th>MGI Reference</th>
                            </tr>
                        </c:when>
                    </c:choose>
                </thead>
                <tbody>
                    <c:forEach var="allele" items="${allelesList}" varStatus="status">
                        <tr draggable="true" data-allele_key="${allele.allele_key}">
                            <td style="border: 1px solid gray"><img alt="Drag Handle"
                                src="${pageContext.request.contextPath}/images/draghandle.png"
                                height="15" width="15"
                                title="Drag me"
                                draggable="false">
                            </td>
                            <td style="border: 1px solid black">${allele.allele_key}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(allele.name)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(allele.symbol)}</td>
                            <td style="border: 1px solid black">
                                <a href="javascript:lookupMgi('${fn:escapeXml(allele.mgiReference)}');" target="mgiReference">
                                    ${fn:escapeXml(allele.mgiReference)}
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>