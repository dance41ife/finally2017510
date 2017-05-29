package com.shh.test.Share;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/5/29.
 */

public class ShareLab {
    private static ShareLab sShareLab;
    private List<Share> mShares;
    public static ShareLab get(Context context){
        if (sShareLab==null){
            sShareLab=new ShareLab(context);
        }
        return sShareLab;
    }
    private ShareLab(Context context){
        mShares=new ArrayList<>();
        for (int i=0;i<20;i++){
            Share share=new Share();
            share.setUserName(i+" username");
            share.setShareMessage(i+" ShareMessage");
            mShares.add(share);
        }
    }
    public List<Share> getShares(){
        return mShares;
    }
    public Share getShare(UUID id){
        for (Share share:mShares){
            if (share.getId().equals(id)){
                return share;
            }
        }
        return null;
    }
}
