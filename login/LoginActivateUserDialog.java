package com.tv.goin.activities.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.tv.goin.BaseClasses.BaseDialog;
import com.tv.goin.BaseClasses.DialogInterface;
import com.tv.goin.CallbackInterfaces.CallbackActivateUser;
import com.tv.goin.R;
import com.tv.goin.utils.CommonUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nayan on 2/11/17.
 */

public class LoginActivateUserDialog extends BaseDialog {


    private CallbackActivateUser mCallbackActivateUser;

    @BindView(R.id.user_name_tv)
    TextView user_name_tv;

    @BindView(R.id.user_name_et)
    EditText user_name_et;

    @BindView(R.id.save_btn)
    Button save_btn;

    @BindView(R.id.cross_iv)
    ImageView cross_iv;

    @OnClick(R.id.cross_iv)
    void onClickcross_iv() {
        mCallbackActivateUser.onCancelCalled();


        dismiss();
    }

    @OnClick(R.id.save_btn)
    void onClicksave_btn() {

        if (user_name_et.getText().toString().trim().isEmpty()) {
            showMessage(R.string.pleaseenterUserNameText);
            return;
        }

        mCallbackActivateUser.onNameSelected(user_name_et.getText().toString().trim());


        dismiss();
    }

    private static final String TAG = LoginActivateUserDialog.class.getSimpleName();

    @Override
    public void showDialog(String title, String msg1, String msg2, int type, DialogInterface mDialogInterface) {

    }

    public void show(FragmentManager fragmentManager) {

        super.show(fragmentManager, TAG);
    }

    @Override
    protected void setUp(View view) {

    }

    public static LoginActivateUserDialog newInstance(Bundle bundle , CallbackActivateUser mCallbackActivateUser) {
        LoginActivateUserDialog fragment = new LoginActivateUserDialog();
        fragment.setArguments(bundle);
        fragment.mCallbackActivateUser = mCallbackActivateUser;

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.login_activate_user_dialog_layout, container, false);
        ButterKnife.bind(this, mView);
        setFont();

        return mView;
    }

    private void setFont() {
        user_name_tv.setTypeface(CommonUtils.setSemiBoldFont(getActivity()));
        save_btn.setTypeface(CommonUtils.setRegularFont(getActivity()));
        user_name_et.setTypeface(CommonUtils.setRegularFont(getActivity()));
    }
}
