package com.share.mvp.api;


import com.share.mvp.model.Response;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 接口服务
 */

public interface ServiceApi
{
    //登录
    @POST("login")
    Observable<Response> login(@Body RequestBody body);

    //版本更新
    @GET("satinGodApi?type=1&page=1")
    Observable<Response> versionAndroid();

}
