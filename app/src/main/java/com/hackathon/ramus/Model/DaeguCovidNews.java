package com.hackathon.ramus.Model;

public class DaeguCovidNews {
    private String message;
    private String govName;
    private String areaName;
    private String date;
    private String time;
    private String sido;

    public DaeguCovidNews(String message, String govName, String areaName, String date, String time, String sido) {
        this.message = message;
        this.govName = govName;
        this.areaName = areaName;
        this.date = date;
        this.time = time;
        this.sido = sido;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGovName() {
        return govName;
    }

    public void setGovName(String govName) {
        this.govName = govName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSido() {
        return sido;
    }

    public void setSido(String sido) {
        this.sido = sido;
    }
}
