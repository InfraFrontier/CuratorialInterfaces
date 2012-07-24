<%-- 
    Document   : submissionForm7
    Created on : 30-Jan-2012, 14:49:17
    Author     : phil
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:bind path="command.*" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EMMA Mutant Mouse Strain Submission Wizard - Step ${(sessionScope.pageCount)} of ${(sessionScope.totalStepCount)}</title>
                <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
                <script type="text/javascript" src="../js/jquery.parsequery.js"></script>
        <script type="text/javascript" src="../js/jquery.parsequery.min.js"></script>
        <script type="text/javascript" src="../js/biblioData.js"></script>
        <style type="text/css">@import url(../css/emmastyle.css);</style>
    </head>
    <body>
        <div id="references" class="step">
            <h2>
                References (Step ${(sessionScope.pageCount)} of ${(sessionScope.totalStepCount)})
            </h2>
            <p>
                If the mouse mutant strain you want to deposit in EMMA has been published, please enter the bibliographic information of one or more related publications. For the PubMed ID please <a target='PUBMED' href='http://www.pubmed.gov'>search PubMed</a>, a bibliographic database of biomedical articles.
            </p>
            <form:form method="POST" commandName="command"> 
            <spring:bind path="command.published">
                <div class="field">
                    <label class="label" for="published"><strong>Has this mouse mutant strain been published?<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <form:radiobutton path="${status.expression}" value="yes" id="published-yes" />Yes (please enter bibliographic information below)<br />
                        <form:radiobutton path="${status.expression}" value="no" id="published-no" />No<br />
                        <form:radiobutton path="${status.expression}" value="not_known" id="published-not_known" />Not known<br />
                    </div>
                    <form:errors path="${status.expression}" cssClass="error" />

                </div>
            </spring:bind>
            <spring:bind path="command.reference_descr">
                <fieldset class="reference">
                    <legend>Reference</legend>
                    <div class="field reference_descr">
                        <label class="label" for="reference_descr"><strong>Short description<sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <form:select path="${status.expression}" id="${status.expression}" title="Please select or enter a short description for this reference.">
                                <form:option value="">Please select..</form:option>
                                <form:option value="Generation of the mutant mouse strain">Generation of the mutant mouse strain</form:option>
                                <form:option value="Description of the mutant phenotype"> Description of the mutant phenotype</form:option> 
                                <form:option value="Cloning/characterization of the affected gene">Cloning/characterization of the affected gene</form:option>
                                <form:option  value="Other">Other (please specify)</form:option>
                            </form:select>
                        </spring:bind>
                        <spring:bind path="command.reference_descr_text">
                            <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                        </div>
                        <form:errors path="${status.expression}" cssClass="error" />
                    </div>
                </spring:bind>

                <spring:bind path="command.reference_pmid">
                    <div class="field reference_pmid">
                        <label class="label" for="reference_pmid"><strong>PubMed ID (if available)</strong></label>
                        <div class="input">
                            <form:input  id="${status.expression}" path="${status.expression}"></form:input>&nbsp;(Fields auto populated from PubMed using PubMed ID. Leave PubMed ID field to initiate)
                        </div>
                        <form:errors path="${status.expression}" cssClass="error" />
                    </div>
                </spring:bind>
                    <div id="bibRef" name="bibRef"></div>
                    <script type="text/javascript" > 
                    $('#reference_pmid').focusout(function() {
                        $('#bibRef').load('../ajaxReturn.emma',{pubmedid:$('#reference_pmid').val(), funct: "pubMed"});
                    });
                    
                </script>
                <spring:bind path="command.reference_title">
                    <div class="field reference_title">
                        <label class="label" for="reference_title"><strong>Title</strong></label>
                        <div class="input">
                            <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                        </div>
                        <form:errors path="${status.expression}" cssClass="error" />
                    </div>
                </spring:bind>

                <spring:bind path="command.reference_authors">                   
                    <div class="field reference_authors">
                        <label class="label" for="reference_authors"><strong>Authors</strong></label>
                        <div class="input">
                            <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                            </div>
                        <form:errors path="${status.expression}" cssClass="error" />
                    </div>

                </spring:bind>
                    
                   <spring:bind path="command.reference_journal">   
                <div class="field reference_journal">
                    <label class="label" for="reference_journal"><strong>Journal/Book<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <form:input  id="${status.expression}" path="${status.expression}"></form:input>

                    </div>
                    <form:errors path="${status.expression}" cssClass="error" />
                </div>
                
                 </spring:bind>
                    
                     <spring:bind path="command.reference_year"> 
                <div class="field reference_year">
                    <label class="label" for="reference_year"><strong>Year<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">

                        <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                    </div>
                    <form:errors path="${status.expression}" cssClass="error" />
                </div>
                  </spring:bind>
                
                    <spring:bind path="command.reference_volume">    
                <div class="field reference_volume">
                    <label class="label" for="reference_volume"><strong>Volume</strong></label>

                    <div class="input">
                        <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                    </div>
                    <form:errors path="${status.expression}" cssClass="error" />
                </div>
                
                </spring:bind> 
                
                    <spring:bind path="command.reference_pages"> 
                <div class="field reference_pages">
                    <label class="label" for="reference_pages"><strong>Pages<sup><font color="red">*</font></sup></strong></label>

                    <div class="input">
                        <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                    </div>
                    <form:errors path="${status.expression}" cssClass="error" />
                </div>
                 </spring:bind>
                    
                 <spring:bind path="command.removeReference">    
                <div>
                   
                    <input value="${status.expression}" type="button" class="remove_reference" id="${status.expression}" onClick="javascript:removeBibDetails();" />

                </div>
                    </spring:bind>
            </fieldset>
        <%--    <p>
                <spring:bind path="command.addReference"> 
                    <form:input id="${status.expression}" path="${status.expression}"></form:input>
                </spring:bind>
            </p>
            --%>
                <div id="subBiblios" name="subBiblios">
        <script type="text/javascript" > 
            $('#subBiblios').load('ajaxBiblios.emma',{
                action: "none",
                Id_sub:$('#encID').val()
            });
        </script>
    </div>
            
<p>
        <table width="150" cellspacing="8" class="formNav">
            <tr>
                <td colspan="2" align='center'><input type="submit" value="Next" name="_target8" /><br/></td>
            </tr>
            <tr>
                <td ><input type="submit" value="Previous" name="_target6" /></td>
                <td ><input type="submit" value="Cancel" name="_cancel" /></td>
            </tr>
        </table>
    </p> </form:form>
        </div>
    </body>
</html>
