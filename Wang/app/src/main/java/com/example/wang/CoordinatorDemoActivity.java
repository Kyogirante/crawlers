package com.example.wang;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by wang on 2015/7/22.
 */
public class CoordinatorDemoActivity extends Activity{

    private RecyclerView recyclerView;
    private FloatingActionButton btn;
    private CoordinatorLayout main_content;

    private RecyclerView.LayoutManager mLayoutManager;
    private String[] strings = new String[]{"a","b","c","d","e","f","g","h","i","j","a",
            "b","c","d","e","f","g","h","i","j","a","b","c","d","e","f","g","h","i","j",
            "a","b","c","d","e","f","g","h","i","j"};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_demo_view);

        recyclerView = (RecyclerView) findViewById(R.id.rv_list_view);
        btn = (FloatingActionButton) findViewById(R.id.btn);
        main_content= (CoordinatorLayout) findViewById(R.id.main_content);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(new MyAdapter());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(main_content,"yoyoyo",Snackbar.LENGTH_SHORT)
                        .setAction("点击", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(CoordinatorDemoActivity.this,"yoyoyo",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setActionTextColor(R.color.blue)
                        .show();
            }
        });
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_text_view,viewGroup,false);
            MyViewHolder vh = new MyViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int i) {
            viewHolder.text.setText(strings[i]);
        }

        @Override
        public int getItemCount() {
            return strings.length;
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{
        TextView text;
        public MyViewHolder(View itemView) {
            super(itemView);
            text = (TextView)itemView;
        }
    }

}
