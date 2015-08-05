package example.com.demo.View;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import example.com.demo.Adapter.MyPopRvAdapter;
import example.com.demo.ItemHelper.SimpleItemTouchHelperCallback;
import example.com.demo.R;
import example.com.demo.Utils.AppConfig;

/**
 * Created by wang on 2015/7/27.
 */
public class PopView extends LinearLayout{
    public interface TabDataChangeListener{
        void initTab();
    }

    private TabDataChangeListener listener;

    private ImageView mImage;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> mList;
    private LinearLayout popTitle;
    private LinearLayout rvLayout;

    private MyPopRvAdapter adapter;

    public PopView(Context context) {
        super(context);
        init();
    }

    public PopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public PopView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.pop_view, this);
        getData();

        mImage = (ImageView)findViewById(R.id.image_arrow_up);
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_tab_data);
        popTitle = (LinearLayout) findViewById(R.id.pop_title);
        rvLayout = (LinearLayout)findViewById(R.id.rv_layout);

        mImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAnimation();
                saveToLocal();
            }
        });

        mLayoutManager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(getAdapter());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置删除和添加item动画

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    public void setTabDataChangeListener(TabDataChangeListener listener){
        this.listener = listener;
    }

    private void getData(){
        mList = AppConfig.getTabData();
    }

    private MyPopRvAdapter getAdapter(){
        adapter = new MyPopRvAdapter(mList);
        adapter.setOnItemClickListener(new MyPopRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(),"click item" + position, Toast.LENGTH_SHORT).show();
            }
        });
        return adapter;
    }
    private void finishAnimation(){
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(popTitle,View.ALPHA, 1f,0f);
        ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(rvLayout,View.TRANSLATION_Y,0f,-rvLayout.getHeight());
        AnimatorSet alphaSet = new AnimatorSet();
        alphaSet.setDuration(300);
        alphaSet.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaSet.playTogether(alphaAnimator,translateAnimator);
        alphaSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setVisibility(GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        alphaSet.start();

    }

    public void startShowAnimation(){
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(popTitle,View.ALPHA, 0f,1f);
        ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(rvLayout,View.TRANSLATION_Y,-rvLayout.getHeight(),0f);
        AnimatorSet alphaSet = new AnimatorSet();
        alphaSet.setDuration(300);
        alphaSet.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaSet.playTogether(alphaAnimator,translateAnimator);
        alphaSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        alphaSet.start();
    }

    private void saveToLocal(){
        AppConfig.setTabData(mList);
        if(listener != null){
            listener.initTab();
        }
    }
}
