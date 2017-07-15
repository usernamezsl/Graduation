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
import me.jessyan.mvparms.graduation.mvp.model.entity.ThemeResourc;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.StudyResourceAdapter;
import me.jessyan.mvparms.graduation.mvp.ui.event.StartNextFragment;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * 作者: 张少林 on 2017/4/9 0009.
 * 邮箱:1083065285@qq.com
 */

public class HotThemeFragment extends WEFragment {


    @BindView(R.id.rv_hotTheme_list)
    RecyclerView mRvHotThemeList;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;

    private StudyResourceAdapter mAdapter;

    public static HotThemeFragment newInstance() {
        HotThemeFragment fragment = new HotThemeFragment();
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_hot_theme, null, false);
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
        Observable.create(new Observable.OnSubscribe<List<ThemeResourc>>() {
            @Override
            public void call(Subscriber<? super List<ThemeResourc>> subscriber) {
                List<ThemeResourc> list = DataSupport.findAll(ThemeResourc.class);
                subscriber.onNext(list);
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
                .subscribe(new Action1<List<ThemeResourc>>() {
                    @Override
                    public void call(List<ThemeResourc> themeResourcs) {
                        if (mRefresh.isRefreshing()) {
                            mRefresh.setRefreshing(false);
                        }
                        initAdapter(themeResourcs);
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

    private void initAdapter(List<ThemeResourc> themeResourcs) {
        mAdapter = new StudyResourceAdapter(R.layout.item_zhihu_hot, themeResourcs);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.isFirstOnly(false);
        mRvHotThemeList.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRvHotThemeList.setHasFixedSize(false);
        mRvHotThemeList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ThemeResourc> list = adapter.getData();
                ThemeResourc themeResourc = list.get(position);
                int id = themeResourc.getId();
                EventBus.getDefault().post(new StartNextFragment(ZhihuDetailFragment.newInstance(id)));
            }
        });
    }

}