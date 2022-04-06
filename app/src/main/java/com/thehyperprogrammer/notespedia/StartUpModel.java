package com.thehyperprogrammer.notespedia;

public class StartUpModel {

    String STARTUP_TITLE,STARTUP_DESC,STARTUP_IMG_URL;

    public StartUpModel(String STARTUP_TITLE, String STARTUP_DESC, String STARTUP_IMG_URL) {
        this.STARTUP_TITLE = STARTUP_TITLE;
        this.STARTUP_DESC = STARTUP_DESC;
        this.STARTUP_IMG_URL = STARTUP_IMG_URL;
    }

    public StartUpModel() {
    }

    public String getSTARTUP_TITLE() {
        return STARTUP_TITLE;
    }

    public String getSTARTUP_DESC() {
        return STARTUP_DESC;
    }

    public String getSTARTUP_IMG_URL() {
        return STARTUP_IMG_URL;
    }
}
