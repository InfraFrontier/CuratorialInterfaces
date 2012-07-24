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
<c:set var="JSON" value='${keyRef["JSON"]}'></c:set>
<c:set var="JSONs" value='${keyRef["JSONstatic"]}'></c:set>
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
<c:forEach var="obj" items="${JSON}">
    <c:set var="dataArray" value="${dataArray}${obj},"/> 
    <%--${dataArray}<br/>--%>
</c:forEach>

<c:forEach var="obj" items="${JSONs}">
    <c:set var="dataArrayS" value="${dataArrayS}${obj},"/> 
    <%--${dataArrayS}<br/>--%>
</c:forEach>

<%--</head>
<body>--%>


<table width="80%" align="center">
    <tr>
        <td> <div  style=" float: right; padding: 20px; margin: 0 auto" id="resultsStatic" name="resultsStatic">
                <table id="list1" align="center"></table> 
                <div id="pager1" align="center"></div>
        </div></td>         
        <td> <div style="float: left; padding: 20px; margin:0 auto; width:600px" id="resultsDynamic" name="resultsDynamic">
                <table id="list2" align="center"></table> 
                <div id="pager2" align="center"></div> 
        </div></td>      
    </tr>
</table>

<%--<center>
    <table id="list1" align="center"></table> 
    <div id="pager1" align="center"></div> 

    <table id="list2" align="center"></table> 
    <div id="pager2" align="center"></div> 
</center>--%>
<%
            String view = request.getParameter("view");
            if (view.equals("grid")) {
%>
<script type="text/javascript"> 
   
    //  jQuery(document).ready(function(){ 
    jQuery("#list2").jqGrid({
        // url:'JSON.emma?view=grid',
        // datatype: "json",
        datatype: "local",
        //datastr: jsonString,
        colNames:['Centre','Count','Count used','Min(days)','Max(days)','Median(days)','Average(days)',],
        colModel:[
            {name:'code',index:'code', width:100},
            {name:'COUNT',index:'COUNT', width:100},
            {name:'ACTUALCOUNT',index:'ACTUALCOUNT', width:100},
            {name:'MIN',index:'MIN', width:100},
            {name:'MAX',index:'MAX', width:100},
            {name:'MEDIAN',index:'MEDIAN', width:100},
            {name:'AVG',index:'AVG', width:100}
        ],
        //rowNum:10,
        rowList:[10,20,30],
        pager: '#pager2',
        sortname: 'code',
        viewrecords: true,
        sortorder: "asc",
        caption:"Query Results",
        pgtext : "Page {0} of {1}",
        height:"230"
    });

    jQuery("#list2").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false});

    var  jsonString=[${dataArray}];   
        for(var i=0;i<=jsonString.length;i++)
            jQuery("#list2").jqGrid('addRowData',i+1,jsonString[i]); 
        jQuery("#list2").setGridParam({rowNum:10}).trigger("reloadGrid");
        //   document.write(jsonString);
      
      
</script>
<%//} else if (view.equals("staticGrid")) {
%>
<%--<script type="text/javascript"> 
   
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
        rowList:[10,20,30],
        pager: '#pager1',
        sortname: 'code',
       viewrecords: true,
        sortorder: "asc",
        caption:"Number of strains",
        loadonce: true, 
        pgtext : "Page {0} of {1}"

    });

    jQuery("#list1").jqGrid('navGrid','#pager1',{edit:false,add:false,del:false});

    var  jsonString=[${dataArray}];   
      for(var i=0;i<=jsonString.length;i++)
            jQuery("#list1").jqGrid('addRowData',i+1,jsonString[i]); 
        jQuery("#list1").setGridParam({rowNum:10}).trigger("reloadGrid");
      //   document.write(jsonString);
      
      
</script>--%>

-<script type="text/javascript"> 
   
        //  jQuery(document).ready(function(){ 
        jQuery("#list1").jqGrid({
            // url:'JSON.emma?view=grid',
            // datatype: "json",
            datatype: "local",
            //datastr: jsonString,
            colNames:['Centre','Accepted','Arrived','Archiving','Archived','Count'],
            colModel:[
                {name:'code',index:'code', width:100},
                {name:'accepted',index:'accepted', width:100},
                {name:'arrived',index:'arrived', width:100},
                {name:'archiving',index:'archiving', width:100},	
                {name:'archived',index:'archived', width:100},
                {name:'totalcount',index:'totalcount', width:100}
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
            pgtext : "Page {0} of {1}",
height:"309"
        });

        jQuery("#list1").jqGrid('navGrid','#pager1',{edit:false,add:false,del:false});

        var  jsonStringStatic=[${dataArrayS}];   
            for(var i=0;i<=jsonStringStatic.length;i++)
                jQuery("#list1").jqGrid('addRowData',i+1,jsonStringStatic[i]); 
            jQuery("#list1").setGridParam({rowNum:30}).trigger("reloadGrid");
            //   document.write(jsonString);
      
      
</script>

<%            }
%>
<%--</body>
</html>--%>

