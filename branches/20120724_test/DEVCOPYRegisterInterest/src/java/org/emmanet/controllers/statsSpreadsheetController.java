/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.controllers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.*;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;


import org.emmanet.jobs.WebRequests;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author phil
 */
public class statsSpreadsheetController extends AbstractExcelView {

    private HSSFSheet sheet;
    private HSSFSheet sheet2;
    private HSSFSheet sheet3;
    private HSSFSheet sheet4;
    private HSSFSheet sheet5;
    private HSSFWorkbook wbk;
    private String[] headerTitle;
    private String[] sheetStockFields;
    private WebRequests wr;
    private static final String PATTERN =
            // "^-?\\d+.?\\d?";//pattern for a number with or without leading minus, with or without decimal point
            "^-?\\d+";

    @Override
    protected void buildExcelDocument(Map arg0, HSSFWorkbook arg1, HttpServletRequest arg2, HttpServletResponse arg3) throws Exception {

        System.out.println("arg0.size()=" + arg0.size());

        sheet = arg1.createSheet("Stock Details");
        sheet2 = arg1.createSheet("Distribution");

        sheet3 = arg1.createSheet("Distribution Time Current");
        sheet4 = arg1.createSheet("Distribution Time Shipped");
        sheet5 = arg1.createSheet("Requests by Country");

        /**********************************************
         * 
         * HERE STARTS SHEET 1 - STOCK DETAILS
         * 
         * *******************************************
         */
        //cell headers
        String[] sheetStockFields = {"EMMA ID", "Strain name", "Internal Code", "Number of Requests", "Status", "Centre"};//
        String[] sheetDistributionTimeCurrent = {"Lab Code", "EMMA ID", "Year", "Days"};
        String[] sheetDistributionByCountry = {"Country", "Count"};

        createHeaderCells(arg1, sheet, sheetStockFields);
        populateDataCells(sheet, (List) arg0.get("popularEUCOMMStrains"), 1, true);

        createHeaderCells(arg1, sheet3, sheetDistributionTimeCurrent);
        populateDataCells(sheet3, (List) arg0.get("CurrentDistTime"), 1, true);//OK DUPS REMOVED

        createHeaderCells(arg1, sheet4, sheetDistributionTimeCurrent);
        populateDataCells(sheet4, (List) arg0.get("ShippedDistTime"), 1, true);//OK DUPS REMOVED

        createHeaderCells(arg1, sheet5, sheetDistributionByCountry);
        populateDataCells(sheet5, (List) arg0.get("EUCOMMreqsByCountryData"), 1, false);//OK DUPS REMOVED



        String[] distrubutionFields = {"Number of individual lines requested/centre",
            "Number of individual lines distributed/centre", "Number of individual lines requested/centre",
            "Number of individual lines distributed/centre"
        };

        // EUCOMMreqsMonthYear       
        populateReqShipSheets(arg0, 1, sheet2, "Reqs", arg1);

        //# populateReqShipSheets(arg0, 0, sheet2, "Reqs", arg1);
        // EUCOMMshipMonthYear       
        populateReqShipSheets(arg0, 24, sheet2, "Ship", arg1);


    //end
    }

