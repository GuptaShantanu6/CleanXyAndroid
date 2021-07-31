package com.example.cleanxyandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class notificationActivity extends AppCompatActivity {

    private ArrayList<notification_data> notificationData;
    private RecyclerView notificationRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationData=new ArrayList<>();
        notificationRecycler=findViewById(R.id.notificationRecycler);

        setUserInfo();
        setAdapter();
    }

    private void setAdapter() {
        notificationRecylerAdapter adapter=new notificationRecylerAdapter(notificationData);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        notificationRecycler.setLayoutManager(layoutManager);
        notificationRecycler.setItemAnimator(new DefaultItemAnimator());
        notificationRecycler.setAdapter(adapter);
    }

    private void setUserInfo() {
        notificationData.add(new notification_data("Your slot with Niranjana has been confirmed ","16:27"));
        notificationData.add(new notification_data("Today’s special offer :  Laundry work 25 % off.  Valid till 20th May ","16:27"));
    }
}