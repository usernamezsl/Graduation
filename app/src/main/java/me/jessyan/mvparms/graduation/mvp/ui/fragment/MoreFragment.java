package me.jessyan.mvparms.graduation.mvp.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

import butterknife.BindView;
import common.AppComponent;
import common.WEApplication;
import common.WEFragment;
import de.hdodenhof.circleimageview.CircleImageView;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.di.component.DaggerMoreComponent;
import me.jessyan.mvparms.graduation.di.module.MoreModule;
import me.jessyan.mvparms.graduation.mvp.contract.MoreContract;
import me.jessyan.mvparms.graduation.mvp.model.entity.MoreEntity;
import me.jessyan.mvparms.graduation.mvp.model.entity.MyUser;
import me.jessyan.mvparms.graduation.mvp.presenter.LoginPresenter;
import me.jessyan.mvparms.graduation.mvp.presenter.MorePresenter;
import me.jessyan.mvparms.graduation.mvp.ui.DataServer;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.MoreAdapter;
import me.jessyan.mvparms.graduation.mvp.ui.event.StartBrotherEvent;
import me.jessyan.mvparms.graduation.mvp.ui.fragment.article.MyArticleFragment;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static me.jessyan.mvparms.graduation.R.id.toolbar;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * 作者: 张少林 on 2017/2/23 0023.
 * 邮箱:1083065285@qq.com
 */

public class MoreFragment extends WEFragment<MorePresenter> implements MoreContract.View {

    private static final String TAG = "MoreFragment";

    @BindView(toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_more)
    RecyclerView mRvMore;
    @BindView(R.id.center_title)
    TextView mCenterTitle;
    @BindView(R.id.nav_left_text)
    TextView mNavLeftText;
    private CircleImageView mImageView;
    private TextView mUsername;
    private View mHeaderView;
    private View mFooterView;
    private View mNightView;
    private TextView mNight;
    private TextView mSetup;
    private View mSetupView;


    public static MoreFragment newInstance() {
        MoreFragment fragment = new MoreFragment();
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerMoreComponent
                .builder()
                .appComponent(appComponent)
                .moreModule(new MoreModule(this))//请将MoreModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_more_view, null, false);
    }

    /**
     * 加载头布局
     *
     * @param
     * @return
     */
    private View getHeaderView() {
        View view = LayoutInflater.from(mWeApplication).inflate(R.layout.header_more_view, ((ViewGroup) mRvMore.getParent()), false);
        return view;
    }

    /**
     * 加载尾布局
     *
     * @return
     */
    private View getFooterView() {
        View view = LayoutInflater.from(mWeApplication).inflate(R.layout.footer_more_view, ((ViewGroup) mRvMore.getParent()), false);
        return view;

    }

    /**
     * 沉浸式状态栏
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            _mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            _mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void initData() {
//        initState();
        initToolbarMenu(mToolbar);
        mNavLeftText.setVisibility(View.VISIBLE);
        mNavLeftText.setText("更多");
        mCenterTitle.setVisibility(View.GONE);
    }

    @Subscriber
    public void showUser(MyUser myUser) {
        ((WEApplication) BaseApplication.getInstance())
                .getAppComponent()
                .imageLoader()
                .loadImage(_mActivity, GlideImageConfig.builder()
                        .url(myUser.getAvatar())
                        .imageView(mImageView)
                        .placeholder(R.drawable.android)
                        .errorPic(R.drawable.android)
                        .build());
        mUsername.setText(myUser.getUsername());
    }

    /**
     * 处理复杂操作
     *
     * @param savedInstanceState
     */
    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        ConfigHeaderView();
        ConfigFooterView();
        configRecyclerView();
    }

    /**
     * recyclerView
     */
    private void configRecyclerView() {
        final List<MoreEntity> moreEntities = DataServer.getMoreFragmentData();
        final MoreAdapter moreAdapter = new MoreAdapter(R.layout.item_more_view, moreEntities);
        mRvMore.setLayoutManager(new LinearLayoutManager(getContext()));
        moreAdapter.addHeaderView(mHeaderView);
        moreAdapter.addFooterView(mFooterView);
        mRvMore.setAdapter(moreAdapter);
        mRvMore.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                MoreEntity moreEntity = (MoreEntity) baseQuickAdapter.getData().get(i);
                switch (moreEntity.getTitle()) {
                    case "我的关注":
                        EventBus.getDefault().post(new StartBrotherEvent(MyFocusFragment.newInstance()));
                        break;
                    case "我的收藏":
                        EventBus.getDefault().post(new StartBrotherEvent(MyCollectionFragment.newInstance()));
                        break;
                    case "我的草稿":
                        EventBus.getDefault().post(new StartBrotherEvent(MyDraughtFragment.newInstance()));
                        break;
                    case "浏览记录":
                        EventBus.getDefault().post(new StartBrotherEvent(BrowsingHistoryFragment.newInstance()));
                        break;
                    case "我的文章":
                        EventBus.getDefault().post(new StartBrotherEvent(MyArticleFragment.newInstance()));
                        break;
                }

            }
        });
        mRvMore.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Math.abs(dy) > 5) {
                    if (dy > 0) {
//                        EventBus.getDefault().post(new HideBottomBar("更多页面隐藏"));
                    } else {
//                        EventBus.getDefault().post(new ShowBottomBar("更多页面显示"));
                    }
                }
            }
        });
    }

    /**
     * 尾布局配置
     */
    private void ConfigFooterView() {
        mFooterView = getFooterView();
        mNightView = mFooterView.findViewById(R.id.night_model);
        mNight = (TextView) mFooterView.findViewById(R.id.tv_night);
        mSetup = (TextView) mFooterView.findViewById(R.id.tv_setup);
        mNight.setText("夜间模式");
        mSetup.setText("设置");
        mNightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showLongToastSafe("夜间模式");
            }
        });
        mSetupView = mFooterView.findViewById(R.id.setup);
        mSetupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartBrotherEvent(SetupFragment.newInstance()));
            }
        });
    }

    /**
     * 头布局配置
     */
    private void ConfigHeaderView() {
        mHeaderView = getHeaderView();
        LinearLayout layout = (LinearLayout) mHeaderView.findViewById(R.id.loginandregist);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartBrotherEvent(HomePageFragment.newInstance()));
            }
        });
        mImageView = (CircleImageView) layout.findViewById(R.id.head_iamge);
        mUsername = (TextView) layout.findViewById(R.id.nickname);
        Intent intent = _mActivity.getIntent();
        String name = intent.getStringExtra(LoginPresenter.NAME);
        mUsername.setText(name);
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
}