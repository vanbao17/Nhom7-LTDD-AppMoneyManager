package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PageHomeActivity extends AppCompatActivity {
    private ImageView imageHistory,imageAdd,imageTarget,imagebiendong,imageAccount,imagethongke ;
    private TextView txtBudget,txtUsername;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        imageHistory = (ImageView) findViewById(R.id.btnhistory);
        imageAdd = (ImageView) findViewById(R.id.addthuchi);
        imageTarget = (ImageView) findViewById(R.id.targetImage);
        imagebiendong = (ImageView) findViewById(R.id.biendongsodu);
        imageAccount = (ImageView) findViewById(R.id.account);
        imagethongke = (ImageView) findViewById(R.id.thongke);
        txtBudget = (TextView) findViewById(R.id.textBudget);
        txtUsername = (TextView) findViewById(R.id.nameUser);
        UserEnity receivedUser = (UserEnity) getIntent().getSerializableExtra("user");
        if (receivedUser != null) {
            txtUsername.setText(receivedUser.getUsername().toString());
            txtBudget.setText( Integer.toString(receivedUser.getTotal()));
        }
        imageHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(PageHomeActivity.this,ManagerActivity.class);
                startActivity(intent);
            }
        });

        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(PageHomeActivity.this,KhoanChiActivity.class);
                startActivity(intent);
            }
        });
        imageTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(PageHomeActivity.this,TargetActivity.class);
                startActivity(intent);
            }
        });
        imagebiendong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(PageHomeActivity.this,HistoryActivity.class);
                startActivity(intent);
            }
        });
        imageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(PageHomeActivity.this,AccountActivity.class);
                startActivity(intent);
            }
        });
        imagethongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(PageHomeActivity.this,ThongkeActivity.class);
                startActivity(intent);
            }
        });
    }
}
