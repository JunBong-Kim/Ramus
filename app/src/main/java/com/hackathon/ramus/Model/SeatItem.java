package com.hackathon.ramus.Model;

public class SeatItem {
    private String cnt;
    private int viewType;

    public SeatItem() {
    }

    public SeatItem(String cnt, int viewType) {
        this.cnt = cnt;
        this.viewType = viewType;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}