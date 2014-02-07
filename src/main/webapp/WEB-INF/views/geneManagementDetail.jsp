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

<%--
<c:set var="filteredGenesDAOList" value='${filteredGenesDAOList}'></c:set>
<c:set var="filteredGenesDAOListSize" value = '${filteredGenesDAOListSize}'></c:set>
--%>
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
            var urlRoot = "${pageContext.request.contextPath}/interfaces/geneManagementDetail";
            
            $(document).ready(function() {
                setSynonymHeadings();
                setMaxlengths();
            });
            
            function newSynonym() {
                $('#tabSynonyms').append('<tr>'
                                       +   '<td>'
                                       +      '<input type="hidden" name="hidSeedValues" value="seedValue" />'
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
                      var x = data['chromosome'];
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

        </script>
        <title>Gene Management - add/edit</title>
    </head>
    <body>
        <h2>Gene Management - add/edit</h2>
        <span id="loginHeader">Logged in as user "<sec:authentication property='principal.username'/>"</span>
        
        <br />
        <br />

        <form>
            <table style="border: none">
                <tr>
                    <td>
                        <div class="buttonAlignment">
                            <input type="submit" value="Save"
                                   formaction="${pageContext.request.contextPath}/interfaces/geneManagementDetail/save" formmethod="POST" />
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
                                            <td><label for="geneName">Gene name:</label></td>
                                            <td>
                                                <textarea id="geneName" name="geneName">${gene.name}</textarea>
                                                <br />
                                                <form:errors path="name" cssClass="error" />
                                            </td>
                                            
                                            <%-- GENE SYMBOL --%>
                                            <td><label for="geneSymbol">Gene symbol:</label></td>
                                            <td>
                                                <textarea id="geneSymbol" name="geneSymbol">${gene.symbol}</textarea>
                                                <br />
                                                <form:errors path="symbol" cssClass="error" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <%-- SPECIES --%>
                                            <td><label for="species">Species:</label></td>
                                            <td>
                                                <input id="species" name="species" value="${gene.species}" />
                                                <br />
                                                <form:errors path="species" cssClass="error" />
                                            </td>

                                            <%-- PLASMID CONSTRUCT --%>
                                            <td><label for="plasmidConstruct">Plasmid construct:</label></td>
                                            <td>
                                                <input id="plasmidConstruct" name="plasmidConstruct" value="${gene.plasmid_construct}" />
                                                <br />
                                                <form:errors path="plasmidConstruct" cssClass="error" />
                                            </td>
                                            
                                            <%-- PROMOTER --%>
                                            <td><label for="promoter">Promoter:</label></td>
                                            <td>
                                                <input id="promoter" name="promoter" value="${gene.promoter}" />
                                                <br />
                                                <form:errors path="promoter" cssClass="error" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <%-- CHROMOSOOME --%>
                                            <td><label for="chromosome">Chromosome:</label></td>
                                            <td>
                                                <input id="chromosome" name="chromosome" value="${gene.chromosome}" />
                                                <br />
                                                <form:errors path="chromosome" cssClass="error" />
                                            </td>
                                            
                                            <%-- CENTIMORGAN --%>
                                            <td><label for="centimorgan">Centimorgan:</label></td>
                                            <td>
                                                <input id="centimorgan" name="centimorgan" value="${gene.centimorgan}" />
                                                <br />
                                                <form:errors path="centimorgan" cssClass="error" />
                                            </td>
                                            
                                            <%-- CYTOBAND --%>
                                            <td><label for="cytoband">Cytoband:</label></td>
                                            <td>
                                                <input id="cytoband" name="cytoband" value="${gene.cytoband}" />
                                                <br />
                                                <form:errors path="cytoband" cssClass="error" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <%-- FOUNDER LINE NUMBER --%>
                                            <td><label for="founderLineNumber">Founder line number:</label></td>
                                            <td>
                                                <input id="founderLineNumber" name="founderLineNumber" value="${gene.founder_line_number}" />
                                                <br />
                                                <form:errors path="founderLineNumber" cssClass="error" />
                                            </td>
                                            
                                            <%-- MGI REFERENCE --%>
                                            <td>
                                                <label for="mgiReference">
                                                    <a href="javascript:lookupMGI();">
                                                        MGI reference:
                                                    </a>
                                                </label>
                                            </td>
                                            <td>
                                                <input id="mgiReference" name="mgiReference" value="${gene.mgi_ref}" />
                                                <br />
                                                <form:errors path="mgi_ref" cssClass="error" />
                                            </td>
                                            
                                            <%-- ENSEMBL REFERENCE --%>
                                            <td>
                                                <label for="ensemblReference">
                                                    <a href="javascript:lookupEnsembl();">
                                                        Ensembl reference:
                                                    </a>
                                                </label>
                                            </td>
                                            <td>
                                                <input id="ensemblReference" name="ensemblReference" value="${gene.ensembl_ref}" />
                                                <br />
                                                <form:errors path="ensembl_ref" cssClass="error" />
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
                                                        <input alt="Delete Synonym" type="image" height="15" width="15" title="Delete synonym ${synonym.id_syn}"
                                                               src="${pageContext.request.contextPath}/images/delete.jpg"
                                                               onclick="deleteSynonym(this); return false;"/>
                                                    </td>
                                                    <td>
                                                        <input name="synonymIds" readonly="readonly" value="${synonym.id_syn}" />
                                                    </td>
                                                    <td>
                                                        <input name="synonymNames" class="synonymName" value="${synonym.name}" />
                                                    </td>
                                                    <td>
                                                        <input name="synonymSymbols" class="synonymSymbol" value="${synonym.symbol}" />
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
                            <input type="submit" value="Save" formmethod="POST"
                                   formaction="${pageContext.request.contextPath}/interfaces/geneManagementDetail/save"
                                   />
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>