package com.kang.net;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.ArrayMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yanzhenjie.permission.ApLog;
import com.kang.base.AppConst;
import com.kang.base.BaseApplication;
import com.kang.cache.UserCache;
import com.kang.utils.Md5Utils;
import com.kang.utils.NetUtils;
import com.kang.utils.UIUtils;

import org.reactivestreams.Subscription;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
* @author ：kangjinling
* 邮箱 ：401205099@qq.com
* 功能描述 ： 接口集合
*
*/


public class ApiRetrofit extends BaseApiRetrofit {
    private static String mType = "0";
    private String baseUrl  ="";
    public MyApi mApi; //服务端接口管理
    public static ApiRetrofit mInstance;
    private ArrayMap<Object, Subscription> maps;

    @SuppressLint("NewApi")
    private ApiRetrofit() {
        super();
        maps = new ArrayMap<>();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        //在构造方法中完成对Retrofit接口的初始化
        if (!TextUtils.isEmpty(mType)){
            switch (mType){
                case "0":
                    baseUrl = MyApi.BASE_URL;
                    break;
                default:
                    UIUtils.showToast("请求错误");
                    break;
            }

            mApi = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(getClient())
                    .addConverterFactory( GsonConverterFactory.create(gson))
                    .addCallAdapterFactory( RxJava2CallAdapterFactory.create())
                    .build()
                    .create(MyApi.class);

        }else {
            UIUtils.showToast("请求出错！");
        }
    }


    public static ApiRetrofit getInstance() {
        mType = "0";
        mInstance = null;
        if (mInstance == null) {
            synchronized (ApiRetrofit.class) {
                if (mInstance == null) {
                    mInstance = new ApiRetrofit();
                }
            }
        }

        return mInstance;

    }

    public Map<String, String> getDefaultBodyParam() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("app_type", "1"); // 客户端类型,1.Android 2.iPhone 3. 微信
        map.put("app_version", String.valueOf(NetUtils.getVersionCode(BaseApplication.getContext())) == null ? "" : String.valueOf(NetUtils.getVersionCode(BaseApplication.getContext()))); // APP版本号
        map.put("app_system_version", android.os.Build.VERSION.RELEASE == null ? "" : android.os.Build.VERSION.RELEASE); // 系统版本号
        map.put("appImei", NetUtils.getImei()); // 手机唯一识别码 IMEI
//        map.put("app_imei", NetUtils.getUniquePsuedoID() == null ? "imei" : NetUtils.getUniquePsuedoID());
        map.put("app_phone_device", android.os.Build.MODEL == null ? "" : android.os.Build.MODEL); // 手机型号
        map.put("app_phone_producter", android.os.Build.BRAND == null ? "" : android.os.Build.BRAND); // 手机厂商
//        map.put("app_lat", UserCache.getLatitude()); // 纬度
//        map.put("app_lon", UserCache.getLongtitude()); // 经度
        map.put("app_address_ip", NetUtils.getAddressIp(BaseApplication.getContext()));
        return map;
    }

    private RequestBody getRequestBody(Object obj) {
        String route = new Gson().toJson(obj);
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),route);
        return body;
    }

    private MultipartBody.Part toRequestBodyOfImage(String keyStr, File pFile){
        if (pFile!=null){
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), pFile);
            MultipartBody.Part filedata = MultipartBody.Part.createFormData(keyStr, pFile.getName(), requestBody);
            return filedata;
        }
        return null;
    }

    private MultipartBody.Part toRequestBodyOfText (String keyStr, String value) {
        MultipartBody.Part body = MultipartBody.Part.createFormData(keyStr, value);
        return body;
    }


    /**
     * 获取验证码
     * @param mobile
     * @return
     */
    public Observable<ResponseBody> sendCode(String mobile) {
        Map<String, String> map = getDefaultBodyParam();
        map.put("mobile",mobile );
        map.put("token", Md5Utils.UpperCase(new StringBuffer(NetUtils.getImei()).reverse().toString()+AppConst.TOKEN));
        ApLog.e("body======="+ map);
        return mApi.sendCode( map);

    }

    public Observable<ResponseBody> registe(String appLoginName,String appMessageCode,String appLoginPassword) {
        Map<String, String> map = getDefaultBodyParam();
        map.put("appLoginName",appLoginName );
        map.put("appMessageCode", appMessageCode);
        map.put("appLoginPassword",Md5Utils.UpperCase(appLoginName+appLoginPassword) );

        ApLog.e("body======="+ map);
        return mApi.registe( map);

    }
    public Observable<ResponseBody> login(String appLoginName ,String appLoginPassword) {
        //AppConst.UNTOKEN = Md5Utils.UpperCase((new StringBuffer(NetUtils.getImei()).reverse().toString())+ new Date());
        UserCache.setToken();
        Map<String, String> map = getDefaultBodyParam();
        map.put("appLoginName",appLoginName );
        map.put("appLoginPassword",Md5Utils.UpperCase(appLoginName+appLoginPassword) );
        map.put("uniqueToken", UserCache.getToken());
        ApLog.e("body======="+ map);
        return mApi.login( map);

    }

    public Observable<ResponseBody> certification(String phone,String name,String cardNo) {
        Map<String, String> map = getDefaultBodyParam();
        map.put("phone",phone );
        map.put("name",name );
        map.put("cardNo",cardNo );
        ApLog.e("body======="+ map);
        return mApi.certification( map);

    }


}
