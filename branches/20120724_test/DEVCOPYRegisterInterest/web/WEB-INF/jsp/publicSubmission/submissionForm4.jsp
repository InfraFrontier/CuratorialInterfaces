<%-- 
    Document   : submissionForm4
    Created on : 30-Jan-2012, 14:47:43
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
        <title>EMMA Mutant Mouse Strain Submission Wizard - Step ${(sessionScope.pageCount)} of ${(sessionScope.totalStepCount)}</title>
        <style type="text/css">@import url(../css/emmastyle.css);</style>
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
        <script type="text/javascript" src="../js/modalwindows.js"></script>
        <script type="text/javascript" src="../js/jquery.parsequery.js"></script>
        <script type="text/javascript" src="../js/jquery.parsequery.min.js"></script>
        <script type="text/javascript" src="../js/peopleData.js"></script>
    </head>
    <body>

        <form:form method="POST" commandName="command">
            <form:errors path="*" cssClass="errorblock"/>
            <div id="shipper" class="step">
                <h2> Shipper (Step ${(sessionScope.pageCount)} of ${(sessionScope.totalStepCount)})</h2>
                <p> Please enter the contact information of the person in charge of shipping the mice to EMMA (e.g., animal facility manager or lab head). </p>
                <div id='mask' class='close_modal'></div>
                <div id='user_window' class='modal_window'>
                    <%-- DIV HOLDS USER DATA RETURNED FROM ajaxReturn.emma?funct=peopleCall&email=xxxxxxx@xxx.xxx --%>
                </div><div>
                    <div>
                         <input type="button" name="fill_shipper_with_submitter_data" 
                               id="fill_producer_with_submitter_data" value="Fill with Submitter data" onClick="populateUserDetails('?uid=${command.per_id_per_sub}\
&email=${command.submitter_email}&title=${command.submitter_title}&firstname=${command.submitter_firstname}&surname=${command.submitter_lastname}&phone=${command.submitter_tel}\
&fax=${command.submitter_fax}&institute=${command.submitter_inst}&dept=${command.submitter_dept}\
&addr_line_1=${command.submitter_addr_1}&addr_line_2=${command.submitter_addr_2}\
&town=${command.submitter_city}&county=${command.submitter_county}\
&postcode=${command.submitter_postcode}&country=${command.submitter_country}&fieldset=shipper')"/>
                        <input id="fill_shipper_with_producer_data" class="ui-wizard-content" type="button" value="Fill with Producer data" name="fill_shipper_with_producer_data" onClick="populateUserDetails('?uid=${command.per_id_per}\
&email=${command.producer_email}&title=${command.producer_title}&firstname=${command.producer_firstname}&surname=${command.producer_lastname}&phone=${command.producer_tel}\
&fax=${command.producer_fax}&institute=${command.producer_inst}&dept=${command.producer_dept}\
&addr_line_1=${command.producer_addr_1}&addr_line_2=${command.producer_addr_2}\
&town=${command.producer_city}&county=${command.producer_county}\
&postcode=${command.producer_postcode}&country=${command.producer_country}&fieldset=shipper')"/>
                    </div>
                    <div class="validation_error_message"> &nbsp; </div>
                </div>
                <spring:bind path="command.shipper_email">
                    <div class="field">
                        <label class="label"><strong>Email<sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                                <div id="user_details_link" style="display: none"><p><a class='activate_modal' name='user_window' href='javascript:void(0)'><img src="../images/people.png" border="0" width="32" height="32" align="absmiddle"/></a>&nbsp;&nbsp;<a class='activate_modal' name='user_window' href='javascript:void(0)'>Use these user details</a></p></div></div>
                            <form:errors path="${status.expression}" cssClass="error" />
                    </div>
                    <ul id="ulUsers"></ul>
                </spring:bind>

                <script type="text/javascript" >              
                    $('#shipper_email').focusout(function() {
                        $("#user_details_link").hide();
                        $('#user_window').load('../ajaxReturn.emma',{email:$('#shipper_email').val(), funct: "peopleCall",fieldset: "shipper"});
                        $("#user_details_link").show();
                    });
                </script>

                <spring:bind path="command.shipper_title">
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

                <spring:bind path="command.shipper_firstname">
                    <div class="field">
                        <label class="label" for="${status.expression}"><strong>First name</strong></label>
                        <div class="input">
                            <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                            </div>

                        <form:errors path="${status.expression}" cssClass="error" />

                    </div>
                </spring:bind>

                <spring:bind path="command.shipper_lastname">   
                    <div class="field">
                        <label class="label" for="${status.expression}"><strong>Last Name
                                <sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                            </div>
                        <form:errors path="${status.expression}" cssClass="error" />
                    </div>
                </spring:bind>

                <spring:bind path="command.shipper_tel">   
                    <div class="field">
                        <label class="label" for="${status.expression}"><strong>Phone Number
                                <sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <form:input  id="${status.expression}" path="${status.expression}" alt="The phone number must
                                         begin with + followed by the country code and contain only numbers, hyphens, spaces, or 
                                         parentheses. An extension must begin with x followed by the extension number, 
                                         e.g., +1 (234) 567-8900 x123"></form:input>
                            </div>

                        <form:errors path="${status.expression}" cssClass="error" />

                    </div>
                </spring:bind>

                <spring:bind path="command.shipper_fax">   
                    <div class="field">
                        <label class="label" for="${status.expression}"><strong>Fax Number
                                <sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <form:input  id="${status.expression}" path="${status.expression}" alt="The fax number must 
                                         begin with + followed by the country code and contain only numbers, hyphens, spaces, or 
                                         parentheses. An extension must begin with x followed by the extension number, 
                                         e.g., +1 (234) 567-8900 x123"></form:input></div>

                        <form:errors path="${status.expression}" cssClass="validation_error_message" />

                    </div>
                </spring:bind>

                <spring:bind path="command.shipper_inst"> 
                    <div class="field">
                        <label class="label" for="${status.expression}"><strong>Institution
                                <sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                            </div>

                        <form:errors path="${status.expression}" cssClass="validation_error_message" />

                    </div>
                </spring:bind>

                <spring:bind path="command.shipper_dept"> 
                    <div class="field">
                        <label class="label" for="${status.expression}"><strong>Department</strong></label>
                        <div class="input">
                            <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                            </div>

                        <form:errors path="${status.expression}" cssClass="validation_error_message" />

                    </div>
                </spring:bind>

                <spring:bind path="command.shipper_addr_1"> 
                    <div class="field">
                        <label class="label" for="${status.expression}"><strong>Address line 1/Street address
                                <sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                            </div>

                        <form:errors path="${status.expression}" cssClass="validation_error_message" />

                    </div>
                </spring:bind>



                <spring:bind path="command.shipper_addr_2">
                    <div class="field">
                        <label class="label" for="${status.expression}"><strong>Address line 2</strong></label>
                        <div class="input">
                            <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                            </div>

                        <form:errors path="${status.expression}" cssClass="validation_error_message" />

                    </div>
                </spring:bind>


                <spring:bind path="command.shipper_city">     
                    <div class="field">
                        <label class="label" for="${status.expression}"><strong>City
                                <sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                            </div>

                        <form:errors path="${status.expression}" cssClass="validation_error_message" />

                    </div>
                </spring:bind>  




                <spring:bind path="command.shipper_county">  
                    <div class="field">
                        <label class="label" for="${status.expression}"><strong>County/Province/State</strong></label>
                        <div class="input">
                            <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                            </div>

                        <form:errors path="${status.expression}" cssClass="validation_error_message" />

                    </div>
                </spring:bind>


                <spring:bind path="command.shipper_postcode"> 
                    <div class="field">
                        <label class="label" for="${status.expression}"><strong>Postcode/Zipcode
                                <sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <form:input  id="${status.expression}" path="${status.expression}"></form:input>
                            </div>

                        <form:errors path="${status.expression}" cssClass="validation_error_message" />

                    </div>
                </spring:bind> 

                <spring:bind path="command.shipper_country"> 
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
            </div>

            <div class="validation_error_message"> &nbsp; </div>
        </div>
    </div>
</div>
<spring:bind path="command.per_id_per_contact">
    <form:hidden path="${status.expression}" id="${status.expression}"></form:hidden>
</spring:bind>
<p>
        <table width="150" cellspacing="8" class="formNav">
            <tr>
                <td colspan="2" align='center'><input type="submit" value="Next" name="_target5" /><br/></td>
            </tr>
            <tr>
                <td ><input type="submit" value="Previous" name="_target3" /></td>
                <td ><input type="submit" value="Cancel" name="_cancel" /></td>
            </tr>
        </table>
    </p>

</form:form>

</body>
</html>
