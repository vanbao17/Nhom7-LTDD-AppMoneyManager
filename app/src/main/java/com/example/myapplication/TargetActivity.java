package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TargetActivity extends AppCompatActivity {
    private ImageView imageHome;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
            imageHome = (ImageView) findViewById(R.id.home);
        imageHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TargetActivity.this,PageHomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
