package com.ly.justsoso.sample.adapter.card;

import android.content.Context;
import android.view.View;

import com.ly.justsoso.sample.adapter.ActionProcessor;
import com.ly.justsoso.sample.adapter.item.SampleItem;

/**
 * Created by ly on 2017/7/2.
 */

public abstract class AbstractSampleCard implements ActionProcessor{

    protected SampleItem mSampleItem;

    ActionProcessor mActionProcessor;

    public abstract int getCardType();

    public abstract int getCardStyle();

    public abstract String getCardName();

    public abstract View getCardView(Context context);

    public abstract void updateData(SampleItem item);

    public void setActionProcessor(ActionProcessor actionProcessor) {
        this.mActionProcessor = actionProcessor;
    }

    @Override
    public void action(int action, SampleItem sampleItem) {
        if(mActionProcessor != null) {
            mActionProcessor.action(action,sampleItem);
        }
    }
}
