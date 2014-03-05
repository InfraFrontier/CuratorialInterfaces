/**
 * Copyright © 2013 EMBL - European Bioinformatics Institute
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License.  
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
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
    private String alleleId;        // Valid values: null, empty, 1 numeric value, or multiple numeric values separated by a comma and optional whitespace.
    private String alleleMgiReference;
    private String alleleName;
    private String alleleSymbol;
    private String backgroundId;    // Valid values: null, empty, 1 numeric value, or multiple numeric values separated by a comma and optional whitespace.
    private String chromosome;
    private String geneId;          // Valid values: null, empty, 1 numeric value, or multiple numeric values separated by a comma and optional whitespace.
    private String geneMgiReference;
    private String geneName;
    private String geneSymbol;
    private String mutationId;      // Valid values: null, empty, 1 numeric value, or multiple numeric values separated by a comma and optional whitespace.
    private String mutationType;
    private String mutationSubtype;
    private String strainId;    // Valid values: null, empty, 1 numeric value, or multiple numeric values separated by a comma and optional whitespace.
    
    public Filter() {
        this.alleleId = "";
        this.alleleMgiReference = "";
        this.alleleName = "";
        this.alleleSymbol = "";
        this.backgroundId = "";
        this.chromosome = "";
        this.geneId = "";
        this.geneMgiReference = "";
        this.geneName = "";
        this.geneSymbol = "";
        this.mutationId = "";
        this.mutationType = "";
        this.mutationSubtype = "";
        this.strainId = "";
    }
    
    /**
     * Creates a <code>Filter</code> instance from the request object. Any null
     * request object values are initialized to an empty string.
     * @param request the source <code>HttpServletRequest</code> instance
     */
    public Filter(HttpServletRequest request) {
        this.alleleId = request.getParameter("alleleId") == null ? "" : request.getParameter("alleleId");
        this.alleleMgiReference = request.getParameter("alleleMgiReference") == null ? "" : request.getParameter("alleleMgiReference");
        this.alleleName = request.getParameter("alleleName") == null ? "" : request.getParameter("alleleName");
        this.alleleSymbol = request.getParameter("alleleSymbol") == null ? "" : request.getParameter("alleleSymbol");
        this.backgroundId = request.getParameter("backgroundId") == null ? "" : request.getParameter("backgroundId");
        this.chromosome = request.getParameter("chromosome") == null ? "" : request.getParameter("chromosome");
        this.geneId = request.getParameter("geneId") == null ? "" : request.getParameter("geneId");
        this.geneMgiReference = request.getParameter("geneMgiReference") == null ? "" : request.getParameter("geneMgiReference");
        this.geneName = request.getParameter("geneName") == null ? "" : request.getParameter("geneName");
        this.geneSymbol = request.getParameter("geneSymbol") == null ? "" : request.getParameter("geneSymbol");
        this.mutationId = request.getParameter("mutationId") == null ? "" : request.getParameter("mutationId");
        this.mutationType = request.getParameter("mutationType") == null ? "" : request.getParameter("mutationType");
        this.mutationSubtype = request.getParameter("mutationSubtype") == null ? "" : request.getParameter("mutationSubype");
        this.strainId = request.getParameter("strainId") == null ? "" : request.getParameter("strainId");
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
        if ( ! alleleId.isEmpty())
            builder.addParameter("alleleId", alleleId);
        if ( ! alleleMgiReference.isEmpty())
            builder.addParameter("alleleMgiReference", alleleMgiReference);
        if ( ! alleleName.isEmpty())
            builder.addParameter("alleleName", alleleName);
        if ( ! alleleSymbol.isEmpty())
            builder.addParameter("alleleSymbol", alleleSymbol);
        if ( ! backgroundId.isEmpty())
            builder.addParameter("backgroundId", backgroundId);
        if ( ! chromosome.isEmpty())
            builder.addParameter("chromosome", chromosome);
        if ( ! geneId.isEmpty())
            builder.addParameter("geneId", geneId);
        if ( ! geneMgiReference.isEmpty())
            builder.addParameter("geneMgiReference", geneMgiReference);
        if ( ! geneName.isEmpty())
            builder.addParameter("geneName", geneName);
        if ( ! geneSymbol.isEmpty())
            builder.addParameter("geneSymbol", geneSymbol);
        if ( ! mutationId.isEmpty())
            builder.addParameter("mutationId", mutationId);
        if ( ! mutationType.isEmpty())
            builder.addParameter("mutationType", mutationType);
        if ( ! mutationSubtype.isEmpty())
            builder.addParameter("mutationSubtype", mutationSubtype);
        if ( ! strainId.isEmpty())
            builder.addParameter("strainId", strainId);
        
        String query = "";
        try {
            query = builder.build().getQuery();
        }
        catch (URISyntaxException e) { }

        return query;
    }
    
    // GETTERS AND SETTERS
    public String getAlleleId() {
        return alleleId;
    }

    public void setAlleleId(String alleleId) {
        this.alleleId = alleleId;
    }

    public String getAlleleMgiReference() {
        return alleleMgiReference;
    }

    public void setAlleleMgiReference(String alleleMgiReference) {
        this.alleleMgiReference = alleleMgiReference;
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

    public String getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(String backgroundId) {
        this.backgroundId = backgroundId;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public String getGeneMgiReference() {
        return geneMgiReference;
    }

    public void setGeneMgiReference(String geneMgiReference) {
        this.geneMgiReference = geneMgiReference;
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

    public String getMutationId() {
        return mutationId;
    }

    public void setMutationId(String mutationId) {
        this.mutationId = mutationId;
    }

    public String getMutationType() {
        return mutationType;
    }

    public void setMutationType(String mutationType) {
        this.mutationType = mutationType;
    }

    public String getMutationSubtype() {
        return mutationSubtype;
    }

    public void setMutationSubtype(String mutationSubtype) {
        this.mutationSubtype = mutationSubtype;
    }

    public String getStrainId() {
        return strainId;
    }

    public void setStrainId(String strainId) {
        this.strainId = strainId;
    }
    
    
    
    
}
