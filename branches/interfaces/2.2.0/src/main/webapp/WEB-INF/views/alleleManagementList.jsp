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
            var geneUrlRoot = "${pageContext.request.contextPath}/curation/geneManagementList";
            var urlRoot = "${pageContext.request.contextPath}/curation/alleleManagementList";
            var alleleIds = null;

            $(document).ready(function() {
                populateFilterAutocompletes();
                setResultsControls();
                
                $('#go').click(function() {
                    return validate();
                });

                clearErrors();
                
                $('.filterComponent').each(function (index, element) {
                    $(element).on('keyup blur', function(event) {
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

                filterValue = $('#filterAlleleId').val();
                if ((filterValue !== '') && ( ! isInteger(filterValue)) && ( ! isIntegerArray(filterValue))) {
                    errorCount++;
                    $('#filterAlleleId').parent().append(errMsg);
                }
                filterValue = $('#filterGeneId').val();
                if ((filterValue !== '') && ( ! isInteger(filterValue)) && ( ! isIntegerArray(filterValue))) {
                    errorCount++;
                    $('#filterGeneId').parent().append(errMsg);
                }
                
                if (errorCount > 0)
                    $('#go').attr("disabled", true);

                return (errorCount === 0);
            }

            function populateFilterAutocompletes() {
                var urlRoot = "${pageContext.request.contextPath}/curation/alleleManagementList";

                $("#filterAlleleName").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                            url: urlRoot + "/getAlleleNames"
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
                            url: urlRoot + "/getAlleleSymbols",
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
                            url: geneUrlRoot + "/getGeneNames"
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
                            url: geneUrlRoot + "/getGeneSymbols",
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
                            url: urlRoot + "/getMgiReferences",
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

            function populateAlleleIds(urlRoot) {
                jQuery.ajax({
                    url: urlRoot + "/getAlleleIds"
                  , async: false
                }).done(function(data) {
                    alleleIds = data.slice(0);
                });
            }

            function deleteAllele(id, deleteIcon) {
                $.ajax({
                    url: urlRoot + "/deleteAllele"
                    , dataType: "json"
                    , async: false
                    , data: {'id_allele': id}
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
                    case 'filterAlleleId':
                        $('input[id^="filterAlleleId"]').val(value);
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
                        
                    case 'filterGeneId':
                        $('input[id^="filterGeneId"]').val(value);
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
        
        <form:form method="get" modelAttribute="filter" target="alleleManagementDetail">
            <%-- NEW ALLELE --%>
            <input type="hidden" name="id_allele" value="0" />
            
            <input type="hidden" id="filterAlleleIdNew" name="filterAlleleId" value="${filter.alleleId}" />
            <input type="hidden" id="filterAlleleNameNew" name="filterAlleleName" value="${filter.alleleName}" />
            <input type="hidden" id="filterAlleleSymbolNew" name="filterAlleleSymbol" value="${filter.alleleSymbol}" />
            <input type="hidden" id="filterAlleleMgiReferenceNew" name="filterAlleleMgiReference" value="${filter.alleleMgiReference}" />
            <input type="hidden" id="filterGeneIdNew" name="filterGeneId" value="${filter.geneId}" />
            <input type="hidden" id="filterGeneNameNew" name="filterGeneName" value="${filter.geneName}" />
            <input type="hidden" id="filterGeneSymbolNew" name="filterGeneSymbol" value="${filter.geneSymbol}" />
            <input type="submit" value="New" style="margin-left: 420px; margin-bottom: 5px"
                   formmethod="get"
                   formaction="${pageContext.request.contextPath}/curation/alleleManagementDetail/edit" />
        </form:form>
        
        <form:form modelAttribute="filter" method="get">
            <input type="hidden" id="filterAlleleIdGo" name="filterAlleleId" value="${filter.alleleId}" />
            <input type="hidden" id="filterAlleleNameGo" name="filterAlleleName" value="${filter.alleleName}" />
            <input type="hidden" id="filterAlleleSymbolGo" name="filterAlleleSymbol" value="${filter.alleleSymbol}" />
            <input type="hidden" id="filterAlleleMgiReferenceGo" name="filterAlleleMgiReference" value="${filter.alleleMgiReference}" />
            <input type="hidden" id="filterGeneIdGo" name="filterGeneId" value="${filter.geneId}" />
            <input type="hidden" id="filterGeneNameGo" name="filterGeneName" value="${filter.geneName}" />
            <input type="hidden" id="filterGeneSymbolGo" name="filterGeneSymbol" value="${filter.geneSymbol}" />
            
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
                        <td><form:label path="alleleId">Allele ID:</form:label></td>
                        <td><form:input id="filterAlleleId" class="filterComponent" path="alleleId" /></td>
                        
                        <td><form:label path="geneId">Gene ID:</form:label></td>
                        <td><form:input id="filterGeneId" class="filterComponent" path="geneId" /></td>
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
                                <th>Bound Mutations</th>
                                <th>Allele ID</th>
                                <th>Allele Name</th>
                                <th>Allele Symbol</th>
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
                                                <form method="get" target="alleleManagementDetail" action="${pageContext.request.contextPath}/curation/alleleManagementDetail/edit">
                                                    <input type="hidden" name="id_allele" value="${allele.id_allele}" />

                                                    <input type="hidden" id="filterAlleleIdEdit" name="filterAlleleId" value="${filter.alleleId}" />
                                                    <input type="hidden" id="filterAlleleNameEdit" name="filterAlleleName" value="${filter.alleleName}" />
                                                    <input type="hidden" id="filterAlleleSymbolEdit" name="filterAlleleSymbol" value="${filter.alleleSymbol}" />
                                                    <input type="hidden" id="filterAlleleMgiReferenceEdit" name="filterAlleleMgiReference" value="${filter.alleleMgiReference}" />
                                                    <input type="hidden" id="filterGeneIdEdit" name="filterGeneId" value="${filter.geneId}" />
                                                    <input type="hidden" id="filterGeneNameEdit" name="filterGeneName" value="${filter.geneName}" />
                                                    <input type="hidden" id="filterGeneSymbolEdit" name="filterGeneSymbol" value="${filter.geneSymbol}" />
                                                    <input alt="Edit Allele" type="image" height="15" width="15" title="Edit allele ${allele.id_allele}"
                                                           src="${pageContext.request.contextPath}/images/edit.jpg" />
                                                </form>
                                            </td>
                                            
                                            <c:set var="boundMutations" value="${allele.mutations}" />
                                            <c:set var="boundMutationsCount" value="${fn:length(boundMutations)}" />

                                            <c:set var="boundMutationIds" value="" />
                                            <c:forEach var="mutation" items="${boundMutations}" varStatus="status">
                                                <c:if test="${status.index == 0}">
                                                    <c:set var="boundMutationIds" value="${mutation.id_mutation}" scope="page" />
                                                </c:if>
                                                <c:if test="${status.index > 0}">
                                                    <c:set var="boundMutationIds" value="${boundMutationIds}, ${mutation.id_mutation}" />
                                                </c:if>
                                            </c:forEach>
                                            <c:choose>
                                                <c:when test="${boundMutationsCount == 1}">
                                                    <td>
                                                        <input alt="Delete Allele" type="image" height="15" width="15" disabled="disabled"
                                                               src="${pageContext.request.contextPath}/images/delete.jpg"
                                                               title="Cannot delete allele ID ${allele.id_allele} as it is bound to mutation ID ${boundMutationIds}."
                                                               class="ui-state-disabled" />
                                                    </td>
                                                </c:when>
                                                <c:when test="${boundMutationsCount > 0}">
                                                    <td>
                                                        <input alt="Delete Allele" type="image" height="15" width="15" disabled="disabled"
                                                               src="${pageContext.request.contextPath}/images/delete.jpg"
                                                               title="Cannot delete allele ID ${allele.id_allele} as it is bound to mutation Ids ${boundMutationIds}."
                                                               class="ui-state-disabled" />
                                                    </td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td>
                                                        <%-- DELETE ALLELE --%>
                                                        <input alt="Delete Allele" type="image" height="15" width="15" title="Delete allele ID ${allele.id_allele}"
                                                               src="${pageContext.request.contextPath}/images/delete.jpg"
                                                               onclick="deleteAllele(${allele.id_allele}, this)"
                                                               formmethod="POST" />
                                                    </td>
                                                </c:otherwise>
                                            </c:choose>
                                        </tr>
                                    </tbody>
                                </table>
                            </td>
                            <td style="border: 1px solid black">
                                <form>
                                    <c:choose>
                                        <c:when test="${boundMutationsCount eq 0}">
                                            <a href="mutationManagementList" target="mutationManagementList" title="Edit mutations">
                                                <i>None</i>
                                            </a>
                                        </c:when>
                                        <c:when test="${boundMutationsCount eq 1}">
                                            <a href="mutationManagementList?mutationIds=${boundMutationIds}" target="mutationManagementList" title="Edit bound mutation ${boundMutationIds}">
                                                ${boundMutationIds}
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="mutationManagementList?mutationIds=${boundMutationIds}" target="mutationManagementList" title="Edit bound mutations ${boundMutationIds}">
                                                ${boundMutationIds}
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </form>
                            </td>
                            <td style="border: 1px solid black">${allele.id_allele}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(allele.name)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(allele.symbol)}</td>
                            <td style="border: 1px solid black">
                                <a href="javascript:lookupMgi('${fn:escapeXml(allele.mgi_ref)}');">
                                    ${fn:escapeXml(allele.mgi_ref)}
                                </a>
                            </td>
                            
                            <td style="border: 1px solid black">
                                <a href="${pageContext.request.contextPath}/curation/geneManagementList/go?filterGeneId=${allele.gene.id_gene}&amp;filterGeneName=&amp;filterGeneSymbol=&amp;filterChromosome=&amp;filterGeneMgiReference="
                                   target="geneManagementList">
                                    ${fn:escapeXml(allele.gene.id_gene)}
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