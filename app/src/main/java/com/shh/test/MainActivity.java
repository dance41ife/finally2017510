package com.shh.test;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.shh.test.Fragment.PathFragment;
import com.shh.test.Fragment.StepFragment;
import com.shh.test.menu.TabFragment;
import com.shh.test.menu.library.XFragmentTabHost;
import com.shh.test.menu.library.TabItem;
import com.shh.test.myStep.config.Constant;
import com.shh.test.myStep.service.StepService;

public class MainActivity extends AppCompatActivity implements Handler.Callback{
    private static final String TAG = "currentTime";
    //全局变量
    public static int step=0;
    //基于FragmentTabHost的控件
    private XFragmentTabHost mTabHost;

    String[] mTabTitle = new String[] {"首页", "软件", "游戏", "管理"};
    int[] mImageResId = new int[] {R.drawable.sel_tab_home, R.drawable.sel_tab_app,
            R.drawable.sel_tab_game, R.drawable.sel_tab_mag};

    //这是你要用到的Fragment
    Class[] mFragClass = new Class[] {StepFragment.class, PathFragment.class,
            TabFragment.class, TabFragment.class};

    int stepNum=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_menu_clip);
        delayHandler = new Handler(this);
        initTabHost();


    }

    private void initTabHost() {
        //展开样式数组
        Drawable[] drawables = new Drawable[] { ContextCompat.getDrawable(this, R.mipmap.ic_bg1),
                ContextCompat.getDrawable(this, R.mipmap.ic_bg2),
                ContextCompat.getDrawable(this, R.mipmap.ic_bg3),
                ContextCompat.getDrawable(this, R.mipmap.ic_bg4)};

        mTabHost = (XFragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.relate_tab_content);
        mTabHost.setTabMode(XFragmentTabHost.TabMode.ClipDrawable);

        for (int i = 0; i < mFragClass.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putString(TabFragment.FRAG_KEY, mTabTitle[i]);
            mTabHost.addTabItem(new TabItem(mTabTitle[i], drawables[i], mImageResId[i]),
                    mFragClass[i], bundle);

        }
    }
    //循环取当前时刻的步数中间的时间间隔
    private long TIME_INTERVAL = 500;
    //控件
    private TextView text_step;    //显示走的步数

    private Messenger messenger;
    private Messenger mGetReplyMessenger = new Messenger(new Handler(this));
    private Handler delayHandler;




    //以bind形式开启service，故有ServiceConnection接收回调
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                messenger = new Messenger(service);
                Message msg = Message.obtain(null, Constant.MSG_FROM_CLIENT);
                msg.replyTo = mGetReplyMessenger;
                messenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {}
    };

    //接收从服务端回调的步数
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case Constant.MSG_FROM_SERVER:
                //更新步数
                //text_step.setText(msg.getData().getInt("step") + "");
                //将stepNum传入stepFragment中
                stepNum=msg.getData().getInt("step");
                step=msg.getData().getInt("step");
                checkTime();
//                Bundle bundle=new Bundle();
//                bundle.putString("stepNum",""+1);
                  //StepFragment stepFragment=new StepFragment();
//                stepFragment.setArguments(bundle);
                //Log.d("step2",stepFragment.getId()+"");
                //text_step=(TextView) getFragmentManager().findFragmentById(R.id.step_fragment).getView().findViewById(R.id.main_text_step);
                //text_step=(TextView)findViewById(R.id.main_text_step);

//                text_step=(TextView)findViewById(R.id.main_text_step);
//                text_step.setText(stepNum+"");
                delayHandler.sendEmptyMessageDelayed(Constant.REQUEST_SERVER, TIME_INTERVAL);
                break;
            case Constant.REQUEST_SERVER:
                try {
                    Message msgl = Message.obtain(null, Constant.MSG_FROM_CLIENT);
                    msgl.replyTo = mGetReplyMessenger;
                    messenger.send(msgl);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
        return false;
    }
    //判断当前时间是否为11：00
    public void checkTime(){
        int currentTime=0;
        Time time=new Time();
        time.setToNow();
        currentTime=time.hour;
        Log.e(TAG, "checkTime: "+currentTime );
        if(currentTime==23){
           // new sqlConn().getCon();
        }


    }
    @Override
    public void onStart() {
        super.onStart();
        setupService();
    }
    /**
     * 开启服务
     */
    private void setupService() {
        Intent intent = new Intent(this, StepService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        //取消服务绑定
        unbindService(conn);
        super.onDestroy();
    }
}



