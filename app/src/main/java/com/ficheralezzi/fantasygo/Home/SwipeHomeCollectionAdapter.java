package com.ficheralezzi.fantasygo.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.ICalcoloDannoStrategy;
import com.ficheralezzi.fantasygo.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by gaetano on 14/07/17.
 */

public class SwipeHomeCollectionAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> mSwipeOptions;
    private ArrayList<String> mTabClassNameFragments;

    public SwipeHomeCollectionAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        try {
            fragment = getFragmentByPosition(position);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        Bundle args = new Bundle();
        args.putString("title", mTabClassNameFragments.get(position));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return mTabClassNameFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ((CharSequence) getTitleByPosition(position));
    }

    public ArrayList<String> getmSwipeOptions() {
        return mSwipeOptions;
    }

    public void setmSwipeOptions(ArrayList<String> mSwipeOptions) {
        this.mSwipeOptions = mSwipeOptions;
    }

    public ArrayList<String> getmTabClassNameFragments() {
        return mTabClassNameFragments;
    }

    public void setmTabClassNameFragments(ArrayList<String> mTabClassNameFragments) {
        this.mTabClassNameFragments = mTabClassNameFragments;
    }


    private String getTitleByPosition(int position){
        return mSwipeOptions.get(position);
    }

    private String getTabNameClassByPosition(int position){
        return mTabClassNameFragments.get(position);
    }

    private Fragment getFragmentByPosition(int position) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return (Fragment) Class.forName("com.ficheralezzi.fantasygo.Home.Activity." + getTabNameClassByPosition(position)).newInstance();
    }
}
