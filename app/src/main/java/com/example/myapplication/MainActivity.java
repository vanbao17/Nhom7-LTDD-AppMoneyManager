package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private TextView userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        userName = (TextView) findViewById(R.id.nameUser);
        UserEnity receivedUser = (UserEnity) getIntent().getSerializableExtra("user");
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("USERS").child(receivedUser.getEmail());
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Người dùng có tồn tại trong cơ sở dữ liệu

                } else {
                    // Người dùng không tồn tại trong cơ sở dữ liệu
                    Log.d("Firebase", "User not found");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
                Log.w("Firebase", "getUser:onCancelled", databaseError.toException());
            }
        });
    }

}