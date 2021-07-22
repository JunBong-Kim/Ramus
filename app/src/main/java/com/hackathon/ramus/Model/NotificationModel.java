package com.hackathon.ramus.Model;

public class NotificationModel {
    private String notificationTitle;
    private String notificationBody;
    private int notificationType;
    private String receiveUserKey;
    private double notificationTime;

    public NotificationModel(String notificationTitle, String notificationBody) {
        this.notificationTitle = notificationTitle;
        this.notificationBody = notificationBody;
    }

    public NotificationModel(int notificationType, String receiveUserKey, double notificationTime) {
        this.notificationType = notificationType;
        this.receiveUserKey = receiveUserKey;
        this.notificationTime = notificationTime;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationBody() {
        return notificationBody;
    }

    public void setNotificationBody(String notificationBody) {
        this.notificationBody = notificationBody;
    }

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }

    public String getReceiveUserKey() {
        return receiveUserKey;
    }

    public void setReceiveUserKey(String receiveUserKey) {
        this.receiveUserKey = receiveUserKey;
    }

    public double getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(double notificationTime) {
        this.notificationTime = notificationTime;
    }
}
