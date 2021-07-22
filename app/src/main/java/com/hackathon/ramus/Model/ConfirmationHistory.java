package com.hackathon.ramus.Model;

import android.view.View;

import java.util.ArrayList;

public class ConfirmationHistory {

    private String confirmationHistoryKey;
    private String userKey;
    private ArrayList<Seat> seatHistoryList;
    private long confirmationDay;



    public ConfirmationHistory() {
    }

    public ConfirmationHistory(String confirmationHistoryKey, String userKey, ArrayList<Seat> seatHistoryList, long confirmationDay) {
        this.confirmationHistoryKey = confirmationHistoryKey;
        this.userKey = userKey;
        this.seatHistoryList = seatHistoryList;
        this.confirmationDay = confirmationDay;
    }

    public String getConfirmationHistoryKey() {
        return confirmationHistoryKey;
    }

    public void setConfirmationHistoryKey(String confirmationHistoryKey) {
        this.confirmationHistoryKey = confirmationHistoryKey;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public ArrayList<Seat> getSeatHistoryList() {
        return seatHistoryList;
    }

    public void setSeatHistoryList(ArrayList<Seat> seatHistoryList) {
        this.seatHistoryList = seatHistoryList;
    }

    public long getConfirmationDay() {
        return confirmationDay;
    }

    public void setConfirmationDay(long confirmationDay) {
        this.confirmationDay = confirmationDay;
    }
}


/*


public static final String FIELD_NAME_CONFIRMATION_HISTORY = "confirmationHistoryKey";
    public static final String FIELD_NAME_CONFIRMATION_USER_KEY = "confirmationUserKey";
    public static final String FIELD_NAME_CONFIRMATION_USER_HISTORY = "seatHistoryList";
    public static final String FIELD_NAME_CONFIRMATION_DAY = "confirmationDay";

    confirmationHistoryKey : Sting
    userKey : String
    seatHistoryList : ArrayList<Seat>
    confirmationDay : long’
 */