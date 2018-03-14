package com.kang.base;




import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ：kangjinlingon 2018/3/6.
 *         邮箱 ： 401205099@qq.com
 *         功能描述 ：
 */

public abstract class BasePresenter<V> {

    public  BaseActivity mContext;
    protected V iView;
    private CompositeDisposable  mCompositeDisposable = null;

    public BasePresenter(V view, BaseActivity mContext) {
        this.mContext = mContext;
        attachView(view);
    }

    public void attachView(V view) {
        iView = view;
    }

    public void detachView() {
        iView = null;
        onUnsubscribe();
    }


    public void addSubscription(Observable observable, DisposableObserver subscriber) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable ();
        }
        mCompositeDisposable.add((Disposable) observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscriber));
    }

    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeDisposable != null ) {
            mCompositeDisposable.clear();
            mCompositeDisposable.dispose();
            mCompositeDisposable = null;
        }
    }

}
