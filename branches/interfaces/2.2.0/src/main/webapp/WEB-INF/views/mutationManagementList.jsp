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

                filterValue = $('#filterMutationId').val();
                if ((filterValue !== '') && ( ! isInteger(filterValue)) && ( ! isIntegerArray(filterValue))) {
                    errorCount++;
                    $('#filterMutationId').parent().append(errMsg);
                }
                filterValue = $('#filterStrainId').val();
                if ((filterValue !== '') && ( ! isInteger(filterValue)) && ( ! isIntegerArray(filterValue))) {
                    errorCount++;
                    $('#filterStrainId').parent().append(errMsg);
                }
                filterValue = $('#filterAlleleId').val();
                if ((filterValue !== '') && ( ! isInteger(filterValue)) && ( ! isIntegerArray(filterValue))) {
                    errorCount++;
                    $('#filterAlleleId').parent().append(errMsg);
                }
                filterValue = $('#filterBackgroundId').val();
                if ((filterValue !== '') && ( ! isInteger(filterValue)) && ( ! isIntegerArray(filterValue))) {
                    errorCount++;
                    $('#filterBackgroundId').parent().append(errMsg);
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

            function deleteAllele(id, deleteIcon) {
                $.ajax({
                    url: mutationListUrlRoot + "/deleteAllele"
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
                    case 'filterMutationId':
                        $('input[id^="filterMutationId"]').val(value);
                        break;
                        
                    case 'filterMutationType':
                        $('input[id^="filterMutationType"]').val(value);
                        break;
                        
                    case 'filterMutationSubtype':
                        $('input[id^="filterMutationSubtype"]').val(value);
                        break;
                        
                    case 'filterStrainId':
                        $('input[id^="filterStrainId"]').val(value);
                        break;
                        
                    case 'filterAlleleId':
                        $('input[id^="filterAlleleId"]').val(value);
                        break;

                    case 'filterBackgroundId':
                        $('input[id^="filterBackgroundId"]').val(value);
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
            <input type="hidden" name="id_mutation" value="0" />
            
            <input type="hidden" id="filterMutationIdNew" name="filterMutationId" value="${filter.mutationId}" />
            <input type="hidden" id="filterMutationTypeNew" name="filterMutationType" value="${filter.mutationType}" />
            <input type="hidden" id="filterMutationSubtypeNew" name="filterMutationSubtype" value="${filter.mutationSubtype}" />
            <input type="hidden" id="filterStrainIdNew" name="filterStrainId" value="${filter.strainId}" />
            <input type="hidden" id="filterAlleleIdNew" name="filterAlleleId" value="${filter.alleleId}" />
            <input type="hidden" id="filterBackgroundIdNew" name="filterBackgroundId" value="${filter.backgroundId}" />
            <input type="submit" value="New" style="margin-left: 450px; margin-bottom: 5px" tabindex="1"
                   formmethod="get"
                   formaction="${pageContext.request.contextPath}/curation/mutationManagementDetail/edit" />
        </form:form>
        
        <form:form modelAttribute="filter" method="get">
            <input type="hidden" id="filterMutationIdGo" name="filterMutationId" value="${filter.mutationId}" />
            <input type="hidden" id="filterMutationTypeGo" name="filterMutationType" value="${filter.mutationType}" />
            <input type="hidden" id="filterMutationSubtypeGo" name="filterMutationSubtype" value="${filter.mutationSubtype}" />
            <input type="hidden" id="filterStrainIdGo" name="filterStrainId" value="${filter.strainId}" />
            <input type="hidden" id="filterAlleleIdGo" name="filterAlleleId" value="${filter.alleleId}" />
            <input type="hidden" id="filterBackgroundIdGo" name="filterBackgroundId" value="${filter.backgroundId}" />
            
            <table id="tabFilter" style="border: 1px solid black">
                <thead>
                    <tr><th colspan="4" style="text-align: left">Filter</th></tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="4" style="text-align: right">
                            <%-- CLEAR FILTER --%>
                            <input type="button" value="Clear Filter" onclick="clearFilter();" tabindex="8"/>
                            &nbsp;&nbsp;&nbsp;
                            <%-- GO --%>
                            <input type="submit" id="go" value="Go" tabindex="9"
                                   formaction="${pageContext.request.contextPath}/curation/mutationManagementList/go"
                                   formtarget="mutationManagementList" />
                        </td>
                    </tr>
                </tfoot>
                <tbody>
                    <tr>
                        <%-- MUTATION ID --%>
                        <td><form:label path="mutationId">Mutation Id:</form:label></td>
                        <td><form:input id="filterMutationId" class="filterComponent" path="mutationId" tabindex="2" /></td>
                        
                        <%-- STRAIN ID --%>
                        <td><form:label path="strainId">Strain Id:</form:label></td>
                        <td><form:input id="filterStrainId" class="filterComponent" path="strainId" tabindex="5" /></td>
                    </tr>
                    <tr>
                        <%-- MUTATION TYPE --%>
                        <td><form:label path="mutationType">Mutation Type:</form:label></td>
                        <td><form:input id="filterMutationType" class="filterComponent" path="mutationType" tabindex="3" /></td>
                        
                        <%-- ALLELE ID --%>
                        <td><form:label path="alleleId">Allele Id:</form:label></td>
                        <td><form:input id="filterAlleleId" class="filterComponent" path="alleleId" tabindex="6" /></td>
                    </tr>
                    <tr>
                        <%-- MUTATION SUBTYPE --%>
                        <td><form:label path="mutationSubtype">Mutation Subtype:</form:label></td>
                        <td><form:input id="filterMutationSubtype" class="filterComponent" path="mutationSubtype" tabindex="4" /></td>
                        
                        <%-- BACKGROUND ID --%>
                        <td><form:label path="backgroundId">Background Id:</form:label></td>
                        <td><form:input id="filterBackgroundId" class="filterComponent" path="backgroundId" tabindex="7" /></td>
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
                                <th>Replaced Allele</th>
                                <th>Sex</th>
                                <th>Genotype</th>
                                <th>Knock-in Alter</th>
                                <th>Cause</th>
                                <th>Chromosome</th>
                                <th>Dominance</th>
                                <th>Targeted Mutation Esline</th>
                                <th>Chromosome Annotated Name</th>
                                <th>Chromosome Annotated Description</th>
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
                                                    <input type="hidden" name="id_mutation" value="${mutation.id_mutation}" />

                                                    <input type="hidden" id="filterMutationIdEdit" name="filterMutationId" value="${filter.mutationId}" />
                                                    <input type="hidden" id="filterMutationTypeEdit" name="filterMutationType" value="${filter.mutationType}" />
                                                    <input type="hidden" id="filterMutationSubtypeEdit" name="filterMutationSubtype" value="${filter.mutationSubtype}" />
                                                    <input type="hidden" id="filterStrainIdEdit" name="filterStrainId" value="${filter.strainId}" />
                                                    <input type="hidden" id="filterAlleleIdEdit" name="filterAlleleId" value="${filter.alleleId}" />
                                                    <input type="hidden" id="filterBackgroundIdEdit" name="filterBackgroundId" value="${filter.backgroundId}" />
                                                    <input alt="Edit Mutation" type="image" height="15" width="15" title="Edit mutation Id ${mutation.id_mutation}"
                                                           src="${pageContext.request.contextPath}/images/edit.jpg" />
                                                </form>
                                            </td>
                                            <td>
                                                <%-- DELETE MUTATION --%>
                                                <input alt="Delete Mutation" type="image" height="15" width="15" title="Delete mutation Id ${mutation.id_mutation}"
                                                       src="${pageContext.request.contextPath}/images/delete.jpg"
                                                       onclick="deleteMutation(${mutation.id_mutation}, this)"
                                                       formmethod="POST" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </td>
                            <td style="border: 1px solid black">${mutation.id_mutation}</td>
                            <td style="border: 1px solid black">${mutation.main_type}</td>
                            <td style="border: 1px solid black">${mutation.sub_type}</td>
                            <td style="border: 1px solid black">
<!-- FIXME FIXME FIXME FIXME FIXME FIXME FIXME FIXME !!!!!!!!!!! ADD STRAIN FILTERS ONCE STRAIN SCREEN IS WRITTEN. FIXME FIXME FIXME -->
                                <a href="${pageContext.request.contextPath}/curation/strainManagementList/go?filterStrainId=${mutation.str_id_str}"
                                   target="strainManagementList"
                                   title="Edit strain ID ${mutation.str_id_str}">
                                    ${mutation.str_id_str}
                                </a>
                            </td>
                            <td style="border: 1px solid black">
<!-- FIXME FIXME FIXME FIXME FIXME FIXME FIXME FIXME !!!!!!!!!!! ADD BACKGROUND FILTERS ONCE BACKGROUND SCREEN IS WRITTEN. FIXME FIXME FIXME -->
                                <a href="${pageContext.request.contextPath}/curation/backgroundManagementList/go?filterBackgroundId=${mutation.bg_id_bg}"
                                   target="backgroundManagementList"
                                   title="Edit background ID ${mutation.bg_id_bg}">
                                    ${mutation.bg_id_bg}
                                </a>
                            </td>
                            <td style="border: 1px solid black">
                                <a href="${pageContext.request.contextPath}/curation/alleleManagementList/go?filterAlleleId=${mutation.alls_id_allel}&amp;filterAlleleName=&amp;filterAlleleSymbol=&amp;filterAlleleMgiReference=&amp;filterGeneId=&amp;filterGeneName=&amp;filterGeneSymbol="
                                   target="alleleManagementList"
                                   title="Edit allele ID ${mutation.alls_id_allel}">
                                    ${mutation.alls_id_allel}
                                </a>
                            </td>
                            <td style="border: 1px solid black">
                                <a href="${pageContext.request.contextPath}/curation/alleleManagementList/go?filterAlleleId=${mutation.alls_id_allel_replaced}&amp;filterAlleleName=&amp;filterAlleleSymbol=&amp;filterAlleleMgiReference=&amp;filterGeneId=&amp;filterGeneName=&amp;filterGeneSymbol="
                                   target="alleleManagementList"
                                   title="Edit replaced allele ID ${mutation.alls_id_allel_replaced}">
                                    ${mutation.alls_id_allel_replaced}
                                </a>
                            </td>
                            <td style="border: 1px solid black">${mutation.sex}</td>
                            <td style="border: 1px solid black">${mutation.genotype}</td>
                            <td style="border: 1px solid black">${mutation.ki_alter}</td>
                            <td style="border: 1px solid black">${mutation.mu_cause}</td>
                            <td style="border: 1px solid black">${mutation.chromosome}</td>
                            <td style="border: 1px solid black">${mutation.dominance}</td>
                            <td style="border: 1px solid black">${mutation.tm_esline}</td>
                            <td style="border: 1px solid black">${mutation.ch_ano_name}</td>
                            <td style="border: 1px solid black">${mutation.ch_ano_desc}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
