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
import me.jessyan.mvparms.graduation.mvp.model.entity.MyCollectorOne;
import me.jessyan.mvparms.graduation.mvp.model.entity.ThemeResourc;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.MyCollectionOneAdapter;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.StudyResourceAdapter;
import me.jessyan.mvparms.graduation.mvp.ui.event.StartNextFragment;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 作者: 张少林 on 2017/4/10 0010.
 * 邮箱:1083065285@qq.com
 */

public class PopularCollectionFragment extends WEFragment {

    @BindView(R.id.rv_popular_content_list)
    RecyclerView mRvPopularContentList;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;

    private StudyResourceAdapter mAdapter;
    private MyCollectionOneAdapter mCollectionOneAdapter;

    public static PopularCollectionFragment newInstance() {
        PopularCollectionFragment popularCollectionFragment = new PopularCollectionFragment();
        return popularCollectionFragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected View initView() {
        return LayoutInflater.from(_mActivity).inflate(R.layout.fragment_popular_collection, null);
    }

    @Override
    protected void initData() {
        showContent();
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showContent();
            }
        });
    }

    private void showContent() {
//        Observable.create(new Observable.OnSubscribe<List<ThemeResourc>>() {
//            @Override
//            public void call(Subscriber<? super List<ThemeResourc>> subscriber) {
//                List<ThemeResourc> list = DataSupport.findAll(ThemeResourc.class);
//                subscriber.onNext(list);
//                subscriber.onCompleted();
//            }
//        }).subscribeOn(Schedulers.io())
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        mRefresh.setRefreshing(true);
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<List<ThemeResourc>>() {
//                    @Override
//                    public void call(List<ThemeResourc> themeResourcs) {
//                        if (mRefresh.isRefreshing()) {
//                            mRefresh.setRefreshing(false);
//                        }
//                        initAdapter(themeResourcs);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        if (mRefresh.isRefreshing()) {
//                            mRefresh.setRefreshing(false);
//                        }
//                        ToastUtils.showLongToastSafe(throwable.getMessage());
//                    }
//                });
        Observable.create(new Observable.OnSubscribe<List<MyCollectorOne>>() {
            @Override
            public void call(Subscriber<? super List<MyCollectorOne>> subscriber) {
                List<MyCollectorOne> all = DataSupport
                        .findAll(MyCollectorOne.class);
                subscriber.onNext(all);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRefresh.setRefreshing(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<MyCollectorOne>>() {
                    @Override
                    public void call(List<MyCollectorOne> myCollectorOnes) {
                        mRefresh.setRefreshing(false);
                        refreshAdapyer(myCollectorOnes);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mRefresh.isRefreshing()) {
                            mRefresh.setRefreshing(false);
                        }
                        ToastUtils.showLongToastSafe(throwable.getMessage());
                    }
                });
    }

    private void refreshAdapyer(List<MyCollectorOne> myCollectorOnes) {
        mCollectionOneAdapter = new MyCollectionOneAdapter(R.layout.item_zhihu_hot, myCollectorOnes);
        mCollectionOneAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mCollectionOneAdapter.isFirstOnly(false);
        mRvPopularContentList.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRvPopularContentList.setHasFixedSize(true);
        mRvPopularContentList.setAdapter(mCollectionOneAdapter);
        mCollectionOneAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<MyCollectorOne> myCollectorOneList = adapter.getData();
                MyCollectorOne myCollectorOne = myCollectorOneList.get(position);
                int id = myCollectorOne.getId();
                EventBus.getDefault().post(new StartNextFragment(ZhihuDetailFragment.newInstance(id)));
            }
        });
        mCollectionOneAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<MyCollectorOne> myCollectorOneList = adapter.getData();
                MyCollectorOne myCollectorOne = myCollectorOneList.get(position);
                String title = myCollectorOne.getTitle();
                DataSupport
                        .deleteAll(MyCollectorOne.class, "title = ?", title);
                ToastUtils.showLongToastSafe("取消收藏成功，请刷新显示~~");
            }
        });
    }

    private void initAdapter(List<ThemeResourc> themeResourcs) {
        mAdapter = new StudyResourceAdapter(R.layout.item_zhihu_hot, themeResourcs);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.isFirstOnly(false);
        mRvPopularContentList.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRvPopularContentList.setHasFixedSize(false);
        mRvPopularContentList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ThemeResourc> list = adapter.getData();
                ThemeResourc themeResourc = list.get(position);
                int id = themeResourc.getId();
                EventBus.getDefault().post(new StartNextFragment(ZhihuDetailFragment.newInstance(id)));
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<ThemeResourc> list = adapter.getData();
                String title = list.get(position).getTitle();
                DataSupport
                        .deleteAll(ThemeResourc.class, "title = ?", title);
                ToastUtils.showLongToastSafe("取消收藏成功，请刷新显示~~");
            }
        });
    }

}
