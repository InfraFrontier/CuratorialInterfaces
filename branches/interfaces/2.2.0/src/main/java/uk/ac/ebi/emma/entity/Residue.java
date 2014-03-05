 /**
 * Copyright © 2008 EMBL - European Bioinformatics Institute
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

/**
 *
 * @author phil
 */
public class Residue {
    
    private Integer id = 0;                                                     // primary key

    private String accepted;
    private String accepted_date;
    private String animal_husbandry;
    private String breeding_performance;
    private String char_genotyping;
    private String char_other;
    private String char_phenotyping;
    private String crelox;
    private String current_sanitary_status;
    private String delayed_description;
    private String delayed_release;
    private String delayed_wanted;
    private String deposited_elsewhere;
    private String deposited_elsewhere_text;
    private String flp;
    private String gestation_length;
    private String homozygous_matings_required_text;
    private String ip_rights;
    private String ipr_description;
    private String litters_in_lifetime;
    private String number_of_requests;
    private String other_labos;
    private String owner_permission;
    private String owner_permission_text;
    private String pups_at_birth;
    private String pups_at_weaning;
    private String remedial_actions;
    private String reproductive_decline_age;
    private String reproductive_maturity_age;
    private String specific_info;
    private String tet;
    private String weaning_age;
    private String welfare;
    private String when_how_many_females;
    private String when_how_many_males;
    private String when_how_many_month;
    private String when_how_many_year;
    private String when_mice_month;
    private String when_mice_year;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    public String getAccepted_date() {
        return accepted_date;
    }

    public void setAccepted_date(String accepted_date) {
        this.accepted_date = accepted_date;
    }

    public String getAnimal_husbandry() {
        return animal_husbandry;
    }

    public void setAnimal_husbandry(String animal_husbandry) {
        this.animal_husbandry = animal_husbandry;
    }

    public String getBreeding_performance() {
        return breeding_performance;
    }

    public void setBreeding_performance(String breeding_performance) {
        this.breeding_performance = breeding_performance;
    }

    public String getChar_genotyping() {
        return char_genotyping;
    }

    public void setChar_genotyping(String char_genotyping) {
        this.char_genotyping = char_genotyping;
    }

    public String getChar_other() {
        return char_other;
    }

    public void setChar_other(String char_other) {
        this.char_other = char_other;
    }

    public String getChar_phenotyping() {
        return char_phenotyping;
    }

    public void setChar_phenotyping(String char_phenotyping) {
        this.char_phenotyping = char_phenotyping;
    }

    public String getCrelox() {
        return crelox;
    }

    public void setCrelox(String crelox) {
        this.crelox = crelox;
    }

    public String getCurrent_sanitary_status() {
        return current_sanitary_status;
    }

    public void setCurrent_sanitary_status(String current_sanitary_status) {
        this.current_sanitary_status = current_sanitary_status;
    }

    public String getDelayed_description() {
        return delayed_description;
    }

    public void setDelayed_description(String delayed_description) {
        this.delayed_description = delayed_description;
    }

    public String getDelayed_release() {
        return delayed_release;
    }

    public void setDelayed_release(String delayed_release) {
        this.delayed_release = delayed_release;
    }

    public String getDelayed_wanted() {
        return delayed_wanted;
    }

    public void setDelayed_wanted(String delayed_wanted) {
        this.delayed_wanted = delayed_wanted;
    }

    public String getDeposited_elsewhere() {
        return deposited_elsewhere;
    }

    public void setDeposited_elsewhere(String deposited_elsewhere) {
        this.deposited_elsewhere = deposited_elsewhere;
    }

    public String getDeposited_elsewhere_text() {
        return deposited_elsewhere_text;
    }

    public void setDeposited_elsewhere_text(String deposited_elsewhere_text) {
        this.deposited_elsewhere_text = deposited_elsewhere_text;
    }

    public String getFlp() {
        return flp;
    }

    public void setFlp(String flp) {
        this.flp = flp;
    }

    public String getGestation_length() {
        return gestation_length;
    }

    public void setGestation_length(String gestation_length) {
        this.gestation_length = gestation_length;
    }

