package com.hackathon.ramus.Notification;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.hackathon.ramus.Model.NotiElseModel;
import com.hackathon.ramus.Model.NotificationData;
import com.hackathon.ramus.Model.NotificationModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendNotification {
    private String token;
    private APIService apiService;
    private Context context;

    public SendNotification(String token, Context context) {
        this.token = token;
        this.context = context;
        apiService = Client.getClient().create(APIService.class);
    }

    public void send(NotificationModel notiModel, NotiElseModel elseModel, Callback<MyResponse> callback) {
        apiService.sendNotification(new NotificationData(token,notiModel,elseModel)).enqueue(callback);
    }

}
