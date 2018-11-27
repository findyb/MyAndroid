package com.my.android.test;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.my.android.activity.BluetoothTest;
import com.my.android.activity.RecyclerViewActivity;
import com.my.android.activity.TestActivity;
import com.my.android.bean.TestBean;
import com.my.android.service.ExampleUtil;
import com.my.android.service.TagAliasOperatorHelper;
import com.my.android.service.TestService;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.my.android.service.TagAliasOperatorHelper.ACTION_SET;
import static com.my.android.service.TagAliasOperatorHelper.TagAliasBean;
import static com.my.android.service.TagAliasOperatorHelper.sequence;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int MSG_SET_ALIAS = 0x12;
    private static final String BASE_URL = "http://114.55.53.219:9080/zhongxinv2_api/";
    @BindView(R.id.tv_text)
    TextView tv_text;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.editText)
    EditText editText;
    TestBean.DataBean dataBean;


    private FingerprintManagerCompat manager;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    tv_text.setText("appToken:" + dataBean.getAppToken() + "\n" + "secret:" + dataBean.getSecret());
                    break;
                case 1:
                    textView.setText("appToken:" + dataBean.getAppToken() + "\n" + "secret:" + dataBean.getSecret());
                    break;

                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
//                    JPushInterface.setAliasAndTags(getApplicationContext(),
//                            (String) msg.obj,
//                            null, mAliasCallback);
                    JPushInterface.setAlias(getApplicationContext(), 1, "yuan_");
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        setAlias();

        manager = FingerprintManagerCompat.from(this);
    }


    @OnClick({R.id.button,R.id.button2,R.id.button3})
    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.button:
//                Log.i(TAG, "---" + "点击请求...");
//                getData();
//                getData2();
//                startActivity(new Intent(this, RecyclerViewActivity.class));
//                startActivity(new Intent(this, TestActivity.class));

//                textView.setText("合计："+sum(Integer.parseInt(editText.getText().toString())));
                textView.setText(getChannel());
                break;
        }     switch (view.getId()) {
            case R.id.button2:
                startActivity(new Intent(MainActivity.this, BluetoothTest.class));
                break;
            case R.id.button3:
                manager.authenticate(null, 0, null, new MyCallBack(), null);
                break;
        }
    }

    public class MyCallBack extends FingerprintManagerCompat.AuthenticationCallback {

        // 当出现错误的时候回调此函数，比如多次尝试都失败了的时候，errString是错误信息
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            Log.d(TAG, "onAuthenticationError: " + errString);
        }

        // 当指纹验证失败的时候会回调此函数，失败之后允许多次尝试，失败次数过多会停止响应一段时间然后再停止sensor的工作
        @Override
        public void onAuthenticationFailed() {
            Log.d(TAG, "onAuthenticationFailed: " + "验证失败");
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            Log.d(TAG, "onAuthenticationHelp: " + helpString);
        }

        // 当验证的指纹成功时会回调此函数，然后不再监听指纹sensor
        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult
                                                      result) {
            Log.d(TAG, "onAuthenticationSucceeded: " + "验证成功");
        }
    }

    private static int sum(int s){
        if(s==1 || s==2)
            return 1;
        else
            return sum(s-1)+sum(s-2);
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "设置别名成功";
                    Log.i(TAG, logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
            ExampleUtil.showToast(logs, getApplicationContext());
        }
    };

    private String getChannel() {
        try {
            PackageManager pm = getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return "";
    }
    private void setAlias() {
//        EditText aliasEdit = (EditText) findViewById(R.id.et_alias);
//        String alias = aliasEdit.getText().toString().trim();
//        if (TextUtils.isEmpty(alias)) {
//            Toast.makeText(PushSetActivity.this,R.string.error_alias_empty, Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (!ExampleUtil.isValidTagAndAlias(alias)) {
//            Toast.makeText(PushSetActivity.this,R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
//            return;
//        }

        // 调用 Handler 来异步设置别名
        int action = -1;
        boolean isAliasAction = true;
        action = ACTION_SET;
//        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, "yuan_0x10"));
        TagAliasBean tagAliasBean = new TagAliasBean();
        tagAliasBean.action = action;
        sequence++;
//        if (isAliasAction) {
        tagAliasBean.alias = "yuan_";
//        } else {
//            tagAliasBean.tags = tags;
//        }
        tagAliasBean.isAliasAction = isAliasAction;
        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(), sequence, tagAliasBean);
//        JPushInterface.setAlias(getApplicationContext(), 1, "yuan_");
    }

    private void getData() {
        //创建Retrofit请求对象
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
//        RetrofitWrapper.getInstance().create()
        //创建访问API请求
        TestService service = retrofit.create(TestService.class);
        Call<TestBean> call = service.getTestRestult("LOAN_ANDROID", "123456");
        call.enqueue(new Callback<TestBean>() {
            @Override
            public void onResponse(Call<TestBean> call, Response<TestBean> response) {
                Log.i(TAG, "---" + response.isSuccess());
                Log.i(TAG, "---" + response.toString());
                if (response.isSuccess()) {
                    TestBean result = response.body();
                    dataBean = result.getData();
                    mHandler.sendEmptyMessage(0);

                    Log.i(TAG, "---" + dataBean.getAppToken());
                    Log.i(TAG, "---" + dataBean.getSecret());
                }
            }

            @Override
            public void onFailure(Call<TestBean> call, Throwable t) {

            }
        });
    }


    private void getData2() {
        //创建Retrofit请求对象
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
        //创建访问API请求
        TestService service = retrofit.create(TestService.class);
        Call<TestBean> call = service.getTestRestult("LOAN_ANDROID", "123456");
        call.enqueue(new Callback<TestBean>() {
            @Override
            public void onResponse(Call<TestBean> call, Response<TestBean> response) {
                Log.i(TAG, "====" + response.isSuccess());
                Log.i(TAG, "====" + response.toString());
                if (response.isSuccess()) {
                    mHandler.sendEmptyMessage(1);
                    Log.i(TAG, "code:" + response.body().getCode());
                    Log.i(TAG, "====" + response.message());
                    Log.i(TAG, "====" + response.body().getData().getAppToken());
                }
            }

            @Override
            public void onFailure(Call<TestBean> call, Throwable t) {

            }
        });
    }
}
