package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {
    private WebView wv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        wv = (WebView) findViewById(R.id.webv);
        Intent intent = getIntent();
        String link = (String) intent.getSerializableExtra("link");
        if(link!=null) {
            wv.loadUrl(link);
        }
        else {
            Toast.makeText(WebViewActivity.this, "link not found ", Toast.LENGTH_SHORT).show();
        }
    }
}
