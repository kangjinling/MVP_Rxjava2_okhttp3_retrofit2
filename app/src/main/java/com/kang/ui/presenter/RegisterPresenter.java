package com.kang.ui.presenter;

import android.content.Intent;

import com.google.gson.JsonObject;
import com.yanzhenjie.permission.ApLog;
import com.kang.base.BaseActivity;
import com.kang.base.BasePresenter;
import com.kang.cache.UserCache;
import com.kang.net.ApiRetrofit;
import com.kang.net.HttpState;
import com.kang.ui.activity.RealNameActivity;
import com.kang.ui.model.UserRespone;
import com.kang.ui.view.IRegisterView;
import com.kang.utils.JSONUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

import static com.kang.utils.UIUtils.showToast;

/**
 * @author ：kangjinlingon 2018/3/7.
 *         邮箱 ： 401205099@qq.com
 *         功能描述 ：
 */

public class RegisterPresenter extends BasePresenter<IRegisterView> {


    private Disposable  mDisposable = null;

    public RegisterPresenter(IRegisterView view,BaseActivity context) {
        super(view,context);
    }

    public void sendCodeBtn(){

        // 倒计时 60s
        iView.sendCode().setEnabled(false);
        mDisposable = Flowable.intervalRange(0, 61, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {


                        ApLog.e("倒计时 " + String.valueOf(60 - aLong) + " s");
                        try {
                            iView.sendCode().setText("倒计时 " + String.valueOf(60 - aLong) + " s");
                        }catch (Exception e){
                            e.printStackTrace();
                        }


//                        tvCountDown.setText("倒计时 " + String.valueOf(10 - aLong) + " 秒");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {

                        iView.sendCode().setEnabled(true);
                        iView.sendCode().setText("获取验证码");
                    }
                })
                .subscribe();


    }

    public  void mDisposable(){
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    public void getCode(String phone) {
        addSubscription(ApiRetrofit.getInstance().sendCode(phone), new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody response) {
                try {

                    JsonObject jsonObject = JSONUtils.getAsJsonObject(response.string());
                    int  result = jsonObject.get("result").getAsInt();
                    String msg = jsonObject.get("msg").getAsString();

                    switch (result){
                        // 失败
                        case 0:
                            iView.onNetError();
                            showToast(msg.equals("") ? "请稍后再试" : msg);
                            break;
                        //成功
                        case 1:
                            String data = jsonObject.get("data").getAsString();
                            showToast(data.equals("请稍后再试") ? "" : data);
                            sendCodeBtn();
                            break;
                        default:

                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable e) {
                iView.onNetError();
            }

            @Override
            public void onComplete() {
                iView.onNetSuccess();

            }
        });
    }



    public void registe(String phone,String appMessageCode,String appLoginPassword ) {
        addSubscription(ApiRetrofit.getInstance().registe(phone,appMessageCode ,appLoginPassword), new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody response) {
                try {

                    JsonObject jsonObject = JSONUtils.getAsJsonObject(response.string());
                    int  result = jsonObject.get("result").getAsInt();
                    String msg = jsonObject.get("msg").getAsString();
                    switch (result){
                        // 失败
                        case 0:
                            showToast(msg.equals("") ? "请稍后再试" : msg);
                            iView.onNetError();
                            break;
                        //成功
                        case 1:
                            JsonObject data = jsonObject.get("data").getAsJsonObject();
                            UserRespone userRespone = JSONUtils.fromToJson(data,UserRespone.class);
                            UserCache.setUser(userRespone);
                            ApLog.e("++++++++++++++"+UserCache.getUser().getAppLoginName());
                            login(phone,appLoginPassword);

                            break;
                        default:

                            break;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable e) {
                showToast(HttpState.getStateErr(e.getLocalizedMessage()));
                iView.onNetError();
            }

            @Override
            public void onComplete() {
//                iView.onNetSuccess();
            }
        });
    }


    private void login(String phone,String appLoginPassword){
        addSubscription(ApiRetrofit.getInstance().login(phone,appLoginPassword), new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {

            }

            @Override
            public void onError(Throwable e) {
                showToast(HttpState.getStateErr(e.getLocalizedMessage()));
                iView.onNetError();
            }

            @Override
            public void onComplete() {
                iView.onNetSuccess();


                mContext.startActivity(new Intent(mContext,RealNameActivity.class));
            }
        });
    }




}
