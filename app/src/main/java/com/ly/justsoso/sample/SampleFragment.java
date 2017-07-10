package com.ly.justsoso.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ly.framework.ui.layout.WindowLayerLayout;
import com.ly.justsoso.R;
import com.ly.justsoso.enjoypictures.ui.SpacesItemDecoration;
import com.ly.justsoso.sample.adapter.ActionProcessor;
import com.ly.justsoso.sample.adapter.SampleRecycleViewAdapter;
import com.ly.justsoso.sample.adapter.card.SampleCardStyleDef;
import com.ly.justsoso.sample.adapter.card.SampleCardTypeDef;
import com.ly.justsoso.sample.adapter.item.SampleItem;
import com.ly.justsoso.sample.ui.SampleCategoryView;


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
        SampleCategoryView sampleCategoryView = new SampleCategoryView(getContext());
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

    }
}
