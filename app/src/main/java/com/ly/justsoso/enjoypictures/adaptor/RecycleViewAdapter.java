package com.ly.justsoso.enjoypictures.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ly.justsoso.R;
import com.ly.justsoso.enjoypictures.adaptor.item.PicassoRecycleItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LY on 6/8/2016.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.RecycleViewHolder> {

    public static final String TAG = RecycleViewAdapter.class.getCanonicalName();



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

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecycleViewHolder(mLayoutInflater.from(mContext).inflate(R.layout.picasso_item_recycle_view,parent,false));
    }


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
    public void setmDatas(List<PicassoRecycleItem> mDatas) {
        this.mDatas = mDatas;
    }

    public void addDatas(List<PicassoRecycleItem> datas){
        mDatas.addAll(datas);
    }
    public List<PicassoRecycleItem> getmDatas() {
        return mDatas;
    }

    public interface onClickItemListener{
        void onClickImage(View view,int position,PicassoRecycleItem item);
    }
}
