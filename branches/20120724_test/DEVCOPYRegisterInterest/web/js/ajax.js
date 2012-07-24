var xmlHttp
function ajax(script,div) {
    var url="";
    alert(url+script+div);
    url  +=  script;
    xmlHttp=GetXmlHttpObject()
    xmlHttp.onreadystatechange=stateChanged 
    xmlHttp.open("GET",url,true)
    xmlHttp.send(null)
    
    function stateChanged() { 
        if (xmlHttp.readyState==4 || xmlHttp.readyState=="complete") { 
            
            document.getElementById(div).innerHTML=xmlHttp.responseText ;
            //document.getElementById(span).innerHTML=xmlHttp.responseText ;
        } 
    } 
    
    function GetXmlHttpObject() { 
        var objXMLHttp=null
        if (window.XMLHttpRequest) {
            objXMLHttp=new XMLHttpRequest()
        }
        else if (window.ActiveXObject) {
            objXMLHttp=new ActiveXObject("Microsoft.XMLHTTP")
        }
        return objXMLHttp
    } 
}