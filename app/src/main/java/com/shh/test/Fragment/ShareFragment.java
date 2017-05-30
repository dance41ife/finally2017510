package com.shh.test.Fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.shh.test.R;

/**
 * Created by Administrator on 2017/5/30.
 */

public class ShareFragment extends DialogFragment {
    private Button ccButton;
    private Button ShareButton;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_share,null);
        ccButton=(Button)v.findViewById(R.id.share_button_cancel);
        ccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        ShareButton=(Button)v.findViewById(R.id.share_button_ok);
        ShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return new AlertDialog.Builder(getActivity())
                .setTitle("分享你的心情吧!   "+getUserName(getActivity().getIntent().getStringExtra("userMessage")))
                .setView(v)
                .create();
    }
    private String  getUserName(String userMessage){
        char []a=userMessage.toCharArray();
        String str="";
        for (int i=22;i<a.length;i++){
            str=str+a[i];
        }
        return str;
    }
    
}
