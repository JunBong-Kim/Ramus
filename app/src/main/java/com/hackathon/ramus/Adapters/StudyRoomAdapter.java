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
import java.util.List;

import static com.hackathon.ramus.Constants.SEAT_TYPE_NO;
import static com.hackathon.ramus.Constants.SEAT_TYPE_NO_THIN;
import static com.hackathon.ramus.Constants.SEAT_TYPE_PILLAR;
import static com.hackathon.ramus.Constants.SEAT_TYPE_PILLAR_LEFT_DOWN;
import static com.hackathon.ramus.Constants.SEAT_TYPE_PILLAR_LEFT_UP;
import static com.hackathon.ramus.Constants.SEAT_TYPE_PILLAR_RIGHT_DOWN;
import static com.hackathon.ramus.Constants.SEAT_TYPE_PILLAR_RIGHT_UP;
import static com.hackathon.ramus.Constants.SEAT_TYPE_YES;
import static com.hackathon.ramus.Constants.SEAT_TYPE_YES_SPACE;

public class StudyRoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String TAG = "StudyRoomAdapter";
    private ArrayList<SeatItem> seatItems = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private List<Seat> seats = new ArrayList<>();
    private Context context;

    public StudyRoomAdapter(Context context, ArrayList<SeatItem> mData) {
        this.context = context;
        this.seatItems = mData;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setSeatData(List<Seat> seats) {
        this.seats = seats;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case SEAT_TYPE_NO: //가로
                Log.e(TAG, "onCreateViewHolder: type_no");
                view = mInflater.inflate(R.layout.item_study_room_empty, parent, false);
                return new EmptySeatViewHolder(view);
            case SEAT_TYPE_NO_THIN: // 세로
                Log.e(TAG, "onCreateViewHolder: type_no");
                view = mInflater.inflate(R.layout.item_study_room_empty_thin, parent, false);
                return new EmptySeatViewHolder(view);
            case SEAT_TYPE_YES:  //파란색
                Log.e(TAG, "onCreateViewHolder: type_yes");
                view = mInflater.inflate(R.layout.item_stutdy_room, parent, false);
                return new SeatViewHolder(view);
            case SEAT_TYPE_YES_SPACE: //폐쇄석
                view = mInflater.inflate(R.layout.item_stutdy_room_space, parent, false);
                return new SeatSpaceViewHolder(view);
            case SEAT_TYPE_PILLAR_LEFT_DOWN:
                view = mInflater.inflate(R.layout.item_study_room_pillar_left_down, parent, false);
                return new PillarViewHolder(view);
            case SEAT_TYPE_PILLAR_LEFT_UP:
                view = mInflater.inflate(R.layout.item_study_room_pillar_left_up, parent, false);
                return new PillarViewHolder(view);
            case SEAT_TYPE_PILLAR_RIGHT_DOWN:
                view = mInflater.inflate(R.layout.item_study_room_pillar_right_down, parent, false);
                return new PillarViewHolder(view);
            case SEAT_TYPE_PILLAR_RIGHT_UP:
                view = mInflater.inflate(R.layout.item_study_room_pillar_right_up, parent, false);
                return new PillarViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SeatViewHolder) {
            ((SeatViewHolder) holder).setDrawable(position);
            Log.e(TAG, "onBindViewHolder: " + position);
        } else if (holder instanceof SeatSpaceViewHolder) {
            String seatCnt = seatItems.get(position).getCnt();
            ((SeatSpaceViewHolder) holder).myTextView.setText(seatCnt);
        } else if (holder instanceof EmptySeatViewHolder) {
            ((EmptySeatViewHolder) holder).myTextView.setVisibility(View.GONE);
        } else {
            //
        }
    }

    @Override
    public int getItemCount() {
        return seatItems.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, Seat seat);
    }

    @Override
    public int getItemViewType(int position) {
        return seatItems.get(position).getViewType();
    }

    public String getItem(int id) {
        return seatItems.get(id).toString();
    }

    public class SeatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView myTextView;

        public SeatViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.info_text);
            itemView.setOnClickListener(this);
        }

        public void setDrawable(int position) {
            String seatCnt = seatItems.get(position).getCnt();
            myTextView.setText(seatCnt);
            if (seats.size() != 0) {
                if (seats.get(Integer.parseInt(seatCnt) - 1).getSeatReservationEndTime() > System.currentTimeMillis()) {
                    //자리 불가
                    myTextView.setBackground(context.getDrawable(R.drawable.rec_seat_darkgray));
                } else {
                    myTextView.setBackground(context.getDrawable(R.drawable.rec_seat_blue));
                }
            }
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, seats.get(Integer.parseInt(seatItems.get(getAdapterPosition()).getCnt()) - 1));
        }
    }

    public class SeatSpaceViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;

        public SeatSpaceViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.info_text);
        }
    }

    public class EmptySeatViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;

        public EmptySeatViewHolder(@NonNull View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.info_text);

        }
    }

    public class PillarViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public PillarViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_pillar);
        }
    }


}
