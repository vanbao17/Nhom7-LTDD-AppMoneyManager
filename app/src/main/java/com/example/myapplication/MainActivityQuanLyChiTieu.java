package com.example.myapplication;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivityQuanLyChiTieu extends AppCompatActivity {
    private Button showEditTextButton;
    private Button showLayoutCHiTieu;
    private Button showEditTextButtonTietKiem;
    private Button showLayoutTietKiem;
    private RelativeLayout myRelativeLayout1;
    private RelativeLayout myRelativeLayout;
    private RelativeLayout RelativeLayoutTietKiem;
    private RelativeLayout RelativeLayoutTietKiemHienThi;
    private DatabaseReference databaseReference;
    private TextView MucChiTieu;
    private TextView ChiTieuHomNay;
    private TextView textView46;
    private ImageView imageView8;
    private ImageView imageView9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        // Ánh xạ các thành phần từ layout
        showEditTextButton = findViewById(R.id.showEditTextButton);
        showLayoutCHiTieu = findViewById(R.id.showLayoutCHiTieu);
        myRelativeLayout  = findViewById(R.id.LayoutChitieu);
        myRelativeLayout1 = findViewById(R.id.LayoutChitieuHienthi);

        showEditTextButtonTietKiem = findViewById(R.id.btn_chitieu2);
        showLayoutTietKiem = findViewById(R.id.showLayoutTietKiem);
        RelativeLayoutTietKiem = findViewById(R.id.LayoutTietKiem);
        RelativeLayoutTietKiemHienThi = findViewById(R.id.HienThiTietKiem);

        MucChiTieu = findViewById(R.id.MucChiTieu);
        ChiTieuHomNay = findViewById(R.id.ChiTieuHomNay);
        textView46 = findViewById(R.id.textView46);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);
        // Khởi tạo Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Quản lý chi tiêu");

        // Đặt sự kiện click cho nút
        showEditTextButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Khi nút được nhấn, hiển thị EditText
                myRelativeLayout.setVisibility(View.VISIBLE);
                myRelativeLayout1.setVisibility(View.GONE);
            }
        });
        // Đặt sự kiện click cho nút
        showLayoutCHiTieu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Khi nút được nhấn, hiển thị EditText
                myRelativeLayout.setVisibility(View.GONE);
                myRelativeLayout1.setVisibility(View.VISIBLE);
                // Lấy dữ liệu từ EditText
                String inputData = ((EditText) findViewById(R.id.inputEditText)).getText().toString();

                // Kiểm tra nếu dữ liệu không rỗng
                if (!inputData.isEmpty()) {
                    String dataWithUnit = inputData + "/ngày";
                    // Thêm dữ liệu lên Firebase
                    databaseReference.child("Mức chi tiêu đặt ra").setValue(dataWithUnit);
                }
                loadChiTieuData();
            }
        });
        // Đặt sự kiện click cho nút hien thi nhap tien tiet kiem
        showEditTextButtonTietKiem.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Khi nút được nhấn, hiển thị EditText
                RelativeLayoutTietKiem.setVisibility(View.VISIBLE);
                RelativeLayoutTietKiemHienThi.setVisibility(View.GONE);
                //myRelativeLayout1.setVisibility(View.GONE);
            }
        });
        // Đặt sự kiện click cho nút hien thi trang tien tiet kiem
        showLayoutTietKiem.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Khi nút được nhấn, hiển thị EditText
                RelativeLayoutTietKiem.setVisibility(View.GONE);
                RelativeLayoutTietKiemHienThi.setVisibility(View.VISIBLE);
                // Lấy dữ liệu từ EditText

                String inputData = ((EditText) findViewById(R.id.inputEditTextTietKiem)).getText().toString();

                // Kiểm tra nếu dữ liệu không rỗng
                if (!inputData.isEmpty()) {
                    String dataWithUnit = "+" + inputData;
                    // Thêm dữ liệu lên Firebase
                    databaseReference.child("Khoản tiết kiệm mới").setValue(dataWithUnit);
                }
                loadTietKiemData();
            }
        });
        // Tạo danh sách các EditText bạn muốn thêm gạch dưới chân
        TextView textView1 = findViewById(R.id.textView15);
        // Tạo mảng hoặc danh sách các EditText
        TextView[] TextViews = {textView1};

        for (TextView textView : TextViews) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }

        EditText editText = findViewById(R.id.inputEditText);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // Khi EditText không còn focus, kiểm tra nếu nó trống rỗng
                    if (editText.getText().toString().isEmpty()) {
                        // Nếu trống rỗng, hiển thị lại hint
                        editText.setHint("Nhập mức chi tiêu mới...");
                    }
                } else {
                    // Khi EditText có focus, làm mất hint
                    editText.setHint("");
                }
            }
        });
        TextView textView15 = findViewById(R.id.textView15);
        textView15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayTietKiemHistory();
            }
        });
    }
    private void loadChiTieuData() {
        databaseReference.child("Mức chi tiêu đặt ra").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String chiTieu = dataSnapshot.getValue(String.class);
                    MucChiTieu.setText(chiTieu);

                    String chiTieuHomNay = ChiTieuHomNay.getText().toString();
                    String inputData = ((EditText) findViewById(R.id.inputEditText)).getText().toString();

                    double mucChiTieuValue = Double.parseDouble(inputData);
                    double chiTieuHomNayValue = Double.parseDouble(chiTieuHomNay);

                    compareValues(mucChiTieuValue, chiTieuHomNayValue);
                } else {
                    MucChiTieu.setText("Không có dữ liệu");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu từ Firebase
            }
        });
    }
    private void loadTietKiemData() {
        // Lấy dữ liệu từ EditText
        String inputData = ((EditText) findViewById(R.id.inputEditTextTietKiem)).getText().toString();

        // Kiểm tra nếu dữ liệu không rỗng
        if (!inputData.isEmpty()) {
            // Tạo một đối tượng TietKiemEntry
            TietKiemEntry entry = new TietKiemEntry(String.valueOf(System.currentTimeMillis()), Double.parseDouble(inputData));

            // Thêm đối tượng vào danh sách lịch sử
            databaseReference.child("KhoanTietKiemHistory").push().setValue(entry);
        }

        // Tiếp tục phần còn lại của phương thức
        databaseReference.child("Khoản tiết kiệm mới").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshotTietKiem) {
                if (dataSnapshotTietKiem.exists()) {
                    String tietKiem = dataSnapshotTietKiem.getValue(String.class);
                    TextView newTietKiemTextView = findViewById(R.id.NewTietKiem);
                    newTietKiemTextView.setText(tietKiem);

                    // Convert tietKiem to a numerical value
                    String inputData = ((EditText) findViewById(R.id.inputEditTextTietKiem)).getText().toString();
                    double tietKiemValue = Double.parseDouble(inputData);

                    // Get the current value of textView53
                    TextView textView53 = findViewById(R.id.textView53);
                    String currentText = textView53.getText().toString();

                    // Convert the current text to a numerical value
                    double currentTextValue = Double.parseDouble(currentText);

                    // Add tietKiemValue to currentTextValue
                    double result = currentTextValue + tietKiemValue;
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    String formattedResult = decimalFormat.format(result);

                    // Set the result to textView53
                    textView53.setText(String.valueOf(formattedResult));
                } else {
                    MucChiTieu.setText("Không có dữ liệu");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu từ Firebase
            }
        });
    }
    private void displayTietKiemHistory() {
        DatabaseReference historyRef = databaseReference.child("KhoanTietKiemHistory");

        historyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StringBuilder historyText = new StringBuilder();

                for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                    TietKiemEntry entry = entrySnapshot.getValue(TietKiemEntry.class);

                    if (entry != null) {
                        long timestamp = Long.parseLong(entry.getTimestamp());
                        String formattedTimestamp = formatTimestamp(timestamp);
                        String formattedAmount = String.format("%.0f", entry.getAmount());
                        String entryText = formattedTimestamp + ": " + formattedAmount + "\n";
                        historyText.insert(0, entryText);
                    }
                }
                // Tạo Intent để chuyển đến HistoryDetailActivity
                Intent intent = new Intent(MainActivityQuanLyChiTieu.this, HistoryDetailActivity.class);

                // Gửi dữ liệu lịch sử sang HistoryDetailActivity
                intent.putExtra("historyText", historyText.toString());

                // Chuyển đến HistoryDetailActivity
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu từ Firebase
            }
        });
    }
    private String formatTimestamp(long timestamp) {
        // Định dạng timestamp thành ngày tháng năm ở đây
        // Bạn có thể sử dụng các phương thức từ thư viện SimpleDateFormat hoặc thư viện Android DateFormat
        // Dưới đây là một ví dụ sử dụng SimpleDateFormat:
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }
    private void compareValues(Number MucChiTieu, Number ChiTieuHomNay) {
        // Thực hiện so sánh và hiển thị kết quả vào textView46
        // (Thêm logics so sánh ở đây, dưới đây là một ví dụ đơn giản)
        if (MucChiTieu.doubleValue() > ChiTieuHomNay.doubleValue()) {
            textView46.setText("Chi tiêu đạt mức cho phép");
            textView46.setTextColor(0xFF36CE00);
            imageView8.setVisibility(View.GONE);
            imageView9.setVisibility(View.VISIBLE);
        } else {
            textView46.setText("Cảnh báo Chi tiêu quá mức đặt ra");
            textView46.setTextColor(0xFFFF0000);
            imageView8.setVisibility(View.VISIBLE);
            imageView9.setVisibility(View.GONE);
        }
    }
}