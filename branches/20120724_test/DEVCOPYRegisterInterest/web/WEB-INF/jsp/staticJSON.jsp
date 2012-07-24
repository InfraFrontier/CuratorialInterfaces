<%-- 
    Document   : wp4dashboard
    Created on : 24-Aug-2010, 12:14:12
    Author     : phil
--%>



<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/datetime-1.0" prefix="dt" %>

<c:set var="keyRef" value='${command}'></c:set>
<c:set var="JSONstatic" value='${keyRef["JSONstatic"]}'></c:set>
<c:set var="staticTable" value='${keyRef["staticHTML"]}'></c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%--<html>
    <head>--%>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery.js"></script>
<script src="js/grid.locale-en.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="js/jquery.JsonXml.js" type="text/javascript"></script>
<script src="js/jquery.grid.import.js" type="text/javascript"></script>


<link title="Standard" type="text/css" href="css/emmastyle.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/redmond/jquery-ui-1.8.4.custom.css"/>
<c:forEach var="obj" items="${JSONstatic}">
    <c:set var="dataArray" value="${dataArray}${obj},"/> 
<!--${dataArray}<br/><br/>-->
</c:forEach>


<%--<center>
    <table id="list1" align="center"></table> 
    <div id="pager1" align="center"></div> 
</center>--%>
<%
String view = request.getParameter("view");


String statichtml = session.getAttribute("staticHTML").toString();

%>

<%
// if (view.equals("staticGrid")) {
      if (view.equals("grid")) {
%>
-<script type="text/javascript"> 
   
    //  jQuery(document).ready(function(){ 
    jQuery("#list1").jqGrid({
        // url:'JSON.emma?view=grid',
        // datatype: "json",
        datatype: "local",
        //datastr: jsonString,
        colNames:['Centre','Accepted','Arrived','Archiving','Archived'],
        colModel:[
            {name:'code',index:'code', width:100},
            {name:'accepted',index:'accepted', width:100},
            {name:'arrived',index:'arrived', width:100},
            {name:'archiving',index:'archiving', width:100},	
            {name:'archived',index:'archived', width:100}
        ],
        //rowNum:10,        
      //  
        rowList:[10,20,30],
        pager: '#pager1',
sortable: true,
       viewrecords: true,
        sortname: 'code',
        sortorder: "asc",
        caption:"Number of strains",
        loadonce: true, 
        pgtext : "Page {0} of {1}"

    });

    jQuery("#list1").jqGrid('navGrid','#pager1',{edit:false,add:false,del:false});

    var  jsonStringStatic=[${dataArray}];   
      for(var i=0;i<=jsonStringStatic.length;i++)
            jQuery("#list1").jqGrid('addRowData',i+1,jsonStringStatic[i]); 
        jQuery("#list1").setGridParam({rowNum:10}).trigger("reloadGrid");
      //   document.write(jsonString);
      
      
</script>

<%
//out.print(statichtml);
}
%>
<%--</body>
</html>--%>

