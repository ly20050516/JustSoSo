package com.ly.picassostudy.listview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.ly.picassostudy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LY on 6/8/2016.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.RecycleViewHolder> {

    public static final String TAG = RecycleViewAdapter.class.getCanonicalName();

    public void setmDatas(List<PicassoRecycleItem> mDatas) {
        this.mDatas = mDatas;
    }

    private List<PicassoRecycleItem> mDatas;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public void setmOnClickItemListener(onClickItemListener mOnClickItemListener) {
        this.mOnClickItemListener = mOnClickItemListener;
    }

    private onClickItemListener mOnClickItemListener;
    public RecycleViewAdapter(Context context,List<PicassoRecycleItem> list){
        mDatas = list;
        if(mDatas == null)
            mDatas = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }
    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p/>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p/>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecycleViewHolder(mLayoutInflater.from(mContext).inflate(R.layout.picasso_item_recycle_view,parent,false));
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p/>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p/>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle effcient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(final RecycleViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: url " + position + " " + mDatas.get(position).url);
        Picasso.with(mContext).load(mDatas.get(position).url).placeholder(R.drawable.user_placeholder).error(R.drawable.user_placeholder).into(holder.imageView);
        if(mOnClickItemListener != null){
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickItemListener.onClickImage(holder.imageView,position,mDatas.get(position));
                }
            });
        }
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class RecycleViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public RecycleViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.picasso_item_recycle_view);
        }
    }

    public interface onClickItemListener{
        void onClickImage(View view,int position,PicassoRecycleItem item);
    }
}
