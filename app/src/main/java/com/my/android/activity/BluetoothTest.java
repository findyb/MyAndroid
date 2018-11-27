package com.my.android.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.my.android.test.R;

import java.util.List;

/**
 * Created by Administrator on 2018/5/23.
 */

public class BluetoothTest extends AppCompatActivity {

    private Context mContext;
    private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        initView();
    }

    private void initView() {
        mContext = this;
        setmBluetoothAdapter();

        //获取本地蓝牙名称
        String name = mBluetoothAdapter.getName();
        //获取本地蓝牙地址
        String address = mBluetoothAdapter.getAddress();
        //打印相关信息
        Log.i("BluetoothTest", "BLE Name=" + name);
        Log.i("BluetoothTest", "BLE Address=" + address);

        getConnectedBluetoothInfo();
    }


    private void setmBluetoothAdapter() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "设备不支持蓝牙", Toast.LENGTH_SHORT).show();
            finish();
        }

        // 判断是否打开蓝牙
        if (!mBluetoothAdapter.isEnabled()) {
            // 弹出对话框提示用户是后打开
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 1);
            // 不做提示，强行打开
            // mBluetoothAdapter.enable();
        }
    }

    private void getConnectedBluetoothInfo() {
        int a2dp = mBluetoothAdapter.getProfileConnectionState(BluetoothProfile.A2DP);
        int headset = mBluetoothAdapter.getProfileConnectionState(BluetoothProfile.HEADSET);
        int health = mBluetoothAdapter.getProfileConnectionState(BluetoothProfile.HEALTH);

        Log.i("BluetoothTest", "a2dp=" + a2dp);
        Log.i("BluetoothTest", "headset=" + headset);
        Log.i("BluetoothTest", "health=" + health);

        Log.i("BluetoothTest", "BluetoothProfile.STATE_CONNECTED=" + BluetoothProfile.STATE_CONNECTED);
        int flag = -1;
        if (a2dp == BluetoothProfile.STATE_CONNECTED) {
            flag = a2dp;
        } else if (headset == BluetoothProfile.STATE_CONNECTED) {
            flag = headset;
        } else if (health == BluetoothProfile.STATE_CONNECTED) {
            flag = health;
        }
        if (flag != -1) {
            mBluetoothAdapter.getProfileProxy(mContext, new BluetoothProfile.ServiceListener() {
                @Override
                public void onServiceDisconnected(int profile) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onServiceConnected(int profile, BluetoothProfile proxy) {
                    // TODO Auto-generated method stub
                    List<BluetoothDevice> mDevices = proxy.getConnectedDevices();
                    if (mDevices != null && mDevices.size() > 0) {
                        for (BluetoothDevice device : mDevices) {
                            Log.i("BluetoothTest", "device name: " + device.getName());
                        }
                    } else {
                        Log.i("BluetoothTest", "mDevices is null");
                    }
                }
            }, flag);
        }
    }
}
