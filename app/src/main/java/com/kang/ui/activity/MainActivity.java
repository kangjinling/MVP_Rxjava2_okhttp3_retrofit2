package com.kang.ui.activity;


import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kang.R;
import com.kang.base.BaseActivity;
import com.kang.base.BaseFragment;
import com.kang.base.BasePresenter;
import com.kang.cache.UserCache;
import com.kang.ui.adapter.MainTabAdapter;
import com.kang.ui.fragment.HomeFragment;
import com.kang.ui.fragment.MeFragment;
import com.kang.ui.fragment.RecordFragment;
import com.kang.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author ：kangjinling
 *         邮箱 ：401205099@qq.com
 *         功能描述 ：
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.vp_content)
    NoScrollViewPager vpContent;
    @Bind(R.id.tv_home)
    TextView tvHome;
    @Bind(R.id.tv_record)
    TextView tvRecord;
    @Bind(R.id.tv_me)
    TextView tvMe;
    private List<BaseFragment> mFragments;
    private MainTabAdapter mTabAdapter;


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        mTextView = (TextView) findViewById(R.id.channel);
//        final ChannelInfo channelInfo = WalleChannelReader.getChannelInfo(this.getApplicationContext());
//        if (channelInfo != null) {
//            mTextView.setText(channelInfo.getChannel() + "\n" + channelInfo.getExtraInfo());
//        }
////        mTextView.setText(channel);
//    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        vpContent.setOffscreenPageLimit( 3 );
        mFragments = new ArrayList<>(3);
        mFragments.add(new HomeFragment());
        mFragments.add(new RecordFragment());
        mFragments.add(new MeFragment());

        switchTextColor( 0 );
        mTabAdapter = new MainTabAdapter(mFragments, getSupportFragmentManager());
        vpContent.setAdapter(mTabAdapter);
        vpContent.setOffscreenPageLimit(mFragments.size());
        mImmersionBar.fitsSystemWindows(false).transparentStatusBar().init();
    }




    @OnClick({R.id.tv_home, R.id.tv_record, R.id.tv_me})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_home:
                mImmersionBar.fitsSystemWindows(false).transparentStatusBar().init();
                changeView(0);
                switchTextColor(0);
                break;
            case R.id.tv_record:
                changeView(1);
                switchTextColor(1);
                break;
            case R.id.tv_me:

                if (!UserCache.getIsLogin()){
                    startActivity(new Intent(this,LoginActivity.class));
                }else {
                    mImmersionBar.fitsSystemWindows(false).transparentStatusBar().init();
                    changeView(2);
                    switchTextColor(2);
                }

                break;
            default:
                break;
        }
    }

    public void changeView(int desTab) {
        vpContent.setCurrentItem( desTab );
    }





    private void switchTextColor(int i) {
        switch (i) {
            case 0:
                tvHome.setCompoundDrawablesWithIntrinsicBounds( 0, R.mipmap.tab_home, 0, 0 );//
                tvHome.setTextColor( ContextCompat.getColor( this, R.color.color_0666EB ) );

                tvRecord.setCompoundDrawablesWithIntrinsicBounds( null,
                        ContextCompat.getDrawable( this, R.mipmap.tab_record2 ), null, null );
                tvRecord.setTextColor( ContextCompat.getColor( this, R.color.color_363636 ) );

                tvMe.setCompoundDrawablesWithIntrinsicBounds( null,
                        ContextCompat.getDrawable( this, R.mipmap.tab_mine2 ), null, null );
                tvMe.setTextColor( ContextCompat.getColor( this, R.color.color_363636 ) );
                break;
            case 1:
                tvHome.setCompoundDrawablesWithIntrinsicBounds( 0, R.mipmap.tab_home2, 0, 0 );//
                tvHome.setTextColor( ContextCompat.getColor( this, R.color.color_363636 ) );

                tvRecord.setCompoundDrawablesWithIntrinsicBounds( null,
                        ContextCompat.getDrawable( this, R.mipmap.tab_record ), null, null );
                tvRecord.setTextColor( ContextCompat.getColor( this, R.color.color_0666EB ) );

                tvMe.setCompoundDrawablesWithIntrinsicBounds( null,
                        ContextCompat.getDrawable( this, R.mipmap.tab_mine2 ), null, null );
                tvMe.setTextColor( ContextCompat.getColor( this, R.color.color_363636 ) );
                break;
            case 2:
                tvHome.setCompoundDrawablesWithIntrinsicBounds( 0, R.mipmap.tab_home2, 0, 0 );//
                tvHome.setTextColor( ContextCompat.getColor( this, R.color.color_363636 ) );

                tvRecord.setCompoundDrawablesWithIntrinsicBounds( null,
                        ContextCompat.getDrawable( this, R.mipmap.tab_record2 ), null, null );
                tvRecord.setTextColor( ContextCompat.getColor( this, R.color.color_363636 ) );

                tvMe.setCompoundDrawablesWithIntrinsicBounds( null,
                        ContextCompat.getDrawable( this, R.mipmap.tab_mine ), null, null );
                tvMe.setTextColor( ContextCompat.getColor( this, R.color.color_0666EB ) );
                break;

            default:
                break;
        }
    }



    /**
     * 监听返回键
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            exitApp();
            return true;
        }
        return super.dispatchKeyEvent( event );
    }

    private long exitTime = 0;

    private void exitApp() {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText( MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT ).show();
            exitTime = System.currentTimeMillis();
        } else {

            exit();
            mActivities.clear();
            finish();
        }
    }
}
