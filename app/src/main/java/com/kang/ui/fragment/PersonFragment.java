package com.kang.ui.fragment;


import com.kang.R;
import com.kang.base.BaseFragment;
import com.kang.base.BasePresenter;


/**
* @author ：kangjinling 
* 邮箱 ：401205099@qq.com
* 功能描述 ：
*
*/
public class PersonFragment extends BaseFragment {


    public PersonFragment() {
        // Required empty public constructor
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_person;
    }

    @Override
    protected void loadData() {

    }

}
