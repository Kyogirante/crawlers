package example.com.demo.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.com.demo.ItemHelper.ItemTouchHelperAdapter;
import example.com.demo.ItemHelper.ItemTouchHelperViewHolder;
import example.com.demo.R;


/**
 * Created by wang on 2015/7/27.
 */
public class MyPopRvAdapter extends RecyclerView.Adapter<MyPopRvAdapter.MyPopViewHolder> implements ItemTouchHelperAdapter{

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    private List<String> tabData = new ArrayList<>();
    private OnItemClickListener listener;

    public MyPopRvAdapter(List<String> mList){
        this.tabData = mList;
    }
    @Override
    public MyPopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pop_text_view,parent,false);
        MyPopViewHolder vh = new MyPopViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyPopViewHolder holder, final int position) {
        holder.textView.setText(tabData.get(position));
        if(listener != null){
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return tabData.size();
    }

    public List<String> getTabData(){
        return this.tabData;
    }

    public void removeData(int position){
        tabData.remove(position);
        notifyItemRemoved(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        String temp = tabData.get(fromPosition);
        tabData.remove(fromPosition);
        tabData.add(toPosition, temp);
        notifyItemMoved(fromPosition, toPosition);
    }

    public class MyPopViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        public TextView textView;
        public ImageView imageView;

        public MyPopViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.pop_text);
            imageView = (ImageView)itemView.findViewById(R.id.image_del);
        }

        @Override
        public void onItemSelected() {
            textView.setBackgroundResource(R.drawable.bg_pop_text_draging);
        }

        @Override
        public void onItemClear() {
            textView.setBackgroundResource(R.drawable.bg_pop_text);
        }
    }
}
