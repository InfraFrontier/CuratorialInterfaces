<%--
    Document   : alleleManagementList
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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.dataTables.css" />
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.0.js" type="text/javascript" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/js/jquery-ui.min.js" type="text/javascript" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js" type="text/javascript" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/js/json2.js" type="text/javascript" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/js/utils.js" type="text/javascript" charset="UTF-8"></script>

        <style type="text/css">
            .error {
                    color: #ff0000;
            }
            .clientError {
                    color: #ff0000;
            }

            .errorBorder {
                border-collapse: separate;
                border-spacing: 2px;
                border-color: red;
            }
        </style>

        <script>
            var geneListUrlRoot = "${pageContext.request.contextPath}/curation/geneManagementList";
            var alleleListUrlRoot = "${pageContext.request.contextPath}/curation/alleleManagementList";
            var alleleKeys = null;

            $(document).ready(function() {
                populateFilterAutocompletes();
                setResultsControls();
                
                $('#go').click(function() {
                    return validate();
                });

                clearErrors();
                
                $('.filterComponent').each(function (index, element) {
                    $(element).on('keyup change blur', function(event) {
                        updateFilter(element);
                    });
                });
                
                $('#tabResults').dataTable();
            });

            function clearErrors() {
                $('.clientError').remove();
                $('#go').attr("disabled", false);
            }

            // Returns true if validation passes; false if it does not.
            function validate() {
                var errMsg = '<br class="clientError" /><span class="clientError">Please enter only integers.</span>';
                var errorCount = 0;
                var filterValue;
                
                // Remove any error validation messages.
                clearErrors();

                filterValue = $('#filterAlleleKey').val();
                if ((filterValue !== '') && ( ! isInteger(filterValue)) && ( ! isIntegerArray(filterValue))) {
                    errorCount++;
                    $('#filterAlleleKey').parent().append(errMsg);
                }
                filterValue = $('#filterGeneKey').val();
                if ((filterValue !== '') && ( ! isInteger(filterValue)) && ( ! isIntegerArray(filterValue))) {
                    errorCount++;
                    $('#filterGeneKey').parent().append(errMsg);
                }
                
                if (errorCount > 0)
                    $('#go').attr("disabled", true);

                return (errorCount === 0);
            }

            function populateFilterAutocompletes() {
                $("#filterAlleleName").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                            url: alleleListUrlRoot + "/getAlleleNames"
                            , dataType: "json"
                            , data: {filterTerm: request.term}
                            , success: function(data) {
                                response($.map(data, function(item) {
                                    return {label: item};
                                }));
                            }
                        });
                    },
                    minLength: 1
                });

                $("#filterAlleleSymbol").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                            url: alleleListUrlRoot + "/getAlleleSymbols",
                            dataType: "json",
                            data: {filterTerm: request.term},
                            success: function(data) {
                                response($.map(data, function(item) {
                                    return {label: item};
                                }));
                            }
                        });
                    },
                    minLength: 1
                });

                $("#filterGeneName").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                            url: geneListUrlRoot + "/getGeneNames"
                            , dataType: "json"
                            , data: {filterTerm: request.term}
                            , success: function(data) {
                                response($.map(data, function(item) {
                                    return {label: item};
                                }));
                            }
                        });
                    },
                    minLength: 1
                });

                $("#filterGeneSymbol").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                            url: geneListUrlRoot + "/getGeneSymbols",
                            dataType: "json",
                            data: {filterTerm: request.term},
                            success: function(data) {
                                response($.map(data, function(item) {
                                    return {label: item};
                                }));
                            }
                        });
                    },
                    minLength: 1
                });

                $("#filterAlleleMgiReference").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                            url: alleleListUrlRoot + "/getMgiReferences",
                            dataType: "json",
                            data: {filterTerm: request.term},
                            success: function(data) {
                                response($.map(data, function(item) {
                                    return {label: item};
                                }));
                            }
                        });
                    },
                    minLength: 1
                });
            }
            
            function setResultsControls() {
                var resultsFormDisplayAttribute;
                if (${ (not empty showResultsForm) && showResultsForm}) {
                    resultsFormDisplayAttribute = 'block';
                    // Set the results message and show/hide tabResults as appropriate.
                    var numResultRows = $('#tabResults > tbody > tr').length;
                    switch (numResultRows) {
                        case 0:
                            $('#labResults').text('No results found.');
                            $('#tabResults').css('display', 'none');
                            break;
                            
                        case 1:
                            $('#labResults').text('1 result found.');
                            $('#tabResults').css('display', 'block');
                            break;
                            
                        default:
                            $('#labResults').text(numResultRows + ' results found.');
                            $('#tabResults').css('display', 'block');
                            break;
                    }
                }
                else
                    resultsFormDisplayAttribute = 'none';
                $('#divResults').css('display', resultsFormDisplayAttribute);
            }

            function lookupMgi(id) {
                window.open("http://www.informatics.jax.org/marker?id=MGI:" + id, "MgiWindow");
            }

            function deleteAllele(id, deleteIcon) {
                $.ajax({
                    url: alleleListUrlRoot + "/deleteAllele"
                    , dataType: "json"
                    , async: false
                    , data: {'allele_key': id}
                    , success: function(data) {
                        if (data.status === 'ok') {
                            var tr = $(deleteIcon).parent().parent().parent().parent().parent().parent()[0];
                            $('#tabResults').dataTable().fnDeleteRow(tr);       // Remove the allele from the grid.
                        } else {
                            alert(data.message);
                        }
                    }
                });

                return false;
            }

            function updateFilter(inputObj) {
                if ( ! validate())
                    return false;
                var jqObj = $(inputObj);
                var id = $(jqObj).attr('id');
                var value = $(jqObj).val();
                if (value === null)
                    value = '';

                switch (id) {
                    case 'filterAlleleKey':
                        $('input[id^="filterAlleleKey"]').val(value);
                        break;

                    case 'filterAlleleName':
                        $('input[id^="filterAlleleName"]').val(value);
                        break;

                    case 'filterAlleleSymbol':
                        $('input[id^="filterAlleleSymbol"]').val(value);
                        break;

                    case 'filterChromosome':
                        $('input[id^="filterChromosome"]').val(value);
                        break;

                    case 'filterAlleleMgiReference':
                        $('input[id^="filterAlleleMgiReference"]').val(value);
                        break;
                        
                    case 'filterGeneKey':
                        $('input[id^="filterGeneKey"]').val(value);
                        break;

                    case 'filterGeneName':
                        $('input[id^="filterGeneName"]').val(value);
                        break;

                    case 'filterGeneSymbol':
                        $('input[id^="filterGeneSymbol"]').val(value);
                        break;
                }
            }
            
            function clearFilter() {
                $('.filterComponent').val('');
                clearErrors();
                return false;
            }

        </script>
        <title>Allele Management - list</title>
    </head>
    <body>
        <h2>Allele Management - list</h2>
        <span id="loginHeader">Logged in as user "<sec:authentication property='principal.username'/>"</span>
        
        <form:form method="get" modelAttribute="filter">
            <%-- NEW ALLELE --%>
            <input type="hidden" name="allele_key" value="" />
            
            <input type="hidden" id="filterAlleleKeyNew"          name="filterAlleleKey"          class="filterComponent" value="${filter.allele_key}" />
            <input type="hidden" id="filterAlleleNameNew"         name="filterAlleleName"         class="filterComponent" value="${filter.alleleName}" />
            <input type="hidden" id="filterAlleleSymbolNew"       name="filterAlleleSymbol"       class="filterComponent" value="${filter.alleleSymbol}" />
            <input type="hidden" id="filterAlleleMgiReferenceNew" name="filterAlleleMgiReference" class="filterComponent" value="${filter.alleleMgiReference}" />
            <input type="hidden" id="filterGeneKeyNew"            name="filterGeneKey"            class="filterComponent" value="${filter.gene_key}" />
            <input type="hidden" id="filterGeneNameNew"           name="filterGeneName"           class="filterComponent" value="${filter.geneName}" />
            <input type="hidden" id="filterGeneSymbolNew"         name="filterGeneSymbol"         class="filterComponent" value="${filter.geneSymbol}" />
            <input type="submit" value="New" style="margin-left: 420px; margin-bottom: 5px"
                   formmethod="get"
                   formaction="${pageContext.request.contextPath}/curation/alleleManagementDetail/edit"
                   formtarget="alleleManagementDetail" />
        </form:form>
        
        <form:form modelAttribute="filter" method="get">
            <input type="hidden" id="filterAlleleKeyGo"          name="filterAlleleKey"          class="filterComponent" value="${filter.allele_key}" />
            <input type="hidden" id="filterAlleleNameGo"         name="filterAlleleName"         class="filterComponent" value="${filter.alleleName}" />
            <input type="hidden" id="filterAlleleSymbolGo"       name="filterAlleleSymbol"       class="filterComponent" value="${filter.alleleSymbol}" />
            <input type="hidden" id="filterAlleleMgiReferenceGo" name="filterAlleleMgiReference" class="filterComponent" value="${filter.alleleMgiReference}" />
            <input type="hidden" id="filterGeneKeyGo"            name="filterGeneKey"            class="filterComponent" value="${filter.gene_key}" />
            <input type="hidden" id="filterGeneNameGo"           name="filterGeneName"           class="filterComponent" value="${filter.geneName}" />
            <input type="hidden" id="filterGeneSymbolGo"         name="filterGeneSymbol"         class="filterComponent" value="${filter.geneSymbol}" />
            
            <table id="tabFilter" style="border: 1px solid black">
                <thead>
                    <tr><th colspan="4" style="text-align: left">Filter</th></tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="4" style="text-align: right">
                            <%-- CLEAR FILTER --%>
                            <input type="button" value="Clear Filter" onclick="clearFilter();"/>
                            &nbsp;&nbsp;&nbsp;
                            <%-- GO --%>
                            <input type="submit" id="go" value="Go"
                                   formaction="${pageContext.request.contextPath}/curation/alleleManagementList/go"
                                   formtarget="alleleManagementList" />
                        </td>
                    </tr>
                </tfoot>
                <tbody>
                    <tr>
                        <td><form:label path="allele_key">Allele ID:</form:label></td>
                        <td><form:input id="filterAlleleKey" class="filterComponent" path="allele_key" /></td>
                        
                        <td><form:label path="gene_key">Gene ID:</form:label></td>
                        <td><form:input id="filterGeneKey" class="filterComponent" path="gene_key" /></td>
                    </tr>
                    <tr>
                        <td><form:label path="alleleName">Allele name:</form:label></td>
                        <td><form:input id="filterAlleleName" class="filterComponent" path="alleleName" /></td>
                        
                        <td><form:label path="geneName">Gene name:</form:label></td>
                        <td><form:input id="filterGeneName" class="filterComponent" path="geneName" /></td>
                    </tr>
                    <tr>
                        <td><form:label path="alleleSymbol">Allele symbol:</form:label></td>
                        <td><form:input id="filterAlleleSymbol" class="filterComponent" path="alleleSymbol" /></td>
                        
                        <td><form:label path="geneSymbol">Gene symbol:</form:label></td>
                        <td><form:input id="filterGeneSymbol" class="filterComponent" path="geneSymbol" /></td>
                    </tr>
                    <tr>
                        <td><form:label path="alleleMgiReference">MGI reference:</form:label></td>
                        <td><form:input id="filterAlleleMgiReference" class="filterComponent" path="alleleMgiReference" /></td>
                        <td colspan="2"></td>
                    </tr>
                </tbody>
            </table>
        </form:form>

        <%-- RESULTS GRID --%>
        <div id="divResults">
            
            <%-- # RECORDS LABEL --%>
            <br />
            <label id="labResults"></label>
            <br />
            <br />

            <table id="tabResults" style="border: 1px solid black; display: none">
                <thead>
                    <c:choose>
                        <c:when test="${fn:length(filteredAllelesList) > 0}">
                            <tr style="border: 1px solid black">
                                <th>Actions</th>
                                <th>Allele ID</th>
                                <th>Allele Name</th>
                                <th>Allele Symbol</th>
                                <th>Bound Mutations</th>
                                <th>MGI Reference</th>
                                <th>Gene ID</th>
                                <th>Gene Name</th>
                                <th>Gene Symbol</th>
                            </tr>
                        </c:when>
                    </c:choose>
                </thead>
                <tbody>
                    <c:forEach var="allele" items="${filteredAllelesList}" varStatus="status">
                        <tr>
                            <td style="border: 1px solid black">
                                <table>
                                    <thead></thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <%-- EDIT ALLELE --%>
                                                <form method="get" action="${pageContext.request.contextPath}/curation/alleleManagementDetail/edit">
                                                    <input type="hidden" name="allele_key" value="${allele.allele_key}" />

                                                    <input type="hidden" id="filterAlleleKeyEdit"          name="filterAlleleKey"          class="filterComponent" value="${filter.allele_key}" />
                                                    <input type="hidden" id="filterAlleleNameEdit"         name="filterAlleleName"         class="filterComponent" value="${filter.alleleName}" />
                                                    <input type="hidden" id="filterAlleleSymbolEdit"       name="filterAlleleSymbol"       class="filterComponent" value="${filter.alleleSymbol}" />
                                                    <input type="hidden" id="filterAlleleMgiReferenceEdit" name="filterAlleleMgiReference" class="filterComponent" value="${filter.alleleMgiReference}" />
                                                    <input type="hidden" id="filterGeneKeyEdit"            name="filterGeneKey"            class="filterComponent" value="${filter.gene_key}" />
                                                    <input type="hidden" id="filterGeneNameEdit"           name="filterGeneName"           class="filterComponent" value="${filter.geneName}" />
                                                    <input type="hidden" id="filterGeneSymbolEdit"         name="filterGeneSymbol"         class="filterComponent" value="${filter.geneSymbol}" />
                                                    <a href="${pageContext.request.contextPath}/curation/alleleManagementDetail/edit?allele_key=${allele.allele_key}&amp;filterAlleleKey=&amp;filterAlleleName=&amp;filterAlleleSymbol=&amp;filterAlleleMgiReference=&amp;filterGeneKey=&amp;filterGeneName=&amp;filterGeneSymbol="
                                                       target="alleleManagementDetail"
                                                       title="Edit allele ${allele.allele_key}">
                                                        Edit
                                                    </a>
                                                </form>
                                            </td>
                                            
                                            <%-- BOUND MUTATIONS --%>
                                            <c:set var="boundMutations" value="${allele.mutations}" />
                                            <c:set var="boundMutationsCount" value="${fn:length(boundMutations)}" />

                                            <c:set var="boundMutationKeys" value="" />
                                            <c:forEach var="mutation" items="${boundMutations}" varStatus="status">
                                                <c:if test="${status.index == 0}">
                                                    <c:set var="boundMutationKeys" value="${mutation.mutation_key}" scope="page" />
                                                </c:if>
                                                <c:if test="${status.index > 0}">
                                                    <c:set var="boundMutationKeys" value="${boundMutationKeys}, ${mutation.mutation_key}" />
                                                </c:if>
                                            </c:forEach>
                                            <c:choose>
                                                <c:when test="${boundMutationsCount > 0}">
                                                    <td>
                                                        <input alt="Delete Allele" type="image" height="15" width="15" disabled="disabled"
                                                               src="${pageContext.request.contextPath}/images/delete.jpg"
                                                               title="Cannot delete allele ${allele.allele_key} as it is bound to mutation(s) ${boundMutationKeys}."
                                                               class="ui-state-disabled" />
                                                    </td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td>
                                                        <%-- DELETE ALLELE --%>
                                                        <input alt="Delete Allele" type="image" height="15" width="15" title="Delete allele ${allele.allele_key}"
                                                               src="${pageContext.request.contextPath}/images/delete.jpg"
                                                               onclick="deleteAllele(${allele.allele_key}, this)"
                                                               formmethod="POST" />
                                                    </td>
                                                </c:otherwise>
                                            </c:choose>
                                        </tr>
                                    </tbody>
                                </table>
                            </td>
                            <td style="border: 1px solid black">${allele.allele_key}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(allele.name)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(allele.symbol)}</td>
                            <td style="border: 1px solid black">
                                <form>
                                    <c:choose>
                                        <c:when test="${boundMutationsCount eq 0}">
                                            <a href="${pageContext.request.contextPath}/curation/mutationManagementList/showFilter?filterMutationKey=&amp;filterMutationType=&amp;filterMutationSubtype=&amp;filterStrainKey=&amp;filterAlleleKey=&amp;filterBackgroundKey=&amp;filterGeneKey=&amp;filterGeneSymbol="
                                               target="mutationManagementList"
                                               title="Edit mutations">
                                                <i>None</i>
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${pageContext.request.contextPath}/curation/mutationManagementList/go?filterMutationKey=${boundMutationKeys}&amp;filterMutationType=&amp;filterMutationSubtype=&amp;filterStrainKey=&amp;filterAlleleKey=&amp;filterBackgroundKey=&amp;filterGeneKey=&amp;filterGeneSymbol="
                                               target="mutationManagementList"
                                               title="Edit mutation(s) ${boundMutationKeys}">
                                                ${boundMutationKeys}
                                            </a>   
                                        </c:otherwise>
                                    </c:choose>
                                </form>
                            </td>
                            <td style="border: 1px solid black">
                                <a href="javascript:lookupMgi('${fn:escapeXml(allele.mgiReference)}');">
                                    ${fn:escapeXml(allele.mgiReference)}
                                </a>
                            </td>
                            
                            <td style="border: 1px solid black">
                                <a href="${pageContext.request.contextPath}/curation/geneManagementList/go?filterGeneKey=${allele.gene.gene_key}&amp;filterGeneName=&amp;filterGeneSymbol=&amp;filterChromosome=&amp;filterGeneMgiReference="
                                   target="geneManagementList"
                                   title="Edit gene ${allele.gene.gene_key}">
                                    ${allele.gene.gene_key}
                                </a>
                            </td>
                            <td style="border: 1px solid black">${fn:escapeXml(allele.gene.name)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(allele.gene.symbol)}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>