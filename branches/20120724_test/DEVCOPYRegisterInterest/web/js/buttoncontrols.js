 if (document.images) {
        var first_off = new Image();
        first_off.src = "../images/entrypointbutton.gif";
        var first_on = new Image();
        first_on.src = "../images/Oentrypointbutton.gif";


        var second_off = new Image();
        second_off.src = "../images/archivebutton.gif";
        var second_on = new Image();
        second_on.src = "../images/Oarchivebutton.gif";


        var third_off = new Image();
        third_off.src = "../images/strainsbutton.gif";
        var third_on = new Image();
        third_on.src = "../images/Ostrainsbutton.gif";


        var fourth_off = new Image();
        fourth_off.src = "../images/requestsbutton.gif";
        var fourth_on = new Image();
        fourth_on.src = "../images/Orequestsbutton.gif";

        var fifth_off = new Image();
        fifth_off.src = "../images/pobutton.gif";
        var fifth_on = new Image();
        fifth_on.src = "../images/Opobutton.gif";


        var sixth_off = new Image();
        sixth_off.src = "../images/contactsbutton.gif";
        var sixth_on = new Image();
        sixth_on.src = "../images/Ocontactsbutton.gif";


        var seventh_off = new Image();
        seventh_off.src = "../images/internalsitebutton.gif";
        var seventh_on = new Image();
        seventh_on.src = "../images/Ointernalsitebutton.gif";

   }

    function activate(imgName) {
        if (document.images) {
            if ( eval(imgName + "_on.complete") ) {
                document.images[imgName].src = eval(imgName + "_on.src");
            }
        }
    }

    function deactivate(imgName) {
        if (document.images) {
            if ( eval(imgName + "_off.complete") ) {
                document.images[imgName].src = eval(imgName + "_off.src");
            }
        }
    }