    public Boolean populateReqShipSheets(Map arg0, int irowStart, HSSFSheet sheetID, String state, HSSFWorkbook wb) {
        //state can equal either Reqs or Ship
        // GREY background STYLES NOT WORKING???
        HSSFCellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
        // style.setFillPattern(CellStyle.BIG_SPOTS);
        List results = (List) arg0.get("EUCOMM" + state.toLowerCase() + "MonthYear");///##
        String sheetTitle = "";
        if (state.equals("Ship")) {
            sheetTitle = "Strains Shipped";
        }
        if (state.equals("Reqs")) {
            sheetTitle = "Strains Requested";
        }
        Map map = new HashMap();
        Object O = arg0.get("Centres");
        int irow = irowStart;

        HSSFRow rowTitle = sheetID.createRow(irow - 1);
        HSSFRichTextString titleData = new HSSFRichTextString(sheetTitle);
        //rowTitle.createCell((short) 0).setCellValue(titleData);
        HSSFCell cell = rowTitle.createCell((short) 0);
        cell.setCellValue(titleData);
        if (sheetTitle.equals("Strains Requested")) {
            sheetID.autoSizeColumn((short) 0);
        }
        cell.setCellStyle((HSSFCellStyle) style);


        map = (Map) O;
        System.out.println("Object [0])" + O.toString());

        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            HSSFRow row = sheetID.createRow(irow);
            HSSFRichTextString data = new HSSFRichTextString(key);

            row.createCell((short) 0).setCellValue(data);

            /*use same row to create cell + results list value and count*/
            double[] CNB = (double[]) arg0.get("EUCOMM" + state + "ByMonthYearCentre-CNB");
            double[] CNR = (double[]) arg0.get("EUCOMM" + state + "ByMonthYearCentre-CNR");
            double[] HMGU = (double[]) arg0.get("EUCOMM" + state + "ByMonthYearCentre-HMGU");
            double[] ICS = (double[]) arg0.get("EUCOMM" + state + "ByMonthYearCentre-ICS");
            double[] MRC = (double[]) arg0.get("EUCOMM" + state + "ByMonthYearCentre-MRC");
            double[] SANG = (double[]) arg0.get("EUCOMM" + state + "ByMonthYearCentre-SANG");
            double[] CNRS= (double[]) arg0.get("CNRS" + state + "ByMonthYearCentre-CNRS");

            for (int ii = 0; ii < results.size(); ii++) {

                String colData = "";
                if (key.equals("CNB")) {
                    int iDbleVal = (int) CNB[ii];
                    System.out.println("CNB Val is:" + iDbleVal);
                    colData = "" + iDbleVal + "";

                }
                if (key.equals("CNR")) {
                    int iDbleVal = (int) CNR[ii];
                    System.out.println("CNR Val is:" + iDbleVal);
                    colData = "" + iDbleVal + "";
                }
                if (key.equals("HMGU")) {
                    int iDbleVal = (int) HMGU[ii];
                    System.out.println("HMGU Val is:" + iDbleVal);
                    colData = "" + iDbleVal + "";
                }
                if (key.equals("ICS")) {
                    int iDbleVal = (int) ICS[ii];
                    System.out.println("ICS Val is:" + iDbleVal);
                    colData = "" + iDbleVal + "";
                }
                if (key.equals("MRC")) {
                    int iDbleVal = (int) MRC[ii];
                    System.out.println("MRC Val is:" + iDbleVal);
                    colData = "" + iDbleVal + "";
                }
                if (key.equals("SANG")) {
                    
                    int iDbleVal = (int) SANG[ii];
                    
                    System.out.println("SANG Val is:" + iDbleVal);
                    colData = "" + iDbleVal + "";
                }
             /*                   if (key.equals("CNRS")) {
                    int iDbleVal = (int) CNRS[ii];
                    System.out.println("CNRS Val is:" + iDbleVal);
                    colData = "" + iDbleVal + "";
                }*/

                HSSFRichTextString Data = new HSSFRichTextString(colData);
                int iCell = ii + 1;
                row.createCell((short) iCell).setCellValue(Data);
            }
            System.out.println("key value=" + key);
            irow++;
        }

        // HSSFRow row = sheet2.getRow(1);
        HSSFRow row = sheet2.createRow(irowStart - 1);
        System.out.println("results size" + results.size() + " row " + row);
        int iCol = 1;
        for (Iterator it1 = results.listIterator(); it1.hasNext();) {
            Object[] o = (Object[]) it1.next();

            HSSFRichTextString data = new HSSFRichTextString(o[0].toString() + " " + o[1].toString());

            row.createCell((short) iCol).setCellValue(data);
            iCol++;
//System.out.println("\t" + sendDataCNB[j] + "\t" + sendDataCNR[j] + "\t" + sendDataHMGU[j] + "\t" + sendDataICS[j] + "\t" + sendDataMRC[j] + "\t" + sendDataSANG[j] + "\t");
        }
//shipped counts
        int iirow = 0;
        int iirow1 = 0;

        int[] lists = null;
        int[] lists1 = null;
        //  boolean reqsSet = false;///#
        HSSFRichTextString header = new HSSFRichTextString("");
        HSSFRichTextString header1 = new HSSFRichTextString("");
        if (state.equals("Ship")) {
            iirow = 36;//29;
            header = new HSSFRichTextString("Shipped Totals");
            lists = (int[]) arg0.get(state.toLowerCase() + "Counts");
        } else if (state.equals("Reqs")) {
            iirow = 12;//0;
            // reqsSet = true;//#
            header = new HSSFRichTextString("Request Totals");
            lists = (int[]) arg0.get(state.toLowerCase() + "Counts");
            if (state.equals("Reqs")) {//#
                iirow1 = 12;//0;//#
                header1 = new HSSFRichTextString("Requests Cancelled Totals");//#
                lists1 = (int[]) arg0.get("reqsCountsCanc");
            }
        }

