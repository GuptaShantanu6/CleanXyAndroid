package com.example.cleanxyandroid.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleanxyandroid.BookingActivity;
import com.example.cleanxyandroid.R;
import com.example.cleanxyandroid.studentData;

import java.util.List;
import java.util.Random;

public class StudentAdapter extends RecyclerView.Adapter {
    List<studentData> studentDataList;
    TextView name;

    public StudentAdapter(List studentDataList, BookingActivity bookingActivity) {
        this.studentDataList = studentDataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_list_row, parent, false);
        name=parent.findViewById(R.id.name);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
       name.setText((CharSequence) studentDataList.get(i));

    }

    @Override
    public int getItemCount() {
        return studentDataList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        LinearLayout parent;
        public MyViewHolder(View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            name = itemView.findViewById(R.id.name);
        }
    }
}
