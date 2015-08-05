package com.example.wang.loveshape.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wang.loveshape.Bean.Point;
import com.example.wang.loveshape.Evaluator.BezierEvaluator;
import com.example.wang.loveshape.R;

import java.util.Random;

/**
 * Created by wang on 2015/7/2.
 */
public class LoveShapeLayout extends RelativeLayout{
    private static int flag;
    private Random random = new Random();
    private int lWidth;        //布局自身宽度
    private int lHeight;       //布局自身高度
    private int shapeWidth;    //爱心宽度
    private int shapeHeight;   //爱心高度

    private Drawable red;
    private Drawable green;
    private Drawable blue;
    private Drawable[] drawables;
    private String[] strings;

    private Interpolator line = new LinearInterpolator();//线性
    private Interpolator acc = new AccelerateInterpolator();//加速
    private Interpolator dce = new DecelerateInterpolator();//减速
    private Interpolator accdec = new AccelerateDecelerateInterpolator();//县加速后减速
    private Interpolator[] interpolators;
    private LayoutParams lp;   //爱心位置参数
    public LoveShapeLayout(Context context) {
        this(context, null);
    }

    public LoveShapeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoveShapeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public LoveShapeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        lWidth = getMeasuredWidth();
        lHeight = getMeasuredHeight();
        init();//一些其他初始化操作
    }

    private void init(){
        drawables = new Drawable[3];
        red   =   getResources().getDrawable(R.drawable.red);
        green = getResources().getDrawable(R.drawable.green);
        blue  =  getResources().getDrawable(R.drawable.blue);
        drawables[0] = red;
        drawables[1] = green;
        drawables[2] = blue;

        strings = new String[]{"你","特","么","就","是","个","逗","比"};
        shapeWidth  =    red.getMinimumWidth();
        shapeHeight = red.getIntrinsicHeight();

        lp = new LayoutParams(shapeWidth, shapeHeight);
        lp.addRule(CENTER_HORIZONTAL,TRUE);
        lp.addRule(ALIGN_PARENT_BOTTOM, TRUE);

        interpolators = new Interpolator[4];
        interpolators[0] = line;
        interpolators[1] = acc;
        interpolators[2] = dce;
        interpolators[3] = accdec;
    }

    public void addShape(){
        if(flag > 50){
            TextView textView = new TextView(getContext());
            textView.setText(strings[random.nextInt(strings.length)]);
            textView.setLayoutParams(lp);
            textView.setGravity(Gravity.CENTER);
            addView(textView);

            AnimatorSet set = getEnterAnimator(textView);
            Animator bezier = getBezierAnimator(textView);

            AnimatorSet finalSet = new AnimatorSet();
            finalSet.playSequentially(set);
            finalSet.playSequentially(set, bezier);
            finalSet.setInterpolator(interpolators[random.nextInt(4)]);
            finalSet.addListener(new AnimEndListener(textView));
            finalSet.start();
        } else {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(drawables[random.nextInt(3)]);
            imageView.setLayoutParams(lp);

            addView(imageView);

            AnimatorSet set = getEnterAnimator(imageView);
            Animator bezier = getBezierAnimator(imageView);

            AnimatorSet finalSet = new AnimatorSet();
            finalSet.playSequentially(set);
            finalSet.playSequentially(set, bezier);
            finalSet.setInterpolator(interpolators[random.nextInt(4)]);
            finalSet.addListener(new AnimEndListener(imageView));
            finalSet.start();
        }

        flag++;
    }

    private AnimatorSet getEnterAnimator(final View view){
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view,View.ALPHA, 0.2f, 1f);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view,View.SCALE_X, 0.2f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view,View.SCALE_Y, 0.2f, 1f);
        AnimatorSet enterAnimation = new AnimatorSet();
        enterAnimation.setDuration(500);
        enterAnimation.setInterpolator(new LinearInterpolator());
        enterAnimation.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator);
        enterAnimation.setTarget(view);
        return enterAnimation;
    }

    private Animator getBezierAnimator(final View view){
        BezierEvaluator evaluator = new BezierEvaluator(getPoint(1),getPoint(2));

        ValueAnimator animator = ValueAnimator.ofObject(evaluator,
                new Point((lWidth-shapeWidth)/2,lHeight-shapeHeight),
                new Point((random.nextInt(getWidth())),0));
        animator.addUpdateListener(new BezierListener(view));
        animator.setTarget(view);
        animator.setDuration(3000);

        return animator;
    }

    private Point getPoint(int scale){
        Point point = new Point();
        point.setPointX(random.nextInt(lWidth - 100));
        point.setPointY(random.nextInt(lHeight-100)/scale);
        return point;
    }

    private class AnimEndListener extends AnimatorListenerAdapter{
        private View target;

        public AnimEndListener(View view){
            this.target = view;
        }

        @Override
        public void onAnimationEnd(Animator animator){
            super.onAnimationEnd(animator);

            removeView(target);
        }
    }

    private class BezierListener implements ValueAnimator.AnimatorUpdateListener{
        private View target;

        public BezierListener(View view){
            this.target = view;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation){
            Point point = (Point)animation.getAnimatedValue();
            target.setX(point.getPointX());
            target.setY(point.getPointY());
            target.setAlpha(1 - animation.getAnimatedFraction());
        }
    }
}
