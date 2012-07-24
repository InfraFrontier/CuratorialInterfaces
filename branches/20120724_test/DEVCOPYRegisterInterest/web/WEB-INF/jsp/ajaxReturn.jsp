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

<%--<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>

    </body>
</html>
--%>
<c:set var="keyRef" value='${returnedOut}'></c:set>
<c:choose>
    <c:when test='${not empty keyRef["ajaxReturn"] && empty param.query}'>
        <c:forEach var="strain"  items='${keyRef["ajaxReturn"]}'>
            ${strain[0]}|${strain[1]}|${strain[2]}
        </c:forEach>
    </c:when>
    <c:otherwise>
        <c:if test='${param.query=="bg"}'>
            <c:choose>
                <c:when test='${param.list=="cryo"}'>
                    <c:forEach var="bg"  items='${keyRef["ajaxReturn"]}'>
                        ${bg.name}
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <c:forEach var="bg"  items='${keyRef["ajaxReturn"]}'>
                        ${bg[1]}
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </c:if>
    </c:otherwise>
</c:choose>
<c:if test='${empty keyRef["ajaxReturn"] &&  not empty param.display}'>
    <c:if test="${param.display == 'disabled'}" >
        <c:set var="displayClass" value="disabled"/>
        <c:set var="fieldDisplay" value="disabled"/>
    </c:if>   
    <c:if test="${param.display == 'enabled'}" >
        <c:set var="displayClass" value=""/>
        <c:set var="fieldDisplay" value=""/>
    </c:if>
    <table  id="taOptions" width: 100%; text-align: left; margin-left: auto; margin-right: auto;" border="0" cellpadding="0" cellspacing="0">
            <tr><td colspan="2"><br/><br/><b>Application for <a href="http://www.emmanet.org/projects/emmaservice-activities.php"  target="_blank"> Transnational Access (TA) Activity</a> - free of charge access to EMMA mouse mutant resources.</b></td></tr>
        <tr><td colspan="2">The two TA options are reserved to customers based in EU Member States and EU Associated Countries. Further information on <a href="http://www.emmanet.org/projects/ta-activity.php" target="_blank">Eligibility criteria and selection procedure for TA activity</a>.<br/><br/></td></tr>
        <tr><td  class="${displayClass}" colspan="2"><b>TA Option A:</b><br /></td></tr>
        <tr><td  class="${displayClass}"><%--<spring:bind path="command.application_type">--%><input ${fieldDisplay} type="radio"  name="application_type" value="ta_or_request" <%--onchange="javascript:show('taProjDesc');return false;" /></spring:bind>--%></td><td  class="${displayClass}">Please select to<c:if test="${(empty param.status)}"> confirm you have read <a href="#" onclick="javascript:ajax('conditions.html#top','conditions');return false;" title="EMMA Conditions">the conditions</a>, </c:if> apply for free of charge TA and agree to pay the <a href = "http://www.emmanet.org/strains.php"  target="_blank">service charge</a> plus shipping cost if the TA application is rejected</td></tr>
        <tr><td colspan="2">&nbsp;</td></tr>
        <tr><td  class="${displayClass}" colspan="2"><b>TA Option B:</b><br /></td></tr>
        <tr><td  class="${displayClass}"><%--<spring:bind path="command.application_type">--%><input ${fieldDisplay} type="radio"  name="application_type" value="ta_only" <%--onchange="javascript:show('taProjDesc');return false;" /></spring:bind>--%></td><td  class="${displayClass}">Please select to<c:if test="${(empty param.status)}"> confirm you have read <a href="#" onclick="javascript:ajax('conditions.html#top','conditions');return false;" title="EMMA Conditions">the conditions</a> and </c:if> apply for free of charge TA only. In case the TA application is rejected the request process will then be terminated</td></tr>
    </table>
    <br/>
    <table id="taProjDesc" width="100%" >
        <tr>
            <td valign="top" class="${displayClass}">
                Description of project (1/2 page) involving requested EMMA mouse mutant resource.<br />The <b>project description</b> is <b>mandatory</b> (for TA applicants only) and will be used by the Evaluation Committee for selection of applicants.
            </td>
        </tr>
        <tr>
            <td valign="top">
                <textarea ${fieldDisplay} name="project_description" rows="30" cols="80" maxlength="1750"  wrap="soft"></textarea><br />      
            </td>
        </tr>
    </table>
