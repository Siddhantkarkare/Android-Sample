package com.tv.goin.activities.login;

import com.google.gson.JsonElement;
import com.tv.goin.BaseClasses.BaseInteractor;
import com.tv.goin.data.network.ApiHelper;
import com.tv.goin.data.network.model.Login.LoginRequest;
import com.tv.goin.data.prefs.PreferencesHelper;

import javax.inject.Inject;

import retrofit2.Call;

/**
 * Created by Shoeb on 26/7/17.
 */

public class LoginInteractor extends BaseInteractor implements LoginMvpInteractor {

    @Inject
    public LoginInteractor(PreferencesHelper preferencesHelper,
                           ApiHelper apiHelper) {
        super(preferencesHelper, apiHelper);
    }


    @Override
    public Call<JsonElement> doServerLoginApiCall(LoginRequest request) {
        return getApiHelper().doServerLoginApiCall(request);
    }
}
