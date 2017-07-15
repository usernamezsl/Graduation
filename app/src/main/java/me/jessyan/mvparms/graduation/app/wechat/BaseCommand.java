package me.jessyan.mvparms.graduation.app.wechat;

import java.io.Serializable;

/**
 * 作者: 张少林 on 2017/4/15 0015.
 * 邮箱:1083065285@qq.com
 * 消息的基类
 */

public abstract class BaseCommand implements Serializable {
    protected String mFlag;

    public String getFlag() {
        return mFlag;
    }

    public void setFlag(String flag) {
        mFlag = flag;
    }
}
