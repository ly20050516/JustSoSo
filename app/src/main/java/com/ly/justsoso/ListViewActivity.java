package com.ly.justsoso;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.ly.justsoso.base.item.PicassoItem;
import com.ly.justsoso.base.adaptor.PicassoListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    ListView mPicassoListView;
    PicassoListAdapter mPicassoListAdapter;
    private static String[] urls = {
            "http://c.hiphotos.baidu.com/image/h%3D200/sign=7b991b465eee3d6d3dc680cb73176d41/96dda144ad3459829813ed730bf431adcaef84b1.jpg",
            "http://b.hiphotos.baidu.com/image/h%3D200/sign=52b5924e8b5494ee982208191df4e0e1/c2fdfc039245d6887554a155a3c27d1ed31b24e8.jpg",
            "http://e.hiphotos.baidu.com/image/h%3D200/sign=3ef3e55ee7fe9925d40c6e5004a95ee4/8694a4c27d1ed21b0a2ed37eaa6eddc450da3f41.jpg",
            "http://img4.imgtn.bdimg.com/it/u=142800770,59313854&fm=21&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=3408447900,1234607085&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=16705507,1328875785&fm=21&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=4246510199,2069483326&fm=21&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=2174076011,1829033089&fm=21&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=1924893621,661118346&fm=21&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1898795798,2798655787&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=1296702992,1090972040&fm=21&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=1092490051,3393221619&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3217962789,3430649993&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3123432318,2547934550&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=1009239447,25444153&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=1142469417,341639617&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3598461135,406107685&fm=21&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=2618987948,1808310603&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=1129189053,135477607&fm=21&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2799957097,461761807&fm=21&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=2434382235,3799675822&fm=21&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3965787127,2687840196&fm=21&gp=0.jpg",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        mPicassoListView = (ListView) findViewById(R.id.picasso_list_view);
        initPicassoListView();
    }

    private void initPicassoListView(){
        mPicassoListAdapter = new PicassoListAdapter(initData(),this);
        mPicassoListView.setAdapter(mPicassoListAdapter);
        mPicassoListAdapter.notifyDataSetChanged();
    }

    private List<PicassoItem> initData(){
        List<PicassoItem> lists = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            PicassoItem item = new PicassoItem();
            item.url = urls[i];
            item.description = "description " + i;
            lists.add(item);
        }
        return lists;
    }
}
