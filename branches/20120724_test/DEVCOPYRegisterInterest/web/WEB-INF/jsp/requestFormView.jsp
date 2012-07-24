<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/datetime-1.0" prefix="dt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    
    <title>EMMA interest registration and strain requests</title>
    <meta content="text/html; charset=UTF-8" http-equiv="content-type">
    <title>EMMA interest registration and strain requests</title>
    
    <meta content="Phil Wilkinson" name="author">
    <meta content="Submission form for registering an interest in strains that have recently arrived to the EMMA Centres and for requesting publicly available strains." name="description">
    <link rel="stylesheet" type="text/css" href="http://www.emmanet.org/css/forms.css">
    <script src="js/ajax.js" type="text/javascript"></script>
    <script type="text/javascript">
     /*
      * Fill the contact persons details with the scientists when chosen 'as above'.
      */
    function contactAsAbove (form) {

        var scientist = new Array ('sci_title','sci_firstname','sci_surname','sci_e_mail','sci_phone','sci_fax');
        var contact = new Array ('con_title','con_firstname','con_surname','con_e_mail','con_phone','con_fax');

        if ( form.con_as_above.checked == true ) {
	        for (var i = 0; i < scientist.length; i++) {
	            form.elements[contact[i]].value = form.elements[scientist[i]].value;
	        }
        }
    }
    </script>   
</head>
<body class="form">
<%--DEBUG START USERS IGNORE<br>
    DEBUG:: <b>${command.register_interest}</b> REGISTER INTEREST FLAG<br>
    DEBUG:: <b>${command.req_status}</b> REQUEST STATUS FLAG<br>
    DEBUG:: <b>${command.id_req}</b> REQUEST ID<br>
    DEBUG END --%>
<c:if test="${command.register_interest == '1' && command.id_req == null}" > 
    <h1>EMMA Strain Interest Registration Form</h1> 
</c:if> 
<c:if test="${command.id_req != null}" >
    <h1>EMMA Mutant Request Form</h1>. 
</c:if> 
<div style="text-align: left;">
    <table style="width: 60%; text-align: left; margin-left: auto; margin-right: auto;" border="0" cellpadding="0" cellspacing="0">
        <tbody>
            <tr>
                <td>Dear Scientist<br>
                    Please use the following form to 
                    <c:if test="${command.register_interest == '1' && command.id_req == null}" > 
                        indicate your interest in ordering EMMA strains which are currently under development and not yet available.
                    </c:if> 
                    <c:if test="${command.id_req != null}" >
                        request the mouse strains archived by EMMA.
                    </c:if> 
                    <br>
                    <br>
                    Please complete all fields presented to you (required information is marked by a <span class="asterisk">*</span>) and click <b>once</b> on the <b>Send</b> button at
                    the end of this form to submit your information to EMMA. You  
                    <c:if test="${command.register_interest == '1' && command.id_req == null}" > 
                        will receive automated notice as soon as the strain becomes available and be able to place your strain request in time.
                    </c:if> 
                    <c:if test="${command.id_req != null}" >
                        will receive a response to your request from the archiving centre, by e-mail, within one week.
                    </c:if>
                    <br>
                    <br>
                    Thank you for your interest in the European Mouse Mutant Archive.<br><br>
                </td>
            </tr>
        </tbody>
    </table>
    <hr>
</div>

