package cn.shanghq.seek.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 17634 on 2018/1/2.
 */

public class MyFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> titles;

    public MyFragmentAdapter(FragmentManager fragmentManager,List<Fragment> lists,List<String> strings){
        super(fragmentManager);
        this.fragments=lists;
        this.titles=strings;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
