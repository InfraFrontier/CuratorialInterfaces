/*
 * This class encapsulates the code and data necessary to represent and manage
 * EMMA GUI filtering. The intention is to use a Filter instance to pass
 * filter parameters to the back end for flexible query processing.
 */

package uk.ac.ebi.emma.util;

import java.net.URISyntaxException;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.client.utils.URIBuilder;

/**
 *
 * @author mrelac
 */
public class Filter {
    private String geneId;
    private String geneName;
    private String geneSymbol;
    private String chromosome;
    private String mgiReference;
    private String alleleId;
    private String alleleName;
    private String alleleSymbol;
    
    public Filter() {
        this.geneId = "";
        this.geneName = "";
        this.geneSymbol = "";
        this.chromosome = "";
        this.mgiReference = "";
        this.alleleId = "";
        this.alleleName = "";
        this.alleleSymbol = "";
    }
    
    /**
     * Creates a <code>Filter</code> instance from the request object. Any null
     * request object values are initialized to an empty string.
     * @param request the source <code>HttpServletRequest</code> instance
     */
    public Filter(HttpServletRequest request) {
        this.chromosome = request.getParameter("chromosome") == null ? "" : request.getParameter("chromosome");
        this.geneName = request.getParameter("geneName") == null ? "" : request.getParameter("geneName");
        this.geneSymbol = request.getParameter("geneSymbol") == null ? "" : request.getParameter("geneSymbol");
        this.geneId = request.getParameter("geneId") == null ? "" : request.getParameter("geneId");
        this.mgiReference = request.getParameter("mgiReference") == null ? "" : request.getParameter("mgiReference");
        this.alleleName = request.getParameter("alleleName") == null ? "" : request.getParameter("alleleName");
        this.alleleSymbol = request.getParameter("alleleSymbol") == null ? "" : request.getParameter("alleleSymbol");
        this.alleleId = request.getParameter("alleleId") == null ? "" : request.getParameter("alleleId");
    }

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    public String getGeneSymbol() {
        return geneSymbol;
    }

    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public String getMgiReference() {
        return mgiReference;
    }

    public void setMgiReference(String mgiReference) {
        this.mgiReference = mgiReference;
    }

    public String getAlleleId() {
        return alleleId;
    }

    public void setAlleleId(String alleleId) {
        this.alleleId = alleleId;
    }

    public String getAlleleName() {
        return alleleName;
    }

    public void setAlleleName(String alleleName) {
        this.alleleName = alleleName;
    }

    public String getAlleleSymbol() {
        return alleleSymbol;
    }

    public void setAlleleSymbol(String alleleSymbol) {
        this.alleleSymbol = alleleSymbol;
    }
    
    /**
     * Generates a query string for use in an HTTP GET request from this <code>Filter
     * </code> instance. Returns an empty string if this filter instance has no
     * parameters.
     * 
     * @return a query string for use in an HTTP GET request.
     */
    public String generateQueryString() {
        URIBuilder builder = new URIBuilder();
        if ( ! geneId.isEmpty())
            builder.addParameter("geneId", geneId);
        if ( ! geneName.isEmpty())
            builder.addParameter("geneName", geneName);
        if ( ! geneSymbol.isEmpty())
            builder.addParameter("geneSymbol", geneSymbol);
        if ( ! chromosome.isEmpty())
            builder.addParameter("chromosome", chromosome);
        if ( ! mgiReference.isEmpty())
            builder.addParameter("mgiReference", mgiReference);
        if ( ! alleleId.isEmpty())
            builder.addParameter("alleleId", alleleId);
        if ( ! alleleName.isEmpty())
            builder.addParameter("alleleName", alleleName);
        if ( ! alleleSymbol.isEmpty())
            builder.addParameter("alleleSymbol", alleleSymbol);
        
        String query = "";
        try {
            query = builder.build().getQuery();
        }
        catch (URISyntaxException e) { }

        return query;
    }
    
    
}
