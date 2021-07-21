package com.hackathon.ramus.Model;

import java.util.ArrayList;

public class User {
    private String userKey;
    private String userName;
    private String userStudentNumber;
    private String userFcmToken;
    private String userSeat;
    private ArrayList<Seat> seatHistoryList = new ArrayList<>();

    public User() {
    }

    public User(String userKey, String userName, String userStudentNumber, String userFcmToken, String userSeat) {
        this.userKey = userKey;
        this.userName = userName;
        this.userStudentNumber = userStudentNumber;
        this.userFcmToken = userFcmToken;
        this.userSeat = userSeat;
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

    public User(String userKey, String userName, String userStudentNumber, String userFcmToken, String userSeat, ArrayList<Seat> seatHistoryList) {
        this.userKey = userKey;
        this.userName = userName;
        this.userStudentNumber = userStudentNumber;
        this.userFcmToken = userFcmToken;
        this.userSeat = userSeat;
        this.seatHistoryList = seatHistoryList;
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

    public String getUserSeat() {
        return userSeat;
    }

    public void setUserSeat(String userSeat) {
        this.userSeat = userSeat;
    }

    public ArrayList<Seat> getSeatHistoryList() {
        return seatHistoryList;
    }

    public void setSeatHistoryList(ArrayList<Seat> seatHistoryList) {
        this.seatHistoryList = seatHistoryList;
    }
}
