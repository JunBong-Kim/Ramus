package com.hackathon.ramus.Model;

import com.google.gson.annotations.SerializedName;

public class NotificationData {
    @SerializedName("to")
    private String token;
    @SerializedName("notification")
    private NotificationModel notification;
    @SerializedName("data")
    private NotiElseModel data;

    public NotificationData(String token, NotificationModel notification, NotiElseModel data) {
        this.token = token;
        this.notification = notification;
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public NotificationModel getNotification() {
        return notification;
    }

    public void setNotification(NotificationModel notification) {
        this.notification = notification;
    }

    public NotiElseModel getData() {
        return data;
    }

    public void setData(NotiElseModel data) {
        this.data = data;
    }
}
