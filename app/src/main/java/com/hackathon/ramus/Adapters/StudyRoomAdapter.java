package com.hackathon.ramus.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.Model.SeatItem;
import com.hackathon.ramus.R;

import java.util.ArrayList;

import static com.hackathon.ramus.Constants.SEAT_TYPE_NO;
import static com.hackathon.ramus.Constants.SEAT_TYPE_NO_THIN;
import static com.hackathon.ramus.Constants.SEAT_TYPE_PILLAR;
import static com.hackathon.ramus.Constants.SEAT_TYPE_PILLAR_LEFT_DOWN;
import static com.hackathon.ramus.Constants.SEAT_TYPE_PILLAR_LEFT_UP;
import static com.hackathon.ramus.Constants.SEAT_TYPE_PILLAR_RIGHT_DOWN;
import static com.hackathon.ramus.Constants.SEAT_TYPE_PILLAR_RIGHT_UP;
import static com.hackathon.ramus.Constants.SEAT_TYPE_YES;
import static com.hackathon.ramus.Constants.SEAT_TYPE_YES_SPACE;

public class StudyRoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private String TAG = "StudyRoomAdapter";
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

        View view;
        switch (viewType){
            case SEAT_TYPE_NO:
                Log.e(TAG, "onCreateViewHolder: type_no"  );
                view = mInflater.inflate(R.layout.item_study_room_empty, parent, false);
                return new EmptySeatViewHolder(view);
            case SEAT_TYPE_NO_THIN:
                Log.e(TAG, "onCreateViewHolder: type_no"  );
                view = mInflater.inflate(R.layout.item_study_room_empty_thin, parent, false);
                return new EmptySeatViewHolder(view);
            case SEAT_TYPE_YES:
                Log.e(TAG, "onCreateViewHolder: type_yes"  );
                view = mInflater.inflate(R.layout.item_stutdy_room, parent, false);
                return new SeatViewHolder(view);
            case SEAT_TYPE_YES_SPACE:
                view = mInflater.inflate(R.layout.item_stutdy_room_space,parent,false);
                return new SeatSpaceViewHolder(view);
            case SEAT_TYPE_PILLAR:
                view = mInflater.inflate(R.layout.item_study_room_pillar,parent,false);
                return new PillarViewHolder(view);
            case SEAT_TYPE_PILLAR_LEFT_DOWN:
                view = mInflater.inflate(R.layout.item_study_room_pillar_left_down,parent,false);
                return new PillarViewHolder(view);
            case SEAT_TYPE_PILLAR_LEFT_UP:
                view = mInflater.inflate(R.layout.item_study_room_pillar_left_up,parent,false);
                return new PillarViewHolder(view);
            case SEAT_TYPE_PILLAR_RIGHT_DOWN:
                view = mInflater.inflate(R.layout.item_study_room_pillar_right_down,parent,false);
                return new PillarViewHolder(view);
            case SEAT_TYPE_PILLAR_RIGHT_UP:
                view = mInflater.inflate(R.layout.item_study_room_pillar_right_up,parent,false);
                return new PillarViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       /* if( position % 2 == 0)((SeatViewHolder)(holder)).myTextView.setBackgroundColor(Color.parseColor("#e3f1fc"));
        else {
            ((SeatViewHolder)(holder)).myTextView.setBackgroundColor(Color.parseColor("#ffffff"));
        }*/
        if(holder instanceof SeatViewHolder){
            String seatKey  = seatItems.get(position).getSeatKey();

            ((SeatViewHolder)holder).myTextView.setText(seatKey.substring(seatKey.length()-2)+"");


            Log.e(TAG, "onBindViewHolder: " + position );
        }
        else if(holder instanceof SeatSpaceViewHolder){
            String seatKey  = seatItems.get(position).getSeatKey();
            ((SeatSpaceViewHolder)holder).myTextView.setText(seatKey.substring(seatKey.length()-2)+"");
        }
        else if (holder instanceof EmptySeatViewHolder){
            ((EmptySeatViewHolder)holder).myTextView.setVisibility(View.GONE);
        }else {
          //
        }
    }

    @Override
    public int getItemCount() {
        return  seatItems.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, SeatItem seatItem);
    }

    @Override
    public int getItemViewType(int position) {
        return seatItems.get(position).getViewType();
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
            if (mClickListener != null) mClickListener.onItemClick(view, seatItems.get(getAdapterPosition()));
        }
    }

    public class SeatSpaceViewHolder extends RecyclerView.ViewHolder{
        TextView myTextView;

        public SeatSpaceViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.info_text);
        }
    }

    public class EmptySeatViewHolder extends RecyclerView.ViewHolder{
        TextView myTextView;

        public EmptySeatViewHolder(@NonNull View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.info_text);

        }
    }

    public class PillarViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public PillarViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_pillar);
        }
    }




}
