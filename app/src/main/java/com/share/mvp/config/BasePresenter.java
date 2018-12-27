package com.share.mvp.config;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.share.mvp.model.Response;
import com.share.mvp.util.LogUtil;
import com.trello.rxlifecycle.LifecycleTransformer;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**

 */
public class BasePresenter implements IPresenter
{
    private static final String TAG = "BasePresenter";
    private final IBaseView iBaseView;
    private final Context context;
    private CompositeSubscription mCompositeSubscription;
    private LifecycleTransformer<Response> lifecycleTransformer;
    private Gson gson = new GsonBuilder().
            registerTypeAdapter(Double.class, new JsonSerializer<Double>()
            {

                @Override
                public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context)
                {
                    if (src == src.longValue())
                        return new JsonPrimitive(src.longValue());
                    return new JsonPrimitive(src);
                }
            }).create();

    public BasePresenter(Context context, IBaseView iBaseView, LifecycleTransformer<Response> lifecycle)
    {
        this.iBaseView = iBaseView;
        this.context = context;
        this.lifecycleTransformer = lifecycle;
    }

    @Override
    public void addSubscription(Subscription s)
    {
        if (this.mCompositeSubscription == null)
        {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    public void unSubscribe()
    {
        if (this.mCompositeSubscription != null)
        {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    public String getCallMethodName()
    {
        String method = new Throwable().getStackTrace()[2].getMethodName();// 获取调用方法名
        return "[" + method + "方法]-[请求参数]params-->";
    }

    public String getResultMethod(String method)
    {
        return "[" + method + "方法]-[返回结果]result-->";
    }

    public RequestBody format(String s)
    {
        LogUtil.i(TAG, getCallMethodName() + s);
        return RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8"), s);
    }

    /**
     * application/x-www-form-urlencoded
     *
     * @param map
     * @return
     */
    public String formatMap(Map<String, String> map)
    {
        // StringBuilder是用来组拼请求地址和参数
        StringBuilder sb = new StringBuilder();
        if (map != null && map.size() != 0)
        {
            for (Map.Entry<String, String> entry : map.entrySet())
            {
                // 如果请求参数中有中文，需要进行URLEncoder编码 gbk/utf8
                try
                {
                    sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8"));
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 登录参数
     *
     * @param map
     * @return
     */
    public String formatMapNoToken(Map<String, String> map)
    {
        // StringBuilder是用来组拼请求地址和参数
        StringBuilder sb = new StringBuilder();
        if (map != null && map.size() != 0)
        {
            for (Map.Entry<String, String> entry : map.entrySet())
            {
                // 如果请求参数中有中文，需要进行URLEncoder编码 gbk/utf8
                try
                {
                    sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8"));
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }


    public void requestData(Observable<Response> observable, final DataCallBack callBack)
    {
        iBaseView.showLoading();
        LogUtil.e(TAG, "request-->[observable]");
        Subscription subscription = observable
                .compose(lifecycleTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response>()
                {
                    @Override
                    public void onCompleted()
                    {
                        LogUtil.i(TAG, "onCompleted->");
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        LogUtil.i(TAG, "Throwable--->" + e.toString());
                        ThrowableHandle.Throwable throwable = ThrowableHandle.handleException(e);
                        iBaseView.hideLoading();
                        if (!TextUtils.isEmpty(throwable.message))
                        {
                            callBack.onFailure(throwable.message);
                            iBaseView.showTips(throwable.message);
                        }
                    }

                    @Override
                    public void onNext(Response s)
                    {
                        LogUtil.i(TAG, "[返回结果]--->" + s.toString());
                        if (s.isSuccess())
                        {
                            callBack.onSuccess(gson.toJson(s.getResult()));
                        }
                        else if (s.isToken())
                        {
                            if (!TextUtils.isEmpty(s.getMsg()))
                                iBaseView.showTokenNotice(s.getMsg());
                        }
                        else
                        {
                            if (!TextUtils.isEmpty(s.getMsg()))
                                callBack.onFailure(s.getMsg());
                        }
                        if (!TextUtils.isEmpty(s.getMsg()))
                            iBaseView.showTips(s.getMsg());
                        iBaseView.hideLoading();

                    }
                });
        LogUtil.e(TAG, "[observable]" + subscription.isUnsubscribed());
        addSubscription(subscription);
    }

}
