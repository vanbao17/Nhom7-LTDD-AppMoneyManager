package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class KhoanThuActivity extends AppCompatActivity {
    private Button btnKhoanchi,btnBack,btnSubmit;
    private TextView addTags,txtMoney,txtNote;
    private static TextView textDay;

    private ImageButton backHome;
    private ArrayList<History> listHistory = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_khoanthu);
        btnKhoanchi = (Button) findViewById(R.id.khoanchi);
        btnBack = (Button) findViewById(R.id.btnBack) ;
        btnSubmit= (Button) findViewById(R.id.btnSubmit) ;
        addTags = (TextView) findViewById(R.id.addTags) ;
        backHome = (ImageButton) findViewById(R.id.backHome);
        textDay = (TextView) findViewById(R.id.textday);
        txtMoney = (TextView) findViewById(R.id.txtMoney);
        txtNote = (TextView) findViewById(R.id.txtnote);
        addTags = (TextView) findViewById(R.id.addTags);
        UserEnity receivedUser = (UserEnity) getIntent().getSerializableExtra("user");
        String phone = receivedUser.getPhone();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0, nên cần cộng thêm 1
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        textDay.setText(day+"-"+month+"-"+year);


        Intent intent = getIntent();
        String selectedItem = intent.getStringExtra("cate");
        if(selectedItem!=null) {
            addTags.setText(selectedItem);
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Khoan");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                try {
                    int money = Integer.parseInt(txtMoney.getText().toString());
                    String note = txtNote.getText().toString();
                    Date date = dateFormat.parse(textDay.getText().toString());
                    String tag = addTags.getText().toString();

                    DatabaseReference nodeChaRef = myRef.child(phone);

                    String newKey = nodeChaRef.push().getKey();
                    listHistory.add(new History(money, note, date.toString() , true, tag, phone));
                    History his = new History(money, note, date.toString() , true, tag, phone);

                    nodeChaRef.child(newKey).setValue(his);
                    // Chuyển đến hoạt động mới (Activity)
                    Intent intent = new Intent(KhoanThuActivity.this, PageHomeActivity.class);
                    intent.putExtra("user", receivedUser);
                    startActivity(intent);
                } catch (ParseException | NumberFormatException e) {
                    Toast.makeText(KhoanThuActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        btnKhoanchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KhoanThuActivity.this, KhoanChiActivity.class);
                intent.putExtra("user",receivedUser);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(KhoanThuActivity.this,PageHomeActivity.class);
                intent.putExtra("user",receivedUser);
                startActivity(intent);
            }
        });
        addTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(KhoanThuActivity.this,ThuActivity.class);
                startActivity(intent);
            }
        });
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KhoanThuActivity.this, PageHomeActivity.class);
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
            textDay.setText(day+"-"+month+"-"+year);
        }
    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new KhoanThuActivity.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
