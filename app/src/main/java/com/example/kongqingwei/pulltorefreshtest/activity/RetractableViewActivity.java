package com.example.kongqingwei.pulltorefreshtest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kongqingwei.pulltorefreshtest.R;
import com.example.kongqingwei.pulltorefreshtest.adapter.ListViewAdapter;

public class RetractableViewActivity extends AppCompatActivity {

    private ListView mKistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retractable_view);

//        findViewById(R.id.text_view).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "TextView被点击", Toast.LENGTH_SHORT).show();
//            }
//        });

//        mKistView = (ListView) findViewById(R.id.list_view);
//
//        mKistView.setAdapter(new ListViewAdapter(getApplicationContext()));
    }
}
