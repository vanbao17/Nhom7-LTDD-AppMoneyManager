package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ThuActivity extends AppCompatActivity {
    private ImageButton back;
    private TextView btnkhoanthu,btnkhoanchi;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu);
        //back = (ImageButton) findViewById(R.id.back);
        btnkhoanthu = (TextView) findViewById(R.id.khoanthu);
        btnkhoanchi = (TextView) findViewById(R.id.khoanchi);

        ArrayList<ItemCate> listCate = new ArrayList<>();
        CateAdapter adapter = new CateAdapter(this, listCate);
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("CatetoriesThu");

        // Lắng nghe sự kiện khi dữ liệu thay đổi
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Lấy dữ liệu từ DataSnapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ItemCate data = snapshot.getValue(ItemCate.class);
                    listCate.add(data);
                }
                ListView listViewThu = (ListView) findViewById(R.id.listviewThu);
                listViewThu.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });


//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ThuActivity.this, KhoanThuActivity.class);
//                startActivity(intent);
//            }
//        });
        btnkhoanchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThuActivity.this, ChiActivity.class);
                startActivity(intent);
            }
        });
    }
}
