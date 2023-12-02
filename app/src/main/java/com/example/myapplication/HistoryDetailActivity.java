package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String historyText = intent.getStringExtra("historyText");

        // Hiển thị dữ liệu lịch sử trong TextView hoặc ListView, hoặc biểu đồ tùy thuộc vào ý muốn của bạn
        TextView historyTextView = findViewById(R.id.historyTextView);
        historyTextView.setText(historyText);

        // Sử dụng ImageView để quay lại trang trước
        ImageView imageViewBack = findViewById(R.id.imageViewback);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Gọi phương thức quay lại
            }
        });
    }
}
