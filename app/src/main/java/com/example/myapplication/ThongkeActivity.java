package com.example.myapplication;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.PixelCopy;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ThongkeActivity extends AppCompatActivity {
    private ImageButton home;
    UserSingleton userSingleton = UserSingleton.getInstance();
    UserEnity user = userSingleton.getUser();
    private int thanghientai = 0;
    private int thangtruoc = 0;
    private int percenthientai = 0;
    private int percenttruoc = 0;

    private TextView colhientai,colthangtruoc,sumChi;
    ArrayList<History> listhistory = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke);
        home = (ImageButton) findViewById(R.id.home);
        colhientai = (TextView) findViewById(R.id.colthangnay) ;
        colthangtruoc = (TextView) findViewById(R.id.colthangtruoc) ;
        sumChi = (TextView) findViewById(R.id.sumChi);
        Calendar calendar = Calendar.getInstance();
        int monthhientai = calendar.get(Calendar.MONTH) + 1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        DatabaseReference khoanRef = FirebaseDatabase.getInstance().getReference("Khoan").child(user.getPhone());
        DatabaseReference chiRef = FirebaseDatabase.getInstance().getReference("CatetoriesChi");
        khoanRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot: dataSnapshot.getChildren() ) {
                        History data = snapshot.getValue(History.class);
                        try {
                            Date date = dateFormat.parse(data.getDate());
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);
                            int month = calendar.get(Calendar.MONTH) + 1;

                            if(month==monthhientai && data.getStatus()==false) {
                                thanghientai=thanghientai+data.getCount();
                                listhistory.add(data);
                            }
                            if(month==(monthhientai-1) && data.getStatus()==false) {
                                thangtruoc=thangtruoc+data.getCount();
                            }
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    sumChi.setText(thanghientai*-1+"");
                    if(thanghientai*-1>thangtruoc*-1) {
                        double roundedPercent = ((double) thangtruoc/thanghientai) * 120;
                        percenttruoc = (int) Math.round(roundedPercent);
                        colhientai.getLayoutParams().height=120;
                        int heightPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, percenttruoc, getResources().getDisplayMetrics());
                        colthangtruoc.getLayoutParams().height =heightPixels;
                    }
                    else {
                        double roundedPercent = ((double) thanghientai/thangtruoc) * 120;
                        percenthientai = (int) Math.round(roundedPercent);
                        int heightPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, percenthientai, getResources().getDisplayMetrics());
                        colthangtruoc.getLayoutParams().height=120;
                        colhientai.getLayoutParams().height =heightPixels;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        //Toast.makeText(getApplicationContext(),listhistory.size() +"", Toast.LENGTH_SHORT).show();

        ArrayList<ThongKeEntity> thongKeEntities = new ArrayList<>();
        ThongKeAdapter adapter = new ThongKeAdapter(this, thongKeEntities);
        DatabaseReference khoanchiRef = FirebaseDatabase.getInstance().getReference("Khoan").child(user.getPhone());
        chiRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ThuEntity thu = snapshot.getValue(ThuEntity.class);
                        int dem=0;
                        int count =0;
                        ThongKeEntity thongKe = new ThongKeEntity();
                        khoanchiRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                for(DataSnapshot snapshot1 : dataSnapshot1.getChildren()) {
                                    History his1 = snapshot1.getValue(History.class);
                                    int month=0;
                                    try {
                                        Date date = dateFormat.parse(his1.getDate());
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTime(date);
                                        month = calendar.get(Calendar.MONTH) + 1;
                                    } catch (ParseException e) {
                                        throw new RuntimeException(e);
                                    }
                                    int dem=0;
                                    int count=0;
                                    if(thu.getTitle().equals(his1.getTitleStatus())&&month==monthhientai) {
                                        dem++;
                                        thongKe.setTitle(his1.getTitleStatus());
                                        thongKe.setImage(thu.getImage());
                                        thongKe.setDem(dem);
                                        int per = (int) Math.round(((double) his1.getCount() / thanghientai) * 100);
                                        Toast.makeText(getApplicationContext(),per+"", Toast.LENGTH_SHORT).show();
                                        thongKe.setPercent(per+"%");
                                        thongKeEntities.add(thongKe);
                                    }

                                }
//                                for(int i=0;i<thongKeEntities.size();i++) {
//                                    Toast.makeText(getApplicationContext(),thongKeEntities.get(i).getTitle() +"/"+thongKeEntities.get(i).getDem(), Toast.LENGTH_SHORT).show();
//                                }
                                //Toast.makeText(getApplicationContext(),thongKeEntities.size() +"", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }


                    ListView listView = (ListView) findViewById(R.id.lviewthongke);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongkeActivity.this,PageHomeActivity.class);
                intent.putExtra("user",user);
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
}
