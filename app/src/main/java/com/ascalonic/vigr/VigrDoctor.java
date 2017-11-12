package com.ascalonic.vigr;

/**
 * Created by HP on 12-11-2017.
 */

public class VigrDoctor {

    private int doc_id;
    private String doc_name, hosp_name, spec_name, place;

    public VigrDoctor() {
    }

    public VigrDoctor(int doc_id, String doc_name, String hosp_name, String spec_name, String place) {
        this.doc_id = doc_id;
        this.doc_name = doc_name;
        this.hosp_name = hosp_name;
        this.spec_name = spec_name;
        this.place = place;
    }

    public int getDocID() {
        return doc_id;
    }

    public String getDocName() {
        return doc_name;
    }

    public String getHospName() {
        return hosp_name;
    }

    public String getSpecName() {
        return spec_name;
    }

    public String getPlace() {
        return place;
    }

    public void setDocID(int doc_id) {
        this.doc_id = doc_id;
    }

    public void setDocName(String doc_name) {
        this.doc_name = doc_name;
    }

    public void setHospName(String hosp_name) {
        this.hosp_name = hosp_name;
    }

    public void setSpecName(String spec_name) {
        this.spec_name = spec_name;
    }

    public void setPlace(String place) {
        this.place = place;
    }


}
