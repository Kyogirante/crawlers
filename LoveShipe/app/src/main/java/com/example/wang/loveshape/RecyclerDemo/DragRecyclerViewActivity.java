package com.example.wang.loveshape.RecyclerDemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wang.loveshape.R;
import com.example.wang.loveshape.RecyclerDemo.Interface.ItemTouchHelperAdapter;
import com.example.wang.loveshape.RecyclerDemo.Interface.ItemTouchHelperViewHolder;
import com.example.wang.loveshape.RecyclerDemo.Interface.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class DragRecyclerViewActivity extends Activity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_recycler_view);
        recyclerView = (RecyclerView)findViewById(R.id.drag_recycler);

        adapter = getAdapter();
        recyclerView.setAdapter(adapter);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);//在滑动item的过程中，可能让item消失或者变化位置，在adapter还实现该接口，可以回调
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private MyAdapter getAdapter(){
        return new MyAdapter(getData());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drag_recycler_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public List<String> getData(){
        List<String> data = new ArrayList<>();
        String[] strings = new String[]{"北京市(京)", "天津市(津)", "河北省(冀)", "山西省(晋)","内蒙古自治区(内蒙古)",
                "辽宁省(辽)", "吉林省(吉)", "黑龙江省(黑)", "上海市(沪)"," 江苏省(苏)", "浙江省(浙)",
                "安徽省(皖)", "福建省(闽)", "江西省(赣)",  "山东省(鲁)","河南省(豫)",  "湖北省(鄂)",
                "湖南省(湘)", "广东省(粤)", "广西壮族自治区(桂)", "海南省(琼)", "重庆市(渝)","四川省(川、蜀)",
                "贵州省(黔、贵)", "云南省(滇、云)", "西藏自治区(藏)", "陕西省(陕、秦)",
                "甘肃省(甘、陇)", "青海省(青)", "宁夏回族自治区(宁)", "台湾省(台)",
                "新疆维吾尔自治区(新)", "香港特别行政区(港)", "澳门特别行政区(澳)"};
        data.addAll(Arrays.asList(strings));
        return data;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> implements ItemTouchHelperAdapter{

        private List<String> data;
        public MyAdapter(List<String> data){
            this.data = data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            if(viewGroup == null){
                return null;
            }
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_recyclerview_item,viewGroup,false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder viewHolder, int i) {
            if(viewHolder == null){
                return;
            }
            viewHolder.textView.setText(data.get(i));
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public void onItemMove(int fromPosition, int toPosition) {
            Collections.swap(data,fromPosition,toPosition);
            notifyItemMoved(fromPosition,toPosition);
        }

        @Override
        public void onItemDismiss(int position) {
            data.remove(position);
            notifyItemRemoved(position);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder{
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.text);
        }

        @Override
        public void onItemSelected() {//选中item背景色变化
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {//松开后背景色恢复
            itemView.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_200));
        }
    }
}
