package com.ly.justsoso;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.ly.framework.utilities.ActivityUtils;
import com.ly.framework.utilities.ConstantsUtil;
import com.ly.justsoso.enjoypictures.EnjoyPictureFragment;
import com.ly.justsoso.enjoypictures.EnjoyPicturePresenter;
import com.ly.justsoso.enjoypictures.EnjoyPictureRepository;
import com.ly.justsoso.enjoypictures.data.local.LocalDataSource;
import com.ly.justsoso.enjoypictures.data.remote.RemoteDataSource;
import com.ly.justsoso.osc.OSCFragment;
import com.ly.justsoso.osc.OSCPresenter;
import com.ly.justsoso.osc.OSCRepository;
import com.ly.justsoso.headline.HeadLineFragment;
import com.ly.justsoso.headline.HeadLinePresenter;
import com.ly.justsoso.sample.SampleFragment;
import com.ly.justsoso.sample.SamplePresenter;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,SearchView.OnQueryTextListener{

    public static final String TAG = "MainActivity";
    DrawerLayout mRootDrawerLayout;
    InputMethodManager inputMethodManager;
    EnjoyPicturePresenter mEnjoyPicturePresenter;
    OSCPresenter mGameCenterPresenter;
    HeadLinePresenter mHeadLinePresenter;
    SamplePresenter mSamplePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRootDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mRootDrawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mRootDrawerLayout.setDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        Picasso.with(this).setIndicatorsEnabled(true);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    public void onBackPressed() {
        if(mRootDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mRootDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int menuId = item.getItemId();
        if(menuId == R.id.nav_picture_enjoy){
            handlePictureEnjoy();
        }else if(menuId == R.id.nav_today_headline){
            handleTodayHeadLine();
        }else if(menuId == R.id.nav_sample){
            handleSample();
        }else if(menuId == R.id.nav_osc){
            handleOSC();
        }
        mRootDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void handlePictureEnjoy(){
        EnjoyPictureFragment enjoyPictureFragment = (EnjoyPictureFragment) getSupportFragmentManager().findFragmentByTag(ConstantsUtil.FRAGMENT_ENJOY_PICTURE);
        if(null == enjoyPictureFragment){
            enjoyPictureFragment = EnjoyPictureFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),enjoyPictureFragment,R.id.contentFrame, ConstantsUtil.FRAGMENT_ENJOY_PICTURE);
            EnjoyPictureRepository er = new EnjoyPictureRepository(new LocalDataSource(this),new RemoteDataSource());
            mEnjoyPicturePresenter = new EnjoyPicturePresenter(er,enjoyPictureFragment);
            enjoyPictureFragment.setPresenter(mEnjoyPicturePresenter);
        }else{
            ActivityUtils.showFragmentToActivity(getSupportFragmentManager(),enjoyPictureFragment);
        }
    }

    private void handleTodayHeadLine(){
        HeadLineFragment headLineFragment = (HeadLineFragment) getSupportFragmentManager().findFragmentByTag(ConstantsUtil.FRAGMENT_HEADLINE);
        if(null == headLineFragment) {
            headLineFragment = HeadLineFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),headLineFragment,R.id.contentFrame,ConstantsUtil.FRAGMENT_HEADLINE);
            mHeadLinePresenter = new HeadLinePresenter(headLineFragment);
            headLineFragment.setPresenter(mHeadLinePresenter);
        }else{
            ActivityUtils.showFragmentToActivity(getSupportFragmentManager(),headLineFragment);
        }
    }

    private void handleSample(){
        SampleFragment sampleFragment = (SampleFragment) getSupportFragmentManager().findFragmentByTag(ConstantsUtil.FRAGMENT_SAMPLE);
        if(null == sampleFragment) {
            sampleFragment = SampleFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),sampleFragment,R.id.contentFrame,ConstantsUtil.FRAGMENT_SAMPLE);
            mSamplePresenter = new SamplePresenter(sampleFragment);
            sampleFragment.setPresenter(mSamplePresenter);
        }else {
            ActivityUtils.showFragmentToActivity(getSupportFragmentManager(),sampleFragment);
        }
    }

    private void handleOSC(){
        OSCFragment oscFragment = (OSCFragment) getSupportFragmentManager().findFragmentByTag(ConstantsUtil.FRAGMENT_OSC);
        if(null == oscFragment){
            oscFragment = OSCFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),oscFragment,R.id.contentFrame,ConstantsUtil.FRAGMENT_OSC);
            OSCRepository gc = new OSCRepository();
            mGameCenterPresenter = new OSCPresenter(oscFragment,gc);
            oscFragment.setPresenter(mGameCenterPresenter);
        }else{
            ActivityUtils.showFragmentToActivity(getSupportFragmentManager(),oscFragment);
        }
    }

    private void hideSoftInput() {
        if (inputMethodManager != null) {
            View v = MainActivity.this.getCurrentFocus();
            if (v == null) {
                return;
            }

            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    /**
     * Called when the user submits the query. This could be due to a key press on the
     * keyboard or due to pressing a submit button.
     * The listener can override the standard behavior by returning true
     * to indicate that it has handled the submit request. Otherwise return false to
     * let the SearchView handle the submission by launching any associated intent.
     *
     * @param query the query text that is to be submitted
     * @return true if the query has been handled by the listener, false to let the
     * SearchView perform the default action.
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG, "onQueryTextSubmit: query " + query);
        EnjoyPictureFragment enjoyPictureFragment = (EnjoyPictureFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if(enjoyPictureFragment != null && mEnjoyPicturePresenter != null){
            mEnjoyPicturePresenter.newSearch(query);
            hideSoftInput();
        }
        return true;
    }

    /**
     * Called when the query text is changed by the user.
     *
     * @param newText the new content of the query text field.
     * @return false if the SearchView should perform the default action of showing any
     * suggestions if available, true if the action was handled by the listener.
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
