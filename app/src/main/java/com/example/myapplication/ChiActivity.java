package com.example.myapplication;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;

public class ChiActivity extends AppCompatActivity {
    private ImageButton back;
    private TextView btnkhoanthu,btnkhoanchi;
    String Tag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi);
        //back = (ImageButton) findViewById(R.id.back);
        btnkhoanthu = (TextView) findViewById(R.id.khoanthu);
        btnkhoanchi = (TextView) findViewById(R.id.khoanchi);
        ArrayList<ItemCate> listCate = new ArrayList<>();
        CateAdapter adapter = new CateAdapter(this, listCate);
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("CatetoriesChi");

        // Lắng nghe sự kiện khi dữ liệu thay đổi
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Lấy dữ liệu từ DataSnapshot
                for (DataSnapshot snapshot : dataSnapshot .getChildren()) {
                    ItemCate data = snapshot.getValue(ItemCate.class);
                    listCate.add(data);
                }
                // Tạo Adapter và thiết lập cho ListView
                ListView listView = (ListView) findViewById(R.id.listviewChi);
                listView.setAdapter(adapter);
                // Bây giờ, yourArray chứa mảng dữ liệu từ Firebase
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });



//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ChiActivity.this, KhoanChiActivity.class);
//                startActivity(intent);
//            }
//        });
        btnkhoanthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiActivity.this, ThuActivity.class);
                startActivity(intent);
            }
        });
    }
}
