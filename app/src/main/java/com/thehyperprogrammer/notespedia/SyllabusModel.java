package com.thehyperprogrammer.notespedia;

public class SyllabusModel {

    String CHAPTER;

    public SyllabusModel() {
    }
    @Override
    public String toString() {
        return "SyllabusModel{" +
                "CHAPTER='" + CHAPTER + '\'' +
                '}';
    }
    public SyllabusModel(String CHAPTER) {
        this.CHAPTER = CHAPTER;
    }

    public String getCHAPTER() {
        return CHAPTER;
    }
}
