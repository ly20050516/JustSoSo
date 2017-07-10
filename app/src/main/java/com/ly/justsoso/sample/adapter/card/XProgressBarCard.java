package com.ly.justsoso.sample.adapter.card;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ly.framework.ui.progress.XProgressBar;
import com.ly.framework.utilities.DisplayUtil;
import com.ly.justsoso.sample.adapter.item.SampleItem;

import org.w3c.dom.Text;

import java.lang.ref.SoftReference;

/**
 * Created by ly on 2017/7/2.
 */

public class XProgressBarCard extends AbstractSampleCard {

    SoftReference<LinearLayout> mView;

    private static final int VIEW_ID_XPROGRESSBAR = 0X00000001;
    private static final int VIEW_ID_TV_DESC = 0X00000002;

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
            LinearLayout container = new LinearLayout(context);
            container.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            int margin = DisplayUtil.dp2px(context,10);
            params.leftMargin = margin;
            params.rightMargin = margin;
            container.setGravity(Gravity.CENTER);
            XProgressBar xProgressBar = new XProgressBar(context);
            xProgressBar.setId(VIEW_ID_XPROGRESSBAR);
            container.addView(xProgressBar,params);

            TextView textView = new TextView(context);
            textView.setId(VIEW_ID_TV_DESC);
            textView.setGravity(Gravity.CENTER);
            container.addView(textView,params);

            mView = new SoftReference<>(container);
        }

        return mView.get();
    }

    @Override
    public void updateData(SampleItem item) {
        mSampleItem = item;
        if(mView != null && mView.get() instanceof LinearLayout) {
            LinearLayout linearLayout = mView.get();
            XProgressBar xProgressBar = (XProgressBar) linearLayout.findViewById(VIEW_ID_XPROGRESSBAR);
            xProgressBar.setIndeterminate(false);
            xProgressBar.setProgress(30);

            TextView textView = (TextView) linearLayout.findViewById(VIEW_ID_TV_DESC);
            textView.setText(item.itemDesc);
        }

    }
}
