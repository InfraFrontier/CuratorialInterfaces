<%--
    Document   : geneManagementList
    Created on : Nov 18, 2013, 6:19:43 PM
    Author     : mrelac
--%>
<!DOCTYPE html>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags" %>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.css" />
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.0.js" type="text/javascript" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/js/jquery-ui.min.js" type="text/javascript" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/js/json2.js" type="text/javascript" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/js/utils.js" type="text/javascript" charset="UTF-8"></script>
  
        <style type="text/css">
            .error {
                font-family: Arial; font-size: 14px; margin-left: 30px; color: red;
            }
            .errorBorder {
                border-collapse: separate;
                border-spacing: 2px;
                border-color: red;
            }
        </style>

        <script>
            
            var geneIds = null;
            var chromosomes = null;
            
            $(document).ready(function() {
            
                populateFilterAutocompletes();
                var resultsFormDisplayAttribute = (${showResultsForm} ? 'block' : 'none');
                $('#divResults').css('display', resultsFormDisplayAttribute)
                $('#applyFilter').click(function() {
                    return validate();
                });

                // Remove filter validation message (jira bug EMMA-545)
                clearFilterErrors();

            });

            function clearFilterErrors() {
                $('#tabFilter tbody .filterErrorTr0').remove();
                $('#geneId').removeClass('errorBorder');
            }

            function validate() {
                // Remove any filter validation messages.
                clearFilterErrors();
                
                var filterIdValue = $('#geneId').val();
                var geneError = ((filterIdValue !== '') && ( ! isInteger(filterIdValue)));
                if (geneError) {
                    $('#tabFilter tbody tr:eq(0)').after('<tr class="filterErrorTr0"><td colspan="4" style="color: red">Please enter an integer.</td></tr>');
                    $('#geneId').addClass('errorBorder');
                    return false;
                }
                
                return true;
            }
            
            function populateFilterAutocompletes() {
                var urlRoot = "${pageContext.request.contextPath}/interfaces/geneManagementList";

                if (geneIds === null)
                    populateGeneIds(urlRoot);
                $("#geneId").autocomplete({
                    source: geneIds,
                    minLength: 1
                });
                
                $("#geneName").autocomplete({
                     source: function( request, response ) {
                        $.ajax({
                            url: urlRoot + "/getGeneNames",
                            dataType: "json",
                            data: { filterTerm: request.term },
                            success: function( data ) {
                                response( $.map( data, function(item) {
                                    return { label: item };
                                }));
                            }
                        });
                    },
                    minLength: 1
                });
                
                $("#geneSymbol").autocomplete({
                     source: function( request, response ) {
                        $.ajax({
                            url: urlRoot + "/getGeneSymbols",
                            dataType: "json",
                            data: { filterTerm: request.term },
                            success: function( data ) {
                                response( $.map( data, function(item) {
                                    return { label: item };
                                }));
                            }
                        });
                    },
                    minLength: 1
                });
                
                if (chromosomes === null)
                    populateChromosomes(urlRoot);
                $("#chromosome").autocomplete({
                    source: chromosomes,
                    minLength: 1
                });
                
                $("#mgiReference").autocomplete({
                     source: function( request, response ) {
                        $.ajax({
                            url: urlRoot + "/getMGIReferences",
                            dataType: "json",
                            data: { filterTerm: request.term },
                            success: function( data ) {
                                response( $.map( data, function(item) {
                                    return { label: item };
                                }));
                            }
                        });
                    },
                    minLength: 1
                }); 
            }
            
            function lookupMGI(id) {
                window.open("http://www.informatics.jax.org/searches/accession_report.cgi?id=MGI:" + id, "MgiWindow");
            }
            
            function lookupEnsembl(id) {
                window.open("http://www.ensembl.org/Mus_musculus/geneview?gene=" + id, "EnsemblWindow");
            }
            
            function populateGeneIds(urlRoot) {
                jQuery.ajax({
                    url: urlRoot + "/getGeneIds"
                  , async: false
                })
                .done(function( data ) {
                    geneIds = data.slice(0);
                });
            }
            
            function populateChromosomes(urlRoot) {
                jQuery.ajax({
                    url: urlRoot + "/getChromosomes"
                  , async: false
                })
                .done(function( data ) {
                    chromosomes = data.slice(0);
                });
            }
            
            function showResults() {
                $('#divResults').css('display', 'block');
            }
        </script>
        <title>Gene Management - list</title>
    </head>
    <body>
        <h2>Gene Management - list</h2>
        <span id="loginHeader">Logged in as user "<sec:authentication property='principal.username'/>"</span>
        
        <br />
        
        <!--
        <form action="geneManagementDetail.emma">
            <input type="hidden" name="action" value="newGene" />
            <input type="submit" value="New" style="margin-left: 430px; margin-bottom: 5px" formaction="${pageContext.request.contextPath}/interfaces/geneManagementDetail" />
            <input type="submit" value="New" style="margin-left: 430px; margin-bottom: 5px" />
        </form>
        -->
                                            
        <form:form modelAttribute="filter" method="get"   >
            
            <br />
            
            <table id="tabFilter" style="border: 1px solid black">
                <thead>
                    <tr><th colspan="4" style="text-align: left">Filter</th></tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="4">
                         <!--   <input type="hidden" name="action" value="applyFilter" />-->
                            <input type="submit" id="applyFilter" value="Go" formaction="${pageContext.request.contextPath}/interfaces/geneManagementList/applyFilter?filter=${filter}" onclick="showResults();" />
                        </td>
                    </tr>
                </tfoot>
                <tbody>
                    <tr>
                        <td><form:label path="geneId">Gene Id:</form:label></td>
                        <td><form:input path="geneId" /></td>
                        <td><form:label path="chromosome">Chromosome:</form:label></td>
                        <td><form:input path="chromosome" /></td>
                    </tr>
                    <tr>
                        <td><form:label path="geneName">Gene name:</form:label></td>
                        <td><form:input path="geneName" /></td>
                        <td><form:label path="mgiReference">MGI reference:</form:label></td>
                        <td><form:input path="mgiReference" /></td>
                    </tr>
                    <tr>
                        <td><form:label path="geneSymbol">Gene symbol:</form:label></td>
                        <td><form:input path="geneSymbol" /></td>
                        <td colspan="2">&nbsp;</td>
                    </tr>
                </tbody>
            </table>
        </form:form>
        
        <div id="divResults">
            <br />

            <hr />

            <label id="labResultsCount">
                <c:choose>
                    <c:when test="${resultsCount > 1}">
                        ${resultsCount} results found.
                    </c:when>
                    <c:when test="${resultsCount > 0}">
                        1 result found.
                    </c:when>
                    <c:when test="${resultsCount == 0}">
                        No results found.
                    </c:when>
                    <c:otherwise>

                    </c:otherwise>
                </c:choose>
            </label>
        
            <br />
            <br />

            <table id="tabResults" style="border: 1px solid black">
                <c:choose>
                    <c:when test="${fn:length(filteredGenesList) > 0}">
                        <tr style="border: 1px solid black">
                            <th>Actions</th>
                            <th>Gene ID</th>
                            <th>Gene Name</th>
                            <th>Gene Symbol</th>
                            <th>Chromosome</th>
                            <th>Species</th>
                            <th>Centimorgan</th>
                            <th>MGI Reference</th>
                            <th>ensembl Reference</th>
                            <th>Promoter</th>
                            <th>Founder Line Number</th>
                            <th>Plasmid Construct</th>
                            <th>Cytoband</th>
                        </tr>
                    </c:when>
                </c:choose>
                <c:forEach var="gene" items="${filteredGenesList}" varStatus="status">
                    <tr>
                        <td style="border: 1px solid black">
                            <input type="hidden" id="alleleCount" name="alleleCount" />
                            
                            <table>
                                <tr>
                                    <td>
                                        <form:form commandName="filter" method="post">
                                            <form:hidden path="geneName" />
                                            <form:hidden path="geneId" />
                                            <form:hidden path="chromosome" />
                                            <form:hidden path="geneSymbol" />
                                            <form:hidden path="mgiReference" />
                                            <input type="hidden" name="id" value="${gene.id_gene}" />
                                            <input type="hidden" name="action" value="editGene" />
                                            <input alt="Edit Gene" type="image" height="15" width="15" title="Edit gene ${gene.id_gene}"
                                               src="${pageContext.request.contextPath}/images/edit.jpg" formaction="${pageContext.request.contextPath}/interfaces/geneManagementDetail" />
                                        </form:form>
                                    </td>
                                    
                                    <c:set var="boundAlleles" value="${gene.alleles}" />
                                    <c:set var="boundAllelesCount" value="${fn:length(boundAlleles)}" />
                                    
                                    <c:set var="boundAlleleIds" value="" />
                                    <c:forEach var="allele" items="${boundAlleles}" varStatus="status">
                                        <c:if test="${status.index == 0}">
                                            <c:set var="boundAlleleIds" value="${allele.id_allel}" scope="page" />
                                        </c:if>
                                        <c:if test="${status.index > 0}">
                                            <c:set var="boundAlleleIds" value="${boundAlleleIds}, ${allele.id_allel}" />
                                        </c:if>
                                    </c:forEach>
                                    <c:choose>
                                        <c:when test="${boundAllelesCount == 1}">
                                            <td>
                                                <input alt="Delete Gene" type="image" height="15" width="15" disabled="disabled"
                                                       src="${pageContext.request.contextPath}/images/delete.jpg" formaction="${pageContext.request.contextPath}/interfaces/geneManagementList?id=${gene.id_gene}&amp;action=deleteGene"
                                                       title="Cannot delete gene ${gene.id_gene} as it is bound to allele ID ${boundAlleleIds}."
                                                       class="ui-state-disabled" />
                                            </td>
                                        </c:when>
                                        <c:when test="${boundAllelesCount > 0}">
                                            <td>
                                                <input alt="Delete Gene" type="image" height="15" width="15" disabled="disabled"
                                                       src="${pageContext.request.contextPath}/images/delete.jpg" formaction="${pageContext.request.contextPath}/interfaces/geneManagementList?id=${gene.id_gene}&amp;action=deleteGene"
                                                       title="Cannot delete gene ${gene.id_gene} as it is bound to allele IDs ${boundAlleleIds}."
                                                       class="ui-state-disabled" />
                                            </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>
                                                <form:form commandName="filter" method="post">
                                                    <form:hidden path="geneName" />
                                                    <form:hidden path="geneId" />
                                                    <form:hidden path="chromosome" />
                                                    <form:hidden path="geneSymbol" />
                                                    <form:hidden path="mgiReference" />
                                                    <input type="hidden" name="id" value="${gene.id_gene}" />
                                                    <input type="hidden" name="action" value="deleteGene" />
                                                    <input alt="Delete Gene" type="image" height="15" width="15" title="Delete gene ${gene.id_gene}"
                                                           src="${pageContext.request.contextPath}/images/delete.jpg" formaction="${pageContext.request.contextPath}/interfaces/geneManagementList" />
                                                </form:form>
                                            </td>
                                        </c:otherwise>
                                    </c:choose>
                                    <td>
                                    <input alt="Edit Bound Allele(s)" type="image" height="15" width="15" title="Edit bound allele(s) ${boundAlleleIds}"
                                       src="${pageContext.request.contextPath}/images/edit.jpg" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td style="border: 1px solid black" valign="top">${gene.id_gene}</td>
                        <td style="border: 1px solid black" valign="top">${gene.name}</td>
                        <td style="border: 1px solid black" valign="top">${gene.symbol}</td>
                        <td style="border: 1px solid black" valign="top">${gene.chromosome}</td>
                        <td style="border: 1px solid black" valign="top">${gene.species}</td>
                        <td style="border: 1px solid black" valign="top">${gene.centimorgan}</td>
                        <td style="border: 1px solid black" valign="top">
                            <a href="javascript:lookupMGI('${gene.mgi_ref}');">
                                ${gene.mgi_ref}
                            </a>
                        </td>
                        <td style="border: 1px solid black" valign="top">
                            <a href="javascript:lookupEnsembl('${gene.ensembl_ref}');">
                                ${gene.ensembl_ref}
                            </a>
                        </td>
                        <td style="border: 1px solid black" valign="top">${gene.promoter}</td>
                        <td style="border: 1px solid black" valign="top">${gene.founder_line_number}</td>
                        <td style="border: 1px solid black" valign="top">${gene.plasmid_construct}</td>
                        <td style="border: 1px solid black" valign="top">${gene.cytoband}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>