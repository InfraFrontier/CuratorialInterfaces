<%--
    Document   : biblioManagementList
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
            var curationUrlRoot = "${pageContext.request.contextPath}/curation";

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

                filterValue = $('#filterBiblioKey').val();
                if ((filterValue !== '') && ( ! isInteger(filterValue)) && ( ! isIntegerArray(filterValue))) {
                    errorCount++;
                    $('#filterBiblioKey').parent().append(errMsg);
                }
                filterValue = $('#filterStrainKey').val();
                if ((filterValue !== '') && ( ! isInteger(filterValue)) && ( ! isIntegerArray(filterValue))) {
                    errorCount++;
                    $('#filterStrainKey').parent().append(errMsg);
                }
                filterValue = $('#filterPubmedId').val();
                if ((filterValue !== '') && ( ! isInteger(filterValue)) && ( ! isIntegerArray(filterValue))) {
                    errorCount++;
                    $('#filterPubmedId').parent().append(errMsg);
                }
                
                if (errorCount > 0)
                    $('#go').attr("disabled", true);

                return (errorCount === 0);
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

            function deleteBiblio(id, deleteIcon) {
                $.ajax({
                    url: biblioListUrlRoot + "/deleteBiblio"
                    , dataType: "json"
                    , async: false
                    , data: {'biblio_key': id}
                    , success: function(data) {
                        if (data.status === 'ok') {
                            var tr = $(deleteIcon).parent().parent().parent().parent().parent().parent()[0];
                            $('#tabResults').dataTable().fnDeleteRow(tr);       // Remove the biblio from the grid.
                        } else {
                            alert(data.message);
                        }
                    }
                });

                return false;
            }

            function lookupPubmedId(id) {
                window.open("http://europepmc.org/search?query=" + id, "pubmedWindow");
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
                    case 'filterBiblioKey':
                        $('input[id^="filterBiblioKey"]').val(value);
                        break;

                    case 'filterStrainKey':
                        $('input[id^="filterStrainKey"]').val(value);
                        break;

                    case 'filterPubmedId':
                        $('input[id^="filterPubmedId"]').val(value);
                        break;

                    case 'filterBiblioAuthor1':
                        $('input[id^="filterBiblioAuthor1"]').val(value);
                        break;

                    case 'filterBiblioJournal':
                        $('input[id^="filterBiblioJournal"]').val(value);
                        break;

                    case 'filterBiblioTitle':
                        $('input[id^="filterBiblioTitle"]').val(value);
                        break;
                        
                    case 'filterBiblioYear':
                        $('input[id^="filterBiblioYear"]').val(value);
                        break;
                }
            }
            
            function clearFilter() {
                $('.filterComponent').val('');
                clearErrors();
                return false;
            }

            function populateFilterAutocompletes() {
                $("#filterPubmedId").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                              url: curationUrlRoot + "/biblioManagementList/getPubmedIds"
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
                
                $("#filterBiblioAuthor1").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                              url: curationUrlRoot + "/biblioManagementList/getAuthor1s"
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
                
                $("#filterBiblioJournal").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                              url: curationUrlRoot + "/biblioManagementList/getJournals"
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
                
                $("#filterBiblioTitle").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                              url: curationUrlRoot + "/biblioManagementList/getTitles"
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
                
                $("#filterBiblioYear").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                              url: curationUrlRoot + "/biblioManagementList/getYears"
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
            }

        </script>
        <title>Bibliography Management - list</title>
    </head>
    <body>
        <h2>Bibliography Management - list</h2>
        <span id="loginHeader">Logged in as user "<sec:authentication property='principal.username'/>"</span>
        
        <form:form method="get" modelAttribute="filter">
            <%-- NEW BIBLIO --%>
            <input type="hidden" name="biblio_key" value="" />
            
            <input type="hidden" id="filterBiblioKeyNew"       name="filterBiblioKey"         class="filterComponent" value="${filter.biblio_key}" />
            <input type="hidden" id="filterStrainKeyNew"       name="filterStrainKey"         class="filterComponent" value="${filter.strain_key}" />
            <input type="hidden" id="filterPubmedIdNew"        name="filterPubmedId"          class="filterComponent" value="${filter.pubmedId}" />
            <input type="hidden" id="filterBiblioAuthor1New"   name="filterBiblioAuthor1"     class="filterComponent" value="${filter.biblioAuthor1}" />
            <input type="hidden" id="filterBiblioJournalNew"   name="filterBiblioJournal"     class="filterComponent" value="${filter.biblioJournal}" />
            <input type="hidden" id="filterBiblioTitleNew"     name="filterBiblioTitle"       class="filterComponent" value="${filter.biblioTitle}" />
            <input type="hidden" id="filterBiblioYearNew"      name="filterBiblioYear"        class="filterComponent" value="${filter.biblioYear}" />
            <a href="${pageContext.request.contextPath}/curation/biblioManagementDetail/edit?biblio_key=&amp;filterBiblioKey=&amp;filterStrainKey=&amp;filterPubmedId=&amp;filterBiblioAuthor1=&amp;filterBiblioJournal=&amp;filterBiblioTitle=&amp;filterBiblioYear="
               style="margin-left: 365px"
               target="biblioManagementDetail"
               tabindex="11"
               title="Add new biblio">
                New
            </a>
        </form:form>
        
        <form:form modelAttribute="filter" method="get">
            <input type="hidden" id="filterBiblioKeyGo"       name="filterBiblioKey"          class="filterComponent" value="${filter.biblio_key}" />
            <input type="hidden" id="filterStrainKeyGo"       name="filterStrainKey"          class="filterComponent" value="${filter.strain_key}" />
            <input type="hidden" id="filterPubmedIdGo"        name="filterPubmedId"           class="filterComponent" value="${filter.pubmedId}" />
            <input type="hidden" id="filterBiblioAuthor1Go"   name="filterBiblioAuthor1"      class="filterComponent" value="${filter.biblioAuthor1}" />
            <input type="hidden" id="filterBiblioJournalGo"   name="filterBiblioJournal"      class="filterComponent" value="${filter.biblioJournal}" />
            <input type="hidden" id="filterBiblioTitleGo"     name="filterBiblioTitle"        class="filterComponent" value="${filter.biblioTitle}" />
            <input type="hidden" id="filterBiblioYearGo"      name="filterBiblioYear"         class="filterComponent" value="${filter.biblioYear}" />
            
            <table id="tabFilter" style="border: 1px solid black">
                <thead>
                    <tr><th colspan="4" style="text-align: left">Filter</th></tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="4" style="text-align: right">
                            <%-- CLEAR FILTER --%>
                            <input type="button" value="Clear Filter" onclick="clearFilter();" tabindex="9" />
                            &nbsp;&nbsp;&nbsp;
                            <%-- GO --%>
                            <input type="submit" id="go" value="Go"
                                   tabindex="10"
                                   formaction="${pageContext.request.contextPath}/curation/biblioManagementList/go"
                                   formtarget="biblioManagementList" />
                        </td>
                    </tr>
                </tfoot>
                <tbody>
                    <tr>
                        <td><form:label path="biblio_key">Biblio ID:</form:label></td>
                        <td><form:input id="filterBiblioKey" class="filterComponent" path="biblio_key" tabindex="1" /></td>
                        
                        <td><form:label path="biblioJournal">Journal:</form:label></td>
                        <td><form:input id="filterBiblioJournal" class="filterComponent" path="biblioJournal" tabindex="5" /></td>
                    </tr>
                    <tr>
                        <td><form:label path="strain_key">Strain ID:</form:label></td>
                        <td><form:input id="filterStrainKey" class="filterComponent" path="strain_key" tabindex="2" /></td>
                        
                        <td><form:label path="biblioTitle">Title:</form:label></td>
                        <td><form:input id="filterBiblioTitle" class="filterComponent" path="biblioTitle" tabindex="6" /></td>
                    </tr>
                    <tr>
                        <td><form:label path="pubmedId">Pubmed ID:</form:label></td>
                        <td><form:input id="filterPubmedId" class="filterComponent" path="pubmedId" tabindex="3" /></td>
                        
                        <td><form:label path="biblioYear">Year:</form:label></td>
                        <td><form:input id="filterBiblioYear" class="filterComponent" path="biblioYear" tabindex="7" /></td>
                    </tr>
                    <tr>
                        <td><form:label path="biblioAuthor1">Author1:</form:label></td>
                        <td><form:input id="filterBiblioAuthor1" class="filterComponent" path="biblioAuthor1" tabindex="4" /></td>
                        
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
                        <c:when test="${fn:length(filteredBibliosList) > 0}">
                            <tr style="border: 1px solid black">
                                <th>Actions</th>
                                <th>Biblio ID</th>
                                <th>Bound Strain IDs</th>
                                <th>Curated</th>
                                <th>Pubmed ID</th>
                                <th>Journal</th>
                                <th>Volume</th>
                                <th>Title</th>
                                <th>Pages</th>
                                <th>Author1</th>
                                <th>Additional Authors</th>
                                <th>Year</th>
                                <th>Notes</th>
                            </tr>
                        </c:when>
                    </c:choose>
                </thead>
                <tbody>
                    <c:forEach var="biblio" items="${filteredBibliosList}" varStatus="status">
                        <tr>
                            <td style="border: 1px solid black">
                                <table>
                                    <thead></thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <%-- EDIT BIBLIO --%>
                                                <form method="get" action="${pageContext.request.contextPath}/curation/biblioManagementDetail/edit">
                                                    <input type="hidden" name="biblio_key" value="${biblio.biblio_key}" />
                                                    
                                                    <input type="hidden" id="filterBiblioKeyEdit"       name="filterBiblioKey"          class="filterComponent" value="${filter.biblio_key}" />
                                                    <input type="hidden" id="filterStrainKeyEdit"       name="filterStrainKey"          class="filterComponent" value="${filter.strain_key}" />
                                                    <input type="hidden" id="filterPubmedIdEdit"        name="filterPubmedId"           class="filterComponent" value="${filter.pubmedId}" />
                                                    <input type="hidden" id="filterBiblioAuthor1Edit"   name="filterBiblioAuthor1"      class="filterComponent" value="${filter.biblioAuthor1}" />
                                                    <input type="hidden" id="filterBiblioJournalEdit"   name="filterBiblioJournal"      class="filterComponent" value="${filter.biblioJournal}" />
                                                    <input type="hidden" id="filterBiblioTitleEdit"     name="filterBiblioTitle"        class="filterComponent" value="${filter.biblioTitle}" />
                                                    <input type="hidden" id="filterBiblioYearEdit"      name="filterBiblioYear"         class="filterComponent" value="${filter.biblioYear}" />
                                                    <a href="${pageContext.request.contextPath}/curation/biblioManagementDetail/edit?biblio_key=${biblio.biblio_key}&amp;filterBiblioKey=&amp;filterStrainKey=&amp;filterPubmedId=&amp;filterBiblioAuthor1=&amp;filterBiblioJournal=&amp;filterBiblioTitle=&amp;filterBiblioYear="
                                                       target="biblioManagementDetail"
                                                       title="Edit bibliography ${biblio.biblio_key}">
                                                        Edit
                                                    </a>
                                                </form>
                                            </td>
                                            
                                            <%-- BOUND STRAINS --%>
                                            <c:set var="boundStrainKeys" value="${biblio.strain_keys}" />
                                            <c:set var="boundStrainsCount" value="${fn:length(boundStrainKeys)}" />
