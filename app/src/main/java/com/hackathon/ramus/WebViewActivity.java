package com.hackathon.ramus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.hackathon.ramus.databinding.ActivityWebViewBinding;

import static com.hackathon.ramus.Constants.INTENT_DATA_WEB_VIEW_TYPE;
import static com.hackathon.ramus.Constants.TYPE_DAEGU;
import static com.hackathon.ramus.Constants.TYPE_KNU;
import static com.hackathon.ramus.MainActivity.*;

public class WebViewActivity extends AppCompatActivity {

    private ActivityWebViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        int type =  Integer.parseInt(String.valueOf(getIntent().getExtras().get(INTENT_DATA_WEB_VIEW_TYPE)));
        setView(type);
    }

    private void init(){
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void setView(int type){

        binding.webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        binding.webview.setWebViewClient(new WebViewClient());
        binding.webview.setWebChromeClient(new WebChromeClient());
        binding.webview.setNetworkAvailable(true);
        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.getSettings().setDomStorageEnabled(true );

        switch (type){
            case TYPE_KNU:
                binding.webview.loadUrl("https://www.knu.ac.kr/wbbs/wbbs/bbs/btin/list.action?bbs_cde=34&menu_idx=224");
                break;
            case TYPE_DAEGU:
                binding.webview.loadUrl("http://covid19.daegu.go.kr/00937420.html");
                break;
            default:
                break;
        }
    }
}
