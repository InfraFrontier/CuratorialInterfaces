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
        <link rel="stylesheet" href="../css/jquery-ui.1.10.3.min.css" />
        <script src="../js/jquery-1.10.2.min.js" type="text/javascript" charset="UTF-8"></script>
        <script src="../js/jquery-ui.1.10.3.min.js" type="text/javascript" charset="UTF-8"></script>
        <script src="../js/json2.js" type="text/javascript" charset="UTF-8"></script>
        <script src="../js/utils.js" type="text/javascript" charset="UTF-8"></script>
  
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
            $(document).ready(function() {
                populateFilterAutocompletes();
                $('#applyFilter').click(function() {
                    return validate();
                });
                
                var showResultsForm = ${hidShowResultsForm};
                var divResultsDisplayAttribute = (showResultsForm === 0 ? 'none' : 'block');
                $('#divResults').css('display', divResultsDisplayAttribute);

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
            
            // This function generates thousands of rows. Keep it at the bottom of the function list for easier debugging.
            function populateFilterAutocompletes() {
                var geneIds = new Array();
                var geneNames = new Array();
                var geneSymbols = new Array();
                var chromosomes = new Array();
                var mgiReferences = new Array();

                <c:forEach var="geneId"  items="${options['geneIds']}" varStatus="status">
                    geneIds[${status.index}] = ${geneId};
                </c:forEach>
                <c:forEach var="geneName"  items="${options['geneNames']}" varStatus="status">
                    geneNames[${status.index}] = ${geneName};
                </c:forEach>
                <c:forEach var="geneSymbol"  items="${options['geneSymbols']}" varStatus="status">
                    geneSymbols[${status.index}] = ${geneSymbol};
                </c:forEach>
                <c:forEach var="chromosome"  items="${options['chromosomes']}" varStatus="status">
                    chromosomes[${status.index}] = ${chromosome};
                </c:forEach>
                <c:forEach var="mgiReference"  items="${options['mgiReferences']}" varStatus="status">
                    mgiReferences[${status.index}] = ${mgiReference};
                </c:forEach>
                
                $("#geneId").autocomplete({ source: geneIds, mustMatch:1,max:100});
                $("#chromosome").autocomplete({ source: chromosomes, mustMatch:1, max:100});
                $("#geneName").autocomplete({ source: geneNames, mustMatch:1, max:100});
                $("#mgiReference").autocomplete({ source: mgiReferences, mustMatch:1, max:100});
                $("#geneSymbol").autocomplete({ source: geneSymbols, mustMatch:1, max:100});
            }
            
        </script>
        <title>Gene Management - list</title>
    </head>
    <body>
        <h2>Gene Management - list</h2>
        <span id="loginHeader">Logged in as user "<sec:authentication property='principal.username'/>"</span>
        
        <br />

        <form action="geneManagementDetail.emma">
            <input type="hidden" name="action" value="newGene" />
            <input type="submit" value="New" style="margin-left: 430px; margin-bottom: 5px" formaction="geneManagementDetail.emma" />
        </form>
                                                    
        <form:form commandName="filter" method="get">
            
            <br />
            
            <table id="tabFilter" style="border: 1px solid black">
                <thead>
                    <tr><th colspan="4" style="text-align: left">Filter</th></tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="4">
                            <input type="hidden" name="action" value="applyFilter" />
                            <input type="submit" id="applyFilter" value="Go" formaction="geneManagementList.emma" />
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
                    <c:when test="${fn:length(filteredGenesDAOList) > 0}">
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
                <c:forEach var="gene" items="${filteredGenesDAOList}" varStatus="status">
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
                                               src="../images/edit.jpg" formaction="geneManagementDetail.emma" />
                                        </form:form>
                                    </td>
                                    <c:set var="boundAlleles" value="${gene.boundAlleles}" />
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
                                                       src="../images/delete.jpg" formaction="geneManagementList.emma?id=${gene.id_gene}&action=deleteGene"
                                                       title="Cannot delete gene ${gene.id_gene} as it is bound to allele ID ${boundAlleleIds}."
                                                       class="ui-state-disabled" />
                                            </td>
                                        </c:when>
                                        <c:when test="${boundAllelesCount > 0}">
                                            <td>
                                                <input alt="Delete Gene" type="image" height="15" width="15" disabled="disabled"
                                                       src="../images/delete.jpg" formaction="geneManagementList.emma?id=${gene.id_gene}&action=deleteGene"
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
                                                           src="../images/delete.jpg" formaction="geneManagementList.emma" />
                                                </form:form>
                                            </td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                            </table>
                        </td>
                        <td style="border: 1px solid black" valign="top">${gene.id_gene}</td>
                        <td style="border: 1px solid black" valign="top">${gene.name}</td>
                        <td style="border: 1px solid black" valign="top">${gene.symbol}</td>
                        <td style="border: 1px solid black" valign="top">${gene.chromosome}</td>
                        <td style="border: 1px solid black" valign="top">${gene.species}</td>
                        <td style="border: 1px solid black" valign="top">${gene.centimorgan}</td>
                        <td style="border: 1px solid black" valign="top">${gene.mgi_ref}</td>
                        <td style="border: 1px solid black" valign="top">${gene.ensembl_ref}</td>
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