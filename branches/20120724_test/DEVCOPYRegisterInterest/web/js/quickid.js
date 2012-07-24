function reloadNewID() {
    var id = document.forms[0].searchbyid.value;
    var param=""; var searchString=window.location.search;
    param=searchString.replace("?","");
    if (param.substring(0,5) == "EditA") {
        param="EditArch=" + id;
    }else if (param.substring(0,5) == "poEdi") {
        param="poEdit=" + id;
    }
    else {
        param="EditStrain=" + id;
    }
    window.location.href =  "?" + param;
    return false;
}