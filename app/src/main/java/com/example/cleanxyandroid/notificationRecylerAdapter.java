package com.example.cleanxyandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class notificationRecylerAdapter extends RecyclerView.Adapter<notificationRecylerAdapter.MyViewHolder> {

    private ArrayList<notification_data> notificationList;

    public notificationRecylerAdapter(ArrayList<notification_data> notificationList) {
        this.notificationList = notificationList;
    }

    @NonNull
    @NotNull
    @Override
    public notificationRecylerAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull notificationRecylerAdapter.MyViewHolder holder, int position) {
        String notification=notificationList.get(position).getNotification();
        String time=notificationList.get(position).getTime();
        holder.notification.setText(notification);
        holder.time.setText(time);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView notification,time;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            notification=itemView.findViewById(R.id.notification_info);
            time=itemView.findViewById(R.id.notification_time);

        }
    }
}
