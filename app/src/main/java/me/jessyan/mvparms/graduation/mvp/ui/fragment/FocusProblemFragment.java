package me.jessyan.mvparms.graduation.mvp.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.blankj.utilcode.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.litepal.crud.DataSupport;
import org.simple.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import common.AppComponent;
import common.WEFragment;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.Focus;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.FocusProblemAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 作者: 张少林 on 2017/4/14 0014.
 * 邮箱:1083065285@qq.com
 */

public class FocusProblemFragment extends WEFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private FocusProblemAdapter mAdapter;

    public static FocusProblemFragment newInstance() {
        FocusProblemFragment focusProblemFragment = new FocusProblemFragment();
        return focusProblemFragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected View initView() {
        return LayoutInflater.from(_mActivity).inflate(R.layout.fragment_myfocusproblem_view, null);
    }

    @Override
    protected void initData() {
        getDataFromDb();
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromDb();
            }
        });
    }

    private void getDataFromDb() {
        Observable.create(new Observable.OnSubscribe<List<Focus>>() {
            @Override
            public void call(Subscriber<? super List<Focus>> subscriber) {
                List<Focus> list = DataSupport
                        .findAll(Focus.class);
                subscriber.onNext(list);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Focus>>() {
                    @Override
                    public void call(List<Focus> focuses) {
                        mSwipeRefresh.setRefreshing(false);
                        showContent(focuses);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mSwipeRefresh.isRefreshing()) {
                            mSwipeRefresh.setRefreshing(false);
                        }
                        ToastUtils.showLongToastSafe(throwable.getMessage());
                    }
                });
    }

    private void showContent(List<Focus> focuses) {
        mAdapter = new FocusProblemAdapter(R.layout.item_list_view, focuses);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.isFirstOnly(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<Focus> list = adapter.getData();
                String title = list.get(position).getTitle();
                String content = list.get(position).getContent();
                EventBus.getDefault().post(new me.jessyan.mvparms.graduation.app.EventBus.FocusToNext(ArticleDetailFragment.newInstance(title, content)));
            }
        });
    }
}
