<%-- 
    Document   : submissionForm0
    Created on : 30-Jan-2012, 14:44:12
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
        <title>EMMA Mutant Mouse Strain Submission Wizard </title>
        <style type="text/css">@import url(../css/emmastyle.css);</style>
    </head>
    <body><%-- commandName="submissionForm" --%>
        <form:form method="POST" >

            <div id="splash" class="step">
                <p>
                    Dear Submitting Investigator,
                </p>
                <p>
                    Please use this wizard to provide detailed information about the mutant mouse strain that you want to 
                    submit to EMMA. This information is used to evaluate your submission request. Please note that if the 
                    request is approved the information you provide will be published on the EMMA website.
                </p>
                <p>
                    Please fill the form as accurately as you can using "unknown", "not applicable" or "none" if you do not 
                    possess the information requested. A sample submission is provided <a href="#">here</a> for your 
                    convenience to examine all the information that is required.
                </p>
                <p>
                    <b>Multiple strains must be submitted separately. One strain per submission.</b>
                </p>
                <p>
                    We strongly recommend that you carefully read the <a target="emma" href="<%-- TODO PATH TO EMMA FROM CONFIG --%>/procedures/costs.php">
                        EMMA Procedures</a> on this website. These documents describe the responsibilities EMMA has in 
                    maintaining and distributing the submitted strains as well as the responsibilities assumed by the 
                    submitter.
                </p>
                <p>
                    If your browser does not support forms you can <a href="mailto:info@emmanet.org">e-mail</a> all 
                    the complete strain information to EMMA.
                </p>
                <p>
                    Within 60 days you will be notified by e-mail of the result of the evaluation of your submission request.
                </p>
                <p>
                    Thank you for your interest in the European Mouse Mutant Archive.
                </p>
                <div class="field">
                    <div class="input">
                        <label>
                            <form:checkbox onclick="" id="agree" value="" path=""/>

                            &nbsp;I have read the information above and agree to the 
                            <a href="#">EMMA Terms &amp; Conditions</a>!</label>
                    </div>
<form:errors path="" cssClass="error" />
                   
                </div>
            </div>
<p>
        <table width="150" cellspacing="8" class="formNav">
            <tr>
                <td colspan="2" align='center'><input type="submit" value="Next" name="_target1" /><br/></td>
            </tr>
            <tr>
                <td ><input type="submit" value="Previous" name="" disabled/></td>
                <td ><input type="submit" value="Cancel" name="_cancel" /></td>
            </tr>
        </table>
    </p>
        </form:form>
    </body>
</html>
