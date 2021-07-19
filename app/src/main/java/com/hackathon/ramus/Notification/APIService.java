package com.hackathon.ramus.Notification;

import com.hackathon.ramus.Model.NotificationData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA2Dmnn3s:APA91bE1fAB4K1H-gnLniObjgLcnAX-KFCei8WLluH4_PYEWA6TPkBBIbaP5pujisj_9F_BxQT91XgZ8UNt1QUbhUV4wwIqAJlx1BmU6Xd8dNZUQPIeO2uWFPW5Oc0401FiCFjRrnaEL"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body NotificationData body);

}
