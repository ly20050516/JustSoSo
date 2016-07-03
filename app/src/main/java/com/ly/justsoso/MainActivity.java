package com.ly.justsoso;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.ly.justsoso.base.utils.ActivityUtils;
import com.ly.justsoso.enjoypictures.EnjoyPictureFragment;
import com.ly.justsoso.enjoypictures.EnjoyPicturePresenter;
import com.ly.justsoso.enjoypictures.EnjoyPictureRepository;
import com.ly.justsoso.enjoypictures.data.local.LocalDataSource;
import com.ly.justsoso.enjoypictures.data.remote.RemoteDataSource;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout mRootDrawerLayout;

    EnjoyPicturePresenter mEnjoyPicturePresenter;
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

        Picasso.with(this).setIndicatorsEnabled(true);

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
        }else if(menuId == R.id.nav_battery_statistics){
            handleBatteryStatistics();
        }else if(menuId == R.id.nav_small_talk){
            handleSmallTalk();
        }
        mRootDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void handlePictureEnjoy(){
        EnjoyPictureFragment enjoyPictureFragment = (EnjoyPictureFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if(null == enjoyPictureFragment){
            enjoyPictureFragment = EnjoyPictureFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),enjoyPictureFragment,R.id.contentFrame);
            EnjoyPictureRepository er = new EnjoyPictureRepository(new LocalDataSource(this),new RemoteDataSource());
            mEnjoyPicturePresenter = new EnjoyPicturePresenter(er,enjoyPictureFragment);
            enjoyPictureFragment.setPresenter(mEnjoyPicturePresenter);
        }
    }

    private void handleBatteryStatistics(){

    }

    private void handleSmallTalk(){

    }
}
