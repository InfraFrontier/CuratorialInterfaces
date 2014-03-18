 /**
 * Copyright © 2007 EMBL - European Bioinformatics Institute
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
 */

package uk.ac.ebi.emma.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.springframework.web.context.request.WebRequest;

/**
 *
 * @author phil, mrelac
 */
public class Strain implements Serializable {
    private Integer strain_key = null;                                          // primary key (was id_str)
    
    private String additional_owner;                                            // additional owner (comma-separated list of names)
    private String available_to_order;                                          // available to order
    private String charact_gen;                                                 // characteristics of generation (breeding history)
    private String code_internal;                                               // code internal
    private Date date_published;                                                // date published
    private String emma_id;                                                     // EMMA id NOTE: *NOT* a foreign key!
    private String exclusive_owner;                                             // exclusive owner (yes/no)
    private String exclusive_owner_desc;                                        // exclusive owner (comma-separated list of names) (was ex_owner_description)
    private Integer generation;                                                 // generation
    private String gp_release;                                                  // general publication (strain) release date
    private String health_status;                                               // health status
    private String hethemi_fertile;                                             // het hemi fertile (yes/no/unknown)
    private String human_model;                                                 // human model
    private String human_model_desc;                                            // human model description
    private String immunocompromised;                                           // immunocompromised (yes/no/unknown)
    private String maintenance;                                                 // maintenance
    private String mgiReference;                                                // MGI reference (was mgi_ref)
    private String msgContentArrv;                                              // (was MSGcontentArrv)
    private String msgContentComp;                                              // (was MSGcontentComp)
    private String msgContentFrz;                                               // (was MSGcontentFrz)
    private String mta_file;                                                    // mta filename
    private String mutant_fertile;                                              // mutant fertile (yes/no/unknown)
    private String mutant_viable;                                               // mutant viable (yes/no/unknown)
    private String name;                                                        // name
    private String name_status;                                                 // name status
    private String pheno_text;                                                  // pheno text homo
    private String pheno_text_hetero;                                           // pheno text hetero
    private String reporting_count;
    private String require_homozygous;                                          // require homozygous (yes/no/unknown)
    private String sibmatings;                                                  // sib matings
    private String str_access;                                                  // strain access (public, private)
    private String str_status;                                                  // strain status
    private String str_type;                                                    // strain type
    
    // FOREIGN KEYS
    private Integer archive_key = null;                                         // foreign key to archive table (was archive_id)
    private Integer background_key = null;                                      // foreign key to backgrounds table (was bg_id_bg)
    private Integer personShippingContact_key = null;                           // foreign key to people table for shipping contact (was per_id_per_contact)
    private Integer personCreator_key = null;                                   // foreign key to people table for strain creator/PI (was per_id_per)
    private Integer personSubmitter_key = null;                                 // foreign key to people table for submitter (was per_id_per_sub)
    private Integer residue_key = null;                                         // foreign key to residues table (was res_id)
    private Integer submission_key = null;                                      // foreign key to submissions table (was sub_id_sub)
    
    // COLLECTIONS
    private Set<AvailabilityStrain> availabilitiesStrains;                      // (was AvailabilitiesStrainsDAO)
    private Set<BiblioStrain>       bibliosStrains;                             // (was setBibliosStrainsDAO)
    private Set<CategoryStrain>     categoriesStrains;                          // (was categoriesStrainsDAO)
    private Set<RtoolStrain>        rtoolsStrains;                              // (was cvDAO. List of countries from cv_country.
    private Set<Mutation>           mutations;                                  // (was mutationsStrainsDAO)
    private Set<ProjectStrain>      projectsStrains;                            // (was ProjectsDAO)
    private Set<SourceStrain>       sourcesStrains;                             // (was sources_StrainsDAO)
    private Set<SynonymStrain>      synonymsStrains;                            // (was syn_strainsDAO)
    private Set<WebRequest>         webRequests;                                // (was wrDAO)
    
    // CLASS INSTANCES
    private Archive    archive;                                                 // (was archiveDAO)
    private Background background;                                              // (was backgroundDAO)
    private Person     creator;                                                 // strain creator/PI instance (was peopleDAO)
    private Residue    residue;                                                 // (was residuesDAO)
    private Person     shippingContact;                                         // strain shipping contact instance (was peopleDAOCon)
    private Person     submitter;                                               // strain submitter instance (was peopleDAOSub)
    
    private Date   last_change;                                                 // date last changed
    private String username;                                                    // changed by username

    public Integer getStrain_key() {
        return strain_key;
    }

