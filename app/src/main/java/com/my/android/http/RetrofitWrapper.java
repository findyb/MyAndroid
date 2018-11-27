package com.my.android.http;

import android.content.Context;

import com.my.android.constant.Constant;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/11/8.
 *
 * 网络接口服务包装类
 */

public class RetrofitWrapper {

    private static RetrofitWrapper instance;
    private Context mContext;
    private Retrofit retrofit;

    private RetrofitWrapper() {
        //1.创建Retrofit对象
        retrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL) // 定义访问的主机地址
                .addConverterFactory(GsonConverterFactory.create())  //解析方法
                .build();
    }

    /**
     * 单例模式
     *
     * @return
     */
    public static RetrofitWrapper getInstance() {
        if (instance == null) {
            synchronized (RetrofitWrapper.class){
                if (instance==null){
                    instance = new RetrofitWrapper();
                }
            }
        }
        return instance;
    }


    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }

}
