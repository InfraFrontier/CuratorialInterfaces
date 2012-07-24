<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
========================================================
views should be stored under the web-inf folder so that
they are not accessible except through controller process

this jsp is here to provide a redirect to your controller
servlet but should be the only jsp outide of the web-inf
========================================================
--%>
<c:redirect url="/index.emma" />