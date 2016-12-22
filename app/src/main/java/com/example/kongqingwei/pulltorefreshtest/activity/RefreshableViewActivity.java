package com.example.kongqingwei.pulltorefreshtest.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kongqingwei.pulltorefreshtest.R;
import com.example.kongqingwei.pulltorefreshtest.adapter.ListViewAdapter;
import com.example.kongqingwei.pulltorefreshtest.view.RefreshableView;

public class RefreshableViewActivity extends AppCompatActivity {


    private ListView mListView;
    private RefreshableView refreshableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refreshable_view);

        refreshableView = (RefreshableView) findViewById(R.id.refreshableView);
        refreshableView.addOnPullRefreshListener(new RefreshableView.OnPullRefreshListener() {
            @Override
            public void onPullRefresh() {
                Toast.makeText(getApplicationContext(), "正在刷新", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "刷新完成", Toast.LENGTH_SHORT).show();
                        refreshableView.onComplete();
                    }
                }, 3000);
            }
        });

        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setAdapter(new ListViewAdapter(getApplicationContext()));


    }
}
