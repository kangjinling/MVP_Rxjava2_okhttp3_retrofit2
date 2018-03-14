package com.kang.ui.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kang.R;
import com.kang.base.BaseActivity;
import com.kang.ui.presenter.RealPresenter;
import com.kang.ui.view.IRealView;
import com.kang.utils.IdcardUtils;
import com.kang.utils.UIUtils;
import com.kang.widget.ClearEditText;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * @author ：kangjinling
 *         邮箱 ：401205099@qq.com
 *         功能描述 ：
 */
public class RealNameActivity extends BaseActivity<RealPresenter> implements IRealView {


    @Bind(R.id.left_finish)
    Button leftFinish;
    @Bind(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;
    @Bind(R.id.edt_name)
    ClearEditText edtName;
    @Bind(R.id.edt_card)
    ClearEditText edtCard;
    @Bind(R.id.btn_real)
    Button btnReal;
    @Bind(R.id.btn_next)
    Button btnNext;

    @Override
    protected RealPresenter createPresenter() {
        return new RealPresenter(this, this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_real_name;
    }

    @Override
    public void initView() {
       // StatusBarUtil.setColorForSwipeBack(this, UIUtils.getColor(R.color.color_ffffff), 0);
        tvToolbarTitle.setText("实名认证");
    }

    @Override
    public void initData() {

        edtName.addTextChangedListener(textWatch);
        edtCard.addTextChangedListener(textWatch);

    }

    private TextWatcher textWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String  name =  edtName.getText().toString().trim();
            String  card =  edtCard.getText().toString().trim();

            if (name.length()>1&&card.length()==18){
                btnReal.setEnabled(true);
            }else {
                btnReal.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public void onNetSuccess() {
        hideWaitingDialog();
    }

    @Override
    public void onNetError() {
        hideWaitingDialog();
    }

    @OnClick({R.id.left_finish, R.id.btn_real, R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_finish:
                onBack();

                break;
            case R.id.btn_real:
                String  name =  edtName.getText().toString().trim();
                String  card =  edtCard.getText().toString().trim();

                if (!IdcardUtils.validateCard(card)) {
                    UIUtils.showToast("请输入有效身份证号");
                    return;
                }
                showWaitingDialog1(getResources().getString(R.string.wait_tip),true);
                mPresenter.certification(name,card);

                break;
            case R.id.btn_next:
                onBack();
                break;
            default:
                break;
        }
    }


    private  void  onBack(){
        showMaterialDialog("提示","没有实名认证部分功能无法解锁","继续","关闭");
    }

    @Override
    public void initFinish() {
        mPresenter.intent();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        onBack();
        return super.onKeyDown(keyCode, event);


    }
}
