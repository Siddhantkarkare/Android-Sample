package com.tv.goin.activities.login;

//import com.androidnetworking.error.ANError;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.tv.goin.BaseClasses.BasePresenter;
import com.tv.goin.R;
import com.tv.goin.data.modals.GoinUserModals.GoinUsersModal;
import com.tv.goin.data.network.ApiHelper;
import com.tv.goin.data.network.model.Login.LoginRequest;
import com.tv.goin.data.network.retrofit.ApiClient;
import com.tv.goin.utils.CommonUtils;
import com.tv.goin.utils.Logger;
import com.tv.goin.utils.MyPreference;
import com.tv.goin.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shoeb on 26/7/17.
 */

public class LoginPresenter<V extends LoginMvpView, I extends LoginMvpInteractor>
        extends BasePresenter<V, I> implements LoginMvpPresenter<V, I> {


    private static final String TAG = LoginPresenter.class.getSimpleName();

    @Inject
    public LoginPresenter(I mvpInteractor, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);
    }

    @Override
    public void doLogin(String email, String password, String deviceId) {
//        getMvpView().openMainScreen();
        //validate email and password
        if (email == null || email.isEmpty()) {
            getMvpView().onError(R.string.empty_email);
            return;
        }
        if (!CommonUtils.isEmailValid(email)) {
            getMvpView().onError(R.string.invalid_email);
            return;
        }
        if (password == null || password.isEmpty()) {
            getMvpView().onError(R.string.empty_password);
            return;
        }

        if (!getMvpView().isNetworkConnected()){

            getMvpView().onError(R.string.pleaseCheckInternetConnectionText);
            return;

        }
        getMvpView().showLoading();
        getMvpView().hideKeyboard();
        /*ApiHelper mApiHelper = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiHelper.class);*/
        ApiHelper mApiHelper = ApiClient.getClient().create(ApiHelper.class);

        LoginRequest mLoginRequest = new LoginRequest();
        mLoginRequest.setDeviceId(deviceId);
        mLoginRequest.setPassword(password);
        mLoginRequest.setUsername(email);
        Gson gson = new GsonBuilder().create();
        Logger.logsError(TAG, "JSON requestModal : " + gson.toJson(mLoginRequest));
        mApiHelper.doServerLoginApiCall(mLoginRequest).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Logger.logsError(TAG,"onResponse Called : "  + response.toString()  + " \n call : "+call.request()  + "\n " +
                        response.body() + "\n " + response.errorBody() + "\n " +response.code());
                getMvpView().hideLoading();
                if (response.code()== 200){
                    Logger.logsError(TAG,"response  Header auth-token : "  + response.headers().get("auth-token"));

                    Gson mGson1  = new Gson();
                    GoinUsersModal mGoinUserModalMain = mGson1.fromJson(response.body().toString().trim(),
                            GoinUsersModal.class);
                    MyPreference.setUserData(mGoinUserModalMain);

                    MyPreference.saveUserAuthToken(response.headers().get("auth-token"));
                    Logger.logsError(TAG,"response  BODY : "  + response.body().toString());

                    getMvpView().openMainScreen();

                } else{
                    getMvpView().onError(R.string.loginFailText);

                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Logger.logsError(TAG,"onFailure Called");
                t.printStackTrace();
                getMvpView().hideLoading();
                getMvpView().onError(R.string.some_error);

            }
        });

        /*getCompositeDisposable().add(
                mApiHelper.doServerLoginApiCall(mLoginRequest)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribeWith(new DisposableObserver<LoginResponse>() {


                            @Override
                            public void onNext(LoginResponse loginResponse) {
                                Logger.logsError(TAG, "onNext Called :  loginResponse " + loginResponse);
                                getMvpView().hideLoading();
                                getMvpView().openMainScreen();
                            }

                            @Override
                            public void onError(Throwable t) {
                                Logger.logsError(TAG, "onError Called : " + ((HttpException) t).code());
                                t.printStackTrace();
                                getMvpView().hideLoading();


                            }

                            @Override
                            public void onComplete() {
                                Logger.logsError(TAG, "onComplete Called ");
                                getMvpView().hideLoading();
                                getMvpView().openMainScreen();

                            }
                        }));

*/
    }

    @Override
    public void onForgotClick() {
        getMvpView().openForgotPassword();
    }
}
