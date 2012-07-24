/*
 * BarChart.java
 *
 * Created on 10 December 2007, 12:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.emmanet.util;

import ChartDirector.*;

/** 
 *
 * @author phil
 */
public class BarChart {

    private String chartTitle;
    private String[] dataLabels;
    private int[] chartSize = new int[2];
    private String xLabel;
    private String yLabel;
    private double data[];
    private String chartPath;
    //Name of demo program
    public String toString() {
        return "EMMA Submissions";
    }
    //Number of charts produced in this demo
    public int getNoOfCharts() {
        return 1;
    }
    //Main code for creating charts rmoved ChartViewer viewer, from sig int index,
    public String createChart(double[] sentData,
            String[] sentDataLabels, int[] sentChartSize, String sentXLabel,
            String sentYLabel, String sentChartTitle) {
        Chart.setLicenseCode("DEVP-2ETP-4E8Z-CGPR-2FB8-DB82");
        // The data for the bar chart
        data = sentData;

        // The labels for the bar chart
        dataLabels = sentDataLabels;

        //System.out.println("size of data labels is : " + dataLabels.length);
        // Create a XYChart object of size 400 x 240 pixels.
        chartSize = sentChartSize;
        int width = chartSize[0];
        int height = chartSize[1];
        System.out.println("width=" + width + " height=" + height);
        XYChart c = new XYChart(width, height);
        // Add a title to the chart using 14 pts Times Bold Italic font
        chartTitle = sentChartTitle;
        System.out.println("title=" + chartTitle);
        c.addTitle(chartTitle, "Arial Bold", 10);

        // Set the plotarea at (45, 40) and of 300 x 160 pixels in size.
        if (width > 400) {
            c.setPlotArea(45, 40, 700, 117, 0xf8f8f8, 0xffffff);
            c.yAxis().setTickDensity(10);
        } else {
            c.setPlotArea(45, 40, 300, 145, 0xf8f8f8, 0xffffff);
        }
        // Add a multi-color bar chart layer
        BarLayer layer = c.addBarLayer3(data);

        // Set layer to 3D with 10 pixels 3D depth
        layer.set3D(10);

        // Set bar shape to circular (cylinder)
        layer.setBarShape(Chart.CircleShape);

        // Set the labels on the x axis.
        // Check for equality with data length
        System.out.println("dataLabels lenght=" + dataLabels.length + " data length=" + data.length);
        if (dataLabels.length == data.length) {
            c.xAxis().setLabels(dataLabels).setFontAngle(45);
        } else {
            System.out.println("Check that you have sent an equal number of data items and data labels");
            System.exit(0);
        }
        // Add a title to the y axis
        yLabel = sentYLabel;
        c.yAxis().setTitle(yLabel);

        // Add a title to the x axis
        xLabel = sentXLabel;
        c.xAxis().setTitle(xLabel);

        //show data labels

        layer.setDataLabelStyle();

        // output the chart

        // TODO SET FROM CONFIG
           String chartImage = c.makeTmpFile("/nfs/panda/emma/external/htdocs/tmpimage", 0);
            //String chartImage = c.makeTmpFile("/nfs/panda/emma/tmp", 0);
 //       String chartImage = c.makeTmpFile("/home/phil/DEVCOPYRegisterInterest/build/web/images", 0);
        // String chartImage = c.makeTmpFile("/usr/local/jakarta-tomcat-5.0.28/webapps/DEVCOPYRegisterInterest/images", 0);
        System.out.println("CHARTIMAGE1 LOCATION = " + chartImage);
        return chartImage;

    /*viewer.setImage(c.makeImage());
    
    //include tool tip for the chart
    viewer.setImageMap(c.getHTMLImageMap("clickable", "",
    "title='{xLabel}: {value} Submitted strains'"));*/
    }

