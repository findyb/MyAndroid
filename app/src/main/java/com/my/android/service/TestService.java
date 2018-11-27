package com.my.android.service;

import com.my.android.bean.TestBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/11/8.
 */

public interface TestService {
    @FormUrlEncoded
    @POST("token/getToken")
    Call<TestBean> getTestRestult(@Field("appId") String appId, @Field("password") String pws);
}
