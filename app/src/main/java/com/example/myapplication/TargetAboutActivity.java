package com.example.myapplication;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TargetAboutActivity extends AppCompatActivity {
    UserSingleton userSingleton = UserSingleton.getInstance();
    UserEnity currentUser = userSingleton.getUser();
    private TextView txtTitle,txtProcess,txtNote,textprocess;
    private ImageView btnHome;
    private SeekBar processLine;
    private Button btnUpdate,btnDelete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.target_about);
        txtTitle = (TextView) findViewById(R.id.title);
        txtProcess = (TextView) findViewById(R.id.process);
        txtNote = (TextView) findViewById(R.id.note);
        processLine = (SeekBar) findViewById(R.id.seekBar) ;
        textprocess = (TextView) findViewById(R.id.textprocess);
        btnHome= (ImageView) findViewById(R.id.btnHome);
        btnUpdate = (Button) findViewById(R.id.btnupdate);
        btnDelete = (Button) findViewById(R.id.btndelete);
        DatabaseReference targetsRef = FirebaseDatabase.getInstance().getReference("Targets").child(currentUser.getPhone());
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TargetAboutActivity.this, PageHomeActivity.class);
                intent.putExtra("user",currentUser);
                startActivity(intent);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("USERS").child(currentUser.getPhone());
                            TargetEntity tg = dataSnapshot.getValue(TargetEntity.class);
                            Intent intent = new Intent(TargetAboutActivity.this, TargetInputActivity.class);
                            intent.putExtra("targetUpdate",tg);
                            intent.putExtra("user",currentUser);
                            startActivity(intent);
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
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    targetsRef.removeValue();
                }
                catch (Exception e ) {

                }
                Intent intent = new Intent(TargetAboutActivity.this, TargetActivity.class);
                //intent.putExtra("targetUpdate",)
                intent.putExtra("user",currentUser);
                startActivity(intent);
            }
        });

        targetsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("USERS").child(currentUser.getPhone());
                    TargetEntity tg = dataSnapshot.getValue(TargetEntity.class);
                    usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                            if (dataSnapshot1.exists()) {
                                // Người dùng có tồn tại trong cơ sở dữ liệu
                                UserEnity us = dataSnapshot1.getValue(UserEnity.class);
                                double roundedPercent = ((double) us.getTotal() / tg.getMonney()) * 100;
                                int processPercent = (int) Math.round(roundedPercent);
                                txtTitle.setText(tg.getTitle());
                                txtProcess.setText(us.getTotal()+"/"+tg.getMonney());
                                txtNote.setText("Bạn đã đạt được "+processPercent+"% với mục tiêu của bạn");
                                textprocess.setText(processPercent+"%");
                                processLine.setProgress(Integer.parseInt(String.valueOf(processPercent)));
                                processLine.setEnabled(false);
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
