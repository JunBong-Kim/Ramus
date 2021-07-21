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

    public SendNotification(String token,Context context) {
        this.token="etxgcfKbQ3WVylFe1uBh1I:APA91bFo_jwXGwcawkMmGmExasNVO5mKWuUVAzeBh3nptp6G-3tCxShbqN37vGjtAe4gNm_bgmTqmsBv-xj48NCkRj4CQk5Bu4_l2qEFFhcLFxGEmRGiE9csPhQ0qjw4a2-4IB_jsLs0";
        //this.token=token;
        this.context=context;
        apiService= Client.getClient().create(APIService.class);

    }
    public void send(Callback<MyResponse> callback){
        apiService.sendNotification(new NotificationData(token,new NotificationModel("ttle","body"))).enqueue(callback);
    }

}
