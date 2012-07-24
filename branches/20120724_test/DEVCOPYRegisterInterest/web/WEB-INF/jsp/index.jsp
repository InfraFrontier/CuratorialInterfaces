<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>index page</title>
    </head>
    <body>
        <h5>This is the index page for EMMA interest registration functionality. If you are seeing this message you need to attempt to register an interest in a strain from the strain list <a href="http://www.emmanet.org/" title="EMMA Strain List">here</a></h5>
        <p align=center>
        <c:if test="${not empty message}">
            <font color="green"><c:out value="${message}" /></font>
            <c:set var="message" value="" scope="session" />
        </c:if>
        </p>
       <!-- <a href="requestForm.emma?ACTION=3">Static Display</a><br />
        <a href="requestFormView.emma?ID=3">Form</a>-->
    </body>
</html>