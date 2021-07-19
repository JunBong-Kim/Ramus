package com.hackathon.ramus.Model;

import com.google.gson.annotations.SerializedName;

public class NotificationData {
    @SerializedName("to")
    private String token;
    @SerializedName("notification")
    private NotificationModel notificationModel;

    public NotificationData(String token, NotificationModel notificationModel) {
        this.token = token;
        this.notificationModel = notificationModel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public NotificationModel getNotificationModel() {
        return notificationModel;
    }

    public void setNotificationModel(NotificationModel notificationModel) {
        this.notificationModel = notificationModel;
    }
}
