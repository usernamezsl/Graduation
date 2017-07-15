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
import me.jessyan.mvparms.graduation.di.component.DaggerMajorComponent;
import me.jessyan.mvparms.graduation.di.module.MajorModule;
import me.jessyan.mvparms.graduation.mvp.contract.MajorContract;
import me.jessyan.mvparms.graduation.mvp.model.entity.MyCollectorOne;
import me.jessyan.mvparms.graduation.mvp.model.entity.SectionChildListBean;
import me.jessyan.mvparms.graduation.mvp.model.entity.ThemeResourc;
import me.jessyan.mvparms.graduation.mvp.presenter.MajorPresenter;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.SectionChildAdapter;
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
 * 作者: 张少林 on 2017/3/23 0023.
 * 邮箱:1083065285@qq.com
 */

public class MajorFragment extends WEFragment<MajorPresenter> implements MajorContract.View {


    @BindView(R.id.nav_left_text)
    TextView mNavLeftText;
    @BindView(R.id.center_title)
    TextView mCenterTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_major_content)
    RecyclerView mRvMajorContent;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;

    private List<SectionChildListBean.StoriesBean> mList;
    private SectionChildAdapter mSectionChildAdapter;

    public static MajorFragment newInstance() {
        MajorFragment fragment = new MajorFragment();
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerMajorComponent
                .builder()
                .appComponent(appComponent)
                .majorModule(new MajorModule(this))//请将MajorModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_major_view, null, false);
    }

    @Override
    protected void initData() {
        initToolbarMenu(mToolbar);
        mNavLeftText.setVisibility(View.VISIBLE);
        mNavLeftText.setText("好工作");
        mCenterTitle.setVisibility(View.GONE);
    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        mList = new ArrayList<>();
        mSectionChildAdapter = new SectionChildAdapter(R.layout.item_zhihu_hot, mList);
        mSectionChildAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mSectionChildAdapter.isFirstOnly(false);
        mRvMajorContent.setVisibility(View.INVISIBLE);
        mRvMajorContent.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRvMajorContent.setHasFixedSize(true);
        mRvMajorContent.setAdapter(mSectionChildAdapter);
        mPresenter.getMajorData(36);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getMajorData(36);
            }
        });
    }

    @Override
    public void showContent(SectionChildListBean sectionChildListBean) {
        if (mRefresh.isRefreshing()) {
            mRefresh.setRefreshing(false);
        }
        mRvMajorContent.setVisibility(View.VISIBLE);
        mList.clear();
        mList.addAll(sectionChildListBean.getStories());
        mSectionChildAdapter.notifyDataSetChanged();
        mSectionChildAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<SectionChildListBean.StoriesBean> data = adapter.getData();
                SectionChildListBean.StoriesBean storiesBean = data.get(position);

                ThemeResourc themeResourc = new ThemeResourc();
                themeResourc.setId(storiesBean.getId());
                themeResourc.setImage(storiesBean.getImages().get(0));
                themeResourc.setTitle(storiesBean.getTitle());
                List<ThemeResourc> list = DataSupport
                        .where("title = ?",themeResourc.getTitle())
                        .find(ThemeResourc.class);
                if (list.isEmpty()) {
                    themeResourc.save();
                }
                int id = storiesBean.getId();
                start(ZhihuDetailFragment.newInstance(id));
            }
        });
        mSectionChildAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<SectionChildListBean.StoriesBean> data = adapter.getData();
                SectionChildListBean.StoriesBean storiesBean = data.get(position);
                String title = storiesBean.getTitle();
                String url = storiesBean.getImages().get(0);
                int id = storiesBean.getId();
                MyCollectorOne myCollectorOne = new MyCollectorOne();
                myCollectorOne.setTitle(title);
                myCollectorOne.setUrl(url);
                myCollectorOne.setId(id);
                List<MyCollectorOne> list = DataSupport
                        .where("title = ?", title)
                        .find(MyCollectorOne.class);
                if (list.isEmpty()) {
                    myCollectorOne.saveThrows();
                }
                if (myCollectorOne.isSaved()) {
                    ToastUtils.showLongToastSafe("添加收藏成功~~");
                }
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
            mRefresh.setRefreshing(false);
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        if (mRefresh.isRefreshing()) {
            mRefresh.setRefreshing(false);
        }
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

}