<%-- 
    Document   : mutationManagementDetail
    Created on : Mar 3, 2014, 10:11:15 AM
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
            var urlCurationlRoot = "${pageContext.request.contextPath}/curation";
            var urlUtilRoot = "${pageContext.request.contextPath}/curation/util";
            
            $(document).ready(function() {
                setMaxlengths();
                clearErrors();
 
                $('#divStrain')
                    .on('dragenter', handleDragEnterStrain)
                    .on('dragover',  handleDragOverStrain)
                    .on('dragleave', handleDragLeaveStrain)
                    .on('drop',      handleDropStrain)
                    .on('dragend',   handleDragLeaveStrain);
                $('#strain_key').on('change', function(e) {
                    updateStrainDiv($('#strain_key').val());
                });
                $('#divAllele')
                    .on('dragenter', handleDragEnterAllele)
                    .on('dragover',  handleDragOverAllele)
                    .on('dragleave', handleDragLeaveAllele)
                    .on('drop',      handleDropAllele)
                    .on('dragend',   handleDragLeaveAllele);
                $('#allele_key').on('change', function(e) {
                    updateAlleleDiv();
                });
 
                $('#divBackground')
                    .on('dragenter', handleDragEnterBackground)
                    .on('dragover',  handleDragOverBackground)
                    .on('dragleave', handleDragLeaveBackground)
                    .on('drop',      handleDropBackground)
                    .on('dragend',   handleDragLeaveBackground);
                $('#background_key').on('change', function(e) {
                    updateBackgroundDiv();
                });
            });
            
            function updateStrainDiv(key) {
                var strainName = '';
                
                if (((key + '').trim() !== '') && (isInteger(key)) && (key > 0)) {
                    var strain = getStrain(key);
                    if (strain !== null) {
                        strainName = strain.name;
                    }
                }
                
                // Set strain details
                $('#strain_key').val(key);
                $('#strainName').val(strainName);
            }
            
            function updateAlleleDiv(key) {
                var alleleName = '';
                var alleleSymbol = '';
                
                if (((key + '').trim() !== '') && (isInteger(key)) && (key > 0)) {
                    var allele = getAllele(key);
                    if (allele !== null) {
                        alleleName = allele.name;
                        alleleSymbol = allele.symbol;
                    }
                }
                // Set allele details
                $('#alleleName').val(alleleName);
                $('#alleleSymbol').val(alleleSymbol);
            }
            
            function updateBackgroundDiv(key) {
                var backgroundName = '';
                var backgroundSymbol = '';
                
                if (((key + '').trim() !== '') && (isInteger(key)) && (key > 0)) {
                    var background = getBackground(key);
                    if (background !== null) {
                        backgroundName = background.name;
                        backgroundSymbol = background.symbol;
                    }
                }
                // Set background details
                $('#backgroundName').val(backgroundName);
                $('#backgroundSymbol').val(backgroundSymbol);
            }
            
            function updateReplacedAlleleDiv(key) {
                var replacedAlleleName = '';
                var replacedAlleleSymbol = '';
                
                if (((key + '').trim() !== '') && (isInteger(key)) && (key > 0)) {
                    var replacedAllele = getAllele(key);
                    if (replacedAllele !== null) {
                        replacedAlleleName = replacedAllele.name;
                        replacedAlleleSymbol = replacedAllele.symbol;
                    }
                }
                // Set replacedAllele details
                $('#replacedAlleleName').val(replacedAlleleName);
                $('#replacedAlleleSymbol').val(replacedAlleleSymbol);
            }

            function setMaxlengths() {
                // Set table field maximum lengths.
                $.ajax({
                    url: urlUtilRoot + "/getFieldLengths"
                  , async: false
                  , data: { tablename: 'mutations' }
                  , dataType: "json"
                  , success: function( data ) {
                      $('#type').attr("maxLength", data['main_type']);
                      $('#subtype').attr("maxLength", data['sub_type']);
                      $('#targetedMutationEsLine').attr("maxLength", data['tm_esline']);
                      $('#genotype').attr("maxLength", data['genotype']);
                      $('#knockinAlter').attr("maxLength", data['ki_alter']);
                      $('#cause').attr("maxLength", data['mu_cause']);
                      $('#chromosomeAnnotatedName').attr("maxLength", data['ch_ano_name']);
                      $('#dominance').attr("maxLength", data['dominance']);
                      $('#sex').attr("maxLength", data['sex']);
                      $('#chromosome').attr("maxLength", data['chromosome']);
                      $('#chromosomeAnnotatedDescription').attr("maxLength", data['ch_ano_desc']);
                    }
                  , fail: function(data) { alert(data); }
                });
            }

            // Allele is a required field. Strain, Background, and Replaced Allele are optional but, if specified, must
            // be valid.
            function validate() {
                clearErrors();
                
                var errMsg = '';
                var newId = $('#strain_key').val();
                var name = '';
                var symbol = '';
                var errorCount = 0;

                // STRAIN: Validate strain_key is an int and describes a valid strain.
                if ((newId + '').trim() !== '') {
                    if ((isInteger(newId)) && (newId > 0)) {

                        var strain = getStrain($('#strain_key').val());
                        if (strain !== null) {
                            name = strain.name;
                        } else {
                            errorCount++;
                            errMsg = '<br class="clientError" /><span id="strain_key.errors" class="clientError">Please choose a valid strain.</span>';
                            $('#strain_key').parent().append(errMsg);
                        }
                    } else {
                        errorCount++;
                        errMsg = '<br class="clientError" /><span id="strain_key.errors" class="clientError">Please choose a valid strain.</span>';
                        $('#strain_key').parent().append(errMsg);
                    }
                }
                
                // Set strain details
                $('#strainName').val(name);

                // ALLELE: if supplied, validate allele_key is an int and describes a valid allele.
                newId = $('#allele_key').val();
                if ((newId !== undefined) && (newId.length > 0)) {
                    if ((isInteger(newId)) && (newId > 0)) {
                        var allele = getAllele($('#allele_key').val());
                        if (allele !== null) {
                            name = allele.name;
                            symbol = allele.symbol;
                        } else {
                            errorCount++;
                            errMsg = '<br class="clientError" /><span id="allele_key.errors" class="clientError">Please choose a valid allele.</span>';
                            $('#allele_key').parent().append(errMsg);
                        }
                    } else {
                        errorCount++;
                        errMsg = '<br class="clientError" /><span id="allele_key.errors" class="clientError">Please choose a valid allele.</span>';
                        $('#allele_key').parent().append(errMsg);
                    }
                    // Set allele details
                    $('#alleleName').val(name);
                    $('#alleleSymbol').val(symbol);
                }

                // BACKGROUND if supplied, validate background_key is an int and describes a valid background.
                newId = $('#background_key').val();
                if ((newId !== undefined) && (newId.length > 0)) {
                    if ((isInteger(newId)) && (newId > 0)) {
                        var background = getBackground($('#background_key').val());
                        if (background !== null) {
                            name = background.name;
                            symbol = background.symbol;
                        } else {
                            errorCount++;
                            errMsg = '<br class="clientError" /><span id="background_key.errors" class="clientError">Please choose a valid background.</span>';
                            $('#background_key').parent().append(errMsg);
                        }
                    } else {
                        errorCount++;
                        errMsg = '<br class="clientError" /><span id="background_key.errors" class="clientError">Please choose a valid background.</span>';
                        $('#background_key').parent().append(errMsg);
                    }
                    // Set background details
                    $('#backgroundName').val(name);
                    $('#backgroundSymbol').val(symbol);
                }

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
                $('#tabMutationDetail :input').val('');
                clearErrors();
                $('.error').remove();
                
                return false;
            }
            
            
            // STRAIN DRAG-n-DROP HANDLERS
            function handleDragEnterStrain(e) {
                var isStrain = (e.originalEvent.dataTransfer.types.indexOf('text/strain') >= 0);
                if (isStrain)
                    e.preventDefault();
                
                return false;
            }
            function handleDragLeaveStrain(e) {
                var isStrain = (e.originalEvent.dataTransfer.types.indexOf('text/strain') >= 0);
                if (isStrain) {
                    this.classList.remove('over');
                    e.originalEvent.dataTransfer.dropEffect = 'none';
                }
                
                return false;
            }
            function handleDropStrain(e) {
                var key = e.originalEvent.dataTransfer.getData('text/strain');
                if ((key + "").trim().length > 0) {
                    $('#strain_key').val(key);
                    updateStrainDiv(key);
                }
                
                return false;
            }
            // getData() is not available in this function and breaks drag-n-drop if it is called here.
            function handleDragOverStrain(e) {
                var isStrain = (e.originalEvent.dataTransfer.types.indexOf('text/strain') >= 0);
                if (isStrain) {
                    e.preventDefault();
                    e.originalEvent.dataTransfer.dropEffect = 'copy';
                }
            }
            
            // ALLELE DRAG-n-DROP HANDLERS
            function handleDragEnterAllele(e) {
                var isAllele = (e.originalEvent.dataTransfer.types.indexOf('text/allele') >= 0);
                if (isAllele)
                    e.preventDefault();
                
                return false;
            }
            function handleDragLeaveAllele(e) {
                var isAllele = (e.originalEvent.dataTransfer.types.indexOf('text/allele') >= 0);
                if (isAllele) {
                    this.classList.remove('over');
                    e.originalEvent.dataTransfer.dropEffect = 'none';
                }
                
                return false;
            }
            function handleDropAllele(e) {
                var key = e.originalEvent.dataTransfer.getData('text/allele');
                if ((key + "").trim().length > 0) {
                    $('#allele_key').val(key);
                    updateAlleleDiv(key);
                }
                
                return false;
            }
            // getData() is not available in this function and breaks drag-n-drop if it is called here.
            function handleDragOverAllele(e) {
                var isAllele = (e.originalEvent.dataTransfer.types.indexOf('text/allele') >= 0);
                if (isAllele) {
                    e.preventDefault();
                    e.originalEvent.dataTransfer.dropEffect = 'copy';
                }
            }
            
            // BACKGROUND DRAG-n-DROP HANDLERS
            function handleDragEnterBackground(e) {
                var isBackground = (e.originalEvent.dataTransfer.types.indexOf('text/background') >= 0);
                if (isBackground)
                    e.preventDefault();
                
                return false;
            }
            function handleDragLeaveBackground(e) {
                var isBackground = (e.originalEvent.dataTransfer.types.indexOf('text/background') >= 0);
                if (isBackground) {
                    this.classList.remove('over');
                    e.originalEvent.dataTransfer.dropEffect = 'none';
                }
                
                return false;
            }
            function handleDropBackground(e) {
                var key = e.originalEvent.dataTransfer.getData('text/background');
                if ((key + "").trim().length > 0) {
                    $('#background_key').val(key);
                    updateBackgroundDiv(key);
                }
                
                return false;
            }
            // getData() is not available in this function and breaks drag-n-drop if it is called here.
            function handleDragOverBackground(e) {
                var isBackground = (e.originalEvent.dataTransfer.types.indexOf('text/background') >= 0);
                if (isBackground) {
                    e.preventDefault();
                    e.originalEvent.dataTransfer.dropEffect = 'copy';
                }
            }

            function showStrainChooser() {
                 window.open(urlCurationlRoot + "/strainChooser", "_blank", "width = 768, height=406");

                 return false;
            }

            function showAlleleChooser() {
                 window.open(urlCurationlRoot + "/alleleChooser", "_blank", "width = 768, height=406");

                 return false;
            }

            var backgroundChooserWindow;
            function showBackgroundChooser() {
                 backgroundChooserWindow = window.open(urlCurationlRoot + "/backgroundChooser", "_blank", "width = 768, height=406");

                 return false;
            }

            function getStrain(id) {
                var strain = null;
                $.ajax({
                      url:      urlUtilRoot + "/getStrain"
                    , dataType: "json"
                    , async:    false
                    , data:     {'strain_key': id}
                    , success:  function(data) {
                        strain = data;
                    }
                    , fail: function(data) {
                        alert(data);
                    }
                });
                
                return strain;
            }

            function getBackground(id) {
                var background = null;
                $.ajax({
                    url:        urlUtilRoot + "/getBackground"
                    , dataType: "json"
                    , async:    false
                    , data:     {'background_key': id}
                    , success:  function(data) {
                        background = data;
                    }
                    , fail: function(data) {
                        alert(data);
                    }
                });
                
                return background;
            }
            
            function getAllele(id) {
                var allele = null;
                $.ajax({
                      url:      urlUtilRoot + "/getAllele"
                    , dataType: "json"
                    , async:    false
                    , data:     {'allele_key': id}
                    , success:  function(data) {
                        allele = data;
                    }
                    , fail: function(data) {
                        alert(data);
                    }
                });
                
                return allele;
            }
        </script>
        
        <title>Mutation Management - add/edit</title>
    </head>
    <body>
        <h2>Mutation Management - add/edit</h2>
        <span id="loginHeader">Logged in as user "${loggedInUser}"</span>

        <br />

        <form>
            <input type="hidden" name="filterMutationKey"     value="${filter.mutation_key}" />
            <input type="hidden" name="filterMutationType"    value="${filter.mutationType}" />
            <input type="hidden" name="filterMutationSubtype" value="${filter.mutationSubtype}" />
            <input type="hidden" name="filterStrainKey"       value="${filter.strain_key}" />
            <input type="hidden" name="filterAlleleKey"       value="${filter.allele_key}" />
            <input type="hidden" name="filterBackgroundKey"   value="${filter.background_key}" />
            <input type="hidden" name="filterGeneKey"         value="${filter.gene_key}" />
            <input type="hidden" name="filterGeneSymbol"      value="${filter.geneSymbol}" />
            
            <table style="border: none">
                <tr>
                    <td>
                        <table style="border: 1px solid black">
                            <tr>
                                <td>
                                    <table id="tabMutationDetail" style="border: none">
                                        <tr>
                                            <%-- MUTATION ID --%>
                                            <td><label id="labMutationId">Mutation ID:</label></td>
                                            <td style="border: 0"><form:input id="mutation_key" name="mutation_key" value="${mutation.mutation_key}" readonly="true" path="mutation.mutation_key" /></td>
                                            
                                            <%-- TYPE --%>
                                            <td><label id="labType">Type:</label></td>
                                            <td style="border: 0"><form:input name="type" value="${mutation.type}" path="mutation.type" /></td>
                                            
                                            <%-- SUBTYPE --%>
                                            <td><label id="labSubtype">Subtype:</label></td>
                                            <td style="border: 0"><form:input name="subtype" value="${mutation.subtype}" path="mutation.subtype" /></td>
                                            
                                            <%-- TARGETED MUTATION ES LINE --%>
                                            <td><label id="labTargetedMutationEsLine">Targeted Mutation ES Line:</label></td>
                                            <td style="border: 0"><form:textarea name="targetedMutationEsLine" value="${mutation.targetedMutationEsLine}" path="mutation.targetedMutationEsLine" /></td>
                                        </tr>
                                        <tr>
                                            <%-- GENOTYPE --%>
                                            <td><label id="labGenotype">Genotype:</label></td>
                                            <td style="border: 0"><form:textarea name="genotype" value="${mutation.genotype}" path="mutation.genotype" /></td>

                                            <%-- DOMINANCE --%>
                                            <td><label id="labDominance">Dominance:</label></td>
                                            <td style="border: 0"><form:input name="dominance" value="${mutation.dominance}" path="mutation.dominance" /></td>
                                            
                                            <%-- SEX --%>
                                            <td><label id="labSex">Sex:</label></td>
                                            <td style="border: 0"><form:input name="sex" value="${mutation.sex}" path="mutation.sex" /></td>
                                            
                                            <%-- CAUSE --%>
                                            <td><label id="labCause">Cause:</label></td>
                                            <td style="border: 0"><form:textarea name="cause" value="${mutation.cause}" path="mutation.cause" /></td>
                                        </tr>
                                        <tr><td colspan="8">&nbsp;</td></tr>
                                        <tr>
                                            <%-- STRAIN --%>
                                            <td>
                                                <label>Strain:</label>
                                                <input alt="Click for strain list." type="image" height="15" width="15" title="Click for strain list."
                                                       src="${pageContext.request.contextPath}/images/geneChooser.jpg"
                                                       onclick="showStrainChooser();return false;"
                                                />
                                            </td>
                                            <td colspan="7">
                                                <div id="divStrain" style="border: 1px solid gray" >
                                                    <table>
                                                        <thead style="border: 1px solid black">
                                                            <tr>
                                                                <td colspan="2" style="text-align: center">Id</td>
                                                                <td style="text-align: center">Name</td>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach var="strain" items="${mutation.strains}" varStatus="status">
                                                                <tr draggable="true">
                                                                    <td><img alt="Drag Handle" src="${pageContext.request.contextPath}/images/draghandle.png" height="15" width="15" title="Drag me"></td>
                                                                    <td>
                                                                        <input name="strainIds" value="${strain.strain_key}" readonly="readonly" />
                                                                    </td>
                                                                    <td>
                                                                        <textarea readonly="readonly" style="width: 300px">${strain.name}"</textarea>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <%-- ALLELE --%>
                                            <td>
                                                <label>Allele:</label>
                                                <input alt="Click for allele list." type="image" height="15" width="15" title="Click for allele list."
                                                       src="${pageContext.request.contextPath}/images/geneChooser.jpg"
                                                       onclick="showAlleleChooser();return false;"
                                                />
                                            </td>
                                            <td colspan="7">
                                                <div id="divAllele" style="border: 1px solid gray">
                                                    <table>
                                                        <tr>
                                                            <%-- ALLELE --%>
                                                            <td style="width: 150px"><label>Allele Id:</label></td>
                                                            <td>
                                                                <form:input id="allele_key" path="mutation.allele_key"
                                                                            placeholder="Required field"
                                                                            value="${mutation.allele_key}" />
                                                                <form:errors path="mutation.allele_key" cssClass="error" />
                                                            </td>
                                                            <td style="width: 150px"><label>Allele Name:</label></td>
                                                            <td>
                                                                <form:textarea id="alleleName" path="mutation.allele.name" readonly="true"
                                                                            value="${mutation.allele.name}" type="text" />
                                                            </td>
                                                            <td style="width: 160px"><label>Allele Symbol:</label></td>
                                                            <td>
                                                                <form:textarea id="alleleSymbol" path="mutation.allele.symbol" readonly="true"
                                                                            value="${mutation.allele.symbol}" type="text" />
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <%-- BACKGROUND --%>
                                            <td>
                                                <label>Background:</label>
                                                <input alt="Click for background list." type="image" height="15" width="15" title="Click for background list."
                                                       src="${pageContext.request.contextPath}/images/geneChooser.jpg"
                                                       onclick="showBackgroundChooser();return false;"
                                                />
                                            </td>
                                            <td colspan="7">
                                                <div id="divBackground" style="border: 1px solid gray">
                                                    <table>
                                                        <tr>
                                                            <%-- BACKGROUND --%>
                                                            <td style="width: 150px"><label>Background Id:</label></td>
                                                            <td>
                                                                <form:input id="background_key" path="mutation.background_key"
                                                                            value="${mutation.background_key}" />
                                                                <form:errors path="mutation.background_key" cssClass="error" />
                                                            </td>
                                                            <td style="width: 150px"><label>Background Name:</label></td>
                                                            <td>
                                                                <form:textarea id="backgroundName" path="mutation.background.name" readonly="true"
                                                                            value="${mutation.background.name}" type="text" />
                                                            </td>
                                                            <td style="width: 160px"><label>Background Symbol:</label></td>
                                                            <td>
                                                                <form:textarea id="backgroundSymbol" path="mutation.background.symbol" readonly="true"
                                                                            value="${mutation.background.symbol}" type="text" />
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
                                               formaction="${pageContext.request.contextPath}/curation/mutationManagementDetail/save"
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