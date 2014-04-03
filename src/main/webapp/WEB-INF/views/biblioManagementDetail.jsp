<%-- 
    Document   : biblioManagementDetail
    Created on : Apr 02, 2014, 12:39 PM
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
                    background-color: #FFEEEE;
                    border: 3px solid #FF0000;
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
            
        </style>
        
        <script>
            var urlCurationRoot = "${pageContext.request.contextPath}/curation";
            var urlUtilRoot = "${pageContext.request.contextPath}/curation/util";
            
            $(document).ready(function() {
                setMaxlengths();
                populateFilterAutocompletes();
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
                $('#year').on('change blur keyup', function(e) {
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
            
            function setMaxlengths() {
                // Set table field maximum lengths.
                $.ajax({
                    url: urlUtilRoot + "/getFieldLengths"
                  , async: false
                  , data: { tablename: 'biblios' }
                  , dataType: "json"
                  , success: function( data ) {
                      $('#pubmedId').attr("maxLength", data['pubmed_id']);
                      $('#title').attr("maxLength", data['title']);
                      $('#author1').attr("maxLength", data['author1']);
                      $('#author2').attr("maxLength", data['author2']);
                      $('#journal').attr("maxLength", data['journal']);
                      $('#volume').attr("maxLength", data['volume']);
                      $('#pages').attr("maxLength", data['pages']);
                      $('#notes').attr("maxLength", data['notes']);
                    }
                });
            }

            function clearErrors() {
                $('.clientError').remove();
                $('.saveButton').attr("disabled", false);
            }
            
            function clearInputs() {
                $('#tabBiblioDetail :input').val('');
                clearErrors();
                $(':radio').prop('checked', false);
                tabStrain.fnClearTable();
                $('.error').remove();
                
                return false;
            }

            function lookupPubmedId(id) {
                window.open("http://europepmc.org/search?query=" + id, "pubmedWindow");
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

            function showStrainChooser() {
                 window.open(urlCurationRoot + "/strainChooser", "_blank", "width = 768, height=406");

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

            // There are no required fields. However, year, if supplied, must be an integer.
            // Returns true if valid; false otherwise.
            function validate() {
                clearErrors();
                
                var errMsg = '';
                var year;
                var errorCount = 0;
                
                // YEAR if supplied, must be an integer.
                year = $('#year').val();
                if ((year !== undefined) && (year.length > 0)) {
                    if ( ! isInteger(year)) {
                        errorCount++;
                        errMsg = '<br class="clientError" /><span id="year.errors" class="clientError">Please enter an integer.</span>';
                        $('#year').parent().append(errMsg);
                    }
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
            
            function populateFilterAutocompletes() {
                $("#pubmedId").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                              url: urlCurationRoot + "/biblioManagementList/getPubmedIds"
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
                
                $("#author1").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                              url: urlCurationRoot + "/biblioManagementList/getAuthor1s"
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
                
                $("#journal").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                              url: urlCurationRoot + "/biblioManagementList/getJournals"
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
                
                $("#title").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                              url: urlCurationRoot + "/biblioManagementList/getTitles"
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
                
                $("#year").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                              url: urlCurationRoot + "/biblioManagementList/getYears"
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
        
        <title>Biblio Management - add/edit</title>
    </head>
    <body>
        <h2>Biblio Management - add/edit</h2>
        <span id="loginHeader">Logged in as user "${loggedInUser}"</span>

        <br />

        <form>
            <input type="hidden" name="biblio_key"          value="${biblio.biblio_key}" />
            
            <input type="hidden" name="filterBiblioKey"     value="${filter.biblio_key}" />
            <input type="hidden" name="filterStrainKey"     value="${filter.strain_key}" />
            <input type="hidden" name="filterPubmedId"      value="${filter.pubmedId}" />
            <input type="hidden" name="filterBiblioAuthor1" value="${filter.biblioAuthor1}" />
            <input type="hidden" name="filterBiblioJournal" value="${filter.biblioJournal}" />
            <input type="hidden" name="filterBiblioTitle"   value="${filter.biblioTitle}" />
            <input type="hidden" name="filterBiblioYear"    value="${filter.biblioYear}" />
            <table style="border: none">
                <tr>
                    <td>
                        <table style="border: 1px solid black">
                            <tr>
                                <td>
                                    <table id="tabBiblioDetail" style="border: none">
                                        <tr>
                                            <%-- BIBLIO ID --%>
                                            <td><label id="labBiblioId">Biblio ID:</label></td>
                                            <td style="border: 0"><form:input name="biblio_key" value="${biblio.biblio_key}" readonly="true" path="biblio.biblio_key" /></td>
                                            
                                            <%-- PUBMED ID --%>
                                            <td>
                                                <form:label for="pubmedId" path="biblio.pubmed_id">
                                                    <a href="javascript:lookupPubmedId(${biblio.pubmed_id});">
                                                        Pubmed ID:
                                                    </a>
                                                </form:label>
                                            </td>
                                            <td>
                                                <form:input id="pubmedId" name="pubmedId" path="biblio.pubmed_id" value="${biblio.pubmed_id}" />
                                                <br />
                                                <form:errors path="biblio.pubmed_id" cssClass="error" />
                                            </td>
                                            
                                            <%-- JOURNAL --%>
                                            <td><form:label for="journal" path="biblio.journal">Journal:</form:label></td>
                                            <td>
                                                <form:textarea id="journal" path="biblio.journal" value="${biblio.journal}" />
                                                <br />
                                                <form:errors path="biblio.journal" cssClass="error" />
                                            </td>
                                            
                                            <%-- VOLUME --%>
                                            <td><form:label for="volume" path="biblio.volume">Volume:</form:label></td>
                                            <td>
                                                <form:textarea id="volume" path="biblio.volume" value="${biblio.journal}" />
                                                <br />
                                                <form:errors path="biblio.volume" cssClass="error" />
                                            </td>
                                            
                                        </tr>
                                        <tr>
                                            <%-- TITLE --%>
                                            <td><form:label for="title" path="biblio.title" >Title:</form:label></td>
                                            <td>
                                                <form:textarea id="title" path="biblio.title" value="${biblio.title}" />
                                                <br />
                                                <form:errors path="biblio.title" cssClass="error" />
                                            </td>
                                            
                                            <%-- PAGES --%>
                                            <td><form:label for="pages" path="biblio.pages" >Pages:</form:label></td>
                                            <td>
                                                <form:input id="pages" path="biblio.pages" value="${biblio.pages}" />
                                                <br />
                                                <form:errors path="biblio.pages" cssClass="error" />
                                            </td>
                                            
                                            <%-- AUTHOR1 --%>
                                            <td><form:label for="author1" path="biblio.author1">Author1:</form:label></td>
                                            <td>
                                                <form:textarea id="author1" path="biblio.author1" value="${biblio.author1}" />
                                                <br />
                                                <form:errors path="biblio.author1" cssClass="error" />
                                            </td>
                                            
                                            <%-- VOLUME --%>
                                            <td><form:label for="author2" path="biblio.author2">Additional Authors:</form:label></td>
                                            <td>
                                                <form:textarea id="author2" path="biblio.author2" value="${biblio.author2}" />
                                                <br />
                                                <form:errors path="biblio.author2" cssClass="error" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <%-- YEAR --%>
                                            <td><form:label for="year" path="biblio.year" >Year:</form:label></td>
                                            <td>
                                                <form:input id="year" path="biblio.year" value="${biblio.year}" />
                                                <br />
                                                <form:errors path="biblio.year" cssClass="error" />
                                            </td>
                        
                                            <%-- CURATED --%>
                                            <td><label>Curated:</label></td>
                                            <td>
                                                <form:radiobutton id="curatedYes" path="biblio.updated" value="Y" />Yes
                                                <form:radiobutton id="curatedNo"  path="biblio.updated" value="N" />No
                                            </td>
                                            
                                            <%-- NOTES --%>
                                            <td><form:label for="notes" path="biblio.notes" >Notes:</form:label></td>
                                            <td colspan="3">
                                                <form:textarea id="notes" path="biblio.notes" value="${biblio.notes}" />
                                                <br />
                                                <form:errors path="biblio.notes" cssClass="error" />
                                            </td>
                                        </tr>

                                        <tr>
                                            <%-- STRAIN --%>
                                            <td>
                                                <label>Strains:</label>
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
                                                                <c:when test="${ not empty biblio.strains}"> 
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
                                                            <c:forEach var="strain" items="${biblio.strains}" varStatus="status">
                                                                <tr draggable="true" data-strain_key="${strain.strain_key}" data-biblio_key="${biblio.biblio_key}" style="border: 1px solid gray">
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
                                            <%--   formenctype="multipart/form-data" 
                                            formenctype="multipart/form-data"--%>
                                               formmethod="POST"
                                               formaction="${pageContext.request.contextPath}/curation/biblioManagementDetail/save"
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