package com.shh.test.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.shh.test.LoadingActivity;
import com.shh.test.R;
import com.shh.test.Share.Share;
import com.shh.test.Share.ShareLab;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */

public class MineFragment extends Fragment {
    private RecyclerView shareRecyclerView;
    private Button exitButton;
    private TextView userTextView;
    private ShareAdapter mShareAdapter;
    private TextView allTextView;
    private String userMessage="";
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fragment_mine,container,false);
        shareRecyclerView=(RecyclerView)v.findViewById(R.id.share_recyclerView);
        allTextView=(TextView)v.findViewById(R.id.all_textView);
        allTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateArticle();
                updateUI();
            }
        });
        userTextView=(TextView)v.findViewById(R.id.my_name);
        userTextView.setText(getUserName(getActivity().getIntent().getStringExtra("userMessage")));
        shareRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        exitButton=(Button)v.findViewById(R.id.exit_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(), LoadingActivity.class);
                startActivity(i);

            }
        });

        updateArticle();
        updateUI();

        return v;
    }
    private class ShareHolder extends RecyclerView.ViewHolder{
        public TextView usernameTextView;
        public TextView articleTextView;
        public ShareHolder(View itemView){
            super(itemView);
            usernameTextView=(TextView)itemView.findViewById(R.id.item_userName);
            articleTextView=(TextView)itemView.findViewById(R.id.item_article);

        }
    }
    private class ShareAdapter extends RecyclerView.Adapter<ShareHolder>{
        private List<Share> mShares;
        public ShareAdapter(List<Share> shares){
            mShares=shares;
        }
        @Override
        public ShareHolder onCreateViewHolder(ViewGroup parent,int viwType){
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            View view=layoutInflater.inflate(R.layout.item_message_list,parent,false);
            return new ShareHolder(view);
        }
        @Override
        public void onBindViewHolder(ShareHolder holder,int position){
            Share share=mShares.get(position);
            holder.usernameTextView.setText(share.getUserName());
            holder.articleTextView.setText(share.getShareMessage());
        }
        @Override
        public int getItemCount(){
            return mShares.size();
        }

    }
    private void updateUI(){
        ShareLab shareLab=ShareLab.get(getActivity());
        List<Share> shares=shareLab.getShares();
        mShareAdapter=new ShareAdapter(shares);
        shareRecyclerView.setAdapter(mShareAdapter);
    }
    private String  getUserName(String userMessage){
        char []a=userMessage.toCharArray();
        String str="";
        for (int i=22;i<a.length;i++){
            str=str+a[i];
        }
        return str;
    }
    public void updateArticle(){
        HttpParams params = new HttpParams();

        //获取页面 0
        params.put("articleStatus","0");
        RxVolley.post("http://123.206.119.108:8080/MyStepServer/Article", params, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                System.out.println("MaineFragment: "+t);
                List<Share> shares=new ArrayList<Share>();
                String []str=t.split("&&");
                for(int i=0;i<str.length;i++){
                    System.out.println("MaineFragment: str"+str[i]);
                }
                String []user=new String[1000];
                String []article=new String[1000];
                for(int i=0;i<str.length;i++){
                    Share share=new Share();
                    String[]a=str[i].split("@@@");
                    user[i]=a[0];
                    share.setUserName(user[i]);
                    System.out.println("MaineFragment: user"+user[i]);
                    article[i]=a[1];
                    share.setShareMessage(article[i]);
                    System.out.println("MaineFragment: article"+article[i]);
                    shares.add(share);
                }
                ShareLab.get(getActivity()).setShare(shares);

            }
        });

    }

}
