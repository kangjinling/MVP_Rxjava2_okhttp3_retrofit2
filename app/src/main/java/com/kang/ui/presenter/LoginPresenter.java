package com.kang.ui.presenter;

import com.google.gson.JsonObject;
import com.yanzhenjie.permission.ApLog;
import com.kang.base.BaseActivity;
import com.kang.base.BasePresenter;
import com.kang.cache.UserCache;
import com.kang.net.ApiRetrofit;
import com.kang.net.HttpState;
import com.kang.ui.model.UserRespone;
import com.kang.ui.view.ILoginView;
import com.kang.utils.JSONUtils;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

import static com.kang.utils.UIUtils.showToast;

/**
 * @author ：kangjinlingon 2018/3/9.
 *         邮箱 ： 401205099@qq.com
 *         功能描述 ：
 */

public class LoginPresenter extends BasePresenter<ILoginView> {
    public LoginPresenter(ILoginView view, BaseActivity mContext) {
        super(view, mContext);
    }


    public void login(String phone,String appLoginPassword){
        addSubscription(ApiRetrofit.getInstance().login(phone,appLoginPassword), new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {

                try {

                    JsonObject jsonObject = JSONUtils.getAsJsonObject(responseBody.string());
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
                iView.onNetSuccess();

//                mContext.startActivity(new Intent(mContext,RealNameActivity.class));
            }
        });
    }

}
