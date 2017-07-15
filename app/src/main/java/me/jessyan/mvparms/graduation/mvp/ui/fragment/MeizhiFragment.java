package me.jessyan.mvparms.graduation.mvp.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.github.clans.fab.FloatingActionButton;
import com.jess.arms.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import common.AppComponent;
import common.WEFragment;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.di.component.DaggerMeizhiComponent;
import me.jessyan.mvparms.graduation.di.module.MeizhiModule;
import me.jessyan.mvparms.graduation.mvp.contract.MeizhiContract;
import me.jessyan.mvparms.graduation.mvp.model.entity.GankItemBean;
import me.jessyan.mvparms.graduation.mvp.model.entity.MeizhiWithVideo;
import me.jessyan.mvparms.graduation.mvp.presenter.MeizhiPresenter;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.MeizhiAdapter;
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
 * 作者: 张少林 on 2017/4/21 0021.
 * 邮箱:1083065285@qq.com
 */

public class MeizhiFragment extends WEFragment<MeizhiPresenter> implements MeizhiContract.View {


    @BindView(R.id.nav_left_text)
    TextView mNavLeftText;
    @BindView(R.id.center_title)
    TextView mCenterTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_meizhi_content)
    RecyclerView mRvMeizhiContent;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.sub_fab)
    FloatingActionButton mSubFab;

    private List<GankItemBean.ResultsBean> mList;
    private ProgressDialog mProgressDialog;
    private MeizhiAdapter mMeizhiAdapter;

    public static MeizhiFragment newInstance() {
        MeizhiFragment fragment = new MeizhiFragment();
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerMeizhiComponent
                .builder()
                .appComponent(appComponent)
                .meizhiModule(new MeizhiModule(this))//请将MeizhiModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_meizhi_layout, null, false);
    }

    @Override
    protected void initData() {
        initToolbarMenu(mToolbar);
        int red = Color.parseColor("#FF4500");
        mToolbar.setBackgroundColor(red);
        mNavLeftText.setVisibility(View.VISIBLE);
        mNavLeftText.setText("妹纸");
        mCenterTitle.setVisibility(View.GONE);
    }

    /**
     * 处理复杂操作
     *
     * @param savedInstanceState
     */
    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        mList = new ArrayList<>();
        mMeizhiAdapter = new MeizhiAdapter(R.layout.item_girl_view, mList);
        mMeizhiAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mMeizhiAdapter.isFirstOnly(false);
        mRvMeizhiContent.setVisibility(View.INVISIBLE);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRvMeizhiContent.setLayoutManager(staggeredGridLayoutManager);
        mRvMeizhiContent.setHasFixedSize(true);
        mRvMeizhiContent.setAdapter(mMeizhiAdapter);
//        mPresenter.requestMeizhi(100);
        mPresenter.getMeizhi();
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                mPresenter.requestMeizhi(100);
                mPresenter.getMeizhi();
            }
        });
        mRvMeizhiContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (Math.abs(dy) > 5) {
                    if (dy > 0) {
                        mSubFab.hide(true);
                    } else {
                        mSubFab.show(true);
                    }
                }
            }
        });
        mRvMeizhiContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<GankItemBean.ResultsBean> list = adapter.getData();
                GankItemBean.ResultsBean resultsBean = list.get(position);
                String id = resultsBean.get_id();
                String url = resultsBean.getUrl();
                start(GirlDetailFragment.newInstance(id, url));
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
        mRefresh.setRefreshing(false);
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
    public void showContent(GankItemBean gankItemBean) {
        if (mRefresh.isRefreshing()) {
            mRefresh.setRefreshing(false);
        }
        mRvMeizhiContent.setVisibility(View.VISIBLE);
        mList.clear();
        mList.addAll(gankItemBean.getResults());
        mMeizhiAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMeizhiWithDesc(List<MeizhiWithVideo> meizhiWithVideos) {

    }
}