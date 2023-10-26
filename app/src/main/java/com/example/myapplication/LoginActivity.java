package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin,btnSignin ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maindangnhap);
        btnLogin = (Button) findViewById(R.id.btnDN);
        btnSignin = (Button) findViewById(R.id.btnsignup);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(LoginActivity.this,PageHomeActivity.class);
                startActivity(intent);
            }
        });
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(LoginActivity.this,SigninActivity.class);
                startActivity(intent);
            }
        });
    }
}
