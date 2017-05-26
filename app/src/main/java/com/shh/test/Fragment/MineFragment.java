package com.shh.test.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.shh.test.LoadingActivity;
import com.shh.test.MainActivity;
import com.shh.test.R;

/**
 * Created by Administrator on 2017/5/26.
 */

public class MineFragment extends Fragment {
    private Button exitButton;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fragment_mine,container,false);
        exitButton=(Button)v.findViewById(R.id.exit_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(), LoadingActivity.class);
                startActivity(i);
            }
        });

        return v;
    }
}
