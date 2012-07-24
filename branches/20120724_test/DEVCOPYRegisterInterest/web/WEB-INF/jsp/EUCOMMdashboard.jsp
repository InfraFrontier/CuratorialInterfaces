<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/datetime-1.0" prefix="dt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<c:set var="keyRef" value='${returnedOut}'></c:set>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>EMMA ${keyRef["page"]} Dashboard</title>
    <link title="Standard" type="text/css" href="css/emmastyle.css" rel="stylesheet">
   
</head>
<body topmargin="0" leftmargin="0" marginheight="0" marginwidth="0">
<h4 style='font-family:Arial, Helvetica, sans-serif;background-color:#0090D2; color: #FFFFFF;'>&nbsp;&nbsp;EMMA ${keyRef["page"]} Dashboard&nbsp;&nbsp;Logged in as user: <c:out value="${fn:toUpperCase(sessionScope.SPRING_SECURITY_LAST_USERNAME)}"/></h4>
<p slign="center"><a href="dashboard.emma?page=eucomm&view=spreadsheet"><img src="images/excel_icon.gif" border="0" alt="Generate Excel Metrics report"/></a> <a href="dashboard.emma?page=eucomm&view=spreadsheet">Generate Spreadsheet Metrics Report</a></p>

<img src="${keyRef["EUCOMMReqsByCountryPie"]}" alt="${keyRef["EUCOMMReqsByCountryPie"]}">
<img src="${keyRef["EucommMonthYearChart"]}" alt="${keyRef["EucommMonthYearChart"]}">
<img src="${keyRef["EucommMonthYearCentreChart"]}" alt="${keyRef["EucommMonthYearCentreChart"]}">
<img src="${keyRef["EucommMonthYearCentreShippedChart"]}" alt="${keyRef["EucommMonthYearCentreShippedChart"]}">
<img src="${keyRef["EucommMonthYearCentrePercentChart"]}" alt="${keyRef["EucommMonthYearCentrePercentChart"]}">
<img src="${keyRef["EucommMonthYearCentrePercentReqsChart"]}" alt="${keyRef["EucommMonthYearCentrePercentReqsChart"]}">
</body>
</html>
