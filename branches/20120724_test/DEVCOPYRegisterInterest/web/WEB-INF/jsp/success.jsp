<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EMMA Form Submission - Success Page</title>
            <style type="text/css">@import url(css/emmastyle.css);</style>
    </head>
    <body>
<p>&nbsp;</p>
    <h1>EMMA Form Submission - Success Page</h1>
    
        <p align=center>
        <c:if test="${not empty message}">
            <font color="green"><c:out value="${message}" /></font>
            <c:set var="message" value="" scope="session" />
        </c:if>
        <br />
        <br />
        
        Return to the EMMA Strain List <a href="http://www.emmanet.org/mutant_types.php">here</a>.
    </p>
    </body>
</html>
