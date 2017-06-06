package com.shh.test.Fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.shh.test.R;

/**
 * Created by Administrator on 2017/5/30.
 */

public class ShareFragment extends DialogFragment {
    private Button ccButton;
    private Button ShareButton;
    private EditText etArticle;
    private String article=null;
    private TextView shareMessageTextView;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_share,null);
        shareMessageTextView=(TextView)v.findViewById(R.id.share_message_textView);
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
                if (article==null){
                    shareMessageTextView.setText("你总要写些什么吧");
                }else {
                HttpParams params = new HttpParams();
                //articleStatus为 1 的时候是分享文章，同样适用volley框架。
                params.put("articleStatus","1");
                params.put("username",getUserName(getActivity().getIntent().getStringExtra("userMessage")));
                params.put("article",article);
                RxVolley.post("http://123.206.119.108:8080/MyStepServer/Article", params, new HttpCallback(){
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                    }
                });
                getDialog().dismiss();
                }
            }
        });
        etArticle=(EditText)v.findViewById(R.id.article_editText);
        etArticle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                article=s+"";
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return new AlertDialog.Builder(getActivity())
                .setTitle("分享你的心情吧!")
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
