package com.example.webviewactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    private WebView mwebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        inlistener();
    }

    private void inlistener() {
        mwebview.getSettings().setJavaScriptEnabled(true);
        mwebview.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view,String url){
                view.loadUrl(url);
                Log.d(TAG,"inlistener shouldOverrideUrlLoading...");
                return true;
            }
        });
        mwebview.loadUrl("https://www.doc88.com/p-5196905597948.html");
    }

    private void init() {
        mwebview = (WebView)findViewById(R.id.web_view);
    }
}
