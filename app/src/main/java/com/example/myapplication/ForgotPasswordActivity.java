package com.example.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText txtOldPass, txtPasword, txtPaswordConfirm;
    private Button btnChange;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    UserSingleton userSingleton = UserSingleton.getInstance();
    UserEnity user = userSingleton.getUser();
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_layout);
        txtOldPass = findViewById(R.id.txtOldPass);
        txtPasword = findViewById(R.id.txtPasword);
        txtPaswordConfirm = findViewById(R.id.txtPaswordConfirm);
        btnChange = findViewById(R.id.btnChange);
        progressDialog = new ProgressDialog(this);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("USERS").child(user.getPhone());
                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            UserEnity founderUser = snapshot.getValue(UserEnity.class);
                            if(txtOldPass.getText().toString().equals(founderUser.getPassword())) {
                                if (txtPasword.getText().toString().equals(txtPaswordConfirm.getText().toString())) {
                                    founderUser.setPassword(txtPasword.getText().toString());
                                    usersRef.setValue(founderUser);
                                    showWarningDialog("Đổi mật khẩu thành công",true);
                                } else {
                                    showWarningDialog("Mật khẩu không khớp",false);
                                }
                            }
                            else {
                                showWarningDialog("Mật khẩu cũ chưa đúng, xin mời nhập lại",false);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void showWarningDialog(String message,boolean status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage(message);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(status==false ){
                    dialog.dismiss();
                }
                else {
                    Intent intent = new Intent(ForgotPasswordActivity.this,AccountActivity.class) ;
                    startActivity(intent);
                }

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