    public String getHomozygous_matings_required_text() {
        return homozygous_matings_required_text;
    }

    public void setHomozygous_matings_required_text(String homozygous_matings_required_text) {
        this.homozygous_matings_required_text = homozygous_matings_required_text;
    }

    public String getIp_rights() {
        return ip_rights;
    }

    public void setIp_rights(String ip_rights) {
        this.ip_rights = ip_rights;
    }

    public String getIpr_description() {
        return ipr_description;
    }

    public void setIpr_description(String ipr_description) {
        this.ipr_description = ipr_description;
    }

    public String getLitters_in_lifetime() {
        return litters_in_lifetime;
    }

    public void setLitters_in_lifetime(String litters_in_lifetime) {
        this.litters_in_lifetime = litters_in_lifetime;
    }

    public String getNumber_of_requests() {
        return number_of_requests;
    }

    public void setNumber_of_requests(String number_of_requests) {
        this.number_of_requests = number_of_requests;
    }

    public String getOther_labos() {
        return other_labos;
    }

    public void setOther_labos(String other_labos) {
        this.other_labos = other_labos;
    }

    public String getOwner_permission() {
        return owner_permission;
    }

    public void setOwner_permission(String owner_permission) {
        this.owner_permission = owner_permission;
    }

    public String getOwner_permission_text() {
        return owner_permission_text;
    }

    public void setOwner_permission_text(String owner_permission_text) {
        this.owner_permission_text = owner_permission_text;
    }

    public String getPups_at_birth() {
        return pups_at_birth;
    }

    public void setPups_at_birth(String pups_at_birth) {
        this.pups_at_birth = pups_at_birth;
    }

    public String getPups_at_weaning() {
        return pups_at_weaning;
    }

    public void setPups_at_weaning(String pups_at_weaning) {
        this.pups_at_weaning = pups_at_weaning;
    }

    public String getRemedial_actions() {
        return remedial_actions;
    }

    public void setRemedial_actions(String remedial_actions) {
        this.remedial_actions = remedial_actions;
    }

    public String getReproductive_decline_age() {
        return reproductive_decline_age;
    }

    public void setReproductive_decline_age(String reproductive_decline_age) {
        this.reproductive_decline_age = reproductive_decline_age;
    }

    public String getReproductive_maturity_age() {
        return reproductive_maturity_age;
    }

    public void setReproductive_maturity_age(String reproductive_maturity_age) {
        this.reproductive_maturity_age = reproductive_maturity_age;
    }

    public String getSpecific_info() {
        return specific_info;
    }

    public void setSpecific_info(String specific_info) {
        this.specific_info = specific_info;
    }

    public String getTet() {
        return tet;
    }

    public void setTet(String tet) {
        this.tet = tet;
    }

    public String getWeaning_age() {
        return weaning_age;
    }

    public void setWeaning_age(String weaning_age) {
        this.weaning_age = weaning_age;
    }

    public String getWelfare() {
        return welfare;
    }

    public void setWelfare(String welfare) {
        this.welfare = welfare;
    }

    public String getWhen_how_many_females() {
        return when_how_many_females;
    }

    public void setWhen_how_many_females(String when_how_many_females) {
        this.when_how_many_females = when_how_many_females;
    }

    public String getWhen_how_many_males() {
        return when_how_many_males;
    }

    public void setWhen_how_many_males(String when_how_many_males) {
        this.when_how_many_males = when_how_many_males;
    }

    public String getWhen_how_many_month() {
        return when_how_many_month;
    }

    public void setWhen_how_many_month(String when_how_many_month) {
        this.when_how_many_month = when_how_many_month;
    }

    public String getWhen_how_many_year() {
        return when_how_many_year;
    }

    public void setWhen_how_many_year(String when_how_many_year) {
        this.when_how_many_year = when_how_many_year;
    }

    public String getWhen_mice_month() {
        return when_mice_month;
    }

    public void setWhen_mice_month(String when_mice_month) {
        this.when_mice_month = when_mice_month;
    }

    public String getWhen_mice_year() {
        return when_mice_year;
    }

    public void setWhen_mice_year(String when_mice_year) {
        this.when_mice_year = when_mice_year;
    }

}
