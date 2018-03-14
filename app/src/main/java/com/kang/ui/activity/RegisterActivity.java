package com.kang.ui.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.kang.R;
import com.kang.base.BaseActivity;
import com.kang.ui.presenter.RegisterPresenter;
import com.kang.ui.view.IRegisterView;
import com.kang.utils.PermissionSetting;
import com.kang.utils.RegularUtils;
import com.kang.widget.ClearEditText;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.kang.utils.UIUtils.showToast;

/**
 * @author ：kangjinling
 *         邮箱 ：401205099@qq.com
 *         功能描述 ：注册
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements IRegisterView {
    @Bind(R.id.left_finish)
    Button leftFinish;
    @Bind(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;
    @Bind(R.id.edt_phone)
    ClearEditText edtPhone;
    @Bind(R.id.edt_code)
    ClearEditText edtCode;
    @Bind(R.id.edt_pwd)
    ClearEditText edtPwd;
    @Bind(R.id.edt_affirm_pwd)
    ClearEditText edtAffirmPwd;
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    @Bind(R.id.register_agreement_liner)
    LinearLayout registerAgreementLiner;
    @Bind(R.id.btn_reg)
    Button btnReg;
    @Bind(R.id.btn_sendCode)
    Button btnSendCode;
    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this,this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register;
    }


    @Override
    public void initView() {
        tvToolbarTitle.setText("注册");
        //StatusBarUtil.setColorForSwipeBack(this, UIUtils.getColor(R.color.color_ffffff), 0);

    }

    @Override
    public void initData() {

        //密码输入框监控
        edtPhone.addTextChangedListener(pwTextWatcher);
        edtCode.addTextChangedListener(pwTextWatcher);
        edtPwd.addTextChangedListener(pwTextWatcher);
        edtAffirmPwd.addTextChangedListener(pwTextWatcher);


    }

    private TextWatcher pwTextWatcher  = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String  phone = edtPhone.getText().toString().trim();
            String  verificationCode = edtCode.getText().toString().trim();
            String  password =  edtPwd.getText().toString().trim();
            String  affirmPwd =  edtAffirmPwd.getText().toString().trim();

            if (phone.length()==11&&verificationCode.length()==6&&password.length()>=6&&affirmPwd.length()>=6){
                btnReg.setEnabled(true);
            }else {
                btnReg.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    } ;

    @OnClick({R.id.left_finish, R.id.register_agreement_liner, R.id.btn_reg,R.id.btn_sendCode})
    public void onClick(View view) {
        String  phone = edtPhone.getText().toString().trim();
        String  verificationCode = edtCode.getText().toString().trim();
        String  password =  edtPwd.getText().toString().trim();
        String  affirmPwd =  edtAffirmPwd.getText().toString().trim();

        switch (view.getId()) {
            case R.id.left_finish:
                finish();
                break;
            case R.id.register_agreement_liner:
                break;
            case R.id.btn_sendCode:
                // 获取手机imei权限
                requestPermission(Permission.READ_PHONE_STATE);

                break;

            case R.id.btn_reg:

                if (checkInputInfo(phone,verificationCode,password)){

                    if (TextUtils.equals(password,affirmPwd)){
                        showWaitingDialog1(getResources().getString(R.string.wait_tip),true);
                        mPresenter.registe(phone,verificationCode,password);
                    }else {
                        showToast("请确认新密码");
                    }
                }
                break;
            default:
                break;
        }
    }



    private boolean checkPhone(String phone) {

        if (TextUtils.isEmpty(phone)) {
            showToast("请输入手机号");
            return false;
        }

        if (!RegularUtils.isMobile(phone)) {
            showToast("请输入正确的手机号");
            return false;
        }

        return true;
    }


    private boolean checkInputInfo(String phone,String verificationCode ,String password) {
//        closeLoadingDialog();
        if(TextUtils.isEmpty(phone)){
            showToast("请输入手机号");
            return false;
        }
        if(!RegularUtils.isMobile(phone)){
            showToast("请输入正确的手机号");
            return false;
        }
        if(TextUtils.isEmpty(verificationCode)){
            showToast("请输入验证码");
            return false;
        }
        if(TextUtils.isEmpty(password )){
            showToast("请输入6位以上密码");
            return false;
        }
        if(TextUtils.isEmpty(password)&& TextUtils.isEmpty(verificationCode)){
            showToast("请输入验证码/密码");
            return false;
        }

        return true;
    }
    @Override
    public void onGranted() {
        String  phone = edtPhone.getText().toString().trim();
        if (checkPhone(phone)){
            showWaitingDialog1(getResources().getString(R.string.wait_tip),true);
            mPresenter.getCode(phone);
        }
    }

    @Override
    public void onDenied(List<String> permissions) {
        PermissionSetting  mSetting = null;
        if (AndPermission.hasAlwaysDeniedPermission(this, permissions)) {
            if (mSetting == null){
                mSetting = new PermissionSetting(RegisterActivity.this);
                mSetting.showSetting(permissions);
            }

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

    @Override
    public Button sendCode() {
        return btnSendCode;
    }

    @Override
    protected void onDestroy() {
        mPresenter.mDisposable();
        super.onDestroy();

    }
}
