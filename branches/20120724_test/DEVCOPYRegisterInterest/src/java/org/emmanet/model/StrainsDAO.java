/*
 * StrainsDAO.java
 *
 * Created on 07 December 2007, 14:26
 *
 */
package org.emmanet.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author phil
 */
public class StrainsDAO implements Serializable {
    // private WebRequestsDAO webrequestsDAO;//linked table
//private String id_str;

    private int id_str;
    private String code_internal;
    private String name;
    private String health_status;
    private String generation;
    private String maintenance;
    private String charact_gen;
    private String str_access;
    private String username;
    //   private String last_change;
    private Date last_change;
    private String pheno_text;
    private String bg_id_bg;
    private String per_id_per;
    private String per_id_per_contact;
    private String per_id_per_sub;
    private String emma_id;
    private String mgi_ref;
    private String str_type;
    private String mta_file;
    private String gp_release;
    private String name_status;
    private String date_published;
    private String str_status;
    private String res_id;
    private String require_homozygous;
    private int/*String*/ archive_id;
    private String available_to_order;
    private String human_model;
    private String human_model_desc;
    private String mutant_viable;
    private String mutant_fertile;
    private String immunocompromised;
    private String exclusive_owner;
    private String additional_owner;
    private String ex_owner_description;    //relationship mapping
    private PeopleDAO peopleDAO;
    private ArchiveDAO archiveDAO;
    private BibliosStrainsDAO bibliosstrainsDAO;
    private ResiduesDAO residuesDAO;
    private BackgroundDAO backgroundDAO;
    private AvailabilitiesStrainsDAO availDAO;
    private Set projectsDAO;
    private Set AvailabilitiesStrainsDAO;//added 06042009
    private Set rtoolsDAO;
    private Set sources_StrainsDAO;
    private Set syn_strainsDAO;
    // private RToolsDAO rtoolsDAO;
    private Set mutationsStrainsDAO;
    private Set wrDAO;
    private LaboratoriesStrainsDAO LaboratoriesStrainsDAO;
    // private Set LaboratoriesStrainsDAO;
    //archiving mail messages for view
    private String MSGcontentFrz;
    private String MSGcontentArrv;
    private String MSGcontentComp;
    private List cvDAO;
    private String reporting_count;
    private Set categoriesStrainsDAO;

    public int getId_str() {
        return id_str;
    }

    public void setId_str(int id_str) {
        this.id_str = id_str;
    }

    public String getCode_internal() {
        return code_internal;
    }

