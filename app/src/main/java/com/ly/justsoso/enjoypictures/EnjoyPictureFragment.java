package com.ly.justsoso.enjoypictures;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ly.justsoso.R;
import com.ly.justsoso.base.item.PicassoRecycleItem;
import com.ly.justsoso.base.ui.DetailImageView;
import com.ly.justsoso.base.adaptor.RecycleViewAdapter;
import com.ly.justsoso.base.ui.SpacesItemDecoration;
import com.ly.justsoso.enjoypictures.bean.Pictures;
import com.ly.justsoso.enjoypictures.common.SearchOption;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by LY on 7/3/2016.
 */
public class EnjoyPictureFragment extends Fragment implements EnjoyPictureContract.View{

    public static final String TAG = "EnjoyPictureFragment";
    RecyclerView mRecycleView;
    RecycleViewAdapter mRecycleViewAdapter;
    DetailImageView mDetailImageView;
    ViewGroup.LayoutParams mImageViewParams;
    FrameLayout mFrameLayout;
    SwipeRefreshLayout mSwipeRefreshLayout;
    StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    EnjoyPictureContract.Presenter mEnjoyPicturePresenter;

    public EnjoyPictureFragment(){
        // do nothing
    }
    public static EnjoyPictureFragment newInstance(){
        return new EnjoyPictureFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecycleViewAdapter = new RecycleViewAdapter(getContext(), null);
        initOnClickListener(mRecycleViewAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_recycle_view,container,false);
        mRecycleView = (RecyclerView) root.findViewById(R.id.picasso_recycle_view);
        mFrameLayout = (FrameLayout) root.findViewById(R.id.root_activity_recycle_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.picasso_swipe_refresh_layout);

        mSwipeRefreshLayout.setRefreshing(true);
        initSwipeRefreshLayout();

        mRecycleView.setLayoutManager(mStaggeredGridLayoutManager);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.addItemDecoration(new SpacesItemDecoration(16));
        mRecycleView.setHasFixedSize(true);
        initRecycleViewLoadMore();
        mRecycleView.setAdapter(mRecycleViewAdapter);

        return root;
    }

    @Override
    public void onResume() {
        mEnjoyPicturePresenter.start();
        super.onResume();
    }

    @Override
    public void setPresenter(EnjoyPictureContract.Presenter presenter) {
        mEnjoyPicturePresenter = presenter;
        H.sendEmptyMessage(MSG_NOTIFY_RECYCLE_ADAPTR_CHANGED);
    }

    @Override
    public void setDatas(Pictures pictures) {

        mRecycleViewAdapter.setmDatas(initDataFromePictureServer(pictures));
        H.sendEmptyMessage(MSG_NOTIFY_RECYCLE_ADAPTR_CHANGED);
    }

    @Override
    public void addDatas(Pictures pictures) {
        mRecycleViewAdapter.addDatas(initDataFromePictureServer(pictures));
        H.sendEmptyMessage(MSG_NOTIFY_RECYCLE_ADAPTR_CHANGED);
    }

    List initDataFromePictureServer(Pictures picture){

        List<PicassoRecycleItem> lists = new ArrayList<>();
        Random random = new Random();
        int N = picture.getPictures().size();
        for (int i = 0; i <N; i++) {
            PicassoRecycleItem item = new PicassoRecycleItem();
            item.url = picture.getPictures().get(i).getThumbURL();
            item.detailUrl = picture.getPictures().get(i).getObjURL();
            item.width = 500 + random.nextInt(60);
            item.height = 600 + random.nextInt(100);
            lists.add(item);
        }
        return lists;
    }
    void initRecycleViewLoadMore(){
        mRecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * Callback method to be invoked when RecyclerView's scroll state changes.
             *
             * @param recyclerView The RecyclerView whose scroll state has changed.
             * @param newState     The updated scroll state. One of {@link #SCROLL_STATE_IDLE},
             *                     {@link #SCROLL_STATE_DRAGGING} or {@link #SCROLL_STATE_SETTLING}.
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    int []positions = mStaggeredGridLayoutManager.findLastVisibleItemPositions(null);
                    if(null == positions)
                        return;
                    int max = positions[0];
                    for (int i = 1; i <positions.length; i++) {
                        if(max < positions[i]){
                            max = positions[i];
                        }
                    }
                    if(mRecycleViewAdapter.getItemCount() == max + 1){
                        Log.d(TAG, "onScrollStateChanged: continueSearch");
                        mEnjoyPicturePresenter.continueSearch();
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }
    void initSwipeRefreshLayout(){
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "onRefresh: ");
                mEnjoyPicturePresenter.newSearch("Android");
            }
        });
    }
    void initOnClickListener(RecycleViewAdapter adapter) {
        adapter.setmOnClickItemListener(new RecycleViewAdapter.onClickItemListener() {
            @Override
            public void onClickImage(View view, int position, PicassoRecycleItem item) {
                showImageDetail(item);
            }
        });
    }
    void showImageDetail(PicassoRecycleItem item) {
        if (mDetailImageView == null) {
            mDetailImageView = new DetailImageView(getContext());
            mImageViewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        mFrameLayout.removeView(mDetailImageView);
        mFrameLayout.addView(mDetailImageView, mImageViewParams);
        mDetailImageView.show();
        Picasso.with(getContext()).load(item.detailUrl).into(mDetailImageView.mImageView);
    }

    public static final int MSG_NOTIFY_RECYCLE_ADAPTR_CHANGED = 100;
    Handler H = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_NOTIFY_RECYCLE_ADAPTR_CHANGED:
                {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mRecycleViewAdapter.notifyDataSetChanged();

                    break;
                }
            }
            super.handleMessage(msg);
        }
    };
}
