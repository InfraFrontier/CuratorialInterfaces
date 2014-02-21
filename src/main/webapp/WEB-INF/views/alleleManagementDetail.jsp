<%-- 
    Document   : alleleManagementDetail
    Created on : Feb 19, 2014, 9:53:55 AM
    Author     : mrelac
--%>

<!DOCTYPE html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags" %>

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
            
            #divGene.over {
                background: #cc99ff;
            }
            
        </style>
        
        <script>
            var urlGeneChooserRoot = "${pageContext.request.contextPath}/curation/geneChooser";
            var urlUtilRoot = "${pageContext.request.contextPath}/curation/util";
            var urlGeneListRoot = "${pageContext.request.contextPath}/curation/geneManagementList";
            
            $(document).ready(function() {
                setMaxlengths();
                clearErrors();
 
                $('#divGene')
                    .on('dragenter', handleDragEnter)
                    .on('dragover',  handleDragOver)
                    .on('dragleave', handleDragLeave)
                    .on('drop',      handleDrop)
                    .on('dragend',   handleDragLeave);
                $('#geneId').on('change', function(e) {
                    updateGeneDiv();
                });
            });
            
            function updateGeneDiv() {
                var newGeneId = $('#geneId').val();
                var geneName = '';
                var geneSymbol = '';
                if ((isInteger(newGeneId)) && (newGeneId > 0)) {
                    var gene = getGene($('#geneId').val());
                    if (gene !== null) {
                        geneName = gene.name;
                        geneSymbol = gene.symbol;
                    }
                }
                // Set gene details
                $('#geneName').val(geneName);
                $('#geneSymbol').val(geneSymbol);
            }
            
            function lookupMGI() {
                var id = $('#mgiReference').val();
                window.open("http://www.informatics.jax.org/searches/accession_report.cgi?id=MGI:" + id, "MgiWindow");
            }
            
            function setMaxlengths() {
                // Set alleles table field lengths.
                $.ajax({
                    url: urlUtilRoot + "/getFieldLengths"
                  , async: false
                  , data: { tablename: 'alleles' }
                  , dataType: "json"
                  , success: function( data ) {
                      $('#alleleSymbol').attr("maxLength", data['alls_form']);
                      $('#alleleName').attr("maxLength", data['name']);
                      $('#mgiReference').attr("maxLength", data['mgi_ref']);
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
                /*
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
*/
                return true;
            }
            
            function clearErrors() {
                $('.clientError').remove();
                $('.saveButton').attr("disabled", false);
            }
            
            function clearInputs() {
                $('#tabAlleleDetail :input').val('');
                clearErrors();
                
                return false;
            }
            
            function handleDragEnter(e) {
                this.classList.add('over');
            }
            function handleDragLeave(e) {
                this.classList.remove('over');
            }
            function handleDrop(e) {
                var id_gene = e.originalEvent.dataTransfer.getData('text');
                $('#geneId').val(id_gene);
                updateGeneDiv();
               
                return false;
            }
            function handleDragOver(e) {
                if (e.preventDefault) {
                    e.preventDefault();
                }
                e.originalEvent.dataTransfer.dropEffect = 'copy';
            }

            var geneChooserWindow;
            function showGeneChooser() {
                if (geneChooserWindow === undefined)
                     geneChooserWindow = window.open(urlGeneChooserRoot, "_blank", "width = 768, height=406");

                 return false;
            }

            function getGene(id) {
                var gene = null;
                $.ajax({
                    url: urlGeneListRoot + "/getGene"
                    , dataType: "json"
                    , async: false
                    , data: {id_gene: id}
                    , success: function(data) {
                        gene = data;
                    }
                });
                
                return gene;
            }
        </script>
        
        <title>Allele Management - add/edit</title>
    </head>
    <body>
        <h2>Allele Management - add/edit</h2>
        <span id="loginHeader">Logged in as user "${loggedInUser}"</span>

        <br />

        <form>
            <input type="hidden" name="id_allele" value="${allele.id_allele}" />
            
            <input type="hidden" name="filterAlleleId" value="${filter.alleleId}" />
            <input type="hidden" name="filterAlleleName" value="${filter.alleleName}" />
            <input type="hidden" name="filterAlleleSymbol" value="${filter.alleleSymbol}" />
            <input type="hidden" name="filterAlleleMgiReference" value="${filter.alleleMgiReference}" />
            <input type="hidden" name="filterGeneId" value="${filter.geneId}" />
            <input type="hidden" name="filterGeneName" value="${filter.geneName}" />
            <input type="hidden" name="filterGeneSymbol" value="${filter.geneSymbol}" />
            <table style="border: none">
                <tr>
                    <td>
                        <table style="border: 1px solid black">
                            <tr>
                                <td>
                                    <table id="tabAlleleDetail" style="border: none">
                                        <tr>
                                            <%-- ALLELE ID --%>
                                            <td><label id="labAlleleId">Allele ID:</label></td>
                                            <td style="border: 0"><input name="alleleId" value="${allele.id_allele == 0 ? '' : allele.id_allele}" readonly="readonly" /></td>
                                            
                                            <%-- ALLELE NAME --%>
                                            <td><form:label for="alleleName" path="allele.name" >Allele name:</form:label></td>
                                            <td>
                                                <form:textarea id="alleleName" path="allele.name" value="${allele.name}" placeholder="Required field" />
                                                <br />
                                                <form:errors path="allele.name" cssClass="error" />
                                            </td>
                                            
                                            <%-- ALLELE SYMBOL --%>
                                            <td><form:label for="alleleSymbol" path="allele.symbol">Allele symbol:</form:label></td>
                                            <td>
                                                <form:textarea id="alleleSymbol" path="allele.symbol" value="${allele.symbol}" />
                                                <br />
                                                <form:errors path="allele.symbol" cssClass="error" />
                                            </td>
                                        </tr>
                                        <tr>
                                            
                                            <%-- GENE --%>
                                            <td>
                                                <label>Gene:</label>
                                                <input alt="Click for gene list." type="image" height="15" width="15" title="Click for gene list."
                                                       src="${pageContext.request.contextPath}/images/geneChooser.jpg"
                                                       onclick="showGeneChooser();"
                                                />
                                            </td>
                                            <td>
                                                <div id="divGene" style="border: 1px solid black">
                                                    <table>
                                                        <tr>
                                                            <td><label>Id:</label></td>
                                                            <td>
                                                                <form:input id="geneId" path="allele.gene.id_gene" placeholder="Required field"
                                                                            value="${allele.gene.id_gene > 0 ? allele.gene.id_gene : ''}" />
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            
                                                            <td><label>Name:</label></td>
                                                            <td>
                                                                <form:textarea id="geneName" path="allele.gene.name" readonly="true"
                                                                            value="${allele.gene.name}" type="text" />
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            
                                                            <td><label>Symbol</label></td>
                                                            <td>
                                                                <form:textarea id="geneSymbol" path="allele.gene.symbol" readonly="true"
                                                                            value="${allele.gene.symbol}" type="text" />
                                                                <form:errors path="allele.gene.id_gene" cssClass="error" />
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </td>
                                            
                                            <%-- MGI REFERENCE --%>
                                            <td>
                                                <form:label for="mgiReference" path="allele.mgi_ref">
                                                    <a href="javascript:lookupMGI();">
                                                        MGI reference:
                                                    </a>
                                                </form:label>
                                            </td>
                                            <td>
                                                <form:input id="mgiReference" path="allele.mgi_ref" value="${allele.mgi_ref}" />
                                                <br />
                                                <form:errors path="allele.mgi_ref" cssClass="error" />
                                            </td>
                                            <td colspan="2"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td style="float: right">
                        <table>
                            <tr>
                                <td>
                                    <div class="buttonAlignment">
                                        <input type="submit" value="Back"
                                               formmethod="GET"
                                               formaction="${pageContext.request.contextPath}/curation/alleleManagementDetail/showList" />
                                    </div>
                                </td>
                                <td>&nbsp;&nbsp;&nbsp;</td>
                                <td>
                                    <div class="buttonAlignment">
                                        <input type="button" value="Clear"
                                               onclick="clearInputs();" />
                                    </div>
                                </td>
                                <td>&nbsp;&nbsp;&nbsp;</td>
                                <td>
                                    <div class="buttonAlignment">
                                        <input type="submit" value="Save" class="saveButton"
                                               formmethod="POST"
                                               formaction="${pageContext.request.contextPath}/curation/alleleManagementDetail/save"
                                               onclick="clearErrors();" />
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr><td><form:errors path="allele.*" cssClass="error" /></td></tr>
            </table>
        </form>
    </body>
</html>