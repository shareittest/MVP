package com.share.mvp.config;

import android.os.Bundle;
import android.view.View;


import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * Created on 2017/11/1.
 */

public abstract class BaseFragment extends RxFragment
        implements View.OnClickListener
{
    public BaseActivity mContext;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext = getBaseActivity();
    }

    public BaseActivity getBaseActivity()
    {
        return (BaseActivity) this.getActivity();
    }

    public void showProgress()
    {
        mContext.showProgress();
    }

    public void stopProgress()
    {
        mContext.stopProgress();
    }

    public void onViewClick(View v)
    {

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            default:
                onViewClick(v);
                break;

        }
    }

    /**
     * 预加载
     */
    private boolean isPrepared;

    /**
     * 第一次onResume中的调用onVisible避免操作与onFirstVisible操作重复
     */
    private boolean isFirstResume = true;

    @Override
    public void onResume()
    {
        super.onResume();
        if (isFirstResume)
        {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint())
            onVisible();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (getUserVisibleHint())
            onInvisible();
    }

    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            if (isFirstVisible)
            {
                isFirstVisible = false;
                initPrepare();
            }
            else
                onVisible();
        }
        else
        {
            if (isFirstInvisible)
            {
                isFirstInvisible = false;
                onFirstInvisible();
            }
            else
                onFirstInvisible();
        }
    }

    public synchronized void initPrepare()
    {
        if (isPrepared)
            onFirstVisible();
        else
            isPrepared = true;
    }

    /**
     * fragment可见(切换回来或onResume)
     */
    public void onVisible()
    {

    }

    /**
     * fragment不可见(切换掉或onPause)
     */
    public void onInvisible()
    {

    }

    /**
     * 第一次fragment可见（初始化工作）
     */
    public void onFirstVisible()
    {
        
    }


    /**
     * 第一次fragment不可见（不建议在此处理事件）
     */
    public void onFirstInvisible()
    {

    }

}
