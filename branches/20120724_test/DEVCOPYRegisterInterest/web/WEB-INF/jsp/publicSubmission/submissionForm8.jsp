<%-- 
    Document   : SubmissionForm8
    Created on : 30-Jan-2012, 14:49:45
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
        <style type="text/css">@import url(../css/emmastyle.css);</style>
    </head>
    <body>
        <div id="characterization" class="step">
            <h2>
                Characterization (Step ${(sessionScope.pageCount)} of ${(sessionScope.totalStepCount)})
            </h2>

            <p>
                Please enter information on how you characterize the mouse strain you want to deposit in EMMA.
            </p>
            <form:form method="POST" commandName="command" >
                <spring:bind path="command.genotyping">
            <div class="field">
                <label class="label" for="${status.expression}"><strong>By genotyping</strong></label>
                <div class="input">
                    <form:textarea id="${status.expression}" path="${status.expression}" cols="50" rows="5" title="e.g., sequence of PCR primers and PCR settings, Southern probes and hybridization protocol. A good template for PCR genotyping is available &lt;a href='genotyping-template.doc' target='_blank'&gt;here&lt;/a&gt;."></form:textarea>
                    
                    <div>
                        <label>Upload as attachment&nbsp;<spring:bind path="command.genotyping_file"><input type="file" id="${status.expression}" path="${status.expression}" enctype="multipart/form-data"/><form:errors path="${status.expression}" cssClass="error" /></spring:bind></label>
                    </div>
                </div>
<form:errors path="${status.expression}" cssClass="error" />
            </div>
                </spring:bind>
                
                <spring:bind path="command.phenotyping">
            <div class="field">

                <label class="label" for="${status.expression}"><strong>By phenotyping</strong></label>
                <div class="input">
                    <form:textarea id="${status.expression}" path="${status.expression}" cols="50" rows="5" title="e.g., coat colour, etc."></form:textarea>
                    <div>
                        <label>Upload as attachment&nbsp;<spring:bind path="command.phenotyping_file"><input type="file" id="${status.expression}" path="${status.expression}" enctype="multipart/form-data"/><form:errors path="${status.expression}" cssClass="error" /></spring:bind></label>
                    </div>
                </div>
<form:errors path="${status.expression}" cssClass="error" />
            </div>
                    </spring:bind>
         <spring:bind path="command.othertyping">           
            <div class="field">
                <label class="label" for="${status.expression}"><strong>By any other means that are not genotyping or phenotyping</strong></label>
                <div class="input">
                    <form:textarea id="${status.expression}" path="${status.expression}" cols="50" rows="5" ></form:textarea>
                    <div>
                        <label>Upload as attachment&nbsp;<spring:bind path="command.othertyping_file"><input type="file" id="${status.expression}" path="${status.expression}" enctype="multipart/form-data"/><form:errors path="${status.expression}" cssClass="error" /></spring:bind></label>
                    </div>
                </div>
<form:errors path="${status.expression}" cssClass="error" />
            </div>
</spring:bind>
 <p>
        <table width="150" cellspacing="8" class="formNav">
            <tr>
                <td colspan="2" align='center'><input type="submit" value="Next" name="_target9" /><br/></td>
            </tr>
            <tr>
                <td ><input type="submit" value="Previous" name="_target7" /></td>
                <td ><input type="submit" value="Cancel" name="_cancel" /></td>
            </tr>
        </table>
    </p>
            </form:form>
            </div> 
    </body>
</html>
