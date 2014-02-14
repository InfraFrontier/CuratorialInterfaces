<%-- 
    Document   : geneManagementDetail
    Created on : Dec 2, 2013, 1:43:09 PM
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
        <style>
            .error {
                    color: #ff0000;
            }
            .clientError {
                    color: #ff0000;
            }

            .errorblock {
                    color: #000;
                    background-color: #ffEEEE;
                    border: 3px solid #ff0000;
                    padding: 8px;
                    margin: 16px;
            }
            
            .buttonAlignment {
                text-align: right;
            }
        </style>
        
        <script>
            var urlRoot = "${pageContext.request.contextPath}/curation/geneManagementDetail";
            
            $(document).ready(function() {
                setSynonymHeadings();
                setMaxlengths();
                clearErrors();
            });
            
            function newSynonym() {
                $('#tabSynonyms').append('<tr>'
                                       +   '<td>'
                                       +      '<input type="hidden" name="hidSeedValues" value="seedValue" />'
                                       +      '<input type="hidden" name="synonymsAreDirty" class="clsIsDirty" value="true" />'
                                       +      '<input alt="Delete Synonym" type="image" height="15" width="15" title="Delete Synonym" onclick="deleteSynonym(this);" src="${pageContext.request.contextPath}/images/delete.jpg" />'
                                       +   '</td>'
                                       +   '<td>'
                                       +      '<input type="text" name="synonymIds" readonly="readonly" />'
                                       +   '</td>'
                                       +   '<td>'
                                       +      '<input type="text" name="synonymNames" class="synonymName" />'
                                       +   '</td>'
                                       +   '<td>'
                                       +      '<input type="text" name="synonymSymbols" class="synonymSymbol" />'
                                       +   '</td>'
                                       + '</tr>');
                setSynonymHeadings();
                setMaxGeneSynonymLengths();
                return false;
            }
            
            function deleteSynonym(deleteIcon) {
                $(deleteIcon).parent().parent().remove();
                setSynonymHeadings();

                return false;
            }
            
            function setSynonymHeadings() {
                var displayFormat = ($('#tabSynonyms > tbody > tr').length > 0 ? 'table-row' : 'none');
                $('#trSynonymHeadings').css('display', displayFormat);
            }
            
            function lookupMGI() {
                var id = $('#mgiReference').val();
                window.open("http://www.informatics.jax.org/searches/accession_report.cgi?id=MGI:" + id, "MgiWindow");
            }
            
            function lookupEnsembl() {
                var id = $('#ensemblReference').val();
                window.open("http://www.ensembl.org/Mus_musculus/geneview?gene=" + id, "EnsemblWindow");
            }
            
            function setMaxlengths() {
                setMaxGeneLengths();
                setMaxGeneSynonymLengths();
            }
            
            function setMaxGeneLengths() {
                // Set genes table field lengths.
                $.ajax({
                    url: urlRoot + "/getFieldLengths"
                  , async: false
                  , data: { tablename: 'genes' }
                  , dataType: "json"
                  , success: function( data ) {
                      $('#chromosome').attr("maxLength", data['chromosome']);
                      $('#cytoband').attr("maxLength", data['cytoband']);
                      $('#ensemblReference').attr("maxLength", data['ensembl_ref']);
                      $('#founderLineNumber').attr("maxLength", data['founder_line_number']);
                      $('#mgiReference').attr("maxLength", data['mgi_ref']);
                      $('#geneName').attr("maxLength", data['name']);
                      $('#plasmidConstruct').attr("maxLength", data['plasmid_construct']);
                      $('#promoter').attr("maxLength", data['promoter']);
                      $('#species').attr("maxLength", data['species']);
                      $('#geneSymbol').attr("maxLength", data['symbol']);
                    }
                });
            }
                
            function setMaxGeneSynonymLengths() {
                // Set syn_genes table field lengths.
                $.ajax({
                    url: urlRoot + "/getFieldLengths"
                  , async: false
                  , data: { tablename: 'syn_genes' }
                  , dataType: "json"
                  , success: function( data ) {
                      $('.synonymName').attr("maxLength", data['name']);
                      $('.synonymSymbol').attr("maxLength", data['symbol']);
                    }
                });
            }
            
            function dataChanged(inputControl) {
                var tr = $(inputControl).parent().parent();
                var isDirty = $(tr).find('.clsIsDirty');
                $(isDirty).val('true');
                
                return false;
            }
            
            function validate() {
                // Remove any filter validation messages.
                clearErrors();

                var filterIdValue = $('#centimorgan').val();
                var notAnInteger = ((filterIdValue !== '') && (!isInteger(filterIdValue)));
                if (notAnInteger) {
                    var errMsg = '<span id="centimorgan.errors" class="clientError">Please enter an integer.</span>';
                    $('#centimorgan').parent().append(errMsg);
                    $('.saveButton').attr("disabled", true);
                    return false;
                }

                return true;
            }
            
            function clearErrors() {
                $('.clientError').remove();
                $('.saveButton').attr("disabled", false);
            }
            
        </script>
        
        <title>Gene Management - add/edit</title>
    </head>
    <body>
        <h2>Gene Management - add/edit</h2>
        <%--
            This gets the logged-in user but is deprecated. Left here for handy syntax cheat sheet.
            <sec:authentication property='principal.username' var="loggedInUser" />
        --%>
        <span id="loginHeader">Logged in as user "${loggedInUser}"</span>

        <br />

        <form>
            <input type="hidden" name="id_gene" value="${gene.id_gene}" />
            
            <input type="hidden" name="filterGeneId" value="${filter.geneId}" />
            <input type="hidden" name="filterGeneName" value="${filter.geneName}" />
            <input type="hidden" name="filterGeneSymbol" value="${filter.geneSymbol}" />
            <input type="hidden" name="filterChromosome" value="${filter.chromosome}" />
            <input type="hidden" name="filterMGIReference" value="${filter.mgiReference}" />
            <table style="border: none">
                <tr>
                    <td>
                        <div class="buttonAlignment">
                            <input type="submit" value="Save" class="saveButton"
                                   formmethod="POST"
                                   formaction="${pageContext.request.contextPath}/curation/geneManagementDetail/save"
                                   onclick="clearErrors();" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table style="border: 1px solid black">
                            <tr>
                                <td>
                                    <table style="border: none">
                                        <tr>
                                            <%-- GENE ID --%>
                                            <td><label id="labGeneId">Gene ID:</label></td>
                                            <td style="border: 0"><input name="geneId" value="${gene.id_gene == 0 ? '' : gene.id_gene}" readonly="readonly" /></td>
                                            
                                            <%-- GENE NAME --%>
                                            <td><form:label for="geneName" path="gene.name">Gene name:</form:label></td>
                                            <td>
                                                <form:input id="geneName" name="geneName" path="gene.name" value="${gene.name}" />
                                                <br />
                                                <form:errors path="gene.name" cssClass="error" />
                                            </td>
                                            
                                            <%-- GENE SYMBOL --%>
                                            <td><form:label for="geneSymbol" path="gene.symbol">Gene symbol:</form:label></td>
                                            <td>
                                                <form:textarea id="geneSymbol" name="geneSymbol" path="gene.symbol" value="${gene.symbol}" />
                                                <br />
                                                <form:errors path="gene.symbol" cssClass="error" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <%-- SPECIES --%>
                                            <td><form:label for="species" path="gene.species">Species:</form:label></td>
                                            <td>
                                                <form:input id="species" name="species" path="gene.species" value="${gene.species}" />
                                                <br />
                                                <form:errors path="gene.species" cssClass="error" />
                                            </td>

                                            <%-- PLASMID CONSTRUCT --%>
                                            <td><form:label for="plasmidConstruct" path="gene.plasmid_construct">Plasmid construct:</form:label></td>
                                            <td>
                                                <form:input id="plasmidConstruct" name="plasmidConstruct" path="gene.plasmid_construct" value="${gene.plasmid_construct}" />
                                                <br />
                                                <form:errors path="gene.plasmid_construct" cssClass="error" />
                                            </td>
                                            
                                            <%-- PROMOTER --%>
                                            <td><form:label for="promoter" path="gene.promoter">Promoter:</form:label></td>
                                            <td>
                                                <form:input id="promoter" name="promoter" path="gene.promoter" value="${gene.promoter}" />
                                                <br />
                                                <form:errors path="gene.promoter" cssClass="error" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <%-- CHROMOSOOME --%>
                                            <td><form:label for="chromosome" path="gene.chromosome">Chromosome:</form:label></td>
                                            <td>
                                                <form:input id="chromosome" name="chromosome" path="gene.chromosome" value="${gene.chromosome}" />
                                                <br />
                                                <form:errors path="gene.chromosome" cssClass="error" />
                                            </td>
                                            
                                            <%-- CENTIMORGAN --%>
                                            <td><form:label for="centimorgan" path="gene.centimorgan">Centimorgan:</form:label></td>
                                            <td>
                                                <form:input id="centimorgan" name="centimorgan" path="gene.centimorgan" value="${gene.centimorgan}" onkeyup="validate();" />
                                                <br />
                                                <form:errors path="gene.centimorgan" cssClass="error" />
                                            </td>
                                            
                                            <%-- CYTOBAND --%>
                                            <td><form:label for="cytoband" path="gene.cytoband">Cytoband:</form:label></td>
                                            <td>
                                                <form:input id="cytoband" name="cytoband" path="gene.cytoband" value="${gene.cytoband}" />
                                                <br />
                                                <form:errors path="gene.cytoband" cssClass="error" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <%-- FOUNDER LINE NUMBER --%>
                                            <td><form:label for="founderLineNumber" path="gene.founder_line_number">Founder line number:</form:label></td>
                                            <td>
                                                <form:input id="founderLineNumber" name="founderLineNumber" path="gene.founder_line_number" value="${gene.founder_line_number}" />
                                                <br />
                                                <form:errors path="gene.founder_line_number" cssClass="error" />
                                            </td>
                                            
                                            <%-- MGI REFERENCE --%>
                                            <td>
                                                <form:label for="mgiReference" path="gene.mgi_ref">
                                                    <a href="javascript:lookupMGI();">
                                                        MGI reference:
                                                    </a>
                                                </form:label>
                                            </td>
                                            <td>
                                                <form:input id="mgiReference" name="mgi_ref" path="gene.mgi_ref" value="${gene.mgi_ref}" />
                                                <br />
                                                <form:errors path="gene.mgi_ref" cssClass="error" />
                                            </td>
                                            
                                            <%-- ENSEMBL REFERENCE --%>
                                            <td>
                                                <form:label for="ensemblReference" path="gene.ensembl_ref">
                                                    <a href="javascript:lookupEnsembl();">
                                                        Ensembl reference:
                                                    </a>
                                                </form:label>
                                            </td>
                                            <td>
                                                <form:input id="ensemblReference" name="ensemblReference" path="gene.ensembl_ref" value="${gene.ensembl_ref}" />
                                                <br />
                                                <form:errors path="gene.ensembl_ref" cssClass="error" />
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr><td><hr /></td></tr>
                            <tr>
                                <td>
                                    <table id="tabSynonyms" style="border: none">
                                        <thead style="border: 1px solid black">
                                            <tr>
                                                <td>Synonyms:</td>
                                                <td colspan="3" align="right">
                                                    <input type="submit" value="New" title="Add new synonym" onclick="newSynonym();return false;" />
                                                </td>
                                            </tr>
                                            <tr id="trSynonymHeadings">
                                                <th>Actions</th>
                                                <th>Id</th>
                                                <th>Name</th>
                                                <th>Symbol</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="synonym" items="${gene.synonyms}" varStatus="status">
                                                <tr>
                                                    <td>
                                                        <input type="hidden" name="hidSeedValues" value="seedValue" />
                                                        <input type="hidden" name="synonymsAreDirty" class="clsIsDirty" value="false" />
                                                        <input alt="Delete Synonym" type="image" height="15" width="15" title="Delete synonym ${synonym.id_syn}"
                                                               src="${pageContext.request.contextPath}/images/delete.jpg"
                                                               onclick="deleteSynonym(this); return false;"/>
                                                    </td>
                                                    <td>
                                                        <input name="synonymIds" readonly="readonly" value="${synonym.id_syn}" />
                                                    </td>
                                                    <td>
                                                        <input name="synonymNames" class="synonymName" value="${synonym.name}" onchange="dataChanged(this);" />
                                                    </td>
                                                    <td>
                                                        <input name="synonymSymbols" class="synonymSymbol" value="${synonym.symbol}" onchange="dataChanged(this);" />
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="buttonAlignment">
                            <input type="submit" value="Save" class="saveButton"
                                   formmethod="POST"
                                   formaction="${pageContext.request.contextPath}/curation/geneManagementDetail/save"
                                   onclick="clearErrors();" />
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>