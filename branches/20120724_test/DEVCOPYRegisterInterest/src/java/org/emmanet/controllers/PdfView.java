/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.controllers;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.emmanet.jobs.WebRequests;
import org.emmanet.model.WebRequestsDAO;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.emmanet.model.BibliosManager;
import org.emmanet.model.BibliosStrainsDAO;
import org.emmanet.model.CategoriesDAO;
import org.emmanet.model.CategoriesStrainsDAO;
import org.emmanet.model.MutationsStrainsDAO;
import org.emmanet.model.PeopleDAO;
import org.emmanet.model.PeopleManager;
import org.emmanet.model.RToolsDAO;
import org.emmanet.model.StrainsDAO;
import org.emmanet.model.StrainsManager;
import org.emmanet.model.Syn_StrainsDAO;

/**
 *
 * @author phil
 */
public class PdfView extends AbstractPdfView {

    private String pdfTitle;
    private boolean pdfConditions;

    @Override
    protected void buildPdfDocument(Map map, Document doc, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (map.get("WebRequestsDAO") != null && request.getParameter("type").equals("req")) {
            WebRequestsDAO wrd = new WebRequestsDAO();
            WebRequests wr = new WebRequests();
            wrd = (WebRequestsDAO) map.get("WebRequestsDAO");

            if (wrd.getRegister_interest() != null) {
                if (wrd.getRegister_interest().equals("1")) {
                    pdfTitle = "EMMA Strain Interest Registration Form";
                    pdfConditions = false;
                } else if (wrd.getRegister_interest().equals("0")) {
                    pdfTitle = "EMMA Mutant Request Form";
                    pdfConditions = true;
                }
            }

            Paragraph pHead = new Paragraph(
                    pdfTitle + "\n\n", FontFactory.getFont(
                    FontFactory.HELVETICA, 11));
            pHead.setAlignment(Element.ALIGN_CENTER);
            doc.add(pHead);
            doc.add(new Paragraph(pdfTitle + "\nRequest ID:" + wrd.getId_req() + "\n\n",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20)));
            Paragraph pSubHead = new Paragraph(
                    "Following data have been submitted to EMMA on " + wrd.getTimestamp(), FontFactory.getFont(
                    FontFactory.HELVETICA, 11));
            pSubHead.setAlignment(Element.ALIGN_CENTER);

            doc.add(pSubHead);
            doc.add(Chunk.NEWLINE);
            // Space padding underline
            Chunk underlined = new Chunk(
                    "                                                                                     " + "                                                                       ");
            underlined.setUnderline(new Color(0x00, 0x00, 0x00), 0.0f, 0.2f,
                    16.0f, 0.0f, PdfContentByte.LINE_CAP_BUTT);//Black line
            doc.add(underlined);
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
            // Set table cell widths equiv. to 25% and 75%
            float[] widths = {0.25f, 0.75f};
            PdfPTable table = new PdfPTable(widths);

            table.setWidthPercentage(100);

            PdfPCell cell = new PdfPCell(new Paragraph("Scientist\n\n", font));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);
            table.addCell("Title");
            table.addCell("" + wrd.getSci_title());
            table.addCell("Firstname");
            table.addCell("" + wrd.getSci_firstname());
            table.addCell("Surname");
            table.addCell("" + wrd.getSci_surname());
            table.addCell("E-mail");
            table.addCell("" + wrd.getSci_e_mail());
            table.addCell("Phone");
            table.addCell("" + wrd.getSci_phone());
            table.addCell("Fax");
            table.addCell("" + wrd.getSci_fax());
            PdfPCell cellShip = new PdfPCell(new Paragraph(
                    "\nShipping Contact\n\n", font));
            cellShip.setColspan(2);
            cellShip.setBorder(0);
            table.addCell(cellShip);

            table.addCell("Title");
            table.addCell("" + wrd.getCon_title());
            table.addCell("Firstname");
            table.addCell("" + wrd.getCon_firstname());
            table.addCell("Surname");
            table.addCell("" + wrd.getCon_surname());
            table.addCell("E-mail");
            table.addCell("" + wrd.getCon_e_mail());
            table.addCell("Phone");
            table.addCell("" + wrd.getCon_phone());
            table.addCell("Fax");
            table.addCell("" + wrd.getCon_fax());
            table.addCell("Institution");
            table.addCell("" + wrd.getCon_institution());
            table.addCell("Department");
            table.addCell("" + wrd.getCon_dept());
            table.addCell("Address Line 1");
            table.addCell("" + wrd.getCon_addr_1());
            table.addCell("Address Line 2");
            table.addCell("" + wrd.getCon_addr_2());
            table.addCell("County/province");
            table.addCell("" + wrd.getCon_province());
            table.addCell("Town");
            table.addCell("" + wrd.getCon_town());
            table.addCell("Postcode");
            table.addCell("" + wrd.getCon_postcode());
            table.addCell("Country");
            table.addCell("" + wrd.getCon_country());

