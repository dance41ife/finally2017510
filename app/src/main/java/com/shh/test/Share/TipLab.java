package com.shh.test.Share;

import android.content.Context;

import com.shh.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/29.
 */

public class TipLab {
    private static TipLab sTipLab;
    private List<Tip> mTips;
    public static TipLab get(Context context){
        if (sTipLab==null){
            sTipLab=new TipLab(context);
        }
        return sTipLab;
    }
    private TipLab(Context context){
        mTips=new ArrayList<>();
        Tip tip1 = new Tip();
        Tip tip2 = new Tip();
        Tip tip3 = new Tip();
        Tip tip4 = new Tip();
        Tip tip5 = new Tip();
        Tip tip6 = new Tip();
        Tip tip7 = new Tip();
        Tip tip8 = new Tip();
//        Tip tip9 = new Tip();
        tip1.setContent("研究发现，高强度运动可在饭后两小时进行；中度运动应该安排在饭后一小时进行；最佳运动时间：下午3点至5点是最佳运动时间。");
        tip1.setTitle("最佳运动时间");
        tip1.setDrawable(context.getResources().getDrawable(R.drawable.best_time));
        tip2.setContent("有氧运动和无氧运动是运动生理学中的两个基本概念，是两种不同的运动形式，对人体产生的锻炼效果也是有差异的。");
        tip2.setTitle("有氧运动与无氧运动");
        tip2.setDrawable(context.getResources().getDrawable(R.drawable.tip1));
        tip3.setContent("春天是山青水碧、花香鸟鸣的季节，和煦的春风伴着温暖的阳光，此时是春季健身最适合的时机了。但健身也要按自身的条件，循序渐进。");
        tip3.setTitle("春季运动注意事项");
        tip3.setDrawable(context.getResources().getDrawable(R.drawable.tip4));
        tip4.setContent("夏季清晨和傍晚都比较适合运动，运动不只是简简单单穿着运动服到户外活动。还要注意穿着服装的材质、运动的强度、紫外线等等..");
        tip4.setTitle("夏季运动注意事项");
        tip4.setDrawable(context.getResources().getDrawable(R.drawable.tip2));
        tip5.setContent("户外有风险，队员不得擅自离队;特殊情况需有同伴陪同且报告组织者，经组织者同意后，方可离开，并按预定时间返回指定地点。");
        tip5.setTitle("户外运动注意事项");
        tip5.setDrawable(context.getResources().getDrawable(R.drawable.tip3));
        tip6.setContent("在这个全民健身的时代里，不少人尝试过做一些运动，尤其以户内运动为主，然而部分人不仅没能达到预期的效果，反而弄得浑身不适。");
        tip6.setTitle("户内运动注意事项");
        tip6.setDrawable(context.getResources().getDrawable(R.drawable.tip5));
        tip7.setContent("长期坚持的晨跑，从内到外确实有着很多惊人的好处，分析晨跑的十大好处和3大危害，分享5点晨跑注意事项。一日之计在于晨，一年之计在于春。");
        tip7.setTitle("晨跑的好处");
        tip7.setDrawable(context.getResources().getDrawable(R.drawable.tip6));
        tip8.setContent("夜跑，顾名思义，就是晚上进行的跑步。喜欢跑步锻炼的人们，大多坚持晨跑，但对于一些早上起不来的上班族，夜跑已经成了健身的新选择。");
        tip8.setTitle("夜跑");
        tip8.setDrawable(context.getResources().getDrawable(R.drawable.tip7));

//        tip7.setContent("在这个全民健身的时代里，不少人尝试过做一些运动，尤其以户内运动为主，然而部分人不仅没能达到预期的效果，反而弄得浑身不......");
//        tip7.setTitle("户内运动注意事项");
//        tip7.setDrawable(context.getResources().getDrawable(R.drawable.tip5));
//        tip8.setContent("在这个全民健身的时代里，不少人尝试过做一些运动，尤其以户内运动为主，然而部分人不仅没能达到预期的效果，反而弄得浑身不......");
//        tip8.setTitle("户内运动注意事项");
//        tip8.setDrawable(context.getResources().getDrawable(R.drawable.tip5));
//        tip9.setContent("在这个全民健身的时代里，不少人尝试过做一些运动，尤其以户内运动为主，然而部分人不仅没能达到预期的效果，反而弄得浑身不......");
//        tip9.setTitle("户内运动注意事项");
//        tip9.setDrawable(context.getResources().getDrawable(R.drawable.tip5));
        mTips.add(tip1);
        mTips.add(tip2);
        mTips.add(tip3);
        mTips.add(tip4);
        mTips.add(tip5);
        mTips.add(tip6);
        mTips.add(tip7);
        mTips.add(tip8);
//        mTips.add(tip9);
    }
    public List<Tip> getTips(){
        return mTips;
    }
}
