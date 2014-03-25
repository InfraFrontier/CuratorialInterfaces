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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.dataTables.css" />
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.0.js" type="text/javascript" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/js/jquery-ui.min.js" type="text/javascript" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js" type="text/javascript" charset="UTF-8"></script>
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
            
            #divStrain.over {
                background: #FFFFCC;
            }
            #divStrain {
                min-height: 48px;       // Same size as allele and background divs.
            }
            #divAllele.over {
                background: #CCFFCC;
            }
            #divBackground.over {
                background: #CCFFFF;
            }
            
        </style>
        
        <script>
            var urlCurationlRoot = "${pageContext.request.contextPath}/curation";
            var urlUtilRoot = "${pageContext.request.contextPath}/curation/util";
            var tabStrain;
                    
            $(document).ready(function() {
                setMaxlengths();
                clearErrors();
 
                $('#divStrain')
                    .on('dragover',  handleDragOverStrain)
                    .on('dragleave', handleDragLeaveStrain)
                    .on('drop',      handleDropStrain);
                $('#strain_key').on('change', function(e) {
                    if (validate()) {
                        updateStrainDiv($('#strain_key').val());
                    }
                });
                $('#divAllele')
                    .on('dragover',  handleDragOverAllele)
                    .on('dragleave', handleDragLeaveAllele)
                    .on('drop',      handleDropAllele);
                $('#allele_key').on('change', function(e) {
                   updateAlleleDiv($('#allele_key').val());
                   validate();
                });
 
                $('#divBackground')
                    .on('dragover',  handleDragOverBackground)
                    .on('dragleave', handleDragLeaveBackground)
                    .on('drop',      handleDropBackground);
                $('#background_key').on('change', function(e) {
                   updateBackgroundDiv($('#background_key').val());
                   validate();
                });
                
                tabStrain = $('#tabStrain').dataTable(
                    {
                        "bPaginate": false,
                        "bLengthChange": false,
                        "bFilter": true,
                        "bSort": true,
                        "bInfo": false,
                        "bAutoWidth": false
                    }
                );
        
                $('#tabStrain > tbody > tr')
                    .on('dragstart', handleDragStartStrain);
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
                  , fail: function(data) { alert('setMaxLengths() failed: ' + data); }
                });
            }

            // Allele is a required field. Strain, Background, and Replaced Allele are optional but, if specified, must
            // be valid.
            // Returns true if valid; false otherwise.
            function validate() {
                clearErrors();
                
                var errMsg = '';
                var newId;
                var key;
                var errorCount = 0;

                // ALLELE: Required. key must describe a valid allele.
                newId = $('#allele_key').val();
                key = getAllele(newId);
                if (key === null) {
                    errorCount++;
                    errMsg = '<br class="clientError" /><span id="allele_key.errors" class="clientError">Please choose a valid allele.</span>';
                    $('#allele_key').parent().append(errMsg);
                }

                // BACKGROUND if supplied, validate background_key must describe a valid background.
                newId = $('#background_key').val();
                key = getBackground(newId);
                if (key === null) {
                        errorCount++;
                        errMsg = '<br class="clientError" /><span id="background_key.errors" class="clientError">Please choose a valid background.</span>';
                        $('#background_key').parent().append(errMsg);
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
            
            function handleDragStartStrain(e) {
                var strain_key = $(this).data('strain_key');
                e.originalEvent.dataTransfer.setData('text/mut_strain', strain_key);
                $('#divStrain')
                        .data('strain_key', strain_key);
            }
            // getData() is not available in this function and breaks drag-n-drop if it is called here.
            function handleDragOverStrain(e) {
                var isStrain = (e.originalEvent.dataTransfer.types.indexOf('text/strain') >= 0);
                if (isStrain) {
                    $('#divStrain').addClass('over');
                    e.preventDefault();
                    e.originalEvent.dataTransfer.dropEffect = 'copy';
                } else {
                    e.originalEvent.dataTransfer.dropEffect = 'none';
                }
                
                return false;
            }
            function handleDragLeaveStrain(e) {
                if (e.originalEvent.dataTransfer.types.indexOf('text/strain') >= 0) {               // (from strain chooser)
                    $(this).removeClass('over');
                } else if (e.originalEvent.dataTransfer.types.indexOf('text/mut_strain') >= 0) {    // (from within divStrain)
                    var strain_key = $('#divStrain').data('strain_key');
                    var mutation_key = $('#mutation_key').val();
                    
                    // Remove row from mutations_strains.
                    
                    // Find the row to remove, then remove it from the view.
                    var data = tabStrain.fnGetData();
                    var index = 0;
                    for (index = 0; index < data.length; index++) {
                        var row = data[index];
                        if (row[1] + "" === strain_key + "") {
                            tabStrain.fnDeleteRow(index);
                            break;
                        }
                    }
                }
                
                return false;
            }
            function handleDropStrain(e) {
                var isStrain = (e.originalEvent.dataTransfer.types.indexOf('text/strain') >= 0);
                if (isStrain) {
                    $('#divStrain').removeClass('over');
                    var strain_key = e.originalEvent.dataTransfer.getData('text/strain');
                    $('#strain_key').val(strain_key);
                    addToStrainDiv(strain_key);
                }
                
                return false;
            }
            function addToStrainDiv(strain_key) {
                var strain = getStrain(strain_key);
                if ((strain !== undefined) && (keyToRownum(strain_key) < 0)) {
                    $('#tabStrainHeading').css('display', 'table-row');
                    var markup =
                             '<tr draggable="true" data-strain_key="' + strain.strain_key + '" style="border: 1px solid gray">\n'
                           + '   <td style="border: 1px solid gray"><img alt="Drag Handle" src="${pageContext.request.contextPath}/images/draghandle.png" height="15" width="15" title="Drag me" draggable="false"></td>\n'
                                 <%-- This must be dataTable cell[1] or the dataTable index reference must be changed to match the new index. --%>
                           + '   <td style="border: 1px solid gray">' + strain.strain_key + '</td>\n'
                           + '   <td style="border: 1px solid gray">' + scrub(strain.name) + '</td>\n'
                           + '   <td style="display: none"><input name="strain_keys" type="hidden" value="' + strain.strain_key + '" /></td>\n'
                          + '</tr>\n';
                    $('#tabStrain > tbody > tr:last').after(markup);            // Add the new row.
                    $('#tabStrain > tbody > tr:last')
                        .on('dragstart', handleDragStartStrain);                // Bind the strain drag handler to get the strain_key.
                    
                    // Keep the dataTable's model in sync with what's shown.
                    tabStrain.fnDestroy();
                    tabStrain = $('#tabStrain').dataTable(
                        {
                            "bPaginate": false,
                            "bLengthChange": false,
                            "bFilter": true,
                            "bSort": true,
                            "bInfo": false,
                            "bAutoWidth": false
                        });
                }
                
            }
            
            // Searches tabStrain for key in column 1. Returns 0-relative offset
            // if found; -1 otherwise.
            function keyToRownum(key) {
                var data = tabStrain.fnGetData();
                for (var i = 0; i < data.length; i++) {
                    var trKey = data[i][1];
                    if (key + "" === trKey + "")
                        return i;
                }
                
                return -1;
            }
            
            // ALLELE DRAG-n-DROP HANDLERS
            // getData() is not available in this function and breaks drag-n-drop if it is called here.
            function handleDragOverAllele(e) {
                var isAllele = (e.originalEvent.dataTransfer.types.indexOf('text/allele') >= 0);
                if (isAllele) {
                    $('#divAllele').addClass('over');
                    e.preventDefault();
                    e.originalEvent.dataTransfer.dropEffect = 'copy';
                } else {
                    e.originalEvent.dataTransfer.dropEffect = 'none';
                }
                
                return false;
            }
            function handleDragLeaveAllele(e) {
                var isAllele = (e.originalEvent.dataTransfer.types.indexOf('text/allele') >= 0);
                if (isAllele) {
                    $(this).removeClass('over');
                }
                
                return false;
            }
            function handleDropAllele(e) {
                var isAllele = (e.originalEvent.dataTransfer.types.indexOf('text/allele') >= 0);
                if (isAllele) {
                    $('#divAllele').removeClass('over');
                    var allele_key = e.originalEvent.dataTransfer.getData('text/allele');
                    $('#allele_key').val(allele_key);
                    updateAlleleDiv(allele_key);
                }
                
                return false;
            }
            
            // BACKGROUND DRAG-n-DROP HANDLERS
            // getData() is not available in this function and breaks drag-n-drop if it is called here.
            function handleDragOverBackground(e) {
                var isBackground = (e.originalEvent.dataTransfer.types.indexOf('text/background') >= 0);
                if (isBackground) {
                    $('#divBackground').addClass('over');
                    e.preventDefault();
                    e.originalEvent.dataTransfer.dropEffect = 'copy';
                } else {
                    e.originalEvent.dataTransfer.dropEffect = 'none';
                }
                
                return false;
            }
            function handleDragLeaveBackground(e) {
                var isBackground = (e.originalEvent.dataTransfer.types.indexOf('text/background') >= 0);
                if (isBackground) {
                    $(this).removeClass('over');
                }
                
                return false;
            }
            function handleDropBackground(e) {
                var isBackground = (e.originalEvent.dataTransfer.types.indexOf('text/background') >= 0);
                if (isBackground) {
                    $('#divBackground').removeClass('over');
                    var background_key = e.originalEvent.dataTransfer.getData('text/background');
                    $('#background_key').val(background_key);
                    updateBackgroundDiv(background_key);
                }
                
                return false;
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
                        alert('getStrain(' + id + ') failed: ' + data);
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
                        alert('getBackground(' + id + ') failed: ' + data);
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
                        alert('getAllele(' + id + ') failed: ' + data);
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
                                                    <table id="tabStrain">
                                                        <thead style="border: 1px solid black">  
                                                            <c:choose>
                                                                <c:when test="${ not empty mutation.strains}"> 
                                                                    <tr id="tabStrainHeading">
                                                                        <td>&nbsp;</td>
                                                                        <td style="text-align: center">Id</td>
                                                                        <td style="text-align: center">Name</td>
                                                                    <td style="display: none"></td>
                                                                    </tr>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <tr id="tabStrainHeading">
                                                                        <td>&nbsp;</td>
                                                                        <td>&nbsp;</td>
                                                                        <td>&nbsp;</td>
                                                                    <td style="display: none"></td>
                                                                    </tr>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach var="strain" items="${mutation.strains}" varStatus="status">
                                                                <tr draggable="true" data-strain_key="${strain.strain_key}" data-mutation_key="${mutation.mutation_key}" style="border: 1px solid gray">
                                                                    <td style="border: 1px solid gray"><img alt="Drag Handle" src="${pageContext.request.contextPath}/images/draghandle.png" height="15" width="15" title="Drag me" draggable="false"></td>
                                                                    <%-- This must be dataTable cell[1] or the dataTable index reference must be changed to match the new index. --%>
                                                                    <td style="border: 1px solid gray">${strain.strain_key}</td>
                                                                    <td style="border: 1px solid gray">${fn:escapeXml(strain.name)}</td>
                                                                    <td style="display: none"><input name="strain_keys" type="hidden" value="${strain.strain_key}" /></td>
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
                                                                            placeholder="Required field"
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