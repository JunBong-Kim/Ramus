package com.hackathon.ramus.Notification;

import com.hackathon.ramus.Model.NotificationData;
import com.hackathon.ramus.Model.NotificationModel;

import retrofit2.Callback;

public class SendNotification {
    private String token;
    private APIService apiService;

    public SendNotification(String token) {
        this.token="dy7ghFoBT_GzKY72bCo1xf:APA91bFjtHWg22Wq3_3cqGZ6y6GPU8HjqUzBFdmqtgVf9DmS6gay1Ms6_Egyv-nokByRdg1ZaZbl-o7hTAmMCjn4dHadQk8ouE66aONgB2Xq2Jh_P7I4cFMwvR3l_2-32ZexRoAAh1Df";
        //this.token=token;
        apiService= Client.getClient().create(APIService.class);
    }
    public void send(Callback<MyResponse> callback){
        apiService.sendNotification(new NotificationData(token,new NotificationModel("ttle","body"))).enqueue(callback);
    }
}
