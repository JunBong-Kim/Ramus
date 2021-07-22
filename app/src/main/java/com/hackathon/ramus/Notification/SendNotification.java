package com.hackathon.ramus.Notification;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.hackathon.ramus.Model.NotificationData;
import com.hackathon.ramus.Model.NotificationModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendNotification {
    private String token;
    private APIService apiService;
    private Context context;
    private String title, body;

    public SendNotification(String token, Context context) {
        this.token = token;
        this.context = context;
        apiService = Client.getClient().create(APIService.class);
    }

    public void send(int type, Callback<MyResponse> callback) {
        Log.d("SendNotification", token);
        classifiedType(type);
        apiService.sendNotification(new NotificationData(token, new NotificationModel(title, body))).enqueue(callback);
    }

    private void classifiedType(int type) {
        switch (type) {
            case 1:
                title = "마스크를 착용해 주세요";
                body = "마스크를 착용";
                break;
            case 2:
                title = "시끄럽습니다.";
                body = "조용해주세요";
                break;
            case 3:
                title = "너무 많이 비워두지마세요";
                body = "공용의 자리입니다.";
                break;
            default:
                break;
        }
    }

}
