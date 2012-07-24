
var gmyWin = null;  

/*function windowOpener(url, winObj)
{
    //open with javascript:gmyWin=openWindow("targett.xxxx", gmyWin ); return false;
    var theWin; 
    if (winObj != null)
    {
        if (!winObj.closed) {
            winObj.focus();
            return winObj;
        }
    }
    theWin =  window.open(url, null, "width=880, height=480, menubar=no,status=no,location=no,scrollbars=yes");
    return theWin;
} 
*/

function openWindow(url) {
if (typeof(popupWin) != "object"){
popupWin = window.open(url,"editWindow", "width=880, height=480, menubar=no,status=no,location=no,scrollbars=yes");
} else {
if (!popupWin.closed){
popupWin.location.href = url;
} else {
popupWin = window.open(url, "editWindow", "width=880, height=480, menubar=no,status=no,location=no,scrollbars=yes");
}
}
popupWin.focus();
}