package com.hackathon.ramus.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hackathon.ramus.Model.DaeguCovidNews;
import com.hackathon.ramus.R;

import java.util.ArrayList;

public class DaeguCovidConfirmationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<DaeguCovidNews> list = new ArrayList<>();

    public DaeguCovidConfirmationAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_daegu_covid_news,parent,false);
        return new NewsViewHolder(view);
    }

    public void setList(ArrayList<DaeguCovidNews> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DaeguCovidNews covidNews = list.get(position);
        NewsViewHolder holder1 = (NewsViewHolder)holder;
        holder1.textView_area_name.setText(covidNews.getAreaName());
        holder1.textView_gov_name.setText(covidNews.getGovName());
        holder1.textView_message.setText(covidNews.getMessage());
        holder1.textView_time.setText(covidNews.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class NewsViewHolder extends RecyclerView.ViewHolder{
        TextView textView_area_name,textView_time,textView_message,textView_gov_name;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_area_name = itemView.findViewById(R.id.textView_area_name);
            textView_time = itemView.findViewById(R.id.textView_time);
            textView_message = itemView.findViewById(R.id.textView_message);
            textView_gov_name = itemView.findViewById(R.id.textView_gov_name);
        }
    }
}
