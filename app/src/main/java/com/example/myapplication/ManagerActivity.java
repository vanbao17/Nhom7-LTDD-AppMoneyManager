package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ManagerActivity extends AppCompatActivity {
    private ImageView imageHome;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        imageHome = (ImageView) findViewById(R.id.backHome);
        imageHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(ManagerActivity.this, PageHomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
