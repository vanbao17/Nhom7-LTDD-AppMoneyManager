package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TargetInputActivity extends AppCompatActivity {
    private Button btnAdd;
    UserSingleton userSingleton = UserSingleton.getInstance();
    UserEnity currentUser = userSingleton.getUser();
    private EditText txtTitle,txtMonney;
    private static TextView txtDateBegin;
    private static TextView txtDateFinish;
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0, nên cần cộng thêm 1
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.target_input);
        btnAdd = (Button) findViewById(R.id.btn_themmoi);
        txtTitle = (EditText) findViewById(R.id.title);
        txtMonney = (EditText) findViewById(R.id.monney);
        txtDateBegin = (TextView) findViewById(R.id.datebegin);
        txtDateFinish = (TextView) findViewById(R.id.datefinish);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Targets");
        txtDateBegin.setText(day+"-"+month+"-"+year);
        txtDateFinish.setText(day+"-"+month+"-"+year);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String title = txtTitle.getText().toString();
                    int monney = Integer.parseInt(txtMonney.getText().toString());

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat outputFormat;
                    Date datebegin = dateFormat.parse(txtDateBegin.getText().toString());
                    Date datefinish = dateFormat.parse(txtDateFinish.getText().toString());

                    SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'Z yyyy", Locale.getDefault());
                    outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    inputFormat.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
                    Date d = inputFormat.parse(datebegin.toString());
                    Date d1 = inputFormat.parse(datefinish.toString());


                    TargetEntity targetEntity = new TargetEntity(title, monney, outputFormat.format(d),outputFormat.format(d1));
                    DatabaseReference nodeChaRef = myRef.child(currentUser.getPhone());
                    nodeChaRef.setValue(targetEntity);



                    Intent intent = new Intent(TargetInputActivity.this,TargetAboutActivity.class);
                    intent.putExtra("user",currentUser);
                    startActivity(intent);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

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
            txtDateBegin.setText(day+"-"+month+"-"+year);
        }
    }
    public static class DatePickerFragment1 extends DialogFragment
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
            txtDateFinish.setText(day+"-"+month+"-"+year);
        }
    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new TargetInputActivity.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void showDatePickerDialog1(View v) {
        DialogFragment newFragment = new TargetInputActivity.DatePickerFragment1();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
