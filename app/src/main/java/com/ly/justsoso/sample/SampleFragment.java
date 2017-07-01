package com.ly.justsoso.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ly.justsoso.R;
import com.ly.justsoso.enjoypictures.ui.SpacesItemDecoration;
import com.ly.justsoso.sample.adapter.ActionProcessor;
import com.ly.justsoso.sample.adapter.SampleRecycleViewAdapter;
import com.ly.justsoso.sample.adapter.card.SampleCardStyleDef;
import com.ly.justsoso.sample.adapter.card.SampleCardTypeDef;
import com.ly.justsoso.sample.adapter.item.SampleItem;


/**
 * Created by ly on 2017/6/26.
 */

public class SampleFragment extends Fragment implements SampleContract.View{

    RecyclerView mRecycleView;
    SampleRecycleViewAdapter mRecycleViewAdapter;

    SampleContract.Presenter mSamplePresenter;
    public static SampleFragment newInstance() {
        return new SampleFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_sample,container,false);
        mRecycleView = (RecyclerView) root.findViewById(R.id.recycle_view_sample);
        init();
        return root;
    }

    private void init() {
        mRecycleViewAdapter = new SampleRecycleViewAdapter(getContext());
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycleView.addItemDecoration(new SpacesItemDecoration(5));
        mRecycleView.setAdapter(mRecycleViewAdapter);
        mRecycleViewAdapter.setActionProcessor(new ActionProcessor() {
            @Override
            public void action(int action, SampleItem sampleItem) {

            }
        });

        SampleItem item = new SampleItem();
        item.itemStyle = SampleCardStyleDef.CARD_STYLE_UI_XPROGRESS_BAR;
        item.itemType = SampleCardTypeDef.CARD_TYPE_UI;
        item.itemName = "XProgressBarCard";
        mRecycleViewAdapter.add(item);
        mRecycleViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void setPresenter(SampleContract.Presenter presenter) {
        mSamplePresenter = presenter;

    }

}
