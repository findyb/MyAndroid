package com.my.android.activity;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/5/22.
 */

public class JavaScriptObject {
    Context mContxt;
    public JavaScriptObject(Context mContxt) {
        this.mContxt = mContxt;
    }

    public void fun1FromAndroid(String name) {
        Toast.makeText(mContxt, name, Toast.LENGTH_LONG).show();
    }

    public void fun2(String name) {
        Toast.makeText(mContxt, "调用fun2:" + name, Toast.LENGTH_SHORT).show();
    }
}
