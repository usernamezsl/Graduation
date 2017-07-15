package me.jessyan.mvparms.graduation.app.wechat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import common.WEFragment;


/**
 *将fragment添加到arrayList中，定义消息接口
 * Created by Administrator on 2017/1/13.
 */

public abstract class MyFragment extends WEFragment {

    private Context context = null;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将fragment加入ArrayList<>中
        FragmentHandler.getInstance().registerFragment(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * onDestroy时将fragment移除
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        FragmentHandler.getInstance().unRegisterFragment(this);
    }

    /**
     * 消息接口
     * @param bundle
     */
    public abstract void recvMsgFormHandler(Bundle bundle);
}