            if (!wrd.getRegister_interest().equals("1")) {
                //webrequest is not a roi so these were set above so send them to pdf
                PdfPCell cellBill = new PdfPCell(new Paragraph(
                        "\nBilling Details\n\n", font));
                cellBill.setColspan(2);
                cellBill.setBorder(0);
                table.addCell(cellBill);

                table.addCell("VAT reference");
                table.addCell("" + wrd.getBil_vat());
                table.addCell("Purchase Order Number");
                table.addCell("" + wrd.getPO_ref());
                table.addCell("Title");
                table.addCell("" + wrd.getBil_title());
                table.addCell("Firstname");
                table.addCell("" + wrd.getBil_firstname());
                table.addCell("Surname");
                table.addCell("" + wrd.getBil_surname());
                table.addCell("E-mail");
                table.addCell("" + wrd.getBil_e_mail());
                table.addCell("Phone");
                table.addCell("" + wrd.getBil_phone());
                table.addCell("Fax");
                table.addCell("" + wrd.getBil_fax());
                table.addCell("Institution");
                table.addCell("" + wrd.getBil_institution());
                table.addCell("Department");
                table.addCell("" + wrd.getBil_dept());
                table.addCell("Address Line 1");
                table.addCell("" + wrd.getBil_addr_1());
                table.addCell("Address Line 2");
                table.addCell("" + wrd.getBil_addr_2());
                table.addCell("County/province");
                table.addCell("" + wrd.getBil_province());
                table.addCell("Town");
                table.addCell("" + wrd.getBil_town());
                table.addCell("Postcode");
                table.addCell("" + wrd.getBil_postcode());
                table.addCell("Country");
                table.addCell("" + wrd.getBil_country());
            }

            PdfPCell cellStrain = new PdfPCell(new Paragraph(
                    "\nStrain Details\n\n", font));
            cellStrain.setColspan(2);
            cellStrain.setBorder(0);

            table.addCell(cellStrain);
            table.addCell("Strain ID");
            table.addCell("" + wrd.getStrain_id());
            table.addCell("Strain name");
            table.addCell("" + wrd.getStrain_name());
            table.addCell("Common Name(s)");
            table.addCell("" + wrd.getCommon_name_s());

            if (wrd.getReq_material() != null) {
                PdfPCell cellMaterial = new PdfPCell(new Paragraph(
                        "\nRequested Material\n\n", font));
                cellMaterial.setColspan(2);
                cellMaterial.setBorder(0);

                table.addCell(cellMaterial);
                table.addCell("Material");
                table.addCell("" + wrd.getReq_material());
            }

            if (wrd.getLive_animals() != null) {
                table.addCell("Live Animals");
                table.addCell("Selected");
            }

            if (wrd.getFrozen_emb() != null) {
                table.addCell("Frozen Embryos");
                table.addCell("Selected");
            }

            if (wrd.getFrozen_spe() != null) {
                table.addCell("Frozen Sperm");
                table.addCell("Selected");
            }

