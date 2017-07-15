package me.jessyan.mvparms.graduation.app.wechat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.jess.arms.base.BaseFragment;

import java.util.ArrayList;


/**
 * 作者: 张少林 on 2017/4/17 0017.
 * 邮箱:1083065285@qq.com
 */
public class FragmentHandler extends Handler {
    private ArrayList<MyFragment> fragmentList;

    private Bundle bundle;
    private FragmentHandler(){
        fragmentList = new ArrayList<MyFragment>();
    }

    private static FragmentHandler instance = null;

    @Override
    public void handleMessage(Message msg) {
        // TODO Auto-generated method stub
        super.handleMessage(msg);
        bundle = msg.getData();
        try {
            if(fragmentList.size()>0){
                for(MyFragment myfragment:fragmentList){
                    myfragment.recvMsgFormHandler(bundle);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static FragmentHandler getInstance(){
        if(instance == null){
            instance = new FragmentHandler();
        }
        return instance;
    }
    public void  registerFragment(MyFragment myfragment)
    {
        Log.d("MY_FRAGMENT", "add "+myfragment.toString());
        fragmentList.add(myfragment);
    }
    public void unRegisterFragment(BaseFragment fragment){
        Log.d("MY_FRAGMENT","remove"+fragment.toString());
        fragmentList.remove(fragment);
    }
}
