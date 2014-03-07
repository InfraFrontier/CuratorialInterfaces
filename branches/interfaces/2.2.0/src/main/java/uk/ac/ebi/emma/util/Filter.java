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
    private String allele_key;        // Valid values: null, empty, 1 numeric value, or multiple numeric values separated by a comma and optional whitespace.
    private String alleleMgiReference;
    private String alleleName;
    private String alleleSymbol;
    private String background_key;    // Valid values: null, empty, 1 numeric value, or multiple numeric values separated by a comma and optional whitespace.
    private String chromosome;
    private String gene_key;       // Valid values: null, empty, 1 numeric value, or multiple numeric values separated by a comma and optional whitespace.
    private String geneMgiReference;
    private String geneName;
    private String geneSymbol;
    private String mutation_key;      // Valid values: null, empty, 1 numeric value, or multiple numeric values separated by a comma and optional whitespace.
    private String mutationType;
    private String mutationSubtype;
    private String strain_key;    // Valid values: null, empty, 1 numeric value, or multiple numeric values separated by a comma and optional whitespace.
    
    public Filter() {
        this.allele_key = "";
        this.alleleMgiReference = "";
        this.alleleName = "";
        this.alleleSymbol = "";
        this.background_key = "";
        this.chromosome = "";
        this.gene_key = "";
        this.geneMgiReference = "";
        this.geneName = "";
        this.geneSymbol = "";
        this.mutation_key = "";
        this.mutationType = "";
        this.mutationSubtype = "";
        this.strain_key = "";
    }
    
    /**
     * Creates a <code>Filter</code> instance from the request object. Any null
     * request object values are initialized to an empty string.
     * @param request the source <code>HttpServletRequest</code> instance
     */
    public Filter(HttpServletRequest request) {
        this.allele_key = request.getParameter("allele_key") == null ? "" : request.getParameter("allele_key");
        this.alleleMgiReference = request.getParameter("alleleMgiReference") == null ? "" : request.getParameter("alleleMgiReference");
        this.alleleName = request.getParameter("alleleName") == null ? "" : request.getParameter("alleleName");
        this.alleleSymbol = request.getParameter("alleleSymbol") == null ? "" : request.getParameter("alleleSymbol");
        this.background_key = request.getParameter("background_key") == null ? "" : request.getParameter("background_key");
        this.chromosome = request.getParameter("chromosome") == null ? "" : request.getParameter("chromosome");
        this.gene_key = request.getParameter("gene_key") == null ? "" : request.getParameter("gene_key");
        this.geneMgiReference = request.getParameter("geneMgiReference") == null ? "" : request.getParameter("geneMgiReference");
        this.geneName = request.getParameter("geneName") == null ? "" : request.getParameter("geneName");
        this.geneSymbol = request.getParameter("geneSymbol") == null ? "" : request.getParameter("geneSymbol");
        this.mutation_key = request.getParameter("mutation_key") == null ? "" : request.getParameter("mutation_key");
        this.mutationType = request.getParameter("mutationType") == null ? "" : request.getParameter("mutationType");
        this.mutationSubtype = request.getParameter("mutationSubtype") == null ? "" : request.getParameter("mutationSubype");
        this.strain_key = request.getParameter("strain_key") == null ? "" : request.getParameter("strain_key");
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
        if ( ! allele_key.isEmpty())
            builder.addParameter("allele_key", allele_key);
        if ( ! alleleMgiReference.isEmpty())
            builder.addParameter("alleleMgiReference", alleleMgiReference);
        if ( ! alleleName.isEmpty())
            builder.addParameter("alleleName", alleleName);
        if ( ! alleleSymbol.isEmpty())
            builder.addParameter("alleleSymbol", alleleSymbol);
        if ( ! background_key.isEmpty())
            builder.addParameter("background_key", background_key);
        if ( ! chromosome.isEmpty())
            builder.addParameter("chromosome", chromosome);
        if ( ! gene_key.isEmpty())
            builder.addParameter("gene_key", gene_key);
        if ( ! geneMgiReference.isEmpty())
            builder.addParameter("geneMgiReference", geneMgiReference);
        if ( ! geneName.isEmpty())
            builder.addParameter("geneName", geneName);
        if ( ! geneSymbol.isEmpty())
            builder.addParameter("geneSymbol", geneSymbol);
        if ( ! mutation_key.isEmpty())
            builder.addParameter("mutation_key", mutation_key);
        if ( ! mutationType.isEmpty())
            builder.addParameter("mutationType", mutationType);
        if ( ! mutationSubtype.isEmpty())
            builder.addParameter("mutationSubtype", mutationSubtype);
        if ( ! strain_key.isEmpty())
            builder.addParameter("strain_key", strain_key);
        
        String query = "";
        try {
            query = builder.build().getQuery();
        }
        catch (URISyntaxException e) { }

        return query;
    }
    
    // GETTERS AND SETTERS
    public String getAllele_key() {
        return allele_key;
    }

    public void setAllele_key(String allele_key) {
        this.allele_key = allele_key;
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

    public String getBackground_key() {
        return background_key;
    }

    public void setBackground_key(String background_key) {
        this.background_key = background_key;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public String getGene_key() {
        return gene_key;
    }

    public void setGene_key(String gene_key) {
        this.gene_key = gene_key;
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

    public String getMutation_key() {
        return mutation_key;
    }

    public void setMutation_key(String mutation_key) {
        this.mutation_key = mutation_key;
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

    public String getStrain_key() {
        return strain_key;
    }

    public void setStrain_key(String strain_key) {
        this.strain_key = strain_key;
    }
    
    
    
    
}
