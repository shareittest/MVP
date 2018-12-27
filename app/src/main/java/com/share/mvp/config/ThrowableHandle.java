package com.share.mvp.config;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.share.mvp.util.LogUtil;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;

/**
 *
 */
public class ThrowableHandle
{

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static Throwable handleException(java.lang.Throwable e)
    {
        Throwable ex = new Throwable(e);
        LogUtil.e("ThrowableHandle", "[throwable]" + ex.toString());
        if (e instanceof HttpException)
        {
            HttpException httpException = (HttpException) e;
            switch (httpException.code())
            {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.message = "网络错误";//简单统一显示
                    break;
            }
            return ex;
        }
        else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException)
        {
            ex.message = "解析错误";
            return ex;
        }
        else if (e instanceof ConnectException)
        {
            ex.message = "连接失败";
            return ex;
        }
        else if (e instanceof SocketTimeoutException)
        {
            ex.message = "连接超时";
            return ex;
        }
        else if (e instanceof javax.net.ssl.SSLHandshakeException)
        {
            ex.message = "证书验证失败";
            return ex;
        }
        else
        {
            ex.message = "未知错误";
            return ex;
        }
    }


    public static class Throwable extends Exception
    {
        public String message;

        public Throwable(java.lang.Throwable throwable)
        {
            super(throwable);
        }
    }
}