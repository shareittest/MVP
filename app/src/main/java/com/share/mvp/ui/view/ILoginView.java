package com.share.mvp.ui.view;


import com.share.mvp.config.IBaseView;

/**

 */
public interface ILoginView extends IBaseView
{
    void onLoginSuccess(String response);

    void onVersionAndroidSuccess(String response);
}
