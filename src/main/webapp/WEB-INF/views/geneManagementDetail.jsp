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
        <link rel="stylesheet" href="../css/jquery-ui.css" />
        <script src="../js/jquery-1.11.0.js" type="text/javascript" charset="UTF-8"></script>
        <script src="../js/jquery-ui.min.js" type="text/javascript" charset="UTF-8"></script>
        <script src="../js/json2.js" type="text/javascript" charset="UTF-8"></script>
        <script src="../js/utils.js" type="text/javascript" charset="UTF-8"></script>
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
        </style>
        
        <script>
            
            $(document).ready(function() {
            });

        </script>
        <title>Gene Management - add/edit</title>
    </head>
    <body>
        <h2>Gene Management - add/edit</h2>
        <span id="loginHeader">Logged in as user "<sec:authentication property='principal.username'/>"</span>
        
        <br />
        <br />
<%--
        <form:form commandName="gene" method="post">
                <table style="border: 1px solid black">
                    <tr>
                        <td><label id="labGeneId">Gene ID:</label></td>
                        <td style="border: 0"><form:input id="geneId" tabindex="0" path="id_gene" readonly="true"></form:input></td>
                        <td><form:label for="mgiReference" path="mgi_ref">MGI reference:</form:label></td>
                        <td>
                            <form:input id="mgiReference" tabindex="6" path="mgi_ref" />
                            <br />
                            <form:errors path="mgi_ref" cssClass="error" />
                        </td>
                        <td rowspan="5">
                            <table style="border: 1px solid black">
                                <tr style="border: 1px solid black">
                                    <td>Synonyms:</td>
                                    <td colspan="3" align="right"><input type="submit" value="New" formaction="geneManagementDetail.emma?id=${gene.id_gene}&amp;action=newSynonym" /></td>
                                </tr>
                                <tr>
                                    <th>Actions</th>
                                    <th>Id</th>
                                    <th>Name</th>
                                    <th>Symbol</th>
                                </tr>
                                <c:forEach var="synonym" items="${gene.synonyms}" varStatus="status">
                                    <tr>
                                        <td>
                                            <form:form commandName="command" method="post">
                                                <input type="hidden" name="id" value="${gene.id_gene}" />
                                                <input type="hidden" name="id_syn" value="${synonym.id_syn}" />
                                                <input type="hidden" name="action" value="deleteSynonym" />
                                                <input alt="Delete Synonym" type="image" height="15" width="15" title="Delete Synonym ${synonym.id_syn}"
                                                       src="../images/delete.jpg" formaction="geneManagementDetail.emma" />
                                            </form:form>
                                        </td>
                                        <td>
                                            <c:set var="nameIndex" value="${status.index + 12}" />
                                            <spring:bind path="gene.synonyms[${status.index}].id_syn" >
                                                <form:input id="${status.expression}" tabindex="${nameIndex + 1}" path="${status.expression}" readonly="true" />
                                                <br />
                                                <form:errors path="${status.expression}" cssClass="error" />
                                            </spring:bind>
                                        </td>
                                        <td>
                                            <c:set var="nameIndex" value="${status.index + 12}" />
                                            <spring:bind path="gene.synonyms[${status.index}].name" >
                                                <form:input id="${status.expression}" tabindex="${nameIndex + 1}" path="${status.expression}" />
                                                <br />
                                                <form:errors path="${status.expression}" cssClass="error" />
                                            </spring:bind>
                                        </td>
                                        <td>
                                            <spring:bind path="gene.synonyms[${status.index}].symbol" >
                                                <form:input id="${status.expression}" tabindex="${nameIndex + 1}" path="${status.expression}" />
                                                <br />
                                                <form:errors path="${status.expression}" cssClass="error" />
                                            </spring:bind>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td><form:label for="geneName" path="name">Gene Name:</form:label></td>
                        <td>
                            <form:input id="geneName" tabindex="1" path="name" />
                            <br />
                            <form:errors path="name" cssClass="error" />
                        </td>
                        <td><form:label for="ensemblReference" path="ensembl_ref">Ensembl reference:</form:label></td>
                        <td>
                            <form:input id="ensemblReference" tabindex="7" path="ensembl_ref" />
                            <br />
                            <form:errors path="ensembl_ref" cssClass="error" />
                        </td>
                    </tr>
                    <tr>
                        <td><form:label for="geneSymbol" path="symbol">Gene Symbol:</form:label></td>
                        <td>
                            <form:input id="geneSymbol" tabindex="2" path="symbol" />
                            <br />
                            <form:errors path="symbol" cssClass="error" />
                        </td>
                        <td><form:label for="promoter" path="promoter">Promoter:</form:label></td>
                        <td>
                            <form:input id="promoter" tabindex="8" path="promoter" />
                            <br />
                            <form:errors path="promoter" cssClass="error" />
                        </td>
                    </tr>
                    <tr>
                        <td><form:label for="chromosome" path="chromosome">Chromosome:</form:label></td>
                        <td>
                            <form:input id="chromosome" tabindex="3" path="chromosome" />
                            <br />
                            <form:errors path="chromosome" cssClass="error" />
                        </td>
                        <td><form:label for="founderLineNumber" path="founder_line_number">Founder line number:</form:label></td>
                        <td colspan="2">
                            <form:input id="founderLineNumber" tabindex="9" path="founder_line_number" />
                            <br />
                            <form:errors path="founder_line_number" cssClass="error" />
                        </td>
                    </tr>
                    <tr>
                        <td><form:label for="species" path="species">Species:</form:label></td>
                        <td>
                            <form:input id="species" tabindex="4" path="species" />
                            <br />
                            <form:errors path="species" cssClass="error" />
                        </td>
                        <td><form:label for="plasmidConstruct" path="plasmid_construct">Plasmid Construct:</form:label></td>
                        <td colspan="2">
                            <form:input id="plasmidConstruct" tabindex="10" path="plasmid_construct" />
                            <br />
                            <form:errors path="plasmid_construct" cssClass="error" />
                        </td>
                    </tr>
                    <tr>
                        <td><form:label for="centimorgan" path="centimorgan">Centimorgan:</form:label></td>
                        <td>
                            <form:input id="centimorgan" tabindex="5" path="centimorgan" />
                            <br />
                            <form:errors path="centimorgan" cssClass="error" />
                        </td>
                        <td><form:label for="cytoband" path="cytoband">Cytoband:</form:label></td>
                        <td colspan="2">
                            <form:input id="cytoband" tabindex="11" path="cytoband" />
                            <br />
                            <form:errors path="cytoband" cssClass="error" />
                        </td>
                    </tr >
                    <tr>
                        <td colspan="4" align="right"><input type="submit" value="Save" formaction="geneManagementDetail.emma?id=${gene.id_gene}&amp;action=save" /></td>
                    </tr>
                </table>
        </form:form>
--%>
    </body>
</html>