        /////   int[] lists = (int[]) arg0.get(state.toLowerCase() + "Counts");
        /*
        now base on Reqs set new data list if not empty then dump it out
        
        or construct a method
         * 
         * to write extra out
        
         */

        HSSFRow rowHeader = sheet2.createRow(iirow - 1);

        rowHeader.createCell((short) 1).setCellValue(header);

        it = map.keySet().iterator();
        int iCodeRow = iirow;
        while (it.hasNext()) {
            String key = (String) it.next();
            HSSFRow rowSiteCodes = sheet2.createRow(iCodeRow);
            HSSFRichTextString dataSiteCodes = new HSSFRichTextString(key);

            if (state.equals("CancReqs")) {//#
                rowSiteCodes.createCell((short) 4).setCellValue(dataSiteCodes);//NEEDS CHANGING FOR CANC
            } else {
                rowSiteCodes.createCell((short) 0).setCellValue(dataSiteCodes);//NEEDS CHANGING FOR CANC
            }
            iCodeRow++;
        }
        for (int i = 0; i < lists.length; i++) {
            Object o = (Object) lists[i];
            HSSFRow rowLists = sheet2.createRow(iirow);

            HSSFRichTextString data = new HSSFRichTextString(o.toString());


            rowLists.createCell((short) 1).setCellValue(data);


            iirow++;
        }

        // cancelled request counts.
        if (lists1 != null) {
            HSSFRow rowHeader1 = sheet2.createRow(iirow1 - 1);

            rowHeader1.createCell((short) 5).setCellValue(header1);
            for (int i = 0; i < lists1.length; i++) {
                Object o = (Object) lists1[i];
                HSSFRow rowLists = sheet2.createRow(iirow1);

                HSSFRichTextString data = new HSSFRichTextString(o.toString());


                rowLists.createCell((short) 5).setCellValue(data);


                iirow1++;
            }



        }

//end shipped counts


        return true;
    }

    public boolean createHeaderCells(HSSFWorkbook arg1, HSSFSheet sheet, String[] fieldNames) {

        headerTitle = fieldNames;
        HSSFRow header = sheet.createRow(0);

        /**********************************************
         *
         *  CELL HEADER STYLE
         * 
         * ********************************************
         */
        HSSFCellStyle style = arg1.createCellStyle();
        style.setFillBackgroundColor(HSSFColor.AQUA.index);
        style.setFillForegroundColor(HSSFColor.WHITE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < headerTitle.length; i++) {
            HSSFRichTextString richString = new HSSFRichTextString(headerTitle[i]);
            HSSFCell cell = header.createCell((short) i);
            cell.setCellStyle(style);
            cell.setCellValue(richString);
        }

        return true;
    }

    public boolean populateDataCells(HSSFSheet arg1, List results, int startRow, boolean freezeFrame) {


        System.out.println("results size" + results.size());
        int irow = startRow;//0 row set to header already
        for (Iterator it = results.listIterator(); it.hasNext();) {
            Object[] o = (Object[]) it.next();
            HSSFRow row = arg1.createRow(irow);
//System.out.println("O length is::" + o.length);
            for (int i = 0; i < o.length; i++) {
                if (o[i] == null) {
                    o[i] = "";
                }
                HSSFRichTextString data = new HSSFRichTextString(o[i].toString());
                Pattern pattern = Pattern.compile(PATTERN);
                Matcher matcher = pattern.matcher(o[i].toString());
                boolean matchFound = matcher.matches();
                if (matchFound) {
                    //this is a numeric data field format as such
                    double value = Double.parseDouble(o[i].toString());
                    row.createCell((short) i, 0).setCellValue(value);
                } else {
                    row.createCell((short) i).setCellValue(data);
                }
//System.out.println(i + ".  result value= " + o.length + " ---- " +o[i].toString());
            }

            irow++;
        // System.out.println();
        }
        // AUTOSIZE COLUMNS TO DATA
        for (int i = 0; i <= headerTitle.length; i++) {
            arg1.autoSizeColumn((short) i);
        }

        if (freezeFrame) {
            arg1.createFreezePane(0, 1, 0, 1);
        }
        return true;
    }

    public String[] getSheetStockFields() {
        return sheetStockFields;
    }

    public void setSheetStockFields(String[] sheetStockFields) {
        this.sheetStockFields = sheetStockFields;
    }

    public String[] getHeaderTitle() {
        return headerTitle;
    }

    public void setHeader(String[] headerTitle) {
        this.headerTitle = headerTitle;
    }
}


