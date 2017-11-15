package com.ascalonic.vigr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GoogleCalAdd extends AppCompatActivity {

    WebView caladd;

    public static int appo_id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_cal_add);

        setTitle("Adding event to your Calendar");
        caladd=(WebView)findViewById(R.id.webCal);

        // Enable Javascript
        WebSettings webSettings = caladd.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUserAgentString("Mozilla/5.0 Google");


        caladd.setWebViewClient(new WebViewClient());
        caladd.loadUrl("http://mitscse.acm.org/vigr/cal.php");

    }
}