<div class="contacts" id="contacts">
    <center><spring:bind path="command.*">
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
    
    <form method="post">
    <input type="hidden" name="HiddenFormName" value="request"  />
    <table>
        <div class="first">
            <tr><td class="noborder" align="right"><b>Scientist</b>'s Title</td><td class="noborder">
                    <spring:bind path="command.sci_title">
                        <select name="sci_title" >
                            <option value=""></option>
                            <c:choose>
                                <c:when test="${command.sci_title  == 'Mr'}" >
                                    <option value="Mr" selected>Mr</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Mr">Mr</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.sci_title  == 'Mrs'}" >
                                    <option value="Mrs" selected>Mrs</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Mrs">Mrs</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.sci_title  == 'Ms'}" >
                                    <option value="Ms" selected>Ms</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Ms">Ms</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.sci_title  == 'Prof'}" >
                                    <option value="Prof" selected>Prof</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Prof">Prof</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.sci_title  == 'Dr'}" >
                                    <option value="Dr" selected>Dr</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Dr">Dr</option> 
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </spring:bind>
            </td></tr>
            <tr><td class="noborder" align="right"><span class="asterisk">*</span> Firstname</td>
                <td class="noborder">
                    <spring:bind path="command.sci_firstname">
                        <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" size="50" maxlength="50" />
                    </spring:bind>
                </td>
            </tr>
            <tr><td class="noborder" align="right"><span class="asterisk">*</span> Surname</td>
                <td class="noborder">
                    <spring:bind path="command.sci_surname">
                        <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" size="50" maxlength="50" />
                    </spring:bind>
            </td></tr>
            <tr><td class="noborder" align="right"><span class="asterisk">*</span> E_mail</td>
                <td class="noborder">
                    <spring:bind path="command.sci_e_mail">
                        <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" size="50" maxlength="100" />
                    </spring:bind>
            </td></tr>
            <tr><td class="noborder" align="right"><span class="asterisk">*</span> Phone</td>
                <td class="noborder">
                    <spring:bind path="command.sci_phone">
                        <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" size="20" maxlength="20" />
                    </spring:bind>
            </td></tr>
            <tr><td class="noborder" align="right">Fax</td>
                <td class="noborder">
                    <spring:bind path="command.sci_fax">
                        <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" size="20" maxlength="20" />
                    </spring:bind>
                    <br />
                    <tr>
                        <td class="noborder">&nbsp;</td>
                        <td> <input type="checkbox" name="con_as_above" value="on" onclick="contactAsAbove(this.form)" />Shipping Contact same as Scientist</td>
                    </tr>
            </td></tr>
        </div><!-- END DIV CLASS FIRST-->
        <div class="clear"></div>
        
        <div class="second">
            <tr><td class="noborder" align="right"><b>Shipping Contact</b>'s Title</td><td class="noborder">
                    <spring:bind path="command.sci_title">
                        <select name="con_title" >
                            <option value=""></option>
                            <c:choose>
                                <c:when test="${command.con_title  == 'Mr'}" >
                                    <option value="Mr" selected>Mr</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Mr">Mr</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_title  == 'Mrs'}" >
                                    <option value="Mrs" selected>Mrs</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Mrs">Mrs</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_title  == 'Ms'}" >
                                    <option value="Ms" selected>Ms</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Ms">Ms</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_title  == 'Prof'}" >
                                    <option value="Prof" selected>Prof</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Prof">Prof</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_title  == 'Dr'}" >
                                    <option value="Dr" selected>Dr</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Dr">Dr</option> 
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </spring:bind>
            </td></tr>
            <tr><td class="noborder" align="right"><span class="asterisk">*</span> Firstname</td>
                <td class="noborder">
                    <spring:bind path="command.con_firstname">
                        <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" size="50" />
                    </spring:bind>
            </td></tr>
            <tr><td class="noborder" align="right"><span class="asterisk">*</span> Surname</td>
                <td class="noborder">
                    <spring:bind path="command.con_surname">
                        <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" />
                    </spring:bind>
            </td></tr>
            <tr><td class="noborder" align="right"><span class="asterisk">*</span> E_mail</td>
                <td class="noborder">
                    <spring:bind path="command.con_e_mail">
                        <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" size="50" />
                    </spring:bind>
            </td></tr>
            <tr><td class="noborder" align="right"><span class="asterisk">*</span> Phone</td>
                <td class="noborder">
                    <spring:bind path="command.con_phone">
                        <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" size="20" />
                    </spring:bind>
            </td></tr>
            <tr><td class="noborder" align="right">Fax</td>
                <td class="noborder">
                    <spring:bind path="command.con_fax">
                        <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" size="20" />
                    </spring:bind>
            </td></tr>
            <tr><td class="noborder" align="right"><span class="asterisk">*</span> Institution</td>
                <td class="noborder">
                    <spring:bind path="command.con_institution">
                        <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"  size="75" />
                    </spring:bind>
            </td></tr>
            <tr><td class="noborder" align="right">Department</td>
                <td class="noborder">
                    <spring:bind path="command.con_dept">
                        <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" size="75" />
                    </spring:bind>
            </td></tr>
            <tr><td class="noborder" align="right"><span class="asterisk">*</span> Address line 1</td>
                <td class="noborder">
                    <spring:bind path="command.con_addr_1">
                        <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" size="75" />
                    </spring:bind>
            </td></tr>
            <tr><td class="noborder" align="right">Address line 2</td>
                <td class="noborder">
                    <spring:bind path="command.con_addr_2">
                        <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" size="75" />
            </spring:bind></td></tr>
            <tr><td class="noborder" align="right">County/Province</td>
                <td class="noborder">
                    <spring:bind path="command.con_province">
                        <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" size="50" />
                    </spring:bind>
            </td></tr>
            <tr><td class="noborder" align="right"><span class="asterisk">*</span> Town</td>
                <td class="noborder">
                    <spring:bind path="command.con_town">
                        <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" size="50" />
                    </spring:bind>
            </td></tr>
            <tr><td class="noborder" align="right"><span class="asterisk">*</span> Postcode</td>
                <td class="noborder">
                    <spring:bind path="command.con_postcode">
                        <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" size="10" />
                    </spring:bind>
            </td></tr>
            <tr><td class="noborder" align="right"><span class="asterisk">*</span> Country</td>
                <td class="noborder">
                    <spring:bind path="command.con_country">
                        <select name="con_country">
                            <option value=""></option>
                            <option value="Australia">Australia</option>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Australia'}" >
                                    <option value="Australia" selected>Australia</option>
                                </c:when>
                                <c:otherwise> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Austria'}" >
                                    <option value="Austria" selected>Austria</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Austria">Austria</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Belgium'}" >
                                    <option value="Belgium" selected>Belgium</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Belgium">Belgium</option> 
                                </c:otherwise>
                            </c:choose>  
                            <c:choose>
                                <c:when test="${command.con_country  == 'Brazil'}" >
                                    <option value="Brazil" selected>Brazil</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Brazil">Brazil</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Canada'}" >
                                    <option value="Canada" selected>Canada</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Canada">Canada</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'China'}" >
                                    <option value="China" selected>China</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="China">China</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Croatia'}" >
                                    <option value="Croatia" selected>Croatia</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Croatia">Croatia</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Cyprus'}" >
                                    <option value="Cyprus" selected>Cyprus</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Cyprus">Cyprus</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Czech Republic'}" >
                                    <option value="Czech Republic" selected>Czech Republic</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Czech Republic">Czech Republic</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Denmark'}" >
                                    <option value="Denmark" selected>Denmark</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Denmark">Denmark</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Estonia'}" >
                                    <option value="Estonia" selected>Estonia</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Estonia">Estonia</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Finland'}" >
                                    <option value="Finland" selected>Finland</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Finland">Finland</option> 
                                </c:otherwise>
                            </c:choose> 
                            <c:choose>
                                <c:when test="${command.con_country  == 'France'}" >
                                    <option value="France" selected>France</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="France">France</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Germany'}" >
                                    <option value="Germany" selected>Germany</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Germany">Germany</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Greece'}" >
                                    <option value="Greece">Greece</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Greece">Greece</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Hungary'}" >
                                    <option value="Hungary" selected>Hungary</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Hungary">Hungary</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Ireland'}" >
                                    <option value="Ireland" selected>Ireland</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Ireland">Ireland</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Israel'}" >
                                    <option value="Israel" selected>Israel</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Israel">Israel</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Italy'}" >
                                    <option value="Italy" selected>Italy</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Italy">Italy</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Japan'}" >
                                    <option value="Japan" selected>Japan</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Japan">Japan</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Netherlands'}" >
                                    <option value="Netherlands" selected>Netherlands</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Netherlands">Netherlands</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'New Zealand'}" >
                                    <option value="New Zealand" selected>New Zealand</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="New Zealand">New Zealand</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Norway'}" >
                                    <option value="Norway" selected>Norway</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Norway">Norway</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Poland'}" >
                                    <option value="Poland" selected>Poland</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Poland">Poland</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Portugal'}" >
                                    <option value="Portugal" selected>Portugal</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Portugal">Portugal</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Quebec'}" >
                                    <option value="Quebec">Quebec</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Quebec">Quebec</option> 
                                </c:otherwise>
                            </c:choose>
                            <%--<c:choose>
                                <c:when test="${command.con_country  == 'Scotland'}" >
                                    <option value="Scotland">Scotland</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Scotland">Scotland</option> 
                                </c:otherwise>
                            </c:choose>--%>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Slovenia'}" >
                                    <option value="Slovenia" selected>Slovenia</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Slovenia">Slovenia</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'South Korea'}" >
                                    <option value="South Korea" selected>South Korea</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="South Korea">South Korea</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Spain'}" >
                                    <option value="Spain" selected>Spain</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Spain">Spain</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Sweden'}" >
                                    <option value="Sweden" selected>Sweden</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Sweden">Sweden</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Switzerland'}" >
                                    <option value="Switzerland" selected>Switzerland</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Switzerland">Switzerland</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'Taiwan'}" >
                                    <option value="Taiwan" selected>Taiwan</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="Taiwan">Taiwan</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'UK'}" >
                                    <option value="UK" selected>United Kingdom</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="UK">United Kingdom</option> 
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${command.con_country  == 'USA'}" >
                                    <option value="USA" selected>USA</option>
                                </c:when>
                                <c:otherwise> 
                                    <option value="USA">USA</option> 
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </spring:bind>
            </td></tr>
        </div> <!-- DIV CLASS SECOND END -->
        <div class="clear"></div>
    </table>
