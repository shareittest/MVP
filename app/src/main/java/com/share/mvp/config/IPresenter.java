package com.share.mvp.config;

import rx.Subscription;

/**
 *
 */
public interface IPresenter
{

    void addSubscription(Subscription s);

    void unSubscribe();

}
