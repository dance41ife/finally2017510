package com.shh.test.Fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.shh.test.R;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/5/27.
 */

public class RegisterFragment extends DialogFragment {
    private EditText etUserName;
    private EditText etPassWord;
    private String RegisterUserName;
    private String RegisterPassWord;
    private CheckBox registerCheckBox;
    private Button cancelButton;
    private Button okBUtton;
    private TextView registerMessageTextView;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_register,null);
        etUserName=(EditText)v.findViewById(R.id.username_editText);
        etPassWord=(EditText)v.findViewById(R.id.userpassword_editText);
        registerMessageTextView=(TextView)v.findViewById(R.id.register_message_textView);
        registerCheckBox=(CheckBox)v.findViewById(R.id.register_checkbox);
        registerCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    etPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    etPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
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
        cancelButton=(Button)v.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        okBUtton=(Button)v.findViewById(R.id.ok_button);
        okBUtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RegisterUserName==null||RegisterPassWord==null){
                    registerMessageTextView.setText("用户名或者密码不能为空!");
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
                                registerMessageTextView.setText("用户名已存在");
                            }
                            else {
                                getDialog().dismiss();
                            }
                        }
                    });
                }

            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setCancelable(false)
                .setTitle("注册成为新用户:")
                .create();

    }
}
