

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:set var="keyRef" value='${returnedOut}'></c:set>
<%

            java.io.BufferedReader inSources = new java.io.BufferedReader(new java.io.FileReader("/nfs/panda/emma/tmp/cilist"));
            String strInSources;
            String strOutSources = "";

            while ((strInSources = inSources.readLine()) != null) {
                strOutSources = strOutSources + strInSources;
            }
%>

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
        
        <script type="text/javascript" src="../js/jquery.js"></script>
        
        
        <script>
            $(document).ready(function(){
                // $("#mutantSubType").hide();
                //alert();
                $("#mutantType").change( function() {
                    // alert("changed");
                    // $("#mutantSubType").hide();
                    // $("#mutantMessage").html('Retrieving ...');
                    $.ajax({
                        type: "POST",
                        data: "data=" + $(this).val(),
                        url: "../ajaxReturn.emma",
                        success: function(msg){
                            if (msg != ''){
                                // alert(msg);
                                $("#mutantSubType").hide();
                                $("#mutantSubType").html(msg).show();
                            }
                            
                            else{
                                // alert("Message is " + msg);
                                $("#mutantSubType").hide();
                                $("#mutantMessage").html('No associated mutant sub-type');
                            }
                        }
                    })
                });
            });

      
            function optionVal(){
                if ($('#projects').val() == '3' || $('#reqProjects').val() == '3'){
                    alert("Selecting a EUCOMM project ID to find EUCOMM requests or strains is better served by searching on a research tool value of EUC (Strains generated from EUCOMM ES cell resource)");
                }
            }
        </script>
        
        <title>EMMA Internal Interfaces</title>
    </head>
    <body>
        <c:set var="UserName" value='${keyRef["UserName"]}'></c:set>
        
        <span id="loginHeader">Logged in as user <c:out value="${fn:toUpperCase(UserName)}" /></span>
        <br /><br />
        <%-- include consistent navigation --%>
        <%@ include file="navigation_inc.html" %>
        <h4 class="footer"><a href="javascript: void(0)" class="footer" onclick="javascript:showlayer('strSearch');return false;">+-</a> Strains Search</h4>
        <form name="strainsSearch" action="strainsInterface.emma">
            <table width="98%" border="0"
                   cellpadding="3" cellspacing="0" align="center" id="strSearch">
                <tbody>
                    <tr>
                        
                        <td valign="top">EMMA ID text search:</td>
                        <td valign="top"><input type="text" name="emmaIdText" value="" size="20" /></td>
                        <td align="top">&nbsp;<%--EMMA ID--%></td>
                        <td valign="top">
                            &nbsp;<%--<select name="emmaID"  id="emmaID" class="ifSelect">
                            <option selected value=''>Please select</option>
                            <c:forEach var="emma_id" items='${keyRef["strains"]}' varStatus="status">
                                <option value="${emma_id[2]}">${emma_id[2]}</option>
                            </c:forEach>
                            </select>--%>
                        </td>
                    </tr>
                    <tr>
                        <td>International strain name:</td>
                        <td  valign="top">
                            <select name="strainName" id="strainName" class="ifSelect">
                                <option selected value=''>Please select</option>
                                <c:forEach var="intStrainName" items='${keyRef["strains"]}' varStatus="status">
                                    <option value="${fn:escapeXml(intStrainName[1])}">${fn:escapeXml(intStrainName[1])}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td valign="top">Strain name text search:</td>
                        <td valign="top"><input type="text" name="strainNameText" value="" size="20" /></td>
                    </tr>
                    <tr>
                        <td valign="top">Common strain name:</td>
                        <td valign="top"><select name="commonStrainName" id="commonStrainName" value="" class="ifSelect" >
                            <option selected value=''>Please select</option>
                            <c:forEach var="common_name" items='${keyRef["commonNames"]}' varStatus="status">
                                <option value="${fn:escapeXml(common_name[1])}">${fn:escapeXml(common_name[1])}</option>
                            </c:forEach>
                        </select>
                        <td valign="top">Common name text search:</td>
                        <td valign="top"> <input type="text" name="commonStrainText" value="" size="20" /></td>
                    </tr>
                    
                    <tr>
                        <td  valign="top">Repository:</td>
                        <td valign="top">
                            <select name="archCentres"  id="archCentres" class="ifSelect">
                                <option selected value=''>Please select</option>
                                <c:forEach var="labs" items='${keyRef["labs"]}'>
                                    <option value="${labs.id_labo}">${labs.name} (${labs.code})</option>
                                </c:forEach>
                            </select>
                            <br/>
                        </td>
                        <td valign="top">Strain access:</td>
                        <td valign="top">
                            <select name="strainAccess"  id="strainAccess" class="ifSelect">
                                <option selected value=''>Please select</option>
                                <c:forEach var="strainAccess" items='${keyRef["access"]}'>
                                    <c:choose>
                                        <c:when  test="${strainAccess eq 'P'}" >
                                            <option value="${strainAccess}">(${strainAccess}) Unrestricted access</option>
                                        </c:when>
                                        <c:when  test="${strainAccess eq 'C'}" >
                                            <option value="${strainAccess}">(${strainAccess}) Confidential (2 years grace period)</option>
                                        </c:when>
                                        <c:when  test="${strainAccess eq 'R'}" >
                                            <option value="${strainAccess}">(${strainAccess}) Retracted</option>
                                        </c:when>
                                        <c:when  test="${strainAccess eq 'N'}" >
                                            <option value="${strainAccess}">(${strainAccess}) Not for distribution</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${strainAccess}">(${strainAccess})</option>
                                        </c:otherwise>
                                    </c:choose> 
                                </c:forEach>
                        </select><br/></td>
                    </tr>
                    <tr>
                    <td valign="top">Strain status:</td>
                    <td valign="top">
                        <select name="status"  id="status" multiple="multiple" size="3" class="ifSelect">
                            <option selected value=''>Please select</option>
                            <c:forEach var="status" items='${keyRef["status"]}'>
                                <option value="${status}">${status}</option>
                            </c:forEach> 
                        </select>
                        <br /><br />
                    </td>
                    <td valign="top">Available to order whilst archiving:</td>
                    <td valign="top">
                        <select name="orderAvail">
                            <option value="">Please select</option>
                            <option value="yes">Yes</option>
                            <option value="no">No</option>
                        </select>
                    </td>
                    <tr>
                        <td valign="top">Research tools:</td>
                        <td valign="top">
                            <select name="rTools"  id="rTools" class="ifSelect">
                                <option selected value=''>Please select</option>
                                <c:forEach var="rtools" items='${keyRef["rTools"]}'>
                                    <c:choose>
                                        <c:when  test="${empty rtools['code']}" >
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when  test="${rtools.id == 6}" >
                                                    <%-- DO NOTHING --%>
                                                </c:when>
                                                <c:when  test="${rtools.id == 7}" >
                                                    <%-- DO NOTHING --%>
                                                </c:when>
                                                <%-- <c:when  test="${rtools.id == 9}" >--%>
                                                    <%-- DO NOTHING 
                                                </c:when>--%>
                                                <c:otherwise>
                                                    <%--  || rtools['id'] !=9 --%>
                                                    <c:if test="${rtools['id'] != 6 || rtools['id'] !=7}">
                                                        <option value="${rtools["id"]}">${rtools["code"]} (${rtools["description"]})</option>
                                                    </c:if>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                            <br />
                        </td>
                        <td>Projects</td>
                        <td><select name="projects"  id="projects" class="ifSelect" onchange="optionVal();">
                                <option selected value=''>Please select</option>
                                <c:forEach var="projects" items='${keyRef["projects"]}'>
                                    <c:choose>
                                        <c:when  test="${empty projects}" >
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${projects[0]}">${projects[1]} (${projects[2]})</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                            <br />
                        </td>
                    </tr>
                    <tr>
                        <td>Internal Code:</td>
                        <td>
                            <select name="codeInternal" id="codeInternal" class="ifSelect">
                                <%= strOutSources%>
                            </select>
                        </td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="4">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="4" class="sectHead"><a href="javascript: void(0)" onclick="javascript:showlayer('typeFilters');">+-</a>  <b>Additional Search Filters</b>
                        </td>
                    </tr>
                    
                    <tr>
                        <td colspan="4">
                            <table width="100%" border="0"
                                   cellpadding="3" cellspacing="0" align="center" id="typeFilters" style="display: none;">
                                
                                <tr>
                                    <td valign="top">Affected gene:</td>
                                    <td valign="top">
                                        <select name="affectedGene" id="affectedGene" value="" class="ifSelect" >
                                            <option selected value=''>Please select</option>
                                            <c:forEach var="gene" items='${keyRef["genes"]}' varStatus="status">
                                                <option value="${gene}">${gene}</option><%-- .id_str--%>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td valign="top">Affected gene text search:</td>
                                    <td valign="top"><input type="text" name="affectedGeneText" value="" size="20" /></td>
                                </tr>
                                <tr>
                                    <td valign="top">Phenotype description:</td>
                                    <td valign="top"><input type="text" name="phenoDesc" value="" size="20" /></td>
                                    <td valign="top">Funding source:</td>
                                    <td valign="top">            <select name="funding"  id="funding" multiple="multiple" size="3" class="ifSelect">
                                            <option selected value=''>Please select</option>
                                            <c:forEach var="funding" items='${keyRef["fundingStrains"]}'>
                                                <option value="${funding[0]}">( ${funding[1]} ) ${funding[2]}</option>
                                            </c:forEach>
                                        </select>
                                    <br /></td>
                                </tr>
                                <tr>
                                    <td valign="top">Mutation type:</td>
                                    <td valign="top"><select name="mutantType"  id="mutantType" class="ifSelect">
                                            <option selected value=''>Please select</option>
                                            <c:forEach var="muts" items='${keyRef["muts"]}'>
                                                <c:choose>
                                                    <c:when  test="${empty muts}" >
                                                        
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${muts}">${muts}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <br />
                                    </td>
                                    <td valign="top">Mutation subtype:</td>
                                    <td valign="top">
                                        
                                        <select name="mutantSubType"  id="mutantSubType" class="ifSelect">
                                            <option selected value=''>Please select</option>
                                            <c:forEach var="mutsSub" items='${keyRef["mutsSub"]}'>
                                                <c:choose>
                                                    <c:when  test="${empty mutsSub}" >  
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${mutsSub}">${mutsSub}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <br />
                                    </td>
                                </tr>
                                <tr>
                                    <td valign="top">Scientists Country:</td>
                                    <td valign="top">            <select name="conCountry"  id="conCountry" class="ifSelect">
                                            <option selected value=''>Please select</option>
                                            <c:forEach var="conCountries" items='${keyRef["conCountries"]}'>
                                                <c:choose>
                                                    <c:when  test="${empty conCountries}" >
                                                        
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${conCountries}">${conCountries}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    <br /></td>
                                    <td valign="top">Scientists surname:</td>
                                    <td valign="top"><input type="text" name="sciSurname" value="" size="20" /></td>
                                </tr>
                                <tr>
                                    <td valign="top">Contact surname:</td>
                                    <td valign="top"><input type="text" name="conSurname" value="" size="20" /></td>
                                    <td valign="top">MGI Strain ID:</td>
                                    <td valign="top"><input type="text" name="mgiId" value="" size="10" /></td>
                                </tr>
                                
                            </table>
                        <br/></td>
                    </tr>
                    
                    <tr>
                        <td colspan="4" class="sectHead"><a href="javascript: void(0)" onclick="javascript:showlayer('dateFilters');">+-</a>  <b>Date Search Filters</b>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4">
                            <table width="100%" border="0"
                                   cellpadding="3" cellspacing="0" align="center" id="dateFilters" style="display: none;">
                                <tr>
                                    <td valign="top">Submitted:</td>
                                    <td valign="top">After: <input type="text" id="subDateAfter" name="subDateAfter" size="10"/>
                                        <img src="../images/cal.gif"  id="subDateAfterTrigger" border="0">
                                        <script type="text/javascript">

                                            Calendar.setup(
                                            {
                                                inputField  : "subDateAfter",         // ID of the input field
                                                ifFormat    : "%Y-%m-%d",    // the date format
                                                button      : "subDateAfterTrigger"       // ID of the button
                                            }
                                        );
                                        </script>
                                    </td>
                                    
                                    <td colspan="2" valign="top">Before: <input type="text" id="subDateBefore" name="subDatebefore" size="10"/>
                                        <img src="../images/cal.gif"  id="subDateBeforeTrigger" border="0">
                                        <script type="text/javascript">

                                            Calendar.setup(
                                            {
                                                inputField  : "subDateBefore",         // ID of the input field
                                                ifFormat    : "%Y-%m-%d",    // the date format
                                                button      : "subDateBeforeTrigger"       // ID of the button
                                            }
                                        );
                                    </script></td>
                                    
                                </tr>
                                <tr>
                                    <td valign="top">Evaluated:</td>
                                    <td valign="top">After: <input type="text" id="evalDateAfter" name="evalDateAfter" size="10"/>
                                        <img src="../images/cal.gif"  id="evalDateAfterTrigger" border="0">
                                        <script type="text/javascript">

                                            Calendar.setup(
                                            {
                                                inputField  : "evalDateAfter",         // ID of the input field
                                                ifFormat    : "%Y-%m-%d",    // the date format
                                                button      : "evalDateAfterTrigger"       // ID of the button
                                            }
                                        );
                                        </script>
                                    </td>
                                    <td colspan="2" valign="top">Before: <input type="text" id="evalDateBefore" name="evalDatebefore" size="10"/>
                                        <img src="../images/cal.gif"  id="evalDateBeforeTrigger" border="0">
                                        <script type="text/javascript">

                                            Calendar.setup(
                                            {
                                                inputField  : "evalDateBefore",         // ID of the input field
                                                ifFormat    : "%Y-%m-%d",    // the date format
                                                button      : "evalDateBeforeTrigger"       // ID of the button
                                            }
                                        );
                                    </script></td>
                                </tr>
                                <tr>
                                    <td valign="top">Mice arrived:</td>
                                    <td valign="top">After: <input type="text" id="recDateAfter" name="recDateAfter" size="10"/>
                                        <img src="../images/cal.gif"  id="recDateAfterTrigger" border="0">
                                        <script type="text/javascript">

                                            Calendar.setup(
                                            {
                                                inputField  : "recDateAfter",         // ID of the input field
                                                ifFormat    : "%Y-%m-%d",    // the date format
                                                button      : "recDateAfterTrigger"       // ID of the button
                                            }
                                        );
                                        </script>
                                    </td>
                                    <td colspan="2" valign="top">Before: <input type="text" id="recDateBefore" name="recDatebefore" size="10"/>
                                        <img src="../images/cal.gif"  id="recDateBeforeTrigger" border="0">
                                        <script type="text/javascript">

                                            Calendar.setup(
                                            {
                                                inputField  : "recDateBefore",         // ID of the input field
                                                ifFormat    : "%Y-%m-%d",    // the date format
                                                button      : "recDateBeforeTrigger"       // ID of the button
                                            }
                                        );
                                    </script></td>
                                    
                                </tr>
                                <tr>
                                    <td valign="top">Archiving started:</td>
                                    <td valign="top">After: <input type="text" id="archstartDateAfter" name="archstartDateAfter" size="10"/>
                                        <img src="../images/cal.gif"  id="archstartDateAfterTrigger" border="0">
                                        <script type="text/javascript">

                                            Calendar.setup(
                                            {
                                                inputField  : "archstartDateAfter",         // ID of the input field
                                                ifFormat    : "%Y-%m-%d",    // the date format
                                                button      : "archstartDateAfterTrigger"       // ID of the button
                                            }
                                        );
                                        </script>
                                    </td>
                                    <td colspan="2" valign="top">Before: <input type="text" id="archstartDateBefore" name="archstartDatebefore" size="10"/>
                                        <img src="../images/cal.gif"  id="archstartDateBeforeTrigger" border="0">
                                        <script type="text/javascript">

                                            Calendar.setup(
                                            {
                                                inputField  : "archstartDateBefore",         // ID of the input field
                                                ifFormat    : "%Y-%m-%d",    // the date format
                                                button      : "archstartDateBeforeTrigger"       // ID of the button
                                            }
                                        );
                                    </script></td>
                                </tr>
                                
                                <tr>
                                    <td valign="top">Archived:</td>
                                    <td valign="top">After: <input type="text" id="archDateAfter" name="archDateAfter" size="10"/>
                                        <img src="../images/cal.gif"  id="archDateAfterTrigger" border="0">
                                        <script type="text/javascript">

                                            Calendar.setup(
                                            {
                                                inputField  : "archDateAfter",         // ID of the input field
                                                ifFormat    : "%Y-%m-%d",    // the date format
                                                button      : "archDateAfterTrigger"       // ID of the button
                                            }
                                        );
                                        </script>
                                    </td>
                                    <td colspan="2" valign="top">Before: <input type="text" id="archDateBefore" name="archDatebefore" size="10"/>
                                        <img src="../images/cal.gif"  id="archDateBeforeTrigger" border="0">
                                        <script type="text/javascript">

                                            Calendar.setup(
                                            {
                                                inputField  : "archDateBefore",         // ID of the input field
                                                ifFormat    : "%Y-%m-%d",    // the date format
                                                button      : "archDateBeforeTrigger"       // ID of the button
                                            }
                                        );
                                    </script></td>
                                </tr>
                                <tr>
                                    <td width="25%" colspan="4" rowspan="1" align="center"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <%--<table width="98%" border="0"
                    cellpadding="3" cellspacing="0" align="center" >--%>
                    <tr>
                        <td width="25%" colspan="4" rowspan="1" align="center"><br/>
                            <input type="submit" id="ss" value="Search strains" name="ss" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" id="sc" value="Clear search" name="sc" />
                        </td>
                    </tr>
                </tbody>
            </table>
            
        </form>
        <br /><br />
        <h4 class="footer"><a href="javascript: void(0)" class="footer" onclick="javascript:showlayer('reqSearch');return false;">+-</a> Requests Search</h4>
        
        <form name="requestsSearch" action="requestsInterface.emma">
            <table width="98%"  border="0"
                   cellpadding="3" cellspacing="0" align="center" id="reqSearch">
                <tbody>
                    <tr>
                        <td>EMMA ID text search:</td>
                        <td><input type="text" name="emmaIdText" value="" size="20" /></td>
                        <td valign="top">Request ID</td>
                        <td valign="top" ><input type="text" name="reqIdText" value="" size="20" /></td>
                    </tr>
                    <tr>
                        <td>International strain name:</td>
                        <td valign="top" > <select name="strainNameReq" id="strainNameReq" class="ifSelect">
                                <option selected value=''>Please select</option>
                                <c:forEach var="intStrainName" items='${keyRef["strains"]}' varStatus="status">
                                    <option value="${fn:escapeXml(intStrainName[1])}">${fn:escapeXml(intStrainName[1])}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>Strain name text search:</td>
                        <td><input type="text" name="strainNameReqText" value="" size="20" /></td>
                    </tr>
                    
                    <tr>
                        <td valign="top">Common strain name:</td>
                        <td valign="top"><select name="commonStrainName" id="commonStrainName" value="" class="ifSelect" >
                            <option selected value=''>Please select</option>
                            <c:forEach var="common_name" items='${keyRef["commonNames"]}' varStatus="status">
                                <option value="${fn:escapeXml(common_name[1])}">${fn:escapeXml(common_name[1])}</option>
                            </c:forEach>
                        </select>
                        <td>Common strain name:</td>
                        <td valign="top">
                            <input type="text" name="commonStrainNameText" value="" size="20" />
                            <br />
                        </td>
                        <td valign="top">&nbsp;</td>
                        <td valign="top">&nbsp;</td>
                        <br />
                    </tr>
                    <tr>
                        <td valign="top" >Repository:</td>
                        <td valign="top" >
                            <select name="ReqArchCentres"  id="ReqArchCentres" class="ifSelect">
                                <option selected value=''>Please select</option>
                                <c:forEach var="labs" items='${keyRef["labs"]}'>
                                    <option value="${labs.id_labo}">${labs.name} (${labs.code})</option>
                                </c:forEach>
                            </select>
                            <br/>
                        </td>
                        <td valign="top" >Request status:</td>
                        <td valign="top" >
                            <select name="reqStatus"  id="reqStatus" class="ifSelect">
                                <option selected value=''>Please select</option>
                                <c:forEach var="reqStatus" items='${keyRef["reqStatus"]}'>
                                    <c:choose>
                                        <c:when  test="${reqStatus eq 'IN_PR'}" > <
                                            <option value="${reqStatus}">In progress</option>
                                        </c:when>
                                        <c:when  test="${reqStatus eq 'TO_PR'}" > <
                                            <option value="${reqStatus}">To process</option>
                                        </c:when>
                                        <c:when  test="${reqStatus eq 'CANC'}" > <
                                            <option value="${reqStatus}">Cancelled</option>
                                        </c:when>
                                        <c:when  test="${reqStatus eq 'SHIP'}" > <
                                            <option value="${reqStatus}">Shipped</option>
                                        </c:when>
                                        <c:otherwise>
                                            
                                        </c:otherwise>
                                    </c:choose> 
                                </c:forEach>
                            </select>
                            <br/>
                        </td>
                    </tr>
                    <tr>
                        <td valign="top" >Registration of interest (ROI):</td>
                        <td valign="top" >
                            <select name="reqROI">
                                <option selected value=""></option>
                                <option value="1">Yes</option>
                                <option value="0">No</option>
                            </select>
                        </td>
                        <td valign="top" >Country:</td>
                        <td valign="top" >
                            <select name="reqConCountry"  id="reqConCountry" class="ifSelect">
                                <option selected value=''>Please select</option>
                                <c:forEach var="conCountries" items='${keyRef["conCountries"]}'>
                                    <c:choose>
                                        <c:when  test="${empty conCountries}" >
                                            
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${conCountries}">${conCountries}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                            <br />
                        </td>
                    </tr>
                    
                    <tr>
                        <td valign="top" >Contacts surname:</td>
                        <td valign="top" >
                            <input type="text" name="reqCon_contact" value="" size="20" />
                        </td>
                        <td valign="top" >Scientists surname:</td>
                        <td valign="top" >
                            <input type="text" name="reqSci_surname" value="" size="20" />
                            <br />
                        </td>
                    </tr>
                    <tr>
                        <td valign="top">Research tools:</td>
                        <td valign="top">
                            <select name="reqrTools"  id="reqrTools" class="ifSelect">
                                <option selected value=''>Please select</option>
                                <c:forEach var="rtools" items='${keyRef["rTools"]}'>
                                    <c:choose>
                                        <c:when  test="${empty rtools['code']}" >
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when  test="${rtools.id == 6}" >
                                                    <%-- DO NOTHING --%>
                                                </c:when>
                                                <c:when  test="${rtools.id == 7}" >
                                                    <%-- DO NOTHING --%>
                                                </c:when>
                                                <%--<c:when  test="${rtools.id == 9}" >
                                                    <%-- DO NOTHING 
                                                    
                                                </c:when>--%>
                                                <c:otherwise>
                                                    <option value="${rtools["id"]}">${rtools["code"]} (${rtools["description"]})</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                            <br />
                        </td>
                        <td>Projects</td>
                        <td><select name="reqProjects"  id="reqProjects" class="ifSelect" onchange="optionVal();">
                                <option selected value=''>Please select</option>
                                <c:forEach var="projects" items='${keyRef["projects"]}'>
                                    <c:choose>
                                        <c:when  test="${empty projects}" >
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${projects[0]}">${projects[1]} (${projects[2]})</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                            <br />
                        </td>
                    </tr>
                    <tr>
                        <%--  <td>&nbsp;</td><td>&nbsp;</td>--%>
                        <td valign="top">Funding source:</td>
                        <td valign="top">            <select name="reqFunding"  id="reqFunding" multiple="multiple" size="3" class="ifSelect">
                                <option selected value=''>Please select</option>
                                <c:forEach var="funding" items='${keyRef["fundingReqs"]}'>
                                    <option value="${funding[0]}">( ${funding[1]} ) ${funding[2]}</option>
                                </c:forEach>
                            </select>
                        <br /></td>
                        <td>Requested material:</td>
                        <td>
                            <select name="reqMaterial"  id="reqMaterial" class="ifSelect">
                                <option value="" selected>Please select</option>
                                <option value="live">Live animals</option>
                                <option value="frozen">Frozen material</option>
                                <option value="first available">First available</option>
                        </select></td>
                    </tr>
                    <tr>
                        <td valign="top">TA panel descision:</td>
                        <td valign="top">
                            <select name="reqTADecision"  id="reqTADecision" class="ifSelect">
                                <option value="" selected>Please select</option>
                                <option value="yes">Yes</option>
                                <option value="no">No</option>
                            </select>
                        </td>
                        <td>Application type:</td>
                        <td>
                            <select name="reqApplicationType"  id="reqApplicationType" class="ifSelect">
                                <option value="" selected>Please select</option>
                                <option value="request_only">Request only</option>
                                <option value="ta_only">TA only</option>
                                <option value="ta_or_request">TA or request</option>
                        </select></td>
                    </tr>
                    <tr>
                        <td>EUCOMM/EMMA Shipment</td>
                        <td><select name="reqShipmentType"  id="reqShipmentType" class="ifSelect">
                                <option value="" selected>Please select</option>
                                <option value="EUCOMM mice">EUCOMM mice</option>
                                <option value="EMMA mice">EMMA mice</option>
                        </select></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        </tr>
                    <tr>
                        <td valign="top">Submitted:</td>
                        <td valign="top">After: <input type="text" id="reqSubDateAfter" name="reqSubDateAfter" size="10"/>
                            <img src="../images/cal.gif"  id="reqSubDateAfterTrigger" border="0">
                            <script type="text/javascript">

                                Calendar.setup(
                                {
                                    inputField  : "reqSubDateAfter",         // ID of the input field
                                    ifFormat    : "%Y-%m-%d",    // the date format
                                    button      : "reqSubDateAfterTrigger"       // ID of the button
                                }
                            );
                            </script>
                        </td>
                        <td colspan="2" valign="top">Before: <input type="text" id="reqSubDateBefore" name="reqSubDatebefore" size="10"/>
                            <img src="../images/cal.gif"  id="reqSubDateBeforeTrigger" border="0">
                            <script type="text/javascript">

                                Calendar.setup(
                                {
                                    inputField  : "reqSubDateBefore",         // ID of the input field
                                    ifFormat    : "%Y-%m-%d",    // the date format
                                    button      : "reqSubDateBeforeTrigger"       // ID of the button
                                }
                            );
                        </script></td>
                    </tr>
                    <tr>
                        <td valign="top">Date of shipment/cancellation:</td>
                        <td valign="top">After: <input type="text" id="reqShipDateAfter" name="reqShipDateAfter" size="10"/>
                            <img src="../images/cal.gif"  id="reqShipDateAfterTrigger" border="0">
                            <script type="text/javascript">

                                Calendar.setup(
                                {
                                    inputField  : "reqShipDateAfter",         // ID of the input field
                                    ifFormat    : "%Y-%m-%d",    // the date format
                                    button      : "reqShipDateAfterTrigger"       // ID of the button
                                }
                            );
                            </script>
                        </td>
                        <td colspan="2" valign="top">Before: <input type="text" id="reqShipDateBefore" name="reqShipDatebefore" size="10"/>
                            <img src="../images/cal.gif"  id="reqShipDateBeforeTrigger" border="0">
                            <script type="text/javascript">

                                Calendar.setup(
                                {
                                    inputField  : "reqShipDateBefore",         // ID of the input field
                                    ifFormat    : "%Y-%m-%d",    // the date format
                                    button      : "reqShipDateBeforeTrigger"       // ID of the button
                                }
                            );
                        </script></td>
                    </tr>
                    <tr>
                        <td colspan="4" rowspan="1" align="center">
                            <input type="submit" id="sr" value="Search requests" name="sr" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" id="sc" value="Clear search" name="sc" />
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>
        <br><br>
        <%-- include consistent footer/navigation --%>
        <%@ include file="footer_inc.html" %>
    </body>
</html>
