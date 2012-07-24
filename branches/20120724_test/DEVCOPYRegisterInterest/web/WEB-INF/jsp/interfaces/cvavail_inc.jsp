<%-- 
    Document   : cvavail_inc
    Created on : 07-Apr-2009, 10:59:23
    Author     : phil
--%>

<%
            response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1

            response.setHeader("Pragma", "no-cache"); //HTTP 1.0

            response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/datetime-1.0" prefix="dt" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/request-1.0" prefix="req" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<c:set var="keyRefSub" value='${returnedOut}'></c:set>
<c:set var="command" value='${command}'></c:set>
<%
            String EditArch = request.getParameter("EditArch");
            String requestInterface = "";
            if (request.getParameter("ReqInterface") != null) {
                requestInterface = request.getParameter("ReqInterface");
            }
            if (requestInterface.equals("true")) {

%>
<c:set var="disabled" value='DISABLED'></c:set>
<%            }
// if in use from Request form then dislay to_distr values only as options

            if (request.getParameter("ReqForm") != null) {
                String requestForm = request.getParameter("ReqForm");
                %>
                <c:set var="requestForm" value="true"></c:set>
                <%
            }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Included Strain Details Page</title>
        <style type="text/css">@import url(../css/emmastyle.css);</style>
    </head>
    <body>
        
        <c:if test="${command.size > 0}">
            <c:forEach var="it" begin="0" end="${command.size-1}">
                
                <c:if test="${it == 0}"><c:set var="cvaDAO" value='${command.cvaDAO0}'></c:set></c:if>
                <c:if test="${it == 1}"><c:set var="cvaDAO" value='${command.cvaDAO1}'></c:set></c:if>
                <c:if test="${it == 2}"><c:set var="cvaDAO" value='${command.cvaDAO2}'></c:set></c:if>
                <c:if test="${it == 3}"><c:set var="cvaDAO" value='${command.cvaDAO3}'></c:set></c:if>
                <c:if test="${it == 4}"><c:set var="cvaDAO" value='${command.cvaDAO4}'></c:set></c:if>
                <c:if test="${it == 5}"><c:set var="cvaDAO" value='${command.cvaDAO5}'></c:set></c:if>
                
                <c:if test="${cvaDAO['code'] == 'C' && cvaDAO['in_stock'] =='1'}"><c:set var="availability_C" value="checked" /></c:if>
                <c:if test="${cvaDAO['code'] == 'E' && cvaDAO['in_stock'] =='1'}"><c:set var="availability_E" value="checked" /></c:if>
                <c:if test="${cvaDAO['code'] == 'O' && cvaDAO['in_stock'] =='1'}"><c:set var="availability_O" value="checked" /></c:if>
                <c:if test="${cvaDAO['code'] == 'S' && cvaDAO['in_stock'] =='1'}"><c:set var="availability_S" value="checked" /></c:if>
                <c:if test="${cvaDAO['code'] == 'L' && cvaDAO['in_stock'] =='1'}"><c:set var="availability_L" value="checked" /></c:if>
                <c:if test="${cvaDAO['code'] == 'R' && cvaDAO['in_stock'] =='1'}"><c:set var="availability_R" value="checked" /></c:if>
                
                <c:if test="${cvaDAO['code'] == 'C' && cvaDAO['to_distr'] =='1'}"><c:set var="distro_C" value="checked" /></c:if>
                <c:if test="${cvaDAO['code'] == 'E' && cvaDAO['to_distr'] =='1'}"><c:set var="distro_E" value="checked" /></c:if>
                <c:if test="${cvaDAO['code'] == 'O' && cvaDAO['to_distr'] =='1'}"><c:set var="distro_O" value="checked" /></c:if>
                <c:if test="${cvaDAO['code'] == 'S' && cvaDAO['to_distr'] =='1'}"><c:set var="distro_S" value="checked" /></c:if>
                <c:if test="${cvaDAO['code'] == 'L' && cvaDAO['to_distr'] =='1'}"><c:set var="distro_L" value="checked" /></c:if>
                <c:if test="${cvaDAO['code'] == 'R' && cvaDAO['to_distr'] =='1'}"><c:set var="distro_R" value="checked" /></c:if>
                
            </c:forEach>
        </c:if>
        <form:form>

            <c:choose>
                <c:when test="${requestForm == 'true'}">
                    <c:if test="${distro_C == 'checked'}"><input type="radio" name="req_material" value="Frozen ES cells"/>Frozen ES cells</c:if>
                    <c:if test="${distro_E == 'checked'}"><input type="radio" name="req_material" value="Frozen embryos"/>Frozen embryos</c:if>
                    <c:if test="${distro_O == 'checked'}"><input type="radio" name="req_material" value="Frozen ovaries"/>Frozen ovaries</c:if>
                    <c:if test="${distro_S == 'checked'}"><input type="radio" name="req_material" value="Frozen sperm"/>Frozen sperm</c:if>
                    <c:if test="${distro_L == 'checked'}"><input type="radio" name="req_material" value="Live mice on shelf"/>Live mice on shelf</c:if>
                    <c:if test="${distro_R == 'checked'}"><input type="radio" name="req_material" value="Rederivation of mice from frozen stock"/>Rederivation of mice from frozen stock</c:if>
                </c:when>
                
                <c:otherwise>
                    <table width="98%" border="0"
                           cellpadding="3" cellspacing="0" align="center" id="cvavailabilities">
                        <tr>
                            <td width="25%" valign="top" class="label"><b>In stock :</b><br>
                                <input type="checkbox" name="availability_C" value="1" ${availability_C} ${disabled}>Frozen ES cells<br>
                                <input type="checkbox" name="availability_E" value="1" ${availability_E} ${disabled}>Frozen embryos<br>
                                <input type="checkbox" name="availability_O" value="1" ${availability_O} ${disabled}>Frozen ovaries<br>
                                <input type="checkbox" name="availability_S" value="1" ${availability_S} ${disabled}>Frozen sperm<br>
                                <input type="checkbox" name="availability_L" value="1" ${availability_L} ${disabled}>Live mice on shelf<br>
                                <%--
                        Not needed rederived mice will be in stock
                        <input type="checkbox" name="availability_R" value="1" ${availability_R} ${disabled}>Rederivation of mice from frozen stock<br>
                                --%>
                            </td>
                            <td width="25%" valign="top" class="label"><b>Willing to distribute :</b><br>
                                <input type="checkbox" name="distro_C" value="1" ${distro_C} ${disabled}>Frozen ES cells<br>
                                <input type="checkbox" name="distro_E" value="1" ${distro_E} ${disabled}>Frozen embryos<br>
                                <input type="checkbox" name="distro_O" value="1" ${distro_O} ${disabled}>Frozen ovaries<br>
                                <input type="checkbox" name="distro_S" value="1" ${distro_S} ${disabled}>Frozen sperm<br>
                                <input type="checkbox" name="distro_L" value="1" ${distro_L} ${disabled}>Live mice on shelf<br>
                                <input type="checkbox" name="distro_R" value="1" ${distro_R} ${disabled}>Rederivation of mice from frozen stock<br>
                                <input type="hidden" name="EditArch" value="<%= EditArch%>" />
                            </td>
                            <td valign="middle" align="left"><td colspan="2" align="center"> <c:if test="${disabled !='DISABLED'}"><br/><input type="submit" name="updateAvail" id="updateAvail" value="Update Availabilities"/></c:if></td>
                            <td width="45%" >&nbsp</td>
                        </tr>
                     <!--   <tr>
                            <td colspan="2" align="center"> <c:if test="${disabled !='DISABLED'}"><br/><input type="submit" name="updateAvail" id="updateAvail" value="Update Availabilities"/></c:if></td>
                        </tr> -->
                    </table>
                </c:otherwise>
            </c:choose>
        </form:form>
        
    </body>
</html>