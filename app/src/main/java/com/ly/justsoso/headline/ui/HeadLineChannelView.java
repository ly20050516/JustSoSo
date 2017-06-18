package com.ly.justsoso.headline.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ly.framework.mvp.BaseDataSource;
import com.ly.justsoso.R;
import com.ly.justsoso.base.ui.SpacesItemDecoration;
import com.ly.justsoso.headline.HeadLineContract;
import com.ly.justsoso.headline.bean.NewsItem;
import com.ly.justsoso.headline.bean.NewsList;
import com.ly.justsoso.headline.request.RequestNewsList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by LY on 2017-06-11.
 */

public class HeadLineChannelView extends AbstractChannelView{

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    HeadLineChannelAdapter mRecyclerViewAdapter;
    HeadLineContract.Presenter mListPresenter;
    RequestNewsList mRequestNewsList;
    public HeadLineChannelView(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.head_line_channel_view,this,true);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.head_line_channel_swipe);
        mRecyclerView = (RecyclerView) findViewById(R.id.head_line_channel_recycle);


    }

    private void addNewsList(NewsList newsList) {
        Observable.just(newsList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        if(newsList == null) {
                            mSwipeRefreshLayout.setRefreshing(false);
                            return;
                        }
                        int page = mRequestNewsList.getPage() + 1;
                        if(mRecyclerViewAdapter.mNewsItemList.size() == 0) {
                            mRecyclerViewAdapter.mNewsItemList.addAll(newsList.getList());
                        }else {
                            mRecyclerViewAdapter.mNewsItemList.addAll(0,newsList.getList());
                        }
                        mRecyclerViewAdapter.notifyDataSetChanged();
                        mRequestNewsList.setPage(page);
                        mRecyclerViewAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    private void lastInit() {
        mRequestNewsList = new RequestNewsList();
        mRequestNewsList.setType(getViewPageType());
        mRequestNewsList.setPage(1);
        mRequestNewsList.setLimit(10);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRequestListRefresh();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(16));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewAdapter = new HeadLineChannelAdapter();
        mRecyclerViewAdapter.mOnClickItem = new OnClickItem() {
            @Override
            public void onClickItem(View view, int postion, NewsItem newsItem) {
                mListPresenter.itemClick(newsItem);
            }
        };
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        onRequestListRefresh();
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void onRequestListRefresh() {
        mListPresenter.requestList(mRequestNewsList,new BaseDataSource.DataLoadCallback<NewsList>(){

            @Override
            public void onDataLoadComplete(NewsList newsList) {
                addNewsList(newsList);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void setPresenter(HeadLineContract.Presenter presenter) {
        mListPresenter = presenter;
        lastInit();
    }

    class HeadLineChannelAdapter extends RecyclerView.Adapter<HeadLineChannelAdapter.RecyclerViewHolder>{

        List<NewsItem> mNewsItemList = new ArrayList<>();
        OnClickItem mOnClickItem = null;
        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.head_line_channel_item,parent,false);
            return new RecyclerViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
            final NewsItem item = mNewsItemList.get(position);
            if(!TextUtils.isEmpty(item.getImgurl())) {
                Picasso.with(getContext()).load(item.getImgurl()).placeholder(R.drawable.user_placeholder).error(R.drawable.user_placeholder).into(holder.img);
            }else{
                holder.img.setImageResource(R.drawable.user_placeholder);
            }
            holder.title.setText(item.getTitle());
            holder.root.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickItem.onClickItem(holder.root,position,item);
                }
            });
        }


        @Override
        public int getItemCount() {
            return mNewsItemList.size();
        }

        class RecyclerViewHolder extends  RecyclerView.ViewHolder{
            ImageView img;
            TextView title;
            CardView root;
            public RecyclerViewHolder(View itemView) {
                super(itemView);
                root = (CardView) itemView.findViewById(R.id.head_line_item_root);
                img = (ImageView) itemView.findViewById(R.id.head_line_item_image);
                title = (TextView) itemView.findViewById(R.id.head_line_item_title);
            }
        }
    }

    interface OnClickItem{
        void onClickItem(View view,int postion,NewsItem newsItem);
    }
}
