package com.kang.ui.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kang.R;
import com.kang.base.BaseActivity;
import com.kang.ui.presenter.LoginPresenter;
import com.kang.ui.view.ILoginView;
import com.kang.utils.RegularUtils;
import com.kang.utils.UIUtils;
import com.kang.widget.ClearEditText;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * @author ：kangjinling
 *         邮箱 ：401205099@qq.com
 *         功能描述 ：登录页面
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView {
    @Bind(R.id.imbtn_del)
    ImageButton imbtnDel;
    @Bind(R.id.lg_img)
    ImageView lgImg;
    @Bind(R.id.et_phone)
    ClearEditText etPhone;
    @Bind(R.id.et_pwd)
    ClearEditText etPwd;
    @Bind(R.id.imbtn_login)
    Button imbtnLogin;
    @Bind(R.id.tv_reg)
    TextView tvReg;
    @Bind(R.id.btn_forgetPwd)
    Button btnForgetPwd;


    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this, this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login;
    }


    @Override
    public void initView() {
        //StatusBarUtil.setColorForSwipeBack(this, UIUtils.getColor(R.color.color_ffffff), 0);
        String str = "还没账号？<font color='#0666E8'>立即注册</font>";
        tvReg.setTextSize(13);
        tvReg.setText(Html.fromHtml(str));
        etPhone.addTextChangedListener(textWatch);
        etPwd.addTextChangedListener(textWatch);
    }

    private TextWatcher textWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String phone = etPhone.getText().toString().trim();
            String pwd = etPwd.getText().toString().trim();

            if (phone.length() == 11 && pwd.length() >= 6) {
                imbtnLogin.setEnabled(true);
            } else {
                imbtnLogin.setEnabled(false);
            }

            

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public void initData() {

    }

    @OnClick({R.id.imbtn_del, R.id.imbtn_login, R.id.tv_reg,R.id.btn_forgetPwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imbtn_del:
                finish();
                break;

            case R.id.tv_reg:

                startActivity(new Intent(this, RegisterActivity.class));

                break;
            case R.id.btn_forgetPwd:

                startActivity(new Intent(this, RegisterActivity.class));

                break;

            case R.id.imbtn_login:
                String phone = etPhone.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();

                if (TextUtils.isEmpty(phone)) { //手机号不能为空
                    UIUtils.showToast("手机号不能为空");
                    return;
                }

                if (!RegularUtils.isMobile(phone)) { //手机号格式是否正确
                    UIUtils.showToast("手机号格式是否正确");
                    return;
                }

                if (TextUtils.isEmpty(pwd)) { // 密码不能为空
                    UIUtils.showToast("密码不能为空");
                    return;
                }
                showWaitingDialog1(getResources().getString(R.string.wait_tip), true);
                mPresenter.login(phone, pwd);

                break;


            default:
                break;
        }
    }

    @Override
    public void onNetSuccess() {

        hideWaitingDialog();
    }

    @Override
    public void onNetError() {
        hideWaitingDialog();
    }
}
