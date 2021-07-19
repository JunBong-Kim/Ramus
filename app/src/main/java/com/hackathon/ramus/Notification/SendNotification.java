package com.hackathon.ramus.Notification;

import com.hackathon.ramus.Model.NotificationData;
import com.hackathon.ramus.Model.NotificationModel;

import retrofit2.Callback;

public class SendNotification {
    private String token;
    private APIService apiService;

    public SendNotification(String token) {
        this.token="fKtRajhQTtCvqU5v5aqQJ-:APA91bGwR3g8cylzGRpDxqZRnMToMThhK1QnCbSulEWQgoOeBtqxrbj3EyeS0FhzL8-m2cj27fRF-hlWZ2FlQuvTxPixXIwsfMYgL0hLmtkPgWH6bLlXvcy6WgvQ4IFU0uwmIuNhodjG";
        //this.token=token;
        apiService= Client.getClient().create(APIService.class);
    }
    public void send(Callback<MyResponse> callback){
        apiService.sendNotification(new NotificationData(token,new NotificationModel("ttle","body"))).enqueue(callback);
    }
}
