package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountActivity extends AppCompatActivity {
    private TextView nameUser;
    private ImageButton home;
    private Button aboutus,deleteAcc,btnchangepass;
    UserSingleton userSingleton = UserSingleton.getInstance();
    UserEnity user = userSingleton.getUser();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        home = (ImageButton) findViewById(R.id.home);
        aboutus = (Button) findViewById(R.id.logout);
        deleteAcc = (Button) findViewById(R.id.deleteacc);
        nameUser = (TextView) findViewById(R.id.nameUser);
        btnchangepass = (Button) findViewById(R.id.btnchangepass);
        nameUser.setText(user.getUsername());
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, PageHomeActivity.class);
                startActivity(intent);
            }
        });
        btnchangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                catch (Exception e) {
                }
            }
        });
        deleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    showWarningDialog();
                    Intent intent = new Intent(AccountActivity.this, SigninActivity.class);
                    startActivity(intent);
                }
                catch (Exception e) {

                }
            }
        });
    }
    private void showWarningDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa tài khoản");
        builder.setMessage("Tất cả dữ liệu sẽ mất nếu bạn xóa tài khoản, bạn có chắc chắn là muốn xóa tài khoản . ?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("USERS");
                DatabaseReference khoansRef = FirebaseDatabase.getInstance().getReference("Khoan");
                DatabaseReference targetsRef = FirebaseDatabase.getInstance().getReference("Targets");
                usersRef.child(user.getPhone()).removeValue();
                khoansRef.child(user.getPhone()).removeValue();
                targetsRef.child(user.getPhone()).removeValue();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
