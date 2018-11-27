package com.my.android.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.my.android.adapter.RecyclerViewAdapter;
import com.my.android.test.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/28.
 */

public class RecyclerViewActivity extends AppCompatActivity {

    private Context mContext;
    @BindView(R.id.recyclerView)
    XRecyclerView mRecyclerView;

    private List<String> list;
    private RecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        mContext = this;

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.findLastCompletelyVisibleItemPosition();
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecyclerViewAdapter(mContext);
        mAdapter.setData(getData());

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
//        mRecyclerView.setLoadingMoreEnabled(false);//设置是否滑动
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener(){

            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete();
                    }
                },3000);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.add("new Data");
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.loadMoreComplete();
                    }
                },3000);
            }
        });
    }

    private List<String> getData(){
        list = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            list.add(i + "测试");
        }
        return list;
    }
}
