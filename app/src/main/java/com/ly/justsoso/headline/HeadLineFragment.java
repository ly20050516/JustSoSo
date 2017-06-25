package com.ly.justsoso.headline;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ly.framework.ui.layout.WindowLayerLayout;
import com.ly.justsoso.R;
import com.ly.justsoso.headline.bean.NewsItem;
import com.ly.justsoso.headline.bean.ViewPageTabTitle;
import com.ly.justsoso.headline.ui.HeadLineChannelDetailView;
import com.ly.justsoso.headline.ui.HeadLineChannelView;
import com.ly.justsoso.headline.ui.HeadLineChannelViewFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LY on 2017-06-10.
 */

public class HeadLineFragment extends Fragment implements HeadLineContract.View,ViewPager.OnPageChangeListener{

    HeadLineContract.Presenter mHeadLinePresenter;

    WindowLayerLayout mWindowLayerLayoutRoot;
    ViewPager mViewPager;
    PagerTabStrip mPagerTabShip;
    List<ViewPageTabTitle> mTabTitle;
    List<HeadLineChannelView> mTabViews;
    HeadLineChannelDetailView mHeadLineChannelDetailView;

    String[] channelIds = new String[]{"war", "sport", "tech", "edu", "ent", "money", "gupiao", "travel", "lady"};
    int[] channelNameIds = new int[]{R.string.type_title_war, R.string.type_title_sport, R.string.type_title_tech, R.string.type_title_edu, R.string.type_title_ent, R.string.type_title_money, R.string.type_title_gupiao, R.string.type_title_travel, R.string.type_title_lady};

    public HeadLineFragment() {
    }

    public static HeadLineFragment newInstance() {
        return new HeadLineFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_headline, container, false);

        mViewPager = (ViewPager) root.findViewById(R.id.viewpager_list);
        mPagerTabShip = (PagerTabStrip) root.findViewById(R.id.pager_tab_strip_list);
        mWindowLayerLayoutRoot = (WindowLayerLayout) root.findViewById(R.id.head_line_root);

        mTabTitle = new ArrayList<>();
        mTabViews = new ArrayList<>();
        for (int i = 0;i < channelIds.length;i ++) {
            ViewPageTabTitle title = new ViewPageTabTitle();
            title.setChannelId(channelIds[i]);
            title.setChannelName(getString(channelNameIds[i]));
            mTabTitle.add(title);
            HeadLineChannelView headLineListView = (HeadLineChannelView) HeadLineChannelViewFactory.getChannelView(getContext(), HeadLineChannelViewFactory.CHANNEL_VIEW_NORMAL);
            headLineListView.setViewPageTabTitle(title);
            headLineListView.setPresenter(mHeadLinePresenter);
            mTabViews.add(headLineListView);
        }

        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mTabViews.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mTabViews.get(position));
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mTabViews.get(position));
                return mTabViews.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTabTitle.get(position).getChannelName();
            }
        });


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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onItemClick(NewsItem newsItem) {
        if(mHeadLineChannelDetailView == null) {
            mHeadLineChannelDetailView = new HeadLineChannelDetailView(getContext());
            mHeadLineChannelDetailView.setHeadLineDetailPresenter(mHeadLinePresenter);
        }

        mWindowLayerLayoutRoot.addView(mHeadLineChannelDetailView);
        mHeadLineChannelDetailView.updateView(newsItem);

    }
}
