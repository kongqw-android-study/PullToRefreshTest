package com.example.kongqingwei.pulltorefreshtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;


/**
 * Created by kongqingwei on 2016/12/21.
 * 弹性的View
 */

public class RetractableView extends ScrollView {

    private static final String TAG = "RetractableView";

    public RetractableView(Context context) {
        super(context);
        initView(context);
    }

    public RetractableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RetractableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化View
     *
     * @param context
     */
    private void initView(Context context) {

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    private int startY;
    private int lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i(TAG, "onTouchEvent: ");
        int y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetY = lastY - y;
                ((View) getParent()).scrollBy(0, offsetY / 3);
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onTouchEvent: 复位");
                ((View) getParent()).scrollTo(0, 0);
                break;
            default:
                return super.onTouchEvent(event);
        }
        return true;
    }
}
