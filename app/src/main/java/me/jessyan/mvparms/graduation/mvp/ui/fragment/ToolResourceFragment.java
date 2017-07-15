package me.jessyan.mvparms.graduation.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.utils.UiUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import common.AppComponent;
import common.WEFragment;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.di.component.DaggerToolResourceComponent;
import me.jessyan.mvparms.graduation.di.module.ToolResourceModule;
import me.jessyan.mvparms.graduation.mvp.contract.ToolResourceContract;
import me.jessyan.mvparms.graduation.mvp.model.entity.GankTechBean;
import me.jessyan.mvparms.graduation.mvp.model.entity.MyStudyCollection;
import me.jessyan.mvparms.graduation.mvp.model.entity.Subject;
import me.jessyan.mvparms.graduation.mvp.presenter.ToolResourcePresenter;
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
 * 作者: 张少林 on 2017/2/28 0028.
 * 邮箱:1083065285@qq.com
 */

public class ToolResourceFragment extends WEFragment<ToolResourcePresenter> implements ToolResourceContract.View {


    @BindView(R.id.nav_left_text)
    TextView mNavLeftText;
    @BindView(R.id.center_title)
    TextView mCenterTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private List<GankTechBean.ResultsBean> mList;
    private GankTeachAdapter mAdapter;

    public static ToolResourceFragment newInstance() {
        ToolResourceFragment fragment = new ToolResourceFragment();
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerToolResourceComponent
                .builder()
                .appComponent(appComponent)
                .toolResourceModule(new ToolResourceModule(this))//请将ToolResourceModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_tool_resource, null, false);
    }

    @Override
    protected void initData() {
        initToolbarMenu(mToolbar);
        mNavLeftText.setVisibility(View.VISIBLE);
        mNavLeftText.setText("Android");
        mCenterTitle.setVisibility(View.GONE);
    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {

        mList = new ArrayList<>();
        mAdapter = new GankTeachAdapter(R.layout.item_zhihu_hot, mList);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.isFirstOnly(false);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.getAndroidData("Android", 20, 0);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                        mSwipeRefresh.setRefreshing(true);
                    }
                });
    }

    @Override
    public void hideLoading() {
        if (mSwipeRefresh.isRefreshing()) {
            Observable.just(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Integer>() {
                        @Override
                        public void call(Integer integer) {
                            mSwipeRefresh.setRefreshing(false);
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
        mRecyclerView.setVisibility(View.VISIBLE);
        mList.clear();
        mList.addAll(resultsBeen);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Subject subject = new Subject();
                List<GankTechBean.ResultsBean> data = adapter.getData();
                GankTechBean.ResultsBean bean = data.get(position);

                String url = bean.getUrl();
                String desc = bean.getDesc();

                subject.setDesc(desc);
                subject.setUrl(url);
                List<Subject> list = DataSupport
                        .where("desc = ?", subject.getDesc())
                        .find(Subject.class);
                if (list.isEmpty()) {
                    subject.saveThrows();
                }

                start(StudyDetailFragment.newInstance(desc,url));
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<GankTechBean.ResultsBean> list = adapter.getData();
                GankTechBean.ResultsBean bean = list.get(position);
                String url = bean.getUrl();
                String desc = bean.getDesc();
                switch (view.getId()) {
                    case R.id.iv_like:
                        MyStudyCollection myStudyCollection = new MyStudyCollection();
                        myStudyCollection.setUrl(url);
                        myStudyCollection.setDesc(desc);
                        List<MyStudyCollection> studyCollections = DataSupport
                                .where("desc = ?", myStudyCollection.getDesc())
                                .find(MyStudyCollection.class);
                        if (studyCollections.isEmpty()) {
                            myStudyCollection.saveThrows();
                        }
                        if (myStudyCollection.isSaved()) {
                            ToastUtils.showLongToastSafe("收藏成功~~");
                        }
                        break;
                }
            }
        });
    }
}