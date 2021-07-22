package com.hackathon.ramus.Adapters;

import android.content.Context;
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
import java.util.Date;

public class SpecificConfirmationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<Seat> arrayList = new ArrayList<>();
    private long confirmationTime;
    public SpecificConfirmationAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclervie_confirmed_detail,parent,false);
        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SeatViewHolder seatViewHolder = (SeatViewHolder)(holder);
        Seat seat = arrayList.get(position);
        seatViewHolder.textView_seat_name.setText(seat.getSeatKey());
        seatViewHolder.textView_day.setText(longToMM_dd(seat.getSeatReservationEndTime()));
        String startTime = longToTime(seat.getSeatReservationStartTime()).substring(11,16);
        String endTime = longToTime(seat.getSeatReservationEndTime()).substring(11,16);
        seatViewHolder.textView_time.setText(startTime +"~" + endTime);
        seatViewHolder.textView_confirmed_day.setText("확진일 : "+longToMM_dd(confirmationTime));
    }


    private String longToYY_MM_dd(long in){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(in);
        return sdf.format(date);
    }

    private String longToTime(long in){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(in);
        return sdf.format(date);
    }

    private String longToMM_dd(long in){

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        Date date = new Date(in);
        return sdf.format(date);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setList(ArrayList<Seat> list,long time){
        arrayList = list;
        confirmationTime = time;
        notifyDataSetChanged();
    }

    private class SeatViewHolder extends RecyclerView.ViewHolder{
        TextView textView_seat_name,textView_day,textView_time,textView_confirmed_day;
        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_seat_name = itemView.findViewById(R.id.textView_seat_name);
            textView_day = itemView.findViewById(R.id.textview_day);
            textView_time= itemView.findViewById(R.id.textView_time);
            textView_confirmed_day= itemView.findViewById(R.id.textview_confirmed_day);
        }
    }
}
