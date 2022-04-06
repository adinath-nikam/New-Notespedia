package com.thehyperprogrammer.notespedia;

public class SendDataDB {
    public String userName;
    public String userEmail;
    public String userPhoneNumber;
    public String userGender;
    public  String userDepartment;
    public  String userSemester;
    public String userCollege;

    public SendDataDB(){
    }

    public SendDataDB(String userName, String userEmail, String userPhoneNumber, String userGender, String userDepartment, String userSemester, String userCollege) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userGender = userGender;
        this.userDepartment = userDepartment;
        this.userSemester = userSemester;
        this.userCollege = userCollege;
    }

    public String getUserName() {
        return userName;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }
    public String getUserGender() {
        return userGender;
    }
    public String getUserDepartment() {
        return userDepartment;
    }
    public String getUserSemester() {
        return userSemester;
    }
    public String getUserCollege() {
        return userCollege;
    }
}
