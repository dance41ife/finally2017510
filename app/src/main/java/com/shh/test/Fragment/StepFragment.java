package com.shh.test.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shh.test.R;

import java.util.Timer;
import java.util.TimerTask;

import static com.shh.test.MainActivity.step;

public class StepFragment extends Fragment {


    private   TextView text_step;


    private static final String TAG = "StepFragment";
    private static String CURRENTDATE="";   //当前的日期
    private boolean isNewDay=false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_step, container, false);
        text_step=(TextView)v.findViewById(R.id.main_text_step);
        init();
        return v;

    }
    //开启线程，接受来自activity的全局变量step，并将它显示。
    public void init(){
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==1){
                    Log.e(TAG, "handleMessage: "+step);
                    text_step.setText(step+"");
                }
            }
        };
        Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        };

        timer.schedule(timerTask,500,500);
    }




}
