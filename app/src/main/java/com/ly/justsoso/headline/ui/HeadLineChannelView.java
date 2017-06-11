package com.ly.justsoso.headline.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ly.justsoso.R;
import com.ly.justsoso.base.ui.SpacesItemDecoration;
import com.ly.justsoso.headline.bean.NewsItem;
import com.ly.justsoso.headline.bean.NewsList;
import com.ly.justsoso.headline.common.RequestNewsList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by LY on 2017-06-11.
 */

public class HeadLineChannelView extends AbstractChannelView {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    HeadLineChannelAdapter mRecyclerViewAdapter;
    RequestNewsList mRequestNewsList;
    public HeadLineChannelView(@NonNull Context context,RequestList requestList) {
        super(context,requestList);
        LayoutInflater.from(context).inflate(R.layout.head_line_channel_view,this,true);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.head_line_channel_swipe);
        mRecyclerView = (RecyclerView) findViewById(R.id.head_line_channel_recycle);


    }

    @Override
    public void addNewsList(NewsList newsList) {

        Observable.just(newsList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        int size = newsList.getList().size() + mRecyclerViewAdapter.mNewsItemList.size();
                        int page = size % 10 == 0 ? size / 10 : size / 10 + 1;
                        mRecyclerViewAdapter.mNewsItemList.addAll(newsList.getList());
                        mRecyclerViewAdapter.notifyDataSetChanged();
                        mRequestNewsList.setPage(page);
                        mRecyclerViewAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void lastInit() {
        mRequestNewsList = new RequestNewsList();
        mRequestNewsList.setType(getViewPageType());
        mRequestNewsList.setPage(1);
        mRequestNewsList.setLimit(10);

        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRequestList.requestList(mRequestNewsList,HeadLineChannelView.this);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(16));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewAdapter = new HeadLineChannelAdapter();
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRequestList.requestList(mRequestNewsList,HeadLineChannelView.this);
    }

    class HeadLineChannelAdapter extends RecyclerView.Adapter<HeadLineChannelAdapter.RecyclerViewHolder>{

        List<NewsItem> mNewsItemList = new ArrayList<>();

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.head_line_channel_item,parent,false);
            return new RecyclerViewHolder(view);
        }


        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            NewsItem item = mNewsItemList.get(position);
            if(item == null || TextUtils.isEmpty(item.getImgurl())){
                return;
            }
            Picasso.with(getContext()).load(item.getImgurl()).placeholder(R.drawable.user_placeholder).error(R.drawable.user_placeholder).into(holder.img);
            holder.title.setText(item.getTitle());
        }


        @Override
        public int getItemCount() {
            return mNewsItemList.size();
        }

        class RecyclerViewHolder extends  RecyclerView.ViewHolder{
            ImageView img;
            TextView title;
            public RecyclerViewHolder(View itemView) {
                super(itemView);
                img = (ImageView) itemView.findViewById(R.id.head_line_item_image);
                title = (TextView) itemView.findViewById(R.id.head_line_item_title);
            }
        }
    }
}