</div><!-- Contact div end -->
<br />
<b>Requested strain:</b>
<br />
<table>
    <tr><td class="noborder" align="right">EMMA ID</td>
        <td class="noborder">
            <spring:bind path="command.strain_id">
                <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" size="10" READONLY />
            </spring:bind>
        </td>
    </tr>
    <tr><td class="noborder" align="right">Strain name</td>
        <td class="noborder">
            <spring:bind path="command.strain_name">
                <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" size="100" READONLY />
            </spring:bind>
        </td>
    </tr>
    <tr><td class="noborder" align="right">Common name(s)</td>
        <td class="noborder">
            <spring:bind path="command.common_name_s">
                <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" size="100" READONLY />
            </spring:bind>
        </td>
    </tr>
</table>
<br /><span class="asterisk">*</span><b> Material you 
    <c:choose>
    <c:when test="${command.id_req != null}" >have requested:</b>
</c:when>
<c:otherwise> 
    intend to request:</b><br />
    (please check current availabilities on strain description page)
</c:otherwise>
</c:choose>
<%-- TODO WORK OUT STRAIN AVAILABILITY + PERTINENT SELECTIONS IF FINAL FORM SUBMISSION--%>
<c:if test="${command.register_interest  != null && command.req_material != null}" >
    <br /><br />${command.req_material}<br />
