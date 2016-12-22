package com.example.kongqingwei.pulltorefreshtest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.kongqingwei.pulltorefreshtest.R;
import com.example.kongqingwei.pulltorefreshtest.adapter.ListViewAdapter;

public class RefreshableViewActivity extends AppCompatActivity {


    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refreshable_view);

        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setAdapter(new ListViewAdapter(getApplicationContext()));
    }
}
