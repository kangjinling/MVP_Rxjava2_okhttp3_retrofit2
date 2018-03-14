package com.kang.ui.fragment;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kang.R;
import com.kang.base.BaseFragment;
import com.kang.base.BasePresenter;
import com.kang.ui.adapter.FragmentViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * @author ：kangjinling
 *         邮箱 ：401205099@qq.com
 *         功能描述 ：首页
 */
public class HomeFragment extends BaseFragment {


    @Bind(R.id.h_Tab)
    TabLayout hTab;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    private List<Integer> titlesImg;
    private List<String> titles;
    private List<Fragment> fragments;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View rootView) {

    }

    @Override
    public void initData() {
        fragments = new ArrayList<>();
        fragments.add(new PersonFragment());
        fragments.add(new PersonFragment());
        titles = new ArrayList<>();
        titles.add("个人征信");
        titles.add("企业信用");

        titlesImg = new ArrayList<>();
        titlesImg.add(R.mipmap.home_personal);
        titlesImg.add(R.mipmap.home_enterprise);

        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getChildFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        hTab.setupWithViewPager(viewPager);
        setupTabIcons();
        viewPager.setCurrentItem(0);
    }


    @Override
    protected void loadData() {

    }


    private void setupTabIcons() {
        hTab.getTabAt(0).setCustomView(getTabView(0));
        hTab.getTabAt(1).setCustomView(getTabView(1));
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_tab_home, null);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText(titles.get(position));
        txt_title.setTextSize(14);
        ImageView img_title = (ImageView) view.findViewById(R.id.img_title);
        img_title.setImageResource(titlesImg.get(position));

        return view;
    }


}
