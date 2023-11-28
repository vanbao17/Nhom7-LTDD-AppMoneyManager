package com.example.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin, btnSignin,btnForgotPass;
    private EditText txtEmail, txtPassword;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maindangnhap);
        btnLogin = findViewById(R.id.btnDN);
        btnSignin = findViewById(R.id.btnsignup);
        txtEmail = findViewById(R.id.txtUser);
        txtPassword = findViewById(R.id.txtPassword);
        progressDialog = new ProgressDialog(this);

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, PageHomeActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = txtEmail.getText().toString().trim();
                String strPassword = txtPassword.getText().toString().trim();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String Tag = "";
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("USERS").child(strEmail);
                progressDialog.show();
                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Người dùng có tồn tại trong cơ sở dữ liệu
                            UserEnity foundUser = dataSnapshot.getValue(UserEnity.class);
                            if(foundUser.getPassword().equals(strPassword)) {
                                Intent intent = new Intent(LoginActivity.this,PageHomeActivity.class);
                                progressDialog.dismiss();
                                intent.putExtra("user", foundUser);
                                startActivity(intent);
                            }
                            else {
                                showLoginErrorDialog();
                                progressDialog.dismiss();
                            }
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

    }
    private void showLoginErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đăng nhập thất bại");
        builder.setMessage("Sai mật khẩu hoặc số điện thoại. Vui lòng thử lại.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
