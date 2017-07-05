package com.ly.justsoso.sample.adapter.card;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ly.justsoso.sample.adapter.item.SampleItem;

import java.lang.ref.SoftReference;

/**
 * Created by ly on 2017/7/3.
 */

public class SampleNormalCard extends AbstractSampleCard {

    SoftReference<TextView> mView;

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
            TextView textView = new TextView(context);
            mView = new SoftReference<>(textView);
        }
        return mView.get();
    }

    @Override
    public void updateData(SampleItem item) {

        mSampleItem = item;

        if(mView != null && mView.get() != null) {
            TextView textView = mView.get();
            textView.setText("SampleNormalCard");
        }
    }
}
