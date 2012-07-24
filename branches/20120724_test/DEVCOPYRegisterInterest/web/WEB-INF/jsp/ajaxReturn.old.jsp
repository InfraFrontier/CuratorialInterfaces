<%-- 
    Document   : ajaxReturn.jsp
    Created on : 09-Feb-2010, 15:23:11
    Author     : phil
--%>


<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/datetime-1.0" prefix="dt" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/request-1.0" prefix="req" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<c:set var="keyRef" value='${returnedOut}'></c:set>

<c:if test='${not empty keyRef["ajaxReturn"]}'>
    <option value="">Please select..</option>
    <c:forEach var="item"  items='${keyRef["ajaxReturn"]}'>
        <c:choose>
            <c:when test='${item eq "No associated mutation sub-type"}'><option selected value="">${item}</option></c:when>
            <c:otherwise>
                <option value="${item}">${item}</option>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</c:if>

<c:if test='${empty keyRef["ajaxReturn"]}'>
</c:if>