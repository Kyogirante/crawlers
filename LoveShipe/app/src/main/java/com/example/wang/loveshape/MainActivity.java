package com.example.wang.loveshape;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.wang.loveshape.RecyclerDemo.DragRecyclerViewActivity;
import com.example.wang.loveshape.View.LoveShapeLayout;


public class MainActivity extends Activity {

    private TextView text;
    private LoveShapeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView)findViewById(R.id.hello);
        layout = (LoveShapeLayout)findViewById(R.id.layout);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.addShape();
                Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
                Keyframe kf1 = Keyframe.ofFloat(.5f, 360f);
                Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
                PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2);//动画属性名，可变参数
                ObjectAnimator rotationAnim = ObjectAnimator.ofPropertyValuesHolder(text, pvhRotation);
                rotationAnim.setDuration(5000);
                rotationAnim.start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id) {
            case R.id.drag_recycler_view:
                Intent intent = new Intent(this, DragRecyclerViewActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
