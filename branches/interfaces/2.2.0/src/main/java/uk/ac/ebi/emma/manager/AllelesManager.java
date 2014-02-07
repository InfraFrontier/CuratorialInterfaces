/**
 * Copyright © 2009-2013 EMBL - European Bioinformatics Institute
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
package uk.ac.ebi.emma.manager;

import com.google.gson.Gson;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import uk.ac.ebi.emma.entity.Allele;
import uk.ac.ebi.emma.entity.Gene;
import uk.ac.ebi.emma.entity.GeneSynonym;

/**
 *
 * @author mrelac
 */
public class AllelesManager extends AbstractManager {
    private GenesManager genesManager = new GenesManager();
    
     public List getAlleles() {
        Session session = getCurrentSession();
        session.beginTransaction();
        List strains = null;
        try {
            strains = session.createQuery(
                    "FROM Allele").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strains;
    }
     
     //get alleles by id, use SQLQuery and return list.
     
    public List getAllelesByID(String alleleIDs) {
        Session session = getCurrentSession();
        session.beginTransaction();
        List alleles = null;
        try {
            alleles = session.createSQLQuery(
                    "SELECT * FROM alleles where id_allel IN (?)").setParameter(0, alleleIDs).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return alleles;
    }
          
    public Allele getAlleleByID(int id_allel) {
        Session session = getCurrentSession();
        session.beginTransaction();
        Allele ad = null;
        try {
            ad = (Allele) session.get(Allele.class,
                    id_allel);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return ad;
    }
     
     
    
    public void save(Allele aDAO) {
        /************** FIXME FIXME FIXME WHERE IS USERNAME AND TIMESTAMP? *******************/
        /************** FIXME FIXME FIXME WHERE IS USERNAME AND TIMESTAMP? *******************/
        /************** FIXME FIXME FIXME WHERE IS USERNAME AND TIMESTAMP? *******************/
        /************** FIXME FIXME FIXME WHERE IS USERNAME AND TIMESTAMP? *******************/
        Session session = getCurrentSession();
        session.beginTransaction();

        try {
            session.saveOrUpdate(aDAO);
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }    
    
//    public static String toJSON(Allele allele) {
//        return new Gson().toJson(allele);
//    }
    
//    /**
//     * Transforms a <code>List&lt;Gene&gt;</code> to a JSON string.
//     * @param allelesList the list to be transformed
//     * @return the transformed JSON string
//     */
//    public static String toJSON(List<Allele> allelesList) {
//        JSONArray jsonList = new JSONArray();
//        for (Allele allele : allelesList) {
//            JSONObject jsonAllele = new JSONObject();
//            
//            
//            
//            jsonAllele.put("symbol",      allele.getAlls_form() == null ? "" : allele.getAlls_form());
//            jsonAllele.put("gen_id_gene", allele.getGen_id_gene() == null ? "" : allele.getGen_id_gene());
//            jsonAllele.put("id_allel",    Integer.toString(allele.getId_allel()));
//            jsonAllele.put("mgi_ref",     allele.getMgi_ref() == null ? "" : allele.getMgi_ref());
//            jsonAllele.put("name",        allele.getName() == null ? "" : allele.getName());
//            jsonAllele.put("strainId",    allele.getStrainID() == null ? "" : allele.getStrainID());
//            
//            if (allele.getGene() != null) {
//                    JSONObject jsonGene = GenesManager.toJSON(allele.getGene());
//                    jsonGene.put("gene", jsonGene);
//                    
//                    
//                    
//                    
//                    jsonGene.put("symbol", geneSynonym.getSymbol());
//                    jsonGene.put("id_syn", Integer.toString(geneSynonym.getId_syn()));
//                    synonyms.add(jsonGene);
//                }
//                jsonAllele.put("synonyms", synonyms);
//            }
//            
//            if ((allele.getSynonyms() != null) && (allele.getSynonyms().size() > 0)) {
//                JSONArray synonyms = new JSONArray();
//                Iterator<GeneSynonym> iterator = allele.getSynonyms().iterator();
//                while (iterator.hasNext()) {
//                    GeneSynonym geneSynonym = iterator.next();
//                    JSONObject synonym = new JSONObject();
//                    synonym.put("id_syn", Integer.toString(geneSynonym.getId_syn()));
//                    synonym.put("name",   geneSynonym.getName());
//                    synonym.put("symbol", geneSynonym.getSymbol());
//                    synonyms.add(synonym);
//                }
//                jsonAllele.put("synonyms", synonyms);
//            }
//            
//            jsonList.add(jsonAllele);
//        }
//        
//
//        // Gson dosn't reserve space for fields with null values!!!!
//////////        Gson gson = new Gson();
//////////            String s = gson.toJson(genesDAOList);
//////////            System.out.println(s);
//////////        return s;
//        
//        return jsonList.toString();
//    }
    
}
