<%-- 
    Document   : submissionForm1
    Created on : 30-Jan-2012, 14:44:44
    Author     : phil
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:bind path="command.*" />
<% java.util.Enumeration EditStrain = session.getAttributeNames();

java.util.Enumeration enumeration = session.getAttributeNames();
while (enumeration.hasMoreElements()) {
        Object element = enumeration.nextElement();

        System.out.println(element.toString());
    }

%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EMMA Mutant Mouse Strain Submission Wizard - Step ${(sessionScope.pageCount)} of ${(sessionScope.totalStepCount)}</title>
        <style type="text/css">@import url(../css/emmastyle.css);</style>
        <script type="text/javascript" src="../js/submission.js"></script>
    </head>
    <body>
        <h1>Submitter (Step ${(sessionScope.pageCount)} of ${(sessionScope.totalStepCount)})</h1>
        <form:form method="POST" commandName="command">
            
            <form:errors path="*" cssClass="errorblock"/><%--cssClass="errorblock" --%>
        <div id="start" class="step">
            <h2> Start </h2>
            <p> Please enter your email address for identification. For your convenience, if you have started or 
                completed a mutant mouse strain submission previously using this computer, you will be prompted to 
                either resume your incomplete submission from where you left off, or, if the submission was completed, 
                you will be asked whether you want to reuse your contact information. </p>
        <%--  <spring:bind path="command.peopleDAO.email">--%>
           <spring:bind path="command.submitter_email">
            <div class="field">
                <label class="label" for="${status.expression}">
                    <strong>
                        Email
                        <sup>
                            <font color="red">*</font>
                        </sup>
                    </strong>
                </label>
                <div class="input">
                    <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                    
                </div>
                <form:errors path="${status.expression}" cssClass="error" />
            </div>
          
         </spring:bind>

        </div>
<p>
        <table width="150" cellspacing="8" class="formNav">
            <tr>
                <td colspan="2" align='center'><input type="submit" value="Next" name="_target2" /><br/></td>
            </tr>
            <tr>
                <td ><input type="submit" value="Previous" name="_target0" /></td>
                <td ><input type="submit" value="Cancel" name="_cancel" /></td>
            </tr>
        </table>
    </p>
        </form:form>
    </body>
</html>
