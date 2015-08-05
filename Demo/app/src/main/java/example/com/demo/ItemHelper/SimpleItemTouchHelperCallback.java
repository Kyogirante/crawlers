package example.com.demo.ItemHelper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by wang on 2015/7/29.
 */
public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback{

    private ItemTouchHelperAdapter adapter;

    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter){
        this.adapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled(){
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled(){
        return false;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        final int swipeFlag = ItemTouchHelper.START;
        return makeMovementFlags(dragFlags,swipeFlag);
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if(viewHolder.getItemViewType() != target.getItemViewType()){
            return false;
        }
        adapter.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder,int actionState){
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            ItemTouchHelperViewHolder itemTouchHelperViewHolder = (ItemTouchHelperViewHolder)viewHolder;
            itemTouchHelperViewHolder.onItemSelected();
        }
        super.onSelectedChanged(viewHolder,actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder){
        super.clearView(recyclerView,viewHolder);

        ItemTouchHelperViewHolder itemTouchHelperViewHolder = (ItemTouchHelperViewHolder)viewHolder;
        itemTouchHelperViewHolder.onItemClear();
    }
}
