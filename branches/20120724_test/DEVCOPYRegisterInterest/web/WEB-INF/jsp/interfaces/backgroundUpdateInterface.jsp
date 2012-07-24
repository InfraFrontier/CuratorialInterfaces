<%-- 
    Document   : backgroundUpdateInterface
    Created on : 26-Mar-2012, 12:30:24
    Author     : phil
--%>

<%@page import="java.io.IOException"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/datetime-1.0" prefix="dt" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/request-1.0" prefix="req" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<spring:bind path="command.*"></spring:bind>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EMMA Background Update Interface (curation)</title>
        <style type="text/css">@import url(../css/emmastyle.css);</style>
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
        <script type="text/javascript" src="../js/jquery.parsequery.js"></script>
        <script type="text/javascript" src="../js/jquery.parsequery.min.js"></script>
    </head>
    <body>
        <span id="loginHeader">Background update interface- Logged in as user <c:out value="${fn:toUpperCase(sessionScope.SPRING_SECURITY_LAST_USERNAME)}"/></span>
        <br /><br />

    <center>
        <c:if test="${not empty status.errorMessages}">
            <c:forEach var="error" items="${status.errorMessages}">
                <font color="red"><c:out value="${error}" escapeXml="false" /> </font>
                <br />
            </c:forEach>
        </c:if>

        <c:if test="${not empty message}">
            <font color="green"><c:out value="${message}" /></font>
            <c:set var="message" value="" scope="session" />
        </c:if>
    </center>
    <form:form>

        <table width="60%" align="center">
            <tr>
                <td colspan="2"  valign ="top" >
                    <spring:bind path="command.id_bg">
                        <div class="field">
                            <div class="input">
                                <!--TODO BACKGROUNDS FROM DATABASE PRODUCED LIST-->
                                <form:select path="${status.expression}" id="${status.expression}">
                                    
                                    <form:option value="0">Create a new background >></form:option>
                                                                        <%
                                        try {
                                            BufferedReader in = new BufferedReader(new FileReader("/nfs/panda/emma/tmp/bgNamesList.emma"));
                                            String str;

                                            while ((str = in.readLine()) != null) {
                                                int i = str.indexOf("||");
                                                String id = str.substring(0, i);
                                                str = str.replace(id + "||", "");
                                    %>
                                    <form:option value="<%= id%>"><%= str%></form:option>
                                    <%}
                                            in.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    %>
                                   
                                </form:select>&nbsp;
                            </div>
                            <form:errors path="${status.expression}" cssClass="error" />
                        </div>
                    </spring:bind> <br/><br/>  
                    <script type="text/javascript" > 
                        $('#id\\_bg').change(function() {
                            window.location='?bgid=' + $('#id_bg').val();
                        });
                    </script>
                </td>
            </tr>

            <tr>
                <td valign ="top" >        <spring:bind path="command.name">
                        <label class="label" for="${status.expression}"><strong>Background name</strong></label>
                    </td>
                    <td>            <div class="input">
                            <form:input  id="${status.expression}" path="${status.expression}" size="50"></form:input>
                        </div>
                        <form:errors path="${status.expression}" cssClass="error" />                  
                    </spring:bind><br/><br/></td>
            </tr>

            <tr>
                <td valign ="top" >        <spring:bind path="command.symbol">
                        <label class="label" for="${status.expression}"><strong>Background symbol</strong></label>

                    </td>
                    <td>            <div class="input">
                            <form:input  id="${status.expression}" path="${status.expression}" size="50"></form:input>
                        </div>
                        <form:errors path="${status.expression}" cssClass="error" />                  
                    </spring:bind>  
                    <br/><br/>
                </td>
            </tr>

            <tr>
                <td valign ="top" >        <spring:bind path="command.species">
                        <label class="label" for="${status.expression}"><strong>Species (select existing)&nbsp;</strong></label>
                        <form:select path="${status.expression}" id="${status.expression}">
                            <form:option value="">Please select >></form:option>
                            <c:forEach var="species" items="${sessionScope.species}">
                                <form:option value="${species}">${species}</form:option>
                            </c:forEach>
                        </form:select>
                            </spring:bind>
                    </td>
                    <td valign ="top" >
                        <strong> Create new?&nbsp;</strong><input  id="speciesNew"  name="speciesNew"/>
                        <spring:bind path="command.species">
                        <form:errors path="${status.expression}" cssClass="error" />  
                    </spring:bind><br/><br/></td>
            </tr>

            <tr>
                <td valign ="top" >        <div class="input">
                        <spring:bind path="command.inbred">
                            <label class="label" for="${status.expression}"><strong>Inbred</strong></label>
                    </td>
                    <td>
                        <form:select path="${status.expression}" id="${status.expression}">
                            <form:option value="">Please select >></form:option>
                            <form:option value="Y">Yes</form:option>
                            <form:option value="N">No</form:option>
                            <form:option value="">Undefined</form:option>
                        </form:select>
                        <form:errors path="${status.expression}" cssClass="error" />
                    </spring:bind> 
                    </div>            
                    <br/><br/>
                </td>
            </tr>

            <tr>
                <td valign ="top" >        <spring:bind path="command.notes">
                        <label class="label" for="${status.expression}"><strong>Notes&nbsp</strong></label>

                    </td>
                    <td>            <div class="input">
                            <form:textarea id="${status.expression}" path="${status.expression}"  cols="50" rows="5"></form:textarea>  
                        </div>
                        <form:errors path="${status.expression}" cssClass="error" />                  
                    </spring:bind>
                    <br/><br/></td>
            </tr>
            
            <tr>
                <td valign ="top" ><div class="input">
                        <spring:bind path="command.curated">
                            <label class="label" for="${status.expression}"><strong>Curated (yes to appear on curated list)</strong></label>
                    </td>
                    <td>
                        <form:select path="${status.expression}" id="${status.expression}">
                           <form:option value="N">No</form:option>
                            <form:option value="Y">Yes</form:option>
                        </form:select>
                        <form:errors path="${status.expression}" cssClass="error" />
                    </spring:bind> 
                    </div>            
                    <br/><br/>
                </td>
            </tr>
            
            <c:if test="${requestScope.preventDel == 'n'}">
                <tr>
                    <td colspan="2">
                        <input type="checkbox" name="delete" id="delete" value="y" onChange="if(confirm('Record WILL be deleted on update, are you sure?'))
alert('OK record will be deleted');
else this.checked=false;"> 
                        Check box to <font color="red"><strong>DELETE</strong></font> record on update
                        (record is not referenced from the mutations , archive or strains table)
                    </td>
                    </tr>
            </c:if>
            <tr>
                <td colspan="2" valign ="top" align="center">
                    <spring:bind path="command.username">
                        <form:hidden  id="${status.expression}" path="${status.expression}"></form:hidden>
                    </spring:bind>
                    <input name="update" value="Update" class="button" type="submit">
                    <input name="Clear updates" value="Clear updates" class="button" type="reset">
                </td>
            </tr>

        </table>
    </form:form>
</body>
</html>
