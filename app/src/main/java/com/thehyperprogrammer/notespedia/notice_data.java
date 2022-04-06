package com.thehyperprogrammer.notespedia;

public class notice_data{
    String notice_content,notice_sender,notice_date;

    public notice_data(String notice_content, String notice_sender, String notice_date) {
        this.notice_content = notice_content;
        this.notice_sender = notice_sender;
        this.notice_date = notice_date;
    }

    public notice_data() {
    }

    public String getNotice_content() {
        return notice_content;
    }

    public void setNotice_content(String notice_content) {
        this.notice_content = notice_content;
    }

    public String getNotice_sender() {
        return notice_sender;
    }

    public void setNotice_sender(String notice_sender) {
        this.notice_sender = notice_sender;
    }

    public String getNotice_date() {
        return notice_date;
    }

    public void setNotice_date(String notice_date) {
        this.notice_date = notice_date;
    }
}
