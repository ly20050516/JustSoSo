package com.ly.framework.utilities;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by LY on 7/3/2016.
 */
public class ActivityUtils {

    private static List<Fragment> mCacheFragment;
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment,int frameId,String fragmentTag){

        Preconditions.checkNotNull(fragmentManager);
        Preconditions.checkNotNull(fragment);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(frameId,fragment,fragmentTag);
        if(mCacheFragment == null){
            mCacheFragment = new ArrayList<>();
        }
        mCacheFragment.add(fragment);
        fragmentTransaction.commit();
    }

    public static void showFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment){
        Preconditions.checkNotNull(fragmentManager);
        Preconditions.checkNotNull(fragment);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction,fragment);
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }

    private static void hideAllFragment(FragmentTransaction fragmentTransaction,Fragment currentFragment){
        if(null == mCacheFragment){
            return;
        }
        for (Fragment fragment:mCacheFragment) {
            if(fragment.equals(currentFragment)){
                continue;
            }
            fragmentTransaction.hide(fragment);
        }
    }
}
