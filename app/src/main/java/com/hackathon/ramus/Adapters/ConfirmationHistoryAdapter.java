package com.hackathon.ramus.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hackathon.ramus.Model.ConfirmationHistory;
import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ConfirmationHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ItemClickListener mListener;

    public interface ItemClickListener {
        void onItemClick(String key);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.mListener = itemClickListener;
    }

    private Context mContext;
    private ArrayList<ConfirmationHistory> arrayList = new ArrayList<>();


    public ConfirmationHistoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_confimation_history,parent,false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((HistoryViewHolder)holder).textView_confirmation_day.setText(longToYY_MM(arrayList.get(position).getConfirmationDay()));
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private class HistoryViewHolder extends RecyclerView.ViewHolder{
        private TextView textView_confirmation_day;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_confirmation_day = itemView.findViewById(R.id.textView_confirmation_day);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getAdapterPosition() != RecyclerView.NO_POSITION){
                        mListener.onItemClick(arrayList.get(getAdapterPosition()).getConfirmationHistoryKey());
                    }
                }
            });

        }
    }

    public void setArrayList(ArrayList<ConfirmationHistory> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    private String longToYY_MM(long in){
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        Date date = new Date(in);
        return sdf.format(date);
    }

    /*

       private String confirmationHistoryKey;
    private String userKey;
    private ArrayList<Seat> seatHistoryList;
    private long confirmationDay;

     */
}
