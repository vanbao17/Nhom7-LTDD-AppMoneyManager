package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ThongKeAdapter extends ArrayAdapter<ThongKeEntity> {
    public ThongKeAdapter(@NonNull Context context, ArrayList<ThongKeEntity> items) {
        super(context,  0,items);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Lấy dữ liệu cho item tại vị trí position
        ThongKeEntity item = getItem(position);

        // Kiểm tra xem convertView có được sử dụng lại không, nếu không thì inflate lại
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.itemthongke, parent, false);
        }

        // Ánh xạ các phần tử trong layout item_layout.xml
        TextView textViewIcon = convertView.findViewById(R.id.imageThu);
        TextView textViewTitle = convertView.findViewById(R.id.titleStatus);
        TextView textViewPercent = convertView.findViewById(R.id.percent);

        // Gán giá trị từ đối tượng ItemCate vào các phần tử của layout
        if (item != null) {
            textViewTitle.setText(item.getTitle());
            textViewIcon.setText(item.getImage());
            textViewPercent.setText(item.getPercent());
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến hoạt động mới và truyền dữ liệu

            }
        });
        return convertView;
    }
}
