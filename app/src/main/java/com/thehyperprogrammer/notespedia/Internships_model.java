package com.thehyperprogrammer.notespedia;

public class Internships_model {

    String IMG_URL,INTERN_URL,INTERN_COMPANY,INTERN_ADDRESS, INTERN_PH,INTERN_EMAIL,INTERN_HRS,INTERN_SKILLS;

    public Internships_model(String IMG_URL, String INTERN_URL, String INTERN_COMPANY, String INTERN_ADDRESS, String INTERN_PH, String INTERN_EMAIL, String INTERN_HRS, String INTERN_SKILLS) {
        this.IMG_URL = IMG_URL;
        this.INTERN_URL = INTERN_URL;
        this.INTERN_COMPANY = INTERN_COMPANY;
        this.INTERN_ADDRESS = INTERN_ADDRESS;
        this.INTERN_PH = INTERN_PH;
        this.INTERN_EMAIL = INTERN_EMAIL;
        this.INTERN_HRS = INTERN_HRS;
        this.INTERN_SKILLS = INTERN_SKILLS;
    }

    public Internships_model() {
    }

    public String getIMG_URL() {
        return IMG_URL;
    }

    public String getINTERN_URL() {
        return INTERN_URL;
    }

    public String getINTERN_COMPANY() {
        return INTERN_COMPANY;
    }

    public String getINTERN_ADDRESS() {
        return INTERN_ADDRESS;
    }

    public String getINTERN_PH() {
        return INTERN_PH;
    }

    public String getINTERN_EMAIL() {
        return INTERN_EMAIL;
    }

    public String getINTERN_HRS() {
        return INTERN_HRS;
    }

    public String getINTERN_SKILLS() {
        return INTERN_SKILLS;
    }

}
