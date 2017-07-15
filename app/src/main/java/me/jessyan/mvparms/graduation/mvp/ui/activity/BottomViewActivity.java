package me.jessyan.mvparms.graduation.mvp.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.blankj.utilcode.utils.BarUtils;
import com.jess.arms.base.BaseApplication;

import org.simple.eventbus.Subscriber;

import butterknife.ButterKnife;
import common.AppComponent;
import common.WEActivity;
import common.WEApplication;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.app.service.DemoService;
import me.jessyan.mvparms.graduation.app.wechat.ChatMsgEntity;
import me.jessyan.mvparms.graduation.mvp.presenter.LoginPresenter;
import me.jessyan.mvparms.graduation.mvp.ui.fragment.MainFragment;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation.helper.FragmentLifecycleCallbacks;

public class BottomViewActivity extends WEActivity {
    private static final String TAG = "BottomViewActivity";
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;
    public static DemoService.MyBinder myBinder = null;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (DemoService.MyBinder) service;

//            myBinder.chatSocketThread();
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        int parseColor = Color.parseColor("#4BA1EE");
//        initState();
        BarUtils.setColor(this, parseColor);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_main_container, MainFragment.newInstance());
        }
        // 可以监听该Activity下的所有Fragment的18个 生命周期方法
        registerFragmentLifecycleCallbacks(new FragmentLifecycleCallbacks() {

            @Override
            public void onFragmentSupportVisible(SupportFragment fragment) {
                Log.i(TAG, "onFragmentSupportVisible--->" + fragment.getClass().getSimpleName());
            }

            @Override
            public void onFragmentCreated(SupportFragment fragment, Bundle savedInstanceState) {
                Log.i(TAG, "onFragmentCreated--->" + fragment.getClass().getSimpleName());
            }

            // 省略其余生命周期方法
        });
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!bindService(new Intent(BottomViewActivity.this, DemoService.class), connection, BIND_AUTO_CREATE))
                        ;


                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = getIntent();
                    String username = intent.getStringExtra(LoginPresenter.NAME);
                    while (myBinder == null) ;
                    myBinder.chatSocketThread();
                    while (DemoService.socket == null);
                    ChatMsgEntity onLine = new ChatMsgEntity();
                    onLine.setTyp(1);
                    onLine.setName(username);
                    onLine.setText("登录成功~~");

                }
            }).start();
        } catch (Exception e) {
            Toast.makeText(this, "服务器连接失败！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 沉浸式状态栏
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    public void onExceptionAfterOnSaveInstanceState(Exception e) {
        // TODO: 16/12/7 在此可以监听到警告： Can not perform this action after onSaveInstanceState!
        // 建议在线上包中，此处上传到异常检测服务器（eg. 自家异常检测系统或Bugtags等崩溃检测第三方），来监控该异常
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bottom_view;
    }

    @Subscriber
    public void getChatMsg(ChatMsgEntity chatMsgEntity) {

    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void onBackPressedSupport() {

        Fragment topFragment = getTopFragment();

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                ((WEApplication) BaseApplication.getInstance())
                        .getAppComponent()
                        .appManager()
                        .AppExit();

            } else {
                TOUCH_TIME = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出~", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
