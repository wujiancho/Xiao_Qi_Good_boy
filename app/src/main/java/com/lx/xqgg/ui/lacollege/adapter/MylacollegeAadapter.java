package com.lx.xqgg.ui.lacollege.adapter;

import com.lx.xqgg.ui.lacollege.LivebroadcastFragment;
import com.lx.xqgg.ui.lacollege.OfflineactivitiesFragment;
import com.lx.xqgg.ui.lacollege.OnlinecoursesFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MylacollegeAadapter extends FragmentPagerAdapter {
    private List<Fragment> fragments=new ArrayList<>();
    private  String[] title={"直播","线上课程","线下活动"};
    public MylacollegeAadapter(FragmentManager fm) {
        super(fm);
        fragments.add(new LivebroadcastFragment());
        fragments.add(new OnlinecoursesFragment());
        fragments.add(new OfflineactivitiesFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
