package com.ly.picassostudy;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SearchView;

import com.alibaba.fastjson.JSON;
import com.ly.picassostudy.bean.Pictures;
import com.ly.picassostudy.listview.DetailImageView;
import com.ly.picassostudy.listview.PicassoRecycleItem;
import com.ly.picassostudy.listview.RecycleViewAdapter;
import com.ly.picassostudy.listview.SpacesItemDecoration;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RecycleViewActivity extends AppCompatActivity {

    public static final String TAG = RecycleViewActivity.class.getCanonicalName();

    final static String KEY_WORD = "KEY_WORD";
    final static String PAGE_NO = "PAGE_NO";
    final static String PAGE_COUNT = "PAGE_COUNT";
    // static final String PICTURE_SERVER = "http://192.168.3.13:8080/PictureServer/GetPictures";
    static final String PICTURE_SERVER = "http://192.168.3.13:8080/PictureServer/SpringMVC/GetPictures";

    static String[] URLS = {
            "http://b.hiphotos.baidu.com/zhidao/pic/item/eaf81a4c510fd9f9169aeb8c272dd42a2934a442.jpg",
            "http://pic67.nipic.com/file/20150514/21036787_181947848862_2.jpg",
            "http://c.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=4f582c74b4fb43161a4a727e15946a15/72f082025aafa40f39ce8262ad64034f78f01900.jpg",
            "http://img1.imgtn.bdimg.com/it/u=3444612337,3844090186&fm=21&gp=0.jpg",
            "http://h.hiphotos.baidu.com/zhidao/wh%3D600%2C800/sign=63f8974c03e93901565785384bdc78df/6c224f4a20a4462316d68a0e9a22720e0cf3d7b9.jpg",
            "http://a.hiphotos.baidu.com/zhidao/pic/item/d52a2834349b033b5b349bfe16ce36d3d539bd51.jpg",
            "http://e.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=75aaa91fa444ad342eea8f83e59220c2/0bd162d9f2d3572cf556972e8f13632763d0c388.jpg",
            "http://d.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=d8c645729513b07ebde8580c39e7bd15/a8014c086e061d95294ff56b7df40ad162d9ca5c.jpg",
            "http://pic0.qiyipic.com/common/20130322/df0d316a18a448e18bad0923e516c284.jpg",
            "http://pic51.nipic.com/file/20141016/18941053_114429044000_2.jpg",
            "http://b.hiphotos.baidu.com/zhidao/pic/item/d1160924ab18972b45907eb7e2cd7b899e510a20.jpg",
            "http://img0.imgtn.bdimg.com/it/u=2537677985,1540529059&fm=21&gp=0.jpg",
            "http://g.hiphotos.baidu.com/zhidao/pic/item/eac4b74543a98226b1599e898b82b9014b90eb80.jpg",
            "http://g.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=c5b0327128dda3cc0bb1b02434d91537/a1ec08fa513d2697d768a3b353fbb2fb4216d8a1.jpg",
            "http://f.hiphotos.baidu.com/zhidao/pic/item/aa18972bd40735fa735731399d510fb30e24088e.jpg",
            "http://c.hiphotos.baidu.com/zhidao/pic/item/d788d43f8794a4c22fe6ab9408f41bd5ac6e3943.jpg",
            "http://f.hiphotos.baidu.com/zhidao/pic/item/8b82b9014a90f60326b707453b12b31bb051eda9.jpg",
            "http://c.hiphotos.baidu.com/zhidao/pic/item/242dd42a2834349b118764e3caea15ce37d3bed6.jpg",
            "http://g.hiphotos.baidu.com/zhidao/pic/item/50da81cb39dbb6fdc4b794a70824ab18962b3746.jpg",
            "http://a.hiphotos.baidu.com/zhidao/pic/item/b3fb43166d224f4a60e8b1cb0bf790529922d1f1.jpg",
            "http://img8.cache.hxsd.com/hxsdmy/gallery/2013/01/88/48/61/03/28/134037443/134037443_7.jpg",
            "http://f.hiphotos.baidu.com/zhidao/pic/item/0dd7912397dda14430a39448b0b7d0a20df486a6.jpg",
            "http://cdn2.image.apk.gfan.com/asdf/PImages/2014/8/6/912904_2e2fcb5e9-9717-433b-87c6-67f3856d6dbf.jpg",
            "http://g.hiphotos.baidu.com/zhidao/pic/item/ae51f3deb48f8c54ca20c4f23b292df5e1fe7f7f.jpg",
            "http://g.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=85b8146aa78b87d65017a31b3238040e/fd039245d688d43fb13037a97b1ed21b0ff43bcb.jpg",
            "http://d.hiphotos.baidu.com/zhidao/pic/item/1c950a7b02087bf44969b885f4d3572c10dfcfd1.jpg",
            "http://imgsrc.baidu.com/forum/w%3D580/sign=915d42037ed98d1076d40c39113eb807/8db1cb134954092362d4b7819058d109b2de4994.jpg",
            "http://c.hiphotos.baidu.com/zhidao/pic/item/2f738bd4b31c870113fcdcf6217f9e2f0708ff5c.jpg",
            "http://img5.pcpop.com/bizhi/big/10/142/413/10142413.jpg",
            "http://c.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=9742b125133853438c9a8f25a6239c48/29381f30e924b8990b693b716d061d950a7bf626.jpg",
            "http://f.hiphotos.baidu.com/zhidao/pic/item/a6efce1b9d16fdfa9276bc6eb78f8c5494ee7b13.jpg",
            "http://imgsrc.baidu.com/forum/w=580/sign=a002ccf4662762d0803ea4b790ee0849/43018618367adab4430368948ed4b31c8601e42a.jpg",
            "http://c.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=e1d4d1168501a18bf0be1a4bab1f2b3e/c2fdfc039245d688be3fe59ea6c27d1ed21b24ce.jpg",
            "http://d.hiphotos.baidu.com/zhidao/pic/item/562c11dfa9ec8a13c8621b5ff103918fa0ecc011.jpg",
            "http://d.hiphotos.baidu.com/zhidao/pic/item/f9dcd100baa1cd1151dcd92fbd12c8fcc3ce2d52.jpg",
            "http://imgsrc.baidu.com/forum/w%3D580/sign=12d1cf12a1cc7cd9fa2d34d109002104/5edf8db1cb134954b75d3c9b544e9258d0094a8a.jpg"
    };


    RecyclerView mRecycleView;
    RecycleViewAdapter mRecycleViewAdapter;
    DetailImageView mImageView;
    ViewGroup.LayoutParams mImageViewParams;
    FrameLayout mFrameLayout;
    SearchOption mSearchOption;

    SwipeRefreshLayout mSwipeRefreshLayout;
    StaggeredGridLayoutManager  mStaggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d(TAG, "onCreate: query " + query);
        }

        mRecycleView = (RecyclerView) findViewById(R.id.picasso_recycle_view);
        mFrameLayout = (FrameLayout) findViewById(R.id.root_activity_recycle_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.picasso_swipe_refresh_layout);
        mSwipeRefreshLayout.setRefreshing(true);

        initSwipeRefreshLayout();
        mSearchOption = new SearchOption();

        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(mStaggeredGridLayoutManager);
        mRecycleViewAdapter = new RecycleViewAdapter(this, null);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.addItemDecoration(new SpacesItemDecoration(16));
        mRecycleView.setHasFixedSize(true);
        initRecycleViewLoadMore();
        initOnClickListener(mRecycleViewAdapter);
        mRecycleView.setAdapter(mRecycleViewAdapter);

        searchPictureFromPictureServer(mSearchOption);
    }

    @Override
    protected void onNewIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d(TAG, "onNewIntent: query " + query);
            mSearchOption.keywords = query;
            mSearchOption.pageNo = 0;
            mSearchOption.requestCounts = 40;
            searchPictureFromPictureServer(mSearchOption);

        }
        super.onNewIntent(intent);
    }

    @Override
    public void onBackPressed() {
        if (mImageView!= null && mImageView.isShowing()) {
            mImageView.hide();
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recycle_view, menu);

        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
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

    void initSwipeRefreshLayout(){
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSearchOption.pageNo = 0;
                mSearchOption.requestCounts = 40;
                searchPictureFromPictureServer(mSearchOption);
            }
        });
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
                        if(mSearchOption.responesCounts >= mSearchOption.requestCounts){
                            mSearchOption.pageNo = mSearchOption.pageNo + mSearchOption.requestCounts;
                            mSearchOption.responesCounts = 0;
                            searchPictureFromPictureServer(mSearchOption);
                        }
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }
    void searchPictureFromPictureServer(SearchOption sOption){
        try {
            okhttpFromPictureServer(sOption);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    void okhttpFromPictureServer(SearchOption sOption) throws UnsupportedEncodingException {

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBuild = new FormBody.Builder().add(KEY_WORD, URLEncoder.encode(sOption.keywords,"utf-8")).add(PAGE_NO,"" + sOption.pageNo).add(PAGE_COUNT,"" + sOption.requestCounts).build();
        Request request = new Request.Builder().url(PICTURE_SERVER).post(formBuild).build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.d(TAG, "onFailure: " + e.getMessage());
                e.printStackTrace();
                mRecycleViewAdapter.setmDatas(initDataFromURLS());
                H.sendEmptyMessage(MSG_NOTIFY_RECYCLE_ADAPTR_CHANGED);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonString = response.body().string();
                Log.d(TAG, "onResponse: jsonString " + jsonString);
                Pictures pictures = JSON.parseObject(jsonString,Pictures.class);
                Log.d(TAG, "onResponse: getClientName " + pictures.getClientName());
                mSearchOption.responesCounts = pictures.getRealCounts();
                if(mSearchOption.pageNo == 0)
                     mRecycleViewAdapter.setmDatas(initDataFromePictureServer(pictures));
                else
                    mRecycleViewAdapter.addDatas(initDataFromePictureServer(pictures));
                H.sendEmptyMessage(MSG_NOTIFY_RECYCLE_ADAPTR_CHANGED);
            }
        });
    }
    List initDataFromURLS() {
        List<PicassoRecycleItem> lists = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < URLS.length; i++) {
            PicassoRecycleItem item = new PicassoRecycleItem();
            item.url = URLS[i];
            item.width = 500 + random.nextInt(60);
            item.height = 600 + random.nextInt(100);
            lists.add(item);
        }


        return lists;
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
    void initOnClickListener(RecycleViewAdapter adapter) {
        adapter.setmOnClickItemListener(new RecycleViewAdapter.onClickItemListener() {
            @Override
            public void onClickImage(View view, int position, PicassoRecycleItem item) {
                Log.d(TAG, "onClickImage: url " + item.url);
                showImageDetail(item);
            }
        });
    }

    void showImageDetail(PicassoRecycleItem item) {
        if (mImageView == null) {
            mImageView = new DetailImageView(this);
            mImageViewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        mFrameLayout.removeView(mImageView);
        mFrameLayout.addView(mImageView, mImageViewParams);
        mImageView.show();
        Picasso.with(this).load(item.detailUrl).into(mImageView.mImageView);
    }

    class SearchOption{
        String keywords = "Android";
        int pageNo = 0;
        int requestCounts = 40;
        int responesCounts;
    }
}