</c:if>
<%--
JSON return for submission form people data fro PI and contact
--%>

<c:if test='${not empty keyRef["JSON"] && param.funct == "peopleCall"}'>
    <%--        <html>
<head>
<meta http-equiv="Content-Type" content="application/json, charset=UTF-8" />

        <title></title>
    </head>
    <body>
        ${keyRef.JSON}
        <c:forEach  varStatus="status" var="item"  items='${keyRef.JSON}'>
            ${item.firstname} ${item.surname}<br/>
            <br/>
        </c:forEach>
    --%>  
    <c:set var="PeopleDAO" value="${keyRef.peopleDAO}" />  
    <c:if test="${not empty PeopleDAO}">
        The following user data is held associated with the e-mail address  ${command.peopleDAO.email} :<br/>
        <c:forEach var="obj" items="${PeopleDAO}">
            <p class="userdetailspanel">
                <c:set var="user" value="${obj}"/> 
                Name: ${user['firstname']}&nbsp;
                ${user['surname']}<br/>
                Email: ${user['email']}<br/>
                Phone: ${user['phone']}<br/>
                <c:if test="${not empty user['fax']}">Fax: ${user['fax']}<br/></c:if>
                <c:if test="${not empty user.labsDAO['name']}">Institute: ${user.labsDAO['name']}<br/></c:if>
                <c:if test="${not empty user.labsDAO['dept']}">Department: ${user.labsDAO['dept']}<br/></c:if>
                <c:if test="${not empty user.labsDAO['addr_line_1']}">Address Line 1: ${user.labsDAO['addr_line_1']}<br/></c:if>
                <c:if test="${not empty user.labsDAO['addr_line_2']}">Address Line 2: ${user.labsDAO['addr_line_2']}<br/></c:if>
                <c:if test="${not empty user.labsDAO['town']}">Town: ${user.labsDAO['town']}<br/></c:if>
                <c:if test="${not empty user.labsDAO['province']}">County: ${user.labsDAO['province']}<br/></c:if>
                <c:if test="${not empty user.labsDAO['postcode']}">Postcode: ${user.labsDAO['postcode']}<br/></c:if>
                <c:if test="${not empty user.labsDAO['country']}">Country: ${user.labsDAO['country']}<br/></c:if>
                <br/>Would you like to use the details above ?&nbsp;
                <input type="button" id="modal" name="yes" onClick="populateUserDetails('?uid=${user['id_per']}\
&email=${user['email']}&title=${user['title']}&firstname=${user['firstname']}&surname=${user['surname']}&phone=${user['phone']}\
&fax=${user['fax']}&institute=${user.labsDAO['name']}&dept=${user.labsDAO['dept']}\
&addr_line_1=${user.labsDAO['addr_line_1']}&addr_line_2=${user.labsDAO['addr_line_2']}\
&town=${user.labsDAO['town']}&county=${user.labsDAO['province']}\
&postcode=${user.labsDAO['postcode']}&country=${user.labsDAO['country']}&fieldset=${param.fieldset}')" 
                       value="Yes"/><br/>
            </p>
        </c:forEach>
    </c:if>
    <%--</body>
</html> --%>
</c:if>
        <c:set var="paper" value="${keyRef.paper}" />  
         <c:if test="${not empty paper}">
        
             <script type="text/javascript" >
                 populateBibDetails("?title=${paper[0]}&author1=${paper[1]}&author2=${paper[2]}\
&journal=${paper[3]}&year=${paper[4]}&volume=${paper[5]}&issue=${paper[6]}&pages=${paper[7]}\
&paperid=${paper[8]}",0);

             </script>  
            </c:if>