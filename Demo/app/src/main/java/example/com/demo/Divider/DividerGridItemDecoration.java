package example.com.demo.Divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by wang on 2015/7/29.
 */
public class DividerGridItemDecoration extends RecyclerView.ItemDecoration{
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;

    public DividerGridItemDecoration(Context context){
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(canvas, parent);
        drawVertical(canvas, parent);
    }

    private int getSpanCount(RecyclerView parent){
        int spanCount = -1;
        RecyclerView.LayoutManager mLayoutManager = parent.getLayoutManager();
        if(mLayoutManager instanceof GridLayoutManager){
            spanCount = ((GridLayoutManager)mLayoutManager).getSpanCount();
        } else if(mLayoutManager instanceof StaggeredGridLayoutManager){
            spanCount = ((StaggeredGridLayoutManager)mLayoutManager).getSpanCount();
        }

        return spanCount;
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent){

    }

    private void drawVertical(Canvas canvas, RecyclerView parent){

    }
}
