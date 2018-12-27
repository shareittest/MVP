package com.share.mvp.config;


import android.support.multidex.MultiDexApplication;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.share.mvp.util.LogUtil;


/**
 * Created on 2017/11/1.
 */

public class MainApplication extends MultiDexApplication
{
    private static final String TAG = LogUtil.makeLogTag(MainApplication.class);
    private static MainApplication mInstance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        if (mInstance == null)
        {
            mInstance = this;
        }
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static MainApplication getInstance()
    {
        return mInstance;
    }

}