</c:if>
<table>
    <tr>
        <td class="noborder" align="right">Live Animals</td>
        <td class="noborder">
            <label>
                <spring:bind path="command.req_material">
                    <c:choose>
                        <c:when test="${command.req_material  == 'live animals'}" >
                            <input type="radio" name="<c:out value="${status.expression}"/>" value="live animals" class="radio" checked />
                        </c:when>
                        <c:otherwise>
                            <input type="radio" name="<c:out value="${status.expression}"/>" value="live animals" class="radio" /> 
                        </c:otherwise>
                    </c:choose>
                </spring:bind>
            </label>
        </td>
        <td class="noborder" align="right">Frozen Material</td>
        <td class="noborder">
            <label>
                <spring:bind path="command.req_material">
                    <c:choose>
                        <c:when test="${command.req_material  == 'frozen material'}" >
                            <input type="radio" name="<c:out value="${status.expression}"/>" value="frozen material" class="radio" checked />
                        </c:when>
                        <c:otherwise>
                            <input type="radio" name="<c:out value="${status.expression}"/>" value="frozen material" class="radio" /> 
                        </c:otherwise>
                    </c:choose>
                </spring:bind>
            </label>
        </td>
    </tr>
</table>
<c:if test="${command.register_interest == '1' && command.id_req == null}" >
    <p>EMMA
        does not guarantee that mice will
        be made available. The possible shipping date can not be scheduled
        while the strain is still under development.
    </p> 
