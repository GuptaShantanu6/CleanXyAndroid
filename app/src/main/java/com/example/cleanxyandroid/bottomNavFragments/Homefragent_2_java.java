package com.example.cleanxyandroid.bottomNavFragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cleanxyandroid.R;
import com.example.cleanxyandroid.timerActivity;

import java.util.Locale;


public class Homefragent_2_java extends Fragment {


    private ImageButton imageButton_timer,btn_close_timer;
    private TextView time;
    private Button btn_start,btn_stop;
    private AlertDialog alertDialog;

    private int second=0;
    private boolean running;
    private boolean wasrunning;


    private CountDownTimer timer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_homefragent_2_java, container, false);

        imageButton_timer=view.findViewById(R.id.imageButton_timer);


        imageButton_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), timerActivity.class));



//                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
//                builder.setCancelable(true);
//
//                View addView=LayoutInflater.from(getContext()).inflate(R.layout.time_tacking_pop_up,null);
//
//                btn_start=addView.findViewById(R.id.btn_start_timer);
//                btn_stop=addView.findViewById(R.id.btn_stop_timer);
//
//                btn_close_timer=addView.findViewById(R.id.btn_close_timer);
//                time=addView.findViewById(R.id.txt_Time);
//
//                btn_close_timer.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        alertDialog.dismiss();
//                        second=0;
//                    }
//                });
//                btn_start.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        runtimer();
////                        time.setText("sdsdsd");
//                        running=true;
//                        final Handler handler=new Handler();
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                int hr=second/3600;
//                                int min=(second%3600)/60;
//                                int secs=second%60;
//
//                                String showTime=String.format(Locale.getDefault(),"%02d:%02d:%02d",hr,min,secs);
//                                time.setText(showTime);
//
//                                if(running){
//                                    second++;
//                                }
//                                handler.postDelayed(this,1000);
//
//                            }
//                        });
//                    }
//                });
//
//                btn_stop.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v){
//                            running=false;
//                    }
//                });
//
////                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//                builder.setView(addView);
//                alertDialog=builder.create();
//                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                alertDialog.show();
            }

            private void runtimer() {
                final Handler handler=new Handler();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        int hr=second/3600;
                        int min=(second%3600)/60;
                        int secs=second%60;
                        time.setText("sdsdsd");
                        String showTime=String.format(Locale.getDefault(),"%02d:%02d:%02d",hr,min,secs);
                        time.setText(showTime);

                        if(running){
                            second++;
                        }
                        handler.postDelayed(this,1000);

                    }
                });
            }
        });

        return view;
    }


}