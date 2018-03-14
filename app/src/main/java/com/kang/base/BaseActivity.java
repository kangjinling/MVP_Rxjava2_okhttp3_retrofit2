package com.kang.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.ApLog;
import com.kang.R;
import com.kang.utils.AndroidTools;
import com.kang.widget.CustomDialog;
import com.kang.widget.MaterialDialog;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * @author ：kangjinlingon 2018/3/5.
 *         邮箱 ： 401205099@qq.com
 *         功能描述 ：
 */

public abstract class BaseActivity <T extends BasePresenter> extends AppCompatActivity implements BGASwipeBackHelper.Delegate {

    public static List<Activity> mActivities = new LinkedList<Activity>();
    protected T mPresenter;
    protected BGASwipeBackHelper mSwipeBackHelper;
    protected ImmersionBar mImmersionBar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);

        ApLog.setTag(getPackageName());

        //初始化沉浸式
        if (isImmersionBarEnabled()){
            initImmersionBar();
        }




        //初始化的时候将其添加到集合中
        synchronized (mActivities) {
            mActivities.add(this);
        }

        mPresenter = createPresenter();
        //子类不再需要设置布局ID，也不再需要使用ButterKnife.bind()
        setContentView(provideContentViewId());
       ButterKnife.bind(this);

        initView();
        initData();

    }
    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }
    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    public void requestPermission(String... permissions) {
        AndPermission.with(BaseApplication.getContext())
                .permission(permissions)
//                .rationale(mRationale)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        ApLog.e("获取权限成功");
                        onGranted();
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        ApLog.e("获取权限失败");
                        onDenied(permissions);

                    }
                })
                .start();



    }

    public void onGranted() {

    }
    public void onDenied(List<String> permissions) {

    }
    public void initView() {

    }
    public void initData() {

    }


    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();

    @Override
    protected void onDestroy() {

        //销毁的时候从集合中移除
        synchronized (mActivities) {
            mActivities.remove(this);
        }
        if (mPresenter != null) {
            mPresenter.detachView();
        }

        if (mImmersionBar != null){
            mImmersionBar.destroy();  //在BaseActivity里销毁
        }


        super.onDestroy();

        // AndroidTools.closeInputMethod(this);
    }


    /**
     * 完全退出
     * 一般用于“退出程序”功能
     */
    public static void exit() {
        for (Activity activity : mActivities) {
            activity.finish();
        }
    }

    /**
     * 不需要根据系统字体的大小来改变字体大小
     * @return
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics() );
        return res;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                AndroidTools.hideKeyboard(ev, view, BaseActivity.this);//调用方法判断是否需要隐藏键盘
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }



    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }
    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {

    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {

    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }


    private CustomDialog mDialogWaiting;
    public Dialog showWaitingDialog1(String tip, boolean b) {
        hideWaitingDialog();
        View view = View.inflate(this, R.layout.dialog_waiting, null);
        if (!TextUtils.isEmpty(tip)){
            ((TextView) view.findViewById(R.id.tvTip)).setText(tip);
        }
        mDialogWaiting = new CustomDialog(this, view, R.style.MyDialog);
        mDialogWaiting.show();
        mDialogWaiting.setCancelable(b);
        mDialogWaiting.setCanceledOnTouchOutside(b);
        mDialogWaiting.setOnCancelListener(dialogInterface -> {
            dialogInterface.dismiss();
            mPresenter.detachView();

        });
        return mDialogWaiting;
    }

    /**
     * 隐藏等待提示框
     */
    public void hideWaitingDialog() {
        if (mDialogWaiting != null) {
            mDialogWaiting.dismiss();
            mDialogWaiting = null;
        }
    }
    /**
     * 显示MaterialDialog
     */

    MaterialDialog mMaterialDialog;
    public void showMaterialDialog(String title, String message, String positiveText, String negativeText) {
        hideMaterialDialog();
        mMaterialDialog = new MaterialDialog(this);
        if (mMaterialDialog != null){
            mMaterialDialog.setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(positiveText,v -> {

                        mMaterialDialog.dismiss();

                    })
                    .setNegativeButton(negativeText,v -> {
                        initFinish();
                        mMaterialDialog.dismiss();

                    })
                    .setCanceledOnTouchOutside(false)
                    .show();
        }

    }

    public void initFinish() {

    }

    /**
     * 隐藏MaterialDialog
     */
    public void hideMaterialDialog() {
        if (mMaterialDialog != null) {
            mMaterialDialog.dismiss();
            mMaterialDialog = null;
        }
    }

}
