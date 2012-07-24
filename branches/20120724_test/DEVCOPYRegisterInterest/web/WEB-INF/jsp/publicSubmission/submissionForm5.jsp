<%-- 
    Document   : submissionForm5
    Created on : 30-Jan-2012, 14:48:00
    Author     : phil
--%>

<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:bind path="command.*" />
<%@page import="java.io.*"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EMMA Mutant Mouse Strain Submission Wizard - Step ${(sessionScope.pageCount)} of ${(sessionScope.totalStepCount)}</title>
        <style type="text/css">@import url(../css/emmastyle.css);</style>
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
        <script type="text/javascript" src="../js//jquery-1.6.1.min.js"></script>

        <script type="text/javascript" src="../js/submission.js"></script>
    </head>
    <body>
        <div id="genotype" class="step">
            <h2>Genotype (Step ${(sessionScope.pageCount)} of ${(sessionScope.totalStepCount)})</h2>
            <p>
                Please enter the genotype information of the mouse mutant strain you want to deposit in EMMA. A mutant strain is defined by its specific mutation(s) AND genetic background. Therefore strains with the same mutation(s) but different genetic backgrounds require distinct names and consequently separate submissions.
            </p>
            <p>
                For the definitions of terms <a target="IMSR" href="http://www.informatics.jax.org/imsr/glossary.jsp">see the IMSR glossary</a>. For gene/allele symbols and identifiers please <a target='MGI' href='http://www.informatics.jax.org/javawi2/servlet/WIFetch?page=markerQF'>search MGI</a>.
            </p>
            <form:form method="POST" commandName="command"> 
                <input type="hidden" name="encID" id="encID" value="${param.getprev}"/>
                <spring:bind path="command.strain_name">
                    <div class="field">
                        <label class="label" for="${status.expression}"><strong>Strain name<sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <form:input  id="${status.expression}" path="${status.expression}" 
                                         alt="Please enter the mutant mouse strain name. If you are submitting lines on more than one background, please include the background in the strain name and submit each line separately."></form:input>
                            </div>
                        <form:errors path="${status.expression}" cssClass="error" />
                    </div>
                </spring:bind>


                <spring:bind path="command.genetic_descr">
                    <div class="field">
                        <label class="label" for="${status.expression}"><strong>Genetic description<sup><font color="red">*</font></sup></strong></label>

                        <div class="input">
                            <form:textarea id="${status.expression}" path="${status.expression}"  cols="50" rows="5" 
                                           title="Please enter a short description of the mutant mouse strain genotype (this will be used in the public web listing, see an example)."></form:textarea>
                            </div>
                        <form:errors path="${status.expression}" cssClass="error" />
                    </div>
                </spring:bind>


                <spring:bind path="command.current_backg">
                    <div class="field">

                        <label class="label" for="current_backg"><strong>Current genetic background<sup><font color="red">*</font></sup></strong></label>
                        <div class="input">
                            <!--TODO BACKGROUNDS FROM DATABASE PRODUCED LIST-->
                            <form:select path="${status.expression}" id="${status.expression}"  title="Please specify the current genetic background of the strain that is being submitted.">

                                <form:option value='0'>Please select...</form:option>
                                <c:forEach var="background" items="${command.bgDAO}">

                                    <form:option value='${background[0]}'>${background[1]}</form:option>
                                </c:forEach>               
                            </form:select>&nbsp;
                        </div>
                        <form:errors path="${status.expression}" cssClass="error" />
                    </div>
                </spring:bind>   


                <input type="text" name="command.current_backg_text" id="current_backg_text" />
            </div>
            <form:errors path="${status.expression}" cssClass="error" />
        </div>


        <spring:bind path="command.backcrosses">         
            <div class="field">
                <label class="label" for="backcrosses"><strong>Number of generations backcrossed</strong></label>
                <div class="input">
                    <input type="text" id="${status.expression}" path="${status.expression}" alt="Please enter the number of generations backcrossed to background strain (if applicable and known)." />
                </div>
                <form:errors path="${status.expression}" cssClass="error" />
            </div>
        </spring:bind>

        <spring:bind path="command.sibmatings"><%-- TODO DISCOVER FIELD --%>
            <div class="field">
                <label class="label" for="${status.expression}"><strong>Number of generations sib-mated</strong></label>
                <div class="input">
                    <form:input  id="${status.expression}" path="${status.expression}"  alt="Please enter the number of generations mated to a sibling (since inception or subsequent to any outcrosses or backcrosses and if applicable and known)." />
                </div>
                <form:errors path="${status.expression}" cssClass="error" />
            </div>
        </spring:bind>

        <spring:bind path="command.breeding_history">
            <div class="field">
                <label class="label" for="breeding_history"><strong>Breeding history</strong></label>
                <div class="input">
                    <textarea id="${status.expression}" path="${status.expression}" cols="50" rows="5" alt="Please describe the breeding history (outcrosses, backcrosses, intercrosses, incrosses) from the original founder strain to the current genetic background.">
                    </textarea>
                </div>
                <form:errors path="${status.expression}" cssClass="error" />
            </div>
        </spring:bind>

        <fieldset class="mutation" id="mutation">
            <legend>Mutation</legend>
            <div class="field mutation_type">
                <label class="label" for="mutation_type"><strong>Type<sup><font color="red">*</font></sup></strong></label>

                <div class="input">
                    <!--TODO MUTS FROM DATABASE-->
                    <select name="mutation_type" id="mutation_type">
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
                <form:errors path="${status.expression}" cssClass="error" />
            </div>
            <div id="exposeCH" style="display: none">CH Exposed</div>
            <script>
                $("#mutation_type").change(mutFieldDisplay);
                mutFieldDisplay();
            </script>
            <div style="display: none" class="field mutation_subtype conditional CH" id="field mutation_subtype_conditionalCH">
                <label class="label" for="mutation_subtype"><strong>Subtype<sup><font color="red">*</font></sup></strong></label>
                <div class="input">
                    <!--TODO MUTS SUBS FROM DATABASE-->
                    <select name="mutation_subtype" id="mutation_subtype">
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
            <div style="display: none" class="field mutation_subtype conditional IN" id="field mutation_subtype conditional IN">
                <label class="label" for="mutation_subtype"><strong>Subtype<sup><font color="red">*</font></sup></strong></label>
                <div class="input">

                    <select name="mutation_subtype" id="mutation_subtype">
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
            <div style="display: none" class="field mutation_subtype conditional TM" id="field mutation_subtype conditional TM">
                <label class="label" for="mutation_subtype"><strong>Subtype<sup><font color="red">*</font></sup></strong></label>

                <div class="input">
                    <!--TODO MUTS SUBS FROM DATABASE-->
                    <select name="mutation_subtype" id="mutation_subtype">
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
            <div style="display: none" class="field mutation_transgene_mgi_symbol conditional TG" id="field mutation_transgene_mgi_symbol conditional TG">
                <label class="label" for="mutation_transgene_mgi_symbol"><strong>Transgene</strong></label>

                <div class="input">
                    <input type="text" name="mutation_transgene_mgi_symbol" value="" id="mutation_transgene_mgi_symbol" />
                </div>
                <div class="validation_error_message">
                    &nbsp;
                </div>
            </div>
            <div style="display: none" class="field mutation_gene_mgi_symbol conditional CH GT IN SP TM XX" id="field mutation_gene_mgi_symbol conditional CH GT IN SP TM XX">
                <label class="label" for="mutation_gene_mgi_symbol"><strong>Affected gene</strong></label>
                <div class="input">
                    <input type="text" name="mutation_gene_mgi_symbol" value="" id="mutation_gene_mgi_symbol" />
                </div>
                <div class="validation_error_message">
                    &nbsp;
                </div>
            </div>
            <div style="display: none" class="field mutation_allele_mgi_symbol conditional CH GT IN SP TM XX" id="field mutation_allele_mgi_symbol conditional CH GT IN SP TM XX">
                <label class="label" for="mutation_allele_mgi_symbol"><strong>Affected allele</strong></label>

                <div class="input">
                    <input type="text" name="mutation_allele_mgi_symbol" value="" id="mutation_allele_mgi_symbol" />
                </div>
                <div class="validation_error_message">
                    &nbsp;
                </div>
            </div>
            <div style="display: none" class="field mutation_chrom conditional CH GT IN SP TM XX" id="field mutation_chrom conditional CH GT IN SP TM XX">
                <label class="label" for="mutation_chrom"><strong>Affected chromosome</strong></label>

                <div class="input">
                    <select name="mutation_chrom" id="mutation_chrom">
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
                <label class="label" for="mutation_dominance_pattern"><strong>Dominance pattern</strong></label>
                <div class="input">
                    <select name="mutation_dominance_pattern" id="mutation_dominance_pattern">
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

            <spring:bind path="command.mutation_original_backg">
                <div class="field mutation_original_backg">
                    <label class="label" for="mutation_original_backg"><strong>Original genetic background</strong></label>
                    <div class="input">
                        <!--TODO BACKGROUNDS FROM DATABASE PRODUCED LIST-->
                        <form:select path="${status.expression}" id="${status.expression}" title="Please specify the genetic background of the founder strain (e.g., the genetic background of ES cells or oocytes used to generate a knockout or transgenic mouse respectively).">

                            <form:option value='0'>Please select...</form:option>
                            <c:forEach var="background" items="${command.bgDAO}">
                                <form:option value='${background[0]}'>${background[1]}</form:option>
                            </c:forEach>        
                        </form:select>
                        </select>&nbsp;<%--<input type="text" name="mutation_original_backg_text_0" id="mutation_original_backg_text_0" />--%>
                    </div>
                    <form:errors path="${status.expression}" cssClass="error" />
                </div>
            </div>

        </spring:bind>

        <div style="display: none" class="field mutation_chrom_anomaly_name conditional CH" id="field mutation_chrom_anomaly_name conditional CH">
            <label class="label" for="mutation_chrom_anomaly_name"><strong>Chromosomal anomaly name</strong></label>
            <div class="input">
                <input type="text" name="mutation_chrom_anomaly_name" value="" id="mutation_chrom_anomaly_name" />

            </div>
            <div class="validation_error_message">
                &nbsp;
            </div>
        </div>
        <div style="display: none" class="field mutation_chrom_anomaly_descr conditional CH" id="field mutation_chrom_anomaly_descr conditional CH">
            <label class="label" for="mutation_chrom_anomaly_descr"><strong>Chromosomal anomaly description</strong></label>
            <div class="input">

                <input type="text" name="mutation_chrom_anomaly_descr" value="" id="mutation_chrom_anomaly_descr" />
            </div>
            <div class="validation_error_message">
                &nbsp;
            </div>
        </div>
        <div style="display: none" class="field mutation_es_cell_line conditional GT TM" id="field mutation_es_cell_line conditional GT TM">
            <label class="label" for="mutation_es_cell_line"><strong>ES cell line used</strong></label>

            <div class="input">
                <input type="text" name="mutation_es_cell_line" value="" id="mutation_es_cell_line" />
            </div>
            <div class="validation_error_message">
                &nbsp;
            </div>
        </div>
        <div style="display: none" class="field mutation_mutagen conditional IN" id="field mutation_mutagen conditional IN>
             <label class="label for="mutation_mutagen"><strong>Mutagen used</strong></label>

            <div class="input">
                <input type="text" name="mutation_mutagen" value="" id="mutation_mutagen" />
            </div>
            <div class="validation_error_message">
                &nbsp;
            </div>
        </div>
        <div style="display: none" class="field mutation_promoter conditional TG" id="field mutation_promoter conditional TG">
            <label class="label" for="mutation_promoter"><strong>Promoter</strong></label>

            <div class="input">
                <input type="text" name="mutation_promoter" value="" id="mutation_promoter" />
            </div>
            <div class="validation_error_message">
                &nbsp;
            </div>
        </div>
        <div style="display: none" class="field mutation_founder_line_number conditional TG" id="field mutation_founder_line_number conditional TG">
            <label class="label" for="mutation_founder_line_number"><strong>Founder line number</strong></label>

            <div class="input">
                <input type="text" name="mutation_founder_line_number" value="" id="mutation_founder_line_number" />
            </div>
            <div class="validation_error_message">
                &nbsp;
            </div>
        </div>
        <div style="display: none" class="field mutation_plasmid conditional TG" id="field mutation_plasmid conditional TG">
            <label class="label" for="mutation_plasmid"><strong>Plasmid/construct name or symbol</strong></label>

            <div class="input">
                <input type="text" name="mutation_plasmid" value="" id="mutation_plasmid" />
            </div>
            <div class="validation_error_message">
                &nbsp;
            </div>
        </div>
        <br/>
        <div>
            <input value="Clear mutation" type="button" class="clearMutation" id="clearMutation" onclick="clear_form_elements(document.getElementById('mutation'))" />

        </div>
    </fieldset>
    <%--<div name="addMut" id="addMut">
        <p>
            <input value="Add mutation" type="button" id="add_mutation" />
        </p>
    </div>--%>
    <div id="subMutations" name="subMutations">
        <script type="text/javascript" > 
            $('#subMutations').load('ajaxMutations.emma',{
                action: "none",
                Id_sub:$('#encID').val()
            });
        </script>
    </div>
    <%--    <script type="text/javascript" > 
        $('#add_mutation').click(function() {
            $('#subMutations').load('ajaxMutations.emma',{
                action: "add",
                Id_sub:$('#encID').val(), 
                mutation_allele_mgi_symbol:$('#mutation_allele_mgi_symbol').val(),
                mutation_chrom:$('#mutation_chrom').val(), 
                mutation_chrom_anomaly_name:$('#mutation_chrom_anomaly_name').val(), 
                mutation_chrom_anomaly_descr:$('#mutation_chrom_anomaly_descr').val(), 
                mutation_dominance_pattern:$('#mutation_dominance_pattern').val(), 
                mutation_es_cell_line:$('#mutation_es_cell_line').val(), 
                mutation_founder_line_number:$('#mutation_founder_line_number').val(), 
                mutation_gene_mgi_symbol:$('#mutation_gene_mgi_symbol').val(), 
                mutation_mutagen:$('#mutation_mutagen').val(), 
                mutation_original_backg:$('#mutation_original_backg').val(), 
                mutation_original_backg_text:$('#mutation_original_backg option:selected').text(), 
                mutation_plasmid:$('#mutation_plasmid').val(), 
                mutation_promoter:$('#mutation_promoter').val(),
                mutation_subtype:$('#mutation_subtype').val(),
                mutation_transgene_mgi_symbol:$('#mutation_transgene_mgi_symbol').val(),
                mutation_type:$('#mutation_type').val()
            });
        });
    </script>--%>
</div>

<p>
<table width="150" cellspacing="8" class="formNav">
    <tr>
        <td colspan="2" align='center'><input type="submit" value="Next" name="_target6" /><br/></td>
    </tr>
    <tr>
        <td ><input type="submit" value="Previous" name="_target4" /></td>
        <td ><input type="submit" value="Cancel" name="_cancel" /></td>
    </tr>
</table>
</p>
</form:form>  
<br/>
<br/>
</body>
</html>
