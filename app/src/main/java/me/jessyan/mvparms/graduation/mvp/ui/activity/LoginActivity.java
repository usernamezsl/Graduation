package me.jessyan.mvparms.graduation.mvp.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.jess.arms.utils.UiUtils;

import butterknife.BindView;
import common.AppComponent;
import common.WEActivity;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.di.component.DaggerLoginComponent;
import me.jessyan.mvparms.graduation.di.module.LoginModule;
import me.jessyan.mvparms.graduation.mvp.contract.LoginContract;
import me.jessyan.mvparms.graduation.mvp.presenter.LoginPresenter;

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
 * 作者: 张少林 on 2017/3/6 0006.
 * 邮箱:1083065285@qq.com
 */

public class LoginActivity extends WEActivity<LoginPresenter> implements LoginContract.View {
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_email)
    EditText mInputEmail;
    @BindView(R.id.input_password)
    EditText mInputPassword;
    @BindView(R.id.btn_login)
    AppCompatButton mBtnLogin;
    @BindView(R.id.link_signup)
    TextView mLinkSignup;
    @BindView(R.id.remember_pass)
    CheckBox mRememberPass;
    private ProgressDialog mProgressDialog;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this)) //请将LoginModule()第一个首字母改为小写
                .build()
                .inject(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_view;
    }

    @Override
    protected void initData() {

        mPresenter.setEditText();
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mInputEmail.getText().toString().trim();
                String password = mInputPassword.getText().toString().trim();
//                mPresenter.loginRequest(username, password);
                mPresenter.getUseFromDb(username, password);
            }
        });
        mLinkSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void showLoading() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在登录，请稍后...");
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        mProgressDialog.hide();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ToastUtils.showLongToastSafe(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void loginSuccess() {
        UiUtils.startActivity(new Intent(LoginActivity.this, BottomViewActivity.class));
        killMyself();
    }

    @Override
    public Boolean judgeCheck() {
        return mRememberPass.isChecked();
    }

    @Override
    public void setUsername(String username) {
        mInputPassword.setText(username);
    }

    @Override
    public void setPassword(String password) {
        mInputPassword.setText(password);
    }

    @Override
    public void setChecked() {
        mRememberPass.setChecked(true);
    }
}