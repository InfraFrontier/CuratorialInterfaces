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
            var backgroundListUrlRoot = "${pageContext.request.contextPath}/curation/backgroundManagementList";
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
                
                $('#tabResults > tbody > tr')
                    .on('dragstart', handleDragStart);
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
                $("#filterBackgroundName").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                            url: backgroundListUrlRoot + "/getBackgroundNames"
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

                $("#filterBackgroundSymbol").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                            url: backgroundListUrlRoot + "/getBackgroundSymbols",
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

            function deleteBackground(id, deleteIcon) {
                $.ajax({
                    url: backgroundListUrlRoot + "/deleteBackground"
                    , dataType: "json"
                    , async: false
                    , data: {'background_key': id}
                    , success: function(data) {
                        if (data.status === 'ok') {
                            var tr = $(deleteIcon).parent().parent().parent().parent().parent().parent()[0];
                            $('#tabResults').dataTable().fnDeleteRow(tr);       // Remove the background from the grid.
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
                    case 'filterBackgroundKey':
                        $('input[id^="filterBackgroundKey"]').val(value);
                        break;

                    case 'filterBackgroundName':
                        $('input[id^="filterBackgroundName"]').val(value);
                        break;

                    case 'filterBackgroundSymbol':
                        $('input[id^="filterBackgroundSymbol"]').val(value);
                        break;

                    case 'filterCuratedYes':
                        $('input[id^="filterBackgroundIsCurated"]').val('Y');
                        break;

                    case 'filterCuratedNo':
                        $('input[id^="filterBackgroundIsCurated"]').val('N');
                        break;

                    case 'filterCuratedNotSpecified':
                        $('input[id^="filterBackgroundIsCurated"]').val('NS');
                        break;

                    case 'filterInbredYes':
                        $('input[id^="filterBackgroundIsInbred"]').val('Y');
                        break;

                    case 'filterInbredNo':
                        $('input[id^="filterBackgroundIsInbred"]').val('N');
                        break;

                    case 'filterInbredNotSpecified':
                        $('input[id^="filterBackgroundIsInbred"]').val('NS');
                        break;
                }
            }
            
            function clearFilter() {
                $('.filterComponent').val('');
                $(':radio').prop('checked', false);
                clearErrors();
                return false;
            }
            
            function handleDragStart(e) {
                var background_key = $(this).data('background_key');
                e.originalEvent.dataTransfer.setData('text/background', background_key);
            }

        </script>
        <title>Background Management - list</title>
    </head>
    <body>
        <h2>Background Management - list</h2>
        <span id="loginHeader">Logged in as user "<sec:authentication property='principal.username'/>"</span>
        
        <form:form method="get" modelAttribute="filter">
            <%-- NEW BACKGROUND --%>
            <input type="hidden" name="background_key" value="" />
            
            <input type="hidden" id="filterBackgroundKeyNew"          name="filterBackgroundKey"          class="filterComponent" value="${filter.background_key}" />
            <input type="hidden" id="filterBackgroundNameNew"         name="filterBackgroundName"         class="filterComponent" value="${filter.backgroundName}" />
            <input type="hidden" id="filterBackgroundSymbolNew"       name="filterBackgroundSymbol"       class="filterComponent" value="${filter.backgroundSymbol}" />
            <input type="hidden" id="filterBackgroundIsCuratedNew"    name="filterBackgroundIsCurated"    class="filterComponent" value="${filter.backgroundIsCurated}" />
            <input type="hidden" id="filterBackgroundIsInbredNew"     name="filterBackgroundIsInbred"     class="filterComponent" value="${filter.backgroundIsInbred}" />
            <a href="${pageContext.request.contextPath}/curation/backgroundManagementDetail/edit?background_key=&amp;filterBackgroundKey=&amp;filterBackgroundName=&amp;filterBackgroundSymbol=&amp;filterBackgroundIsCurated=&amp;filterBackgroundIsInbred="
               style="margin-left: 490px"
               tabindex="12"
               target="backgroundManagementDetail"
               title="Add new background">
                New
            </a>
        </form:form>
        
        <form:form modelAttribute="filter" method="get">
            <input type="hidden" id="filterBackgroundKeyGo"          name="filterBackgroundKey"          class="filterComponent" value="${filter.background_key}" />
            <input type="hidden" id="filterBackgroundNameGo"         name="filterBackgroundName"         class="filterComponent" value="${filter.backgroundName}" />
            <input type="hidden" id="filterBackgroundSymbolGo"       name="filterBackgroundSymbol"       class="filterComponent" value="${filter.backgroundSymbol}" />
            <input type="hidden" id="filterBackgroundIsCuratedGo"    name="filterBackgroundIsCurated"    class="filterComponent" value="${filter.backgroundIsCurated}" />
            <input type="hidden" id="filterBackgroundIsInbredGo"     name="filterBackgroundIsInbred"     class="filterComponent" value="${filter.backgroundIsInbred}" />
            
            <table id="tabFilter" style="border: 1px solid black">
                <thead>
                    <tr><th colspan="4" style="text-align: left">Filter</th></tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="4" style="text-align: right">
                            <%-- CLEAR FILTER --%>
                            <input type="button" value="Clear Filter" onclick="clearFilter();" tabindex="10" />
                            &nbsp;&nbsp;&nbsp;
                            <%-- GO --%>
                            <input type="submit" id="go" value="Go"
                                   formaction="${pageContext.request.contextPath}/curation/backgroundManagementList/go"
                                   formtarget="backgroundManagementList"
                                   tabindex="11" />
                        </td>
                    </tr>
                </tfoot>
                <tbody>
                    <tr>
                        <td><form:label path="background_key">Background ID:</form:label></td>
                        <td><form:input id="filterBackgroundKey"             class="filterComponent" path="background_key" tabindex="1" /></td>
                        
                        <td><form:label path="backgroundIsCurated">Curated:</form:label></td>
                        <td>
                            <form:radiobutton id="filterCuratedYes"          class="filterComponent" path="backgroundIsCurated" value="Y"  tabindex="4" />Yes
                            <form:radiobutton id="filterCuratedNo"           class="filterComponent" path="backgroundIsCurated" value="N"  tabindex="5" />No
                            <form:radiobutton id="filterCuratedNotSpecified" class="filterComponent" path="backgroundIsCurated" value="NS" tabindex="6"  />Not specified
                        </td>
                    </tr>
                    <tr>
                        <td><form:label path="backgroundName">Background name:</form:label></td>
                        <td><form:input id="filterBackgroundName" class="filterComponent" path="backgroundName" tabindex="2" /></td>
                        
                        <td><form:label path="backgroundIsInbred">Inbred:</form:label></td>
                        <td>
                            <form:radiobutton id="filterInbredYes"          class="filterComponent" path="backgroundIsInbred" value="Y"  tabindex="7" />Yes
                            <form:radiobutton id="filterInbredNo"           class="filterComponent" path="backgroundIsInbred" value="N"  tabindex="8"/>No
                            <form:radiobutton id="filterInbredNotSpecified" class="filterComponent" path="backgroundIsInbred" value="NS" tabindex="9"  />Not specified
                        </td>
                    </tr>
                    <tr>
                        <td><form:label path="backgroundSymbol">Background symbol:</form:label></td>
                        <td colspan="3"><form:input id="filterBackgroundSymbol" class="filterComponent" path="backgroundSymbol" tabindex="3" /></td>
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
                        <c:when test="${fn:length(filteredBackgroundsList) > 0}">
                            <tr style="border: 1px solid black">
                                <th>Actions</th>
                                <th>Background ID</th>
                                <th>Background Name</th>
                                <th>Background Symbol</th>
                                <th>Bound Mutations</th>
                                <th>Inbred</th>
                                <th>Species</th>
                                <th>Curated</th>
                                <th>Notes</th>
                            </tr>
                        </c:when>
                    </c:choose>
                </thead>
                <tbody>
                    <c:forEach var="background" items="${filteredBackgroundsList}" varStatus="status">
                        <tr draggable="true" data-background_key="${background.background_key}">
                            <td style="border: 1px solid black">
                                <table>
                                    <thead></thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <%-- EDIT BACKGROUND --%>
                                                <form method="get" action="${pageContext.request.contextPath}/curation/backgroundManagementDetail/edit">
                                                    <input type="hidden" name="background_key" value="${background.background_key}" />

                                                    <input type="hidden" id="filterBackgroundKeyEdit"          name="filterBackgroundKey"          class="filterComponent" value="${filter.background_key}" />
                                                    <input type="hidden" id="filterBackgroundNameEdit"         name="filterBackgroundName"         class="filterComponent" value="${filter.backgroundName}" />
                                                    <input type="hidden" id="filterBackgroundSymbolEdit"       name="filterBackgroundSymbol"       class="filterComponent" value="${filter.backgroundSymbol}" />
                                                    <input type="hidden" id="filterBackgroundIsCuratedEdit"    name="filterBackgroundIsCurated"    class="filterComponent" value="${filter.backgroundIsCurated}" />
                                                    <input type="hidden" id="filterBackgroundIsInbredEdit"     name="filterBackgroundIsInbred"     class="filterComponent" value="${filter.backgroundIsInbred}" />
                                                    <a href="${pageContext.request.contextPath}/curation/backgroundManagementDetail/edit?background_key=${background.background_key}&amp;filterBackgroundKey=&amp;filterBackgroundName=&amp;filterBackgroundSymbol=&amp;filterBackgroundIsCurated=&amp;filterBackgroundIsInbred="
                                                       target="backgroundManagementDetail"
                                                       title="Edit background ${background.background_key}">
                                                        Edit
                                                    </a>
                                                </form>
                                            </td>
                                            
                                            <%-- BOUND MUTATIONS --%>
                                            <c:set var="boundMutationKeys" value="${background.mutation_keys}" />
                                            <c:choose>
                                                <c:when test="${not empty boundMutationKeys}">
                                                    <td>
                                                        <input alt="Delete Background" type="image" height="15" width="15" disabled="disabled"
                                                               src="${pageContext.request.contextPath}/images/delete.jpg"
                                                               title="Cannot delete background ${background.background_key} as it is bound to mutation(s) ${boundMutationKeys}."
                                                               class="ui-state-disabled" />
                                                    </td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td>
                                                        <%-- DELETE BACKGROUND --%>
                                                        <input alt="Delete Background" type="image" height="15" width="15" title="Delete background ${background.background_key}"
                                                               src="${pageContext.request.contextPath}/images/delete.jpg"
                                                               onclick="deleteBackground(${background.background_key}, this)"
                                                               formmethod="POST" />
                                                    </td>
                                                </c:otherwise>
                                            </c:choose>
                                        </tr>
                                    </tbody>
                                </table>
                            </td>
                            <td style="border: 1px solid black">${background.background_key}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(background.name)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(background.symbol)}</td>
                            <td style="border: 1px solid black">
                                <form>
                                    <c:choose>
                                        <c:when test="${empty boundMutationKeys}">
                                            <a href="${pageContext.request.contextPath}/curation/mutationManagementList/showFilter?filterMutationKey=&amp;filterMutationType=&amp;filterMutationSubtype=&amp;filterStrainKey=&amp;filterAlleleKey=&amp;filterBackgroundKey=&amp;filterGeneKey=&amp;filterGeneSymbol="
                                               target="mutationManagementList"
                                               title="Edit mutations">
                                                <i>None</i>
                                            </a>
                                        </c:when>
                                        <c:when test="${fn:length(boundMutationKeys) <= 10}">
                                            <a href="${pageContext.request.contextPath}/curation/mutationManagementList/go?filterMutationKey=${boundMutationKeys}&amp;filterMutationType=&amp;filterMutationSubtype=&amp;filterStrainKey=&amp;filterAlleleKey=&amp;filterBackgroundKey=&amp;filterGeneKey=&amp;filterGeneSymbol="
                                               target="mutationManagementList"
                                               title="Edit mutation(s) ${boundMutationKeys}">
                                                ${boundMutationKeys}
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${pageContext.request.contextPath}/curation/mutationManagementList/go?filterMutationKey=${boundMutationKeys}&amp;filterMutationType=&amp;filterMutationSubtype=&amp;filterStrainKey=&amp;filterAlleleKey=&amp;filterBackgroundKey=&amp;filterGeneKey=&amp;filterGeneSymbol="
                                               target="mutationManagementList"
                                               title="Edit mutation(s) ${boundMutationKeys}">
                                                ${fn:substring(boundMutationKeys, 0, 10)} ...
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </form>
                            </td>
                            <td style="border: 1px solid black">${background.inbred}</td>
                            <td style="border: 1px solid black">${background.species}</td>
                            <td style="border: 1px solid black">${background.curated}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(background.notes)}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>