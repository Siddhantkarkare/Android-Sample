package com.tv.goin.activities.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.tv.goin.BaseClasses.BaseActivity;
import com.tv.goin.CallbackInterfaces.CallbackActivateUser;
import com.tv.goin.R;
import com.tv.goin.activities.forgotPass.ForgotPasswordActivity;
import com.tv.goin.activities.signup.otp.SignUpOtpActivity;
import com.tv.goin.data.network.model.SignUpModals.GoinUserRequest;
import com.tv.goin.tabs.MainTabActivity;
import com.tv.goin.utils.CommonUtils;
import com.tv.goin.utils.Constant;
import com.tv.goin.utils.MyPreference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shoeb on 21/7/17.
 */

public class LoginActivity extends BaseActivity implements LoginMvpView {


    @Inject
    LoginMvpPresenter<LoginMvpView, LoginMvpInteractor> mPresenter;

    @BindView(R.id.back_iv)
    ImageView back_iv;

    @OnClick(R.id.back_iv)
    void onClickback_iv(View v) {
        finishCurrentActivity();
    }

    @BindView(R.id.forgot_pass_tv)
    TextView forgot_pass_tv;

    @BindView(R.id.activate_acc_tv)
    TextView activate_acc_tv;

    @OnClick(R.id.activate_acc_tv)
    void onClickActivateAcc(View v){

        openLoginActivateUserDialog();
    }

    private void openLoginActivateUserDialog() {
        Bundle mBundle = new Bundle();

        LoginActivateUserDialog.newInstance(mBundle, new CallbackActivateUser() {
            @Override
            public void onNameSelected(String mName) {
                if (!mName.isEmpty()){
                    Intent mIntent = SignUpOtpActivity.getStartIntent(LoginActivity.this);
                    GoinUserRequest mSignUpResponseModalMain = new GoinUserRequest();
                    mSignUpResponseModalMain.setUsername(mName);
                    mIntent.putExtra(Constant.GOIN_USER_REQUEST, mSignUpResponseModalMain);
                    MyPreference.setUserRegistrationRequest(mSignUpResponseModalMain);
                    MyPreference.setUserUnderRegistration(true);
                    startActivity(mIntent);
                    startActivitySideWiseAnimation();
                }

            }

            @Override
            public void onCancelCalled() {

            }
        }).show(getSupportFragmentManager());


    }

    @BindView(R.id.login_rl)
    RelativeLayout login_rl;

    @BindView(R.id.email_et)
    EditText email_et;
    @BindView(R.id.password_et)
    EditText password_et;

    @BindView(R.id.login_tv)
    TextView login_tv;

    @OnClick(R.id.login_rl)
    void onLoginClick(View v) {
        mPresenter.doLogin(email_et.getText().toString().trim(), password_et.getText().toString().trim(),
                CommonUtils.getOneSignalToken());
    }


    @OnClick(R.id.forgot_pass_tv)
    void onForgotClick(View v) {
        mPresenter.onForgotClick();

    }


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(LoginActivity.this);
        setFonts();

    }

    private void setFonts() {
        activate_acc_tv.setTypeface(CommonUtils.setSemiBoldFont(this));
        forgot_pass_tv.setTypeface(CommonUtils.setRegularFont(this));
        login_tv.setTypeface(CommonUtils.setRegularFont(this));
        email_et.setTypeface(CommonUtils.setRegularFont(this));
        password_et.setTypeface(CommonUtils.setRegularFont(this));
    }

    @Override
    protected void setUp() {

    }


    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void openMainScreen() {

        Intent intent = MainTabActivity.getStartIntent(LoginActivity.this);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MyPreference.setUserUnderRegistration(false);
        startActivity(intent);
        finishCurrentActivity();
    }

    @Override
    public void openForgotPassword() {

        Intent intent = ForgotPasswordActivity.getStartIntent(LoginActivity.this);
        startActivity(intent);
        startActivitySideWiseAnimation();

    }
}
