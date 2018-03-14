package com.kang.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.kang.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
* @author ：kangjinling 
* 邮箱 ：401205099@qq.com
* 功能描述 ：
*
*/

public class MainTabAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> mFragments = new ArrayList<BaseFragment>();

    public MainTabAdapter(List<BaseFragment> fragmentList, FragmentManager fm) {
        super(fm);
        if (fragmentList != null){
            mFragments = fragmentList;
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
