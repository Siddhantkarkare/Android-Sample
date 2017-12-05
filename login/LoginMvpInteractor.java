package com.tv.goin.activities.login;

import com.google.gson.JsonElement;
import com.tv.goin.BaseClasses.MvpInteractor;
import com.tv.goin.data.network.model.Login.LoginRequest;

import retrofit2.Call;

/**
 * Created by Shoeb on 26/7/17.
 */

public interface LoginMvpInteractor extends MvpInteractor{

    Call<JsonElement> doServerLoginApiCall(
            LoginRequest request);
}
