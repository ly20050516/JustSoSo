package com.ly.justsoso.sample.adapter.card;

import android.content.Context;
import android.view.View;

import com.ly.framework.ui.progress.XProgressBar;
import com.ly.justsoso.sample.adapter.item.SampleItem;

import java.lang.ref.SoftReference;

/**
 * Created by ly on 2017/7/2.
 */

public class XProgressBarCard extends AbstractSampleCard {

    SoftReference<XProgressBar> mView;

    @Override
    public int getCardType() {
        return mSampleItem.itemType;
    }

    @Override
    public int getCardStyle() {
        return mSampleItem.itemStyle;
    }

    @Override
    public String getCardName() {
        return mSampleItem.itemName;
    }

    @Override
    public View getCardView(Context context) {

        if(mView == null || mView.get() == null) {
            XProgressBar view = new XProgressBar(context);
            mView = new SoftReference<>(view);
        }

        return mView.get();
    }

    @Override
    public void updateData(SampleItem item) {
        mSampleItem = item;
        mSampleItem.itemName = this.getClass().getSimpleName();
        mSampleItem.itemType = SampleCardTypeDef.CARD_TYPE_UI;

        if(mView != null && mView.get() instanceof XProgressBar) {
            XProgressBar xProgressBar = mView.get();
            xProgressBar.setIndeterminate(false);
            xProgressBar.setProgress(30);
        }

    }
}
