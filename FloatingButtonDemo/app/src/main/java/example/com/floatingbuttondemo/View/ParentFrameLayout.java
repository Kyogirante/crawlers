package example.com.floatingbuttondemo.View;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by wang on 2015/7/30.
 */
public class ParentFrameLayout extends FrameLayout{

    private View view;
    private ViewDragHelper mDrag;
    private Point btnPoint = new Point();

    public ParentFrameLayout(Context context) {
        super(context);
    }

    public ParentFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParentFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public ParentFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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

    protected void onLayout(boolean changed, int l, int t, int r, int b){
        super.onLayout(changed,l,t,r,b);

        btnPoint.set(view.getLeft(), view.getTop());
    }
    @Override
    protected void onFinishInflate(){
        super.onFinishInflate();

        //将btn放在最后，获取最后一个子view的时候就是获取btn
        int count = getChildCount();
        if(count > 0){
            view = getChildAt(count-1);
            mDrag = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
                /**
                 * 捕获view
                 */
                @Override
                public boolean tryCaptureView(View child, int pointerId) {
                    return child == view;
                }

                /**
                 * 手指释放后调用
                 */
                @Override
                public void onViewReleased(View releasedChild, float xvel, float yvel) {
                    if (releasedChild == view) {
                        mDrag.settleCapturedViewAt(btnPoint.x, btnPoint.y);
                        invalidate();
                    }
                }

                /**
                 * 设置水平拖动范围
                 */
                @Override
                public int clampViewPositionHorizontal(View child, int left, int dx) {
                    return left;
                }

                /**
                 * 设置竖直拖动范围
                 */
                @Override
                public int clampViewPositionVertical(View child, int top, int dy) {
                    return top;
                }

                /**
                 * view可以点击时候需要重写
                 */
                @Override
                public int getViewHorizontalDragRange(View child) {
                    return getMeasuredWidth() - child.getMeasuredWidth();
                }

                @Override
                public int getViewVerticalDragRange(View child) {
                    return getMeasuredHeight() - child.getMeasuredHeight();
                }
            });
        }
    }
}
