<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/datetime-1.0" prefix="dt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EMMA Dashboard</title>
        <link title="Standard" type="text/css" href="css/emmastyle.css" rel="stylesheet">
        
    </head>
    <body topmargin="0" leftmargin="0" marginheight="0" marginwidth="0">
        <h4 style='font-family:Arial, Helvetica, sans-serif;background-color:#0090D2; color: #FFFFFF;'>&nbsp;&nbsp;EMMA Dashboard&nbsp;&nbsp;Logged in as user: <c:out value="${fn:toUpperCase(sessionScope.SPRING_SECURITY_LAST_USERNAME)}"/></h4>
        <c:set var="keyRef" value='${returnedOut}'></c:set>
        <!-- ARCHIVING CENTRE STATS START -->
        
        <p style="text-align: center;">
            <a href="wp4dashboard.emma">WP4 reporting tool</a> || <a href="dashboard.emma?page=eucomm">View EUCOMM Charts</a> || <a href="http://www.emmanet.org/internal/webstats/latest/awstats.147-20110613.html" target="_blank">View current website statistics report</a>
        </p>
        
        
       <%-- <h4>Archiving Centre Activities</h4>
        
        <table border="1" width="80%" cellpadding="2" align="center">
            <tbody>
                <tr>
                    <th>EMMA Centre</th>
                    <th>Time to Archive (avg days)</th>
                    <th>Time to Freeze (avg days)</th>
                    <th>Strains (total)</th>
                    <th>Public Strains (total)</th>
                    <th>Strains Arrived</th>
                    <th>Strains Not Arrived</th>
                </tr>
                <tr>
                    <td>${keyRef["labName0"]} (${keyRef["labCountry0"]})</td>
                    <td>${keyRef["LabServiceTimeToArch0"]}</td>
                    <td>${keyRef["LabServiceTimeToFrze0"]}</td>
                    <td>${keyRef["LabStrainCount0"]}</td>
                    <td>${keyRef["LabPubStrainCount0"]}</td>
                    <td>${keyRef["LabArrdStrainCount0"]}</td>
                    <td>${keyRef["LabNotArrdStrainCount0"]}</td>
                </tr>
                <tr>
                    <td>${keyRef["labName1"]} (${keyRef["labCountry1"]})</td>
                    <td>${keyRef["LabServiceTimeToArch1"]}</td>
                    <td>${keyRef["LabServiceTimeToFrze1"]}</td>
                    <td>${keyRef["LabStrainCount1"]}</td>
                    <td>${keyRef["LabPubStrainCount1"]}</td>
                    <td>${keyRef["LabArrdStrainCount1"]}</td>
                    <td>${keyRef["LabNotArrdStrainCount1"]}</td>
                </tr>
                <tr>
                    <td>${keyRef["labName2"]} (${keyRef["labCountry2"]})</td>
                    <td>${keyRef["LabServiceTimeToArch2"]}</td>
                    <td>${keyRef["LabServiceTimeToFrze2"]}</td>
                    <td>${keyRef["LabStrainCount2"]}</td>
                    <td>${keyRef["LabPubStrainCount2"]}</td>
                    <td>${keyRef["LabArrdStrainCount2"]}</td>
                    <td>${keyRef["LabNotArrdStrainCount2"]}</td>
                </tr>
                <tr>
                    <td>${keyRef["labName3"]} (${keyRef["labCountry3"]})</td>
                    <td>${keyRef["LabServiceTimeToArch3"]}</td>
                    <td>${keyRef["LabServiceTimeToFrze3"]}</td>
                    <td>${keyRef["LabStrainCount3"]}</td>
                    <td>${keyRef["LabPubStrainCount3"]}</td>
                    <td>${keyRef["LabArrdStrainCount3"]}</td>
                    <td>${keyRef["LabNotArrdStrainCount3"]}</td>
                </tr>
                <tr>
                    <td>${keyRef["labName4"]} (${keyRef["labCountry4"]})</td>
                    <td>${keyRef["LabServiceTimeToArch4"]}</td>
                    <td>${keyRef["LabServiceTimeToFrze4"]}</td>
                    <td>${keyRef["LabStrainCount4"]}</td>
                    <td>${keyRef["LabPubStrainCount4"]}</td>
                    <td>${keyRef["LabArrdStrainCount4"]}</td>
                    <td>${keyRef["LabNotArrdStrainCount4"]}</td>
                </tr>
                <tr>
                    <td>${keyRef["labName5"]} (${keyRef["labCountry5"]})</td>
                    <td>${keyRef["LabServiceTimeToArch5"]}</td>
                    <td>${keyRef["LabServiceTimeToFrze5"]}</td>
                    <td>${keyRef["LabStrainCount5"]}</td>
                    <td>${keyRef["LabPubStrainCount5"]}</td>
                    <td>${keyRef["LabArrdStrainCount5"]}</td>
                    <td>${keyRef["LabNotArrdStrainCount5"]}</td>
                </tr>
                <tr>
                    <td>${keyRef["labName6"]} (${keyRef["labCountry6"]})</td>
                    <td>${keyRef["LabServiceTimeToArch6"]}</td>
                    <td>${keyRef["LabServiceTimeToFrze6"]}</td>
                    <td>${keyRef["LabStrainCount6"]}</td>
                    <td>${keyRef["LabPubStrainCount6"]}</td>
                    <td>${keyRef["LabArrdStrainCount6"]}</td>
                    <td>${keyRef["LabNotArrdStrainCount6"]}</td>
                </tr>
                <tr>
                    <td>${keyRef["labName7"]} (${keyRef["labCountry7"]})</td>
                    <td>${keyRef["LabServiceTimeToArch7"]}</td>
                    <td>${keyRef["LabServiceTimeToFrze7"]}</td>
                    <td>${keyRef["LabStrainCount7"]}</td>
                    <td>${keyRef["LabPubStrainCount7"]}</td>
                    <td>${keyRef["LabArrdStrainCount7"]}</td>
                    <td>${keyRef["LabNotArrdStrainCount7"]}</td>
                </tr>
                <tr>
                    <td>${keyRef["labName8"]} (${keyRef["labCountry8"]})</td>
                    <td>${keyRef["LabServiceTimeToArch8"]}</td>
                    <td>${keyRef["LabServiceTimeToFrze8"]}</td>
                    <td>${keyRef["LabStrainCount8"]}</td>
                    <td>${keyRef["LabPubStrainCount8"]}</td>
                    <td>${keyRef["LabArrdStrainCount8"]}</td>
                    <td>${keyRef["LabNotArrdStrainCount8"]}</td>
                </tr>
            </tbody>
        </table>
        --%>
        <!-- ARCHIVING CENTRE STATS FINISH -->
        <!-- TOP 5 data START -->
        <br />
        
        <p>&nbsp;</p>
        <h4 style='font-family:Arial, Helvetica, sans-serif;background-color:#0090D2; color: #FFFFFF;'>&nbsp;&nbsp;EMMA Charts</h4>
        <img src="${keyRef["chartTotReq"]}" alt="${keyRef["chartTotReq"]}">
        <img src="${keyRef["chart2"]}" alt="${keyRef["chart2"]}">
        <img src="${keyRef["chartReqProjYear"]}" alt="${keyRef["chartReqProjYear"]}">
        <!-- CHART data FINISH -->

        <!-- PIE CHART data START -->
        <img src="${keyRef["pie1"]}" alt="${keyRef["pie1"]}">
        <img src="${keyRef["pieMaterial"]}" alt="${keyRef["pieMaterial"]}">
        
        
        <c:forEach var="pieYearChart" items="${keyRef['piematerialbyyear']}">
        
            <img src="<c:out value="${pieYearChart}"/>" alt="<c:out value="${pieYearChart}"/>">
            
        </c:forEach>
        
        <!--<table>
        <tr> 
