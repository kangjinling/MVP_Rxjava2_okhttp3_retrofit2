package com.kang.cache;

import com.google.gson.JsonObject;
import com.kang.ui.model.UserRespone;

/**
 * @author ：kangjinlingon 2018/3/9.
 *         邮箱 ： 401205099@qq.com
 *         功能描述 ：
 */

public class UserJson {


    public static JsonObject buildUser(UserRespone u) {
        if (u == null) {
            return null;
        } else {
            JsonObject jo = new JsonObject();
            try {
                jo.addProperty("seqId", u.getSeqId());
                jo.addProperty("appLoginName", u.getAppLoginName());
                jo.addProperty("appVerificationStatus", u.getAppVerificationStatus());
                jo.addProperty("appVerificationName", u.getAppVerificationName());
                jo.addProperty("appVerificationIdcard", u.getAppVerificationIdcard());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return jo;
        }
    }
}
