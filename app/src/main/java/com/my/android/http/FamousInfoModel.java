package com.my.android.http;

import android.content.Context;

import com.my.android.service.TestService;

/**
 * Created by Administrator on 2017/11/8.
 */

public class FamousInfoModel {

    private static FamousInfoModel famousInfoModel;
    private TestService testService;


    /**
     * 单例模式
     *
     * @return
     */
    public static FamousInfoModel getInstance(Context context) {
        if (famousInfoModel == null) {
            famousInfoModel = new FamousInfoModel(context.getApplicationContext());
        }
        return famousInfoModel;
    }


    private FamousInfoModel(Context context) {
        testService = RetrofitWrapper.getInstance().create(TestService.class);
    }
}
