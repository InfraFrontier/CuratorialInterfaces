<%-- 
    Document   : submissionForm
    Created on : 26-Jan-2012, 10:53:06
    Author     : phil
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/datetime-1.0" prefix="dt" %>
<spring:bind path="command.*"/>

<c:set var="keyRef" value='${command}'></c:set>

<c:set var="PeopleDAO" value='${keyRef["peopleDAO"]}'></c:set>
<c:set var="LabsDAO" value='${ArchiveDAO["labsDAO"]}'></c:set>

<%
//read in option list from file source
    java.io.BufferedReader inSources = new java.io.BufferedReader(new java.io.FileReader("/nfs/panda/emma/tmp/bglist"));
    String strInBGSources;
    String strOutBGSources = "";
    while ((strInBGSources = inSources.readLine()) != null) {
        strOutBGSources = strOutBGSources + strInBGSources;
    }

%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML> 
<html lang="en">
    <head>
        <title>
            EMMA Mutant Mouse Strain Submission Wizard
        </title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
        <link type="text/css" href="css/token-input.css" rel="stylesheet" media="screen" charset="UTF-8" />
        <link type="text/css" href="css/submission.css" rel="stylesheet" media="screen" charset="UTF-8" />
        <script type="text/javascript" src="js/json2.js" charset="UTF-8">
        </script>
        <script type="text/javascript" src="js/jquery-1.6.1.min.js" charset="UTF-8">
        </script>
        <script type="text/javascript" src="js/jquery.tokeninput.js" charset="UTF-8">
        </script>
        <script type="text/javascript" src="js/jquery.qtip-1.0.0-rc3.min.js" charset="UTF-8">
        </script>
        <script type="text/javascript" src="js/jquery.easydate-0.2.4.min.js" charset="UTF-8">
        </script>
        <script type="text/javascript" src="js/jquery.store.js" charset="UTF-8">
        </script>
        <script type="text/javascript" src="js/jquery.form.js" charset="UTF-8">
        </script>
        <script type="text/javascript" src="js/jquery.validate.js" charset="UTF-8">
        </script>
        <script type="text/javascript" src="js/bbq.js" charset="UTF-8">
        </script>
        <script type="text/javascript" src="js/jquery-ui-1.8.5.custom.min.js" charset="UTF-8">
        </script>
        <script type="text/javascript" src="js/jquery.form.wizard-3.0.5.js" charset="UTF-8">
        </script>
        <script type="text/javascript" src="js/submission.js" charset="UTF-8">
        </script>
    </head>
    <body>
        <h1>
            EMMA Mutant Mouse Strain Submission Wizard
        </h1>
        <form id="submission" method="POST" action="." enctype="multipart/form-data" class="bbq">
            <div id="splash" class="step">
                <p>
                    Dear Submitting Investigator,
                </p>
                <p>
                    Please use this wizard to provide detailed information about the mutant mouse strain that you want to submit to EMMA. This information is used to evaluate your submission request. Please note that if the request is approved the information you provide will be published on the EMMA website.
                </p>
                <p>
                    Please fill the form as accurately as you can using "unknown", "not applicable" or "none" if you do not possess the information requested. A sample submission is provided <a href="#">here</a> for your convenience to examine all the information that is required.
                </p>
                <p>
                    <b>Multiple strains must be submitted separately. One strain per submission.</b>
                </p>
                <p>
                    We strongly recommend that you carefully read the <a target="PDF" href="/procedures/costs.php">EMMA Procedures</a> on this website. These documents describe the responsibilities EMMA has in maintaining and distributing the submitted strains as well as the responsibilities assumed by the submitter.
                </p>
                <p>
                    If your browser does not support forms you can <a href="mailto:info@emmanet.org">e-mail</a> all the complete strain information to EMMA.
                </p>
                <p>
                    Within 60 days you will be notified by e-mail of the result of the evaluation of your submission request.
                </p>
                <p>
                    Thank you for your interest in the European Mouse Mutant Archive.
                </p>
                <div class="field">
                    <div class="input">
                        <label><input type="checkbox" name="agree" value="" id="agree" />&nbsp;I have read the information above and agree to the <a href="#">EMMA Terms &amp; Conditions</a>!</label>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
            </div>
            <div id="start" class="step">
                <h2>
                    Start
                </h2>
                <p>
                    Please enter your email address for identification. For your convenience, if you have started or completed a mutant mouse strain submission previously using this computer, you will be prompted to either resume your incomplete submission from where you left off or, if the submission was completed, you will be asked whether you want to reuse your contact information.
                </p>
                <div class="field">
                    <label class="label" for="submitter_email"><strong>Email<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="submitter_email" value="" id="submitter_email" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
            </div>
            <div id="submitter" class="step">
                <h2>
                    Submitter (step 1 of 10)
                </h2>
                <p>
                    Please enter your contact information.
                </p>
                <div class="field">
                    <label class="label"><strong>Email<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <span name="submitter_email_alias" id="submitter_email_alias">&nbsp;</span>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="submitter_title"><strong>Title</strong></label>
                    <div class="input">
                        <select name="submitter_title" id="submitter_title">
                            <option value="">
                                &nbsp;
                            </option>
                            <option value="Mr">
                                Mr
                            </option>
                            <option value="Mrs">
                                Mrs
                            </option>
                            <option value="Ms">
                                Ms
                            </option>
                            <option value="Prof">
                                Prof
                            </option>
                            <option value="Dr">
                                Dr
                            </option>
                        </select>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="submitter_firstname"><strong>First name</strong></label>
                    <div class="input">
                        <input type="text" name="submitter_firstname" value="" id="submitter_firstname" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="submitter_lastname"><strong>Last Name<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="submitter_lastname" value="" id="submitter_lastname" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="submitter_tel"><strong>Phone Number<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="submitter_tel" value="" id="submitter_tel" alt="The phone number must begin with + followed by the country code and contain only numbers, hyphens, spaces, or parentheses. An extension must begin with x followed by the extension number, e.g., +1 (234) 567-8900 x123" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="submitter_fax"><strong>Fax Number<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="submitter_fax" value="" id="submitter_fax" alt="The fax number must begin with + followed by the country code and contain only numbers, hyphens, spaces, or parentheses. An extension must begin with x followed by the extension number, e.g., +1 (234) 567-8900 x123" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="submitter_inst"><strong>Institution<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="submitter_inst" value="" id="submitter_inst" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="submitter_dept"><strong>Department</strong></label>
                    <div class="input">
                        <input type="text" name="submitter_dept" value="" id="submitter_dept" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="submitter_addr_1"><strong>Address line 1/Street address<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="submitter_addr_1" value="" id="submitter_addr_1" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="submitter_addr_2"><strong>Address line 2</strong></label>
                    <div class="input">
                        <input type="text" name="submitter_addr_2" value="" id="submitter_addr_2" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="submitter_city"><strong>City<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="submitter_city" value="" id="submitter_city" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="submitter_county"><strong>County/Province/State</strong></label>
                    <div class="input">
                        <input type="text" name="submitter_county" value="" id="submitter_county" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="submitter_postcode"><strong>Postcode/Zipcode<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="submitter_postcode" value="" id="submitter_postcode" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <!--TODO COUNTRIES FROM DATABASE-->
                    <label class="label" for="submitter_country"><strong>Country<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <select name="submitter_country" id="submitter_country">
                            <option value="">
                                &nbsp;
                            </option>
                            <option value="AF">
                                Afghanistan
                            </option>
                            <option value="AX">
                                Åland Islands
                            </option>
                            <option value="AL">
                                Albania
                            </option>
                            <option value="DZ">
                                Algeria
                            </option>
                            <option value="AS">
                                American Samoa
                            </option>
                            <option value="AD">
                                Andorra
                            </option>
                            <option value="AO">
                                Angola
                            </option>
                            <option value="AI">
                                Anguilla
                            </option>
                            <option value="AQ">
                                Antarctica
                            </option>
                            <option value="AG">
                                Antigua and Barbuda
                            </option>
                            <option value="AR">
                                Argentina
                            </option>
                            <option value="AM">
                                Armenia
                            </option>
                            <option value="AW">
                                Aruba
                            </option>
                            <option value="AU">
                                Australia
                            </option>
                            <option value="AT">
                                Austria
                            </option>
                            <option value="AZ">
                                Azerbaijan
                            </option>
                            <option value="BS">
                                Bahamas
                            </option>
                            <option value="BH">
                                Bahrain
                            </option>
                            <option value="BD">
                                Bangladesh
                            </option>
                            <option value="BB">
                                Barbados
                            </option>
                            <option value="BY">
                                Belarus
                            </option>
                            <option value="BE">
                                Belgium
                            </option>
                            <option value="BZ">
                                Belize
                            </option>
                            <option value="BJ">
                                Benin
                            </option>
                            <option value="BM">
                                Bermuda
                            </option>
                            <option value="BT">
                                Bhutan
                            </option>
                            <option value="BO">
                                Bolivia, Plurinational State of
                            </option>
                            <option value="BA">
                                Bosnia and Herzegovina
                            </option>
                            <option value="BW">
                                Botswana
                            </option>
                            <option value="BV">
                                Bouvet Island
                            </option>
                            <option value="BR">
                                Brazil
                            </option>
                            <option value="IO">
                                British Indian Ocean Territory
                            </option>
                            <option value="BN">
                                Brunei Darussalam
                            </option>
                            <option value="BG">
                                Bulgaria
                            </option>
                            <option value="BF">
                                Burkina Faso
                            </option>
                            <option value="BI">
                                Burundi
                            </option>
                            <option value="KH">
                                Cambodia
                            </option>
                            <option value="CM">
                                Cameroon
                            </option>
                            <option value="CA">
                                Canada
                            </option>
                            <option value="CV">
                                Cape Verde
                            </option>
                            <option value="KY">
                                Cayman Islands
                            </option>
                            <option value="CF">
                                Central African Republic
                            </option>
                            <option value="TD">
                                Chad
                            </option>
                            <option value="CL">
                                Chile
                            </option>
                            <option value="CN">
                                China
                            </option>
                            <option value="CX">
                                Christmas Island
                            </option>
                            <option value="CC">
                                Cocos (Keeling) Islands
                            </option>
                            <option value="CO">
                                Colombia
                            </option>
                            <option value="KM">
                                Comoros
                            </option>
                            <option value="CG">
                                Congo
                            </option>
                            <option value="CD">
                                Congo, The Democratic Republic of the
                            </option>
                            <option value="CK">
                                Cook Islands
                            </option>
                            <option value="CR">
                                Costa Rica
                            </option>
                            <option value="CI">
                                Côte d'Ivoire
                            </option>
                            <option value="HR">
                                Croatia
                            </option>
                            <option value="CU">
                                Cuba
                            </option>
                            <option value="CY">
                                Cyprus
                            </option>
                            <option value="CZ">
                                Czech Republic
                            </option>
                            <option value="DK">
                                Denmark
                            </option>
                            <option value="DJ">
                                Djibouti
                            </option>
                            <option value="DM">
                                Dominica
                            </option>
                            <option value="DO">
                                Dominican Republic
                            </option>
                            <option value="EC">
                                Ecuador
                            </option>
                            <option value="EG">
                                Egypt
                            </option>
                            <option value="SV">
                                El Salvador
                            </option>
                            <option value="GQ">
                                Equatorial Guinea
                            </option>
                            <option value="ER">
                                Eritrea
                            </option>
                            <option value="EE">
                                Estonia
                            </option>
                            <option value="ET">
                                Ethiopia
                            </option>
                            <option value="FK">
                                Falkland Islands (Malvinas)
                            </option>
                            <option value="FO">
                                Faroe Islands
                            </option>
                            <option value="FJ">
                                Fiji
                            </option>
                            <option value="FI">
                                Finland
                            </option>
                            <option value="FR">
                                France
                            </option>
                            <option value="GF">
                                French Guiana
                            </option>
                            <option value="PF">
                                French Polynesia
                            </option>
                            <option value="TF">
                                French Southern Territories
                            </option>
                            <option value="GA">
                                Gabon
                            </option>
                            <option value="GM">
                                Gambia
                            </option>
                            <option value="GE">
                                Georgia
                            </option>
                            <option value="DE">
                                Germany
                            </option>
                            <option value="GH">
                                Ghana
                            </option>
                            <option value="GI">
                                Gibraltar
                            </option>
                            <option value="GR">
                                Greece
                            </option>
                            <option value="GL">
                                Greenland
                            </option>
                            <option value="GD">
                                Grenada
                            </option>
                            <option value="GP">
                                Guadeloupe
                            </option>
                            <option value="GU">
                                Guam
                            </option>
                            <option value="GT">
                                Guatemala
                            </option>
                            <option value="GG">
                                Guernsey
                            </option>
                            <option value="GN">
                                Guinea
                            </option>
                            <option value="GW">
                                Guinea-Bissau
                            </option>
                            <option value="GY">
                                Guyana
                            </option>
                            <option value="HT">
                                Haiti
                            </option>
                            <option value="HM">
                                Heard Island and McDonald Islands
                            </option>
                            <option value="VA">
                                Holy See (Vatican City State)
                            </option>
                            <option value="HN">
                                Honduras
                            </option>
                            <option value="HK">
                                Hong Kong
                            </option>
                            <option value="HU">
                                Hungary
                            </option>
                            <option value="IS">
                                Iceland
                            </option>
                            <option value="IN">
                                India
                            </option>
                            <option value="ID">
                                Indonesia
                            </option>
                            <option value="IR">
                                Iran, Islamic Republic of
                            </option>
                            <option value="IQ">
                                Iraq
                            </option>
                            <option value="IE">
                                Ireland
                            </option>
                            <option value="IM">
                                Isle of Man
                            </option>
                            <option value="IL">
                                Israel
                            </option>
                            <option value="IT">
                                Italy
                            </option>
                            <option value="JM">
                                Jamaica
                            </option>
                            <option value="JP">
                                Japan
                            </option>
                            <option value="JE">
                                Jersey
                            </option>
                            <option value="JO">
                                Jordan
                            </option>
                            <option value="KZ">
                                Kazakhstan
                            </option>
                            <option value="KE">
                                Kenya
                            </option>
                            <option value="KI">
                                Kiribati
                            </option>
                            <option value="KP">
                                Korea, Democratic People's Republic of
                            </option>
                            <option value="KR">
                                Korea, Republic of
                            </option>
                            <option value="KW">
                                Kuwait
                            </option>
                            <option value="KG">
                                Kyrgyzstan
                            </option>
                            <option value="LA">
                                Lao People's Democratic Republic
                            </option>
                            <option value="LV">
                                Latvia
                            </option>
                            <option value="LB">
                                Lebanon
                            </option>
                            <option value="LS">
                                Lesotho
                            </option>
                            <option value="LR">
                                Liberia
                            </option>
                            <option value="LY">
                                Libyan Arab Jamahiriya
                            </option>
                            <option value="LI">
                                Liechtenstein
                            </option>
                            <option value="LT">
                                Lithuania
                            </option>
                            <option value="LU">
                                Luxembourg
                            </option>
                            <option value="MO">
                                Macao
                            </option>
                            <option value="MK">
                                Macedonia, The Former Yugoslav Republic of
                            </option>
                            <option value="MG">
                                Madagascar
                            </option>
                            <option value="MW">
                                Malawi
                            </option>
                            <option value="MY">
                                Malaysia
                            </option>
                            <option value="MV">
                                Maldives
                            </option>
                            <option value="ML">
                                Mali
                            </option>
                            <option value="MT">
                                Malta
                            </option>
                            <option value="MH">
                                Marshall Islands
                            </option>
                            <option value="MQ">
                                Martinique
                            </option>
                            <option value="MR">
                                Mauritania
                            </option>
                            <option value="MU">
                                Mauritius
                            </option>
                            <option value="YT">
                                Mayotte
                            </option>
                            <option value="MX">
                                Mexico
                            </option>
                            <option value="FM">
                                Micronesia, Federated States of
                            </option>
                            <option value="MD">
                                Moldova, Republic of
                            </option>
                            <option value="MC">
                                Monaco
                            </option>
                            <option value="MN">
                                Mongolia
                            </option>
                            <option value="ME">
                                Montenegro
                            </option>
                            <option value="MS">
                                Montserrat
                            </option>
                            <option value="MA">
                                Morocco
                            </option>
                            <option value="MZ">
                                Mozambique
                            </option>
                            <option value="MM">
                                Myanmar
                            </option>
                            <option value="NA">
                                Namibia
                            </option>
                            <option value="NR">
                                Nauru
                            </option>
                            <option value="NP">
                                Nepal
                            </option>
                            <option value="NL">
                                Netherlands
                            </option>
                            <option value="AN">
                                Netherlands Antilles
                            </option>
                            <option value="NC">
                                New Caledonia
                            </option>
                            <option value="NZ">
                                New Zealand
                            </option>
                            <option value="NI">
                                Nicaragua
                            </option>
                            <option value="NE">
                                Niger
                            </option>
                            <option value="NG">
                                Nigeria
                            </option>
                            <option value="NU">
                                Niue
                            </option>
                            <option value="NF">
                                Norfolk Island
                            </option>
                            <option value="MP">
                                Northern Mariana Islands
                            </option>
                            <option value="NO">
                                Norway
                            </option>
                            <option value="OM">
                                Oman
                            </option>
                            <option value="PK">
                                Pakistan
                            </option>
                            <option value="PW">
                                Palau
                            </option>
                            <option value="PS">
                                Palestinian Territory, Occupied
                            </option>
                            <option value="PA">
                                Panama
                            </option>
                            <option value="PG">
                                Papua New Guinea
                            </option>
                            <option value="PY">
                                Paraguay
                            </option>
                            <option value="PE">
                                Peru
                            </option>
                            <option value="PH">
                                Philippines
                            </option>
                            <option value="PN">
                                Pitcairn
                            </option>
                            <option value="PL">
                                Poland
                            </option>
                            <option value="PT">
                                Portugal
                            </option>
                            <option value="PR">
                                Puerto Rico
                            </option>
                            <option value="QA">
                                Qatar
                            </option>
                            <option value="RE">
                                Réunion
                            </option>
                            <option value="RO">
                                Romania
                            </option>
                            <option value="RU">
                                Russian Federation
                            </option>
                            <option value="RW">
                                Rwanda
                            </option>
                            <option value="BL">
                                Saint Barthélemy
                            </option>
                            <option value="SH">
                                Saint Helena, Ascension and Tristan Da Cunha
                            </option>
                            <option value="KN">
                                Saint Kitts and Nevis
                            </option>
                            <option value="LC">
                                Saint Lucia
                            </option>
                            <option value="MF">
                                Saint Martin
                            </option>
                            <option value="PM">
                                Saint Pierre and Miquelon
                            </option>
                            <option value="VC">
                                Saint Vincent and the Grenadines
                            </option>
                            <option value="WS">
                                Samoa
                            </option>
                            <option value="SM">
                                San Marino
                            </option>
                            <option value="ST">
                                Sao Tome and Principe
                            </option>
                            <option value="SA">
                                Saudi Arabia
                            </option>
                            <option value="SN">
                                Senegal
                            </option>
                            <option value="RS">
                                Serbia
                            </option>
                            <option value="SC">
                                Seychelles
                            </option>
                            <option value="SL">
                                Sierra Leone
                            </option>
                            <option value="SG">
                                Singapore
                            </option>
                            <option value="SK">
                                Slovakia
                            </option>
                            <option value="SI">
                                Slovenia
                            </option>
                            <option value="SB">
                                Solomon Islands
                            </option>
                            <option value="SO">
                                Somalia
                            </option>
                            <option value="ZA">
                                South Africa
                            </option>
                            <option value="GS">
                                South Georgia and the South Sandwich Islands
                            </option>
                            <option value="ES">
                                Spain
                            </option>
                            <option value="LK">
                                Sri Lanka
                            </option>
                            <option value="SD">
                                Sudan
                            </option>
                            <option value="SR">
                                Suriname
                            </option>
                            <option value="SJ">
                                Svalbard and Jan Mayen
                            </option>
                            <option value="SZ">
                                Swaziland
                            </option>
                            <option value="SE">
                                Sweden
                            </option>
                            <option value="CH">
                                Switzerland
                            </option>
                            <option value="SY">
                                Syrian Arab Republic
                            </option>
                            <option value="TW">
                                Taiwan, Province of China
                            </option>
                            <option value="TJ">
                                Tajikistan
                            </option>
                            <option value="TZ">
                                Tanzania, United Republic of
                            </option>
                            <option value="TH">
                                Thailand
                            </option>
                            <option value="TL">
                                Timor-Leste
                            </option>
                            <option value="TG">
                                Togo
                            </option>
                            <option value="TK">
                                Tokelau
                            </option>
                            <option value="TO">
                                Tonga
                            </option>
                            <option value="TT">
                                Trinidad and Tobago
                            </option>
                            <option value="TN">
                                Tunisia
                            </option>
                            <option value="TR">
                                Turkey
                            </option>
                            <option value="TM">
                                Turkmenistan
                            </option>
                            <option value="TC">
                                Turks and Caicos Islands
                            </option>
                            <option value="TV">
                                Tuvalu
                            </option>
                            <option value="UG">
                                Uganda
                            </option>
                            <option value="UA">
                                Ukraine
                            </option>
                            <option value="AE">
                                United Arab Emirates
                            </option>
                            <option value="GB">
                                United Kingdom
                            </option>
                            <option value="US">
                                United States
                            </option>
                            <option value="UM">
                                United States Minor Outlying Islands
                            </option>
                            <option value="UY">
                                Uruguay
                            </option>
                            <option value="UZ">
                                Uzbekistan
                            </option>
                            <option value="VU">
                                Vanuatu
                            </option>
                            <option value="VE">
                                Venezuela, Bolivarian Republic of
                            </option>
                            <option value="VN">
                                Viet Nam
                            </option>
                            <option value="VG">
                                Virgin Islands, British
                            </option>
                            <option value="VI">
                                Virgin Islands, U.S.
                            </option>
                            <option value="WF">
                                Wallis and Futuna
                            </option>
                            <option value="EH">
                                Western Sahara
                            </option>
                            <option value="YE">
                                Yemen
                            </option>
                            <option value="ZM">
                                Zambia
                            </option>
                            <option value="ZW">
                                Zimbabwe
                            </option>
                        </select>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
            </div>
            <div id="producer" class="step">
                <h2>
                    Producer (step 2 of 10)
                </h2>
                <p>
                    Please enter the contact information of the principal investigator who generated the mouse mutant strain you want to deposit in EMMA.
                </p>
                <div>
                    <div>
                        <input type="button" name="fill_producer_with_submitter_data" id="fill_producer_with_submitter_data" value="Fill with Submitter data" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="producer_email"><strong>Email<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="producer_email" value="" id="producer_email" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="producer_title"><strong>Title</strong></label>
                    <div class="input">
                        <select name="producer_title" id="producer_title">
                            <option value="">
                                &nbsp;
                            </option>
                            <option value="Mr">
                                Mr
                            </option>
                            <option value="Mrs">
                                Mrs
                            </option>
                            <option value="Ms">
                                Ms
                            </option>
                            <option value="Prof">
                                Prof
                            </option>
                            <option value="Dr">
                                Dr
                            </option>
                        </select>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="producer_firstname"><strong>First name</strong></label>
                    <div class="input">
                        <input type="text" name="producer_firstname" value="" id="producer_firstname" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="producer_lastname"><strong>Last Name<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="producer_lastname" value="" id="producer_lastname" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="producer_tel"><strong>Phone Number<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="producer_tel" value="" id="producer_tel" alt="The phone number must begin with + followed by the country code and contain only numbers, hyphens, spaces, or parentheses. An extension must begin with x followed by the extension number, e.g., +1 (234) 567-8900 x123" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="producer_fax"><strong>Fax Number<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="producer_fax" value="" id="producer_fax" alt="The fax number must begin with + followed by the country code and contain only numbers, hyphens, spaces, or parentheses. An extension must begin with x followed by the extension number, e.g., +1 (234) 567-8900 x123" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="producer_inst"><strong>Institution<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="producer_inst" value="" id="producer_inst" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="producer_dept"><strong>Department</strong></label>
                    <div class="input">
                        <input type="text" name="producer_dept" value="" id="producer_dept" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="producer_addr_1"><strong>Address line 1/Street address<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="producer_addr_1" value="" id="producer_addr_1" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="producer_addr_2"><strong>Address line 2</strong></label>
                    <div class="input">
                        <input type="text" name="producer_addr_2" value="" id="producer_addr_2" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="producer_city"><strong>City<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="producer_city" value="" id="producer_city" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="producer_county"><strong>County/Province/State</strong></label>
                    <div class="input">
                        <input type="text" name="producer_county" value="" id="producer_county" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="producer_postcode"><strong>Postcode/Zipcode<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="producer_postcode" value="" id="producer_postcode" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <!--TODO COUNTRIES FROM DATABASE-->
                    <label class="label" for="producer_country"><strong>Country<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <select name="producer_country" id="producer_country">
                            <option value="">
                                &nbsp;
                            </option>
                            <option value="AF">
                                Afghanistan
                            </option>
                            <option value="AX">
                                Åland Islands
                            </option>
                            <option value="AL">
                                Albania
                            </option>
                            <option value="DZ">
                                Algeria
                            </option>
                            <option value="AS">
                                American Samoa
                            </option>
                            <option value="AD">
                                Andorra
                            </option>
                            <option value="AO">
                                Angola
                            </option>
                            <option value="AI">
                                Anguilla
                            </option>
                            <option value="AQ">
                                Antarctica
                            </option>
                            <option value="AG">
                                Antigua and Barbuda
                            </option>
                            <option value="AR">
                                Argentina
                            </option>
                            <option value="AM">
                                Armenia
                            </option>
                            <option value="AW">
                                Aruba
                            </option>
                            <option value="AU">
                                Australia
                            </option>
                            <option value="AT">
                                Austria
                            </option>
                            <option value="AZ">
                                Azerbaijan
                            </option>
                            <option value="BS">
                                Bahamas
                            </option>
                            <option value="BH">
                                Bahrain
                            </option>
                            <option value="BD">
                                Bangladesh
                            </option>
                            <option value="BB">
                                Barbados
                            </option>
                            <option value="BY">
                                Belarus
                            </option>
                            <option value="BE">
                                Belgium
                            </option>
                            <option value="BZ">
                                Belize
                            </option>
                            <option value="BJ">
                                Benin
                            </option>
                            <option value="BM">
                                Bermuda
                            </option>
                            <option value="BT">
                                Bhutan
                            </option>
                            <option value="BO">
                                Bolivia, Plurinational State of
                            </option>
                            <option value="BA">
                                Bosnia and Herzegovina
                            </option>
                            <option value="BW">
                                Botswana
                            </option>
                            <option value="BV">
                                Bouvet Island
                            </option>
                            <option value="BR">
                                Brazil
                            </option>
                            <option value="IO">
                                British Indian Ocean Territory
                            </option>
                            <option value="BN">
                                Brunei Darussalam
                            </option>
                            <option value="BG">
                                Bulgaria
                            </option>
                            <option value="BF">
                                Burkina Faso
                            </option>
                            <option value="BI">
                                Burundi
                            </option>
                            <option value="KH">
                                Cambodia
                            </option>
                            <option value="CM">
                                Cameroon
                            </option>
                            <option value="CA">
                                Canada
                            </option>
                            <option value="CV">
                                Cape Verde
                            </option>
                            <option value="KY">
                                Cayman Islands
                            </option>
                            <option value="CF">
                                Central African Republic
                            </option>
                            <option value="TD">
                                Chad
                            </option>
                            <option value="CL">
                                Chile
                            </option>
                            <option value="CN">
                                China
                            </option>
                            <option value="CX">
                                Christmas Island
                            </option>
                            <option value="CC">
                                Cocos (Keeling) Islands
                            </option>
                            <option value="CO">
                                Colombia
                            </option>
                            <option value="KM">
                                Comoros
                            </option>
                            <option value="CG">
                                Congo
                            </option>
                            <option value="CD">
                                Congo, The Democratic Republic of the
                            </option>
                            <option value="CK">
                                Cook Islands
                            </option>
                            <option value="CR">
                                Costa Rica
                            </option>
                            <option value="CI">
                                Côte d'Ivoire
                            </option>
                            <option value="HR">
                                Croatia
                            </option>
                            <option value="CU">
                                Cuba
                            </option>
                            <option value="CY">
                                Cyprus
                            </option>
                            <option value="CZ">
                                Czech Republic
                            </option>
                            <option value="DK">
                                Denmark
                            </option>
                            <option value="DJ">
                                Djibouti
                            </option>
                            <option value="DM">
                                Dominica
                            </option>
                            <option value="DO">
                                Dominican Republic
                            </option>
                            <option value="EC">
                                Ecuador
                            </option>
                            <option value="EG">
                                Egypt
                            </option>
                            <option value="SV">
                                El Salvador
                            </option>
                            <option value="GQ">
                                Equatorial Guinea
                            </option>
                            <option value="ER">
                                Eritrea
                            </option>
                            <option value="EE">
                                Estonia
                            </option>
                            <option value="ET">
                                Ethiopia
                            </option>
                            <option value="FK">
                                Falkland Islands (Malvinas)
                            </option>
                            <option value="FO">
                                Faroe Islands
                            </option>
                            <option value="FJ">
                                Fiji
                            </option>
                            <option value="FI">
                                Finland
                            </option>
                            <option value="FR">
                                France
                            </option>
                            <option value="GF">
                                French Guiana
                            </option>
                            <option value="PF">
                                French Polynesia
                            </option>
                            <option value="TF">
                                French Southern Territories
                            </option>
                            <option value="GA">
                                Gabon
                            </option>
                            <option value="GM">
                                Gambia
                            </option>
                            <option value="GE">
                                Georgia
                            </option>
                            <option value="DE">
                                Germany
                            </option>
                            <option value="GH">
                                Ghana
                            </option>
                            <option value="GI">
                                Gibraltar
                            </option>
                            <option value="GR">
                                Greece
                            </option>
                            <option value="GL">
                                Greenland
                            </option>
                            <option value="GD">
                                Grenada
                            </option>
                            <option value="GP">
                                Guadeloupe
                            </option>
                            <option value="GU">
                                Guam
                            </option>
                            <option value="GT">
                                Guatemala
                            </option>
                            <option value="GG">
                                Guernsey
                            </option>
                            <option value="GN">
                                Guinea
                            </option>
                            <option value="GW">
                                Guinea-Bissau
                            </option>
                            <option value="GY">
                                Guyana
                            </option>
                            <option value="HT">
                                Haiti
                            </option>
                            <option value="HM">
                                Heard Island and McDonald Islands
                            </option>
                            <option value="VA">
                                Holy See (Vatican City State)
                            </option>
                            <option value="HN">
                                Honduras
                            </option>
                            <option value="HK">
                                Hong Kong
                            </option>
                            <option value="HU">
                                Hungary
                            </option>
                            <option value="IS">
                                Iceland
                            </option>
                            <option value="IN">
                                India
                            </option>
                            <option value="ID">
                                Indonesia
                            </option>
                            <option value="IR">
                                Iran, Islamic Republic of
                            </option>
                            <option value="IQ">
                                Iraq
                            </option>
                            <option value="IE">
                                Ireland
                            </option>
                            <option value="IM">
                                Isle of Man
                            </option>
                            <option value="IL">
                                Israel
                            </option>
                            <option value="IT">
                                Italy
                            </option>
                            <option value="JM">
                                Jamaica
                            </option>
                            <option value="JP">
                                Japan
                            </option>
                            <option value="JE">
                                Jersey
                            </option>
                            <option value="JO">
                                Jordan
                            </option>
                            <option value="KZ">
                                Kazakhstan
                            </option>
                            <option value="KE">
                                Kenya
                            </option>
                            <option value="KI">
                                Kiribati
                            </option>
                            <option value="KP">
                                Korea, Democratic People's Republic of
                            </option>
                            <option value="KR">
                                Korea, Republic of
                            </option>
                            <option value="KW">
                                Kuwait
                            </option>
                            <option value="KG">
                                Kyrgyzstan
                            </option>
                            <option value="LA">
                                Lao People's Democratic Republic
                            </option>
                            <option value="LV">
                                Latvia
                            </option>
                            <option value="LB">
                                Lebanon
                            </option>
                            <option value="LS">
                                Lesotho
                            </option>
                            <option value="LR">
                                Liberia
                            </option>
                            <option value="LY">
                                Libyan Arab Jamahiriya
                            </option>
                            <option value="LI">
                                Liechtenstein
                            </option>
                            <option value="LT">
                                Lithuania
                            </option>
                            <option value="LU">
                                Luxembourg
                            </option>
                            <option value="MO">
                                Macao
                            </option>
                            <option value="MK">
                                Macedonia, The Former Yugoslav Republic of
                            </option>
                            <option value="MG">
                                Madagascar
                            </option>
                            <option value="MW">
                                Malawi
                            </option>
                            <option value="MY">
                                Malaysia
                            </option>
                            <option value="MV">
                                Maldives
                            </option>
                            <option value="ML">
                                Mali
                            </option>
                            <option value="MT">
                                Malta
                            </option>
                            <option value="MH">
                                Marshall Islands
                            </option>
                            <option value="MQ">
                                Martinique
                            </option>
                            <option value="MR">
                                Mauritania
                            </option>
                            <option value="MU">
                                Mauritius
                            </option>
                            <option value="YT">
                                Mayotte
                            </option>
                            <option value="MX">
                                Mexico
                            </option>
                            <option value="FM">
                                Micronesia, Federated States of
                            </option>
                            <option value="MD">
                                Moldova, Republic of
                            </option>
                            <option value="MC">
                                Monaco
                            </option>
                            <option value="MN">
                                Mongolia
                            </option>
                            <option value="ME">
                                Montenegro
                            </option>
                            <option value="MS">
                                Montserrat
                            </option>
                            <option value="MA">
                                Morocco
                            </option>
                            <option value="MZ">
                                Mozambique
                            </option>
                            <option value="MM">
                                Myanmar
                            </option>
                            <option value="NA">
                                Namibia
                            </option>
                            <option value="NR">
                                Nauru
                            </option>
                            <option value="NP">
                                Nepal
                            </option>
                            <option value="NL">
                                Netherlands
                            </option>
                            <option value="AN">
                                Netherlands Antilles
                            </option>
                            <option value="NC">
                                New Caledonia
                            </option>
                            <option value="NZ">
                                New Zealand
                            </option>
                            <option value="NI">
                                Nicaragua
                            </option>
                            <option value="NE">
                                Niger
                            </option>
                            <option value="NG">
                                Nigeria
                            </option>
                            <option value="NU">
                                Niue
                            </option>
                            <option value="NF">
                                Norfolk Island
                            </option>
                            <option value="MP">
                                Northern Mariana Islands
                            </option>
                            <option value="NO">
                                Norway
                            </option>
                            <option value="OM">
                                Oman
                            </option>
                            <option value="PK">
                                Pakistan
                            </option>
                            <option value="PW">
                                Palau
                            </option>
                            <option value="PS">
                                Palestinian Territory, Occupied
                            </option>
                            <option value="PA">
                                Panama
                            </option>
                            <option value="PG">
                                Papua New Guinea
                            </option>
                            <option value="PY">
                                Paraguay
                            </option>
                            <option value="PE">
                                Peru
                            </option>
                            <option value="PH">
                                Philippines
                            </option>
                            <option value="PN">
                                Pitcairn
                            </option>
                            <option value="PL">
                                Poland
                            </option>
                            <option value="PT">
                                Portugal
                            </option>
                            <option value="PR">
                                Puerto Rico
                            </option>
                            <option value="QA">
                                Qatar
                            </option>
                            <option value="RE">
                                Réunion
                            </option>
                            <option value="RO">
                                Romania
                            </option>
                            <option value="RU">
                                Russian Federation
                            </option>
                            <option value="RW">
                                Rwanda
                            </option>
                            <option value="BL">
                                Saint Barthélemy
                            </option>
                            <option value="SH">
                                Saint Helena, Ascension and Tristan Da Cunha
                            </option>
                            <option value="KN">
                                Saint Kitts and Nevis
                            </option>
                            <option value="LC">
                                Saint Lucia
                            </option>
                            <option value="MF">
                                Saint Martin
                            </option>
                            <option value="PM">
                                Saint Pierre and Miquelon
                            </option>
                            <option value="VC">
                                Saint Vincent and the Grenadines
                            </option>
                            <option value="WS">
                                Samoa
                            </option>
                            <option value="SM">
                                San Marino
                            </option>
                            <option value="ST">
                                Sao Tome and Principe
                            </option>
                            <option value="SA">
                                Saudi Arabia
                            </option>
                            <option value="SN">
                                Senegal
                            </option>
                            <option value="RS">
                                Serbia
                            </option>
                            <option value="SC">
                                Seychelles
                            </option>
                            <option value="SL">
                                Sierra Leone
                            </option>
                            <option value="SG">
                                Singapore
                            </option>
                            <option value="SK">
                                Slovakia
                            </option>
                            <option value="SI">
                                Slovenia
                            </option>
                            <option value="SB">
                                Solomon Islands
                            </option>
                            <option value="SO">
                                Somalia
                            </option>
                            <option value="ZA">
                                South Africa
                            </option>
                            <option value="GS">
                                South Georgia and the South Sandwich Islands
                            </option>
                            <option value="ES">
                                Spain
                            </option>
                            <option value="LK">
                                Sri Lanka
                            </option>
                            <option value="SD">
                                Sudan
                            </option>
                            <option value="SR">
                                Suriname
                            </option>
                            <option value="SJ">
                                Svalbard and Jan Mayen
                            </option>
                            <option value="SZ">
                                Swaziland
                            </option>
                            <option value="SE">
                                Sweden
                            </option>
                            <option value="CH">
                                Switzerland
                            </option>
                            <option value="SY">
                                Syrian Arab Republic
                            </option>
                            <option value="TW">
                                Taiwan, Province of China
                            </option>
                            <option value="TJ">
                                Tajikistan
                            </option>
                            <option value="TZ">
                                Tanzania, United Republic of
                            </option>
                            <option value="TH">
                                Thailand
                            </option>
                            <option value="TL">
                                Timor-Leste
                            </option>
                            <option value="TG">
                                Togo
                            </option>
                            <option value="TK">
                                Tokelau
                            </option>
                            <option value="TO">
                                Tonga
                            </option>
                            <option value="TT">
                                Trinidad and Tobago
                            </option>
                            <option value="TN">
                                Tunisia
                            </option>
                            <option value="TR">
                                Turkey
                            </option>
                            <option value="TM">
                                Turkmenistan
                            </option>
                            <option value="TC">
                                Turks and Caicos Islands
                            </option>
                            <option value="TV">
                                Tuvalu
                            </option>
                            <option value="UG">
                                Uganda
                            </option>
                            <option value="UA">
                                Ukraine
                            </option>
                            <option value="AE">
                                United Arab Emirates
                            </option>
                            <option value="GB">
                                United Kingdom
                            </option>
                            <option value="US">
                                United States
                            </option>
                            <option value="UM">
                                United States Minor Outlying Islands
                            </option>
                            <option value="UY">
                                Uruguay
                            </option>
                            <option value="UZ">
                                Uzbekistan
                            </option>
                            <option value="VU">
                                Vanuatu
                            </option>
                            <option value="VE">
                                Venezuela, Bolivarian Republic of
                            </option>
                            <option value="VN">
                                Viet Nam
                            </option>
                            <option value="VG">
                                Virgin Islands, British
                            </option>
                            <option value="VI">
                                Virgin Islands, U.S.
                            </option>
                            <option value="WF">
                                Wallis and Futuna
                            </option>
                            <option value="EH">
                                Western Sahara
                            </option>
                            <option value="YE">
                                Yemen
                            </option>
                            <option value="ZM">
                                Zambia
                            </option>
                            <option value="ZW">
                                Zimbabwe
                            </option>
                        </select>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="producer_ilarcode"><strong>ILAR Code</strong></label>
                    <div class="input">
                        <input type="text" name="producer_ilarcode" value="" id="producer_ilarcode" alt="Please enter the producer's ILAR-registered laboratory code to be used for developing the official strain designation consistent with the rules and guidelines established by the &lt;a href='http://www.informatics.jax.org/mgihome/nomen/inc.shtml' target='_blank'&gt;International Committee on Standardized Genetic Nomenclature for Mice&lt;/a&gt;. If the producer does not have an ILAR code, it can be registered on-line at &lt;a href='http://dels-old.nas.edu/ilar_n/ilarhome/labcode.shtml' target='_blank'&gt;http://dels-old.nas.edu/ilar_n/ilarhome/labcode.shtml&lt;/a&gt;. The EMMA staff can assist with the registration procedure." />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
            </div>
            <div id="shipper" class="step">
                <h2>
                    Shipper (step 3 of 10)
                </h2>
                <p>
                    Please enter the contact information of the person in charge of shipping the mice to EMMA (e.g., animal facility manager or lab head).
                </p>
                <div>
                    <div>
                        <input type="button" name="fill_shipper_with_submitter_data" id="fill_shipper_with_submitter_data" value="Fill with Submitter data" />&nbsp;<input type="button" name="fill_shipper_with_producer_data" id="fill_shipper_with_producer_data" value="Fill with Producer data" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="shipper_email"><strong>Email<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="shipper_email" value="" id="shipper_email" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="shipper_title"><strong>Title</strong></label>
                    <div class="input">
                        <select name="shipper_title" id="shipper_title">
                            <option value="">
                                &nbsp;
                            </option>
                            <option value="Mr">
                                Mr
                            </option>
                            <option value="Mrs">
                                Mrs
                            </option>
                            <option value="Ms">
                                Ms
                            </option>
                            <option value="Prof">
                                Prof
                            </option>
                            <option value="Dr">
                                Dr
                            </option>
                        </select>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="shipper_firstname"><strong>First name</strong></label>
                    <div class="input">
                        <input type="text" name="shipper_firstname" value="" id="shipper_firstname" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="shipper_lastname"><strong>Last Name<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="shipper_lastname" value="" id="shipper_lastname" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="shipper_tel"><strong>Phone Number<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="shipper_tel" value="" id="shipper_tel" alt="The phone number must begin with + followed by the country code and contain only numbers, hyphens, spaces, or parentheses. An extension must begin with x followed by the extension number, e.g., +1 (234) 567-8900 x123" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="shipper_fax"><strong>Fax Number<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="shipper_fax" value="" id="shipper_fax" alt="The fax number must begin with + followed by the country code and contain only numbers, hyphens, spaces, or parentheses. An extension must begin with x followed by the extension number, e.g., +1 (234) 567-8900 x123" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="shipper_inst"><strong>Institution<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="shipper_inst" value="" id="shipper_inst" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="shipper_dept"><strong>Department</strong></label>
                    <div class="input">
                        <input type="text" name="shipper_dept" value="" id="shipper_dept" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="shipper_addr_1"><strong>Address line 1/Street address<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="shipper_addr_1" value="" id="shipper_addr_1" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="shipper_addr_2"><strong>Address line 2</strong></label>
                    <div class="input">
                        <input type="text" name="shipper_addr_2" value="" id="shipper_addr_2" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="shipper_city"><strong>City<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="shipper_city" value="" id="shipper_city" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="shipper_county"><strong>County/Province/State</strong></label>
                    <div class="input">
                        <input type="text" name="shipper_county" value="" id="shipper_county" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="shipper_postcode"><strong>Postcode/Zipcode<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="shipper_postcode" value="" id="shipper_postcode" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="shipper_country"><strong>Country<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <!--TODO COUNTRIES FROM DATABASE-->
                        <select name="shipper_country" id="shipper_country">
                            <option value="">
                                &nbsp;
                            </option>
                            <option value="AF">
                                Afghanistan
                            </option>
                            <option value="AX">
                                Åland Islands
                            </option>
                            <option value="AL">
                                Albania
                            </option>
                            <option value="DZ">
                                Algeria
                            </option>
                            <option value="AS">
                                American Samoa
                            </option>
                            <option value="AD">
                                Andorra
                            </option>
                            <option value="AO">
                                Angola
                            </option>
                            <option value="AI">
                                Anguilla
                            </option>
                            <option value="AQ">
                                Antarctica
                            </option>
                            <option value="AG">
                                Antigua and Barbuda
                            </option>
                            <option value="AR">
                                Argentina
                            </option>
                            <option value="AM">
                                Armenia
                            </option>
                            <option value="AW">
                                Aruba
                            </option>
                            <option value="AU">
                                Australia
                            </option>
                            <option value="AT">
                                Austria
                            </option>
                            <option value="AZ">
                                Azerbaijan
                            </option>
                            <option value="BS">
                                Bahamas
                            </option>
                            <option value="BH">
                                Bahrain
                            </option>
                            <option value="BD">
                                Bangladesh
                            </option>
                            <option value="BB">
                                Barbados
                            </option>
                            <option value="BY">
                                Belarus
                            </option>
                            <option value="BE">
                                Belgium
                            </option>
                            <option value="BZ">
                                Belize
                            </option>
                            <option value="BJ">
                                Benin
                            </option>
                            <option value="BM">
                                Bermuda
                            </option>
                            <option value="BT">
                                Bhutan
                            </option>
                            <option value="BO">
                                Bolivia, Plurinational State of
                            </option>
                            <option value="BA">
                                Bosnia and Herzegovina
                            </option>
                            <option value="BW">
                                Botswana
                            </option>
                            <option value="BV">
                                Bouvet Island
                            </option>
                            <option value="BR">
                                Brazil
                            </option>
                            <option value="IO">
                                British Indian Ocean Territory
                            </option>
                            <option value="BN">
                                Brunei Darussalam
                            </option>
                            <option value="BG">
                                Bulgaria
                            </option>
                            <option value="BF">
                                Burkina Faso
                            </option>
                            <option value="BI">
                                Burundi
                            </option>
                            <option value="KH">
                                Cambodia
                            </option>
                            <option value="CM">
                                Cameroon
                            </option>
                            <option value="CA">
                                Canada
                            </option>
                            <option value="CV">
                                Cape Verde
                            </option>
                            <option value="KY">
                                Cayman Islands
                            </option>
                            <option value="CF">
                                Central African Republic
                            </option>
                            <option value="TD">
                                Chad
                            </option>
                            <option value="CL">
                                Chile
                            </option>
                            <option value="CN">
                                China
                            </option>
                            <option value="CX">
                                Christmas Island
                            </option>
                            <option value="CC">
                                Cocos (Keeling) Islands
                            </option>
                            <option value="CO">
                                Colombia
                            </option>
                            <option value="KM">
                                Comoros
                            </option>
                            <option value="CG">
                                Congo
                            </option>
                            <option value="CD">
                                Congo, The Democratic Republic of the
                            </option>
                            <option value="CK">
                                Cook Islands
                            </option>
                            <option value="CR">
                                Costa Rica
                            </option>
                            <option value="CI">
                                Côte d'Ivoire
                            </option>
                            <option value="HR">
                                Croatia
                            </option>
                            <option value="CU">
                                Cuba
                            </option>
                            <option value="CY">
                                Cyprus
                            </option>
                            <option value="CZ">
                                Czech Republic
                            </option>
                            <option value="DK">
                                Denmark
                            </option>
                            <option value="DJ">
                                Djibouti
                            </option>
                            <option value="DM">
                                Dominica
                            </option>
                            <option value="DO">
                                Dominican Republic
                            </option>
                            <option value="EC">
                                Ecuador
                            </option>
                            <option value="EG">
                                Egypt
                            </option>
                            <option value="SV">
                                El Salvador
                            </option>
                            <option value="GQ">
                                Equatorial Guinea
                            </option>
                            <option value="ER">
                                Eritrea
                            </option>
                            <option value="EE">
                                Estonia
                            </option>
                            <option value="ET">
                                Ethiopia
                            </option>
                            <option value="FK">
                                Falkland Islands (Malvinas)
                            </option>
                            <option value="FO">
                                Faroe Islands
                            </option>
                            <option value="FJ">
                                Fiji
                            </option>
                            <option value="FI">
                                Finland
                            </option>
                            <option value="FR">
                                France
                            </option>
                            <option value="GF">
                                French Guiana
                            </option>
                            <option value="PF">
                                French Polynesia
                            </option>
                            <option value="TF">
                                French Southern Territories
                            </option>
                            <option value="GA">
                                Gabon
                            </option>
                            <option value="GM">
                                Gambia
                            </option>
                            <option value="GE">
                                Georgia
                            </option>
                            <option value="DE">
                                Germany
                            </option>
                            <option value="GH">
                                Ghana
                            </option>
                            <option value="GI">
                                Gibraltar
                            </option>
                            <option value="GR">
                                Greece
                            </option>
                            <option value="GL">
                                Greenland
                            </option>
                            <option value="GD">
                                Grenada
                            </option>
                            <option value="GP">
                                Guadeloupe
                            </option>
                            <option value="GU">
                                Guam
                            </option>
                            <option value="GT">
                                Guatemala
                            </option>
                            <option value="GG">
                                Guernsey
                            </option>
                            <option value="GN">
                                Guinea
                            </option>
                            <option value="GW">
                                Guinea-Bissau
                            </option>
                            <option value="GY">
                                Guyana
                            </option>
                            <option value="HT">
                                Haiti
                            </option>
                            <option value="HM">
                                Heard Island and McDonald Islands
                            </option>
                            <option value="VA">
                                Holy See (Vatican City State)
                            </option>
                            <option value="HN">
                                Honduras
                            </option>
                            <option value="HK">
                                Hong Kong
                            </option>
                            <option value="HU">
                                Hungary
                            </option>
                            <option value="IS">
                                Iceland
                            </option>
                            <option value="IN">
                                India
                            </option>
                            <option value="ID">
                                Indonesia
                            </option>
                            <option value="IR">
                                Iran, Islamic Republic of
                            </option>
                            <option value="IQ">
                                Iraq
                            </option>
                            <option value="IE">
                                Ireland
                            </option>
                            <option value="IM">
                                Isle of Man
                            </option>
                            <option value="IL">
                                Israel
                            </option>
                            <option value="IT">
                                Italy
                            </option>
                            <option value="JM">
                                Jamaica
                            </option>
                            <option value="JP">
                                Japan
                            </option>
                            <option value="JE">
                                Jersey
                            </option>
                            <option value="JO">
                                Jordan
                            </option>
                            <option value="KZ">
                                Kazakhstan
                            </option>
                            <option value="KE">
                                Kenya
                            </option>
                            <option value="KI">
                                Kiribati
                            </option>
                            <option value="KP">
                                Korea, Democratic People's Republic of
                            </option>
                            <option value="KR">
                                Korea, Republic of
                            </option>
                            <option value="KW">
                                Kuwait
                            </option>
                            <option value="KG">
                                Kyrgyzstan
                            </option>
                            <option value="LA">
                                Lao People's Democratic Republic
                            </option>
                            <option value="LV">
                                Latvia
                            </option>
                            <option value="LB">
                                Lebanon
                            </option>
                            <option value="LS">
                                Lesotho
                            </option>
                            <option value="LR">
                                Liberia
                            </option>
                            <option value="LY">
                                Libyan Arab Jamahiriya
                            </option>
                            <option value="LI">
                                Liechtenstein
                            </option>
                            <option value="LT">
                                Lithuania
                            </option>
                            <option value="LU">
                                Luxembourg
                            </option>
                            <option value="MO">
                                Macao
                            </option>
                            <option value="MK">
                                Macedonia, The Former Yugoslav Republic of
                            </option>
                            <option value="MG">
                                Madagascar
                            </option>
                            <option value="MW">
                                Malawi
                            </option>
                            <option value="MY">
                                Malaysia
                            </option>
                            <option value="MV">
                                Maldives
                            </option>
                            <option value="ML">
                                Mali
                            </option>
                            <option value="MT">
                                Malta
                            </option>
                            <option value="MH">
                                Marshall Islands
                            </option>
                            <option value="MQ">
                                Martinique
                            </option>
                            <option value="MR">
                                Mauritania
                            </option>
                            <option value="MU">
                                Mauritius
                            </option>
                            <option value="YT">
                                Mayotte
                            </option>
                            <option value="MX">
                                Mexico
                            </option>
                            <option value="FM">
                                Micronesia, Federated States of
                            </option>
                            <option value="MD">
                                Moldova, Republic of
                            </option>
                            <option value="MC">
                                Monaco
                            </option>
                            <option value="MN">
                                Mongolia
                            </option>
                            <option value="ME">
                                Montenegro
                            </option>
                            <option value="MS">
                                Montserrat
                            </option>
                            <option value="MA">
                                Morocco
                            </option>
                            <option value="MZ">
                                Mozambique
                            </option>
                            <option value="MM">
                                Myanmar
                            </option>
                            <option value="NA">
                                Namibia
                            </option>
                            <option value="NR">
                                Nauru
                            </option>
                            <option value="NP">
                                Nepal
                            </option>
                            <option value="NL">
                                Netherlands
                            </option>
                            <option value="AN">
                                Netherlands Antilles
                            </option>
                            <option value="NC">
                                New Caledonia
                            </option>
                            <option value="NZ">
                                New Zealand
                            </option>
                            <option value="NI">
                                Nicaragua
                            </option>
                            <option value="NE">
                                Niger
                            </option>
                            <option value="NG">
                                Nigeria
                            </option>
                            <option value="NU">
                                Niue
                            </option>
                            <option value="NF">
                                Norfolk Island
                            </option>
                            <option value="MP">
                                Northern Mariana Islands
                            </option>
                            <option value="NO">
                                Norway
                            </option>
                            <option value="OM">
                                Oman
                            </option>
                            <option value="PK">
                                Pakistan
                            </option>
                            <option value="PW">
                                Palau
                            </option>
                            <option value="PS">
                                Palestinian Territory, Occupied
                            </option>
                            <option value="PA">
                                Panama
                            </option>
                            <option value="PG">
                                Papua New Guinea
                            </option>
                            <option value="PY">
                                Paraguay
                            </option>
                            <option value="PE">
                                Peru
                            </option>
                            <option value="PH">
                                Philippines
                            </option>
                            <option value="PN">
                                Pitcairn
                            </option>
                            <option value="PL">
                                Poland
                            </option>
                            <option value="PT">
                                Portugal
                            </option>
                            <option value="PR">
                                Puerto Rico
                            </option>
                            <option value="QA">
                                Qatar
                            </option>
                            <option value="RE">
                                Réunion
                            </option>
                            <option value="RO">
                                Romania
                            </option>
                            <option value="RU">
                                Russian Federation
                            </option>
                            <option value="RW">
                                Rwanda
                            </option>
                            <option value="BL">
                                Saint Barthélemy
                            </option>
                            <option value="SH">
                                Saint Helena, Ascension and Tristan Da Cunha
                            </option>
                            <option value="KN">
                                Saint Kitts and Nevis
                            </option>
                            <option value="LC">
                                Saint Lucia
                            </option>
                            <option value="MF">
                                Saint Martin
                            </option>
                            <option value="PM">
                                Saint Pierre and Miquelon
                            </option>
                            <option value="VC">
                                Saint Vincent and the Grenadines
                            </option>
                            <option value="WS">
                                Samoa
                            </option>
                            <option value="SM">
                                San Marino
                            </option>
                            <option value="ST">
                                Sao Tome and Principe
                            </option>
                            <option value="SA">
                                Saudi Arabia
                            </option>
                            <option value="SN">
                                Senegal
                            </option>
                            <option value="RS">
                                Serbia
                            </option>
                            <option value="SC">
                                Seychelles
                            </option>
                            <option value="SL">
                                Sierra Leone
                            </option>
                            <option value="SG">
                                Singapore
                            </option>
                            <option value="SK">
                                Slovakia
                            </option>
                            <option value="SI">
                                Slovenia
                            </option>
                            <option value="SB">
                                Solomon Islands
                            </option>
                            <option value="SO">
                                Somalia
                            </option>
                            <option value="ZA">
                                South Africa
                            </option>
                            <option value="GS">
                                South Georgia and the South Sandwich Islands
                            </option>
                            <option value="ES">
                                Spain
                            </option>
                            <option value="LK">
                                Sri Lanka
                            </option>
                            <option value="SD">
                                Sudan
                            </option>
                            <option value="SR">
                                Suriname
                            </option>
                            <option value="SJ">
                                Svalbard and Jan Mayen
                            </option>
                            <option value="SZ">
                                Swaziland
                            </option>
                            <option value="SE">
                                Sweden
                            </option>
                            <option value="CH">
                                Switzerland
                            </option>
                            <option value="SY">
                                Syrian Arab Republic
                            </option>
                            <option value="TW">
                                Taiwan, Province of China
                            </option>
                            <option value="TJ">
                                Tajikistan
                            </option>
                            <option value="TZ">
                                Tanzania, United Republic of
                            </option>
                            <option value="TH">
                                Thailand
                            </option>
                            <option value="TL">
                                Timor-Leste
                            </option>
                            <option value="TG">
                                Togo
                            </option>
                            <option value="TK">
                                Tokelau
                            </option>
                            <option value="TO">
                                Tonga
                            </option>
                            <option value="TT">
                                Trinidad and Tobago
                            </option>
                            <option value="TN">
                                Tunisia
                            </option>
                            <option value="TR">
                                Turkey
                            </option>
                            <option value="TM">
                                Turkmenistan
                            </option>
                            <option value="TC">
                                Turks and Caicos Islands
                            </option>
                            <option value="TV">
                                Tuvalu
                            </option>
                            <option value="UG">
                                Uganda
                            </option>
                            <option value="UA">
                                Ukraine
                            </option>
                            <option value="AE">
                                United Arab Emirates
                            </option>
                            <option value="GB">
                                United Kingdom
                            </option>
                            <option value="US">
                                United States
                            </option>
                            <option value="UM">
                                United States Minor Outlying Islands
                            </option>
                            <option value="UY">
                                Uruguay
                            </option>
                            <option value="UZ">
                                Uzbekistan
                            </option>
                            <option value="VU">
                                Vanuatu
                            </option>
                            <option value="VE">
                                Venezuela, Bolivarian Republic of
                            </option>
                            <option value="VN">
                                Viet Nam
                            </option>
                            <option value="VG">
                                Virgin Islands, British
                            </option>
                            <option value="VI">
                                Virgin Islands, U.S.
                            </option>
                            <option value="WF">
                                Wallis and Futuna
                            </option>
                            <option value="EH">
                                Western Sahara
                            </option>
                            <option value="YE">
                                Yemen
                            </option>
                            <option value="ZM">
                                Zambia
                            </option>
                            <option value="ZW">
                                Zimbabwe
                            </option>
                        </select>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
            </div>
            <div id="genotype" class="step">
                <h2>
                    Genotype (step 4 of 10)
                </h2>
                <p>
                    Please enter the genotype information of the mouse mutant strain you want to deposit in EMMA. A mutant strain is defined by its specific mutation(s) AND genetic background. Therefore strains with the same mutation(s) but different genetic backgrounds require distinct names and consequently separate submissions.
                </p>
                <p>
                    For the definitions of terms <a target="IMSR" href="http://www.informatics.jax.org/imsr/glossary.jsp">see the IMSR glossary</a>. For gene/allele symbols and identifiers please <a target='MGI' href='http://www.informatics.jax.org/javawi2/servlet/WIFetch?page=markerQF'>search MGI</a>.
                </p>
                <div class="field">
                    <label class="label" for="strain_name"><strong>Strain name<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <input type="text" name="strain_name" value="" id="strain_name" alt="Please enter the mutant mouse strain name. If you are submitting lines on more than one background, please include the background in the strain name and submit each line separately." />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="genetic_descr"><strong>Genetic description<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <textarea name="genetic_descr" id="genetic_descr" cols="50" rows="5" alt="Please enter a short description of the mutant mouse strain genotype (this will be used in the public web listing, see an example).">
                        </textarea>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="current_backg"><strong>Current genetic background<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <!--TODO BACKGROUNDS FROM DATABASE PRODUCED LIST-->
                        <select name="current_backg" id="current_backg" alt="Please specify the current genetic background of the strain that is being submitted.">
                            <option value="">
                                &nbsp;
                            </option>
                            <option value="129">
                                129
                            </option>
                            <option value="129/Sv">
                                129/Sv
                            </option>
                            <option value="129/SvEv">
                                129/SvEv
                            </option>
                            <option value="129/SvHsd-Ter">
                                129/SvHsd-Ter
                            </option>
                            <option value="129P1/ReJ">
                                129P1/ReJ
                            </option>
                            <option value="129P2/OlaHsd">
                                129P2/OlaHsd
                            </option>
                            <option value="129P2/OlaHsd x C57BL/6">
                                129P2/OlaHsd x C57BL/6
                            </option>
                            <option value="129S2/SvPas">
                                129S2/SvPas
                            </option>
                            <option value="129S4/SvJae">
                                129S4/SvJae
                            </option>
                            <option value="129S5/SvEvBrd">
                                129S5/SvEvBrd
                            </option>
                            <option value="129S6/SvEvTac">
                                129S6/SvEvTac
                            </option>
                            <option value="129S7/SvEvBrd-Hprt&lt;b-m2&gt;">
                                129S7/SvEvBrd-Hprt&lt;b-m2&gt;
                            </option>
                            <option value="129X1/SvJ">
                                129X1/SvJ
                            </option>
                            <option value="3H1">
                                3H1
                            </option>
                            <option value="A/J">
                                A/J
                            </option>
                            <option value="BALB/c">
                                BALB/c
                            </option>
                            <option value="BALB/c x DBA/2">
                                BALB/c x DBA/2
                            </option>
                            <option value="BALB/cAnN">
                                BALB/cAnN
                            </option>
                            <option value="BALB/cByJIco">
                                BALB/cByJIco
                            </option>
                            <option value="BALB/cOrl">
                                BALB/cOrl
                            </option>
                            <option value="C3Fe">
                                C3Fe
                            </option>
                            <option value="C3H">
                                C3H
                            </option>
                            <option value="C3H/El">
                                C3H/El
                            </option>
                            <option value="C3H/He">
                                C3H/He
                            </option>
                            <option value="C3H/HeH">
                                C3H/HeH
                            </option>
                            <option value="C3H/HeH x 101/H">
                                C3H/HeH x 101/H
                            </option>
                            <option value="C3H/HeJ">
                                C3H/HeJ
                            </option>
                            <option value="C3H/HePas">
                                C3H/HePas
                            </option>
                            <option value="C3HeB">
                                C3HeB
                            </option>
                            <option value="C3HeB/FeJ">
                                C3HeB/FeJ
                            </option>
                            <option value="C3HeB/FeJOrl">
                                C3HeB/FeJOrl
                            </option>
                            <option value="C57BL">
                                C57BL
                            </option>
                            <option value="C57BL x DBA2">
                                C57BL x DBA2
                            </option>
                            <option value="C57BL/10 x C57BR/CD">
                                C57BL/10 x C57BR/CD
                            </option>
                            <option value="C57BL/10 x DBA/2">
                                C57BL/10 x DBA/2
                            </option>
                            <option value="C57BL/10Orl">
                                C57BL/10Orl
                            </option>
                            <option value="C57BL/10ScSn">
                                C57BL/10ScSn
                            </option>
                            <option value="C57BL/6">
                                C57BL/6
                            </option>
                            <option value="C57BL/6 x CBA">
                                C57BL/6 x CBA
                            </option>
                            <option value="C57BL/6 x DBA2">
                                C57BL/6 x DBA2
                            </option>
                            <option value="C57BL/6 x SJL">
                                C57BL/6 x SJL
                            </option>
                            <option value="C57BL/6.129">
                                C57BL/6.129
                            </option>
                            <option value="C57BL/6.129/Sv">
                                C57BL/6.129/Sv
                            </option>
                            <option value="C57BL/6.129/SvEv">
                                C57BL/6.129/SvEv
                            </option>
                            <option value="C57BL/6.129P2/OlaHsd">
                                C57BL/6.129P2/OlaHsd
                            </option>
                            <option value="C57BL/6.129S2/SvPas">
                                C57BL/6.129S2/SvPas
                            </option>
                            <option value="C57BL/6.129S4/SvJae">
                                C57BL/6.129S4/SvJae
                            </option>
                            <option value="C57BL/6.129S5/SvEvBrd">
                                C57BL/6.129S5/SvEvBrd
                            </option>
                            <option value="C57BL/6.A2G">
                                C57BL/6.A2G
                            </option>
                            <option value="C57BL/6.CBA">
                                C57BL/6.CBA
                            </option>
                            <option value="C57BL/6.CD1">
                                C57BL/6.CD1
                            </option>
                            <option value="C57BL/6.Cg">
                                C57BL/6.Cg
                            </option>
                            <option value="C57BL/6.FVB">
                                C57BL/6.FVB
                            </option>
                            <option value="C57BL/6;129">
                                C57BL/6;129
                            </option>
                            <option value="C57BL/6;129/Sv">
                                C57BL/6;129/Sv
                            </option>
                            <option value="C57BL/6;129/SvEv">
                                C57BL/6;129/SvEv
                            </option>
                            <option value="C57BL/6;129P2/OlaHsd">
                                C57BL/6;129P2/OlaHsd
                            </option>
                            <option value="C57BL/6;129S5/SvEvBrd">
                                C57BL/6;129S5/SvEvBrd
                            </option>
                            <option value="C57BL/6;129S7/SvEvBrd-Hprt&lt;b-m2&gt;">
                                C57BL/6;129S7/SvEvBrd-Hprt&lt;b-m2&gt;
                            </option>
                            <option value="C57BL/6;129X1/SvJ">
                                C57BL/6;129X1/SvJ
                            </option>
                            <option value="C57BL/6;Cg">
                                C57BL/6;Cg
                            </option>
                            <option value="C57BL/6;SJL">
                                C57BL/6;SJL
                            </option>
                            <option value="C57BL/6Apb">
                                C57BL/6Apb
                            </option>
                            <option value="C57BL/6ChR">
                                C57BL/6ChR
                            </option>
                            <option value="C57BL/6J">
                                C57BL/6J
                            </option>
                            <option value="C57BL/6J x 129P2/OlaHsd">
                                C57BL/6J x 129P2/OlaHsd
                            </option>
                            <option value="C57BL/6J x 129X1/SvJ">
                                C57BL/6J x 129X1/SvJ
                            </option>
                            <option value="C57BL/6JHsd">
                                C57BL/6JHsd
                            </option>
                            <option value="C57BL/6JOlaHsd">
                                C57BL/6JOlaHsd
                            </option>
                            <option value="C57BL/6N">
                                C57BL/6N
                            </option>
                            <option value="C57BL/6NCrl">
                                C57BL/6NCrl
                            </option>
                            <option value="C57BL/6Orl">
                                C57BL/6Orl
                            </option>
                            <option value="C57BL/6Rcc">
                                C57BL/6Rcc
                            </option>
                            <option value="C57BL/Ka">
                                C57BL/Ka
                            </option>
                            <option value="C57BL/KaOrl">
                                C57BL/KaOrl
                            </option>
                            <option value="C57BR/CD">
                                C57BR/CD
                            </option>
                            <option value="CBA">
                                CBA
                            </option>
                            <option value="CBA x C57BL/6">
                                CBA x C57BL/6
                            </option>
                            <option value="CBA/Ca">
                                CBA/Ca
                            </option>
                            <option value="CD1">
                                CD1
                            </option>
                            <option value="CD2">
                                CD2
                            </option>
                            <option value="DBA/1">
                                DBA/1
                            </option>
                            <option value="DBA/2">
                                DBA/2
                            </option>
                            <option value="DDK/Pas">
                                DDK/Pas
                            </option>
                            <option value="DW">
                                DW
                            </option>
                            <option value="FVB">
                                FVB
                            </option>
                            <option value="FVB/N">
                                FVB/N
                            </option>
                            <option value="FVB/N x C57BL/6">
                                FVB/N x C57BL/6
                            </option>
                            <option value="GRS/A">
                                GRS/A
                            </option>
                            <option value="MF1">
                                MF1
                            </option>
                            <option value="MPI">
                                MPI
                            </option>
                            <option value="NMRI">
                                NMRI
                            </option>
                            <option value="NOD">
                                NOD
                            </option>
                            <option value="SJL">
                                SJL
                            </option>
                            <option value="(101/El x C3H/El)F1">
                                (101/El x C3H/El)F1
                            </option>
                            <option value="(101/H x C3H/HeH)F1">
                                (101/H x C3H/HeH)F1
                            </option>
                            <option value="(C3H/HeH x 101/H)F1">
                                (C3H/HeH x 101/H)F1
                            </option>
                            <option value="(C57BL/6 x CBA)F1">
                                (C57BL/6 x CBA)F1
                            </option>
                            <option value="(C57BL/6 x DBA/2)F1">
                                (C57BL/6 x DBA/2)F1
                            </option>
                            <option value="(C57BL/6J x C3H)F1">
                                (C57BL/6J x C3H)F1
                            </option>
                            <option value="(C57BL/6J x CBA/Ca)F1">
                                (C57BL/6J x CBA/Ca)F1
                            </option>
                            <option value="mixed">
                                Mixed
                            </option>
                            <option value="not maintained in a specific background">
                                Not maintained in a specific background
                            </option>
                            <option value="unknown">
                                Unknown
                            </option>
                            <option value="house mouse">
                                House mouse
                            </option>
                            <option value="Other">
                                Other (please specify)
                            </option>
                        </select>&nbsp;<input type="text" name="current_backg_text" id="current_backg_text" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="strain_name"><strong>Number of generations backcrossed</strong></label>
                    <div class="input">
                        <input type="text" name="backcrosses" value="" id="backcrosses" alt="Please enter the number of generations backcrossed to background strain (if applicable and known)." />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="strain_name"><strong>Number of generations sib-mated</strong></label>
                    <div class="input">
                        <input type="text" name="sibmatings" value="" id="sibmatings" alt="Please enter the number of generations mated to a sibling (since inception or subsequent to any outcrosses or backcrosses and if applicable and known)." />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="breeding_history"><strong>Breeding history</strong></label>
                    <div class="input">
                        <textarea name="breeding_history" id="breeding_history" cols="50" rows="5" alt="Please describe the breeding history (outcrosses, backcrosses, intercrosses, incrosses) from the original founder strain to the current genetic background.">
                        </textarea>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <fieldset class="mutation">
                    <legend>Mutation</legend>
                    <div class="field mutation_type">
                        <label class="label" for="mutation_type_0"><strong>Type<sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <!--TODO MUTS FROM DATABASE-->
                            <select name="mutation_type_0" id="mutation_type_0">
                                <option value="">
                                    &nbsp;
                                </option>
                                <option value="CH">
                                    Chromosomal Anomaly
                                </option>
                                <option value="GT">
                                    Gene-trap
                                </option>
                                <option value="IN">
                                    Induced
                                </option>
                                <option value="SP">
                                    Spontaneous
                                </option>
                                <option value="TG">
                                    Transgenic
                                </option>
                                <option value="TM">
                                    Targeted
                                </option>
                                <option value="XX">
                                    Undefined
                                </option>
                            </select>
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field mutation_subtype conditional CH">
                        <label class="label" for="mutation_subtype_ch_0"><strong>Subtype<sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <!--TODO MUTS SUBS FROM DATABASE-->
                            <select name="mutation_subtype_ch_0" id="mutation_subtype_ch_0">
                                <option value="">
                                    &nbsp;
                                </option>
                                <option value="INS">
                                    Insertion
                                </option>
                                <option value="INV">
                                    Inversion
                                </option>
                                <option value="DEL">
                                    Deletion
                                </option>
                                <option value="DUP">
                                    Duplication
                                </option>
                                <option value="TRL">
                                    Translocation
                                </option>
                                <option value="TRP">
                                    Transposition
                                </option>
                            </select>
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field mutation_subtype conditional IN">
                        <label class="label" for="mutation_subtype_in_0"><strong>Subtype<sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <select name="mutation_subtype_in_0" id="mutation_subtype_in_0">
                                <option value="">
                                    &nbsp;
                                </option>
                                <option value="CH">
                                    Chemical
                                </option>
                                <option value="RD">
                                    Radiation
                                </option>
                            </select>
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field mutation_subtype conditional TM">
                        <label class="label" for="mutation_subtype_tm_0"><strong>Subtype<sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <!--TODO MUTS SUBS FROM DATABASE-->
                            <select name="mutation_subtype_tm_0" id="mutation_subtype_tm_0">
                                <option value="">
                                    &nbsp;
                                </option>
                                <option value="KO">
                                    Knock-out
                                </option>
                                <option value="KI">
                                    Knock-in
                                </option>
                                <option value="PM">
                                    Point mutation
                                </option>
                                <option value="CM">
                                    Conditional mutation
                                </option>
                                <option value="OTH">
                                    Other targeted mutation
                                </option>
                            </select>
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field mutation_transgene_mgi_symbol conditional TG">
                        <label class="label" for="mutation_transgene_mgi_symbol_0"><strong>Transgene</strong></label>
                        <div class="input">
                            <input type="text" name="mutation_transgene_mgi_symbol_0" value="" id="mutation_transgene_mgi_symbol_0" />
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field mutation_gene_mgi_symbol conditional CH GT IN SP TM XX">
                        <label class="label" for="mutation_gene_mgi_symbol_0"><strong>Affected gene</strong></label>
                        <div class="input">
                            <input type="text" name="mutation_gene_mgi_symbol_0" value="" id="mutation_gene_mgi_symbol_0" />
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field mutation_allele_mgi_symbol conditional CH GT IN SP TM XX">
                        <label class="label" for="mutation_allele_mgi_symbol_0"><strong>Affected allele</strong></label>
                        <div class="input">
                            <input type="text" name="mutation_allele_mgi_symbol_0" value="" id="mutation_allele_mgi_symbol_0" />
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field mutation_chrom conditional CH GT IN SP TM XX">
                        <label class="label" for="mutation_chrom_0"><strong>Affected chromosome</strong></label>
                        <div class="input">
                            <select name="mutation_chrom_0" id="mutation_chrom_0">
                                <option value="">
                                    &nbsp;
                                </option>
                                <option value="1">
                                    1
                                </option>
                                <option value="2">
                                    2
                                </option>
                                <option value="3">
                                    3
                                </option>
                                <option value="4">
                                    4
                                </option>
                                <option value="5">
                                    5
                                </option>
                                <option value="6">
                                    6
                                </option>
                                <option value="7">
                                    7
                                </option>
                                <option value="8">
                                    8
                                </option>
                                <option value="9">
                                    9
                                </option>
                                <option value="10">
                                    10
                                </option>
                                <option value="11">
                                    11
                                </option>
                                <option value="12">
                                    12
                                </option>
                                <option value="13">
                                    13
                                </option>
                                <option value="14">
                                    14
                                </option>
                                <option value="15">
                                    15
                                </option>
                                <option value="16">
                                    16
                                </option>
                                <option value="17">
                                    17
                                </option>
                                <option value="18">
                                    18
                                </option>
                                <option value="19">
                                    19
                                </option>
                                <option value="X">
                                    X
                                </option>
                                <option value="Y">
                                    Y
                                </option>
                            </select>
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field mutation_dominance_pattern">
                        <label class="label" for="mutation_dominance_pattern_0"><strong>Dominance pattern</strong></label>
                        <div class="input">
                            <select name="mutation_dominance_pattern_0" id="mutation_dominance_pattern_0">
                                <option value="">
                                    &nbsp;
                                </option>
                                <option value="recessive">
                                    recessive
                                </option>
                                <option value="dominant">
                                    dominant
                                </option>
                                <option value="codominant">
                                    codominant
                                </option>
                                <option value="semidominant">
                                    semidominant
                                </option>
                                <option value="X-linked">
                                    X-linked
                                </option>
                            </select>
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field mutation_original_backg">
                        <label class="label" for="mutation_original_backg_0"><strong>Original genetic background</strong></label>
                        <div class="input">
                            <!--TODO BACKGROUNDS FROM DATABASE PRODUCED LIST-->
                            <select name="mutation_original_backg_0" id="mutation_original_backg_0" alt="Please specify the genetic background of the founder strain (e.g., the genetic background of ES cells or oocytes used to generate a knockout or transgenic mouse respectively).">
                                <option value="">
                                    &nbsp;
                                </option>
                                <option value="129">
                                    129
                                </option>
                                <option value="129/Sv">
                                    129/Sv
                                </option>
                                <option value="129/SvEv">
                                    129/SvEv
                                </option>
                                <option value="129/SvHsd-Ter">
                                    129/SvHsd-Ter
                                </option>
                                <option value="129P1/ReJ">
                                    129P1/ReJ
                                </option>
                                <option value="129P2/OlaHsd">
                                    129P2/OlaHsd
                                </option>
                                <option value="129P2/OlaHsd x C57BL/6">
                                    129P2/OlaHsd x C57BL/6
                                </option>
                                <option value="129S2/SvPas">
                                    129S2/SvPas
                                </option>
                                <option value="129S4/SvJae">
                                    129S4/SvJae
                                </option>
                                <option value="129S5/SvEvBrd">
                                    129S5/SvEvBrd
                                </option>
                                <option value="129S6/SvEvTac">
                                    129S6/SvEvTac
                                </option>
                                <option value="129S7/SvEvBrd-Hprt&lt;b-m2&gt;">
                                    129S7/SvEvBrd-Hprt&lt;b-m2&gt;
                                </option>
                                <option value="129X1/SvJ">
                                    129X1/SvJ
                                </option>
                                <option value="3H1">
                                    3H1
                                </option>
                                <option value="A/J">
                                    A/J
                                </option>
                                <option value="BALB/c">
                                    BALB/c
                                </option>
                                <option value="BALB/c x DBA/2">
                                    BALB/c x DBA/2
                                </option>
                                <option value="BALB/cAnN">
                                    BALB/cAnN
                                </option>
                                <option value="BALB/cByJIco">
                                    BALB/cByJIco
                                </option>
                                <option value="BALB/cOrl">
                                    BALB/cOrl
                                </option>
                                <option value="C3Fe">
                                    C3Fe
                                </option>
                                <option value="C3H">
                                    C3H
                                </option>
                                <option value="C3H/El">
                                    C3H/El
                                </option>
                                <option value="C3H/He">
                                    C3H/He
                                </option>
                                <option value="C3H/HeH">
                                    C3H/HeH
                                </option>
                                <option value="C3H/HeH x 101/H">
                                    C3H/HeH x 101/H
                                </option>
                                <option value="C3H/HeJ">
                                    C3H/HeJ
                                </option>
                                <option value="C3H/HePas">
                                    C3H/HePas
                                </option>
                                <option value="C3HeB">
                                    C3HeB
                                </option>
                                <option value="C3HeB/FeJ">
                                    C3HeB/FeJ
                                </option>
                                <option value="C3HeB/FeJOrl">
                                    C3HeB/FeJOrl
                                </option>
                                <option value="C57BL">
                                    C57BL
                                </option>
                                <option value="C57BL x DBA2">
                                    C57BL x DBA2
                                </option>
                                <option value="C57BL/10 x C57BR/CD">
                                    C57BL/10 x C57BR/CD
                                </option>
                                <option value="C57BL/10 x DBA/2">
                                    C57BL/10 x DBA/2
                                </option>
                                <option value="C57BL/10Orl">
                                    C57BL/10Orl
                                </option>
                                <option value="C57BL/10ScSn">
                                    C57BL/10ScSn
                                </option>
                                <option value="C57BL/6">
                                    C57BL/6
                                </option>
                                <option value="C57BL/6 x CBA">
                                    C57BL/6 x CBA
                                </option>
                                <option value="C57BL/6 x DBA2">
                                    C57BL/6 x DBA2
                                </option>
                                <option value="C57BL/6 x SJL">
                                    C57BL/6 x SJL
                                </option>
                                <option value="C57BL/6.129">
                                    C57BL/6.129
                                </option>
                                <option value="C57BL/6.129/Sv">
                                    C57BL/6.129/Sv
                                </option>
                                <option value="C57BL/6.129/SvEv">
                                    C57BL/6.129/SvEv
                                </option>
                                <option value="C57BL/6.129P2/OlaHsd">
                                    C57BL/6.129P2/OlaHsd
                                </option>
                                <option value="C57BL/6.129S2/SvPas">
                                    C57BL/6.129S2/SvPas
                                </option>
                                <option value="C57BL/6.129S4/SvJae">
                                    C57BL/6.129S4/SvJae
                                </option>
                                <option value="C57BL/6.129S5/SvEvBrd">
                                    C57BL/6.129S5/SvEvBrd
                                </option>
                                <option value="C57BL/6.A2G">
                                    C57BL/6.A2G
                                </option>
                                <option value="C57BL/6.CBA">
                                    C57BL/6.CBA
                                </option>
                                <option value="C57BL/6.CD1">
                                    C57BL/6.CD1
                                </option>
                                <option value="C57BL/6.Cg">
                                    C57BL/6.Cg
                                </option>
                                <option value="C57BL/6.FVB">
                                    C57BL/6.FVB
                                </option>
                                <option value="C57BL/6;129">
                                    C57BL/6;129
                                </option>
                                <option value="C57BL/6;129/Sv">
                                    C57BL/6;129/Sv
                                </option>
                                <option value="C57BL/6;129/SvEv">
                                    C57BL/6;129/SvEv
                                </option>
                                <option value="C57BL/6;129P2/OlaHsd">
                                    C57BL/6;129P2/OlaHsd
                                </option>
                                <option value="C57BL/6;129S5/SvEvBrd">
                                    C57BL/6;129S5/SvEvBrd
                                </option>
                                <option value="C57BL/6;129S7/SvEvBrd-Hprt&lt;b-m2&gt;">
                                    C57BL/6;129S7/SvEvBrd-Hprt&lt;b-m2&gt;
                                </option>
                                <option value="C57BL/6;129X1/SvJ">
                                    C57BL/6;129X1/SvJ
                                </option>
                                <option value="C57BL/6;Cg">
                                    C57BL/6;Cg
                                </option>
                                <option value="C57BL/6;SJL">
                                    C57BL/6;SJL
                                </option>
                                <option value="C57BL/6Apb">
                                    C57BL/6Apb
                                </option>
                                <option value="C57BL/6ChR">
                                    C57BL/6ChR
                                </option>
                                <option value="C57BL/6J">
                                    C57BL/6J
                                </option>
                                <option value="C57BL/6J x 129P2/OlaHsd">
                                    C57BL/6J x 129P2/OlaHsd
                                </option>
                                <option value="C57BL/6J x 129X1/SvJ">
                                    C57BL/6J x 129X1/SvJ
                                </option>
                                <option value="C57BL/6JHsd">
                                    C57BL/6JHsd
                                </option>
                                <option value="C57BL/6JOlaHsd">
                                    C57BL/6JOlaHsd
                                </option>
                                <option value="C57BL/6N">
                                    C57BL/6N
                                </option>
                                <option value="C57BL/6NCrl">
                                    C57BL/6NCrl
                                </option>
                                <option value="C57BL/6Orl">
                                    C57BL/6Orl
                                </option>
                                <option value="C57BL/6Rcc">
                                    C57BL/6Rcc
                                </option>
                                <option value="C57BL/Ka">
                                    C57BL/Ka
                                </option>
                                <option value="C57BL/KaOrl">
                                    C57BL/KaOrl
                                </option>
                                <option value="C57BR/CD">
                                    C57BR/CD
                                </option>
                                <option value="CBA">
                                    CBA
                                </option>
                                <option value="CBA x C57BL/6">
                                    CBA x C57BL/6
                                </option>
                                <option value="CBA/Ca">
                                    CBA/Ca
                                </option>
                                <option value="CD1">
                                    CD1
                                </option>
                                <option value="CD2">
                                    CD2
                                </option>
                                <option value="DBA/1">
                                    DBA/1
                                </option>
                                <option value="DBA/2">
                                    DBA/2
                                </option>
                                <option value="DDK/Pas">
                                    DDK/Pas
                                </option>
                                <option value="DW">
                                    DW
                                </option>
                                <option value="FVB">
                                    FVB
                                </option>
                                <option value="FVB/N">
                                    FVB/N
                                </option>
                                <option value="FVB/N x C57BL/6">
                                    FVB/N x C57BL/6
                                </option>
                                <option value="GRS/A">
                                    GRS/A
                                </option>
                                <option value="MF1">
                                    MF1
                                </option>
                                <option value="MPI">
                                    MPI
                                </option>
                                <option value="NMRI">
                                    NMRI
                                </option>
                                <option value="NOD">
                                    NOD
                                </option>
                                <option value="SJL">
                                    SJL
                                </option>
                                <option value="(101/El x C3H/El)F1">
                                    (101/El x C3H/El)F1
                                </option>
                                <option value="(101/H x C3H/HeH)F1">
                                    (101/H x C3H/HeH)F1
                                </option>
                                <option value="(C3H/HeH x 101/H)F1">
                                    (C3H/HeH x 101/H)F1
                                </option>
                                <option value="(C57BL/6 x CBA)F1">
                                    (C57BL/6 x CBA)F1
                                </option>
                                <option value="(C57BL/6 x DBA/2)F1">
                                    (C57BL/6 x DBA/2)F1
                                </option>
                                <option value="(C57BL/6J x C3H)F1">
                                    (C57BL/6J x C3H)F1
                                </option>
                                <option value="(C57BL/6J x CBA/Ca)F1">
                                    (C57BL/6J x CBA/Ca)F1
                                </option>
                                <option value="mixed">
                                    Mixed
                                </option>
                                <option value="not maintained in a specific background">
                                    Not maintained in a specific background
                                </option>
                                <option value="unknown">
                                    Unknown
                                </option>
                                <option value="house mouse">
                                    House mouse
                                </option>
                                <option value="Other">
                                    Other (please specify)
                                </option>
                            </select>&nbsp;<input type="text" name="mutation_original_backg_text_0" id="mutation_original_backg_text_0" />
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field mutation_chrom_anomaly_name conditional CH">
                        <label class="label" for="mutation_chrom_anomaly_name_0"><strong>Chromosomal anomaly name</strong></label>
                        <div class="input">
                            <input type="text" name="mutation_chrom_anomaly_name_0" value="" id="mutation_chrom_anomaly_name_0" />
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field mutation_chrom_anomaly_descr conditional CH">
                        <label class="label" for="mutation_chrom_anomaly_descr_0"><strong>Chromosomal anomaly description</strong></label>
                        <div class="input">
                            <input type="text" name="mutation_chrom_anomaly_descr_0" value="" id="mutation_chrom_anomaly_descr_0" />
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field mutation_es_cell_line conditional GT TM">
                        <label class="label" for="mutation_es_cell_line_0"><strong>ES cell line used</strong></label>
                        <div class="input">
                            <input type="text" name="mutation_es_cell_line_0" value="" id="mutation_es_cell_line_0" />
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field mutation_mutagen conditional IN">
                        <label class="label" for="mutation_mutagen_0"><strong>Mutagen used</strong></label>
                        <div class="input">
                            <input type="text" name="mutation_mutagen_0" value="" id="mutation_mutagen_0" />
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field mutation_promoter conditional TG">
                        <label class="label" for="mutation_promoter_0"><strong>Promoter</strong></label>
                        <div class="input">
                            <input type="text" name="mutation_promoter_0" value="" id="mutation_promoter_0" />
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field mutation_founder_line_number conditional TG">
                        <label class="label" for="mutation_founder_line_number_0"><strong>Founder line number</strong></label>
                        <div class="input">
                            <input type="text" name="mutation_founder_line_number_0" value="" id="mutation_founder_line_number_0" />
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field mutation_plasmid conditional TG">
                        <label class="label" for="mutation_plasmid_0"><strong>Plasmid/construct name or symbol</strong></label>
                        <div class="input">
                            <input type="text" name="mutation_plasmid_0" value="" id="mutation_plasmid_0" />
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div>
                        <input value="Remove mutation" type="button" class="remove_mutation" id="remove_mutation_0" />
                    </div>
                </fieldset>
                <p>
                    <input value="Add mutation" type="button" id="add_mutation" />
                </p>
            </div>
            <div id="phenotype" class="step">
                <h2>
                    Phenotype (step 5 of 10)
                </h2>
                <p>
                    Please enter the phenotype information of the mouse mutant strain you want to deposit in EMMA.
                </p>
                <div class="field">
                    <label class="label" for="homozygous_phenotypic_descr"><strong>Phenotypic description of homozygous mice<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <textarea name="homozygous_phenotypic_descr" id="homozygous_phenotypic_descr" cols="50" rows="5" alt="A short description of the mutant phenotype of homozygous mice (this will be used in the public web listing, see an example).">
                        </textarea>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="heterozygous_phenotypic_descr"><strong>Phenotypic description of heterozygous/hemizygous mice<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <textarea name="heterozygous_phenotypic_descr" id="heterozygous_phenotypic_descr" cols="50" rows="5" alt="A short description of the mutant phenotype of heterozygous/hemizygous mice (this will be used in the public web listing, see an example).">
                        </textarea>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
            </div>
            <div id="references" class="step">
                <h2>
                    References (step 6 of 10)
                </h2>
                <p>
                    If the mouse mutant strain you want to deposit in EMMA has been published, please enter the bibliographic information of one or more related publications. For the PubMed ID please <a target='PUBMED' href='http://www.pubmed.gov'>search PubMed</a>, a bibliographic database of biomedical articles.
                </p>
                <div class="field">
                    <label class="label" for="published"><strong>Has this mouse mutant strain been published?<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <label><input type="radio" name="published" value="yes" id="published-yes" />Yes (please enter bibliographic information below)</label><br />
                        <label><input type="radio" name="published" value="no" id="published-no" />No</label><br />
                        <label><input type="radio" name="published" value="not_known" id="published-not_known" />Not known</label><br />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <fieldset class="reference">
                    <legend>Reference</legend>
                    <div class="field reference_descr">
                        <label class="label" for="reference_descr_0"><strong>Short description<sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <select name="reference_descr_0" id="reference_descr_0" alt="Please select or enter a short description for this reference.">
                                <option value="">
                                    &nbsp;
                                </option>
                                <option value="Generation of the mutant mouse strain">
                                    Generation of the mutant mouse strain
                                </option>
                                <option value="Description of the mutant phenotype">
                                    Description of the mutant phenotype
                                </option>
                                <option value="Cloning/characterization of the affected gene">
                                    Cloning/characterization of the affected gene
                                </option>
                                <option value="Other">
                                    Other (please specify)
                                </option>
                            </select>&nbsp;<input type="text" name="reference_descr_text_0" id="reference_descr_text_0" />
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field reference_pmid">
                        <label class="label" for="reference_pmid_0"><strong>PubMed ID (if available)</strong></label>
                        <div class="input">
                            <input type="text" name="reference_pmid_0" value="" id="reference_pmid_0" />&nbsp;<input type="button" class="fill_reference_with_pubmed_data" id="fill_reference_with_pubmed_data_0" value="Fill with data from PubMed" />&nbsp;<img class="loading" src="images/loading.gif" />
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field reference_title">
                        <label class="label" for="reference_title_0"><strong>Title</strong></label>
                        <div class="input">
                            <input type="text" name="reference_title_0" value="" id="reference_title_0" />
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field reference_authors">
                        <label class="label" for="reference_authors_0"><strong>Authors</strong></label>
                        <div class="input">
                            <input type="text" name="reference_authors_0" value="" id="reference_authors_0" />
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field reference_journal">
                        <label class="label" for="reference_journal_0"><strong>Journal/Book<sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <input type="text" name="reference_journal_0" value="" id="reference_journal_0" />
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field reference_year">
                        <label class="label" for="reference_year_0"><strong>Year<sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <input type="text" name="reference_year_0" value="" id="reference_year_0" />
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field reference_volume">
                        <label class="label" for="reference_volume_0"><strong>Volume</strong></label>
                        <div class="input">
                            <input type="text" name="reference_volume_0" value="" id="reference_volume_0" />
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div class="field reference_pages">
                        <label class="label" for="reference_pages_0"><strong>Pages<sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <input type="text" name="reference_pages_0" value="" id="reference_pages_0" />
                        </div>
                        <div class="validation_error_message">
                            &nbsp;
                        </div>
                    </div>
                    <div>
                        <input value="Remove reference" type="button" class="remove_reference" id="remove_reference_0" />
                    </div>
                </fieldset>
                <p>
                    <input value="Add reference" type="button" id="add_reference" />
                </p>
            </div>
            <div id="characterization" class="step">
                <h2>
                    Characterization (step 7 of 10)
                </h2>
                <p>
                    Please enter information on how you characterize the mouse strain you want to deposit in EMMA.
                </p>
                <div class="field">
                    <label class="label" for="genotyping"><strong>By genotyping</strong></label>
                    <div class="input">
                        <textarea name="genotyping" id="genotyping" cols="50" rows="5" alt="e.g., sequence of PCR primers and PCR settings, Southern probes and hybridization protocol. A good template for PCR genotyping is available &lt;a href='genotyping-template.doc' target='_blank'&gt;here&lt;/a&gt;.">
                        </textarea>
                        <div>
                            <label>Upload as attachment&nbsp;<input type="file" name="genotyping_file" id="genotyping_file" /></label>
                        </div>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="phenotyping"><strong>By phenotyping</strong></label>
                    <div class="input">
                        <textarea name="phenotyping" id="phenotyping" cols="50" rows="5" alt="e.g., coat colour, etc.">
                        </textarea>
                        <div>
                            <label>Upload as attachment&nbsp;<input type="file" name="phenotyping_file" id="phenotyping_file" /></label>
                        </div>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="othertyping"><strong>By any other means that are not genotyping or phenotyping</strong></label>
                    <div class="input">
                        <textarea name="othertyping" id="othertyping" cols="50" rows="5">
                        </textarea>
                        <div>
                            <label>Upload as attachment&nbsp;<input type="file" name="othertyping_file" id="overtyping_file" /></label>
                        </div>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
            </div>
            <div id="breeding" class="step">
                <h2>
                    Breeding (step 8 of 10)
                </h2>
                <p>
                    Fertility and reproduction statistics, husbandry requirements and sanitary status of the mutant mouse strain you want to deposit in EMMA.
                </p>
                <div class="field">
                    <label class="label" for="homozygous_viable"><strong>Are homozygous mice viable?<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <label><input type="radio" name="homozygous_viable" value="yes" id="homozygous_viable-yes" />Yes</label><br />
                        <label><input type="radio" name="homozygous_viable" value="no" id="homozygous_viable-no" />No</label><br />
                        <label><input type="radio" name="homozygous_viable" value="only_males" id="homozygous_viable-only_males" />Only males</label><br />
                        <label><input type="radio" name="homozygous_viable" value="only_females" id="homozygous_viable-only_females" />Only females</label><br />
                        <label><input type="radio" name="homozygous_viable" value="not_known" id="homozygous_viable-not_known" />Not known</label><br />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="homozygous_fertile"><strong>Are homozygous mice fertile?<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <label><input type="radio" name="homozygous_fertile" value="yes" id="homozygous_fertile-yes" />Yes</label><br />
                        <label><input type="radio" name="homozygous_fertile" value="no" id="homozygous_fertile-no" />No</label><br />
                        <label><input type="radio" name="homozygous_fertile" value="only_males" id="homozygous_fertile-only_males" />Only males</label><br />
                        <label><input type="radio" name="homozygous_fertile" value="only_females" id="homozygous_fertile-only_females" />Only females</label><br />
                        <label><input type="radio" name="homozygous_fertile" value="not_known" id="homozygous_fertile-not_known" />Not known</label><br />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="heterozygous_fertile"><strong>Are heterozygous/hemizygous mice fertile?<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <label><input type="radio" name="heterozygous_fertile" value="yes" id="heterozygous_fertile-yes" />Yes</label><br />
                        <label><input type="radio" name="heterozygous_fertile" value="no" id="heterozygous_fertile-no" />No</label><br />
                        <label><input type="radio" name="heterozygous_fertile" value="only_males" id="heterozygous_fertile-only_males" />Only males</label><br />
                        <label><input type="radio" name="heterozygous_fertile" value="only_females" id="heterozygous_fertile-only_females" />Only females</label><br />
                        <label><input type="radio" name="heterozygous_fertile" value="not_known" id="heterozygous_fertile-not_known" />Not known</label><br />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="homozygous_matings_required"><strong>Are homozygous matings required?<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <label><input type="radio" name="homozygous_matings_required" value="yes" id="homozygous_matings_required-yes" />Yes (please explain below)</label><br />
                        <label><input type="radio" name="homozygous_matings_required" value="no" id="homozygous_matings_required-no" />No</label><br />
                        <label><input type="radio" name="homozygous_matings_required" value="not_known" id="homozygous_matings_required-not_known" />Not known</label><br />
                        <textarea name="homozygous_matings_required_text" id="homozygous_matings_required_text" cols="50" rows="5" alt="Please explain why homozygous matings are required.">

                        </textarea>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="reproductive_maturity_age"><strong>Average age of reproductive maturity (weeks)</strong></label>
                    <div class="input">
                        <select name="reproductive_maturity_age" id="reproductive_maturity_age">
                            <option value="">
                                &nbsp;
                            </option>
                            <option value="5-">
                                less than 6
                            </option>
                            <option value="6">
                                6
                            </option>
                            <option value="7">
                                7
                            </option>
                            <option value="8">
                                8
                            </option>
                            <option value="9">
                                9
                            </option>
                            <option value="10">
                                more than 9
                            </option>
                        </select>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="reproductive_decline_age"><strong>Average age of reproductive decline (months)</strong></label>
                    <div class="input">
                        <select name="reproductive_decline_age" id="reproductive_decline_age">
                            <option value="">
                                &nbsp;
                            </option>
                            <option value="4-">
                                less than 4
                            </option>
                            <option value="4">
                                4
                            </option>
                            <option value="5">
                                5
                            </option>
                            <option value="6">
                                6
                            </option>
                            <option value="7">
                                7
                            </option>
                            <option value="8">
                                8
                            </option>
                            <option value="9">
                                9
                            </option>
                            <option value="10">
                                10
                            </option>
                            <option value="11">
                                11
                            </option>
                            <option value="12">
                                12
                            </option>
                            <option value="13">
                                more than 12
                            </option>
                        </select>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="gestation_length"><strong>Average length of gestation (days)</strong></label>
                    <div class="input">
                        <select name="gestation_length" id="gestation_length">
                            <option value="">
                                &nbsp;
                            </option>
                            <option value="18">
                                18
                            </option>
                            <option value="19">
                                19
                            </option>
                            <option value="20">
                                20
                            </option>
                            <option value="21">
                                21
                            </option>
                            <option value="22">
                                22
                            </option>
                            <option value="23">
                                23
                            </option>
                        </select>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="pups_at_birth"><strong>Average number of pups at birth</strong></label>
                    <div class="input">
                        <select name="pups_at_birth" id="pups_at_birth">
                            <option value="">
                                &nbsp;
                            </option>
                            <option value="1">
                                1
                            </option>
                            <option value="2">
                                2
                            </option>
                            <option value="3">
                                3
                            </option>
                            <option value="4">
                                4
                            </option>
                            <option value="5">
                                5
                            </option>
                            <option value="6">
                                6
                            </option>
                            <option value="7">
                                7
                            </option>
                            <option value="8">
                                8
                            </option>
                            <option value="9">
                                9
                            </option>
                            <option value="10">
                                10
                            </option>
                            <option value="11">
                                11
                            </option>
                            <option value="12">
                                12
                            </option>
                            <option value="13">
                                13
                            </option>
                            <option value="14">
                                14
                            </option>
                        </select>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="pups_at_weaning"><strong>Average number of pups surviving to weaning</strong></label>
                    <div class="input">
                        <select name="pups_at_weaning" id="pups_at_weaning">
                            <option value="">
                                &nbsp;
                            </option>
                            <option value="1">
                                1
                            </option>
                            <option value="2">
                                2
                            </option>
                            <option value="3">
                                3
                            </option>
                            <option value="4">
                                4
                            </option>
                            <option value="5">
                                5
                            </option>
                            <option value="6">
                                6
                            </option>
                            <option value="7">
                                7
                            </option>
                            <option value="8">
                                8
                            </option>
                            <option value="9">
                                9
                            </option>
                            <option value="10">
                                10
                            </option>
                            <option value="11">
                                11
                            </option>
                            <option value="12">
                                12
                            </option>
                            <option value="13">
                                13
                            </option>
                            <option value="14">
                                14
                            </option>
                        </select>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="weaning_age"><strong>Recommended weaning age (days)</strong></label>
                    <div class="input">
                        <input type="text" name="weaning_age" value="" id="weaning_age" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="litters_in_lifetime"><strong>Average number of litters in lifetime</strong></label>
                    <div class="input">
                        <select name="litters_in_lifetime" id="litters_in_lifetime">
                            <option value="">
                                &nbsp;
                            </option>
                            <option value="1">
                                1
                            </option>
                            <option value="2">
                                2
                            </option>
                            <option value="3">
                                3
                            </option>
                            <option value="4">
                                4
                            </option>
                            <option value="5">
                                5
                            </option>
                            <option value="6">
                                6
                            </option>
                            <option value="7">
                                7
                            </option>
                            <option value="8">
                                more than 7
                            </option>
                        </select>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="breeding_performance"><strong>Breeding performance</strong></label>
                    <div class="input">
                        <select name="breeding_performance" id="breeding_performance">
                            <option value="">
                                &nbsp;
                            </option>
                            <option value="poor">
                                Poor
                            </option>
                            <option value="good">
                                Good
                            </option>
                            <option value="excellent">
                                Excellent
                            </option>
                        </select>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="husbandry_requirements"><strong>Husbandry requirements</strong></label>
                    <div class="input">
                        <textarea name="husbandry_requirements" id="husbandry_requirements" cols="50" rows="5" alt="Please describe any special dietary, environmental, medical, housing, handling requirements.">
                        </textarea> <!-- <div>
                                                                                <label>Upload as attachment&nbsp;<input type="file" name="husbandry_requirements_file" id="husbandry_requirements_file" /></label>
                                                                        </div> -->
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="immunocompromised"><strong>Are mice immunocompromised?<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <label><input type="radio" name="immunocompromised" value="yes" id="immunocompromised-yes" />Yes</label><br />
                        <label><input type="radio" name="immunocompromised" value="no" id="immunocompromised-no" />No</label><br />
                        <label><input type="radio" name="immunocompromised" value="not_known" id="immunocompromised-not_known" />Not known</label><br />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="sanitary_status"><strong>Sanitary status</strong></label>
                    <div class="input">
                        <textarea name="sanitary_status" id="sanitary_status" cols="50" rows="5">
                        </textarea>
                        <div>
                            <label>Upload as attachment&nbsp;<input type="file" name="sanitary_status_file" id="sanitary_status_file" /></label>
                        </div>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="welfare"><strong>Animal welfare</strong></label>
                    <div class="input">
                        <input type="text" id="welfare" name="welfare" alt="Please enter the mouse welfare terms that apply to this mutant mouse strain (from <http://www.mousewelfareterms.org>)."/>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="remedial_actions"><strong>Remedial actions</strong></label>
                    <div class="input">
                        <textarea name="remedial_actions" id="remedial_actions" cols="50" rows="5" alt="Please enter the remedial actions necessary to ensure the welfare of this mutant mouse strain.">
                        </textarea>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
            </div>
            <div id="research_value" class="step">
                <h2>
                    Research value (step 9 of 10)
                </h2>
                <p>
                    XXX
                </p>
                <div class="field">
                    <label class="label" for="human_condition"><strong>Does this strain model a human condition or disease?<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <label><input type="radio" name="human_condition" value="yes" id="human_condition-yes" alt="For OMIM IDs please &lt;a href='http://www.ncbi.nlm.nih.gov/omim' target='_blank'&gt;search OMIM&lt;/a&gt;, a database of human genes and genetic disorders. Insert the numeric ID and divide by semicolon if more than one." />Yes (please explain below)</label><br />
                        <label><input type="radio" name="human_condition" value="no" id="human_condition-no" />No</label><br />
                        <label><input type="radio" name="human_condition" value="not_known" id="human_condition-not_known" />Not known</label><br />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field" id="human_condition_more">
                    <label for="human_condition_omim">Please enter the <a href="http://www.ncbi.nlm.nih.gov/omim" target="_blank">Online Mendelian Inheritance in Man</a> identifiers that apply to the human condition or disease:</label>
                    <div class="input">
                        <input type="text" name="human_condition_omim" id="human_condition_omim" />
                        <br/>
                        <div>
                            <label for="human_condition_text">If OMIM IDs are not available, please describe the human condition or disease below:</label>
                        </div>
                        <textarea name="human_condition_text" id="human_condition_text" cols="50" rows="5">
                        </textarea>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label"><strong>Research areas</strong></label>
                    <div class="input">
                        <label><input type="checkbox" name="research_areas_apoptosis" id="research_areas_apoptosis" value="apoptosis" />Apoptosis</label><br />
                        <label><input type="checkbox" name="research_areas_cancer" id="research_areas_cancer" value="cancer" />Cancer</label><br />
                        <label><input type="checkbox" name="research_areas_cardiovascular" id="research_areas_cardiovascular" value="cardiovascular" />Cardiovascular</label><br />
                        <label><input type="checkbox" name="research_areas_cell_biology" id="research_areas_cell_biology" value="cell_biology" />Cell biology</label><br />
                        <label><input type="checkbox" name="research_areas_dermatology" id="research_areas_dermatology" value="dermatology" />Dermatology</label><br />
                        <label><input type="checkbox" name="research_areas_developmental_biology" id="research_areas_developmental_biology" value="developmental_biology" />Developmental biology</label><br />
                        <label><input type="checkbox" name="research_areas_diabetes_obesity" id="research_areas_diabetes_obesity" value="diabetes_obesity" />Diabetes/Obesity</label><br />
                        <label><input type="checkbox" name="research_areas_endocrinology" id="research_areas_endocrinology" value="endocrinology" />Endocrinology</label><br />
                        <label><input type="checkbox" name="research_areas_hematology" id="research_areas_hematology" value="hematology" />Hematology</label><br />
                        <label><input type="checkbox" name="research_areas_immunology_inflammation" id="research_areas_immunology_inflammation" value="immunology_inflammation" />Immunology and Inflammation</label><br />
                        <label><input type="checkbox" name="research_areas_internal_organ" id="research_areas_internal_organ" value="internal_organ" />Internal/Organ</label><br />
                        <label><input type="checkbox" name="research_areas_metabolism" id="research_areas_metabolism" value="metabolism" />Metabolism</label><br />
                        <label><input type="checkbox" name="research_areas_neurobiology" id="research_areas_neurobiology" value="neurobiology" />Neurobiology</label><br />
                        <label><input type="checkbox" name="research_areas_reproduction" id="research_areas_reproduction" value="reproduction" />Reproduction</label><br />
                        <label><input type="checkbox" name="research_areas_sensorineural" id="research_areas_sensorineural" value="sensorineural" />Sensorineural</label><br />
                        <label><input type="checkbox" name="research_areas_virology" id="research_areas_virology" value="virology" />Virology</label><br />
                        <label><input type="checkbox" name="research_areas_other" id="research_areas_other" value="Other" />Other (please specify)</label>&nbsp;<input type="text" name="research_areas_other_text" id="research_areas_other_text" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label"><strong>Research tools</strong></label>
                    <div class="input">
                        <label><input type="checkbox" name="research_tools_cre" id="research_tools_cre" value="cre" />Strain expressing Cre recombinase (please specify expression pattern if known)</label>&nbsp;<input type="text" name="research_tools_cre_text" id="research_tools_cre_text" /><br />
                        <label><input type="checkbox" name="research_tools_loxp" id="research_tools_loxp" value="loxp" />Strain with loxP-flanked sequences</label><br />
                        <label><input type="checkbox" name="research_tools_flp" id="research_tools_flp" value="flp" />Strain expressing FLP recombinase (please specify expression pattern if known)</label>&nbsp;<input type="text" name="research_tools_flp_text" id="research_tools_flp_text" /><br />
                        <label><input type="checkbox" name="research_tools_frt" id="research_tools_frt" value="frt" />Strain with FRT-flanked sequences</label><br />
                        <label><input type="checkbox" name="research_tools_tet" id="research_tools_tet" value="tet" />Strain with Tet expression system</label><br />
                        <label><input type="checkbox" name="research_tools_other" id="research_tools_other" value="Other" />Other (please specify)</label>&nbsp;<input type="text" name="research_tools_other_text" id="research_tools_other_text" />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
            </div>
            <div id="miscellanea" class="step">
                <h2>
                    Additional information (step 10 of 10)
                </h2>
                <p>
                    XXX
                </p>
                <div class="field">
                    <label class="label" for="past_requests"><strong>How many requests for this strain have you received in the last 6 months?<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <select name="past_requests" id="past_requests">
                            <option value="">
                                &nbsp;
                            </option>
                            <option value="0">
                                0
                            </option>
                            <option value="1">
                                1
                            </option>
                            <option value="2">
                                2
                            </option>
                            <option value="3">
                                3
                            </option>
                            <option value="4">
                                4
                            </option>
                            <option value="5">
                                5
                            </option>
                            <option value="6">
                                6
                            </option>
                            <option value="7">
                                7
                            </option>
                            <option value="8">
                                8
                            </option>
                            <option value="9">
                                9
                            </option>
                            <option value="10">
                                10
                            </option>
                            <option value="11">
                                more than 10
                            </option>
                        </select>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="deposited_elsewhere"><strong>Is this strain being deposited with any other institution or company intending to make it available for distribution?<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <label><input type="radio" name="deposited_elsewhere" id="deposited_elsewhere-yes" value="yes" />Yes (please list names of additional institutions and companies below)</label><br />
                        <label><input type="radio" name="deposited_elsewhere" id="deposited_elsewhere-no" value="no" />No</label><br />
                        <label><input type="radio" name="deposited_elsewhere" id="deposited_elsewhere-not_known" value="not_known" />Not known</label><br />
                        <textarea name="deposited_elsewhere_text" id="deposited_elsewhere_text" cols="50" rows="5">

                        </textarea>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="similar_strains"><strong>Are other laboratories producing similar strains?<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <label><input type="radio" name="similar_strains" id="similar_strains-yes" value="yes" />Yes</label><br />
                        <label><input type="radio" name="similar_strains" id="similar_strains-no" value="no" />No</label><br />
                        <label><input type="radio" name="similar_strains" id="similar_strains-not_known" value="not_known" />Not known</label><br />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="ip_rights"><strong>Are there any intellectual property rights or patented technologies linked to this strain?<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <label><input type="radio" name="ip_rights" id="ip_rights-yes" value="yes" />Yes (please explain below)</label><br />
                        <label><input type="radio" name="ip_rights" id="ip_rights-no" value="no" />No</label><br />
                        <label><input type="radio" name="ip_rights" id="ip_rights-not_known" value="not_known" />Not known</label><br />
                        <textarea name="ip_rights_text" id="ip_rights_text" cols="50" rows="5">
                        </textarea>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="exclusive_owner"><strong>Is the producer the exclusive owner of this strain<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <label><input type="radio" name="exclusive_owner" id="exclusive_owner-yes" value="yes" />Yes</label><br />
                        <label><input type="radio" name="exclusive_owner" id="exclusive_owner-no" value="no" />No (please list names of additional owners with affiliation and e-mail address below)</label><br />
                        <label><input type="radio" name="exclusive_owner" id="exclusive_owner-not_known" value="not_known" />Not known</label><br />
                        <textarea name="exclusive_owner_text" id="exclusive_owner_text" cols="50" rows="5">
                        </textarea>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="owner_permission"><strong>Do you have permission from all owners to deposit this strain in the EMMA repository?<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <label><input type="radio" name="owner_permission" id="owner_permission-yes" value="yes" />Yes</label><br />
                        <label><input type="radio" name="owner_permission" id="owner_permission-no" value="no" />No (please explain below)</label><br />
                        <label><input type="radio" name="owner_permission" id="owner_permission-not_known" value="not_known" />Not known</label><br />
                        <textarea name="owner_permission_text" id="owner_permission_text" cols="50" rows="5">

                        </textarea>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="delayed_release"><strong>Do you require <a target="PDF" href="/delayed_release.php">delayed release</a> for this strain?<sup><font color="red">*</font></sup></strong></label>
                    <div class="input">
                        <label><input type="radio" name="delayed_release" id="delayed_release-yes" value="yes" />Yes (please explain below)</label><br />
                        <label><input type="radio" name="delayed_release" id="delayed_release-no" value="no" />No</label><br />
                        <label><input type="radio" name="delayed_release" id="delayed_release-not_known" value="not_known" />Not known</label><br />
                        <textarea name="delayed_release_text" id="delayed_release_text" cols="50" rows="5">

                        </textarea>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label"><strong>How many mice of breeding age could you provide and when?</strong><br />
                        <font color="gray" size="2">Mice of breeding age must be provided. Minimum of 5 females and 5 males for freezing as homozygotes. Minimum of 5 males for freezing as heterozygotes.<br />
                        Provision of more mice than the specified minimum will considerably accelerate the freezing process.</font></label>
                    <div class="input">
                        <table>
                            <tr>
                                <td>
                                    <select name="mice_avail_month" id="mice_avail_month">
                                        <option value="">
                                            &nbsp;
                                        </option>
                                        <option value="1">
                                            1
                                        </option>
                                        <option value="2">
                                            2
                                        </option>
                                        <option value="3">
                                            3
                                        </option>
                                        <option value="4">
                                            4
                                        </option>
                                        <option value="5">
                                            5
                                        </option>
                                        <option value="6">
                                            6
                                        </option>
                                        <option value="7">
                                            7
                                        </option>
                                        <option value="8">
                                            8
                                        </option>
                                        <option value="9">
                                            9
                                        </option>
                                        <option value="10">
                                            10
                                        </option>
                                        <option value="11">
                                            11
                                        </option>
                                        <option value="12">
                                            12
                                        </option>
                                    </select>
                                </td>
                                <td>
                                    <select name="mice_avail_year" id="mice_avail_year">
                                        <!--TODO SOME SORT OF AUTO DATE CALC FOR CURRENT YEAR + 4 0R 5-->
                                        <option value="">
                                            &nbsp;
                                        </option>
                                        <option value="2011">
                                            2011
                                        </option>
                                        <option value="2012">
                                            2012
                                        </option>
                                        <option value="2013">
                                            2013
                                        </option>
                                        <option value="2014">
                                            2014
                                        </option>
                                        <option value="2015">
                                            2015
                                        </option>
                                    </select>
                                </td>
                                <td>
                                    <select name="mice_avail_males" id="mice_avail_males">
                                        <option value="">
                                            &nbsp;
                                        </option>
                                        <option value="0">
                                            0
                                        </option>
                                        <option value="1">
                                            1
                                        </option>
                                        <option value="2">
                                            2
                                        </option>
                                        <option value="3">
                                            3
                                        </option>
                                        <option value="4">
                                            4
                                        </option>
                                        <option value="5">
                                            5
                                        </option>
                                        <option value="6">
                                            6
                                        </option>
                                        <option value="7">
                                            more than 6
                                        </option>
                                    </select>
                                </td>
                                <td>
                                    <select name="mice_avail_females" id="mice_avail_females">
                                        <option value="">
                                            &nbsp;
                                        </option>
                                        <option value="0">
                                            0
                                        </option>
                                        <option value="1">
                                            1
                                        </option>
                                        <option value="2">
                                            2
                                        </option>
                                        <option value="3">
                                            3
                                        </option>
                                        <option value="4">
                                            4
                                        </option>
                                        <option value="5">
                                            5
                                        </option>
                                        <option value="6">
                                            6
                                        </option>
                                        <option value="7">
                                            7
                                        </option>
                                        <option value="8">
                                            8
                                        </option>
                                        <option value="9">
                                            9
                                        </option>
                                        <option value="10">
                                            10
                                        </option>
                                        <option value="11">
                                            more than 10
                                        </option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="mice_avail_month">Month</label>
                                </td>
                                <td>
                                    <label for="mice_avail_year">Year</label>
                                </td>
                                <td>
                                    <label for="mice_avail_males">Males</label>
                                </td>
                                <td>
                                    <label for="mice_avail_females">Females</label>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
                <div class="field">
                    <label class="label"><strong>Additional materials of interest (you can upload up to five attachments)</strong></label>
                    <div class="input">
                        <input type="file" name="additional_materials_file_1" id="additional_materials_file_1" /><br />
                        <input type="file" name="additional_materials_file_2" id="additional_materials_file_2" /><br />
                        <input type="file" name="additional_materials_file_3" id="additional_materials_file_3" /><br />
                        <input type="file" name="additional_materials_file_4" id="additional_materials_file_4" /><br />
                        <input type="file" name="additional_materials_file_5" id="additional_materials_file_5" /><br />
                    </div>
                    <div class="validation_error_message">
                        &nbsp;
                    </div>
                </div>
            </div>
            <div id="summary" class="step">
                <h2>
                    Summary
                </h2>
                <p>
                    XXX
                </p>
                <div>
                    &nbsp;
                </div>
            </div>
            <div id="buttons">
                <div>
                    <input disabled="disabled" id="prev" value="Previous" type="reset" />&nbsp;<input id="next" value="Submit" type="submit" />
                </div>
            </div>
            <ul id="completed_steps"></ul>
        </form>
    </body>
</html>

