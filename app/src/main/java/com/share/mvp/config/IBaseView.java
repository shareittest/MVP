package com.share.mvp.config;

/**
 */
public interface IBaseView
{
    /**
     * 显示进度条
     */
    void showLoading();

    /**
     * 关闭进度条
     */
    void hideLoading();

    /**
     * 提示信息
     *
     * @param tips
     */
    void showTips(String tips);

    /**
     * token 失效
     *
     * @param tips
     */
    void showTokenNotice(String tips);

}
