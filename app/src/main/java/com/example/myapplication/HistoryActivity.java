package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private ImageButton home;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        home = (ImageButton) findViewById(R.id.home);
        ArrayList<History> listHis = new ArrayList<>();
        HistoryAdapter adapter = new HistoryAdapter(this, listHis);
        UserEnity receivedUser = (UserEnity) getIntent().getSerializableExtra("user");
        String phone = receivedUser.getPhone();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Khoan/"+phone);

        // Lắng nghe sự kiện khi dữ liệu thay đổi
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Lấy dữ liệu từ DataSnapshot
                for (DataSnapshot snapshot : dataSnapshot .getChildren()) {
                    History data = snapshot.getValue(History.class);
                        listHis.add(data);
                }
                // Tạo Adapter và thiết lập cho ListView
                ListView listView = (ListView) findViewById(R.id.listviewHistory);
                listView.setAdapter(adapter);
                // Bây giờ, yourArray chứa mảng dữ liệu từ Firebase
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, PageHomeActivity.class);
                intent.putExtra("user",receivedUser);
                startActivity(intent);
            }
        });
    }
}
