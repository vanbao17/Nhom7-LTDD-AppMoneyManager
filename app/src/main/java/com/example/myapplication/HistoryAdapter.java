package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<History> {
    public HistoryAdapter(@NonNull Context context, ArrayList<History> items) {
        super(context,  0,items);
    }
    UserSingleton userSingleton = UserSingleton.getInstance();
    UserEnity currentUser = userSingleton.getUser();
    public View getView(int position, View convertView, ViewGroup parent) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Khoan").child(currentUser.getPhone());
        // Lấy dữ liệu cho item tại vị trí position
        History item = getItem(position);

        // Kiểm tra xem convertView có được sử dụng lại không, nếu không thì inflate lại
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.itemhistory, parent, false);
        }

        // Ánh xạ các phần tử trong layout item_layout.xml
        TextView textViewIcon = convertView.findViewById(R.id.textViewMoney);
        TextView textViewTitle = convertView.findViewById(R.id.textViewTitle);
        TextView textViewDate= convertView.findViewById(R.id.textViewDate);
        TextView textViewNote = convertView.findViewById(R.id.textViewNote);
        TextView textViewDelete = convertView.findViewById(R.id.delete);
        textViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersRef.child(item.getIdHistory()).removeValue();
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("user", currentUser);
                getContext().startActivity(intent);
            }
        });

        // Gán giá trị từ đối tượng ItemCate vào các phần tử của layout
        if (item != null) {
            textViewTitle.setText(item.getTitleStatus());
            if(item.getStatus()==true) {
                textViewIcon.setText("+"+item.getCount() + "VND");
            }
            else {
                textViewIcon.setText(" "+item.getCount()+ "VND");
            }
            textViewDate.setText(item.getDate()+"");
            textViewNote.setText(item.getNote()+"");
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến hoạt động mới và truyền dữ liệu
                Intent intent = new Intent(getContext(), KhoanChiActivity.class);
                intent.putExtra("update", true);
                intent.putExtra("itemHistory", item);
                userSingleton.setHistory(item);
                intent.putExtra("user", currentUser);
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