    public String createMultiBarChart(double[] sentData1, double[] sentData2, double[] sentData3, double[] sentData4, double[] sentData5, double[] sentData6,
            String[] sentDataLabels, int[] sentChartSize, String sentXLabel,
            String sentYLabel, String sentChartTitle) {



// The data for the bar chart

//double[] data1 = sentData1;
//double[] data2 = {97, 87, 56, 267, 157};
//String[] labels = {"Mon", "Tue", "Wed", "Thu", "Fri"};

      /*  System.out.println("sendata1length=" + sentData1.length);
        System.out.println("sendata2length=" + sentData2.length);
        System.out.println("sendata3length=" + sentData3.length);
        System.out.println("sendata4length=" + sentData4.length);
        System.out.println("sendata5length=" + sentData5.length);
        System.out.println("sendata6length=" + sentData6.length);
        
        System.out.println("sentDataLabels=" + sentDataLabels.length);
        
                System.out.println("sendata1length=" + sentData1[1]);
        System.out.println("sendata2length=" + sentData2[1]);
        System.out.println("sendata3length=" + sentData3[1]);
        System.out.println("sendata4length=" + sentData4[1]);
        System.out.println("sendata5length=" + sentData5[1]);
        System.out.println("sendata6length=" + sentData6[1]);
        
        System.out.println("sentDataLabels=" + sentDataLabels[1]);*/
        
        

// Create a XYChart object of size 400 x 240 pixels
        chartSize = sentChartSize;
        int width = chartSize[0];
        int height = chartSize[1];
        XYChart c = new XYChart(width, height);
// Add a title to the chart using 10 pt Arial font

        chartTitle = sentChartTitle;
        c.addTitle(chartTitle, "Arial Bold", 10);

// Set the plot area at (50, 25) and of size 320 x 180. Use two alternative
// background colors (0xffffc0 and 0xffffe0)

       // c.setPlotArea(50, 25, 900, 500, 0xffffc0, 0xffffe0);

        if (width > 400) {
            c.setPlotArea(45, 40, 800, 300, 0xf8f8f8, 0xffffff);
        // c.yAxis().setTickDensity(10);
        } else {
            c.setPlotArea(45, 40, 300, 145, 0xf8f8f8, 0xffffff);
        }

// Add a legend box at (55, 18) using horizontal layout. Use 8 pt Arial font, with
// transparent background
        c.addLegend(65,34, false, "", 8).setBackground(Chart.Transparent);

// Add a title to the y-axis
        c.yAxis().setTitle(sentYLabel);

// Reserve 20 pixels at the top of the y-axis for the legend box
        c.yAxis().setTopMargin(30);

// Set the x axis labels
c.xAxis().setTitle(sentXLabel);
        dataLabels = sentDataLabels;
        c.xAxis().setLabels(dataLabels).setFontAngle(45);
// Add a multi-bar layer with 3 data sets and 3 pixels 3D depth
        BarLayer layer = c.addBarLayer2(Chart.Side, 6);
//BarLayer layer = c.addBarLayer2();
       //  layer.set3D(10);
//Layer.setDataLabelStyle("Arial",8,00000000,0);
        layer.setDataLabelStyle();
        // Set bar shape to circular (cylinder)
        layer.setBarShape(Chart.CircleShape);
        layer.addDataSet(sentData1, -1, "CNB");
        layer.addDataSet(sentData2, -1, "CNR");
        layer.addDataSet(sentData3, -1, "HMGU");
        layer.addDataSet(sentData4, -1, "ICS");
        layer.addDataSet(sentData5, -1, "MRC");
        layer.addDataSet(sentData6, -1, "SANG");
// output the chart
           String chartImage = c.makeTmpFile("/nfs/panda/emma/external/htdocs/tmpimage", 0);
           //String chartImage = c.makeTmpFile("/nfs/panda/emma/tmp", 0);
       // String chartImage = c.makeTmpFile("/home/phil/DEVCOPYRegisterInterest/build/web/images", 0);
        // String chartImage = c.makeTmpFile("/usr/local/jakarta-tomcat-5.0.28/webapps/DEVCOPYRegisterInterest/images", 0);
        System.out.println("CHARTIMAGE2 LOCATION = " + chartImage);
        return chartImage;



    }

    public String getChartTitle() {
        return chartTitle;
    }

    public void setChartTitle(String chartTitle) {
        this.chartTitle = chartTitle;
    }

    public String[] getDataLabels() {
        return dataLabels;
    }

    public void setDataLabels(String[] dataLabels) {
        this.dataLabels = dataLabels;
    }

    public int[] getChartSize() {
        return chartSize;
    }

    public void setChartSize(int[] chartSize) {
        this.chartSize = chartSize;
    }

    public String getXLabel() {
        return xLabel;
    }

    public void setXLabel(String xLabel) {
        this.xLabel = xLabel;
    }

    public String getYLabel() {
        return yLabel;
    }

    public void setYLabel(String yLabel) {
        this.yLabel = yLabel;
    }

    public double[] getData() {
        return data;
    }

    public void setData(double[] data) {
        this.data = data;
    }

    public String ctxPath(String ctx) {
        chartPath = ctx;
        return chartPath;
    }
}
