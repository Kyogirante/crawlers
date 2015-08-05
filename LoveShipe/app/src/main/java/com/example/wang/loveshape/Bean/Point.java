package com.example.wang.loveshape.Bean;

/**
 * Created by wang on 2015/7/2.
 */
public class Point {
    private int pointX;
    private int pointY;

    public Point(){

    }

    public Point(int pointX, int pointY) {
        this.pointX = pointX;
        this.pointY = pointY;
    }

    public float getPointX() {
        return pointX;
    }

    public void setPointX(int pointX) {
        this.pointX = pointX;
    }

    public int getPointY() {
        return pointY;
    }

    public void setPointY(int pointY) {
        this.pointY = pointY;
    }
}
