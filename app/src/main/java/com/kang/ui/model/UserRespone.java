package com.kang.ui.model;

import java.io.Serializable;

/**
 * @author ：kangjinlingon 2018/3/9.
 *         邮箱 ： 401205099@qq.com
 *         功能描述 ：
 */

public class UserRespone implements Serializable {
    private static final long serialVersionUID = 1L;

    private  String  seqId ;//": 27,
    private  String userName;//": null,
    private  String  systemCode;//": null,
    private  String  loginName;//": null,
    private  String  appLoginName;//":private  String  13144806821",
    private  String  appMessageCodeTime;//": 1520566389000,
    private  String  appVerificationStatus = "";//": 0,
    private  String appVerificationName;//": "康金玲",
    private  String appVerificationIdcard;//": "432524198901307410",
    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getAppLoginName() {
        return appLoginName;
    }

    public void setAppLoginName(String appLoginName) {
        this.appLoginName = appLoginName;
    }

    public String getAppMessageCodeTime() {
        return appMessageCodeTime;
    }

    public void setAppMessageCodeTime(String appMessageCodeTime) {
        this.appMessageCodeTime = appMessageCodeTime;
    }

    public String getAppVerificationStatus() {
        return appVerificationStatus;
    }

    public void setAppVerificationStatus(String appVerificationStatus) {
        this.appVerificationStatus = appVerificationStatus;
    }

    public String getAppVerificationName() {
        return appVerificationName;
    }

    public void setAppVerificationName(String appVerificationName) {
        this.appVerificationName = appVerificationName;
    }

    public String getAppVerificationIdcard() {
        return appVerificationIdcard;
    }

    public void setAppVerificationIdcard(String appVerificationIdcard) {
        this.appVerificationIdcard = appVerificationIdcard;
    }
}
