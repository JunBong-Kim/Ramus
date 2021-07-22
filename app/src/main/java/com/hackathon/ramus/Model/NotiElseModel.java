package com.hackathon.ramus.Model;

public class NotiElseModel {

    private int notificationType;
    private String receiveUserKey;
    private double notificationTime;

    public NotiElseModel(int notificationType, String receiveUserKey, double notificationTime) {
        this.notificationType = notificationType;
        this.receiveUserKey = receiveUserKey;
        this.notificationTime = notificationTime;
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
