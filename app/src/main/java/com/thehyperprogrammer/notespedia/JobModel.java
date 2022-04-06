package com.thehyperprogrammer.notespedia;

public class JobModel{
    String CMP_NAME,CMP_CAT,CMP_CALL,CMP_MAIL,CMP_URL,CMP_IMG_URL;

    public JobModel(String CMP_NAME, String CMP_CAT, String CMP_CALL, String CMP_MAIL, String CMP_URL, String CMP_IMG_URL) {
        this.CMP_NAME = CMP_NAME;
        this.CMP_CAT = CMP_CAT;
        this.CMP_CALL = CMP_CALL;
        this.CMP_MAIL = CMP_MAIL;
        this.CMP_URL = CMP_URL;
        this.CMP_IMG_URL = CMP_IMG_URL;
    }

    public JobModel() {
    }

    public String getCMP_NAME() {
        return CMP_NAME;
    }

    public String getCMP_CAT() {
        return CMP_CAT;
    }

    public String getCMP_CALL() {
        return CMP_CALL;
    }

    public String getCMP_MAIL() {
        return CMP_MAIL;
    }

    public String getCMP_URL() {
        return CMP_URL;
    }

    public String getCMP_IMG_URL() {
        return CMP_IMG_URL;
    }
}