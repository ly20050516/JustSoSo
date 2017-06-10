package com.ly.justsoso.headline;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ly.justsoso.R;

/**
 * Created by LY on 2017-06-10.
 */

public class HeadLineFragment extends Fragment implements HeadLineContract.View {

    HeadLineContract.Presenter mHeadLinePresenter;

    public HeadLineFragment(){
    }

    public static HeadLineFragment newInstance() {
        return new HeadLineFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_today_headline,container,false);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mHeadLinePresenter.start();
    }

    @Override
    public void setPresenter(HeadLineContract.Presenter presenter) {
        mHeadLinePresenter = presenter;
    }
}
