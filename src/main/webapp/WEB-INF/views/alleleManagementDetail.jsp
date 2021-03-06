<%-- 
    Document   : alleleManagementDetail
    Created on : Feb 19, 2014, 9:53:55 AM
    Author     : mrelac
--%>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", -1);
    response.setHeader("Cache-Control", "no-store");
%>

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
                    background-color: #FFEEEE;
                    border: 3px solid #FF0000;
                    padding: 8px;
                    margin: 16px;
            }
            
            .buttonAlignment {
                text-align: right;
            }
            
            #divGene.over {
                background: #EED4EE;
            }
            
        </style>
        
        <script>
            var urlCurationlRoot = "${pageContext.request.contextPath}/curation";
            var urlUtilRoot = "${pageContext.request.contextPath}/curation/util";
            
            $(document).ready(function() {
                setMaxlengths();
                populateFilterAutocompletes();
                clearErrors();
 
                $('#divGene')
                    .on('dragover',  handleDragOver)
                    .on('dragleave', handleDragLeave)
                    .on('drop',      handleDrop)
                $('#gene_key').on('change', function(e) {
                    updateGeneDiv();
                });
                $('#alleleName').on('keyup', function(e) {
                    validate();
                });
            });
            
            function updateGeneDiv() {
                var geneName = '';
                var geneSymbol = '';
                
                validate();
                
                var newGeneId = $('#gene_key').val();
                if ((isInteger(newGeneId)) && (newGeneId > 0)) {
                    var gene = getGene($('#gene_key').val());
                    if (gene !== null) {
                        geneName = gene.name;
                        geneSymbol = gene.symbol;
                    }
                }
                // Set gene details
                $('#geneName').val(geneName);
                $('#geneSymbol').val(geneSymbol);
            }
            
            function lookupMGI(mgiReference) {
                var id = $('#mgiReference').val();
                window.open("http://www.informatics.jax.org/allele/MGI:" + mgiReference + "?page=alleleDetail&id=MGI:" + mgiReference, "mgiAlleleReference");
            }
            
            function setMaxlengths() {
                // Set table field maximum lengths.
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

            function validate() {
                clearErrors();
                
                var errMsg = '';
                var newGene_key = $('#gene_key').val();
                var geneName = '';
                var geneSymbol = '';
                var errorCount = 0;
                
                // Validate allele name is not empty.
                var alleleName = $('#alleleName').val().trim();
                if (alleleName.length === 0) {
                    errorCount++;
                    errMsg = '<br class="clientError" /><span id="gene_key.errors" class="clientError">Please choose a name.</span>';
                    $('#alleleName').parent().append(errMsg);
                }
                
                // Validate gene_key is an int and describes a valid gene.
                if ((isInteger(newGene_key)) && (newGene_key > 0)) {
                    var gene = getGene($('#gene_key').val());
                    if (gene !== null) {
                        geneName = gene.name;
                        geneSymbol = gene.symbol;
                    } else {
                        errorCount++;
                        errMsg = '<br class="clientError" /><span id="gene_key.errors" class="clientError">Please choose a valid gene.</span>';
                        $('#gene_key').parent().append(errMsg);
                    }
                } else {
                    errorCount++;
                    errMsg = '<br class="clientError" /><span id="gene_key.errors" class="clientError">Please choose a valid gene.</span>';
                    $('#gene_key').parent().append(errMsg);
                }
                // Set gene details
                $('#geneName').val(geneName);
                $('#geneSymbol').val(geneSymbol);
                
                if (errorCount > 0) {
                    $('.saveButton').attr("disabled", true);
                    return false;
                } else {
                    $('.saveButton').attr("disabled", false);
                    return true;
                }
                
                return false;
            }

            function clearErrors() {
                $('.clientError').remove();
                $('.saveButton').attr("disabled", false);
            }
            
            function clearInputs() {
                $('#tabAlleleDetail :input').val('');
                clearErrors();
                $('.error').remove();
                
                return false;
            }
            
            function handleDragOver(e) {
                var isGene = (e.originalEvent.dataTransfer.types.indexOf('text/gene') >= 0);
                if (isGene) {
                    $('#divGene').addClass('over');
                    e.preventDefault();
                    e.originalEvent.dataTransfer.dropEffect = 'copy';
                }
                
                return false;
            }
            function handleDragLeave(e) {
                var isGene = (e.originalEvent.dataTransfer.types.indexOf('text/gene') >= 0);
                if (isGene) {
                    $(this).removeClass('over');
                }
                
                return false;
            }
            
            function handleDrop(e) {
                var isGene = (e.originalEvent.dataTransfer.types.indexOf('text/gene') >= 0);
                if (isGene) {
                    $('#divGene').removeClass('over');
                    var gene_key = e.originalEvent.dataTransfer.getData('text/gene');
                    $('#gene_key').val(gene_key);
                    updateGeneDiv();
                }
                
                return false;
            }

            function showGeneChooser() {
                 window.open(urlCurationlRoot + "/geneChooser", "_blank", "width = 768, height=406");

                 return false;
            }

            function getGene(id) {
                var gene = null;
                $.ajax({
                      url:      urlUtilRoot + "/getGene"
                    , dataType: "json"
                    , async:    false
                    , data:     {'gene_key': id}
                    , success:  function(data) {
                        gene = data;
                    }
                });
                
                return gene;
            }
            
            function populateFilterAutocompletes() {
                $("#mgiReference").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                              minLength: 0
                            , url: urlCurationlRoot + "/alleleManagementList/getAlleleMgiReferences"
                            , dataType: "json"
                            , data: {filterTerm: request.term}
                            , success: function(data) {
                                response($.map(data, function(item) {
                                    return {label: item};
                                }));
                            }
                        });
                    }
                });
            }
            
        </script>
        
        <title>Allele Management - add/edit</title>
    </head>
    <body>
        <h2>Allele Management - add/edit</h2>
        <span id="loginHeader">Logged in as user "${loggedInUser}"</span>

        <br />

        <form>
            <input type="hidden" name="filterAlleleKey"          value="${filter.allele_key}" />
            <input type="hidden" name="filterAlleleName"         value="${filter.alleleName}" />
            <input type="hidden" name="filterAlleleSymbol"       value="${filter.alleleSymbol}" />
            <input type="hidden" name="filterAlleleMgiReference" value="${filter.alleleMgiReference}" />
            <input type="hidden" name="filterGeneKey"            value="${filter.gene_key}" />
            <input type="hidden" name="filterGeneName"           value="${filter.geneName}" />
            <input type="hidden" name="filterGeneSymbol"         value="${filter.geneSymbol}" />
            
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
                                            <td style="border: 0"><form:input name="allele_key" value="${allele.allele_key}" readonly="true" path="allele.allele_key" /></td>
                                            
                                            <%-- ALLELE NAME --%>
                                            <td><form:label for="alleleName" path="allele.name" >Allele name:</form:label></td>
                                            <td>
                                                <form:textarea id="alleleName" path="allele.name" value="${allele.name}" placeholder="Required field" />
                                                <form:errors path="allele.name" cssClass="error" />
                                            </td>
                                            
                                            <%-- ALLELE SYMBOL --%>
                                            <td><form:label for="alleleSymbol" path="allele.symbol">Allele symbol:</form:label></td>
                                            <td>
                                                <form:textarea id="alleleSymbol" path="allele.symbol" value="${allele.symbol}" />
                                                <br />
                                                <form:errors path="allele.symbol" cssClass="error" />
                                            </td>
                                            
                                            <%-- ALLELE MGI REFERENCE --%>
                                            <td>
                                                <form:label for="mgiReference" path="allele.mgiReference">
                                                    <a href="javascript:lookupMGI('${fn:escapeXml(allele.mgiReference)}');">
                                                        Allele MGI reference:
                                                    </a>
                                                </form:label>
                                            </td>
                                            <td>
                                                <form:input id="mgiReference" path="allele.mgiReference" value="${allele.mgiReference}" />
                                                <br />
                                                <form:errors path="allele.mgiReference" cssClass="error" />
                                            </td>
                                        </tr>
                                        <tr><td colspan="8">&nbsp;</td></tr>
                                        <tr>
                                            <td>
                                                
                                            <%-- GENE --%>
                                                <label>Gene:</label>
                                                <input alt="Click for gene list." type="image" height="15" width="15" title="Click for gene list."
                                                       src="${pageContext.request.contextPath}/images/geneChooser.jpg"
                                                       onclick="showGeneChooser();return false;"
                                                />
                                            </td>
                                            <td colspan="7">
                                                <div id="divGene" style="border: 1px solid gray">
                                                    <table>
                                                        <tr>
                                                            <td><label>Gene Id:</label></td>
                                                            <td>
                                                                <form:input id="gene_key" path="allele.gene_key" placeholder="Required field"
                                                                            value="${allele.gene.gene_key}" />
                                                                <form:errors path="allele.gene.gene_key" cssClass="error" />
                                                            </td>
                                                            <td><label>Gene Name:</label></td>
                                                            <td>
                                                                <form:textarea id="geneName" path="allele.gene.name" readonly="true"
                                                                            value="${allele.gene.name}" type="text" />
                                                            </td>
                                                            <td><label>Gene Symbol</label></td>
                                                            <td>
                                                                <form:textarea id="geneSymbol" path="allele.gene.symbol" readonly="true"
                                                                            value="${allele.gene.symbol}" type="text" />
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </td>
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
                                               onclick="validate();" />
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>