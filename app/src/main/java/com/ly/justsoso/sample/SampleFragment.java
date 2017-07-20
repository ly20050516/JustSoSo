package com.ly.justsoso.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ly.framework.ui.layout.WindowLayerLayout;
import com.ly.justsoso.R;
import com.ly.justsoso.sample.adapter.card.SampleCardAction;
import com.ly.justsoso.sample.adapter.card.SampleCardId;
import com.ly.justsoso.sample.adapter.card.SampleCardTypeDef;
import com.ly.justsoso.sample.adapter.item.SampleItem;
import com.ly.justsoso.sample.ui.SampleCategoryView;
import com.ly.justsoso.sample.ui.detail.SampleFlowLayout;


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
        }
    }

    private void onFlowLayout(int action,SampleItem sampleItem) {
        if(action == SampleCardAction.action_item_click) {
            SampleFlowLayout layout = new SampleFlowLayout(getContext());
            mWindowLayerLayout.addView(layout);
        }
    }
}
