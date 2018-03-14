package com.kang.ui.view;

import android.widget.Button;

/**
 * @author ：kangjinlingon 2018/3/7.
 *         邮箱 ： 401205099@qq.com
 *         功能描述 ：
 */

public interface IRegisterView {

    /**
     * 请求成功
     */
    void onNetSuccess();

    /**
     * 请求报错
     */
    void  onNetError();

    /**
     *  验证码
     * @return
     */
    Button sendCode();


}
