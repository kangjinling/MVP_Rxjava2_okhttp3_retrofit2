package com.kang.ui.activity;

import android.content.Intent;

import com.yanzhenjie.permission.ApLog;
import com.kang.R;
import com.kang.base.BaseActivity;
import com.kang.base.BasePresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
* @author ：kangjinling
* 邮箱 ：401205099@qq.com
* 功能描述 ： 欢迎页面
*
*/
public class WelcomeActivity extends BaseActivity {


    private static  Disposable mDisposable;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_welcome;
    }


    @Override
    public void initData() {
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        cancel();
                    }

                    @Override
                    public void onComplete() {
                        cancel();
                    }
                });



    }

    /**
     * 取消订阅
     */
    public static void cancel(){
        if(mDisposable!=null||!mDisposable.isDisposed()){
            mDisposable.dispose();
            ApLog.e("====定时器取消======");
        }
    }


    @Override
    protected void onDestroy() {
        cancel();
        super.onDestroy();

    }
}
