package com.hackathon.ramus.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MySeatHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String TAG = "MySeatHistoryAdapter";
    private ArrayList<Seat> arrayList = new ArrayList<>();
    private Context mContext ;

    public MySeatHistoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_my_history,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Seat seat = arrayList.get(position);
        ((MyViewHolder)holder).textView_seat_name.setText(seat.getSeatKey());
         ((MyViewHolder)holder).textView_time.setText(HH_mm_format_day(seat.getSeatReservationStartTime()) +" ~ " +HH_mm_format_day(seat.getSeatReservationEndTime()));
        ((MyViewHolder)holder).textView_day.setText(MM_dd_format_day(seat.getSeatReservationStartTime()));
        Log.e(TAG, "onBindViewHolder: "  +MM_dd_format_day(seat.getSeatReservationStartTime()));
    }

    private String HH_mm_format_day(long time){
        SimpleDateFormat dateFormat = new SimpleDateFormat(" HH:mm");
        return dateFormat.format(time);
    }

    private String MM_dd_format_day(long time){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM:dd");
        return dateFormat.format(time);
    }

    public void setHistories(ArrayList<Seat> list){
        arrayList = list;
        notifyDataSetChanged();
    }

    /*

      SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    String formatTime = dateFormat.format(resultTime);
    return formatTime;

     */

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textView_seat_name,textView_day,textView_time;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_day = itemView.findViewById(R.id.textview_day);
            textView_seat_name = itemView.findViewById(R.id.textView_seat_name);
            textView_time =itemView.findViewById(R.id.textView_time);
        }
    }
}
