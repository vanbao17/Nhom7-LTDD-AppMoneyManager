package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SigninActivity extends AppCompatActivity {
    public Button btnLogin,btnSignUp;
    private EditText editEmail,editPassword,editName,editPhone;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainsignin);
        editEmail = (EditText) findViewById(R.id.txtEmail);
        editName = (EditText) findViewById(R.id.txtUser);
        editPhone= (EditText) findViewById(R.id.txtPhone);
        btnSignUp = (Button) findViewById(R.id.btnSignup);
        editPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        progressDialog = new ProgressDialog(this);
        initListener();
        ArrayList<ItemCate> listcate = new ArrayList<>();

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//
//        DatabaseReference myRef = database.getReference("CatetoriesThu");
//        myRef.setValue(listcate);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(SigninActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }
    private void initListener() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignUp();
            }
        });
    }
    private void onClickSignUp() {
        String strEmail = editEmail.getText().toString().trim();
        String strPassword = editPassword.getText().toString().trim();
        String strName = editName.getText().toString().trim();
        String strPhone = editPhone.getText().toString().trim();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("USERS");
        String Tag="";
        progressDialog.show();


        // Khởi tạo Firestore



        auth.createUserWithEmailAndPassword(strEmail, strPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(SigninActivity.this,LoginActivity.class);
                            startActivity(intent);
                            String userId = usersRef.push().getKey();
                            UserEnity newUser = new UserEnity(userId, strName, strEmail,strPassword,strPhone,0);
                            ArrayList<UserEnity> user = new ArrayList<>();
                            user.add(newUser);
                            usersRef.child(strPhone).setValue(newUser);
                            finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(Tag, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SigninActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
