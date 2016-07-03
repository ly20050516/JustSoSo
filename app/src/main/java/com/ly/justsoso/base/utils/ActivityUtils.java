package com.ly.justsoso.base.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.common.base.Preconditions;


/**
 * Created by LY on 7/3/2016.
 */
public class ActivityUtils {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment,int frameId){

        Preconditions.checkNotNull(fragmentManager);
        Preconditions.checkNotNull(fragment);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(frameId,fragment);
        fragmentTransaction.commit();
    }
}