</c:if>
<hr />
<p align="center">
        <c:choose>
            <c:when test="${command.register_interest == '1' && command.id_req == null}" >
                <spring:bind path="command.terms_read">
                    <input type="hidden" name="<c:out value="${status.expression}"/>" value="on"/>
                </spring:bind>
                Please read and be aware of the <a href="#" onclick="javascript:ajax('conditions.html','conditions');return false;" title="EMMA Conditions">conditions</a> and associated shipping costs. These will have to be agreed to, when you finally place an order for the mice.
            </c:when>
            <c:otherwise>
                <spring:bind path="command.terms_read">
            <input type="checkbox" onChange="javascript:ajax('js/printclear.js','conditions');return false;" name="<c:out value="${status.expression}"/>" value="on"<c:if test="${command.terms_read  != null}" >checked</c:if>/>
        </spring:bind>
        Please check this box to confirm you have read <a href="#" onclick="javascript:ajax('conditions.html','conditions');return false;" title="EMMA Conditions">the conditions</a> and agree to pay the transmittal fee of EUR 200 plus shipping cost.
    </c:otherwise>
</c:choose>
</p> 
<p align="left">
    <span align ="left" id="conditions"></span>
</p>
<p style="text-align: center;" class="left">(Click on the "<b>Send</b>"
button <b>once</b> to submit the form to EMMA.)</p>
<p style="text-align: center;" class="left">
    <input name="Send" value="Send" class="button" type="submit">
    <input name="Clear all fields" value="Clear all fields" class="button" type="reset">
</p>
<spring:bind path="command.req_status">
    <input type="hidden" name="<c:out value="${status.expression}"/>" value="TO_PR" />
</spring:bind>
<c:if test="${command.register_interest  != null}" > 
    <c:choose>
        <c:when test="${command.id_req  != null}" >
            <spring:bind path="command.register_interest">
                <input type="hidden" name="<c:out value="${status.expression}"/>" value="0" />
            </spring:bind>
        </c:when>
        <c:otherwise>
            <spring:bind path="command.register_interest">
                <input type="hidden" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" />
            </spring:bind>
        </c:otherwise>
    </c:choose>
</c:if>
<spring:bind path="command.timestamp">
    <input type="hidden" name="<c:out value="${status.expression}"/>" value="<dt:format pattern="yyyyMMddHHmmss"><dt:currentTime/></dt:format>"/>
</spring:bind>
<spring:bind path="command.ftimestamp">
    <input type="hidden" name="<c:out value="${status.expression}"/>" value="<dt:format pattern="yyyy-MM-dd HH:mm:s"><dt:currentTime/></dt:format>"/>
</spring:bind>
<spring:bind path="command.str_id_str">
    <input type="hidden" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" />
</spring:bind>
</form>
</body>
</html>
