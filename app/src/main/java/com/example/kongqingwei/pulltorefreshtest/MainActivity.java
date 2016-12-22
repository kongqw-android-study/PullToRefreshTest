package com.example.kongqingwei.pulltorefreshtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.kongqingwei.pulltorefreshtest.activity.RefreshableViewActivity;
import com.example.kongqingwei.pulltorefreshtest.activity.RetractableViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 弹性布局
        findViewById(R.id.btn_retractable_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RetractableViewActivity.class));
            }
        });

        // 下拉刷新
        findViewById(R.id.btn_refreshable_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RefreshableViewActivity.class));
            }
        });
    }
}
