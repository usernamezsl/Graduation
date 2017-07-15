package me.jessyan.mvparms.graduation.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import common.AppComponent;
import common.WEFragment;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.api.User;
import me.jessyan.mvparms.graduation.mvp.model.entity.TabSelectedEvent;
import me.jessyan.mvparms.graduation.mvp.ui.event.HideBottomBar;
import me.jessyan.mvparms.graduation.mvp.ui.event.ShowBottomBar;
import me.jessyan.mvparms.graduation.mvp.ui.event.StartBrotherEvent;
import me.jessyan.mvparms.graduation.mvp.ui.fragment.article.HotFragment;
import me.jessyan.mvparms.graduation.mvp.ui.view.BottomBar;
import me.jessyan.mvparms.graduation.mvp.ui.view.BottomBarTab;
import me.yokeyword.fragmentation.SupportFragment;

import static me.jessyan.mvparms.graduation.mvp.ui.activity.BottomViewActivity.FOURTH;

/**
 * 作者: 张少林 on 2017/3/1 0001.
 * 邮箱:1083065285@qq.com
 */

public class MainFragment extends WEFragment {

    private static final String TAG = "MainFragment";

    private static final int REQ_MSG = 10;

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURE = 3;
    @BindView(R.id.bottom)
    BottomBar mBottom;

    private SupportFragment[] mFragments = new SupportFragment[4];

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mFragments[FIRST] = HomeFragment.newInstance();
            mFragments[SECOND] = HotFragment.newInstance();
            mFragments[THIRD] = ChatFragment.newInstance();
            mFragments[FOURTH] = MoreFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法方便些
            mFragments[FIRST] = findFragment(HomeFragment.class);
            mFragments[SECOND] = findFragment(HotFragment.class);
            mFragments[THIRD] = findFragment(ChatFragment.class);
            mFragments[FOURTH] = findFragment(MoreFragment.class);
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQ_MSG && resultCode == RESULT_OK) {

        }
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_main_zhihu, null, false);
    }

    @Override
    protected void initData() {
        mBottom.addItem(new BottomBarTab(_mActivity, R.drawable.tab_home, "首页"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.tab_explore, "热门"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.tab_notifications, "联系人"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.tab_profile, "更多"));
        // 模拟未读消息
//        mBottom.getItem(FIRST).setUnreadCount(9);
        mBottom.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
//                BottomBarTab tab = mBottom.getItem(FIRST);
//                if (position == FIRST) {
//                    tab.setUnreadCount(0);
//                } else {
//                    tab.setUnreadCount(tab.getUnreadCount() + 1);
//                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                // 这里推荐使用EventBus来实现 -> 解耦
                // 在FirstPagerFragment,FirstHomeFragment中接收, 因为是嵌套的Fragment
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                EventBus.getDefault().post(new TabSelectedEvent(position));
            }
        });
    }

    /**
     * start other BrotherFragment
     *
     * @param event
     */
    @Subscriber
    public void startBrother(StartBrotherEvent event) {
        start(event.targetFragment);
    }

    /**
     * 隐藏bottomBar
     *
     * @param event
     */
    @Subscriber
    public void hideBottomBar(HideBottomBar event) {
        mBottom.hide(true);
    }

    /**
     * 显示bottomBar
     *
     * @param event
     */
    @Subscriber
    public void showBottomBar(ShowBottomBar event) {
        mBottom.show(true);
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Subscriber
    public void getUsers(User user) {
        Log.i(TAG, "getUsers: " + user);
    }

}
