package com.kang.cache;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kang.base.AppConst;
import com.kang.ui.model.UserRespone;
import com.kang.utils.Md5Utils;
import com.kang.utils.NetUtils;
import com.kang.utils.SPUtils;
import com.kang.utils.SysDataSign;
import com.kang.utils.UIUtils;

import java.util.Date;


/**
 * 作者 : kangjinling
 * 邮箱 ：401205099@qq.com
 * 功能描述 ：用户缓存
 */

public class UserCache {

    public static Boolean getFirst() {
        return SPUtils.getInstance(UIUtils.getContext()).getBoolean("first", true);
    }
    public static void putFirst() {
        SPUtils.getInstance(UIUtils.getContext()).putBoolean("first", false);
    }

    public  static void setToken(){
        SPUtils.getInstance(UIUtils.getContext()).putString(AppConst.UNTOKENI, SysDataSign.enc(Md5Utils.UpperCase((new StringBuffer(NetUtils.getImei()).reverse().toString())+ new Date())));
    }
    public  static String  getToken(){
        return  SysDataSign.unenc(SPUtils.getInstance(UIUtils.getContext()).getString(AppConst.UNTOKENI, ""));
    }

    public static void setUser(UserRespone user){
        try{
            JsonObject jo = UserJson.buildUser(user);
            if(jo!=null){
                SPUtils.getInstance(UIUtils.getContext()).putString(AppConst.USER, SysDataSign.enc(jo.toString()));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static UserRespone getUser(){
        UserRespone loginResponse = null;
        String data = SPUtils.getInstance(UIUtils.getContext()).getString(AppConst.USER, "");
        if(TextUtils.isEmpty(data)){
            return loginResponse;
        }
        try {
            loginResponse = new Gson().fromJson(SysDataSign.unenc(data), UserRespone.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return loginResponse;
    }

    /**
     * 是否登录
     * @return true 登录,false 未登录
     */
    public static boolean getIsLogin() {
        boolean boo = false;
        UserRespone u = getUser();
        if(u==null){
            return boo;
        }
        String userID = u.getAppLoginName();
        if(!TextUtils.isEmpty(userID)){
            boo = true;
        }
        return boo;
    }


    /**
     *  是否实名
     * @return
     */
    public static boolean getIsReal() {
        boolean boo = false;
        UserRespone u = getUser();
        if(u==null){
            return boo;
        }
        String userStatus = u.getAppVerificationStatus();
        if(!TextUtils.isEmpty(userStatus)){
            boo = true;
        }
        return boo;
    }










    public void clearUser(){
        SPUtils.getInstance(UIUtils.getContext()).remove(AppConst.USER);
    }

}
