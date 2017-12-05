package com.tv.goin.activities.login;

import com.tv.goin.BaseClasses.MvpPresenter;
import com.tv.goin.di.PerActivity;

/**
 * Created by Shoeb on 26/7/17.
 */
@PerActivity
public interface LoginMvpPresenter<V extends LoginMvpView,
        I extends LoginMvpInteractor> extends MvpPresenter<V,I> {
    void doLogin(String email,String password,String deviceId);
    void onForgotClick();

}
