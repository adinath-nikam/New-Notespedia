package com.thehyperprogrammer.notespedia;

public class Scholarship_Model {

    String SC_IMG_URL,SC_NAME,SC_DESC,SC_AMOUNT,SC_CATEGORY,SC_EXPIRY,SC_URL;

    public Scholarship_Model() {
    }

    public Scholarship_Model(String SC_IMG_URL, String SC_NAME, String SC_DESC, String SC_AMOUNT, String SC_CATEGORY, String SC_EXPIRY, String SC_URL) {
        this.SC_IMG_URL = SC_IMG_URL;
        this.SC_NAME = SC_NAME;
        this.SC_DESC = SC_DESC;
        this.SC_AMOUNT = SC_AMOUNT;
        this.SC_CATEGORY = SC_CATEGORY;
        this.SC_EXPIRY = SC_EXPIRY;
        this.SC_URL = SC_URL;
    }

    public String getSC_NAME() {
        return SC_NAME;
    }

    public String getSC_DESC() {
        return SC_DESC;
    }

    public String getSC_AMOUNT() {
        return SC_AMOUNT;
    }

    public String getSC_CATEGORY() {
        return SC_CATEGORY;
    }

    public String getSC_EXPIRY() {
        return SC_EXPIRY;
    }

    public String getSC_URL() {
        return SC_URL;
    }

    public String getSC_IMG_URL() {
        return SC_IMG_URL;
    }

}