    public void setStrain_key(Integer strain_key) {
        this.strain_key = strain_key;
    }

    public String getAdditional_owner() {
        return additional_owner;
    }

    public void setAdditional_owner(String additional_owner) {
        this.additional_owner = additional_owner;
    }

    public String getAvailable_to_order() {
        return available_to_order;
    }

    public void setAvailable_to_order(String available_to_order) {
        this.available_to_order = available_to_order;
    }

    public String getCharact_gen() {
        return charact_gen;
    }

    public void setCharact_gen(String charact_gen) {
        this.charact_gen = charact_gen;
    }

    public String getCode_internal() {
        return code_internal;
    }

    public void setCode_internal(String code_internal) {
        this.code_internal = code_internal;
    }

    public Date getDate_published() {
        return date_published;
    }

    public void setDate_published(Date date_published) {
        this.date_published = date_published;
    }

    public String getEmma_id() {
        return emma_id;
    }

    public void setEmma_id(String emma_id) {
        this.emma_id = emma_id;
    }

    public String getExclusive_owner() {
        return exclusive_owner;
    }

    public void setExclusive_owner(String exclusive_owner) {
        this.exclusive_owner = exclusive_owner;
    }

    public String getExclusive_owner_desc() {
        return exclusive_owner_desc;
    }

    public void setExclusive_owner_desc(String exclusive_owner_desc) {
        this.exclusive_owner_desc = exclusive_owner_desc;
    }

    public Integer getGeneration() {
        return generation;
    }

    public void setGeneration(Integer generation) {
        this.generation = generation;
    }

    public String getGp_release() {
        return gp_release;
    }

    public void setGp_release(String gp_release) {
        this.gp_release = gp_release;
    }

    public String getHealth_status() {
        return health_status;
    }

    public void setHealth_status(String health_status) {
        this.health_status = health_status;
    }

    public String getHethemi_fertile() {
        return hethemi_fertile;
    }

    public void setHethemi_fertile(String hethemi_fertile) {
        this.hethemi_fertile = hethemi_fertile;
    }

    public String getHuman_model() {
        return human_model;
    }

    public void setHuman_model(String human_model) {
        this.human_model = human_model;
    }

    public String getHuman_model_desc() {
        return human_model_desc;
    }

    public void setHuman_model_desc(String human_model_desc) {
        this.human_model_desc = human_model_desc;
    }

    public String getImmunocompromised() {
        return immunocompromised;
    }

    public void setImmunocompromised(String immunocompromised) {
        this.immunocompromised = immunocompromised;
    }

    public String getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(String maintenance) {
        this.maintenance = maintenance;
    }

    public String getMgiReference() {
        return mgiReference;
    }

    public void setMgiReference(String mgiReference) {
        this.mgiReference = mgiReference;
    }

    public String getMsgContentArrv() {
        return msgContentArrv;
    }

    public void setMsgContentArrv(String msgContentArrv) {
        this.msgContentArrv = msgContentArrv;
    }

    public String getMsgContentComp() {
        return msgContentComp;
    }

    public void setMsgContentComp(String msgContentComp) {
        this.msgContentComp = msgContentComp;
    }

    public String getMsgContentFrz() {
        return msgContentFrz;
    }

    public void setMsgContentFrz(String msgContentFrz) {
        this.msgContentFrz = msgContentFrz;
    }

    public String getMta_file() {
        return mta_file;
    }

    public void setMta_file(String mta_file) {
        this.mta_file = mta_file;
    }

    public String getMutant_fertile() {
        return mutant_fertile;
    }

    public void setMutant_fertile(String mutant_fertile) {
        this.mutant_fertile = mutant_fertile;
    }

    public String getMutant_viable() {
        return mutant_viable;
    }

