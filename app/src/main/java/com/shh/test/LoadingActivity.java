package com.shh.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.shh.test.Fragment.RegisterFragment;

/**
 * Created by Administrator on 2017/5/26.
 */

public class LoadingActivity extends Activity {
    private Button registerButton;
    private Button loginButton;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        loginButton=(Button)findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoadingActivity.this,MainActivity.class);
                startActivity(i);
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
    }
}
