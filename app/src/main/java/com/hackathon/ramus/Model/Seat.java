package com.hackathon.ramus.Model;

public class Seat {

    private String seatKey;
    private String seatUserKey;
    private long seatReservationEndTime;

    public Seat(String seatKey, String seatUserKey, long seatReservationEndTime) {
        this.seatKey = seatKey;
        this.seatUserKey = seatUserKey;
        this.seatReservationEndTime = seatReservationEndTime;
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
}
