package com.hackathon.ramus.Model;

public class SeatItem {
    private String seatKey;
    private String seatUserKey;
    private long seatReservationEndTime;
    private String roomName;
    private long seatReservationStartTime;
    private int viewType;

    public SeatItem() {
    }

    public SeatItem(String seatKey, String seatUserKey, long seatReservationEndTime, String roomName, long seatReservationStartTime, int viewType) {
        this.seatKey = seatKey;
        this.seatUserKey = seatUserKey;
        this.seatReservationEndTime = seatReservationEndTime;
        this.roomName = roomName;
        this.seatReservationStartTime = seatReservationStartTime;
        this.viewType = viewType;
    }

    public String getSeatKey() {
        return seatKey;
    }

    public void setSeatKey(String seatKey) {
        this.seatKey = seatKey;
    }

    public String getSeatUserKey() {
        return seatUserKey;
    }

    public void setSeatUserKey(String seatUserKey) {
        this.seatUserKey = seatUserKey;
    }

    public long getSeatReservationEndTime() {
        return seatReservationEndTime;
    }

    public void setSeatReservationEndTime(long seatReservationEndTime) {
        this.seatReservationEndTime = seatReservationEndTime;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public long getSeatReservationStartTime() {
        return seatReservationStartTime;
    }

    public void setSeatReservationStartTime(long seatReservationStartTime) {
        this.seatReservationStartTime = seatReservationStartTime;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
