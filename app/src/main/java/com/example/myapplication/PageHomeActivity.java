package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PageHomeActivity extends AppCompatActivity {
    private static PageHomeActivity instance;
    private ImageView imageHistory,imageAdd,imageTarget,imagebiendong,imageAccount,imagethongke ;
    private TextView txtBudget,txtUsername;
    private String phoneUser;
    private UserEnity  receivedUser;
    UserSingleton userSingleton = UserSingleton.getInstance();
    private int count = 0;
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
        receivedUser = (UserEnity) getIntent().getSerializableExtra("user");
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("USERS").child(receivedUser.getPhone());
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Khoan/"+receivedUser.getPhone());

        // Lắng nghe sự kiện khi dữ liệu thay đổi
        databaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Lấy dữ liệu từ DataSnapshot
                for (DataSnapshot snapshot : dataSnapshot .getChildren()) {
                    History data = snapshot.getValue(History.class);
                    count = count + data.getCount();
                }



            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Người dùng có tồn tại trong cơ sở dữ liệu
                    UserEnity foundUser = dataSnapshot.getValue(UserEnity.class);
                    //int sum = (int) (foundUser.getTotal()+count);
                    txtBudget.setText(foundUser.getTotal()+"");
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

        if (receivedUser != null) {
            txtUsername.setText(receivedUser.getUsername().toString());

            userSingleton.setUser(receivedUser);

        }
        imageHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(PageHomeActivity.this,ManagerActivity.class);
                intent.putExtra("user",receivedUser);
                startActivity(intent);
            }
        });

        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(PageHomeActivity.this,KhoanChiActivity.class);
                intent.putExtra("user",receivedUser);
                startActivity(intent);
            }
        });
        imageTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Targets").child(receivedUser.getPhone());
                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Người dùng có tồn tại trong cơ sở dữ liệu
                            Intent intent  = new Intent(PageHomeActivity.this,TargetAboutActivity.class);
                            intent.putExtra("user",receivedUser);
                            startActivity(intent);
                        } else {
                            // Người dùng không tồn tại trong cơ sở dữ liệu
                            Intent intent  = new Intent(PageHomeActivity.this,TargetInputActivity.class);
                            intent.putExtra("user",receivedUser);
                            startActivity(intent);
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


        txtBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
        imagebiendong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(PageHomeActivity.this,HistoryActivity.class);
                intent.putExtra("user",receivedUser);
                startActivity(intent);
            }
        });
        imageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(PageHomeActivity.this,AccountActivity.class);
                intent.putExtra("user",receivedUser);
                startActivity(intent);
            }
        });
        imagethongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(PageHomeActivity.this,ThongkeActivity.class);
                intent.putExtra("user",receivedUser);
                startActivity(intent);
            }
        });
    }
    private void reloadLayout() {
        Intent intent = getIntent();
        finish(); // Kết thúc activity hiện tại
        startActivity(intent);
        //setContentView(R.layout.main_page); // Layout bạn muốn load lại
    }

    public static synchronized PageHomeActivity getInstance() {
        if (instance == null) {
            instance = new PageHomeActivity();
        }
        return instance;
    }
    // Getter và setter cho biến toàn cục
    public UserEnity getGlobalString() {
        return receivedUser;
    }

    public void setGlobalString(UserEnity receivedUser) {
        this.receivedUser = receivedUser;
    }
    private void showInputDialog() {
        // Tạo một AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Sử dụng LayoutInflater để inflate layout cho dialog
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_input, null);
        builder.setView(dialogView);

        // Thiết lập tiêu đề cho dialog
        builder.setTitle("Nhập số tiền bạn đang có :");

        // Thiết lập nút tích cực (positive button)
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý khi người dùng nhấn nút OK
                EditText editText = dialogView.findViewById(R.id.editTextInput);
                int countUpdate = Integer.parseInt(editText.getText().toString());
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("USERS").child(receivedUser.getPhone());
                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Người dùng có tồn tại trong cơ sở dữ liệu
                            UserEnity foundUser = dataSnapshot.getValue(UserEnity.class);
                            foundUser.setTotal( (countUpdate));
                            usersRef.setValue(foundUser);
                            reloadLayout();
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

                // TODO: Xử lý dữ liệu nhập vào
            }
        });

        // Thiết lập nút hủy (negative button)
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý khi người dùng nhấn nút Hủy
                dialog.cancel();
            }
        });

        // Hiển thị dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    };
   }

