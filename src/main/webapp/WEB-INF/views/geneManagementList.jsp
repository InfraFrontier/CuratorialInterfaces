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
            var urlRoot = "${pageContext.request.contextPath}/curation/geneManagementList";
            var chromosomes = null;

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
                // Remove any error validation messages.
                clearErrors();

                var filterValue = $('#filterGeneKey').val();
                if ((filterValue !== '') && ( ! isInteger(filterValue)) && ( ! isIntegerArray(filterValue))) {
                    var errMsg = '<br class="clientError" /><span class="clientError">Please enter only integers.</span>';
                    $('#filterGeneKey').parent().append(errMsg);
                    $('#go').attr("disabled", true);

                    return false;
                }

                return true;
            }

            function populateFilterAutocompletes() {
                var urlRoot = "${pageContext.request.contextPath}/curation/geneManagementList";

                $("#filterGeneName").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                            url: urlRoot + "/getGeneNames"
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
                            url: urlRoot + "/getGeneSymbols",
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

                populateChromosomes(urlRoot);
                
                $("#filterChromosome").autocomplete({
                    source: chromosomes,
                    minLength: 1
                });

                $("#filterGeneMgiReference").autocomplete({
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
                window.open("http://www.informatics.jax.org/marker/MGI:" + id, "MgiWindow");
            }

            function lookupEnsembl(id) {
                window.open("http://www.ensembl.org/Mus_musculus/geneview?gene=" + id, "EnsemblWindow");
            }

            function populateChromosomes(urlRoot) {
                jQuery.ajax({
                    url: urlRoot + "/getChromosomes"
                  , async: false
                }).done(function(data) {
                    chromosomes = data.slice(0);
                });
            }

            function deleteGene(id, deleteIcon) {
                $.ajax({
                    url: urlRoot + "/deleteGene"
                    , dataType: "json"
                    , async: false
                    , data: {'gene_key': id}
                    , success: function(data) {
                        if (data.status === 'ok') {
                            var tr = $(deleteIcon).parent().parent().parent().parent().parent().parent()[0];
                            $('#tabResults').dataTable().fnDeleteRow(tr);       // Remove the gene from the grid.
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
                    case 'filterGeneKey':
                        $('input[id^="filterGeneKey"]').val(value);
                        break;

                    case 'filterGeneName':
                        $('input[id^="filterGeneName"]').val(value);
                        break;

                    case 'filterGeneSymbol':
                        $('input[id^="filterGeneSymbol"]').val(value);
                        break;

                    case 'filterChromosome':
                        $('input[id^="filterChromosome"]').val(value);
                        break;

                    case 'filterGeneMgiReference':
                        $('input[id^="filterGeneMgiReference"]').val(value);
                        break;
                }
            }
            
            function clearFilter() {
                $('.filterComponent').val('');
                clearErrors();
                return false;
            }
            
            function handleDragStart(e) {
                var gene_key = $(this).data('gene_key');
                e.originalEvent.dataTransfer.setData('text/gene', gene_key);
            }

        </script>
        <title>Gene Management - list</title>
    </head>
    <body>
        <h2>Gene Management - list</h2>
        <span id="loginHeader">Logged in as user "<sec:authentication property='principal.username'/>"</span>
        
        <form:form method="get" modelAttribute="filter">
            <%-- NEW GENE --%>
            <input type="hidden" name="gene_key" value="0" />
            
            <input type="hidden" id="filterGeneKeyNew"          name="filterGeneKey"          class="filterComponent" value="${filter.gene_key}" />
            <input type="hidden" id="filterGeneNameNew"         name="filterGeneName"         class="filterComponent" value="${filter.geneName}" />
            <input type="hidden" id="filterGeneSymbolNew"       name="filterGeneSymbol"       class="filterComponent" value="${filter.geneSymbol}" />
            <input type="hidden" id="filterChromosomeNew"       name="filterChromosome"       class="filterComponent" value="${filter.chromosome}" />
            <input type="hidden" id="filterGeneMgiReferenceNew" name="filterGeneMgiReference" class="filterComponent" value="${filter.geneMgiReference}" />                                    
            <a href="${pageContext.request.contextPath}/curation/geneManagementDetail/edit?gene_key=&amp;filterGeneKey=&amp;filterGeneName=&amp;filterGeneSymbol=&amp;filterChromosome=&amp;filterGeneMgiReference="
               style="margin-left: 420px"
               target="geneManagementDetail"
               tabindex="8"
               title="Add new gene">
                 New
            </a>
        </form:form>
        
        <form:form modelAttribute="filter" method="get">
            <input type="hidden" id="filterGeneKeyGo"          name="filterGeneKey"          class="filterComponent" value="${filter.gene_key}" />
            <input type="hidden" id="filterGeneNameGo"         name="filterGeneName"         class="filterComponent" value="${filter.geneName}" />
            <input type="hidden" id="filterGeneSymbolGo"       name="filterGeneSymbol"       class="filterComponent" value="${filter.geneSymbol}" />
            <input type="hidden" id="filterChromosomeGo"       name="filterChromosome"       class="filterComponent" value="${filter.chromosome}" />
            <input type="hidden" id="filterGeneMgiReferenceGo" name="filterGeneMgiReference" class="filterComponent" value="${filter.geneMgiReference}" />
            <table id="tabFilter" style="border: 1px solid black">
                <thead>
                    <tr><th colspan="4" style="text-align: left">Filter</th></tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="4" style="text-align: right">
                            <%-- CLEAR FILTER --%>
                            <input type="button" value="Clear Filter" onclick="clearFilter();" tabindex="6" />
                            &nbsp;&nbsp;&nbsp;
                            <%-- GO --%>
                            <input type="submit" id="go" value="Go"
                                   tabindex="7"
                                   formaction="${pageContext.request.contextPath}/curation/geneManagementList/go"
                                   formtarget="geneManagementList" />
                        </td>
                    </tr>
                </tfoot>
                <tbody>
                    <%-- FILTER --%>
                    <tr>
                        <td><form:label path="gene_key">Gene ID:</form:label></td>
                        <td><form:input id="filterGeneKey" class="filterComponent" path="gene_key" onkeyup="updateFilter(this);" tabindex="1" /></td>
                        
                        <td><form:label path="chromosome">Chromosome:</form:label></td>
                        <td><form:input id="filterChromosome" class="filterComponent" path="chromosome" tabindex="4" /></td>
                    </tr>
                    <tr>
                        <td><form:label path="geneName">Gene name:</form:label></td>
                        <td><form:input id="filterGeneName" class="filterComponent" path="geneName" tabindex="2" /></td>
                        <td><form:label path="geneMgiReference">MGI reference:</form:label></td>
                        <td><form:input id="filterGeneMgiReference" class="filterComponent" path="geneMgiReference" tabindex="5" /></td>
                    </tr>
                    <tr>
                        <td><form:label path="geneSymbol">Gene symbol:</form:label></td>
                        <td><form:input id="filterGeneSymbol" class="filterComponent" path="geneSymbol" tabindex="3" /></td>
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
                        <c:when test="${fn:length(filteredGenesList) > 0}">
                            <tr style="border: 1px solid black">
                                <th>Actions</th>
                                <th>Gene ID</th>
                                <th>Gene Name</th>
                                <th>Gene Symbol</th>
                                <th>Bound Alleles</th>
                                <th>Chromosome</th>
                                <th>Species</th>
                                <th>Centimorgan</th>
                                <th>MGI Reference</th>
                                <th>Ensembl Reference</th>
                                <th>Promoter</th>
                                <th>Founder Line Number</th>
                                <th>Plasmid Construct</th>
                                <th>Cytoband</th>
                            </tr>
                        </c:when>
                    </c:choose>
                </thead>
                <tbody>
                    <c:forEach var="gene" items="${filteredGenesList}" varStatus="status">
                        <tr draggable="true" data-gene_key="${gene.gene_key}">
                            <td style="border: 1px solid black">
                                <table>
                                    <thead></thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <%-- EDIT GENE --%>
                                                <form method="get" action="${pageContext.request.contextPath}/curation/geneManagementDetail/edit">
                                                    <input type="hidden" name="gene_key" value="${gene.gene_key}" />

                                                    <input type="hidden" id="filterGeneKeyEdit"          name="filterGeneKey"          class="filterComponent" value="${filter.gene_key}" />
                                                    <input type="hidden" id="filterGeneNameEdit"         name="filterGeneName"         class="filterComponent" value="${filter.geneName}" />
                                                    <input type="hidden" id="filterGeneSymbolEdit"       name="filterGeneSymbol"       class="filterComponent" value="${filter.geneSymbol}" />
                                                    <input type="hidden" id="filterChromosomeEdit"       name="filterChromosome"       class="filterComponent" value="${filter.chromosome}" />
                                                    <input type="hidden" id="filterGeneMgiReferenceEdit" name="filterGeneMgiReference" class="filterComponent" value="${filter.geneMgiReference}" />
                                                    <a href="${pageContext.request.contextPath}/curation/geneManagementDetail/edit?gene_key=${gene.gene_key}&amp;filterGeneKey=&amp;filterGeneName=&amp;filterGeneSymbol=&amp;filterChromosome=&amp;filterGeneMgiReference="
                                                       target="geneManagementDetail"
                                                       title="Edit gene ${gene.gene_key}">
                                                        Edit
                                                    </a>
                                                </form>
                                            </td>

                                            <c:set var="boundAlleles" value="${gene.alleles}" />
                                            <c:set var="boundAllelesCount" value="${fn:length(boundAlleles)}" />

                                            <c:set var="boundAlleleKeys" value="" />
                                            <c:forEach var="allele" items="${boundAlleles}" varStatus="status">
                                                <c:if test="${status.index == 0}">
                                                    <c:set var="boundAlleleKeys" value="${allele.allele_key}" scope="page" />
                                                </c:if>
                                                <c:if test="${status.index > 0}">
                                                    <c:set var="boundAlleleKeys" value="${boundAlleleKeys}, ${allele.allele_key}" />
                                                </c:if>
                                            </c:forEach>
                                            <c:choose>
                                                <c:when test="${boundAllelesCount > 0}">
                                                    <td>
                                                        <input alt="Delete Gene" type="image" height="15" width="15" disabled="disabled"
                                                               src="${pageContext.request.contextPath}/images/delete.jpg"
                                                               title="Cannot delete gene ID ${gene.gene_key} as it is bound to allele(s) ${boundAlleleKeys}."
                                                               class="ui-state-disabled" />
                                                    </td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td>
                                                        <%-- DELETE GENE --%>
                                                        <input alt="Delete Gene" type="image" height="15" width="15" title="Delete gene ID ${gene.gene_key}"
                                                               src="${pageContext.request.contextPath}/images/delete.jpg"
                                                               onclick="deleteGene(${gene.gene_key}, this)"
                                                               formmethod="POST" />
                                                    </td>
                                                </c:otherwise>
                                            </c:choose>
                                        </tr>
                                    </tbody>
                                </table>
                            </td>
                            <td style="border: 1px solid black">${gene.gene_key}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(gene.name)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(gene.symbol)}</td>
                            <td style="border: 1px solid black">
                                <form>
                                    <c:choose>
                                        <c:when test="${boundAllelesCount eq 0}">
                                            <a href="${pageContext.request.contextPath}/curation/alleleManagementList/showFilter?filterAlleleKey=&amp;filterAlleleName=&amp;filterAlleleSymbol=&amp;filterAlleleMgiReference=&amp;filterGeneKey=&amp;filgerGeneName=&amp;filterGeneSymbol="
                                               target="alleleManagementList"
                                               title="Edit alleles">
                                                <i>None</i>
                                            </a>
                                        </c:when>
                                        <c:when test="${boundAllelesCount eq 1}">
                                            <a href="${pageContext.request.contextPath}/curation/alleleManagementList/go?filterAlleleKey=${boundAlleleKeys}&amp;filterAlleleName=&amp;filterAlleleSymbol=&amp;filterAlleleMgiReference=&amp;filterGeneKey=&amp;filterGeneName=&amp;filterGeneSymbol="
                                               target="alleleManagementList"
                                               title="Edit bound allele ${boundAlleleKeys}">
                                                ${boundAlleleKeys}
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${pageContext.request.contextPath}/curation/alleleManagementList/go?filterAlleleKey=${boundAlleleKeys}&amp;filterAlleleName=&amp;filterAlleleSymbol=&amp;filterAlleleMgiReference=&amp;filterGeneKey=&amp;filterGeneName=&amp;filterGeneSymbol="
                                               target="alleleManagementList"
                                               title="Edit bound alleles ${boundAlleleKeys}">
                                                ${boundAlleleKeys}
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </form>
                            </td>
                            <td style="border: 1px solid black">${fn:escapeXml(gene.chromosome)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(gene.species)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(gene.centimorgan)}</td>
                            <td style="border: 1px solid black">
                                <a href="javascript:lookupMgi('${fn:escapeXml(gene.mgiReference)}');">
                                    ${fn:escapeXml(gene.mgiReference)}
                                </a>
                            </td>
                            <td style="border: 1px solid black">
                                <a href="javascript:lookupEnsembl('${fn:escapeXml(gene.ensemblReference)}');">
                                    ${fn:escapeXml(gene.ensemblReference)}
                                </a>
                            </td>
                            <td style="border: 1px solid black">${fn:escapeXml(gene.promoter)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(gene.founderLineNumber)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(gene.plasmidConstruct)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(gene.cytoband)}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>