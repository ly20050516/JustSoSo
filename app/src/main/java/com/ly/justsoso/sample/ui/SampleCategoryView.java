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
import com.ly.justsoso.sample.adapter.card.SampleCardId;
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

    public SampleCategoryView(@NonNull Context context,SampleContract.Presenter presenter) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.sample_category_view,this,true);
        mRecycleView = (RecyclerView)findViewById(R.id.recycle_view_sample);
        setPresenter(presenter);
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
        item.cardStyle = SampleCardStyleDef.CARD_STYLE_UI_XPROGRESS_BAR;
        item.cardType = SampleCardTypeDef.CARD_TYPE_UI;
        item.cardName = "XProgressBarCard";
        item.cardDesc = "自定义进度条";
        item.cardId = SampleCardId.CARD_ID_XPROGRESSBAR;
        mRecycleViewAdapter.add(item);

        item = new SampleItem();
        item.cardStyle = SampleCardStyleDef.CARD_STYLE_UI_NORMAL;
        item.cardType = SampleCardTypeDef.CARD_TYPE_UI;
        item.cardName = "SampleNormalCard";
        item.cardDesc = "流式布局";
        item.cardId = SampleCardId.CARD_ID_FLOW_LAYOUT;
        mRecycleViewAdapter.add(item);

        item = new SampleItem();
        item.cardStyle = SampleCardStyleDef.CARD_STYLE_UI_NORMAL;
        item.cardType = SampleCardTypeDef.CARD_TYPE_UI;
        item.cardName = "SampleNormalCard";
        item.cardDesc = "ColorFilter";
        item.cardId = SampleCardId.CARD_ID_COLOR_FILTER;
        mRecycleViewAdapter.add(item);

        item = new SampleItem();
        item.cardStyle = SampleCardStyleDef.CARD_STYLE_UI_NORMAL;
        item.cardType = SampleCardTypeDef.CARD_TYPE_UI;
        item.cardName = "SampleNormalCard";
        item.cardDesc = "ColorMatrix";
        item.cardId = SampleCardId.CARD_ID_COLOR_MATRIX;
        mRecycleViewAdapter.add(item);

        item = new SampleItem();
        item.cardStyle = SampleCardStyleDef.CARD_STYLE_UI_NORMAL;
        item.cardType = SampleCardTypeDef.CARD_TYPE_UI;
        item.cardName = "SampleNormalCard";
        item.cardDesc = "MediaPlayer";
        item.cardId = SampleCardId.CARD_ID_MEDIAPLAYER;
        mRecycleViewAdapter.add(item);

        item = new SampleItem();
        item.cardStyle = SampleCardStyleDef.CARD_STYLE_UI_NORMAL;
        item.cardType = SampleCardTypeDef.CARD_TYPE_UI;
        item.cardName = "SampleNormalCard";
        item.cardDesc = "VideoView";
        item.cardId = SampleCardId.CARD_ID_VIDEOVIEW;
        mRecycleViewAdapter.add(item);

        mRecycleViewAdapter.notifyDataSetChanged();

    }
}
