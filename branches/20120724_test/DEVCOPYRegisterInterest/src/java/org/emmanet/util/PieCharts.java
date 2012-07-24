/*
 * PieCharts.java
 *
 * Created on 19 February 2008, 16:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *
 *chartlicence=DEVP-2ETP-4E8Z-CGPR-2FB8-DB82
 */

package org.emmanet.util;
import javax.servlet.ServletContext;

/**
 *
 * @author phil
 */
import ChartDirector.*;

public class PieCharts {
    
    private double[] data;
    private String[] labels;
    double[] sectorDepths;
    private String pieTitle;
    private int[] size = new int[2];
    private int[] pieSize = new int[3];
    private String chartPath;
    private int xPlodingSector;
    
    // Main code for creating charts
    public String createChart(double[] sentData, String[] sentDataLabels,
            double[] sentSectorDepths, int[] sentChartSize, int[] sentPieSize,
            int sentxPlodingSector, String sentChartTitle) {
         Chart.setLicenseCode("DEVP-2ETP-4E8Z-CGPR-2FB8-DB82");
        data = sentData;
        labels = sentDataLabels;
        sectorDepths = sentSectorDepths;
        size = sentChartSize;
        pieSize = sentPieSize;
        xPlodingSector = sentxPlodingSector;
        pieTitle = sentChartTitle;
        
        PieChart c = new PieChart(size[0], size[1]);
        c.setPieSize(pieSize[0], pieSize[1], pieSize[2]);
        c.addTitle(pieTitle, "Arial Bold", 10);
        c.setColors(Chart.transparentPalette);
        // ensures start sector at back to improve look
        c.setStartAngle(240);//225
 
        //if a eucomm pie then use remote labels
        
        if (pieTitle.contains("EUCOMM") || pieTitle.equals("Requests per Centre")) {
            // Use the side label layout method
c.setLabelLayout(Chart.SideLayout);

// Set the label box background color the same as the sector color, with glass
// effect, and with 5 pixels rounded corners
TextBox t = c.setLabelStyle();
t.setBackground(Chart.SameAsMainColor, Chart.Transparent, Chart.glassEffect());
t.setRoundedCorners(5);

// Set the border color of the sector the same color as the fill color. Set the line
// color of the join line to black (0x0)
c.setLineColor(Chart.SameAsMainColor, 0x000000);

// Set the start angle to 135 degrees may improve layout when there are many small
// sectors at the end of the data array (that is, data sorted in descending order).
// It is because this makes the small sectors position near the horizontal axis,
// where the text label has the least tendency to overlap. For data sorted in
// ascending order, a start angle of 45 degrees can be used instead.
c.setStartAngle(135);

// Set the pie data and the pie labels
//c.setData(data, labels);
// c.setExplode(xPlodingSector,0);//xPlodingSector, 25
        }       else {
                            // Explode the 1st sector (index = 0)
        c.setExplode(xPlodingSector, 25);
        }
        
        if (!pieTitle.contains("EUCOMM")) {
        // Draw the pie in 3D
        c.set3D(-1, 60, true);
        // Draw the pie in 3D with variable 3D depths
        if (data.length == sectorDepths.length) {
            c.set3D2(sectorDepths);
        } else {
            System.out
                    .println("Check that you have set an approriate number of data "
                    + "sectors for the pie chart " + pieTitle +
                    " ( Data length = " + data.length + " // Sector depths length = "
                    + sectorDepths.length + ")");
            //System.exit(0);
        }
        }
        
        // Set the pie data and the pie labels
        if (labels.length == data.length) {
            c.setData(data, labels);
        } else {
            System.out
                    .println("Check that you have sent an equal number of data "
                    + "items and data labels for the pie chart " + pieTitle +
                    " ( Labels length = " + labels.length + " // Data length = "
                    + data.length + ")");
            //System.exit(0);
        }


        // TODO SET VIA CONFIG
  String chartImage = c.makeTmpFile("/nfs/panda/emma/external/htdocs/tmpimage", 0);
    //  String chartImage = c.makeTmpFile("/home/phil/DEVCOPYRegisterInterest/build/web/images", 0);
       // String chartImage = c.makeTmpFile("/usr/local/jakarta-tomcat-5.0.28/webapps/DEVCOPYRegisterInterest/images", 0);
      // ServletContext scxt = null;
      // String absolutePath = scxt.getRealPath("images");
       System.out.println("PIECHARTIMAGE1 LOCATION = " + chartImage);
        return chartImage;
    }
}

