<%-- 
    Document   : submissionForm6
    Created on : 30-Jan-2012, 14:48:36
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
        <div id="phenotype" class="step">
            <h2>
                Phenotype (Step ${(sessionScope.pageCount)} of ${(sessionScope.totalStepCount)})
            </h2>

            <p>
                Please enter the phenotype information of the mouse mutant strain you want to deposit in EMMA.
            </p>
            <form:form method="POST" commandName="command"> 
                <spring:bind path="command.homozygous_phenotypic_descr">
                    <div class="field">
                        <label class="label" for="${status.expression}"><strong>Phenotypic description of homozygous mice<sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <form:textarea id="${status.expression}" path="${status.expression}"  cols="50" rows="5" 
                                           title="A short description of the mutant phenotype of homozygous mice (this will be used in the public web listing, see an example)."></form:textarea>
                            </div>
                        <form:errors path="${status.expression}" cssClass="error" />
                    </div>
                </div>
            </spring:bind>
            <spring:bind path="command.heterozygous_phenotypic_descr">
                <div class="field">
                    <label class="label" for="heterozygous_phenotypic_descr"><strong>Phenotypic description of heterozygous/hemizygous mice<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <form:textarea id="${status.expression}" path="${status.expression}"  cols="50" rows="5" 
                                       title="A short description of the mutant phenotype of heterozygous/hemizygous mice (this will be used in the public web listing, see an example)."></form:textarea>      
                        </div>
                    <form:errors path="${status.expression}" cssClass="error" />
                </div>
            </spring:bind>
<p>
        <table width="150" cellspacing="8" class="formNav">
            <tr>
                <td colspan="2" align='center'><input type="submit" value="Next" name="_target7" /><br/></td>
            </tr>
            <tr>
                <td ><input type="submit" value="Previous" name="_target5" /></td>
                <td ><input type="submit" value="Cancel" name="_cancel" /></td>
            </tr>
        </table>
    </p>
        </form:form>
    </div>
</body>
</html>
