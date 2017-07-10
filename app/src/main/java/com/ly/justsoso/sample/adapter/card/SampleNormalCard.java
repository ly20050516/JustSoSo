package com.ly.justsoso.sample.adapter.card;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ly.justsoso.sample.adapter.item.SampleItem;

import java.lang.ref.SoftReference;

/**
 * Created by ly on 2017/7/3.
 */

public class SampleNormalCard extends AbstractSampleCard {

    SoftReference<LinearLayout> mView;
    private static final int VIEW_ID_TV_DESC = 0X00000001;

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
            LinearLayout linearLayout = new LinearLayout(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setGravity(Gravity.CENTER);
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            linearLayout.addView(textView,layoutParams);
            textView.setId(VIEW_ID_TV_DESC);
            mView = new SoftReference<>(linearLayout);
        }
        return mView.get();
    }

    @Override
    public void updateData(SampleItem item) {

        mSampleItem = item;

        if(mView != null && mView.get() != null) {
            LinearLayout linearLayout = mView.get();
            TextView textView = (TextView) linearLayout.findViewById(VIEW_ID_TV_DESC);
            textView.setText(item.itemDesc);
        }
    }
}
