package me.jessyan.mvparms.graduation.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.utils.UiUtils;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import common.AppComponent;
import common.WEFragment;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.di.component.DaggerMyCollectionComponent;
import me.jessyan.mvparms.graduation.di.module.MyCollectionModule;
import me.jessyan.mvparms.graduation.mvp.contract.MyCollectionContract;
import me.jessyan.mvparms.graduation.mvp.presenter.MyCollectionPresenter;
import me.jessyan.mvparms.graduation.mvp.ui.event.StartNextFragment;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * 作者: 张少林 on 2017/2/27 0027.
 * 邮箱:1083065285@qq.com
 */

public class MyCollectionFragment extends WEFragment<MyCollectionPresenter> implements MyCollectionContract.View {


    @BindView(R.id.center_title)
    TextView mCenterTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collection_tab_layout)
    TabLayout mCollectionTabLayout;
    @BindView(R.id.collection_view_pager)
    ViewPager mCollectionViewPager;
    private String[] mtitles = new String[]{"热门专题", "学习资源"};
    private List<Fragment> mFragments = new ArrayList<>();

    public static MyCollectionFragment newInstance() {
        MyCollectionFragment fragment = new MyCollectionFragment();
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerMyCollectionComponent
                .builder()
                .appComponent(appComponent)
                .myCollectionModule(new MyCollectionModule(this))//请将MyCollectionModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my_collection, null, false);
    }

    @Override
    protected void initData() {
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        initToolbarMenu(mToolbar);
        mCenterTitle.setText("我的收藏");
        mCenterTitle.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        mCollectionTabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (String mtitle : mtitles) {
            mCollectionTabLayout.addTab(mCollectionTabLayout.newTab().setText(mtitle));
        }
        mFragments.add(PopularCollectionFragment.newInstance());
        mFragments.add(StudyCollectionsFragment.newInstance());
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getChildFragmentManager(), mtitles, mFragments);
        mCollectionViewPager.setAdapter(myViewPageAdapter);
        mCollectionTabLayout.setupWithViewPager(mCollectionViewPager);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.SnackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Subscriber
    public void startNextFragment(StartNextFragment startNextFragment) {
        start(startNextFragment.mSupportFragment);
    }

    public class MyViewPageAdapter extends FragmentPagerAdapter {
        private String[] mTitles;
        private List<Fragment> mFragments;

        public MyViewPageAdapter(FragmentManager fm, String[] titles, List<Fragment> fragments) {
            super(fm);
            mTitles = titles;
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}