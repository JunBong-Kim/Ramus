package com.hackathon.ramus;

public class Constants {

    // Collection Table Name
    public static final String COLLECTION_NAME_OF_USERS = "USERS";
    public static final String COLLECTION_NAME_OF_SEATS = "SEATS";
    public static final String COLLECTION_NAME_OF_CONFIRMATION_HISTORY = "CONFIRMATION_HISTORY";


    //user Table Field Name
    public static final String FILED_NAME_USER_NAME = "userName";
    public static final String FILED_NAME_USER_FCM_TOKEN = "userFcmToken";
    public static final String FILED_NAME_USER_KEY = "userKey";
    public static final String FILED_NAME_USER_STUDENT_NUMBER = "userStudentNumber";
    public static final String FIELD_NAME_SEAT_KEY = "seatKey";
    public static final String FIELD_NAME_SEAT_USER_KEY = "seatUserKey";
    public static final String FIELD_NAME_SEAT_RESERVATION_END_TIME = "seatReservationEndTime";
    public static final String FIELD_NAME_SEAT_RESERVATION_START_TIME = "seatReservationStartTime";
    public static final String FIELD_NAME_USER_USER_SEAT = "userSeat";
    public static final String FIELD_NAME_SEAT_ROOM_NAME = "roomName";
    public static final String FIELD_NAME_USER_SEAT_HISTORY = "seatHistoryList";
    public static final String FIELD_NAME_CONFIRMATION_HISTORY_KEY = "confirmationHistoryKey";
    public static final String FIELD_NAME_CONFIRMATION_USER_KEY = "confirmationUserKey";
    public static final String FIELD_NAME_CONFIRMATION_USER_HISTORY = "seatHistoryList";
    public static final String FIELD_NAME_CONFIRMATION_DAY = "confirmationDay";

    public static final String INTENT_DATA_KEY = "INTENT_DATA_KEY";
    /*


confirmationHistoryKey : Sting
userKey : String
seatHistoryList : ArrayList<Seat>
confirmationDay : long’

     */

    public static final String FIELD_NAME_USER_NOTIFICATION_HISTORY = "notificationHistoryList";


    public static final String INTENT_DATA_NUMBER = "INTENT_DATA_NUMBER";
    public static final String INTENT_DATA_EMAIL = "INTENT_DATA_EMAIL";
    public static final String INTENT_DATA_WEB_VIEW_TYPE = "INTENT_DATA_WEB_VIEW_TYPE";

    public static final String DATA_USER_SEAT_NULL = "NULL";

    public static final int TYPE_KNU = 0;
    public static final int TYPE_DAEGU = 1;
    public static final int TYPE_KB = 2;
    public static final int TYPE_MOHW = 3;
    public static final int TYPE_NAVER = 4;


    public static final int SEAT_TYPE_NO_THIN = 4;
    public static final int SEAT_TYPE_NO = 0;
    public static final int SEAT_TYPE_YES= 1;
    public static final int SEAT_TYPE_YES_SPACE= 9;
    public static final int SEAT_TYPE_PILLAR = 2;
    public static final int SEAT_TYPE_PILLAR_LEFT_UP = 5;
    public static final int SEAT_TYPE_PILLAR_RIGHT_UP = 6;
    public static final int SEAT_TYPE_PILLAR_LEFT_DOWN = 7;
    public static final int SEAT_TYPE_PILLAR_RIGHT_DOWN = 8;
    /*


      private String seatKey;
    private String seatUserKey;
    private long seatReservationEndTime;

     */
}