            if (pdfConditions) {
                String text = "";
                String text1 = "";
                String text2 = "";
                String header = "";
                String header1 = "";
                String header2 = "";
                if (wrd.getApplication_type().equals("request_only")) {
                    text =
                            new StringBuilder().append(text).append(
                            "\nYou have indicated that you have read the conditions and agree to pay the transmittal fee " + "plus shipping costs.").toString();

                    header =
                            new StringBuilder().append(header).append("\nStandard request\n").toString();

                } else if (!wrd.getApplication_type().equals("request_only")) {
                    header1 =
                            new StringBuilder().append(header1).append("\nApplication for Transnational Access Activity").toString();
                    if (wrd.getApplication_type().equals("ta_only")) {
                        text1 =
                                new StringBuilder().append(text1).append("\nYou have indicated that you have read the conditions and have applied for free of charge TA only. "
                                + "In the case of the TA application being rejected the request process will be terminated.").toString();
                        header1 =
                                new StringBuilder().append(header1).append(" (TA Option B)\n").toString();
                    } else {
                        text1 =
                                new StringBuilder().append(text1).append("\nYou have indicated that you have read the conditions and have applied for free of charge TA "
                                + "and have agreed to pay the service charge plus shipping cost if the TA application is rejected.").toString();
                        header1 =
                                new StringBuilder().append(header1).append(" (TA Option A)\n").toString();
                    }

                    header2 =
                            new StringBuilder().append(header2).append("\n\nDescription of project (1/2 page) involving requested EMMA mouse mutant resource. "
                            + "The project description will be used by the Evaluation Committee for selection of applicants:").toString();
                }

                if (!wrd.getApplication_type().equals("request_only")) {
                    //  table.addCell("" + model.get("ta_proj_desc"));
                    text2 =
                            new StringBuilder().append(text2).append("\n\n ").append(wrd.getProject_description()).toString();
                }

                if (!wrd.getApplication_type().equals("request_only")) {
                    //we have a ta header and text to add
                    PdfPCell cellConditions1 = new PdfPCell(new Paragraph(header1, font));

                    cellConditions1.setColspan(2);
                    cellConditions1.setBorder(0);
                    table.addCell(cellConditions1);

                    PdfPCell cellConditions2 = new PdfPCell(new Paragraph(text1));

                    cellConditions2.setColspan(2);
                    cellConditions2.setBorder(0);
                    table.addCell(cellConditions2);

                    PdfPCell cellConditions3 = new PdfPCell(new Paragraph(header2, font));

                    cellConditions3.setColspan(2);
                    cellConditions3.setBorder(0);
                    table.addCell(cellConditions3);

                    PdfPCell cellConditions4 = new PdfPCell(new Paragraph(text2));

                    cellConditions4.setColspan(2);
                    cellConditions4.setBorder(0);
                    table.addCell(cellConditions4);

                } else {

                    PdfPCell cellConditions = new PdfPCell(new Paragraph(header, font));
                    cellConditions.setColspan(2);
                    cellConditions.setBorder(0);
                    table.addCell(cellConditions);

                    PdfPCell cellConditionsTxt = new PdfPCell(new Paragraph(text));
                    cellConditionsTxt.setColspan(2);
                    cellConditionsTxt.setBorder(0);
                    table.addCell(cellConditionsTxt);
                }
            }
            doc.add(table);
        }

        if (map.get("StrainsDAO") != null && request.getParameter("type").equals("sub")) {
            StrainsDAO sd = new StrainsDAO();
            StrainsManager sm = new StrainsManager();
            PeopleDAO pd = new PeopleDAO();
            PeopleManager pm = new PeopleManager();
            BibliosManager bm = new BibliosManager();

            sd = (StrainsDAO) map.get("StrainsDAO");
            pd = pm.getPerson(sd.getPer_id_per_contact());//For shipping details
            pdfTitle = "EMMA Mutant Submission Form";

            Paragraph pHead = new Paragraph(
                    pdfTitle + "\n\n", FontFactory.getFont(
                    FontFactory.HELVETICA, 11));
            pHead.setAlignment(Element.ALIGN_CENTER);
            doc.add(pHead);
            doc.add(new Paragraph(pdfTitle + "\n\n",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20)));
            Paragraph pSubHead = new Paragraph(
                    "Following data have been submitted to EMMA on " + sd.getArchiveDAO().getSubmitted(), FontFactory.getFont(
                    FontFactory.HELVETICA, 11));
            pSubHead.setAlignment(Element.ALIGN_CENTER);

            doc.add(pSubHead);
            doc.add(Chunk.NEWLINE);
            // Space padding underline
            Chunk underlined = new Chunk(
                    "                                                                                     " + "                                                                       ");
            underlined.setUnderline(new Color(0x00, 0x00, 0x00), 0.0f, 0.2f,
                    16.0f, 0.0f, PdfContentByte.LINE_CAP_BUTT);//Black line
            doc.add(underlined);
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);

            Font f = FontFactory.getFont(FontFactory.ZAPFDINGBATS, 11);
            char checkbox = '\u2611';

