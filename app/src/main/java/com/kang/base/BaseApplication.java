package com.kang.base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.squareup.leakcanary.LeakCanary;
import com.yanzhenjie.permission.ApLog;
import com.kang.BuildConfig;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * @author ：kangjinlingon 2018/3/2.
 *         邮箱 ： 401205099@qq.com
 *         功能描述 ：
 */

public class BaseApplication extends MultiDexApplication {

    private static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);


        if (BuildConfig.DEBUG){
            ApLog.setEnable(true);
        }else {
            ApLog.setEnable(false);
        }


        BGASwipeBackHelper.init(BaseApplication.this, null);



//        Observable.create(new ObservableOnSubscribe<Object>() {
//            @Override
//            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
//
//
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());




    }




    public static Context getContext() {
        return mContext;
    }





}
