package com.example.myapplication;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class KhoanChiActivity extends AppCompatActivity {

    private Button btnkhoanthu,btnBackHome,btnSubmit;
    UserSingleton userSingleton = UserSingleton.getInstance();
    private TextView addTags ,txtMoney,txtNote,txtHead;
    private static TextView textDay,addTag;
    private Spinner spinnerTags;
    private ImageButton backHome;
    private ArrayList<History> listHistory = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_khoanchi);
        //btnkhoanthu = (Button) findViewById(R.id.khoanthu) ;
        btnBackHome = (Button) findViewById(R.id.btnBack) ;
        btnSubmit = (Button) findViewById(R.id.btnSubmit) ;
        backHome=(ImageButton) findViewById(R.id.backHome);
        addTags = (TextView) findViewById(R.id.addTags) ;
        textDay = (TextView) findViewById(R.id.textday);
        txtMoney = (TextView) findViewById(R.id.txtMoney);
        txtNote = (TextView) findViewById(R.id.txtnote);
        txtHead = (TextView) findViewById(R.id.textHead);

        UserEnity receivedUser = (UserEnity) getIntent().getSerializableExtra("user");
        ItemCate itemcate = (ItemCate) getIntent().getSerializableExtra("cate");
        //boolean update = (Boolean) getIntent().getSerializableExtra("update");
        String phone = receivedUser.getPhone();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0, nên cần cộng thêm 1
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        textDay.setText(day+"/"+month+"/"+year);

        if(itemcate!=null) {
            addTags.setText(itemcate.getTitle());
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Khoan");

        History itemHistory = (History) getIntent().getSerializableExtra("itemHistory");
        if(itemHistory!=null) {
            //addTags.setText(itemHistory.getTitleStatus().toString());
            textDay.setText(itemHistory.getDate().toString());
            txtMoney.setText(itemHistory.getCount()*-1+"");
            txtNote.setText(itemHistory.getNote().toString());
            btnSubmit.setText("Cập nhật");
            txtHead.setText("Sửa");
        }
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                try {

                    int money = Integer.parseInt(txtMoney.getText().toString());
                    String note = txtNote.getText().toString();
                    Date date = dateFormat.parse(textDay.getText().toString());
                    String tag = addTags.getText().toString();

                    SimpleDateFormat inputFormat = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                    inputFormat.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
                    //Date d = inputFormat.parse(date.toString());



                    DatabaseReference nodeChaRef = myRef.child(phone);
                    if(itemcate.isStatus()==false ){
                        money = money*-1;
                    }
                    if(itemHistory!=null) {

                        History newhis = new History(money, note, outputFormat.format(date) , itemcate.isStatus(), tag, phone,itemHistory.getIdHistory());
                        int finalMoney = money;
                        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("USERS").child(phone);
                        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    // Người dùng có tồn tại trong cơ sở dữ liệu
                                    UserEnity foundUser = dataSnapshot.getValue(UserEnity.class);
                                    //foundUser.setTotal((int) (foundUser.getTotal()+ finalMoney));
                                   // usersRef.setValue(foundUser);
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
                        nodeChaRef.child(itemHistory.getIdHistory()).setValue(newhis);
                    }
                    else {
                        String newKey = nodeChaRef.push().getKey();
                        History his = new History(money, note, outputFormat.format(date) , itemcate.isStatus(), tag, phone,newKey);
                        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("USERS").child(phone);
                        int finalMoney = money;
                        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    // Người dùng có tồn tại trong cơ sở dữ liệu
                                    UserEnity foundUser = dataSnapshot.getValue(UserEnity.class);
                                    //foundUser.setTotal((int) (foundUser.getTotal()+ finalMoney));
                                    //usersRef.setValue(foundUser);
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
                        nodeChaRef.child(newKey).setValue(his);
                    }
                    // Chuyển đến hoạt động mới (Activity)
                    Intent intent = new Intent(KhoanChiActivity.this, HistoryActivity.class);
                    intent.putExtra("user", receivedUser);
                    startActivity(intent);
                } catch (ParseException | NumberFormatException e) {
                    Toast.makeText(KhoanChiActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

//        spinnerTags = (Spinner) findViewById(R.id.spiner) ;
//        btnkhoanthu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent  = new Intent(KhoanChiActivity.this,KhoanThuActivity.class);
//                intent.putExtra("user",receivedUser);
//                startActivity(intent);
//            }
//        });
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(KhoanChiActivity.this,PageHomeActivity.class);
                intent.putExtra("user",receivedUser);
                startActivity(intent);
            }
        });
//        spinnerTags.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent  = new Intent(KhoanChiActivity.this,ChiActivity.class);
//                startActivity(intent);
//            }
//        });
        addTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(KhoanChiActivity.this,ChiActivity.class);
                intent.putExtra("user",receivedUser);
                if(itemHistory!=null) {
                    userSingleton.setHistory(itemHistory);
                }
                startActivity(intent);
            }
        });
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KhoanChiActivity.this,PageHomeActivity.class);
                intent.putExtra("user",receivedUser);
                startActivity(intent);
            }
        });

    }
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            textDay.setText(day+"/"+month+"/"+year);
        }
    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
