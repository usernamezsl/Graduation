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
import me.jessyan.mvparms.graduation.mvp.model.entity.Subject;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.HotThemeAdapter;
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

public class StudyFragment extends WEFragment {


    @BindView(R.id.rv_study_list)
    RecyclerView mRvStudyList;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;

    private HotThemeAdapter mAdapter;

    public static StudyFragment newInstance() {
        StudyFragment fragment = new StudyFragment();
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_study_view, null, false);
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
        Observable.create(new Observable.OnSubscribe<List<Subject>>() {
            @Override
            public void call(Subscriber<? super List<Subject>> subscriber) {
                List<Subject> list = DataSupport.findAll(Subject.class);
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
                .subscribe(new Action1<List<Subject>>() {
                    @Override
                    public void call(List<Subject> subjects) {
                        if (mRefresh.isRefreshing()) {
                            mRefresh.setRefreshing(false);
                        }
                        initAdapter(subjects);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mRefresh.isRefreshing()) {
                            mRefresh.setRefreshing(false);
                        }
                        ToastUtils.showLongToastSafe("数据库查询失败~~");
                    }
                });
    }

    private void initAdapter(List<Subject> list) {
        mAdapter = new HotThemeAdapter(R.layout.item_zhihu_hot, list);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.isFirstOnly(false);
        mRvStudyList.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRvStudyList.setHasFixedSize(false);
        mRvStudyList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<Subject> subjectList = adapter.getData();
                String url = subjectList.get(position).getUrl();
                String desc = subjectList.get(position).getDesc();
                EventBus.getDefault().post(new StartNextFragment(StudyDetailFragment.newInstance(desc,url)));
            }
        });
    }
}