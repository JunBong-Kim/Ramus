package com.hackathon.ramus.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.Model.SeatItem;
import com.hackathon.ramus.R;

import java.util.ArrayList;

public class StudyRoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<SeatItem> seatItems= new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public StudyRoomAdapter(Context context, ArrayList<SeatItem> mData) {
        this.seatItems = mData;
        this.mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_stutdy_room, parent, false);
        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if( position % 2 == 0)((SeatViewHolder)(holder)).myTextView.setBackgroundColor(Color.parseColor("#e3f1fc"));
        else {
            ((SeatViewHolder)(holder)).myTextView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        ((SeatViewHolder)holder).myTextView.setText(position);
    }

    @Override
    public int getItemCount() {
        return  seatItems.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public String getItem(int id) {
        return seatItems.get(id).toString();
    }


    public class SeatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView myTextView;
        public SeatViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.info_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


}
