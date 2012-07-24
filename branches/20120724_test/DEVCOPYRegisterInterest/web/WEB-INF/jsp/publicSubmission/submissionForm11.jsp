<%-- 
    Document   : submissionForm11
    Created on : 15-Mar-2012, 16:28:57
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
    </head>
    <body>
        <div id="miscellanea" class="step">
            <h2>
                Additional information (Step ${(sessionScope.pageCount)} of ${(sessionScope.totalStepCount)})
            </h2>
            <p>

            </p>
            <form:form method="POST" commandName="command">

                <spring:bind path="command.past_requests">
                    <div class="field">
                        <label class="label" for="${status.expression}"><strong>How many requests for this strain have you received in the last 6 months?<sup><font color="red">*</font></sup></strong></label>

                        <div class="input">

                            <form:select path="${status.expression}" id="${status.expression}">
                                <c:forEach var="it" begin="0" end="11" varStatus="status">
                                    <c:choose>
                                        <c:when test="${it!=11}"><form:option value="${it}">${it}</form:option></c:when>
                                        <c:otherwise>
                                            <form:option value="${it}">More than 10</form:option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach> 
                            </form:select>
                        </div>
                        <form:errors path="${status.expression}" cssClass="error" />
                    </div>
                </spring:bind>                    

                <spring:bind path="command.deposited_elsewhere">
                    <div class="field">
                        <label class="label" for="${status.expression}"><strong>Is this strain being deposited with any other institution or company intending to make it available for distribution?<sup><font color="red">*</font></sup></strong></label>

                        <div class="input">
                            <label><form:radiobutton id="${status.expression}-yes" path="${status.expression}" value="yes" />Yes (please explain below)</label><br />
                            <label><form:radiobutton id="${status.expression}-no" path="${status.expression}" value="no" />No</label><br />
                            <label><form:radiobutton id="${status.expression}-not_known" path="${status.expression}" value="not known" />Not known</label><br />
                        </spring:bind>

                        <spring:bind path="command.deposited_elsewhere_text">
                            <form:textarea id="${status.expression}" path="${status.expression}" cols="50" rows="5"></form:textarea>
                        </div>
                        <form:errors path="${status.expression}" cssClass="error" />
                    </div>
                </spring:bind>

                <spring:bind path="command.similar_strains">
                    <div class="field">
                        <label class="label" for="${status.expression}"><strong>Are other laboratories producing similar strains?<sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <label><form:radiobutton id="${status.expression}-yes" path="${status.expression}" value="yes" />Yes</label><br />
                            <label><form:radiobutton id="${status.expression}-no" path="${status.expression}" value="no" />No</label><br />
                            <label><form:radiobutton id="${status.expression}-not_known" path="${status.expression}" value="not known" />Not known</label><br />
                        </div>
                        <form:errors path="${status.expression}" cssClass="error" />
                    </div>
                </spring:bind>

                <spring:bind path="command.ip_rights">
                    <div class="field">
                        <label class="label" for="${status.expression}"><strong>Are there any intellectual property rights or patented technologies linked to this strain?<sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <label><form:radiobutton id="${status.expression}-yes" path="${status.expression}" value="yes" />Yes (please explain below)</label><br />
                            <label><form:radiobutton id="${status.expression}-no" path="${status.expression}" value="no" />No</label><br />
                            <label><form:radiobutton id="${status.expression}-not_known" path="${status.expression}" value="not known" />Not known</label><br />
                            <form:errors path="${status.expression}" cssClass="error" />
                        </spring:bind>
                        <spring:bind path="command.ip_rights_text">
                            <form:textarea id="${status.expression}" path="${status.expression}" cols="50" rows="5"></form:textarea>
                        </div>
                        <form:errors path="${status.expression}" cssClass="error" />
                    </div>
                </spring:bind>

                <spring:bind path="command.exclusive_owner">
                    <div class="field">
                        <label class="label" for="${status.expression}"><strong>Is the producer the exclusive owner of this strain<sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <label><form:radiobutton id="${status.expression}-yes" path="${status.expression}" value="yes" />Yes</label><br />
                            <label><form:radiobutton id="${status.expression}-no" path="${status.expression}" value="no" />No (please list names of additional owners with affiliation and e-mail address below)</label><br />
                            <label><form:radiobutton id="${status.expression}-not_known" path="${status.expression}" value="not known" />Not known</label><br />
                            <form:errors path="${status.expression}" cssClass="error" />
                        </spring:bind>
                        <spring:bind path="command.exclusive_owner_text">
                            <form:textarea id="${status.expression}" path="${status.expression}" cols="50" rows="5"></form:textarea>
                        </div>
                        <form:errors path="${status.expression}" cssClass="error" />
                    </div>
                </spring:bind>

                <spring:bind path="command.owner_permission">
                    <div class="field">
                        <label class="label" for="${status.expression}"><strong>Do you have permission from all owners to deposit this strain in the EMMA repository?<sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <label><form:radiobutton id="${status.expression}-yes" path="${status.expression}" value="yes" />Yes</label><br />
                            <label><form:radiobutton id="${status.expression}-no" path="${status.expression}" value="no" />No (please explain below)</label><br />
                            <label><form:radiobutton id="${status.expression}-not_known" path="${status.expression}" value="not known" />Not known</label><br />
                            <form:errors path="${status.expression}" cssClass="error" />
                        </spring:bind>
                        <spring:bind path="command.owner_permission_text">  
                            <form:textarea id="${status.expression}" path="${status.expression}" cols="50" rows="5"></form:textarea>
                        </div>
                        <form:errors path="${status.expression}" cssClass="error" />
                    </div>
                </spring:bind>

                <spring:bind path="command.delayed_release">
                    <div class="field">
                        <label class="label" for="${status.expression}"><strong>Do you require <a target="PDF" href="/delayed_release.php">delayed release</a> for this strain?<sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <label><form:radiobutton id="${status.expression}-yes" path="${status.expression}" value="yes" />Yes (please explain below)</label><br />
                            <label><form:radiobutton id="${status.expression}-no" path="${status.expression}" value="no" />No</label><br />
                            <label><form:radiobutton id="${status.expression}-not_known" path="${status.expression}" value="not known" />Not known</label><br />
                        </spring:bind>
                        <spring:bind path="command.delayed_release_text">
                            <form:textarea id="${status.expression}" path="${status.expression}" cols="50" rows="5"></form:textarea>
                            </div>
                        <form:errors path="${status.expression}" cssClass="error" />
                    </div>
                </spring:bind>


                <div class="field">
                    <label class="label"><strong>How many mice of breeding age could you provide and when?</strong><br />
                        <font color="gray" size="2">Mice of breeding age must be provided. Minimum of 5 females and 5 males for freezing as homozygotes. Minimum of 5 males for freezing as heterozygotes.<br />
                        Provision of more mice than the specified minimum will considerably accelerate the freezing process.</font></label>
                    <div class="input">
                        <table>
                            <tr>
                                <td>
                                    <spring:bind path="command.mice_avail_month">
                                        <form:select path="${status.expression}" id="${status.expression}">
                                            <form:option value="">Please select..</form:option>
                                            <c:forEach var="it" begin="1" end="12" varStatus="status">
                                                <form:option value="${it}">${it}</form:option>
                                            </c:forEach> 
                                        </form:select>
                                    </spring:bind>
                                </td>

                                <td>

                                    <spring:bind path="command.mice_avail_year">
                                        <form:select path="${status.expression}" id="${status.expression}">
                                            <form:option value="">Please select..</form:option>
                                            <c:forEach var="it" begin="0" end="5" varStatus="status">
                                                <form:option value="${(sessionScope.startYear)+it}">${(sessionScope.startYear)+it}</form:option>
                                            </c:forEach> 
                                        </form:select>
                                    </spring:bind>
                                </td>
                                <td>

                                    <spring:bind path="command.mice_avail_males">
                                        <form:select path="${status.expression}" id="${status.expression}">
                                            <form:option value="">Please select..</form:option>
                                            <c:forEach var="it" begin="0" end="7" varStatus="status">
                                                <c:choose>
                                                    <c:when test="${it!=7}"><form:option value="${it}">${it}</form:option></c:when>
                                                    <c:otherwise>
                                                        <form:option value="${it}">More than 6</form:option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach> 
                                        </form:select>
                                    </spring:bind>

                                </td>
                                <td>
                                    <spring:bind path="command.mice_avail_females">
                                        <form:select path="${status.expression}" id="${status.expression}">
                                            <form:option value="">Please select..</form:option>
                                            <c:forEach var="it" begin="0" end="11" varStatus="status">
                                                <c:choose>
                                                    <c:when test="${it!=11}"><form:option value="${it}">${it}</form:option></c:when>
                                                    <c:otherwise>
                                                        <form:option value="${it}">More than 10</form:option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach> 
                                        </form:select>
                                    </spring:bind>
                                </td>
                            </tr>
                            <tr>

                                <td>
                                    <label for="mice_avail_month">Month</label>
                                </td>
                                <td>
                                    <label for="mice_avail_year">Year</label>
                                </td>
                                <td>
                                    <label for="mice_avail_males">Males</label>

                                </td>
                                <td>
                                    <label for="mice_avail_females">Females</label>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="validation_error_message">

                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label"><strong>Additional materials of interest (you can upload up to five attachments)</strong></label>
                    <div class="input">
                        <spring:bind path="command.additional_materials_file_1"><input type="file" id="${status.expression}" path="${status.expression}" enctype="multipart/form-data"/><form:errors path="${status.expression}" cssClass="error" /></spring:bind><br/>
