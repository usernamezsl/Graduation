package me.jessyan.mvparms.graduation.mvp.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.CleanUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.UiUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import common.AppComponent;
import common.WEFragment;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.di.component.DaggerSetupComponent;
import me.jessyan.mvparms.graduation.di.module.SetupModule;
import me.jessyan.mvparms.graduation.mvp.contract.SetupContract;
import me.jessyan.mvparms.graduation.mvp.model.entity.SetupEntity;
import me.jessyan.mvparms.graduation.mvp.presenter.SetupPresenter;
import me.jessyan.mvparms.graduation.mvp.ui.DataServer;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.SetupAdapter;

import static android.content.Context.INPUT_METHOD_SERVICE;
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

public class SetupFragment extends WEFragment<SetupPresenter> implements SetupContract.View {


    @BindView(R.id.rv_setup_list)
    RecyclerView mRvSetupList;
    @BindView(R.id.center_title)
    TextView mCenterTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static SetupFragment newInstance() {
        SetupFragment fragment = new SetupFragment();
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerSetupComponent
                .builder()
                .appComponent(appComponent)
                .setupModule(new SetupModule(this))//请将SetupModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_setup_view, null, false);
    }

    //简单ui处理
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
        mCenterTitle.setText("设置");
    }

    /**
     * 处理复杂逻辑操作
     *
     * @param savedInstanceState
     */
    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        List<SetupEntity> setupData = DataServer.getSetupData();
        SetupAdapter setupAdapter = new SetupAdapter(R.layout.item_setup_view, setupData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRvSetupList.setLayoutManager(layoutManager);
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.header_setup_view, null);
        View imgHead = headView.findViewById(R.id.imageView2);
        imgHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(MeizhiFragment.newInstance());
            }
        });
        View footerView = LayoutInflater.from(getContext()).inflate(R.layout.footer_setup_view, null);
        View viewById = footerView.findViewById(R.id.chean_cache);
        final TextView fileSize = (TextView) footerView.findViewById(R.id.tv_file_size);
        final File cacheFile = DataHelper.getCacheFile(_mActivity);
        long dirSize = DataHelper.getDirSize(cacheFile);
        fileSize.setText(dirSize + "");
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("清理缓存数据 T T");
                dialog.setMessage("确定清除???");
                dialog.setCancelable(false);
                dialog.setPositiveButton("清理", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (CleanUtils.cleanCustomCache(cacheFile)) {
                            ToastUtils.showLongToastSafe("清理缓存成功~~");
                            fileSize.setText("0.0M");
                        }
                    }
                });
                dialog.setNegativeButton("等着", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });

        setupAdapter.addHeaderView(headView);
        setupAdapter.addFooterView(footerView);
        mRvSetupList.setAdapter(setupAdapter);
        mRvSetupList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                SetupEntity setupEntity = (SetupEntity) baseQuickAdapter.getData().get(i);
                switch (setupEntity.getTitle()) {
                    case "省流量模式":
                        AlertDialog.Builder dialog = new AlertDialog.Builder(_mActivity);
                        dialog.setTitle("在没网的情况的自动使用缓存数据 ？ ？");
                        dialog.setMessage("是否关注 ? ?");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ToastUtils.showLongToastSafe("开启省流量模式~~");
                            }
                        });
                        dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        dialog.show();
                        break;
                    case "反馈":
                        showDialog();
                        break;
                    case "关于":
                        start(AboutFragment.newInstance());
                        break;
                }
            }
        });
    }

    private void showDialog() {
        final EditText ed_fileNewName;
        //新建重令名编辑窗口
        final AlertDialog mDialog = new AlertDialog.Builder(_mActivity)
                .setCancelable(false).create();
        mDialog.show();
        //弹出菜单窗口
        mDialog.getWindow().setContentView(R.layout.dialog);//加载布局文件

        mDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //得到窗口对象中的textView

        //得到窗口对象中的EditText
        ed_fileNewName = (EditText) mDialog.getWindow().findViewById(R.id.dialog_new_name);


        InputMethodManager im = ((InputMethodManager) _mActivity.getSystemService(INPUT_METHOD_SERVICE));
        im.showSoftInput(ed_fileNewName, 0);

        //确定
        mDialog.getWindow().findViewById(R.id.dialog_ent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = ed_fileNewName.getText().toString().trim();
                mDialog.dismiss();

            }
        });
        //取消
        mDialog.getWindow().findViewById(R.id.dialog_cancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
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