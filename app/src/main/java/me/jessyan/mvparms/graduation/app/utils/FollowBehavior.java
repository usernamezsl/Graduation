package me.jessyan.mvparms.graduation.app.utils;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 作者: 张少林 on 2017/2/28 0028.
 * 邮箱:1083065285@qq.com
 */

public class FollowBehavior extends CoordinatorLayout.Behavior<TextView> {
    public FollowBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 对界面进行布局时至少会调用一次，用来确定本次交互行为中的dependent view
     * 当dependency是Button类的实例时返回true，
     * 就可以让系统知道布局文件中的Button就是本次交互行为中的dependent view。
     *
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        return dependency instanceof Button;
    }

    /**
     * 当dependent view发生变化时，这个方法会被调用
     * 参数中的child相当于本次交互行为中的观察者，
     * 观察者可以在这个方法中对被观察者的变化做出响应，从而完成一次交互行为。
     *
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
        child.setX(dependency.getX() + 150);
        child.setY(dependency.getY() + 150);
        return true;
    }
}
