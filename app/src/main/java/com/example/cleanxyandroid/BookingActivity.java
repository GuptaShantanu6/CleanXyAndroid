package com.example.cleanxyandroid;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleanxyandroid.adapters.StudentAdapter;
import com.example.cleanxyandroid.studentData;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BookingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    Button add_new;
    private List studentDataList = new ArrayList<>();
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

add_new=findViewById(R.id.add_new_address);
        recyclerView = findViewById(R.id.recycler_view);
        studentAdapter = new StudentAdapter(studentDataList,BookingActivity.this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(studentAdapter);
        StudentDataPrepare();
        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidein();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void StudentDataPrepare() {
        studentData data = new studentData("20-6-31 malladi vari Street, 1st line, ramalingeswarapeta, ayodhya Nagar, Vijayawada, krishna, Andhra Pradesh - 520003");
        studentDataList.add(data);
        data = new studentData("20-6-31 malladi vari Street, 1st line, ramalingeswarapeta, ayodhya Nagar, Vijayawada, krishna, Andhra Pradesh - 520003");
        studentDataList.add(data);
        data = new studentData("20-6-31 malladi vari Street, 1st line, ramalingeswarapeta, ayodhya Nagar, Vijayawada, krishna, Andhra Pradesh - 520003");
        studentDataList.add(data);
        }
        public void slidein(){
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(R.layout.temp_address_add);

            LinearLayout booking = bottomSheetDialog.findViewById(R.id.temp_address_add_id);


            bottomSheetDialog.show();

    }}
