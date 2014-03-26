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
            var urlUtilRoot = "${pageContext.request.contextPath}/curation/util";
            
            $(document).ready(function() {
                setMaxlengths();
                clearErrors();
 
                $('#backgroundName').on('keyup', function(e) {
                    validate();
                });
            });
            
            function setMaxlengths() {
                // Set table field maximum lengths.
                $.ajax({
                    url: urlUtilRoot + "/getFieldLengths"
                  , async: false
                  , data: { tablename: 'backgrounds' }
                  , dataType: "json"
                  , success: function( data ) {
                      $('#alleleName').attr("maxLength", data['name']);
                      $('#alleleSymbol').attr("maxLength", data['symbol']);
                      $('#species').attr("maxLength", data['species']);
                      $('#notes').attr("maxLength", data['notes']);
                    }
                });
            }

            function validate() {
                clearErrors();
                
                var errMsg = '';
                var errorCount = 0;
                
                // Validate background name is not empty.
                var backgroundName = $('#backgroundName').val().trim();
                if (backgroundName.length === 0) {
                    errorCount++;
                    errMsg = '<br class="clientError" /><span id="background_key.errors" class="clientError">Please choose a name.</span>';
                    $('#backgroundName').parent().append(errMsg);
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
                $('#tabBackgroundDetail :input').val('');
                clearErrors();
                $('.error').remove();
                
                return false;
            }

        </script>
        
        <title>Background Management - add/edit</title>
    </head>
    <body>
        <h2>Background Management - add/edit</h2>
        <span id="loginHeader">Logged in as user "${loggedInUser}"</span>

        <br />

        <form>
            <input type="hidden" name="background_key"            value="${background.background_key}" />
            
            <input type="hidden" name="filterBackgroundKey"       value="${filter.background_key}" />
            <input type="hidden" name="filterBackgroundName"      value="${filter.backgroundName}" />
            <input type="hidden" name="filterBackgroundSymbol"    value="${filter.backgroundSymbol}" />
            <input type="hidden" name="filterBackgroundIsCurated" value="${filter.backgroundIsCurated}" />
            <input type="hidden" name="filterBackgroundIsInbred"  value="${filter.backgroundIsInbred}" />
            
            <table style="border: none">
                <tr>
                    <td>
                        <table style="border: 1px solid black">
                            <tr>
                                <td>
                                    <table id="tabBackgroundDetail" style="border: none">
                                        <tr>
                                            <%-- BACKGROUND ID --%>
                                            <td><label id="labBackgroundId">Background ID:</label></td>
                                            <td style="border: 0"><form:input name="background_key" value="${background.background_key}" readonly="true" path="background.background_key" /></td>
                                            
                                            <%-- BACKGROUND NAME --%>
                                            <td><form:label for="backgroundName" path="background.name" >Background name:</form:label></td>
                                            <td>
                                                <form:textarea id="backgroundName" path="background.name" value="${background.name}" placeholder="Required field" />
                                                <form:errors path="background.name" cssClass="error" />
                                            </td>
                                            
                                            <%-- BACKGROUND SYMBOL --%>
                                            <td><form:label for="backgroundSymbol" path="background.symbol">Background symbol:</form:label></td>
                                            <td>
                                                <form:textarea id="backgroundSymbol" path="background.symbol" value="${background.symbol}" />
                                                <br />
                                                <form:errors path="background.symbol" cssClass="error" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <%-- INBRED --%>
                                            <td><label>Inbred:</label></td>
                                            <td>
                                                <form:radiobutton id="inbredYes" path="background.inbred" value="Y" />Yes
                                                <form:radiobutton id="inbredNo"  path="background.inbred" value="N" />No
                                            </td>
                                            
                                            <%-- SPECIES --%>
                                            <td><form:label for="species" path="background.species" >Species:</form:label></td>
                                            <td>
                                                <form:textarea id="species" path="background.species" value="${background.species}" />
                                                <form:errors path="background.species" cssClass="error" />
                                            </td>
                        
                                            <%-- CURATED --%>
                                            <td><label>Curated:</label></td>
                                            <td>
                                                <form:radiobutton id="curatedYes" path="background.curated" value="Y" />Yes
                                                <form:radiobutton id="curatedNo"  path="background.curated" value="N" />No
                                            </td>
                                        <tr>
                                            <%-- NOTES --%>
                                            <td><label>Notes:</label></td>
                                            <td colspan="5">
                                                <form:textarea id="notes" path="background.notes"
                                                               value="${background.notes}" type="text" />
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
                                               formaction="${pageContext.request.contextPath}/curation/backgroundManagementDetail/save"
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