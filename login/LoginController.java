package com.tv.goin.activities.login;

import android.content.Intent;
import android.view.View;

import com.tv.goin.R;
import com.tv.goin.activities.forgotPass.ForgotPasswordActivity;

/**
 * Created by Shoeb on 21/7/17.
 */

public class LoginController implements View.OnClickListener {


    private final LoginActivity activity;

    public LoginController(LoginActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                activity.finish();
                break;
            case R.id.forgot_pass_tv:
                Intent mIntent = new Intent(activity, ForgotPasswordActivity.class);
                activity.startActivity(mIntent);
                break;
            default:
                break;
        }
    }
}
