/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.controllers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.emmanet.model.MutationsManager;
import org.emmanet.model.SubmissionMutationsDAO;
import org.emmanet.util.Encrypter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author phil
 */
public class submissionMutationsController implements Controller {

    private SubmissionMutationsDAO smd;
    private List mutdaos;
    private MutationsManager mm = new MutationsManager();
    public static final String MAP_KEY = "returnedOut";
    private Map returnedOut = new HashMap();
    Encrypter enc = new Encrypter();

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        mutdaos = mm.getSubMutationBySubID(Integer.parseInt(enc.decrypt(request.getParameter("Id_sub"))));
        String alleleMGI = request.getParameter("mutation_allele_mgi_symbol");
        String chrom = request.getParameter("mutation_chrom");
        String chromName = request.getParameter("mutation_chrom_anomaly_name");
        String chromDesc = request.getParameter("mutation_chrom_anomaly_descr");
        String domPattern = request.getParameter("mutation_dominance_pattern");
        String esCell = request.getParameter("mutation_es_cell_line");
        String lineNumber = request.getParameter("mutation_founder_line_number");

        String geneMGI = request.getParameter("mutation_gene_mgi_symbol");
        String mut = request.getParameter("mutation_mutagen");
        String bg = request.getParameter("mutation_original_backg");
        String bgText = request.getParameter("mutation_original_backg_text");
        String plasmid = request.getParameter("mutation_plasmid");
        String promoter = request.getParameter("mutation_promoter");
        String subtype = request.getParameter("mutation_subtype");
        String type = request.getParameter("mutation_type");
        String transMGI = request.getParameter("mutation_transgene_mgi_symbol");


        if (request.getParameter("action").equals("add")) {
            smd = new SubmissionMutationsDAO();
            smd.setId_sub(Integer.parseInt(enc.decrypt(request.getParameter("Id_sub"))));
            smd.setMutation_allele_mgi_symbol(alleleMGI);
            smd.setMutation_chrom(chrom);
            smd.setMutation_chrom_anomaly_name(chromName);
            smd.setMutation_chrom_anomaly_descr(chromDesc);
            smd.setMutation_dominance_pattern(domPattern);
            smd.setMutation_es_cell_line(esCell);
            smd.setMutation_founder_line_number(lineNumber);

            smd.setMutation_gene_mgi_symbol(geneMGI);
            smd.setMutation_mutagen(mut);
            smd.setMutation_original_backg(bg);
            smd.setMutation_original_backg_text(bgText);
            smd.setMutation_plasmid(plasmid);
            smd.setMutation_promoter(promoter);
            smd.setMutation_subtype(subtype);
            smd.setMutation_transgene_mgi_symbol(transMGI);
            smd.setMutation_type(type);
            //mutdaos.add(smd);
            //System.out.println("DEBUG INTEGER ISSUE LINE 72::" + smd.getId_sub());
            int id_sub = smd.getId_sub();
            // System.out.println("DEBUG INTEGER ISSUE LINE 72::" + id_sub);
            mm.save(smd);
            mutdaos = mm.getSubMutationBySubID(id_sub);

        } else if (request.getParameter("action").equals("edit")) {
            //edit records
            //get record from id
             //mutdaos = mm.getSubMutationBySubID(Integer.parseInt(enc.decrypt(request.getParameter("Id_sub"))));
            System.out.print("id_Mut==" + Integer.parseInt(request.getParameter("Id_mut")));
            SubmissionMutationsDAO smd = mm.getSubMutationBySubMutID(Integer.parseInt(request.getParameter("Id_mut")));
            returnedOut.put("SubMutDAO", smd);
            //load record in fields
   
        } else if (request.getParameter("action").equals("get")) {
            mutdaos = mm.getSubMutationBySubID(smd.getId_sub());
            
        } else if (request.getParameter("action").equals("delete")) {
            mm.delete(Integer.parseInt(request.getParameter("Id_mut")));
            mutdaos = mm.getSubMutationBySubID(Integer.parseInt(enc.decrypt(request.getParameter("Id_sub"))));
        }
        //return new ModelAndView("ajaxMutations.emma");
        System.out.println("mutdaos size==" + mutdaos.size());
        int count = mutdaos.size();
        if(smd != null){
            smd.setMutationCount(mutdaos.size());
        }
        
        returnedOut.put("count", count);
        returnedOut.put("mutdaos", mutdaos);
        return new ModelAndView("/publicSubmission/ajaxMutations", MAP_KEY, returnedOut);
    }
}
