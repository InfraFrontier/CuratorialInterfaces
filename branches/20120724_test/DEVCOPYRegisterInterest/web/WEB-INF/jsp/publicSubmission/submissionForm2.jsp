<%-- 
    Document   : submissionForm2
    Created on : 30-Jan-2012, 14:45:19
    Author     : phil
--%>


<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/datetime-1.0" prefix="dt" %>
<spring:bind path="command.*" />
<c:set var="PeopleDAO" value='${requestScope.userdaos}'></c:set>
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>EMMA Mutant Mouse Strain Submission Wizard - Step ${(sessionScope.pageCount)} of ${(sessionScope.totalStepCount)}</title>
        <style type="text/css">@import url(../css/emmastyle.css);</style>   
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.min.js"></script>
        <script type="text/javascript" src="../js/modalwindows.js"></script>
        <script type="text/javascript" src="../js/jquery.parsequery.js"></script>
        <script type="text/javascript" src="../js/jquery.parsequery.min.js"></script>
        <script type="text/javascript" src="../js/peopleData.js?<%= new java.util.Date()%>"></script>
        <script type="text/javascript" src="../js/jquery.floatobject-1.0.js"></script>
        <script type="text/javascript" src="../js/jquery.qtip-1.0.0-rc3.min.js"></script>
    </head>
    <body>
        <h2>
            Submitter (Step ${(sessionScope.pageCount)} of ${(sessionScope.totalStepCount)})
        </h2>
        <c:if test="${not empty requestScope.previousSub}">
            <c:set var="submissionDAO"  value="${requestScope.previousSub}"/>
            <c:set var="step"  value="${submissionDAO.step}"/>
            <%-- <c:if test="${not empty PeopleDAO.LabsDAO.authority}">--%>
            <%--${submissionDAO.timestamp}
            ${requestScope['javax.servlet.forward.request_uri']}
            ${submissionDAO.id_sub}--%>
            <p id="confirm"></p>
            <script type="text/javascript">
                <%--  $("#confirm").dialog({
                   msg:'Do you want to continue the submission that you started on  ${submissionDAO.timestamp} but never completed?',
                 buttons: {
                   "Yes": function() {},
                   "No": function() {}
                 }
               });
                
                ${requestScope['javax.servlet.forward.request_uri']}
                --%>
     

                
     var conf=confirm("Click OK to continue the submission that you started on  ${submissionDAO.timestamp} but never completed?");
     if(conf) {
                <%
                    String step = request.getParameter("step");
                    request.setAttribute("_target" + step, "Next");
                %>
                                $.post("${requestScope['javax.servlet.forward.request_uri']}", { _target${submissionDAO.step}: "Next", getprev: "${submissionDAO.encryptedId_sub}" } );
                                location.replace("?getprev=${submissionDAO.encryptedId_sub}&_target${submissionDAO.step}=Next");     
                <c:set var="command" value="${requestScope.previousSub}"/>
              
                    }
            </script>

            //
            <%--</c:if>--%>
            <%-- ${command.timestamp} --%>
            ////////////////////////new modal code here





            <div id="confirmStepForm" title="Recall previous submission?">
                <form method="POST" action="${requestScope['javax.servlet.forward.request_uri']}">
                    <fieldset>
                        <input type="hidden" name="getprev" id="getprev" value="${submissionDAO.encryptedId_sub}" class="text ui-widget-content ui-corner-all" />
                        <input type="hidden" name="_target${submissionDAO.step}" id="_target${submissionDAO.step}" value="Next" class="text ui-widget-content ui-corner-all" />
                    </fieldset>
                    
                    <button id="No">No</button>&nbsp;&nbsp;<button id="Yes">Yes</button>
                </form>
            </div>

















            ////////////////////////////////////end new modal code








        </c:if>
        <p>
            Please enter your contact information.
        </p>
        <c:if test="${not empty PeopleDAO}">
            <div id="loadUserDetails" name="loadUserDetails">
                <script type="text/javascript">show_modal('user_window');</script>
                <a class='activate_modal' name='user_window' href='javascript:void(0)'><img src="../images/people.png" border="0" width="32" height="32" align="absmiddle"/></a>&nbsp;&nbsp;<a class='activate_modal' name='user_window' href='javascript:void(0)'>Use these user details</a>
            </div>
            <script type="text/javascript">$("#loadUserDetails").makeFloat({x:200,y:"current",speed:"normal"});</script>  
        </c:if>
        <div id='mask' class='close_modal'></div>
        <div id='user_window' class='modal_window'>
            <c:if test="${not empty PeopleDAO}">
                The following user data is held associated with the e-mail address  ${command.submitter_email}<%--${command.peopleDAO.email}--%> :<br/>
                <c:forEach var="obj" items="${PeopleDAO}">
                    <p class="userdetailspanel">
                        <c:set var="user" value="${obj}"/>
                        <c:set var="lab" value="${obj.labsDAO}"/>
                        Name: ${user['title']}&nbsp;${user['firstname']}&nbsp;
                        ${user['surname']}<br/>
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
&postcode=${user.labsDAO['postcode']}&country=${user.labsDAO['country']}&fieldset=submitter&auth=${user.labsDAO['authority']}')" 
                               value="Yes"/><br/>
                    </p>
                </c:forEach>
            </c:if>
        </div>
        <form:form method="POST" commandName="command">


            <form:errors path="*" cssClass="errorblock"/>
            <%-- <spring:bind path="command.peopleDAO.email">--%>
            <spring:bind path="command.submitter_email">
                <div class="field">
                    <label class="label"><strong>Email<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <span name="${status.expression}" id="${status.expression}">${status.value}</span>
                    </div>
                    <form:errors path="${status.expression}" cssClass="error" />
                </div>
            </spring:bind>
            <%--<spring:bind path="command.peopleDAO.title">--%>
            <spring:bind path="command.submitter_title">
                <div class="field">
                    <label class="label" for="${status.expression}"><strong>Title</strong></label>
                    <div class="input">
                        <form:select path="${status.expression}" id="${status.expression}">
                            <form:option value="">Please select : </form:option>
                            <form:option value="Mr">Mr</form:option>
                            <form:option value="Mrs">Mrs</form:option>
                            <form:option value="Ms">Ms</form:option>
                            <form:option value="Prof">Prof</form:option>
                            <form:option value="Dr">Dr</form:option>
                        </form:select>
                    </div>

                    <form:errors path="${status.expression}" cssClass="error" />

                </div>
            </spring:bind>

            <spring:bind path="command.submitter_firstname">
                <div class="field">
                    <label class="label" for="${status.expression}"><strong>First name</strong></label>
                    <div class="input">
                        <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                        </div>
                    <form:errors path="${status.expression}" cssClass="error" />
                </div>
            </spring:bind>

            <spring:bind path="command.submitter_lastname">   
                <div class="field">
                    <label class="label" for="${status.expression}"><strong>Last Name
                            <sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                        </div>

                    <form:errors path="${status.expression}" cssClass="error" />

                </div>
            </spring:bind>

            <spring:bind path="command.submitter_tel">   
                <div class="field">
                    <label class="label" for="${status.expression}"><strong>Phone Number
                            <sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <form:input  id="${status.expression}" path="${status.expression}" alt="The phone number must begin with + 
                                     followed by the country code and contain only numbers, hyphens, spaces, or parentheses. 
                                     An extension must begin with x followed by the extension number, 
                                     e.g., +1 (234) 567-8900 x123"></form:input>
                        </div>

                    <form:errors path="${status.expression}" cssClass="error" />

                </div>
            </spring:bind>

            <spring:bind path="command.submitter_fax">   
                <div class="field">
                    <label class="label" for="${status.expression}"><strong>Fax Number
                            <sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <form:input  id="${status.expression}" path="${status.expression}" alt="The fax number must begin with + 
                                     followed by the country code and contain only numbers, hyphens, spaces, or parentheses. 
                                     An extension must begin with x followed by the extension number, 
                                     e.g., +1 (234) 567-8900 x123"></form:input></div>

                    <form:errors path="${status.expression}" cssClass="validation_error_message" />

                </div>
            </spring:bind>

            <spring:bind path="command.submitter_inst"> 
                <div class="field">
                    <label class="label" for="${status.expression}"><strong>Institution
                            <sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                        </div>

                    <form:errors path="${status.expression}" cssClass="validation_error_message" />

                </div>
            </spring:bind>

            <spring:bind path="command.submitter_dept"> 
                <div class="field">
                    <label class="label" for="${status.expression}"><strong>Department</strong></label>
                    <div class="input">
                        <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                        </div>

                    <form:errors path="${status.expression}" cssClass="validation_error_message" />

                </div>
            </spring:bind>

            <spring:bind path="command.submitter_addr_1"> 
                <div class="field">
                    <label class="label" for="${status.expression}"><strong>Address line 1/Street address
                            <sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                        </div>

                    <form:errors path="${status.expression}" cssClass="validation_error_message" />

                </div>
            </spring:bind>

            <spring:bind path="command.submitter_addr_2">
                <div class="field">
                    <label class="label" for="${status.expression}"><strong>Address line 2</strong></label>
                    <div class="input">
                        <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                        </div>

                    <form:errors path="${status.expression}" cssClass="validation_error_message" />

                </div>
            </spring:bind>

            <spring:bind path="command.submitter_city">     
                <div class="field">
                    <label class="label" for="${status.expression}"><strong>City
                            <sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                        </div>

                    <form:errors path="${status.expression}" cssClass="validation_error_message" />

                </div>
            </spring:bind>   

            <spring:bind path="command.submitter_county">  
                <div class="field">
                    <label class="label" for="${status.expression}"><strong>County/Province/State</strong></label>
                    <div class="input">
                        <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                        </div>

                    <form:errors path="${status.expression}" cssClass="validation_error_message" />

                </div>
            </spring:bind>

            <spring:bind path="command.submitter_postcode"> 
                <div class="field">
                    <label class="label" for="${status.expression}"><strong>Postcode/Zipcode
                            <sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                        </div>

                    <form:errors path="${status.expression}" cssClass="validation_error_message" />

                </div>
            </spring:bind> 

            <spring:bind path="command.submitter_country"> 
                <div class="field">
                    <label class="label" for="${status.expression}"><strong>Country
                            <sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <%--name="submitter_country" id="submitter_country">--%>
                        <form:select path="${status.expression}" id="${status.expression}">
                            <form:option value="">Please select : </form:option>
                            <form:options  items="${command.cvDAO}"></form:options>
                        </form:select>
                    </div>

                    <form:errors path="${status.expression}" cssClass="validation_error_message" />

                </div>
            </spring:bind>


            <%-- Hidden input field for submitter id to populate per_id_per --%>
            <spring:bind path="command.per_id_per_sub">
                <form:hidden path="${status.expression}" id="${status.expression}"></form:hidden>
            </spring:bind>
            <input type="hidden" name="submitter_authority" id="submitter_authority"/>
            <p>
            <table width="150" cellspacing="8" class="formNav">
                <tr>
                    <td colspan="2" align='center'><input type="submit" value="Next" name="_target3" /><br/></td>
                </tr>
                <tr>
                    <td ><input type="submit" value="Previous" name="_target1" /></td>
                    <td ><input type="submit" value="Cancel" name="_cancel" /></td>
                </tr>
            </table>
        </p>
    </div>
</form:form>
</body>
</html>
