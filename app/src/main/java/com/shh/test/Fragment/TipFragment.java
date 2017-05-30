package com.shh.test.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shh.test.R;
import com.shh.test.Share.Tip;
import com.shh.test.Share.TipLab;

import java.util.List;

/**
 * Created by Administrator on 2017/5/29.
 */

public class TipFragment extends Fragment {

    private RecyclerView mTipRecyclerView;
    private TipAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_tip,container,false);

        mTipRecyclerView = (RecyclerView)view.findViewById(R.id.tip_recycler_view);
        mTipRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();


        return view;
    }

    private void updateUI() {

        TipLab tipLab = TipLab.get(getActivity());
        List<Tip> tips = tipLab.getTips();
        mAdapter = new TipAdapter(tips);
        mTipRecyclerView.setAdapter(mAdapter);


    }

    private class TipHolder extends RecyclerView.ViewHolder{

        private TextView mTitleTextView;
        private TextView mContentTextView;
        private ImageView mImageView;

        public  TipHolder(View itemView){
            super(itemView);
            mContentTextView = (TextView) itemView.findViewById(R.id.item_tip_content);
            mImageView = (ImageView) itemView.findViewById(R.id.item_tip_img);
            mTitleTextView = (TextView) itemView.findViewById(R.id.item_tip_title);
        }
    }

    private class TipAdapter extends RecyclerView.Adapter<TipHolder>{
        private List<Tip> tips;

        public TipAdapter(List<Tip> tips){
            this.tips = tips;
        }

        @Override
        public TipHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_tip, parent, false);
            return new TipHolder(view);
        }

        @Override
        public void onBindViewHolder(TipHolder holder, int position) {
            Tip tip = tips.get(position);
            holder.mImageView.setImageDrawable(tip.getDrawable());
            holder.mContentTextView.setText(tip.getContent());
            holder.mTitleTextView.setText(tip.getTitle());
        }

        @Override
        public int getItemCount() {
            return tips.size();
        }
    }
}
