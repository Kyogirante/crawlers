package com.example.wang.loveshape.Evaluator;

import android.animation.TypeEvaluator;

import com.example.wang.loveshape.Bean.Point;

/**
 * Created by wang on 2015/7/2.
 */
public class BezierEvaluator implements TypeEvaluator<Point>{
    private Point pointOne;
    private Point pointTwo;

    public BezierEvaluator(Point pointOne, Point pointTwo) {
        this.pointOne = pointOne;
        this.pointTwo = pointTwo;
    }

    @Override
    public Point evaluate(float time, Point startValue, Point endValue) {
        Point track = new Point();
        float leftTime = 1 - time;

        Point start = startValue;
        Point end = endValue;

        track.setPointX(
                (int)(leftTime * leftTime * leftTime * start.getPointX()
                + 3 * leftTime  * leftTime * time * pointOne.getPointX()
                + 3 * leftTime * time * time * pointTwo.getPointX()
                + time * time * time * endValue.getPointX())
        );

        track.setPointY(
                (int)(leftTime * leftTime * leftTime * start.getPointY()
                        + 3 * leftTime  * leftTime * time * pointOne.getPointY()
                        + 3 * leftTime * time * time * pointTwo.getPointY()
                        + time * time * time * endValue.getPointY())
        );
        return track;
    }
}
