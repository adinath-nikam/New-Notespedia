package com.thehyperprogrammer.notespedia;

public class UploadFileHelper {

    String Filename, FileDesc, FileSub, Time, Date,Username,Url;

    public UploadFileHelper() {
    }

    public UploadFileHelper(String username,String filename, String fileDesc, String fileSub, String time, String date, String url) {
        Filename = filename;
        FileDesc = fileDesc;
        FileSub = fileSub;
        Time = time;
        Date = date;
        Username = username;
        Url = url;
    }

    public String getFilename() {
        return Filename;
    }

    public void setFilename(String filename) {
        Filename = filename;
    }

    public String getFileDesc() {
        return FileDesc;
    }

    public void setFileDesc(String fileDesc) {
        FileDesc = fileDesc;
    }

    public String getFileSub() {
        return FileSub;
    }

    public void setFileSub(String fileSub) {
        FileSub = fileSub;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
