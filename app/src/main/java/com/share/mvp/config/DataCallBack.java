package com.share.mvp.config;

/**
 *
 */
public interface DataCallBack<T>
{
    public void onSuccess(T content);

    public void onFailure(String err);
}
