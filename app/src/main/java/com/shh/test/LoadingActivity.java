package com.shh.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.toolbox.Loger;
import com.shh.test.Fragment.RegisterFragment;

/**
 * Created by Administrator on 2017/5/26.
 */

public class LoadingActivity extends Activity {
    private Button registerButton;

    private Button loginButton;
    private EditText editTextUserName;
    private EditText editTextPassWord;
    private CheckBox mCheckBox;
    String password;
    String username;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        loginButton=(Button)findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpParams params = new HttpParams();
                if(username==null||password==null){
                    Toast.makeText(LoadingActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();

                }else{
                    //使用了RxVolly框架，进行网络连接，通过回掉函数，获得web端返回的请求，为字符串t。
                    //使用post方法，使用params将param传入到url链接中。
                    params.put("login","1");
                    params.put("username", username);
                    params.put("password", password);
                    RxVolley.post("http://123.206.119.108:8080/MyStepServer/loginServlet", params, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            Loger.debug("请求到的数据1:" + t);
//                        loginStatus=t;

                            if(t.equals("user not exist")){
                                Toast.makeText(LoadingActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                            }else {
                                Intent i=new Intent(LoadingActivity.this,MainActivity.class);
                                startActivity(i);
                            }
                        }
                    });}
//                System.out.println("请求到的数据2:"+loginStatus);

            }
        });
        editTextUserName=(EditText) findViewById(R.id.username_editText);
        editTextPassWord=(EditText) findViewById(R.id.userpassword_editText);

        editTextUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                username=s+"";
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("LoginActivity: "+username);
            }
        });
        editTextPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password=s+"";
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        registerButton=(Button)findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.FragmentManager manager=getFragmentManager();
                RegisterFragment registerFragment=new RegisterFragment();
                registerFragment.show(manager,"");

            }
        });
        mCheckBox=(CheckBox)findViewById(R.id.checkbox);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    editTextPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    editTextPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
}
