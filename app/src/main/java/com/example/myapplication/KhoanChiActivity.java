package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.TextView;

public class KhoanChiActivity extends AppCompatActivity {
    private Button btnkhoanthu,btnBackHome;
    private TextView addTags;
    private Spinner spinnerTags;
    private ImageButton backHome;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_khoanchi);
        btnkhoanthu = (Button) findViewById(R.id.khoanthu) ;
        btnBackHome = (Button) findViewById(R.id.btnBack) ;
        addTags = (TextView) findViewById(R.id.addTags) ;
        backHome=(ImageButton) findViewById(R.id.backHome);
//        spinnerTags = (Spinner) findViewById(R.id.spiner) ;
        btnkhoanthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(KhoanChiActivity.this,KhoanThuActivity.class);
                startActivity(intent);
            }
        });
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(KhoanChiActivity.this,PageHomeActivity.class);
                startActivity(intent);
            }
        });
//        spinnerTags.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent  = new Intent(KhoanChiActivity.this,ChiActivity.class);
//                startActivity(intent);
//            }
//        });
        addTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(KhoanChiActivity.this,ChiActivity.class);
                startActivity(intent);
            }
        });
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KhoanChiActivity.this,PageHomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