    public void setMutant_viable(String mutant_viable) {
        this.mutant_viable = mutant_viable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_status() {
        return name_status;
    }

    public void setName_status(String name_status) {
        this.name_status = name_status;
    }

    public String getPheno_text() {
        return pheno_text;
    }

    public void setPheno_text(String pheno_text) {
        this.pheno_text = pheno_text;
    }

    public String getPheno_text_hetero() {
        return pheno_text_hetero;
    }

    public void setPheno_text_hetero(String pheno_text_hetero) {
        this.pheno_text_hetero = pheno_text_hetero;
    }

    public String getReporting_count() {
        return reporting_count;
    }

    public void setReporting_count(String reporting_count) {
        this.reporting_count = reporting_count;
    }

    public String getRequire_homozygous() {
        return require_homozygous;
    }

    public void setRequire_homozygous(String require_homozygous) {
        this.require_homozygous = require_homozygous;
    }

    public String getSibmatings() {
        return sibmatings;
    }

    public void setSibmatings(String sibmatings) {
        this.sibmatings = sibmatings;
    }

    public String getStr_access() {
        return str_access;
    }

    public void setStr_access(String str_access) {
        this.str_access = str_access;
    }

    public String getStr_status() {
        return str_status;
    }

    public void setStr_status(String str_status) {
        this.str_status = str_status;
    }

    public String getStr_type() {
        return str_type;
    }

    public void setStr_type(String str_type) {
        this.str_type = str_type;
    }

    public Integer getArchive_key() {
        return archive_key;
    }

    public void setArchive_key(Integer archive_key) {
        this.archive_key = archive_key;
    }

    public Integer getBackground_key() {
        return background_key;
    }

    public void setBackground_key(Integer background_key) {
        this.background_key = background_key;
    }

    public Integer getPersonShippingContact_key() {
        return personShippingContact_key;
    }

    public void setPersonShippingContact_key(Integer personShippingContact_key) {
        this.personShippingContact_key = personShippingContact_key;
    }

    public Integer getPersonCreator_key() {
        return personCreator_key;
    }

    public void setPersonCreator_key(Integer personCreator_key) {
        this.personCreator_key = personCreator_key;
    }

    public Integer getPersonSubmitter_key() {
        return personSubmitter_key;
    }

    public void setPersonSubmitter_key(Integer personSubmitter_key) {
        this.personSubmitter_key = personSubmitter_key;
    }

    public Integer getResidue_key() {
        return residue_key;
    }

    public void setResidue_key(Integer residue_key) {
        this.residue_key = residue_key;
    }

    public Integer getSubmission_key() {
        return submission_key;
    }

    public void setSubmission_key(Integer submission_key) {
        this.submission_key = submission_key;
    }

    public Set<AvailabilityStrain> getAvailabilitiesStrains() {
        return availabilitiesStrains;
    }

    public void setAvailabilitiesStrains(Set<AvailabilityStrain> availabilitiesStrains) {
        this.availabilitiesStrains = availabilitiesStrains;
    }

    public Set<BiblioStrain> getBibliosStrains() {
        return bibliosStrains;
    }

    public void setBibliosStrains(Set<BiblioStrain> bibliosStrains) {
        this.bibliosStrains = bibliosStrains;
    }

    public Set<CategoryStrain> getCategoriesStrains() {
        return categoriesStrains;
    }

    public void setCategoriesStrains(Set<CategoryStrain> categoriesStrains) {
        this.categoriesStrains = categoriesStrains;
    }

    public Set<RtoolStrain> getRtoolsStrains() {
        return rtoolsStrains;
    }

    public void setRtoolsStrains(Set<RtoolStrain> rtoolsStrains) {
        this.rtoolsStrains = rtoolsStrains;
    }

    public Set<Mutation> getMutations() {
        return mutations;
    }

    public void setMutations(Set<Mutation> mutations) {
        this.mutations = mutations;
    }

    public Set<ProjectStrain> getProjectsStrains() {
        return projectsStrains;
    }

    public void setProjectsStrains(Set<ProjectStrain> projectsStrains) {
        this.projectsStrains = projectsStrains;
    }

    public Set<SourceStrain> getSourcesStrains() {
        return sourcesStrains;
    }

    public void setSourcesStrains(Set<SourceStrain> sourcesStrains) {
        this.sourcesStrains = sourcesStrains;
    }

    public Set<SynonymStrain> getSynonymsStrains() {
        return synonymsStrains;
    }

    public void setSynonymsStrains(Set<SynonymStrain> synonymsStrains) {
        this.synonymsStrains = synonymsStrains;
    }

    public Set<WebRequest> getWebRequests() {
        return webRequests;
    }

    public void setWebRequests(Set<WebRequest> webRequests) {
        this.webRequests = webRequests;
    }

    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    public Residue getResidue() {
        return residue;
    }

    public void setResidue(Residue residue) {
        this.residue = residue;
    }

    public Person getShippingContact() {
        return shippingContact;
    }

    public void setShippingContact(Person shippingContact) {
        this.shippingContact = shippingContact;
    }

    public Person getSubmitter() {
        return submitter;
    }

    public void setSubmitter(Person submitter) {
        this.submitter = submitter;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLast_change() {
        return last_change;
    }

    public void setLast_change(Date last_change) {
        this.last_change = last_change;
    }
    
}
