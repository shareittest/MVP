package com.share.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.share.mvp.R;
import com.share.mvp.config.BaseActivity;
import com.share.mvp.model.Response;
import com.share.mvp.ui.persenters.impl.LoginPresenter;
import com.share.mvp.ui.view.ILoginView;
import com.share.mvp.util.LogUtil;
import com.share.mvp.util.ToastUtils;


/**
 * Created on 2017/11/2.
 */
public class LoginActivity extends BaseActivity
        implements ILoginView
{
    private static final String TAG = LogUtil.makeLogTag(LoginActivity.class);
    private EditText etAccount, etPwd;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
        initView();
        initData();

    }

    private void initView()
    {
        etAccount = findViewById(R.id.etAccount);
        etPwd = findViewById(R.id.etPwd);
        findViewById(R.id.btnLogin).setOnClickListener(this);
    }

    private void initialize()
    {
        loginPresenter = new LoginPresenter(getApplicationContext(), this, this.<Response>bindToLifecycle());
    }


    private void initData()
    {
        loginPresenter.versionAndroid();
    }

    private boolean check()
    {
        if (TextUtils.isEmpty(etAccount.getText().toString().trim()))
        {
            ToastUtils.showShort("账号不能为空！");
            return false;
        }
        if (TextUtils.isEmpty(etPwd.getText().toString().trim()))
        {
            ToastUtils.showShort("密码不能为空！");
            return false;
        }
        return true;
    }

    @Override
    public void showLoading()
    {
        showProgress();
    }

    @Override
    public void hideLoading()
    {
        stopProgress();
    }

    @Override
    public void showTips(String tips)
    {
        if (!TextUtils.isEmpty(tips) && !tips.equals("获取成功"))
            ToastUtils.showShort(tips);
    }

    @Override
    public void showTokenNotice(String tips)
    {

    }

    @Override
    public void onLoginSuccess(String response)
    {
        LogUtil.e(TAG, "onLoginSuccess==>" + response);
    }

    @Override
    public void onVersionAndroidSuccess(String response)
    {
        LogUtil.e(TAG, "onVersionAndroidSuccess==>" + response);
    }

    @Override
    public void onViewClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnLogin:
                if (check())
                    loginPresenter.login(etAccount.getText().toString(), etPwd.getText().toString());
                break;
        }
    }
}
