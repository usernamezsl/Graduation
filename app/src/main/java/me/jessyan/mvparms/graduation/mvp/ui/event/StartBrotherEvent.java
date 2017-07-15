package me.jessyan.mvparms.graduation.mvp.ui.event;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 作者: 张少林 on 2017/3/2 0002.
 * 邮箱:1083065285@qq.com
 */

public class StartBrotherEvent {
    public SupportFragment targetFragment;

    public StartBrotherEvent(SupportFragment targetFragment) {
        this.targetFragment = targetFragment;
    }
}
