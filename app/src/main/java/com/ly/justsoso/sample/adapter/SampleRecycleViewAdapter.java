package com.ly.justsoso.sample.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ly.framework.context.ContextManager;
import com.ly.framework.utilities.DisplayUtil;
import com.ly.justsoso.sample.adapter.card.AbstractSampleCard;
import com.ly.justsoso.sample.adapter.card.SampleCardFactory;
import com.ly.justsoso.sample.adapter.item.SampleItem;

import java.util.ArrayList;

/**
 * Created by ly on 2017/7/1.
 */

public class SampleRecycleViewAdapter extends RecyclerView.Adapter<SampleRecycleViewAdapter.SampleViewHolder> {

    Context mContext;
    ArrayList<SampleItem> mSampleList = new ArrayList<>();
    ActionProcessor mActionProcessor;

    public SampleRecycleViewAdapter(Context context) {
        mContext = context;
    }

    public void clear() {
        mSampleList.clear();
    }

    public void add(SampleItem item) {
        mSampleList.add(item);
    }

    public void add(int index,SampleItem item) {
        mSampleList.add(index,item);
    }

    public void addAll(ArrayList<SampleItem> list) {
        mSampleList.addAll(list);
    }

    public void setActionProcessor(ActionProcessor actionProcessor) {
        this.mActionProcessor = actionProcessor;
    }

    @Override
    public SampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = new CardView(mContext);
        return new SampleViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(SampleViewHolder holder, int position) {
        SampleItem sampleItem = mSampleList.get(position);
        holder.update(mContext,sampleItem);

    }

    @Override
    public int getItemCount() {
        return mSampleList.size();
    }

    class SampleViewHolder extends RecyclerView.ViewHolder {

        final int height = DisplayUtil.dp2px(ContextManager.getApplicationContext(),100);
        CardView.LayoutParams layoutParams = new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        CardView mRoot;
        SampleCardFactory mSampleCardFactory = new SampleCardFactory();
        public SampleViewHolder(CardView itemView) {
            super(itemView);
            mRoot = itemView;
        }

        public void update(Context context,SampleItem sampleItem) {
            AbstractSampleCard sampleCard = mSampleCardFactory.generate(sampleItem);
            mRoot.removeAllViews();
            mRoot.addView(sampleCard.getCardView(context),layoutParams);
            sampleCard.updateData(sampleItem);
            sampleCard.setActionProcessor(new ActionProcessor() {
                @Override
                public void action(int action, SampleItem sampleItem) {
                    if(mActionProcessor != null) {
                        mActionProcessor.action(action,sampleItem);
                    }
                }
            });
        }
    }
}
