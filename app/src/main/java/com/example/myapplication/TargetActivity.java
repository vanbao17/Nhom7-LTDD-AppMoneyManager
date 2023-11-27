package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TargetActivity extends AppCompatActivity {
    private Button AddTargetBtn;
    private ImageView imageHome;
    UserSingleton userSingleton = UserSingleton.getInstance();
    UserEnity currentUser = userSingleton.getUser();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        imageHome = (ImageView) findViewById(R.id.home);
        AddTargetBtn = (Button) findViewById(R.id.btn_them);
        imageHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TargetActivity.this,PageHomeActivity.class);
                intent.putExtra("user",currentUser);
                startActivity(intent);
            }
        });
        AddTargetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TargetActivity.this, TargetInputActivity.class);
                intent.putExtra("user",currentUser);
                startActivity(intent);
            }
        });
    }
}
