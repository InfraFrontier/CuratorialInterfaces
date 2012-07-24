<%-- 
    Document   : ajaxBiblios
    Created on : 12-Jul-2012, 11:46:43
    Author     : phil
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/datetime-1.0" prefix="dt" %>
<c:set var="keyRef"  value="${returnedOut}"></c:set>
<c:set var="subMutsDAO"  value="${keyRef['mutdaos']}"></c:set>
<c:set var="singleSubMutsDAO"  value="${keyRef['SubMutDAO']}"></c:set>
<c:set var="count"  value="${keyRef['count']}"></c:set>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<!DOCTYPE html>
<div name="addBib" id="addBib">
    <p>
        <input value="Add Biblio" type="button" id="add_biblio" /> You can add ${10 - count} more reference<c:if test="${10 - count != 1}">s</c:if>.
    </p>
</div>
<h3>Bibliographic references for Submission</h3>
<script type="text/javascript" >  $("#addMut").show(); </script>
<c:choose>
    <c:when test="${count<=0}">No references added.</c:when>
    <c:otherwise>
        <table width="60%" align="center">
            <tr>
                <th>Pubmed ID</th>
                <th>Title</th>
                <th>Mutation Subtype</th>
                <th>Author</th>
                <th>Journal/Year/Volume/Pages</th>
                <th>Action</th>
            </tr>
            <c:forEach var="mut" items="${subMutsDAO}">
                <tr>
                    <td>${mut.mutation_original_backg_text} (${mut.mutation_original_backg})</td>
                    <td>${mut.mutation_type}</td>
                    <td>${mut.mutation_subtype}</td>
                    <td>${mut.mutation_dominance_pattern}</td>
                    <td><c:if test="${not empty mut.mutation_plasmid}"/></td>

                    <td><a href="javascript:void(0)" title="${id_biblio}" id="editmutation_${id_biblio}">Edit</a> / <a href="javascript:void(0)"  id="deletebiblio_${id_biblio}" title="${id_biblio}">Remove</a></td>
                </tr>
            </c:forEach> 
        </table>
    </c:otherwise>
</c:choose>
<script type="text/javascript" > 
    $("a[id^='editmutation']").click(function() {
        alert("Handler for edit mutation called. FUNCTIONALITY NOT IN PLACE YET!");
        //testcall();
        //alert($('#encID').val() + "    " + $('#mutation_type').val() + "      " );
        $('#subMutations').load('ajaxMutations.emma',{
            action: "edit",
            Id_sub:$('#encID').val(),
            Id_mut:$(this).attr("title"),
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
        alert($(this).attr("title"));
    });
        
    $("a[id^='deletemutation']").click(function() {
        alert("Your mutation will be deleted from your submission\n\n\
Are you sure you wish to continue? ");
        $('#subMutations').load('ajaxMutations.emma',{
            action: "delete",
            Id_sub:$('#encID').val(),
            Id_mut:$(this).attr("title")

            //"a[id^='deletemutation']"
        });
    });
    $("addMut").hide();

</script>
<script type="text/javascript" > 
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
</script>
<c:if test="${count >= 10}"><script type="text/javascript" >  $("#addBib").hide(); </script></c:if>
<c:if test="${count < 10}"><script type="text/javascript" >  $("#addBib").show(); </script></c:if>
