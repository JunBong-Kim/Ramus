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

import java.util.ArrayList;

public class SpecificConfirmationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<Seat> arrayList = new ArrayList<>();

    public SpecificConfirmationAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_specific_confirmation_history,parent,false);
        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SeatViewHolder seatViewHolder = (SeatViewHolder)(holder);
        seatViewHolder.textView_seat_name.setText(arrayList.get(position).getSeatKey());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setList(ArrayList<Seat> list){
        arrayList = list;
        notifyDataSetChanged();
    }

    private class SeatViewHolder extends RecyclerView.ViewHolder{
        TextView textView_seat_name;
        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_seat_name = itemView.findViewById(R.id.textView_seat_name);
        }
    }
}
