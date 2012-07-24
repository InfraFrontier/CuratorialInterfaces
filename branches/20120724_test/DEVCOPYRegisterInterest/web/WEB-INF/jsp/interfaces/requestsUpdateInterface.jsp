<%-- 
    Document   : requestsUpdateInterface
    Created on : 24-Jul-2008, 12:42:01
    Author     : phil
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="keyRef" value='${command}'></c:set>
<spring:bind path="command.*"></spring:bind>
<%
            String id = request.getParameter("strainID");
            String reqID = request.getParameter("Edit");
            String archID = request.getParameter("archID");
            String taEdit = request.getParameter("ta");

            java.io.BufferedReader inSources = new java.io.BufferedReader(new java.io.FileReader("/nfs/panda/emma/tmp/reqfslist"));
            String strInSources;
            String strOutSources = "";

            while ((strInSources = inSources.readLine()) != null) {
                strOutSources = strOutSources + strInSources;
            }

%>
<c:set var="ta" value="<%=taEdit%>"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style type="text/css">@import url(../css/emmastyle.css);</style>
        <style type="text/css">@import url(../css/calendar-blue.css);</style>
        <script type="text/javascript" src="../js/calendar.js"></script>
        <script type="text/javascript" src="../js/calendar-en.js"></script>
        <script type="text/javascript" src="../js/calendar-setup.js"></script>
        <script type="text/javascript" src="../js/buttoncontrols.js"></script>
        <script type="text/javascript" src="../js/synonymDiv.js"></script>
         <script type="text/javascript">
        function reqStatusCancTrigger(){
            var element = document.getElementById("req_status");
var optionSelected = element.options[element.selectedIndex].value;
if(optionSelected == "CANC"){
    ShowContent('cancellation');
    return false;
    //        alert("function called:" + optionSelected);
        }
        }
        
        </script>
        <title>EMMA Request Update Interface</title>
    </head>
    <body>
        
        <span id="loginHeader">Requests update for request <%= reqID%> - Logged in as user <c:out value="${fn:toUpperCase(sessionScope.SPRING_SECURITY_LAST_USERNAME)}"/></span>
        <spring:bind path="command.*" />
        <br /><br />
        <%-- include consistent navigation --%>
        <%@ include file="navigation_inc.html" %>
        <center>
            <spring:bind path="command.*">
                <c:if test="${not empty status.errorMessages}">
                    <c:forEach var="error" items="${status.errorMessages}">
                        <font color="red"><c:out value="${error}" escapeXml="false" /> </font>
                        <br />
                    </c:forEach>
                </c:if>
            </spring:bind>
            
            <c:if test="${not empty message}">
                <font color="green"><c:out value="${message}" /></font>
                <c:set var="message" value="" scope="session" />
            </c:if>
        </center>
        <%-- 
        TODO: RESOLVE FUGLEY HACK 
        INCLUDING A JSP FILE TO DISPLAY DETAILS OF STRAIN FROM
        STRAINSDAO AS WEB REQUEST DETAILS NEED TO BIND TO WEBREQUESTDAO
        AND CANNOT SEEM TO BRING STRAIN INFO AND WEB REQUEST INFO AT SAME TIME
        MAY NEED TO USE ANOTHER CONTROLLER BUT TIME IS SHORT
        --%>
        
        <center>
            
            <IFRAME  HEIGHT="200" SRC="strainDetails_inc.emma?strainID=<%=id%>&archID=<%=archID%>&pdf=${sessionScope.reqPDF}" WIDTH=85%  FRAMEBORDER=0 scrolling="no">
                
            </IFRAME>
            
            <IFRAME  HEIGHT="165" SRC="cvavail_inc.emma?EditArch=<%=id%>&ReqInterface=true" WIDTH=85%  FRAMEBORDER=0 scrolling="no">
                
            </IFRAME>
        </center>
        <form method="post">
            <%-- START DATA GRID --%>
            <input type="hidden" name="user" value="<c:out value="${sessionScope.SPRING_SECURITY_LAST_USERNAME}"/>"/>
            <table border="0" width="85%" align="center">
                
                <tbody>
                    <tr>
                        <td colspan="4" class="sectHead">Request details</td>
                    </tr>
                    <tr>
                        <td>Scientists name:</td>
                        <td><spring:bind path="command.sci_firstname">
                            <c:out value="${status.value}"/>
                        </spring:bind>&nbsp;<spring:bind path="command.sci_surname">
                        <c:out value="${status.value}"/></spring:bind>
                        <td>Requested material:</td>
                        <td><spring:bind path="command.req_material">
                                <c:out value="${status.value}"/>
                                <select name="<c:out value="${status.expression}"/>">
                                    <c:choose>
                                        <c:when test="${fn:containsIgnoreCase(status.value,'live')}" >
                                            <option value="live animals" selected>Live animals</option>
                                        </c:when>
                                        <c:otherwise> 
                                            <option value="live animals">Live animals</option> 
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${fn:containsIgnoreCase(status.value,'frozen')}" >
                                            <option value="frozen material" selected>Frozen material</option>
                                        </c:when>
                                        <c:otherwise> 
                                            <option value="frozen material">Frozen Material</option> 
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${status.value eq 'first available'}" >
                                            <option value="first available" selected>First available</option>
                                        </c:when>
                                        <c:otherwise> 
                                            <option value="first available">First available</option> 
                                        </c:otherwise>
                                    </c:choose>
                                    <c:if test="${empty status.value}" >
                                        <option value="" selected>Please select...</option>
                                    </c:if>
                                </select>
                            </spring:bind>
                        </td>
                    </tr>
                    <tr>
                        <td>Contacts name:</td>
                        <td><spring:bind path="command.con_firstname">
                                <c:out value="${status.value}"/>
                            </spring:bind>&nbsp;<spring:bind path="command.con_surname">
                        <c:out value="${status.value}"/></spring:bind></td>
                        <td>Country:</td>
                        <td>
                        <spring:bind path="command.con_country"><c:out value="${status.value}"/>
                        </spring:bind>
                    </tr>
                    <tr>
                        <td>Request type:</td>
                        <td><c:choose><c:when test="${keyRef['register_interest'] == 1}">Registration Of Interest (ROI)</c:when><c:otherwise>Confirmed request</c:otherwise></c:choose></td>
                        <td>Submitted date:</td>
                        <td>${fn:substringBefore(keyRef["timestamp"], " ")}</td>
                    </tr>
                    <%--<c:if test="${not empty keyRef['roi_date']}">--%>
                        <tr>
                            <td>ROI Date: </td>
                            <td>${fn:substringBefore(keyRef["roi_date"], " ")}</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            </tr>
                   <%-- </c:if>--%>
                    <tr>
                                                <tr>
                            <td>MTA Arrived Date: </td>
                            <td><spring:bind path="command.mta_arrived_date"><input type="text"  size="22" name="<c:out value='${status.expression}'/>"   id="<c:out value='${status.expression}'/>" value="<c:out value='${fn:substringBefore(status.value, " ")}'/>" ${show} > <img src="../images/cal.gif"  id="MTAArrivedDate" border="0"/>
                                        <script type="text/javascript">
                                            Calendar.setup(
                                            {
                                                inputField  : "<c:out value='${status.expression}'></c:out>",
                                                ifFormat    : "%Y-%m-%d",
                                                button      : "MTAArrivedDate"     
                                            }
                                        );
                                        </script></spring:bind> </td>
                            <td>All Paperwork in Place Date:</td>
                            <td> <spring:bind path="command.all_paperwork_date"><input type="text"  size="22" name="<c:out value='${status.expression}'/>"   id="<c:out value='${status.expression}'/>" value="<c:out value='${fn:substringBefore(status.value, " ")}' />" ${show} > <img src="../images/cal.gif"  id="allPaperworkDate" border="0"/>
                                        <script type="text/javascript">
                                            Calendar.setup(
                                            {
                                                inputField  : "<c:out value='${status.expression}'></c:out>",
                                                ifFormat    : "%Y-%m-%d",
                                                button      : "allPaperworkDate"     
                                            }
                                        );
                                        </script></spring:bind> </td>
                            </tr>
                   
                    <tr>
                        <td> Request status:</td>
                        <td>
                            <spring:bind path="command.req_status">
                                <select id="<c:out value="${status.expression}"/>" name="<c:out value="${status.expression}"/>" onChange="javascript:reqStatusCancTrigger()">
                                    <c:choose>
                                        <c:when test="${empty status.value}" >
                                            <option value="" selected></option>
                                        </c:when>
                                        <c:otherwise> 
                                            <option value=""></option> 
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${status.value  == 'IN_PR'}" >
                                            <option value="IN_PR" selected>In progress</option>
                                        </c:when>
                                        <c:otherwise> 
                                            <option value="IN_PR">In progress</option> 
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${status.value  == 'CANC'}" >
                                            <option value="CANC" selected>Cancelled</option>

                                        </c:when>
                                        <c:otherwise> 
                                            <option value="CANC">Cancelled</option> 
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${status.value  == 'SHIP'}" >
                                            <option value="SHIP" selected>Shipped</option>
                                        </c:when>
                                        <c:otherwise> 
                                            <option value="SHIP">Shipped</option> 
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${status.value  == 'TO_PR'}" >
                                            <option value="TO_PR" selected>To process</option>
                                        </c:when>
                                        <c:otherwise> 
                                            <option value="TO_PR">To process</option> 
                                        </c:otherwise>
                                    </c:choose>
                                </select>
                            </spring:bind>
                            <input type="hidden" name="currentReqStatus" value="${command.req_status}"/>
                            <input type="hidden" name="bil_vat" value="${command.bil_vat}"/>

                            <div id="cancellation" class="plain">
                                <c:if test="${keyRef['register_interest'] == 1}">
                                <!--<a href="javascript:void(0)"  onclick="HideContent('cancellation'); return false;" >Hide cancellation fields</a><br/>-->
                                   Reason for ROI cancellation:<spring:bind path="command.cancelation_reason">
                                <select name="<c:out value="${status.expression}"/>">
                                    <option value="" selected="yes">Please select</option>
                                    <option value="No response from client" <c:if test="${keyRef['cancelation_reason'] == 'No response from client'}">selected</c:if>>No response from client</option>
                                    <option value="Stock sourced from elsewhere" <c:if test="${keyRef['cancelation_reason'] == 'Stock sourced from elsewhere'}">selected</c:if>>Stock sourced from elsewhere</option>
                                    <option value="Delivery time too long" <c:if test="${keyRef['cancelation_reason'] == 'Delivery time too long'}">selected</c:if>>Delivery time too long</option>
                                    <option value="EMMA charges too high" <c:if test="${keyRef['cancelation_reason'] == 'EMMA charges too high'}">selected</c:if>>EMMA charges too high</option>
                                    <option value="Strain no longer required" <c:if test="${keyRef['cancelation_reason'] == 'Strain no longer required'}">selected</c:if>>Strain no longer required</option>
                                    <option value="QC failed" <c:if test="${keyRef['cancelation_reason'] == 'QC failed'}">selected</c:if>>QC failed</option>
                                    <option value="Customer requested delayed shipment" <c:if test="${keyRef['cancelation_reason'] == 'Customer requested delayed shipment'}">selected</c:if>>Customer requested delayed shipment</option>
                                    <option value="Other reasons" <c:if test="${keyRef['cancelation_reason'] == 'Other reasons'}">selected</c:if>>Other reasons</option>
                         </select>  
                            </spring:bind>
                                   </c:if>
                            </div>
                                                        <c:if test="${keyRef['req_status'] == 'CANC'}">
                                <script type="text/javascript">
                                    ShowContent('cancellation');
                                </script>
                            </c:if>
                        </td>
                        <td>Processed date: </td>
                        <td><spring:bind path="command.date_processed">
                            <input type="text" id="<c:out value="${status.expression}"/>" name="<c:out value="${status.expression}"/>" size="10" value="<c:out value="${status.value}"/>">
                            <img src="../images/cal.gif"  id="ShipDate" border="0">
                            <script type="text/javascript">

                                Calendar.setup(
                                {
                                    inputField  : "<c:out value='${status.expression}'></c:out>",
                                    ifFormat    : "%Y-%m-%d",    
                                    button      : "ShipDate"     
                                }
                            );
                            </script>
                            </spring:bind>
                      
                        </td>
                    </tr>
                

                    
                    <tr>
                        <td>Material in stock at beginning of request:</td>
                        <td>
                            <spring:bind path="command.req_material_state">
                                <select name="<c:out value="${status.expression}"/>">
                                    <option value="" selected="yes">Please select</option>
                                    <option value="live animals" <c:if test="${keyRef['req_material_state'] == 'live animals'}">selected</c:if>>Live animals</option>
                                    <option value="frozen embryos" <c:if test="${keyRef['req_material_state'] == 'frozen embryos'}">selected</c:if>>Frozen embryos</option>
                                    <option value="frozen sperm" <c:if test="${keyRef['req_material_state'] == 'frozen sperm'}">selected</c:if>>Frozen sperm</option>
                                    <option value="ES cells" <c:if test="${keyRef['req_material_state'] == 'ES cells'}">selected</c:if>>ES cells</option>
                                    <option value="targeting vectors" <c:if test="${keyRef['req_material_state'] == 'targeting vectors'}">selected</c:if>>Targeting vectors</option>
                                </select>  
                            </spring:bind>
                        </td>
                        <input type="hidden" name="projectID" value="${sessionScope.projectID}"/>
                        <c:choose>
                            <%-- project id now changed to cvrtools id as project id used in a different way now and will not id every eucomm strain --%>
                            <c:when test="${sessionScope.projectID == '9'}"><%--   && sessionScope.SPRING_SECURITY_LAST_USERNAME == 'super'} --%>
                                <td class="boxoutB">
                                    EUCOMM Mice funding identification 
                                </td>
                                <td class="boxoutB">
                                    <spring:bind path="command.eucomm_funding">
                                        <select name="<c:out value="${status.expression}"/>">
                                            <option value="">Value MUST be selected >></option>
                                            <option value="EUCOMM mice" <c:if test="${keyRef['eucomm_funding'] == 'EUCOMM mice'}">selected</c:if>>EUCOMM/EMMA mice</option>
                                            <option value="EMMA mice" <c:if test="${keyRef['eucomm_funding'] == 'EMMA mice'}">selected</c:if>>EMMA mice</option>
                                        </select>
                                    </spring:bind>
                                </td>                        
                            </c:when>
                            <c:otherwise>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                            </c:otherwise>
                        </c:choose>   
                    </tr>
                    <tr>
                        <td>Notes:</td>
                        <td><spring:bind path="command.notes">
                            <textarea name="<c:out value='${status.expression}'/>"  rows="4" cols="50" maxlength="250" wrap="virtual"><c:out value="${status.value}"/></textarea>
                        </spring:bind></td>
                        <td>Quantity shipped:</td>
                        <td> <spring:bind path="command.shipped">
                            <textarea name="<c:out value='${status.expression}'/>" rows="4" cols="50" maxlength="250" wrap="virtual"><c:out value="${status.value}"/></textarea>
                        </spring:bind></td> 
                        
                    </tr>
                    <tr>
                        <td colspan="4" class="sectHead">TA Request details</td>
                    </tr>
                    <%--     <c:choose>
                        <c:when test="${not empty keyRef['application_type']}">--%>
                    <spring:bind path="command.eligible_country"> <input type="hidden" name="<c:out value='${status.expression}'/>" value='${status.value}'/></spring:bind>
                    <c:if test="${not empty keyRef['ta_panel_decision']}"><c:set var="show" value="disabled"/></c:if>
                    <tr>
                        
                        <td>Application type: </td><td><c:if test="${keyRef['application_type']=='ta_only'}">TA only</c:if><c:if test="${keyRef['application_type']=='ta_or_request'}">TA or request</c:if><c:if test="${empty keyRef['application_type']}">Standard request</c:if></td> 
                        <td class="boxout">Panel decision: </td><td class="boxout"><spring:bind path="command.ta_panel_decision">
                                
                                <c:choose>
                                    <c:when test="${ fn:toUpperCase(sessionScope.SPRING_SECURITY_LAST_USERNAME) == 'SUPER'}">
                                    
                                        <select ${show} name="<c:out value='${status.expression}'/>">
                                            <option value="" <c:if test="${empty keyRef[ 'ta_panel_decision']}">selected</c:if>>Please select</option>
                                            <option value="yes" <c:if test="${keyRef['ta_panel_decision'] == 'yes'}">selected</c:if>>Yes</option>
                                            <option value="no" <c:if test="${keyRef['ta_panel_decision'] == 'no'}">selected</c:if>>No</option>
                                        </select>
                                        <input name="noTAinfo" type="hidden" value="">
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value='${status.value}'/><input name="noTAinfo" type="hidden" value="true">
                                    </c:otherwise>
                                </c:choose>
                            </spring:bind>
                        </td>
                    </tr>
                    <tr>
                        
                        <td>Panel submission date: </td>
                        <td>
                        
                        <spring:bind path="command.ta_panel_sub_date">
                            <c:choose>                     
                                <c:when test="${ fn:toUpperCase(sessionScope.SPRING_SECURITY_LAST_USERNAME) == 'SUPER'}">
                                
                                    <input type="text"  size="22" name="<c:out value='${status.expression}'/>"   id="<c:out value='${status.expression}'/>" value="<c:out value='${status.value}' />" ${show} > <img src="../images/cal.gif"  id="taSubDate" border="0"/>
                                    <script type="text/javascript">
                                        Calendar.setup(
                                        {
                                            inputField  : "<c:out value='${status.expression}'></c:out>",
                                            ifFormat    : "%Y-%m-%d",
                                            button      : "taSubDate"     
                                        }
                                    );
                                    </script>
                                </c:when>
                                <c:otherwise>
                                    <c:out value='${status.value}'/>
                                </c:otherwise>
                            </c:choose>
                        </spring:bind>           
                        
                        <td>Panel decision date: </td>
                        
                        <td>
                            <spring:bind path="command.ta_panel_decision_date">
                                
                                <c:choose>        
                                    <c:when test="${ fn:toUpperCase(sessionScope.SPRING_SECURITY_LAST_USERNAME) == 'SUPER'}">
                                    
                                        <input type="text"  size="22" name="<c:out value='${status.expression}'/>"   id="<c:out value='${status.expression}'/>" value="<c:out value='${status.value}' />" ${show} > <img src="../images/cal.gif"  id="taDecDate" border="0"/>
                                        <script type="text/javascript">
                                            Calendar.setup(
                                            {
                                                inputField  : "<c:out value='${status.expression}'></c:out>",
                                                ifFormat    : "%Y-%m-%d",
                                                button      : "taDecDate"     
                                            }
                                        );
                                        </script>
                                        
                                        
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value='${status.value}'/>
                                    </c:otherwise>
                                </c:choose>
                                
                            </spring:bind>
                        </td>
                    </tr>
                    <tr>
                        
                        <c:if test="${not empty keyRef.sourcesrequestsDAO}">
                            <c:forEach var="fsource" items="${keyRef.sourcesrequestsDAO}">
                                <c:set var="sourid" value="${fsource['sour_id']}"/>
                                
                            </c:forEach>
                            <%
            strOutSources = strOutSources.replace("<option value=\"0\" selected>", "<option value=\"0\">");
            strOutSources = strOutSources.replace("<option value=\"" + pageContext.getAttribute("sourid").toString() + "\">", "<option value=\"" + pageContext.getAttribute("sourid").toString() + "\" selected>");
                            %>
                        </c:if>
                        
    
                        <td>Funding source: </td>
                        <td>
                            <select name="fSourceID"><%= strOutSources%>
                            </select>
                        </td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="4"><a href="javascript:void(0)"  onclick="ShowContent('taProjDesc'); return false;" >Show Project Description</a><div id="taProjDesc" class="syndiv"><a href="javascript:void(0)"  onclick="HideContent('taProjDesc'); return false;" >-</a> <a href="javascript:void(0)"  onclick="HideContent('taProjDesc'); return false;" >Hide Project Description</a><br/><spring:bind path="command.project_description"><textarea name="<c:out value='${status.expression}'/>"  rows="25" cols="50" maxlength="3000" wrap="virtual" disabled><c:out value='${status.value}' /></textarea></spring:bind></div></td>
                    </tr>
                    <%--         </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="4">No TA request details available.</td>
                            </tr>
                        </c:otherwise>                        
                    </c:choose>--%>
                    <tr>
                        <td colspan="8"> <p style="text-align: center;" class="left">
                                <input name="update" value="Update" class="button" type="submit">
                                <input name="Clear all fields" value="Clear all fields" class="button" type="reset">
                        </p></td>
                    </tr>
                </tbody>
            </table>
            
            <%-- END  DATA GRID --%>
        </form>
        <br><br>
        <%-- include consistent footer/navigation --%>
        <%@ include file="footer_inc.html" %>
    </body>
</html>