<c:forEach var="Country" items="${keyRef['country']}">
    
    <th><c:out value="${Country}"/></th>
    
</c:forEach>
</tr>
<tr>
<c:forEach var="Countrycount" items="${keyRef['countryCounts']}">
    
    <td> (<c:out value="${Countrycount}"/>) </td>
   
    
</c:forEach>
</tr>-->

        
        <img src="${keyRef["pieReqCountries"]}" alt="${keyRef["pieReqCountries"]}">
        <img src="${keyRef["chartCountryReqProjYear"]}" alt="${keyRef["chartCountryReqProjYear"]}">
        <img src="${keyRef["submissionChartPerYear"]}" alt="${keyRef["submissionChartPerYear"]}">
        <img src="${keyRef["totalSubmissionsPerYear"]}" alt="${keyRef["totalSubmissionsPerYear"]}">
        <img src="${keyRef["chartSubmissionsProjYear"]}" alt="${keyRef["chartSubmissionsProjYear"]}">
        <!-- PIE CHART data FINISH -->
        <!-- FOOTER -->
        <br /><br /><br /><center><address style="font-family:Verdana, Arial, Helvetica, sans-serif; font-size: 10pt;color:#A6A6A6;">EMMA Dashboard live report<br />generated on <dt:format pattern="dd-MM-yyyy HH:mm:s"><dt:currentTime/></dt:format> gmt
        <c:set var="keyRef" value='${returnedOut}'></c:set></address></center>
    </body>
</html>
