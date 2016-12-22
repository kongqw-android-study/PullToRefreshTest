package com.example.kongqingwei.pulltorefreshtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.kongqingwei.pulltorefreshtest.R;

/**
 * Created by kongqingwei on 2016/12/22.
 * 可以下拉刷新的View
 */

public class RefreshableView extends LinearLayout {

    private static final String TAG = "RefreshableView";
    private View header;

    public RefreshableView(Context context) {
        super(context);
        initView(context);
    }

    public RefreshableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RefreshableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        header = layoutInflater.inflate(R.layout.header, null);
        this.addView(header, 0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed) {
            int headerHeight = header.getHeight();
            int headerHideHeight = -headerHeight;

            // 隐藏header方式一
            // header.setPadding(header.getPaddingLeft(),headerHideHeight,header.getPaddingRight(),header.getPaddingBottom());
            // 隐藏header方式二
            // LayoutParams layoutParams = (LayoutParams) header.getLayoutParams();
            // layoutParams.topMargin = headerHideHeight;
            // 隐藏header方式三
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) header.getLayoutParams();
            marginLayoutParams.topMargin = headerHideHeight;
        }
    }
}
