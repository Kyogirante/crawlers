package com.example.wang.loveshape.RecyclerDemo.Interface;

/**
 * Created by wang on 2015/7/3.
 */
public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