//sd.getEmma_id();
            // Set table cell widths equiv. to 25% and 75%
            float[] widths = {0.25f, 0.75f};
            PdfPTable table = new PdfPTable(widths);

            table.setWidthPercentage(100);

            PdfPCell cell = new PdfPCell(new Paragraph("Producer\n\n", font));
            cell.setColspan(2);
            cell.setBorder(0);

            table.addCell(cell);
            table.addCell("Title");
            table.addCell("" + sd.getPeopleDAO().getTitle());
            table.addCell("Firstname");
            table.addCell("" + sd.getPeopleDAO().getFirstname());
            table.addCell("Surname");
            table.addCell("" + sd.getPeopleDAO().getSurname());
            table.addCell("E-mail");
            table.addCell("" + sd.getPeopleDAO().getEmail());
            table.addCell("Phone");
            table.addCell("" + sd.getPeopleDAO().getPhone());
            table.addCell("Fax");
            table.addCell("" + sd.getPeopleDAO().getFax());
            table.addCell("Institution");
            table.addCell("" + sd.getPeopleDAO().getLabsDAO().getName());
            table.addCell("Department");
            table.addCell("" + sd.getPeopleDAO().getLabsDAO().getDept());
            table.addCell("Address Line 1");
            table.addCell("" + sd.getPeopleDAO().getLabsDAO().getAddr_line_1());
            table.addCell("Address Line 2");
            table.addCell("" + sd.getPeopleDAO().getLabsDAO().getAddr_line_2());
            table.addCell("County/province");
            table.addCell("" + sd.getPeopleDAO().getLabsDAO().getProvince());
            table.addCell("Town");
            table.addCell("" + sd.getPeopleDAO().getLabsDAO().getTown());
            table.addCell("Postcode");
            table.addCell("" + sd.getPeopleDAO().getLabsDAO().getPostcode());
            table.addCell("Country");
            table.addCell("" + sd.getPeopleDAO().getLabsDAO().getCountry());

            cell = new PdfPCell(new Paragraph("\nShipping contact\n\n", font));
            cell.setColspan(2);
            cell.setBorder(0);

            table.addCell(cell);
            table.addCell("Title");
            table.addCell("" + pd.getTitle());
            table.addCell("Firstname");
            table.addCell("" + pd.getFirstname());
            table.addCell("Surname");
            table.addCell("" + pd.getSurname());
            table.addCell("E-mail");
            table.addCell("" + pd.getEmail());
            table.addCell("Phone");
            table.addCell("" + pd.getPhone());
            table.addCell("Fax");
            table.addCell("" + pd.getFax());
            table.addCell("Institution");
            table.addCell("" + pd.getLabsDAO().getName());
            table.addCell("Department");
            table.addCell("" + pd.getLabsDAO().getDept());
            table.addCell("Address Line 1");
            table.addCell("" + pd.getLabsDAO().getAddr_line_1());
            table.addCell("Address Line 2");
            table.addCell("" + pd.getLabsDAO().getAddr_line_2());
            table.addCell("County/province");
            table.addCell("" + pd.getLabsDAO().getProvince());
            table.addCell("Town");
            table.addCell("" + pd.getLabsDAO().getTown());
            table.addCell("Postcode");
            table.addCell("" + pd.getLabsDAO().getPostcode());
            table.addCell("Country");
            table.addCell("" + pd.getLabsDAO().getCountry());



            doc.add(table);
            doc.add(Chunk.NEWLINE);
            doc.add(Chunk.NEWLINE);
            doc.add(underlined);
            table = new PdfPTable(widths);

            table.setWidthPercentage(100);

            /*
            
            Mutation information section
            
             */
            
            /*strain name and description*/
            Set sSynonym = sd.getSyn_strainsDAO();
            String strainName = "";
            StringBuffer sName = new StringBuffer();
            for (Iterator it = sSynonym.iterator(); it.hasNext();) {
                Syn_StrainsDAO synDAO = (Syn_StrainsDAO) it.next();
                if (synDAO.getName() != null) {
                    sName = new StringBuffer(strainName).append(synDAO.getName().toString());
                }
            }

            cell = new PdfPCell(new Paragraph("\nYour strain/line name " + sName + "\n", font));

            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            doc.add(table);
            doc.add(Chunk.NEWLINE);
            doc.add(Chunk.NEWLINE);
            doc.add(underlined);
            table = new PdfPTable(widths);

            table.setWidthPercentage(100);


            cell = new PdfPCell(new Paragraph("Mutation/s\n\n", font));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            /* cell = new PdfPCell(new Paragraph("" + sd.getCharact_gen() + sd.getMaintenance() + sd.getPheno_text()));
            cell.setColspan(2);
            table.addCell(cell);*/
            /*END of strain name and description*/

            /* MUTATIONS */
            String origBgName = "";
            origBgName=sd.getBackgroundDAO().getName();
            Set sMutations = sd.getMutationsStrainsDAO();

            StringBuffer sDom = new StringBuffer();
            for (Iterator it = sMutations.iterator(); it.hasNext();) {
                MutationsStrainsDAO mutDAO = (MutationsStrainsDAO) it.next();
                if (mutDAO.getMutationsDAO().getDominance() != null) {
                    sDom = new StringBuffer(sDom).append(mutDAO.getMutationsDAO().getDominance().toString());
                }

                cell = new PdfPCell(new Paragraph("Main type: " + cleanNULLS(mutDAO.getMutationsDAO().getMain_type(),false) , FontFactory.getFont(FontFactory.HELVETICA, 11)));
                cell.setColspan(2);
                cell.setBorder(0);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("Sub type: " + cleanNULLS(mutDAO.getMutationsDAO().getSub_type(),false) , FontFactory.getFont(FontFactory.HELVETICA, 11)));
                cell.setColspan(2);
                cell.setBorder(0);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("Affected gene: " + cleanNULLS(mutDAO.getMutationsDAO().getAllelesDAO().getGenesDAO().getName(),false) , FontFactory.getFont(FontFactory.HELVETICA, 11)));
                cell.setColspan(2);
                cell.setBorder(0);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("MGI of affected gene: " + cleanNULLS(mutDAO.getMutationsDAO().getAllelesDAO().getGenesDAO().getMgi_ref(),false) , FontFactory.getFont(FontFactory.HELVETICA, 11)));
                cell.setColspan(2);
                cell.setBorder(0);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("Affected allele: " + cleanNULLS(mutDAO.getMutationsDAO().getAllelesDAO().getName(),false) , FontFactory.getFont(FontFactory.HELVETICA, 11)));
                cell.setColspan(2);
                cell.setBorder(0);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("MGI of affected allele: " + cleanNULLS(mutDAO.getMutationsDAO().getAllelesDAO().getMgi_ref(), false) , FontFactory.getFont(FontFactory.HELVETICA, 11)));
                cell.setColspan(2);
                cell.setBorder(0);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("Affected chromosome: " + cleanNULLS(mutDAO.getMutationsDAO().getAllelesDAO().getGenesDAO().getChromosome(), false) , FontFactory.getFont(FontFactory.HELVETICA, 11)));
                cell.setColspan(2);
                cell.setBorder(0);
                table.addCell(cell);
                
                cell = new PdfPCell(new Paragraph("Dominance pattern: " + cleanNULLS(mutDAO.getMutationsDAO().getDominance(), false) , FontFactory.getFont(FontFactory.HELVETICA, 11)));
                cell.setColspan(2);
                cell.setBorder(0);
                table.addCell(cell);

               // origBgName = mutDAO.getMutationsDAO().getBackgroundDAO().getName();
                
                if (mutDAO.getMutationsDAO().getBackgroundDAO() != null) {
                    cell = new PdfPCell(new Paragraph("Original background: " + cleanNULLS(mutDAO.getMutationsDAO().getBackgroundDAO().getName(), false), FontFactory.getFont(FontFactory.HELVETICA, 11)));
                } else {
                    cell = new PdfPCell(new Paragraph("\n"));
                }
                
                cell.setColspan(2);
                cell.setBorder(0);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("ES cell line: " + cleanNULLS(mutDAO.getMutationsDAO().getTm_esline(),false) + "\n\n", FontFactory.getFont(FontFactory.HELVETICA, 11)));
                cell.setColspan(2);
                cell.setBorder(0);
                table.addCell(cell);
            }

            /* END MUTATIONS*/

            doc.add(table);
            doc.add(Chunk.NEWLINE);
            doc.add(Chunk.NEWLINE);
            doc.add(underlined);
            table = new PdfPTable(widths);

            table.setWidthPercentage(100);

            /* STRAIN GENETIC BACKGROUND */
            cell = new PdfPCell(new Paragraph("Strain genetic descriptionn\n\n", font));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("Short genetic description of your mouse strain\n(this will be used in the public web listing)\n\n", FontFactory.getFont(FontFactory.HELVETICA, 11)));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);
            
            cell = new PdfPCell(new Paragraph("" + cleanNULLS(sd.getCharact_gen(), true)));
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("\n\nOn what genetic background is this strain currently maintained? " + cleanNULLS(origBgName, false)/*cleanNULLS(sd.getBackgroundDAO().getName()) */, FontFactory.getFont(FontFactory.HELVETICA, 11)));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);


            cell = new PdfPCell(new Paragraph("\n\nONE mutant strain is defined by its specific set of mutation(s) "
                    + "and its specific genetic background. Therefore strains with the same set of mutation(s) but different "
                    + "backgrounds do require DISTINCT submission forms (i.e) ONE form for each background\n\n", font));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            /* END OF STRAIN GENETIC BACKGROUND */

            doc.add(table);
            doc.add(Chunk.NEWLINE);
            doc.add(Chunk.NEWLINE);
            doc.add(underlined);
            table = new PdfPTable(widths);

            table.setWidthPercentage(100);

            /* STRAIN PHENOTYPE DESCRIPTION */

            cell = new PdfPCell(new Paragraph("Strain phenotype description\n\n", font));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("Short phenotype description of your mouse strain\n(this will be used in the public web listing)\n\n", FontFactory.getFont(FontFactory.HELVETICA, 11)));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("" + cleanNULLS(sd.getPheno_text(), true)));
            cell.setColspan(2);
            table.addCell(cell);

            /* END OF STRAIN PHENOTYPE DESCRIPTION */

            doc.add(table);
            doc.add(Chunk.NEWLINE);
            doc.add(Chunk.NEWLINE);
            doc.add(underlined);
            table = new PdfPTable(widths);

            table.setWidthPercentage(100);

            /* BIBLIOGRAPHY */
            List bibliosStrains = bm.BibliosStrains(sd.getId_str());


            cell = new PdfPCell(new Paragraph("Relevant bibliographic/database references\n\n", font));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            for (Iterator it = bibliosStrains.iterator(); it.hasNext();) {
                BibliosStrainsDAO bsdao = (BibliosStrainsDAO) it.next();

                cell = new PdfPCell(new Paragraph("PubMed ID: " + bsdao.getBibliosDAO().getPubmed_id() , FontFactory.getFont(FontFactory.HELVETICA, 11)));
                cell.setColspan(2);
                cell.setBorder(0);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("Title: " + bsdao.getBibliosDAO().getTitle() , FontFactory.getFont(FontFactory.HELVETICA, 11)));
                cell.setColspan(2);
                cell.setBorder(0);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("Author: " + bsdao.getBibliosDAO().getAuthor1() , FontFactory.getFont(FontFactory.HELVETICA, 11)));
                cell.setColspan(2);
                cell.setBorder(0);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("Other author(s): " + bsdao.getBibliosDAO().getAuthor2() , FontFactory.getFont(FontFactory.HELVETICA, 11)));
                cell.setColspan(2);
                cell.setBorder(0);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("Journal: " + bsdao.getBibliosDAO().getJournal() , FontFactory.getFont(FontFactory.HELVETICA, 11)));
                cell.setColspan(2);
                cell.setBorder(0);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("Year: " + cleanNULLS(bsdao.getBibliosDAO().getYear(), false) , FontFactory.getFont(FontFactory.HELVETICA, 11)));
                cell.setColspan(2);
                cell.setBorder(0);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("Volume: " + bsdao.getBibliosDAO().getVolume() , FontFactory.getFont(FontFactory.HELVETICA, 11)));
                cell.setColspan(2);
                cell.setBorder(0);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("Pages: " + bsdao.getBibliosDAO().getPages() + "\n\n", FontFactory.getFont(FontFactory.HELVETICA, 11)));
                cell.setColspan(2);
                cell.setBorder(0);
                table.addCell(cell);

            }

            /* END BIBLIOGRAPHY */

            doc.add(table);
            doc.add(Chunk.NEWLINE);
            doc.add(Chunk.NEWLINE);
            doc.add(underlined);
            table = new PdfPTable(widths);

            table.setWidthPercentage(100);

            /* BREEDING PROCEDURES AND SANITARY STATUS OF YOUR STRAIN */

            cell = new PdfPCell(new Paragraph("Breeding procedures and sanitary status of your strain\n\n", font));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("Describe breeding history (was it backcrossed/outcrossed/inbred? "
                    + "How many generations? How is it currently bred?))\n\n", FontFactory.getFont(FontFactory.HELVETICA, 11)));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("" + cleanNULLS(sd.getMaintenance(), true)));
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("\n\nAre homozygous mice viable? " + cleanNULLS(sd.getMutant_viable(), false), FontFactory.getFont(
                    FontFactory.HELVETICA, 11)));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("Are homozygous mice fertile?  " + cleanNULLS(sd.getMutant_fertile(), false), FontFactory.getFont(
                    FontFactory.HELVETICA, 11)));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("Does the strain necessarily require homozygous matings? " + cleanNULLS(sd.getRequire_homozygous(), false), FontFactory.getFont(FontFactory.HELVETICA, 11)));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("Is the strain immunocompromised? " + cleanNULLS(sd.getImmunocompromised(), false), FontFactory.getFont(FontFactory.HELVETICA, 11)));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("\n\nWhat is the current sanitary status of the strain of mice being archived "
                    + "(detected viruses, bacteria, parasites, etc.)\n\n", FontFactory.getFont(FontFactory.HELVETICA, 11)));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);
            if (sd.getResiduesDAO() != null) {
                cell = new PdfPCell(new Paragraph("" + cleanNULLS(sd.getResiduesDAO().getCurrent_sanitary_status(), true)));
            } else {
                cell = new PdfPCell(new Paragraph("\n"));
            }
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("\nSpecific information on animal husbandry (nutrition, special handling, light cycle ...)\n\n", FontFactory.getFont(FontFactory.HELVETICA, 11)));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            if (sd.getResiduesDAO() != null) {
                cell = new PdfPCell(new Paragraph("" + cleanNULLS(sd.getResiduesDAO().getAnimal_husbandry(), true)));
            } else {
                cell = new PdfPCell(new Paragraph("\n"));
            }
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("\n\nPlease note that under certain circumstances "
                    + "(e.g.: long-term cryopreservation by sperm freezing) the strain's original genotype will not "
                    + "always be available for future reconstitution of live colonies. Therefore, the original genetic "
                    + "background cannot be guaranteed.\n\n", font));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            /* END OF  BREEDING PROCEDURES AND SANITARY STATUS OF YOUR STRAIN */

            doc.add(table);
            doc.add(Chunk.NEWLINE);
            doc.add(Chunk.NEWLINE);
            doc.add(underlined);
            table = new PdfPTable(widths);

            table.setWidthPercentage(100);

            /* STRAIN CHARACTERISATION */

            cell = new PdfPCell(new Paragraph("Characterisation of your strain? \n\n", font));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("By genotyping \n"
                    + "(e.g. sequence of PCR primers and PCR settings,Southern probes and "
                    + "hybridization protocol)\n\n", FontFactory.getFont(FontFactory.HELVETICA, 11)));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            if (sd.getResiduesDAO() != null) {
                cell = new PdfPCell(new Paragraph("" + cleanNULLS(sd.getResiduesDAO().getChar_genotyping(), true)));
            } else {
                cell = new PdfPCell(new Paragraph("\n"));
            }
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("\nBy phenotyping (e.g. coat colour)\n\n", FontFactory.getFont(FontFactory.HELVETICA, 11)));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            if (sd.getResiduesDAO() != null) {
                cell = new PdfPCell(new Paragraph("" + cleanNULLS(sd.getResiduesDAO().getChar_phenotyping(), true)));
            } else {
                cell = new PdfPCell(new Paragraph("\n"));
            }
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("\nBy any other means that is not a genotyping nor a phenotyping procedure\n\n", FontFactory.getFont(FontFactory.HELVETICA, 11)));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            if (sd.getResiduesDAO() != null) {
                cell = new PdfPCell(new Paragraph("" + cleanNULLS(sd.getResiduesDAO().getChar_other(), true)));
            } else {
                cell = new PdfPCell(new Paragraph("\n"));
            }
            cell.setColspan(2);
            table.addCell(cell);


            //original taken from submissions table now unused


            /* END OF STRAIN CHARACTERISATION */

            doc.add(table);
            doc.add(Chunk.NEWLINE);
            doc.add(Chunk.NEWLINE);
            doc.add(underlined);
            table = new PdfPTable(widths);

            table.setWidthPercentage(100);

            /* SCIENTIFIC INTEREST */

            cell = new PdfPCell(new Paragraph("Scientific interest \n\n", font));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("Does this mutant strain model a human condition? " + cleanNULLS(sd.getHuman_model(), false), FontFactory.getFont(FontFactory.HELVETICA, 11)));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            if (sd.getResiduesDAO() != null) {
                cell = new PdfPCell(new Paragraph("OMIM IDs " + cleanNULLS(sd.getResiduesDAO().getOmim_ids(), false), FontFactory.getFont(FontFactory.HELVETICA, 11)));
            } else {
                cell = new PdfPCell(new Paragraph("OMIM IDs ", FontFactory.getFont(FontFactory.HELVETICA, 11)));
            }
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("Otherwise please describe human condition in the box below\n\n", FontFactory.getFont(FontFactory.HELVETICA, 11)));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("" + cleanNULLS(sd.getHuman_model_desc(), true)));
            cell.setColspan(2);
            table.addCell(cell);

            Set cs = sd.getCategoriesStrainsDAO();
            StringBuffer categories = new StringBuffer();
            for (Iterator it = cs.iterator(); it.hasNext();) {
                CategoriesStrainsDAO cd = (CategoriesStrainsDAO) it.next();
                categories = new StringBuffer(categories).append(cd.getCategoriesDAO().getMain_cat().toString()).append("\n        ");

            }

            cell = new PdfPCell(new Paragraph("\n\nResearch areas:\n\n        " + categories + "\n", FontFactory.getFont(FontFactory.HELVETICA, 11)));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            Set sRTools = sd.getRtoolsDAO();
            StringBuffer rtools = new StringBuffer();
            for (Iterator it = sRTools.iterator(); it.hasNext();) {
                RToolsDAO rtd = (RToolsDAO) it.next();
                rtools = new StringBuffer(rtools).append(rtd.getCvrtoolsDAO().getDescription().toString()).append("\n        ");
            }

            cell = new PdfPCell(new Paragraph("Research tools:\n\n        " + rtools + "\n", FontFactory.getFont(FontFactory.HELVETICA, 11)));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            /* END OF SCIENTIFIC INTEREST */

            doc.add(table);
            doc.add(Chunk.NEWLINE);
            doc.add(Chunk.NEWLINE);
            doc.add(underlined);
            table = new PdfPTable(widths);

            table.setWidthPercentage(100);

            /* ADDITIONAL INFORMATION */

            cell = new PdfPCell(new Paragraph("Additional information\n\n", font));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            if (sd.getResiduesDAO() != null) {
                cell = new PdfPCell(new Paragraph("How many requests for this strain have you received in the last 6 months? "
                        + cleanNULLS(sd.getResiduesDAO().getNumber_of_requests(), false), FontFactory.getFont(FontFactory.HELVETICA, 11)));
            } else {
                cell = new PdfPCell(new Paragraph("How many requests for this strain have you received in the last 6 months? ", FontFactory.getFont(FontFactory.HELVETICA, 11)));
            }

            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            if (sd.getResiduesDAO() != null) {
                cell = new PdfPCell(new Paragraph("Is this strain being deposited with any other institution or biotechnology company? "
                        + cleanNULLS(sd.getResiduesDAO().getDeposited_elsewhere(), false), FontFactory.getFont(FontFactory.HELVETICA, 11)));
            } else {
                cell = new PdfPCell(new Paragraph("Is this strain being deposited with any other institution or biotechnology company? ", FontFactory.getFont(FontFactory.HELVETICA, 11)));
            }
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            if (sd.getResiduesDAO() != null) {
                cell = new PdfPCell(new Paragraph("Are other laboratories producing similar mutant strains? "
                        + cleanNULLS(sd.getResiduesDAO().getOther_labos(), false), FontFactory.getFont(FontFactory.HELVETICA, 11)));
            } else {
                cell = new PdfPCell(new Paragraph("Are other laboratories producing similar mutant strains? ", FontFactory.getFont(FontFactory.HELVETICA, 11)));
            }
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);
            String IPRights = "";
            if (sd.getResiduesDAO() != null) {
                IPRights = sd.getResiduesDAO().getIp_rights();
            } else {
                //do nothing
            }
            cell = new PdfPCell(new Paragraph("Are there particular intellectual property rights linked to this strain? "
                    + IPRights + "\n\n", FontFactory.getFont(FontFactory.HELVETICA, 11)));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            if (sd.getResiduesDAO() != null) {
                if (sd.getResiduesDAO().getIpr_description() != null) {
                    cell = new PdfPCell(new Paragraph("" + cleanNULLS(sd.getResiduesDAO().getIpr_description(), true)));
                    cell.setColspan(2);
                    table.addCell(cell);
                }
            }

            cell = new PdfPCell(new Paragraph("\nAre you the exclusive owner of the strain? "
                    + cleanNULLS(sd.getExclusive_owner(), false) + "\n\n", FontFactory.getFont(FontFactory.HELVETICA, 11)));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("" + cleanNULLS(sd.getEx_owner_description(), true)));
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("\n\nWere any of the following techniques used in the construction of this mutant?\n\n", FontFactory.getFont(FontFactory.HELVETICA, 11)));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            if (sd.getResiduesDAO() != null) {
                cell = new PdfPCell(new Paragraph("Cre recombinase-loxP technology? " + cleanNULLS(sd.getResiduesDAO().getCrelox(), false), FontFactory.getFont(FontFactory.HELVETICA, 11)));
            } else {
                cell = new PdfPCell(new Paragraph("Cre recombinase-loxP technology? ", FontFactory.getFont(FontFactory.HELVETICA, 11)));
            }
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            if (sd.getResiduesDAO() != null) {
                cell = new PdfPCell(new Paragraph("FLP recombinase technology? " + cleanNULLS(sd.getResiduesDAO().getFlp(), false), FontFactory.getFont(FontFactory.HELVETICA, 11)));
            } else {
                cell = new PdfPCell(new Paragraph("FLP recombinase technology? ", FontFactory.getFont(FontFactory.HELVETICA, 11)));
            }
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            if (sd.getResiduesDAO() != null) {
                cell = new PdfPCell(new Paragraph("TET-system technology? " + cleanNULLS(sd.getResiduesDAO().getTet(), false), FontFactory.getFont(FontFactory.HELVETICA, 11)));
            } else {
                cell = new PdfPCell(new Paragraph("TET-system technology? ", FontFactory.getFont(FontFactory.HELVETICA, 11)));
            }
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);


            if (sd.getResiduesDAO() != null) {
                cell = new PdfPCell(new Paragraph("\n\nIf the submission request is accepted when could you provide "
                        + "10 females and 6 males (5-12 weeks old)?"
                        + "\nEstimated date of shipping\nMonth: " + cleanNULLS(sd.getResiduesDAO().getWhen_mice_month(), false) + "\nYear: " + cleanNULLS(sd.getResiduesDAO().getWhen_mice_year(), false), FontFactory.getFont(FontFactory.HELVETICA, 11)));
            } else {
                cell = new PdfPCell(new Paragraph("\n\nIf the submission request is accepted when could you provide "
                        + "10 females and 6 males (5-12 weeks old)?"
                        + "\nEstimated date of shipping\nMonth: \nYear:", FontFactory.getFont(FontFactory.HELVETICA, 11)));
            }

            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);


            if (sd.getResiduesDAO() != null) {
                cell = new PdfPCell(new Paragraph("\n\nIf unable to provide this number of mice how many could you provide and when?"
                        + "\nEstimated date of shipping\nMonth: " + cleanNULLS(sd.getResiduesDAO().getWhen_how_many_month(), false)
                        + "\nYear: " + cleanNULLS(sd.getResiduesDAO().getWhen_how_many_year(), false)
                        + "\nNumber of males: " + cleanNULLS(sd.getResiduesDAO().getWhen_how_many_males(), false)
                        + "\nNumber of females: " + cleanNULLS(sd.getResiduesDAO().getWhen_how_many_females(), false),
                        FontFactory.getFont(FontFactory.HELVETICA, 11)));
            } else {
                cell = new PdfPCell(new Paragraph("\n\nIf unable to provide this number of mice how many could you provide and when?"
                        + "\nEstimated date of shipping\nMonth: "
                        + "\nYear: "
                        + "\nNumber of males: "
                        + "\nNumber of females: ",
                        FontFactory.getFont(FontFactory.HELVETICA, 11)));
            }
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            String delayedRelease = "";
            if (sd.getResiduesDAO() != null) {
                delayedRelease = cleanNULLS(sd.getResiduesDAO().getDelayed_wanted(), false);
            }
            if (delayedRelease != null && delayedRelease.startsWith("yes")) {
                delayedRelease = new StringBuffer(delayedRelease).append(" (briefly explain below)\n\n ").toString();
            }

            cell = new PdfPCell(new Paragraph("\nDo you require delayed release for your strain? " + delayedRelease, FontFactory.getFont(FontFactory.HELVETICA, 11)));
            cell.setColspan(2);
            cell.setBorder(0);
            table.addCell(cell);

            if (delayedRelease != null && delayedRelease.startsWith("yes")) {
                cell = new PdfPCell(new Paragraph("" + sd.getResiduesDAO().getDelayed_description()));
                cell.setColspan(2);
                table.addCell(cell);
            }
            /* END OF ADDITIONAL INFORMATION */
            doc.add(table);
        }
    }

    public String cleanNULLS(String toClean, boolean cell) {
        String cleaned = "";
        if (toClean == null || toClean.isEmpty() && cell) {
            cleaned = "\n";
        } else if (toClean == null || toClean.isEmpty()  && !cell) {
            cleaned = "";
        } else {
            cleaned = toClean;
        }
        return cleaned;
    }
}
