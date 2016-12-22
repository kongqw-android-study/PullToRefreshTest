package com.example.kongqingwei.pulltorefreshtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kongqingwei.pulltorefreshtest.R;

/**
 * Created by kongqingwei on 2016/12/22.
 * 可以下拉刷新的View
 */

public class RefreshableView extends LinearLayout implements View.OnTouchListener {

    private static final String TAG = "RefreshableView";
    private View header;
    private ListView listView;

    // 正常状态
    private static final int STATUS_NONE = 0;
    // 下拉刷新状态
    private static final int STATUS_PULL_TO_REFRESH = 1;
    // 释放立即刷新状态
    private static final int STATUS_RELEASE_TO_REFRESH = 2;
    // 正在刷新状态
    private static final int STATUS_REFRESHING = 3;
    // 刷新完成状态
    private static final int STATUS_REFRESH_FINISHED = 4;

    private int mStatus = STATUS_NONE;
    private MarginLayoutParams headerLayoutParams;
    private int hideHeaderHeight;
    private ImageView mImageViewArrow;
    private TextView mTextViewTip;

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
        mImageViewArrow = (ImageView) header.findViewById(R.id.arrow);
        mTextViewTip = (TextView) header.findViewById(R.id.text);
        this.addView(header, 0);
    }

    private boolean loadOnce;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed && !loadOnce) {
            /*
             * 隐藏Header头布局
             *************************************************************************************/
            hideHeaderHeight = -header.getHeight();

            // 隐藏header方式一
            // header.setPadding(header.getPaddingLeft(),hideHeaderHeight,header.getPaddingRight(),header.getPaddingBottom());
            // 隐藏header方式二
            // LayoutParams layoutParams = (LayoutParams) header.getLayoutParams();
            // layoutParams.topMargin = hideHeaderHeight;
            // 隐藏header方式三
            headerLayoutParams = (MarginLayoutParams) header.getLayoutParams();
            headerLayoutParams.topMargin = hideHeaderHeight;
            header.setLayoutParams(headerLayoutParams);

            /*
             * 获取子布局
             *************************************************************************************/
            int childCount = getChildCount();
            Log.i(TAG, "onLayout: 子控件数 ：" + childCount);
            if (2 <= childCount) {
                View view = getChildAt(1);
                if (view instanceof ListView) {
                    // 子空间是ListView
                    Log.i(TAG, "onLayout: 子控件是一个ListView");
                    listView = (ListView) view;
                    listView.setOnTouchListener(this);
                } else {
                    Log.i(TAG, "onLayout: 子控件不是ListView");
                }
            }
            loadOnce = true;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Log.i(TAG, "onTouch: mStatus = " + mStatus);
        isPullRefreshable(event);
        if (isPullRefreshable) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    yDown = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float yMove = event.getRawY();
                    int distance = (int) (yMove - yDown);
                    if (distance <= 0 && headerLayoutParams.topMargin <= hideHeaderHeight) {
                        return false;
                    }

                    if (hideHeaderHeight < headerLayoutParams.topMargin && 0 > headerLayoutParams.topMargin) {
                        mStatus = STATUS_PULL_TO_REFRESH;
                    } else if (0 < headerLayoutParams.topMargin) {
                        mStatus = STATUS_RELEASE_TO_REFRESH;
                    } else {
                        mStatus = STATUS_NONE;
                    }

                    headerLayoutParams.topMargin = (distance / 2) + hideHeaderHeight;
                    header.setLayoutParams(headerLayoutParams);
                    break;
                case MotionEvent.ACTION_UP:
                    // showTip("ACTION_UP");
                    if (headerLayoutParams.topMargin != hideHeaderHeight) {
                        headerLayoutParams.topMargin = hideHeaderHeight;
                        header.setLayoutParams(headerLayoutParams);
                    }
                    mStatus = STATUS_NONE;
                    break;
                default:
                    break;
            }
            // 刷新头布局显示状态
            refreshHeaderView();
            return true;
        }
        return super.onTouchEvent(event);
    }

    private boolean isPullRefreshable;
    private float yDown;

    /**
     * 判断当前状态能否下拉刷新
     *
     * @param event 状态
     */
    private void isPullRefreshable(MotionEvent event) {
        View firstChild = listView.getChildAt(0);
        if (null == firstChild) {
            isPullRefreshable = true;
        } else {
            int firstVisiblePos = listView.getFirstVisiblePosition();
            if (firstVisiblePos == 0 && firstChild.getTop() == 0) {
                if (!isPullRefreshable) {
                    yDown = event.getRawY();
                    // Log.i(TAG, "isPullRefreshable: 起点Y ： " + yDown);
                }
                isPullRefreshable = true;
            } else {
                // showTip("Not Top!\nfirstVisiblePos = " + firstVisiblePos + "\nfirstChild.getTop() = " + firstChild.getTop());
                if (headerLayoutParams.topMargin != hideHeaderHeight) {
                    headerLayoutParams.topMargin = hideHeaderHeight;
                    header.setLayoutParams(headerLayoutParams);
                }
                isPullRefreshable = false;
            }
        }
    }

    private int tempStatus = STATUS_NONE;

    /**
     * 刷新头布局
     */
    private void refreshHeaderView() {
        if (tempStatus != mStatus) {
            switch (mStatus) {
                case STATUS_NONE: // NONE
                    break;
                case STATUS_PULL_TO_REFRESH: // 下拉刷新
                    if (VISIBLE != mImageViewArrow.getVisibility()) {
                        mImageViewArrow.setVisibility(VISIBLE);
                    }
                    // 旋转箭头向下
                    rotateArrow(mImageViewArrow);
                    mTextViewTip.setText("下拉刷新");
                    break;
                case STATUS_RELEASE_TO_REFRESH: // 释放立即刷新
                    if (VISIBLE != mImageViewArrow.getVisibility()) {
                        mImageViewArrow.setVisibility(VISIBLE);
                    }
                    // 旋转箭头向上
                    rotateArrow(mImageViewArrow);
                    mTextViewTip.setText("释放立即刷新");
                    break;
                case STATUS_REFRESHING: // 正在刷新
                    break;
                case STATUS_REFRESH_FINISHED: // 刷新完成
                    break;
                default:
                    break;
            }
            tempStatus = mStatus;
        }
    }

    /**
     * 旋转箭头
     *
     * @param arrow 箭头
     */
    private void rotateArrow(ImageView arrow) {
        float pivotX = arrow.getWidth() / 2f;
        float pivotY = arrow.getHeight() / 2f;
        float fromDegrees = 0f;
        float toDegrees = 0f;
        if (mStatus == STATUS_PULL_TO_REFRESH) {
            fromDegrees = 0f;
            toDegrees = 180f;
        } else if (mStatus == STATUS_RELEASE_TO_REFRESH) {
            fromDegrees = 180f;
            toDegrees = 360f;
        }
        RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);
        animation.setDuration(100);
        animation.setFillAfter(true);
        arrow.startAnimation(animation);
    }

}
