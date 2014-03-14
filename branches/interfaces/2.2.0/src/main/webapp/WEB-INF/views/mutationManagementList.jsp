<%-- 
    Document   : mutationManagementList
    Created on : Mar 3, 2014, 10:11:04 AM
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
            var mutationListUrlRoot = "${pageContext.request.contextPath}/curation/mutationManagementList";
            var mutationDetailUrlRoot = "${pageContext.request.contextPath}/curation/mutationManagementDetail";
            var mutationIds = null;

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

                filterValue = $('#filterMutationKey').val();
                if ((filterValue !== '') && ( ! isInteger(filterValue)) && ( ! isIntegerArray(filterValue))) {
                    errorCount++;
                    $('#filterMutationKey').parent().append(errMsg);
                }
                filterValue = $('#filterStrainKey').val();
                if ((filterValue !== '') && ( ! isInteger(filterValue)) && ( ! isIntegerArray(filterValue))) {
                    errorCount++;
                    $('#filterStrainKey').parent().append(errMsg);
                }
                filterValue = $('#filterAlleleKey').val();
                if ((filterValue !== '') && ( ! isInteger(filterValue)) && ( ! isIntegerArray(filterValue))) {
                    errorCount++;
                    $('#filterAlleleKey').parent().append(errMsg);
                }
                filterValue = $('#filterBackgroundKey').val();
                if ((filterValue !== '') && ( ! isInteger(filterValue)) && ( ! isIntegerArray(filterValue))) {
                    errorCount++;
                    $('#filterBackgroundKey').parent().append(errMsg);
                }
                
                if (errorCount > 0)
                    $('#go').attr("disabled", true);

                return (errorCount === 0);
            }

            function populateFilterAutocompletes() {
                $("#filterMutationType").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                            url: mutationListUrlRoot + "/getMutationTypes"
                            , dataType: "json"
                            , success: function(data) {
                                response($.map(data, function(item) {
                                    return {label: item};
                                }));
                            }
                        });
                    }
                });

                $("#filterMutationSubtype").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                            url: mutationListUrlRoot + "/getMutationSubtypes"
                            , dataType: "json"
                            , success: function(data) {
                                response($.map(data, function(item) {
                                    return {label: item};
                                }));
                            }
                        });
                    }
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

            function deleteMutation(id, deleteIcon) {
                $.ajax({
                    url: mutationListUrlRoot + "/deleteMutation"
                    , dataType: "json"
                    , async: false
                    , data: {'mutation_key': id}
                    , success: function(data) {
                        if (data.status === 'ok') {
                            var tr = $(deleteIcon).parent().parent().parent().parent().parent().parent()[0];
                            $('#tabResults').dataTable().fnDeleteRow(tr);       // Remove the mutation from the grid.
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
                    case 'filterMutationKey':
                        $('input[id^="filterMutationKey"]').val(value);
                        break;
                        
                    case 'filterMutationType':
                        $('input[id^="filterMutationType"]').val(value);
                        break;
                        
                    case 'filterMutationSubtype':
                        $('input[id^="filterMutationSubtype"]').val(value);
                        break;
                        
                    case 'filterStrainKey':
                        $('input[id^="filterStrainKey"]').val(value);
                        break;
                        
                    case 'filterAlleleKey':
                        $('input[id^="filterAlleleKey"]').val(value);
                        break;

                    case 'filterBackgroundKey':
                        $('input[id^="filterBackgroundKey"]').val(value);
                        break;
                        
                    case 'filterGeneKey':
                        $('input[id^="filterGeneKey"]').val(value);
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
        <title>Mutation Management - list</title>
    </head>
    <body>
        <h2>Mutation Management - list</h2>
        <span id="loginHeader">Logged in as user "<sec:authentication property='principal.username'/>"</span>
        
        <form:form method="get" modelAttribute="filter" target="mutationManagementDetail">
            <%-- NEW MUTATION --%>
            <input type="hidden" name="mutation_key" value="0" />
            
            <input type="hidden" id="filterMutationKeyNew"      name="filterMutationKey"     class="filterComponent" value="${filter.mutation_key}" />
            <input type="hidden" id="filterMutationTypeNew"     name="filterMutationType"    class="filterComponent" value="${filter.mutationType}" />
            <input type="hidden" id="filterMutationSubtypeNew"  name="filterMutationSubtype" class="filterComponent" value="${filter.mutationSubtype}" />
            <input type="hidden" id="filterStrainKeyNew"        name="filterStrainKey"       class="filterComponent" value="${filter.strain_key}" />
            <input type="hidden" id="filterAlleleKeyNew"        name="filterAlleleKey"       class="filterComponent" value="${filter.allele_key}" />
            <input type="hidden" id="filterBackgroundKeyNew"    name="filterBackgroundKey"   class="filterComponent" value="${filter.background_key}" />
            <input type="hidden" id="filterGeneKeyNew"          name="filterGeneKey"         class="filterComponent" value="${filter.gene_key}" />
            <input type="hidden" id="filterGeneSymbolNew"       name="filterGeneSymbol"      class="filterComponent" value="${filter.geneSymbol}" />
            <input type="submit" value="New" style="margin-left: 450px; margin-bottom: 5px" tabindex="1"
                   formmethod="get"
                   formaction="${pageContext.request.contextPath}/curation/mutationManagementDetail/edit" />
        </form:form>
        
        <form:form modelAttribute="filter" method="get">
            <input type="hidden" id="filterMutationKeyGo"      name="filterMutationKey"     class="filterComponent" value="${filter.mutation_key}" />
            <input type="hidden" id="filterMutationTypeGo"     name="filterMutationType"    class="filterComponent" value="${filter.mutationType}" />
            <input type="hidden" id="filterMutationSubtypeGo"  name="filterMutationSubtype" class="filterComponent" value="${filter.mutationSubtype}" />
            <input type="hidden" id="filterStrainKeyGo"        name="filterStrainKey"       class="filterComponent" value="${filter.strain_key}" />
            <input type="hidden" id="filterAlleleKeyGo"        name="filterAlleleKey"       class="filterComponent" value="${filter.allele_key}" />
            <input type="hidden" id="filterBackgroundKeyGo"    name="filterBackgroundKey"   class="filterComponent" value="${filter.background_key}" />
            <input type="hidden" id="filterGeneKeyGo"          name="filterGeneKey"         class="filterComponent" value="${filter.gene_key}" />
            <input type="hidden" id="filterGeneSymbolGo"       name="filterGeneSymbol"      class="filterComponent" value="${filter.geneSymbol}" />
            
            <table id="tabFilter" style="border: 1px solid black">
                <thead>
                    <tr><th colspan="6" style="text-align: left">Filter</th></tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="6" style="text-align: right">
                            <%-- CLEAR FILTER --%>
                            <input type="button" value="Clear Filter" onclick="clearFilter();" tabindex="10"/>
                            &nbsp;&nbsp;&nbsp;
                            <%-- GO --%>
                            <input type="submit" id="go" value="Go" tabindex="11"
                                   formaction="${pageContext.request.contextPath}/curation/mutationManagementList/go"
                                   formtarget="mutationManagementList" />
                        </td>
                    </tr>
                </tfoot>
                <tbody>
                    <tr>
                        <%-- MUTATION ID --%>
                        <td><form:label path="mutation_key">Mutation ID:</form:label></td>
                        <td><form:input id="filterMutationKey" class="filterComponent" path="mutation_key" tabindex="2" /></td>
                        
                        <%-- STRAIN ID --%>
                        <td><form:label path="strain_key">Strain ID:</form:label></td>
                        <td><form:input id="filterStrainKey" class="filterComponent" path="strain_key" tabindex="5" /></td>
                        
                        <%-- GENE ID --%>
                        <td><form:label path="gene_key">Gene ID:</form:label></td>
                        <td><form:input id="filterGeneKey" class="filterComponent" path="gene_key" tabindex="8" /></td>
                    </tr>
                    <tr>
                        <%-- MUTATION TYPE --%>
                        <td><form:label path="mutationType">Mutation Type:</form:label></td>
                        <td><form:input id="filterMutationType" class="filterComponent" path="mutationType" tabindex="3" /></td>
                        
                        <%-- ALLELE ID --%>
                        <td><form:label path="allele_key">Allele ID:</form:label></td>
                        <td><form:input id="filterAlleleKey" class="filterComponent" path="allele_key" tabindex="6" /></td>
                        
                        <%-- GENE SYMBOL --%>
                        <td><form:label path="geneSymbol">Gene Symbol:</form:label></td>
                        <td><form:input id="filterGeneSymbol" class="filterComponent" path="geneSymbol" tabindex="9" /></td>
                    </tr>
                    <tr>
                        <%-- MUTATION SUBTYPE --%>
                        <td><form:label path="mutationSubtype">Mutation Subtype:</form:label></td>
                        <td><form:input id="filterMutationSubtype" class="filterComponent" path="mutationSubtype" tabindex="4" /></td>
                        
                        <%-- BACKGROUND ID --%>
                        <td><form:label path="background_key">Background ID:</form:label></td>
                        <td><form:input id="filterBackgroundKey" class="filterComponent" path="background_key" tabindex="7" /></td>
                        <td colspan="2">&nbsp;</td>
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
                        <c:when test="${fn:length(filteredMutationsList) > 0}">
                            <tr style="border: 1px solid black">
                                <th>Actions</th>
                                <th>Mutation Id</th>
                                <th>Type</th>
                                <th>Subtype</th>
                                <th>Strain</th>
                                <th>Background ID</th>
                                <th>Allele ID</th>
                                <th>Gene ID</th>
                                <th>Gene Symbol</th>
                                <th>Sex</th>
                                <th>Genotype</th>
                                <th>Cause</th>
                                <th>Dominance</th>
                                <th>Targeted Mutation Esline</th>
                            </tr>
                        </c:when>
                    </c:choose>
                </thead>
                <tbody>
                    <c:forEach var="mutation" items="${filteredMutationsList}" varStatus="status">
                        <tr>
                            <td style="border: 1px solid black">
                                <table>
                                    <thead></thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <%-- EDIT MUTATION --%>
                                                <form method="get" target="mutationManagementDetail" action="${pageContext.request.contextPath}/curation/mutationManagementDetail/edit">
                                                    <input type="hidden" name="mutation_key" value="${mutation.mutation_key}" />

                                                    <input type="hidden" id="filterMutationKeyEdit"      name="filterMutationKey"     class="filterComponent" value="${filter.mutation_key}" />
                                                    <input type="hidden" id="filterMutationTypeEdit"     name="filterMutationType"    class="filterComponent" value="${filter.mutationType}" />
                                                    <input type="hidden" id="filterMutationSubtypeEdit"  name="filterMutationSubtype" class="filterComponent" value="${filter.mutationSubtype}" />
                                                    <input type="hidden" id="filterStrainKeyEdit"        name="filterStrainKey"       class="filterComponent" value="${filter.strain_key}" />
                                                    <input type="hidden" id="filterAlleleKeyEdit"        name="filterAlleleKey"       class="filterComponent" value="${filter.allele_key}" />
                                                    <input type="hidden" id="filterBackgroundKeyEdit"    name="filterBackgroundKey"   class="filterComponent" value="${filter.background_key}" />
                                                    <input type="hidden" id="filterGeneKeyEdit"          name="filterGeneKey"         class="filterComponent" value="${filter.gene_key}" />
                                                    <input type="hidden" id="filterGeneSymbolEdit"       name="filterGeneSymbol"      class="filterComponent" value="${filter.geneSymbol}" />
                                                    <a href="${pageContext.request.contextPath}/curation/mutationManagementDetail/edit?mutation_key=${mutation.mutation_key}&amp;filterMutationKey=&amp;filterMutationType=&amp;filterMutationSubtype=&amp;filterStrainKey=&amp;filterAlleleKey=&amp;filterBackgroundKey=&amp;filterGeneKey=&amp;filterGeneSymbol="
                                                       target="mutationManagementDetail"
                                                       title="Edit mutation ${mutation.mutation_key}">
                                                        Edit
                                                    </a>
                                                </form>
                                            </td>

                                            <%-- BOUND STRAINS --%>
                                            <c:set var="boundStrainKeys" value="${mutation.strain_keys}" />
                                            <c:choose>
                                                <c:when test="${fn:length(boundStrainKeys) > 0}">
                                                    <td>
                                                        <input alt="Delete Mutation" type="image" height="15" width="15" disabled="disabled"
                                                               src="${pageContext.request.contextPath}/images/delete.jpg"
                                                               title="Cannot delete mutation ${mutation.mutation_key} as it is bound to strain(s) ${boundStrainKeys}."
                                                               class="ui-state-disabled" />
                                                    </td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td>
                                                        <%-- DELETE MUTATION --%>
                                                        <input alt="Delete Mutation" type="image" height="15" width="15" title="Delete mutation ${mutation.mutation_key}"
                                                               src="${pageContext.request.contextPath}/images/delete.jpg"
                                                               onclick="deleteMutation(${mutation.mutation_key}, this)"
                                                               formmethod="POST" />
                                                    </td>
                                                </c:otherwise>
                                            </c:choose>
                                        </tr>
                                    </tbody>
                                </table>
                            </td>
                            <td style="border: 1px solid black">${mutation.mutation_key}</td>
                            <td style="border: 1px solid black">${mutation.type}</td>
                            <td style="border: 1px solid black">${mutation.subtype}</td>
                            <td style="border: 1px solid black">
                                <c:choose>
                                    <c:when test="${fn:length(boundStrainKeys) > 0}">
                                        <table>
                                            <thead></thead>
                                            <tbody>
                                                <c:forEach var="strainKey" items="${fn:split(boundStrainKeys, ',')}" varStatus="status">
                                                    <tr>
                                                        <td>
                                               
<!-- FIXME FIXME FIXME FIXME FIXME FIXME FIXME FIXME !!!!!!!!!!! DEV URL IS HARD CODED!!!!!!!  FIXME FIXME FIXME -->
                                                            <a href="https://dev.infrafrontier.eu/emma/interfaces/strainsUpdateInterface.emma?EditStrain=${strainKey}"
                                                               target="strainEdit"
                                                               title="Edit strain ${strainKey}">
                                                                ${strainKey}
                                                            </a>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </c:when>
                                </c:choose>
                            </td>
                            <td style="border: 1px solid black">
<!-- FIXME FIXME FIXME FIXME FIXME FIXME FIXME FIXME !!!!!!!!!!! ADD BACKGROUND FILTERS ONCE BACKGROUND SCREEN IS WRITTEN. FIXME FIXME FIXME -->
                                <a href="${pageContext.request.contextPath}/curation/backgroundManagementList/go?filterBackgroundKey=${mutation.background_key}"
                                   target="backgroundManagementList"
                                   title="Edit background ${mutation.background_key}">
                                    ${mutation.background_key}
                                </a>
                            </td>
                            <td style="border: 1px solid black">
                                <a href="${pageContext.request.contextPath}/curation/alleleManagementList/go?filterAlleleKey=${mutation.allele_key}&amp;filterAlleleName=&amp;filterAlleleSymbol=&amp;filterAlleleMgiReference=&amp;filterGeneKey=&amp;filterGeneName=&amp;filterGeneSymbol=&amp;filterGeneKey="
                                   target="alleleManagementList"
                                   title="Edit allele ${mutation.allele_key}">
                                    ${mutation.allele_key}
                                </a>
                            </td>
                            <td style="border: 1px solid black">
                                <a href="${pageContext.request.contextPath}/curation/geneManagementList/go?filterGeneKey=${mutation.allele.gene_key}&amp;filterGeneName=&amp;filterGeneSymbol=&amp;filterChromosome=&amp;filterGeneMgiReference="
                                   target="geneManagementList"
                                   title="Edit gene ${mutation.allele.gene_key}">
                                    ${mutation.allele.gene_key}
                                </a>
                            </td>
                            <td style="border: 1px solid black">${mutation.allele.gene.symbol}</td>
                            <td style="border: 1px solid black">${mutation.sex}</td>
                            <td style="border: 1px solid black">${mutation.genotype}</td>
                            <td style="border: 1px solid black">${mutation.cause}</td>
                            <td style="border: 1px solid black">${mutation.dominance}</td>
                            <td style="border: 1px solid black">${mutation.targetedMutationEsLine}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>