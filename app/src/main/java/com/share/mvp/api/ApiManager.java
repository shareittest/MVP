package com.share.mvp.api;


import com.share.mvp.config.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * api网络
 */
public class ApiManager
{

    private static final int DEFAULT_TIMEOUT = 15;

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .build();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    private ServiceApi serviceApi;

    private ApiManager()
    {
    }

    private static class ApiManagerInstance
    {
        private static final ApiManager INSTANCE = new ApiManager();
    }

    public static ApiManager getInstance()
    {
        return ApiManagerInstance.INSTANCE;
    }

    /**
     * @return 后台接口服务
     */
    public ServiceApi getService()
    {
        if (serviceApi == null)
        {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.ServerURL)
                    .client(okHttpClient)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            serviceApi = retrofit.create(ServiceApi.class);
        }
        return serviceApi;
    }
}