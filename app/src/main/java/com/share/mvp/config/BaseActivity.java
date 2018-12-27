package com.share.mvp.config;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;

import com.share.mvp.R;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created on 2017/11/1.
 */

public abstract class BaseActivity extends RxAppCompatActivity
        implements View.OnClickListener
{
    private Dialog mProgressDialog;

    public void showProgress()
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.custom_loading, null);
        if (mProgressDialog == null)
        {
            mProgressDialog = new Dialog(this, R.style.LoadingDialog);
            mProgressDialog.setContentView(v);
            mProgressDialog.setCancelable(true);
        }
        mProgressDialog.setCanceledOnTouchOutside(false);
        if (!isFinishing())
            mProgressDialog.show();
    }

    public void stopProgress()
    {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
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
}
