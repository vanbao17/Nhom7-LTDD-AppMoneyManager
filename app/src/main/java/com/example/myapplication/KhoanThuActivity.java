package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class KhoanThuActivity extends AppCompatActivity {
    private Button btnKhoanchi,btnBack;
    private TextView addTags;

    private ImageButton backHome;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_khoanthu);
        btnKhoanchi = (Button) findViewById(R.id.khoanchi);
        btnBack = (Button) findViewById(R.id.btnBack) ;
        addTags = (TextView) findViewById(R.id.addTags) ;
        backHome = (ImageButton) findViewById(R.id.backHome);
        btnKhoanchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KhoanThuActivity.this, KhoanChiActivity.class);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(KhoanThuActivity.this,PageHomeActivity.class);
                startActivity(intent);
            }
        });
        addTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(KhoanThuActivity.this,ThuActivity.class);
                startActivity(intent);
            }
        });
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KhoanThuActivity.this, PageHomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
