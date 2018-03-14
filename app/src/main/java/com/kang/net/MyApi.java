package com.kang.net;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author ：kangjinling
 * 邮箱 ：401205099@qq.com
 * 功能描述 ：
 *
 */
public interface MyApi {

    public static final String BASE_URL = "http://192.168.1.254:8989/";

    /**
     * 获取验证码
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("managementCenter/app/appUser/getRegMsgCode")
    Observable<ResponseBody> sendCode(@FieldMap Map<String, String> map);


    /**
     * 注册
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("managementCenter/app/appUser/registe")
    Observable<ResponseBody> registe(@FieldMap Map<String, String> map);


    /**
     *  实名认证
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("managementCenter/app/account/certification")
    Observable<ResponseBody> certification(@FieldMap Map<String, String> map);



    /**
     *  登录
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("managementCenter/app/appUser/login")
    Observable<ResponseBody> login(@FieldMap Map<String, String> map);




}
