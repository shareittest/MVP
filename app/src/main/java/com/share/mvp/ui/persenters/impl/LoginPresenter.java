package com.share.mvp.ui.persenters.impl;

import android.content.Context;

import com.share.mvp.api.ApiManager;
import com.share.mvp.config.BasePresenter;
import com.share.mvp.config.DataCallBack;
import com.share.mvp.config.IBaseView;
import com.share.mvp.model.Response;
import com.share.mvp.ui.persenters.ILoginPresenter;
import com.share.mvp.ui.view.ILoginView;
import com.share.mvp.util.LogUtil;
import com.trello.rxlifecycle.LifecycleTransformer;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * login
 */

public class LoginPresenter extends BasePresenter
        implements ILoginPresenter
{
    private static final String TAG = LogUtil.makeLogTag(LoginPresenter.class);
    Context context;
    ILoginView iLoginView;

    public LoginPresenter(Context context, IBaseView iBaseView, LifecycleTransformer<Response> lifecycle)
    {
        super(context, iBaseView, lifecycle);
        this.context = context;
        this.iLoginView = (ILoginView) iBaseView;
    }

    @Override
    public void login(final String userName, String password)
    {
        Map<String, String> map = new HashMap<>();
        map.put("username", userName);
        map.put("password", password);

        Observable<Response> observable = ApiManager.getInstance()
                .getService().login(format(formatMapNoToken(map)));

        requestData(observable, new DataCallBack<String>()
        {
            @Override
            public void onSuccess(String content)
            {
                LogUtil.i(TAG, getResultMethod("login") + content);
                iLoginView.onLoginSuccess(content);
            }

            @Override
            public void onFailure(String err)
            {

            }
        });
    }

    @Override
    public void versionAndroid()
    {
        Observable<Response> observable = ApiManager.getInstance()
                .getService().versionAndroid();

        requestData(observable, new DataCallBack<String>()
        {
            @Override
            public void onSuccess(String content)
            {
                LogUtil.i(TAG, getResultMethod("versionAndroid") + content);
                iLoginView.onVersionAndroidSuccess(content);
            }

            @Override
            public void onFailure(String err)
            {

            }
        });
    }
}
