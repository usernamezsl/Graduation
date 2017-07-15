package me.jessyan.mvparms.graduation.mvp.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.utils.UiUtils;

import butterknife.BindView;
import common.AppComponent;
import common.WEActivity;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.di.component.DaggerSignupComponent;
import me.jessyan.mvparms.graduation.di.module.SignupModule;
import me.jessyan.mvparms.graduation.mvp.contract.SignupContract;
import me.jessyan.mvparms.graduation.mvp.presenter.SignupPresenter;

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

public class SignupActivity extends WEActivity<SignupPresenter> implements SignupContract.View {


    @BindView(R.id.input_name)
    EditText mInputName;
    @BindView(R.id.input_password)
    EditText mInputPassword;
    @BindView(R.id.btn_signup)
    AppCompatButton mBtnSignup;
    @BindView(R.id.link_login)
    TextView mLinkLogin;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSignupComponent
                .builder()
                .appComponent(appComponent)
                .signupModule(new SignupModule(this)) //请将SignupModule()第一个首字母改为小写
                .build()
                .inject(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_signup_view;
    }

    @Override
    protected void initData() {
        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mInputName.getText().toString();
                String password = mInputPassword.getText().toString();
                mPresenter.signup(userName, password);
            }
        });
        mLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                / Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void onSignupSuccess() {
        mBtnSignup.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        mBtnSignup.setEnabled(true);
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
        finish();
    }



}