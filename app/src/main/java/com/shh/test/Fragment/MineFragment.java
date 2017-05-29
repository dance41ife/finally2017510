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

import com.shh.test.LoadingActivity;
import com.shh.test.MainActivity;
import com.shh.test.R;
import com.shh.test.Share.Share;
import com.shh.test.Share.ShareLab;

import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */

public class MineFragment extends Fragment {
    private RecyclerView shareRecyclerView;
    private Button exitButton;
    private ShareAdapter mShareAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fragment_mine,container,false);
        shareRecyclerView=(RecyclerView)v.findViewById(R.id.share_recyclerView);
        shareRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        exitButton=(Button)v.findViewById(R.id.exit_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(), LoadingActivity.class);
                startActivity(i);
            }
        });
        updateUI();
        return v;
    }
    private class ShareHolder extends RecyclerView.ViewHolder{
        public TextView usernameTextView;
        public ShareHolder(View itemView){
            super(itemView);
            usernameTextView=(TextView)itemView;
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
            View view=layoutInflater.inflate(android.R.layout.simple_list_item_1,parent,false);
            return new ShareHolder(view);
        }
        @Override
        public void onBindViewHolder(ShareHolder holder,int position){
            Share share=mShares.get(position);
            holder.usernameTextView.setText(share.getUserName());
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
}