    public void setCode_internal(String code_internal) {
        this.code_internal = code_internal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHealth_status() {
        return health_status;
    }

    public void setHealth_status(String health_status) {
        this.health_status = health_status;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public String getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(String maintenance) {
        this.maintenance = maintenance;
    }

    public String getCharact_gen() {
        return charact_gen;
    }

    public void setCharact_gen(String charact_gen) {
        this.charact_gen = charact_gen;
    }

    public String getStr_access() {
        return str_access;
    }

    public void setStr_access(String str_access) {
        this.str_access = str_access;
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

    public String getPheno_text() {
        return pheno_text;
    }

    public void setPheno_text(String pheno_text) {
        this.pheno_text = pheno_text;
    }

    public String getBg_id_bg() {
        return bg_id_bg;
    }

    public void setBg_id_bg(String bg_id_bg) {
        this.bg_id_bg = bg_id_bg;
    }

    public String getPer_id_per() {
        return per_id_per;
    }

    public void setPer_id_per(String per_id_per) {
        this.per_id_per = per_id_per;
    }

    public String getPer_id_per_contact() {
        return per_id_per_contact;
    }

    public void setPer_id_per_contact(String per_id_per_contact) {
        this.per_id_per_contact = per_id_per_contact;
    }

    public String getEmma_id() {
        return emma_id;
    }

    public void setEmma_id(String emma_id) {
        this.emma_id = emma_id;
    }

    public String getMgi_ref() {
        return mgi_ref;
    }

    public void setMgi_ref(String mgi_ref) {
        this.mgi_ref = mgi_ref;
    }

    public String getStr_type() {
        return str_type;
    }

    public void setStr_type(String str_type) {
        this.str_type = str_type;
    }

    public String getMta_file() {
        return mta_file;
    }

    public void setMta_file(String mta_file) {
        this.mta_file = mta_file;
    }

    public String getGp_release() {
        return gp_release;
    }

    public void setGp_release(String gp_release) {
        this.gp_release = gp_release;
    }

    public String getName_status() {
        return name_status;
    }

    public void setName_status(String name_status) {
        this.name_status = name_status;
    }

    public String getDate_published() {
        return date_published;
    }

    public void setDate_published(String date_published) {
        this.date_published = date_published;
    }

    public String getStr_status() {
        return str_status;
    }

    public void setStr_status(String str_status) {
        this.str_status = str_status;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public String getRequire_homozygous() {
        return require_homozygous;
    }

    public void setRequire_homozygous(String require_homozygous) {
        this.require_homozygous = require_homozygous;
    }

    public int/*String*/ getArchive_id() {
        return archive_id;
    }

    public void setArchive_id(int/*String*/ archive_id) {
        this.archive_id = archive_id;
    }

    public String getAvailable_to_order() {
        return available_to_order;
    }

    public void setAvailable_to_order(String available_to_order) {
        this.available_to_order = available_to_order;
    }

    public PeopleDAO getPeopleDAO() {
        return peopleDAO;
    }

    public void setPeopleDAO(PeopleDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    public ArchiveDAO getArchiveDAO() {
        return archiveDAO;
    }

    public void setArchiveDAO(ArchiveDAO archiveDAO) {
        this.archiveDAO = archiveDAO;
    }

    public Set getSources_StrainsDAO() {
        return sources_StrainsDAO;
    }

    public void setSources_StrainsDAO(Set sources_StrainsDAO) {
        this.sources_StrainsDAO = sources_StrainsDAO;
    }

    public Set getRtoolsDAO() {
        return rtoolsDAO;
    }

    public void setRtoolsDAO(Set rtoolsDAO) {
        this.rtoolsDAO = rtoolsDAO;
    }

    public Set getMutationsStrainsDAO() {
        return mutationsStrainsDAO;
    }

    public void setMutationsStrainsDAO(Set mutationsStrainsDAO) {
        this.mutationsStrainsDAO = mutationsStrainsDAO;
    }

    public Set getSyn_strainsDAO() {
        return syn_strainsDAO;
    }

    public void setSyn_strainsDAO(Set syn_strainsDAO) {
        this.syn_strainsDAO = syn_strainsDAO;
    }

    public BibliosStrainsDAO getBibliosstrainsDAO() {
        return bibliosstrainsDAO;
    }

    public void setBibliosstrainsDAO(BibliosStrainsDAO bibliosstrainsDAO) {
        this.bibliosstrainsDAO = bibliosstrainsDAO;
    }

    public String getHuman_model_desc() {
        return human_model_desc;
    }

    public void setHuman_model_desc(String human_model_desc) {
        this.human_model_desc = human_model_desc;
    }

    public ResiduesDAO getResiduesDAO() {
        return residuesDAO;
    }

    public void setResiduesDAO(ResiduesDAO residuesDAO) {
        this.residuesDAO = residuesDAO;
    }

    public BackgroundDAO getBackgroundDAO() {
        return backgroundDAO;
    }

    public void setBackgroundDAO(BackgroundDAO backgroundDAO) {
        this.backgroundDAO = backgroundDAO;
    }

    public String getMutant_viable() {
        return mutant_viable;
    }

    public void setMutant_viable(String mutant_viable) {
        this.mutant_viable = mutant_viable;
    }

    public String getMutant_fertile() {
        return mutant_fertile;
    }

    public void setMutant_fertile(String mutant_fertile) {
        this.mutant_fertile = mutant_fertile;
    }

    public String getImmunocompromised() {
        return immunocompromised;
    }

    public void setImmunocompromised(String immunocompromised) {
        this.immunocompromised = immunocompromised;
    }

    public String getHuman_model() {
        return human_model;
    }

    public void setHuman_model(String human_model) {
        this.human_model = human_model;
    }

    public String getExclusive_owner() {
        return exclusive_owner;
    }

    public void setExclusive_owner(String exclusive_owner) {
        this.exclusive_owner = exclusive_owner;
    }

    public String getEx_owner_description() {
        return ex_owner_description;
    }

    public void setEx_owner_description(String ex_owner_description) {
        this.ex_owner_description = ex_owner_description;
    }

    public AvailabilitiesStrainsDAO getAvailDAO() {
        return availDAO;
    }

    public void setAvailDAO(AvailabilitiesStrainsDAO availDAO) {
        this.availDAO = availDAO;
    }

    public String getMSGcontentFrz() {
        return MSGcontentFrz;
    }

    public void setMSGcontentFrz(String MSGcontentFrz) {
        this.MSGcontentFrz = MSGcontentFrz;
    }

    public String getMSGcontentArrv() {
        return MSGcontentArrv;
    }

    public void setMSGcontentArrv(String MSGcontentArrv) {
        this.MSGcontentArrv = MSGcontentArrv;
    }

    public String getMSGcontentComp() {
        return MSGcontentComp;
    }

    public void setMSGcontentComp(String MSGcontentComp) {
        this.MSGcontentComp = MSGcontentComp;
    }

    public Set getAvailabilitiesStrainsDAO() {
        return AvailabilitiesStrainsDAO;
    }

    public void setAvailabilitiesStrainsDAO(Set AvailabilitiesStrainsDAO) {
        this.AvailabilitiesStrainsDAO = AvailabilitiesStrainsDAO;
    }

    public Set getProjectsDAO() {
        return projectsDAO;
    }

    public void setProjectsDAO(Set projectsDAO) {
        this.projectsDAO = projectsDAO;
    }

    public Set getWrDAO() {
        return wrDAO;
    }

    public void setWrDAO(Set wrDAO) {
        this.wrDAO = wrDAO;
    }

    public LaboratoriesStrainsDAO getLaboratoriesStrainsDAO() {
        return LaboratoriesStrainsDAO;
    }

    public void setLaboratoriesStrainsDAO(LaboratoriesStrainsDAO LaboratoriesStrainsDAO) {
        this.LaboratoriesStrainsDAO = LaboratoriesStrainsDAO;
    }

    public String getAdditional_owner() {
        return additional_owner;
    }

    public void setAdditional_owner(String additional_owner) {
        this.additional_owner = additional_owner;
    }

    public List getCvDAO() {
        return cvDAO;
    }

    public void setCvDAO(List cvDAO) {
        this.cvDAO = cvDAO;
    }

    public String getReporting_count() {
        return reporting_count;
    }

    public void setReporting_count(String reporting_count) {
        this.reporting_count = reporting_count;
    }

    /* public Set getLaboratoriesStrainsDAO() {
    return LaboratoriesStrainsDAO;
    }
    
    public void setLaboratoriesStrainsDAO(Set LaboratoriesStrainsDAO) {
    this.LaboratoriesStrainsDAO = LaboratoriesStrainsDAO;
    }
     */

    /*
    public List<MutationsDAO> getMutationsDAO() {
    return mutationsDAO;
    }
    
    public void setMutationsDAO(List<MutationsDAO> mutationsDAO) {
    this.mutationsDAO = mutationsDAO;
    }
     */
    /* public WebRequestsDAO getWebrequestsDAO() {
    return webrequestsDAO;
    }
    
    public void setWebrequestsDAO(WebRequestsDAO webrequestsDAO) {
    this.webrequestsDAO = webrequestsDAO;
    }*/
    /**
     * @return the categoriesStrainsDAO
     */
    public Set getCategoriesStrainsDAO() {
        return categoriesStrainsDAO;
    }

    /**
     * @param categoriesStrainsDAO the categoriesStrainsDAO to set
     */
    public void setCategoriesStrainsDAO(Set categoriesStrainsDAO) {
        this.categoriesStrainsDAO = categoriesStrainsDAO;
    }

    /**
     * @return the per_id_per_sub
     */
    public String getPer_id_per_sub() {
        return per_id_per_sub;
    }

    /**
     * @param per_id_per_sub the per_id_per_sub to set
     */
    public void setPer_id_per_sub(String per_id_per_sub) {
        this.per_id_per_sub = per_id_per_sub;
    }
}