<spring:bind path="command.additional_materials_file_2"><input type="file" id="${status.expression}" path="${status.expression}" enctype="multipart/form-data"/><form:errors path="${status.expression}" cssClass="error" /></spring:bind><br/>
<spring:bind path="command.additional_materials_file_3"><input type="file" id="${status.expression}" path="${status.expression}" enctype="multipart/form-data"/><form:errors path="${status.expression}" cssClass="error" /></spring:bind><br/>
<spring:bind path="command.additional_materials_file_4"><input type="file" id="${status.expression}" path="${status.expression}" enctype="multipart/form-data"/><form:errors path="${status.expression}" cssClass="error" /></spring:bind><br/>
<spring:bind path="command.additional_materials_file_5"><input type="file" id="${status.expression}" path="${status.expression}" enctype="multipart/form-data"/><form:errors path="${status.expression}" cssClass="error" /></spring:bind><br/>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
<p>
        <table width="150" cellspacing="8" class="formNav">
            <tr>
                <td colspan="2" align='center'><input type="submit" value="Submit" name="_finish" /><br/></td>
            </tr>
            <tr>
                <td ><input type="submit" value="Previous" name="_target10" /></td>
                <td ><input type="submit" value="Cancel" name="_cancel" /></td>
            </tr>
        </table>
    </p>

                <br/><br/>
                
            </form:form>
        </div>

    </body>
</html>