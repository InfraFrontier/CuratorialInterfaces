/*
 * CreateSpreadsheet.java
 *
 * Created on 20 March 2008, 10:56
 *
 */
// TODO WITH THE CURRENT SITUATION OF USING AN EXISTING EXTERNAL SCRIPT ARCHIVE2EXCEL.PL TO CREATE SPREADSHEET
// THIS CODE IS CURRENTLY UNUSED.
//SEE SPREADSHEETCONTROLLER
package org.emmanet.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.emmanet.jobs.WebRequests;

import org.emmanet.model.StrainsDAO;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 *
 * @author phil
 */
@SuppressWarnings("deprecation")
public class CreateSpreadsheet extends AbstractExcelView {
    
    private HSSFSheet sheet;
    private WebRequests wr;
    ////private DashboardManager wr;
    private List columns = null;
    private List data = null;
    StrainsDAO sd;
    
    protected void buildExcelDocument(Map map, HSSFWorkbook wb,
            HttpServletRequest httpServletRequest, HttpServletResponse
            httpServletResponse) throws Exception {
        
        data = (List)map.get("data");
        columns = (List)map.get("columns");
        String sheets[] = (String[]) map.get("sheets");
        int freeze = 1;
        String filename = (String) map.get("filename");
        
        
        // set list values
        if (filename.equals("stats")) {
            
            System.out.println("################################################# create spreadsheet called");
            
            /* Due to time constraints and difficulties returning values from model that aren't affected by duplicate col names
             * the decision was made to use existing perl script for now to generate statistics workbook
             * Perl script called from org.emmanet.utils.RunShell.java
             */
            RunShell rs = new RunShell();
            //TODO BEFORE GOING LIVE CHECK THAT THIS RUNS /INTERNAL/SCRIPTS/PERL/archive2excel.pl
            // AND ADD PATH AND SCRIPT IN PLACE OF BELOW AND UNCOMMENT //#####
            //String[] run = {"/home/phil/","string.pl"};
            //Hardcoded for now until this is rebuilt TODO
           String[] run = {"/data/web/internal/scripts/perl/","archive2excel.pl -v -o statistics.xls -u phil.1 -p wilkinson -d emmastr"};
           //String[] run = {"/data/web/EmmaStrains/cron/","statistics.sh search strains emmastr"}; //not working either
            rs.execute(run);
            //#####httpServletResponse.sendRedirect("http://internal.emmanet.org/statistics/statistics.xls");
            httpServletResponse.sendRedirect("http://internal.emmanet.org/statistics/statistics.xls");
            
            columns = (List)map.get("columns_emmaStrains");
            data = (List)map.get("data_emmaStrains");
        }
        
        //SEND ARRAY OF NAMES TO CREATE SHEETS AND COUNT
        for (int i = 0; i < sheets.length; i++) {
            
            sheet = wb.createSheet(sheets[i]);
            // Create a row and put some cells in it. Rows are 0 based.
            for (int ii = 0; ii < data.size(); ii++) {
                
                HSSFRow row = sheet.createRow((short) ii);
                HSSFCell cell = null;
                //HEADER STYLE
                HSSFCellStyle style = wb.createCellStyle();
                style.setFillBackgroundColor(HSSFColor.AQUA.index);
                style.setFillForegroundColor(HSSFColor.WHITE.index);
                style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                
                for (int iii = 0; iii < columns.size(); iii++) {
                    if (ii == 0) {
                        // first row for header col name
                        for (int cn=0; cn < columns.size();cn++) {
                            cell = row.createCell((short) cn);
                            cell.setCellValue((String) columns.get(cn));
                            //cell.setCellValue((HSSFRichTextString) columns.get(cn));
                            
                            //cell.setCellStyle(style);
                            //System.out.println((String) columns.get(cn));
                        }
                        
                    } else {
                        
                        for (int cn=0; cn < columns.size();cn++) {
                            
                            cell = row.createCell((short) cn);//iii
                            String s = (String) columns.get(cn);
                            //TODO FOR TEST
                            //SET DAOs
                            if (filename.equals("stats")) {
                                //sd = (StrainsDAO) data.get(ii);
                                //columns = (List)map.get("columns_emmaStrains");
                                // TODO IF STATEMENTS TO GET DATA AND SET CELL CONTENTS
                                //cell.setCellValue((String)data.get(ii).toString());
                                
                                //cell.setCellValue((String)data.get(ii).toString());
                                //TODO CURRENTLY ITERATING OVER LIST RESULTS THEN ITERATING AGAIN OVER OBJECT
                                // NEED TO REMOVE OR REPOSITION OBJECT ITERATOR it line 68
                                //#Iterator it = data.iterator();
                                //#while (it.hasNext()) {
                                // /*while (ii < data.length) {
                              ////  Object[] dataVal = (Object[])data.get(ii);
                                // *do rest here
                                // }
                                // */
                                //# Object[] dataVal = (Object[]) it.next();
                               //System.out.println(ii + " +=+=+ ");
                                //cell.setCellValue(dataVal[0].toString());
                               //System.out.println(dataVal[0]);
                               //System.out.println(dataVal[1]);
                                //cell.setCellValue(dataVal[1].toString());
                              //System.out.println(dataVal[2]);
                               // cell.setCellValue(dataVal[2].toString());
                              //System.out.println(dataVal[3]);
                               // cell.setCellValue(dataVal[3].toString());
                              // System.out.println(dataVal[4]);
                               // cell.setCellValue(dataVal[4].toString());
                                
                               //// if (s.equals("Muttype")) cell.setCellValue(dataVal[0].toString());
                                //if (s.equals("subtype")) cell.setCellValue(dataVal[1].toString());
                                //System.out.println(dataVal[1]);
                               //// if (s.equals("EMMA ID")) cell.setCellValue(dataVal[2].toString());
                                //System.out.println(dataVal[2].toString());
                               //// if (s.equals("name")) cell.setCellValue(dataVal[3].toString());
                               //// if (s.equals("gene")) cell.setCellValue(dataVal[4].toString());
                                //# }
                                
                                //cell.setCellValue(dataVal.toString());
                                // System.out.println(dataVal[0]);
                                // returnedOut.put("LabServiceTimeToArch" + i, laboServiceTimes[0]);
                                // cell.setCellValue( (HSSFRichTextString) data.get(ii));
                                
                            } else {
                                sd = (StrainsDAO) data.get(ii);
                                // }
                                
                                //TODO //TODO FOR TEST END
                                
                                if (s.equals("id_str")) cell.setCellValue(sd.getId_str());
                                if (s.equals("code_internal")) cell.setCellValue(sd.getCode_internal());
                                if (s.equals("name")) cell.setCellValue(sd.getName());
                                if (s.equals("health_status")) cell.setCellValue(sd.getHealth_status());
                                if (s.equals("generation")) cell.setCellValue(sd.getGeneration());
                                if (s.equals("maintenance")) cell.setCellValue(sd.getMaintenance());
                                if (s.equals("id_str")) cell.setCellValue(sd.getCharact_gen());
                                if (s.equals("charact_gen")) cell.setCellValue(sd.getStr_access());
                                if (s.equals("username")) cell.setCellValue(sd.getUsername());
                                if (s.equals("last_change")) cell.setCellValue(sd.getLast_change());
                                if (s.equals("pheno_text")) cell.setCellValue(sd.getPheno_text());
                                if (s.equals("per_id_per")) cell.setCellValue(sd.getPer_id_per());
                                if (s.equals("per_id_per_contact")) cell.setCellValue(sd.getPer_id_per_contact());
                                if (s.equals("emma_id")) cell.setCellValue(sd.getEmma_id());
                                if (s.equals("mgi_ref")) cell.setCellValue(sd.getMgi_ref());
                                if (s.equals("str_type")) cell.setCellValue(sd.getStr_type());
                                if (s.equals("mta_file")) cell.setCellValue(sd.getMta_file());
                                if (s.equals("gp_release")) cell.setCellValue(sd.getGp_release());
                                if (s.equals("name_status")) cell.setCellValue(sd.getName_status());
                                if (s.equals("date_published")) cell.setCellValue(sd.getDate_published());
                                if (s.equals("str_status")) cell.setCellValue(sd.getStr_status());
                                if (s.equals("res_id")) cell.setCellValue(sd.getRes_id());
                                if (s.equals("require_homozygous")) cell.setCellValue(sd.getRequire_homozygous());
                                if (s.equals("archive_id")) cell.setCellValue(sd.getArchive_id());
                                if (s.equals("bg_id_bg")) cell.setCellValue(sd.getBg_id_bg());
                            }
                        }
                    }
                }
            }
            //AUTOSIZE ACCORDING TO LIST LENGTH AT END OF DATA
            for (i = 0; i < columns.size(); i++) {
                sheet.autoSizeColumn((short) i);
            }
            
            // Freeze just one row, 1st row usually field names
            if (freeze == 1) {
                // freeze row
                sheet.createFreezePane(0, 1, 0, 1);
            }
        }
        
    }
}
