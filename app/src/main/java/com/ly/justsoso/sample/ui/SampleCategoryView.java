package com.ly.justsoso.sample.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.ly.justsoso.R;
import com.ly.justsoso.enjoypictures.ui.SpacesItemDecoration;
import com.ly.justsoso.sample.SampleContract;
import com.ly.justsoso.sample.adapter.ActionProcessor;
import com.ly.justsoso.sample.adapter.SampleRecycleViewAdapter;
import com.ly.justsoso.sample.adapter.card.SampleCardStyleDef;
import com.ly.justsoso.sample.adapter.card.SampleCardTypeDef;
import com.ly.justsoso.sample.adapter.item.SampleItem;

/**
 * Created by ly on 2017/7/9.
 */

public class SampleCategoryView extends FrameLayout {

    RecyclerView mRecycleView;
    SampleRecycleViewAdapter mRecycleViewAdapter;
    SampleContract.Presenter mSamplePresenter;

    public SampleCategoryView(@NonNull Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.sample_category_view,this,true);
        mRecycleView = (RecyclerView)findViewById(R.id.recycle_view_sample);

        init();


    }

    public void setPresenter(SampleContract.Presenter presenter) {
        mSamplePresenter = presenter;

    }
    private void init() {
        mRecycleViewAdapter = new SampleRecycleViewAdapter(getContext());
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycleView.addItemDecoration(new SpacesItemDecoration(5));
        mRecycleView.setAdapter(mRecycleViewAdapter);
        mRecycleViewAdapter.setActionProcessor(new ActionProcessor() {
            @Override
            public void action(int action, SampleItem sampleItem) {
                mSamplePresenter.itemClick(action,sampleItem);
            }
        });

        SampleItem item = new SampleItem();
        item.itemStyle = SampleCardStyleDef.CARD_STYLE_UI_XPROGRESS_BAR;
        item.itemType = SampleCardTypeDef.CARD_TYPE_UI;
        item.itemName = "XProgressBarCard";
        item.itemDesc = "自定义进度条";
        mRecycleViewAdapter.add(item);

        item = new SampleItem();
        item.itemStyle = SampleCardStyleDef.CARD_STYLE_UI_NORMAL;
        item.itemType = SampleCardTypeDef.CARD_TYPE_UI;
        item.itemName = "SampleNormalCard";
        item.itemDesc = "流式布局";

        mRecycleViewAdapter.add(item);

        mRecycleViewAdapter.notifyDataSetChanged();

    }
}
