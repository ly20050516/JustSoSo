package com.ly.justsoso.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ly.framework.ui.layout.WindowLayerLayout;
import com.ly.justsoso.R;
import com.ly.justsoso.sample.adapter.card.SampleCardAction;
import com.ly.justsoso.sample.adapter.card.SampleCardId;
import com.ly.justsoso.sample.adapter.item.SampleItem;
import com.ly.justsoso.sample.ui.SampleCategoryView;
import com.ly.justsoso.sample.ui.detail.AbstractDetailView;
import com.ly.justsoso.sample.ui.detail.SampleColorMatrixView;
import com.ly.justsoso.sample.ui.detail.SampleDetailViewFactory;
import com.ly.justsoso.sample.ui.detail.SampleFlowView;


/**
 * Created by ly on 2017/6/26.
 */

public class SampleFragment extends Fragment implements SampleContract.View{

    WindowLayerLayout mWindowLayerLayout;
    SampleContract.Presenter mSamplePresenter;
    public static SampleFragment newInstance() {
        return new SampleFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_sample,container,false);
        mWindowLayerLayout = (WindowLayerLayout) root.findViewById(R.id.root_window_layer_layout);
        SampleCategoryView sampleCategoryView = new SampleCategoryView(getContext(),mSamplePresenter);
        WindowLayerLayout.LayoutParams layoutParams = new WindowLayerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWindowLayerLayout.addView(sampleCategoryView,layoutParams);
        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void setPresenter(SampleContract.Presenter presenter) {
        mSamplePresenter = presenter;

    }

    @Override
    public void onItemClick(int action,SampleItem sampleItem) {
        if(sampleItem == null) {
            return;
        }

        if(sampleItem.cardId == SampleCardId.CARD_ID_XPROGRESSBAR) {

        }else if(sampleItem.cardId == SampleCardId.CARD_ID_FLOW_LAYOUT) {
            onFlowLayout(action,sampleItem);
        }else if(sampleItem.cardId == SampleCardId.CARD_ID_COLOR_MATRIX) {
            onColorMatrix(action,sampleItem);
        }else if(sampleItem.cardId == SampleCardId.CARD_ID_VIDEOVIEW) {
            onVideoView(action,sampleItem);
        }
    }

    private void onActionItemClick(SampleItem sampleItem) {
        AbstractDetailView view = SampleDetailViewFactory.generate(getContext(),sampleItem);
        mWindowLayerLayout.addView(view);

    }
    private void onVideoView(int action, SampleItem sampleItem) {
        if(action == SampleCardAction.action_item_click) {
            onActionItemClick(sampleItem);
        }
    }

    private void onColorMatrix(int action, SampleItem sampleItem) {
        if(action == SampleCardAction.action_item_click) {
            onActionItemClick(sampleItem);
        }
    }

    private void onFlowLayout(int action,SampleItem sampleItem) {
        if(action == SampleCardAction.action_item_click) {
            onActionItemClick(sampleItem);
        }
    }
}
