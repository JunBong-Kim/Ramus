package com.hackathon.ramus;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hackathon.ramus.Adapters.DaeguCovidConfirmationAdapter;
import com.hackathon.ramus.Model.DaeguCovidNews;
import com.hackathon.ramus.databinding.ActivityDaeguCovidConfirmationBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class DaeguCovidConfirmationActivity extends AppCompatActivity {
    private String TAG = "parsing";
    private ActivityDaeguCovidConfirmationBinding binding;
    private Document doc;
    private ArrayList<DaeguCovidNews> arrayList = new ArrayList<>();
    private DaeguCovidConfirmationAdapter adapter;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        new JsoupAsyncTask().execute();
    }

    private void init(){
        binding = ActivityDaeguCovidConfirmationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new DaeguCovidConfirmationAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        binding.toolbar.setTitle("대구시 재난문자");
        setSupportActionBar(binding.toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {
        String res = "";
        ProgressDialog asyncDialog = new ProgressDialog(DaeguCovidConfirmationActivity.this);
        private String htmlPageUrl = "https://m.news.naver.com/covid19/live.nhn";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            asyncDialog.setMessage("잠시만 기다려 주세요");
            asyncDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                doc = Jsoup.connect(htmlPageUrl).get();

                Elements scriptElements = doc.getElementsByTag("script");
                int cnt = 0;
                for (Element element :scriptElements ){
                    if(cnt == 6){
                        res = element.toString();
                    }
                    cnt++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
           // Log.e(TAG, "onPostExecute: " + res );
            if (res.equals("")) {
                Toast.makeText(DaeguCovidConfirmationActivity.this, "네트워크 확인 바랍니다.", Toast.LENGTH_SHORT).show();
            } else {
                res = res.substring(291);
                Log.e(TAG, "doInBackground: "+ res);
                List<String> list = Arrays.asList(res.split(Pattern.quote("sidoList")));

                List<String> list1 = Arrays.asList(list.get(0).split(Pattern.quote("\"id\":")));
                //첫번쨰 빈거 들어옴

                for (int i = 1; i < list1.size(); i++) {
                    List<String> list2 = Arrays.asList(list1.get(i).split(Pattern.quote("\"")));
                    if(list2.get(13).equals("대구"))
                    arrayList.add(new DaeguCovidNews(list2.get(5), list2.get(9), list2.get(13), list2.get(17), list2.get(21), list2.get(25)));
                }


            }
            adapter.setList(arrayList);
            asyncDialog.dismiss();

        }
    }
}