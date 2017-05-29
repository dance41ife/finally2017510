package com.shh.test.Fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.shh.test.R;

/**
 * Created by Administrator on 2017/5/27.
 */

public class RegisterFragment extends DialogFragment {
    private EditText etUserName;
    private EditText etPassWord;
    private String RegisterUserName;
    private String RegisterPassWord;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_register,null);
        etUserName=(EditText)v.findViewById(R.id.username_editText);
        etPassWord=(EditText)v.findViewById(R.id.userpassword_editText);

        etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                RegisterUserName=s+"";
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                RegisterPassWord=s+"";
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Register as a new user")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(RegisterUserName==null||RegisterPassWord==null){
                            Toast.makeText(getActivity(),"用户名密码不能为空",Toast.LENGTH_SHORT).show();
                        }else {
                        HttpParams params = new HttpParams();
                        params.put("login","0");
                        params.put("username", RegisterUserName);
                        params.put("password", RegisterPassWord);
                        RxVolley.post("http://123.206.119.108:8080/MyStepServer/loginServlet", params, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
                                System.out.println("LOGIN STATUS;"+t);
                                if(t.equals("user has exits")){
                                   Toast.makeText(getActivity(),"用户名存在",Toast.LENGTH_SHORT).show();
                                }else{
                                }
                            }
                        });
                        }
                    }
                })
                .create();
    }
}
