package com.hackathon.ramus.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hackathon.ramus.Model.ConfirmationHistory;
import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ConfirmationHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String TAG = "ConfirmationHistoryAdapter";

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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_confirmed,parent,false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((HistoryViewHolder)holder).textView_day.setText(longToYY_MM(arrayList.get(position).getConfirmationDay())+"\n"+(position +1)+"번 확진자 발생");


        Log.e(TAG, "onBindViewHolder: " + position);

        if(arrayList.get(position).getSeatHistoryList().size() == 0){
            ((HistoryViewHolder)holder).textView_content.setText("최근 14일내 이용기록 없음\n이전 기록은 관리자 문의 바랍니다.");
        }else
        ((HistoryViewHolder)holder).textView_content.setText(
                "최근 14일내 열람실 " + arrayList.get(position).getSeatHistoryList().size() + "회 이용");
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private class HistoryViewHolder extends RecyclerView.ViewHolder{
        private TextView textView_day,textView_content;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_day = itemView.findViewById(R.id.textView_day);
            textView_content = itemView.findViewById(R.id.textView_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getAdapterPosition() != RecyclerView.NO_POSITION){
                        if(arrayList.get(getAdapterPosition()).getSeatHistoryList().size() == 0){
                            Toast.makeText(mContext, "최근 14일 간 도서관 이용내역이 없습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
        Date date = new Date(in);
        return sdf.format(date);
    }

}
