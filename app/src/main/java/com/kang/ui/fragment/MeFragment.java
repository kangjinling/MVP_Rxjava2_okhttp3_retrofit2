package com.kang.ui.fragment;


import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kang.R;
import com.kang.base.BaseFragment;
import com.kang.base.BasePresenter;
import com.kang.cache.UserCache;
import com.kang.utils.NetUtils;
import com.kang.utils.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * @author ：kangjinling
 *         邮箱 ：401205099@qq.com
 *         功能描述 ：我的
 */
public class MeFragment extends BaseFragment {

    @Bind(R.id.mTv_phone)
    TextView mTvPhone;
    @Bind(R.id.mTv_pay)
    RelativeLayout mTvPay;
    @Bind(R.id.mTv_balance)
    TextView mTvBalance;
    @Bind(R.id.mTv_addUp)
    TextView mTvAddUp;
    @Bind(R.id.mTv_consume)
    TextView mTvConsume;
    @Bind(R.id.mTv_pay_record)
    TextView mTvPayRecord;
    @Bind(R.id.mTv_consume_record)
    TextView mTvConsumeRecord;
    @Bind(R.id.mTv_user_setting)
    TextView mTvUserSetting;
    @Bind(R.id.mTv_about_us)
    TextView mTvAboutUs;
    @Bind(R.id.mTv_login_out)
    TextView mTvLoginOut;
    @Bind(R.id.mTv_versions)
    TextView mTvVersions;

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_me;
    }

    @Override
    public void initView(View rootView) {

    }

    @Override
    public void initData() {

//        if (!TextUtils.isEmpty(UserCache.getUser().getAppVerificationStatus())||UserCache.getUser().getAppVerificationStatus()!=null){
//
//            switch (UserCache.getUser().getAppVerificationStatus()){
//                // 未认证
//                case "0":
//                    mTvPhone.setText(Html.fromHtml(UserCache.getUser().getAppLoginName()+"<font color='#0666E8'>未认证</font>"));
//                    break;
//                // 已认证
//                case "1":
//                    mTvPhone.setText(Html.fromHtml(UserCache.getUser().getAppLoginName()+"( <font color='#cadffe'>已认证</font> )"));
//                    break;
//                default:
//
//                    break;
//            }
//        }

        mTvVersions.setText("版本号："+ NetUtils.getVersionName(getActivity()));
    }

    @Override
    protected void loadData() {

        UIUtils.showToast("000");

    }

    @OnClick({R.id.mTv_pay, R.id.mTv_pay_record, R.id.mTv_consume_record, R.id.mTv_user_setting, R.id.mTv_about_us, R.id.mTv_login_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mTv_pay:
                UIUtils.showToast("");
                break;
            case R.id.mTv_pay_record:
                UIUtils.showToast("");
                break;
            case R.id.mTv_consume_record:
                UIUtils.showToast("");
                break;
            case R.id.mTv_user_setting:
                UIUtils.showToast("");
                break;
            case R.id.mTv_about_us:
                UIUtils.showToast("");
                break;
            case R.id.mTv_login_out:
                UIUtils.showToast("");
                break;
            default:
                break;
        }
    }
}
