package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.List;

public class CateAdapter extends ArrayAdapter<ItemCate> {

    public CateAdapter(Context context, List<ItemCate> items) {
        super(context, 0, items);
    }
    UserSingleton userSingleton = UserSingleton.getInstance();
    UserEnity currentUser = userSingleton.getUser();
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Lấy dữ liệu cho item tại vị trí position
        ItemCate item = getItem(position);

        // Kiểm tra xem convertView có được sử dụng lại không, nếu không thì inflate lại
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.itemcate, parent, false);
        }

        // Ánh xạ các phần tử trong layout item_layout.xml
        TextView textViewIcon = convertView.findViewById(R.id.textViewIcon);
        TextView textViewTitle = convertView.findViewById(R.id.textViewTitle);

        // Gán giá trị từ đối tượng ItemCate vào các phần tử của layout
        if (item != null) {
            textViewTitle.setText(item.getTitle());
            textViewIcon.setText(item.getImage());
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến hoạt động mới và truyền dữ liệu
                Intent intent = new Intent(getContext(), KhoanChiActivity.class);
                intent.putExtra("cate",  item);
                intent.putExtra("user", currentUser);
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
