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
            var geneIds = null;
            var chromosomes = null;

            $(document).ready(function() {
                populateFilterAutocompletes();
                setResultsControls();
                
                $('#go').click(function() {
                    return validate();
                });

                // Remove filter validation message (jira bug EMMA-545)
                clearErrors();
                
                $('#tabResults').dataTable();
            });

            function clearErrors() {
     //           $('#tabFilter tbody .filterErrorTr0').remove();
                $('.clientError').remove();
                $('#go').attr("disabled", false);
            }

            function validate() {
                // Remove any error validation messages.
                clearErrors();

                var filterGeneIdValue = $('#geneId').val();
                var error = ((filterGeneIdValue !== '') && ( ! isInteger(filterGeneIdValue)));
                if (error) {
     //               $('#tabFilter tbody tr:eq(0)').after('<tr class="filterErrorTr0"><td colspan="4" style="color: red">Please enter an integer.</td></tr>');
     //               $('#geneId').addClass('error');

                    var errMsg = '<br class="clientError" /><span class="clientError">Please enter an integer.</span>';
                    $('#geneId').parent().append(errMsg);
                    $('#go').attr("disabled", true);

                    return false;
                }

                return true;
            }

            function populateFilterAutocompletes() {
                var urlRoot = "${pageContext.request.contextPath}/curation/geneManagementList";
                

            // This slows down the page very noticably. Since its contribution to the user experience is minimal, let's try leaving it out for now.
            /*
                if (geneIds === null)
                    populateGeneIds(urlRoot);
                $("#geneId").autocomplete({
                    source: geneIds,
                    minLength: 1
                });
                */

                $("#geneName").autocomplete({
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

                $("#geneSymbol").autocomplete({
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
                
                $("#chromosome").autocomplete({
                    source: chromosomes,
                    minLength: 1
                });

                $("#mgiReference").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                            url: urlRoot + "/getMGIReferences",
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
                }).done(function(data) {
                    geneIds = data.slice(0);
                });
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
                    , data: {'id_gene': id}
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
                    case 'geneId':
                        $('input[id^="filterGeneId"]').val(value);
                        break;

                    case 'geneName':
                        $('input[id^="filterGeneName"]').val(value);
                        break;

                    case 'geneSymbol':
                        $('input[id^="filterGeneSymbol"]').val(value);
                        break;

                    case 'chromosome':
                        $('input[id^="filterChromosome"]').val(value);
                        break;

                    case 'mgiReference':
                        $('input[id^="filterMGIReference"]').val(value);
                        break;
                }
            }
            
            function clearFilter() {
                $('.filterComponent').val('');
                clearErrors();
                return false;
            }

        </script>
        <title>Gene Management - list</title>
    </head>
    <body>
        <h2>Gene Management - list</h2>
        <span id="loginHeader">Logged in as user "<sec:authentication property='principal.username'/>"</span>
        
        <form:form method="get" modelAttribute="filter">
            <%-- NEW GENE --%>
            <input type="hidden" name="id_gene" value="0" />
            
            <input type="hidden" id="filterGeneIdNew" name="filterGeneId" value="${filter.geneId}" />
            <input type="hidden" id="filterGeneNameNew" name="filterGeneName" value="${filter.geneName}" />
            <input type="hidden" id="filterGeneSymbolNew" name="filterGeneSymbol" value="${filter.geneSymbol}" />
            <input type="hidden" id="filterChromosomeNew" name="filterChromosome" value="${filter.chromosome}" />
            <input type="hidden" id="filterMGIReferenceNew" name="filterMGIReference" value="${filter.mgiReference}" />
            <input type="submit" value="New" style="margin-left: 420px; margin-bottom: 5px"
                   formmethod="get"
                   formaction="${pageContext.request.contextPath}/curation/geneManagementDetail/editGene" />
        </form:form>
        
        <form:form modelAttribute="filter" method="get">
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
                                   formaction="${pageContext.request.contextPath}/curation/geneManagementList/go"/>
                        </td>
                    </tr>
                </tfoot>
                <tbody>
                    <tr>
                        <td><form:label path="geneId">Gene Id:</form:label></td>
                        <td><form:input id="geneId" class="filterComponent" path="geneId" onkeyup="updateFilter(this);" /></td>
                        
                        <td><form:label path="chromosome">Chromosome:</form:label></td>
                        <td><form:input id="chromosome" class="filterComponent" path="chromosome" onchange="updateFilter(this);" /></td>
                    </tr>
                    <tr>
                        <td><form:label path="geneName">Gene name:</form:label></td>
                        <td><form:input id="geneName" class="filterComponent" path="geneName" onchange="updateFilter(this);" /></td>
                        <td><form:label path="mgiReference">MGI reference:</form:label></td>
                        <td><form:input id="mgiReference" class="filterComponent" path="mgiReference" onchange="updateFilter(this);" /></td>
                    </tr>
                    <tr>
                        <td><form:label path="geneSymbol">Gene symbol:</form:label></td>
                        <td><form:input id="geneSymbol" class="filterComponent" path="geneSymbol" onchange="updateFilter(this);" /></td>
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
                                <th>Bound Alleles </th>
                                <th>Gene ID</th>
                                <th>Gene Name</th>
                                <th>Gene Symbol</th>
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
                        <tr>
                            <td style="border: 1px solid black">
                                <table>
                                    <thead></thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <%-- EDIT GENE --%>
                                                <form method="get" action="${pageContext.request.contextPath}/curation/geneManagementDetail/editGene">
                                                    <input type="hidden" name="id_gene" value="${gene.id_gene}" />

                                                    <input type="hidden" id="filterGeneIdEdit" name="filterGeneId" value="${filter.geneId}" />
                                                    <input type="hidden" id="filterGeneNameEdit" name="filterGeneName" value="${filter.geneName}" />
                                                    <input type="hidden" id="filterGeneSymbolEdit" name="filterGeneSymbol" value="${filter.geneSymbol}" />
                                                    <input type="hidden" id="filterChromosomeEdit" name="filterChromosome" value="${filter.chromosome}" />
                                                    <input type="hidden" id="filterMGIReferenceEdit" name="filterMGIReference" value="${filter.mgiReference}" />
                                                    <input alt="Edit Gene" type="image" height="15" width="15" title="Edit gene ${gene.id_gene}"
                                                           src="${pageContext.request.contextPath}/images/edit.jpg" />
                                                </form>
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
                                                               src="${pageContext.request.contextPath}/images/delete.jpg"
                                                               title="Cannot delete gene ${gene.id_gene} as it is bound to allele ID ${boundAlleleIds}."
                                                               class="ui-state-disabled" />
                                                    </td>
                                                </c:when>
                                                <c:when test="${boundAllelesCount > 0}">
                                                    <td>
                                                        <%-- DELETE GENE --%>
                                                        <input alt="Delete Gene" type="image" height="15" width="15" disabled="disabled"
                                                               src="${pageContext.request.contextPath}/images/delete.jpg"
                                                               title="Cannot delete gene ${gene.id_gene} as it is bound to allele IDs ${boundAlleleIds}."
                                                               class="ui-state-disabled" />
                                                    </td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td>
                                                        <input alt="Delete Gene" type="image" height="15" width="15" title="Delete gene ${gene.id_gene}"
                                                               src="${pageContext.request.contextPath}/images/delete.jpg"
                                                               onclick="deleteGene(${gene.id_gene}, this)"
                                                               formmethod="POST" />
                                                    </td>
                                                </c:otherwise>
                                            </c:choose>
                                        </tr>
                                    </tbody>
                                </table>
                            </td>
                            <td style="border: 1px solid black">
                                <c:choose>
                                    <c:when test="${boundAllelesCount eq 0}">
                                        <a href="alleleManagementList" title="Edit alleles">
                                            <i>None</i>
                                        </a>
                                    </c:when>
                                    <c:when test="${boundAllelesCount eq 1}">
                                        <a href="alleleManagementList?alleleIds=${boundAlleleIds}" title="Edit bound allele ${boundAlleleIds}">
                                            ${boundAlleleIds}
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="alleleManagementList?alleleIds=${boundAlleleIds}" title="Edit bound alleles ${boundAlleleIds}">
                                            ${boundAlleleIds}
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td style="border: 1px solid black">${gene.id_gene}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(gene.name)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(gene.symbol)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(gene.chromosome)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(gene.species)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(gene.centimorgan)}</td>
                            <td style="border: 1px solid black">
                                <a href="javascript:lookupMGI('${fn:escapeXml(gene.mgi_ref)}');">
                                    ${fn:escapeXml(gene.mgi_ref)}
                                </a>
                            </td>
                            <td style="border: 1px solid black">
                                <a href="javascript:lookupEnsembl('${fn:escapeXml(gene.ensembl_ref)}');">
                                    ${fn:escapeXml(gene.ensembl_ref)}
                                </a>
                            </td>
                            <td style="border: 1px solid black">${fn:escapeXml(gene.promoter)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(gene.founder_line_number)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(gene.plasmid_construct)}</td>
                            <td style="border: 1px solid black">${fn:escapeXml(gene.cytoband)}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>