<%--
                                            <c:set var="boundStrainKeys" value="" />
                                            <c:forEach var="strain" items="${boundStrains}" varStatus="status">
                                                <c:if test="${status.index == 0}">
                                                    <c:set var="boundStrainKeys" value="${strain.strain_key}" scope="page" />
                                                </c:if>
                                                <c:if test="${status.index > 0}">
                                                    <c:set var="boundStrainKeys" value="${boundStrainKeys}, ${strain.strain_key}" />
                                                </c:if>
                                            </c:forEach>
--%>
                                            <c:choose>
                                                <c:when test="${boundStrainsCount > 0}">
                                                    <td>
                                                        <input alt="Delete Bibliography" type="image" height="15" width="15" disabled="disabled"
                                                               src="${pageContext.request.contextPath}/images/delete.jpg"
                                                               title="Cannot delete bibliography ${biblio.biblio_key} as it is bound to strain(s) ${boundStrainKeys}."
                                                               class="ui-state-disabled" />
                                                    </td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td>
                                                        <%-- DELETE BIBLIOGRAPHY --%>
                                                        <input alt="Delete Bibliography" type="image" height="15" width="15" title="Delete bibliography ${biblio.biblio_key}"
                                                               src="${pageContext.request.contextPath}/images/delete.jpg"
                                                               onclick="deleteBiblio(${biblio.biblio_key}, this)"
                                                               formmethod="POST" />
                                                    </td>
                                                </c:otherwise>
                                            </c:choose>
                                        </tr>
                                    </tbody>
                                </table>
                            </td>
                            <td style="border: 1px solid black">${biblio.biblio_key}</td>
                            <td style="border: 1px solid black">
                                <c:choose>
                                    <c:when test="${fn:length(boundStrainKeys) > 0}">
                                        <table>
                                            <thead></thead>
                                            <tbody>
                                                <c:forEach var="strainKey" items="${fn:split(boundStrainKeys, ',')}" varStatus="status">
                                                    <tr>
                                                        <td>
                                                            <a href="${emmaContextPath}/strainsUpdateInterface.emma?EditStrain=${strainKey}"
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
                            <td style="border: 1px solid black">${biblio.updated}</td>
                            <td style="border: 1px solid black">
                                <a href="javascript:lookupPubmedId('${biblio.pubmed_id}');">
                                    ${biblio.pubmed_id}
                                </a>
                            </td>
                            <td style="border: 1px solid black">${fn:escapeXml(biblio.journal)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(biblio.volume)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(biblio.title)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(biblio.pages)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(biblio.author1)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(biblio.author2)}</td>
                            <td style="border: 1px solid black">${biblio.year}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(biblio.notes)}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>