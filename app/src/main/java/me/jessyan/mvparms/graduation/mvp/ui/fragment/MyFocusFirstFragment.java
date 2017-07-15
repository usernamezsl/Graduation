package me.jessyan.mvparms.graduation.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.utils.UiUtils;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import common.AppComponent;
import common.WEFragment;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.di.component.DaggerMyFocusFirstComponent;
import me.jessyan.mvparms.graduation.di.module.MyFocusFirstModule;
import me.jessyan.mvparms.graduation.mvp.contract.MyFocusFirstContract;
import me.jessyan.mvparms.graduation.mvp.model.entity.GankTechBean;
import me.jessyan.mvparms.graduation.mvp.presenter.MyFocusFirstPresenter;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.GankTeachAdapter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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
 * 作者: 张少林 on 2017/4/10 0010.
 * 邮箱:1083065285@qq.com
 */

public class MyFocusFirstFragment extends WEFragment<MyFocusFirstPresenter> implements MyFocusFirstContract.View {


    @BindView(R.id.rv_android_list)
    RecyclerView mRvAndroidList;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;

    private List<GankTechBean.ResultsBean> mList;
    private GankTeachAdapter mAdapter;

    public static MyFocusFirstFragment newInstance() {
        MyFocusFirstFragment fragment = new MyFocusFirstFragment();
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerMyFocusFirstComponent
                .builder()
                .appComponent(appComponent)
                .myFocusFirstModule(new MyFocusFirstModule(this))//请将MyFocusFirstModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_myfocus_first, null, false);
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        mList = new ArrayList<>();
        mAdapter = new GankTeachAdapter(R.layout.item_zhihu_hot, mList);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.isFirstOnly(false);
        mRvAndroidList.setVisibility(View.INVISIBLE);
        mRvAndroidList.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRvAndroidList.setHasFixedSize(true);
        mRvAndroidList.setAdapter(mAdapter);
        mPresenter.getAndroidData("Android", 20, 0);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getAndroidData("Android", 20, 0);
            }
        });
    }

    @Override
    public void showLoading() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mRefresh.setRefreshing(true);
                    }
                });
    }

    @Override
    public void hideLoading() {
        if (mRefresh.isRefreshing()) {
            Observable.just(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Integer>() {
                        @Override
                        public void call(Integer integer) {
                            mRefresh.setRefreshing(false);
                        }
                    });
        }
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

    @Override
    public void showContent(List<GankTechBean.ResultsBean> resultsBeen) {
        hideLoading();
        mRvAndroidList.setVisibility(View.VISIBLE);
        mList.clear();
        mList.addAll(resultsBeen);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                List<GankTechBean.ResultsBean> data = adapter.getData();
                GankTechBean.ResultsBean bean = data.get(position);

                String id = bean.get_id();
                String url = bean.getUrl();
                String desc = bean.getDesc();

                EventBus.getDefault().post(new me.jessyan.mvparms.graduation.app.EventBus.FocusToNext(StudyDetailFragment.newInstance(desc, url)));
            }
        });
    }

}