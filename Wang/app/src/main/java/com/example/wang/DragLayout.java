package com.example.wang;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by wang on 2015/7/21.
 */
public class DragLayout extends FrameLayout{

    private ViewDragHelper mDrag;
    private View mText;
    private View m2Text;
    private int mX;
    private int mY;

    public DragLayout(Context context) {
        super(context);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(){
        mDrag = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == m2Text;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx){
                int leftBound = getPaddingLeft();
                int rightBound = getPaddingLeft() + mText.getWidth();
                int newleft = Math.min(Math.max(left,leftBound),rightBound);
                return newleft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy){
                return 0;
            }

            @Override
            public void onViewReleased(View releasedChild, float x, float y){
                int leftBound = getPaddingLeft();
                int width = mText.getWidth();
                int left = m2Text.getLeft();
                if(left >= leftBound && left < width/2) {
                    mDrag.settleCapturedViewAt(mX,mY);
                } else if(left >= width/2 && left <= width){
                    mDrag.settleCapturedViewAt(width,mY);
                }
                invalidate();
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId){
                mDrag.captureChildView(m2Text,pointerId);
            }
        });
    }

    @Override
    public void computeScroll()
    {
        if(mDrag.continueSettling(true))
        {
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event){
        return mDrag.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        mDrag.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate(){
        super.onFinishInflate();
        mText = getChildAt(0);
        m2Text = getChildAt(1);

        mX = m2Text.getLeft();
        mY = m2Text.getTop();
        init();
    }
}
