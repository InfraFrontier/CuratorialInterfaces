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

<%--<c:set var="keyRef" value='${returnedOut}'></c:set>
<c:set var="JSON" value='${keyRef["JSON"]}'></c:set>
<c:set var="LIST" value='${keyRef["list"]}'></c:set>--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="js/jquery.js"></script>
        <%-- <script src="js/grid.locale-en.js" type="text/javascript"></script>
        <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script src="js/JsonXml.js" type="text/javascript"></script>
        <script src="js/grid.import.js" type="text/javascript"></script>--%>


        <script src="js/ajax.js" type="text/javascript"></script>
        <link title="Standard" type="text/css" href="css/emmastyle.css" rel="stylesheet">
        <%-- <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/redmond/jquery-ui-1.8.4.custom.css" />--%>
        <title>EMMAservice WP4 Dynamic Report</title>
        <script type="text/javascript">
            function checkForEucOnly(){
                //document.getElementById('project.disregard').checked = false;
                var projCommunity = document.getElementById('project.community').checked;
                var projMgp = document.getElementById('project.mgp').checked;
                //var projEumodic = document.getElementById('project.eumodic').checked;
                var projEucomm = document.getElementById('eucommtd').checked;
                //&& !projEumodic
                if(!projCommunity
                    &&  !projMgp
                    
            )
                {
                    if(projEucomm){
                        alert('For a more comprehensive search for EUCOMM project strains \nuse the filter for Research Tools selecting the EUC value');
                        var view = $('input:radio[name=view]:checked').val();
                        if (view.indexOf("spreadsheet") >= 0){
                            return;
                        }else{
                            return;// false;
                        }
                    }
                }
            }
            
            function deselect(){
                //alert("deselect called");
                if(document.getElementById('project.disregard').checked) {
                    document.getElementById('project.community').checked = false;
                    document.getElementById('project.mgp').checked = false;
                    document.getElementById('eucommtd').checked = false;
                    // document.getElementById('project.disregard').checked=false;
                    document.getElementById('project.disregard').checked=true;
                }
                if(document.getElementById('funding.disregard').checked) {
                    document.getElementById('funding.s1wp4').checked = false;
                    document.getElementById('funding.s2wp4').checked = false;
                    document.getElementById('funding.s3wp4').checked = false;
                    //document.getElementById('funding.disregard').checked = false;
                    
                    document.getElementById('funding.disregard').checked=true;
                }
                
                if(document.getElementById('stock.disregard').checked) {
                    document.getElementById('stock.embryos').checked = false;
                    document.getElementById('stock.sperm').checked = false;
                    
                    document.getElementById('stock.disregard').checked=true;
                }
            }
            
            function deselectSpecific(field){
                // alert("document.getElementById(" + field + ")");
                document.getElementById(field).checked=false;
            }
                
          
        </script>
    </head>
    <body>
        <h4 style='font-family:Arial, Helvetica, sans-serif;background-color:#0090D2; color: #FFFFFF;'>Query filters</h4>
        <center><h4><a href="http://www.emmanet.org/internal/General/EMMAgeneral/wp4_usage_instructions_v1.0_Feb2012.pdf" target="_blank">? WP4 Dashboard Usage Instructions</a></h4></center>
        <form>
            <table align="center" width="75%" id="searchFilters" >
                <tr>
                    <th align="left">Project</th>
                    <th align="left">Research Tools</th>
                    <th align="left">Funding source</th>
                    <th align="left">In stock</th>
                    <th align="left">Mutation type</th>
                    <th align="left">Factor multiple mutations</th>
                    <th align="left">Strain access</th>
                </tr>
                <tr>
                    <td><input type="checkbox" id="project.community"  onclick="deselectSpecific('project.disregard')" name="project.community" value="on" checked>&nbsp:: Community</td>
                    <td><input type="checkbox" id="rtools.cre"  name="rtools.cre" value="on">&nbsp:: CRE</td>
                    <td><input type="checkbox" id="funding.s1wp4"  onclick="deselectSpecific('funding.disregard')" name="funding.s1wp4" value="on" checked>&nbsp:: s1WP4 (1st period)</td>
                    <td><input type="checkbox" id="stock.embryos"  onclick="deselectSpecific('stock.disregard')"  name="stock.embryos" value="on">&nbsp:: Embryos</td>
                    <td><input type="checkbox" id="mutation.chranomoly"  name="mutation.chranomoly" value="on">&nbsp:: Chr. Anomoly</td>
                    <td><input type="radio" id="multimutation.1"  name="multimutation" value="1" checked>&nbsp:: Count all strains once</td>
                    <td><input type="checkbox" id="strainaccess.public"  name="strainaccess.public" value="on" checked>&nbsp:: Public (P)</td>
                </tr>
                <tr>
                    <td><input type="checkbox" id="project.mgp"  onclick="deselectSpecific(project.deselect)" name="project.mgp" value="on" checked >&nbsp:: MGP</td>
                    <td><input type="checkbox" id="rtools.loxp"  name="rtools.loxp" value="on">&nbsp:: loxP</td>
                    <td><input type="checkbox" id="funding.s2wp4"  onclick="deselectSpecific('funding.disregard')" name="funding.s2wp4" value="on" checked>&nbsp:: s2WP4 (2nd period)</td>
                    <td><input type="checkbox" id="stock.sperm"  onclick="deselectSpecific('stock.disregard')" name="stock.sperm" value="on">&nbsp:: Sperm</td>
                    <td><input type="checkbox" id="mutation.gene-trap"  name="mutation.gene-trap" value="on">&nbsp:: Gene-Trap</td>
                    <td><input type="radio" id="multimutation.2"  name="multimutation" value="2" >&nbsp:: Count as indicated in reporting count</td>
                    <td><input type="checkbox" id="strainaccess.confidential"  name="strainaccess.confidential" value="on" checked>&nbsp:: Confidential (C)</td>
                </tr>
                <tr>
                    <td><input type="checkbox" id="eucommtd"  onclick="deselectSpecific('project.disregard')" name="project.eucomm" value="-" checked >&nbsp:: EUCOMM</td>
                <script>  $('#eucommtd').click(function() {
                    // alert('For a more comprehensice search for EUCOMM project strains \nuse the filter for Research Tools selecting the EUC value');
                });</script>
                <td><input type="checkbox" id="rtools.flp"  name="rtools.flp" value="on">&nbsp:: FLP</td>
                <td><input type="checkbox" id="funding.s3wp4"  onclick="deselectSpecific('funding.disregard')" name="funding.s3wp4" value="on" checked>&nbsp:: s3WP4 (3rd period)</td>
                <td><input type="checkbox" id="stock.disregard"  name="stock.disregard" value="on" checked onClick="deselect()">&nbsp:: Disregard</td>
                <td><input type="checkbox" id="mutation.induced"  name="mutation.induced" value="on">&nbsp:: Induced</td>
                <td>&nbsp;</td>
                <td><input type="checkbox" id="strainaccess.notfordist"  name="strainaccess.notfordist" value="on" checked>&nbsp:: Not for distribution (N)</td>
                </tr>
                <tr>
                    <td><input type="checkbox" id="project.disregard"  name="project.disregard" value="on" onClick="deselect()">&nbsp:: Disregard</td>
                    <td><input type="checkbox" id="rtools.frt"  name="rtools.frt" value="on">&nbsp:: FRT</td>
                    <td><input type="checkbox" id="funding.disregard"  name="funding.disregard" value="on" onClick="deselect()">&nbsp:: Disregard</td>
                    <td>&nbsp;</td>
                    <td><input type="checkbox" id="mutation.spontaneous"  name="mutation.spontaneous" value="on">&nbsp:: Spontaneous</td>
                    <td>&nbsp;</td>
                    <td><input type="checkbox" id="strainaccess.retracted"  name="strainaccess.retracted" value="on">&nbsp:: Retracted (R)</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td><input type="checkbox" id="rtools.tet"  name="rtools.tet" value="on">&nbsp:: TET</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td><input type="checkbox" id="mutation.transgenic"  name="mutation.transgenic" value="on">&nbsp:: Transgenic</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td><input type="checkbox" id="rtools.lex"  name="rtools.lex" value="on">&nbsp:: LEX</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td><input type="checkbox" id="mutation.targeted"  name="mutation.targeted" value="on">&nbsp:: Targeted</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>

                <tr>
                    <td>&nbsp;</td>
                    <td><input type="checkbox" id="rtools.del"  name="rtools.del" value="on">&nbsp:: DEL</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td><input type="checkbox" id="mutation.targetedko"  name="mutation.targetedko" value="on">&nbsp:: Targeted KO</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>

                <tr>
                    <td>&nbsp;</td>
                    <td><input type="checkbox" id="rtools.euc"  name="rtools.euc" value="on">&nbsp:: EUC</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;<!--<input type="checkbox" id="mutation.disregard"  name="mutation.disregard" value="on" checked>&nbsp:: Disregard--></td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>

                <tr>
                    <th colspan="7" align="left">Time period for results</th>
                </tr>
                <tr>
                    <td>Submitted &rarr; Evaluated</td>
                    <td><input type="radio" name="reportingperiod" value="subeval"></td>
                    <td>&nbsp;</td>
                    <td>&nbsp</td>
                    <td>&nbsp</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>Evaluated &rarr; Mice Arrived/GLT</td>
                    <td><input type="radio" name="reportingperiod" value="evalarr"></td>
                    <td>&nbsp</td>
                    <td>&nbsp;</td>
                    <td>&nbsp</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>Mice Arrived/GLT &rarr; Freezing Started</td>
                    <td><input type="radio" name="reportingperiod" value="arrfrz"></td>
                    <td>&nbsp;</td>
                    <td>&nbsp</td>
                    <td ><input type="radio" id="view"  name="view" value="grid" checked> :: Browser grid view</td>
                    <td ><input type="radio" id="view"  name="view" value="spreadsheet_f"> :: <img src="images/excel_icon.gif" border="0" alt="Generate Excel Metrics report" align="middle"/>  Download spreadsheet<br/>
                        <%-- Spreadsheet removed no longer needed //<input type="radio" id="view"  name="view" value="spreadsheet_redundant"> :: <img src="images/excel_icon.gif" border="0" alt="Generate Excel Metrics report" align="middle"/>  Download non-rudandant spreadsheet--%>
                    </td>
                    <td><%--<input type="submit" id="submit" value="Query &#187;" name="submit" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" id="reset" value="Clear query" name="reset" />--%>&nbsp;</td>
                </tr>
                <tr>
                    <td>Freezing Started &rarr; Archived</td>
                    <td><input type="radio" name="reportingperiod" value="frzarch" checked></td>
                    <td>&nbsp;</td>
                    <td>&nbsp</td>
                    <td>&nbsp</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>Submitted &rarr; Archived</td>
                    <td><input type="radio" name="reportingperiod" value="subarch"></td>
                    <td>&nbsp;</td>
                    <td>&nbsp</td>
                    <td>&nbsp</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <%-- <tr>
                    <td>EMMA ID</td>
                    <td><input type="text" name="emmaID" size="20"></td>
                    <td>&nbsp;</td>
                    <td>&nbsp</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>--%>
                <tr>
                    <td colspan="7">&nbsp;</td>
                </tr>


            </table>

        </form>

        <h4 style='font-family:Arial, Helvetica, sans-serif;background-color:#0090D2; color: #FFFFFF;'>Query results</h4>
        <script>
        function showValues() {
            //  alert("showValues called");
            var checked = checkForEucOnly();
            // alert(checked);
            if (checked != false)
            {
                var view = $('input:radio[name=view]:checked').val();
                //  if(view.charAt(13)=="f") {var response=confirm("Generate full spreadsheet report?");if(response) {window.location = 'wp4Spreadsheet.emma?' + $("form").serialize() }};
                var str = "JSON.emma?"+$("form").serialize();
                if (view.indexOf("spreadsheet") >= 0){
                    var response=confirm("Generate spreadsheet report?");
                    if(response) {
            
                        if(view.charAt(12)=="f"){
                            // alert(view.charAt(12));
                            window.location = 'wp4Spreadsheet.emma?report=f&' + $("form").serialize() ;
                        }else{
                            // alert(view.charAt(12));
            <%-- 2nd spreadsheet removed no longer needed --- window.location = 'wp4Spreadsheet.emma?report=r&' + $("form").serialize() ;--%>
                            }
                        }
 
                    };
    
                    $('#grid').load(str, function(){});
                    //$("#results").text(str);
                }
            }
    
            // function showStatic() {
            //  alert("showStatic called");
            //   var str1 = "staticJSON.emma?view=grid";//+$("form").serialize();
            //  $('#resultsStatic').load(str1);
            // setTimeout("4000");
            // showValues();
            //  }
     

            //showValues();
   
        </script>

        <div id="grid"></div>


        <script  type="text/javascript">

        $(document).ready(function () {
            showValues();
            //  showStatic();
               
            $(":checkbox, :radio").click(showValues);
            $("select").change(showValues);
            $("input").keyup(showValues);
    
        });

        </script>
        <br/><br/><br/><br/><br/>
    </body>
</html>
<%--

<tr>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>

--%>
