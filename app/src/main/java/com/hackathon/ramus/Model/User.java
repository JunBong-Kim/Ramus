package com.hackathon.ramus.Model;

public class User {
    private String userKey;
    private String userName;
    private String userStudentNumber;
    private String userFcmToken;

    public User() {
    }

    public User(String userKey, String userName, String userFcmToken) {
        this.userKey=userKey;
        this.userName = userName;
        this.userFcmToken = userFcmToken;
    }

    public User(String userKey, String userName, String userStudentNumber, String userFcmToken) {
        this.userKey = userKey;
        this.userName = userName;
        this.userStudentNumber = userStudentNumber;
        this.userFcmToken = userFcmToken;
    }

    public String getUserStudentNumber() {
        return userStudentNumber;
    }

    public void setUserStudentNumber(String userStudentNumber) {
        this.userStudentNumber = userStudentNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFcmToken() {
        return userFcmToken;
    }

    public void setUserFcmToken(String userFcmToken) {
        this.userFcmToken = userFcmToken;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
}
