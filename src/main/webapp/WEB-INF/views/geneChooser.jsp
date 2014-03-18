<%-- 
    Document   : geneChooser
    Created on : Feb 20, 2014, 2:08:30 PM
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
            var urlRoot = "${pageContext.request.contextPath}/curation/geneChooser";

            $(document).ready(function() {
                $('#tabResults > tbody > tr')
                    .on('dragstart', handleDragStart);
                $('#tabResults').dataTable();
            });
            
            function handleDragStart(e) {
                var gene_key = $(this).data('gene_key');
                e.originalEvent.dataTransfer.setData('text/gene', gene_key);
            }
            
            function lookupMgi(mgiReference) {
                window.open("http://www.informatics.jax.org/marker?id=MGI:" + mgiReference, "MgiWindow");
            }
            
        </script>
        
        <title>Gene Chooser</title>
    </head>
    
    <body>
        <div id="divResults" title="Gene Chooser">
        
            <table id="tabResults" style="border: 1px solid black; display: block">
                <thead>
                    <c:choose>
                        <c:when test="${fn:length(genesList) > 0}">
                            <tr style="border: 1px solid black">
                                <th>&nbsp;</th>
                                <th>Gene ID</th>
                                <th>Gene Name</th>
                                <th>Gene Symbol</th>
                                <th>MGI Reference</th>
                            </tr>
                        </c:when>
                    </c:choose>
                </thead>
                <tbody>
                    <c:forEach var="gene" items="${genesList}" varStatus="status">
                        <tr draggable="true" data-gene_key="${gene.gene_key}">
                            <td style="border: 1px solid gray"><img alt="Drag Handle"
                                src="${pageContext.request.contextPath}/images/draghandle.png"
                                height="15" width="15"
                                title="Drag me"
                                draggable="false">
                            </td>
                            <td style="border: 1px solid black">${gene.gene_key}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(gene.name)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(gene.symbol)}</td>
                            <td style="border: 1px solid black">
                                <a href="javascript:lookupMgi('${fn:escapeXml(gene.mgiReference)}');" target="mgiReference">
                                    ${fn:escapeXml(gene.mgiReference)}
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>