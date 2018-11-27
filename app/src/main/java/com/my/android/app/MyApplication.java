package com.my.android.app;

import android.app.Application;

import com.my.android.service.Logger;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2018/4/12.
 */

public class MyApplication extends Application {

    private static final String TAG = "JIGUANG-Example";

    @Override
    public void onCreate() {
        Logger.d(TAG, "[ExampleApplication] onCreate");
        super.onCreate();

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }
}
