package example.com.demo.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import example.com.demo.R;

/**
 * Created by wang on 2015/7/27.
 */
public class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewHolder>{
    private List<String> strings;

    public MyRvAdapter(List<String> data) {
        strings = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_text_view,viewGroup,false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        viewHolder.text.setText(strings.get(i));
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView text;
        public MyViewHolder(View itemView) {
            super(itemView);
            text = (TextView)itemView;
        }
    }
}
