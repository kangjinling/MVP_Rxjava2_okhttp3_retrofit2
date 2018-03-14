package com.kang.utils;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
* @author ：kangjinling
* 邮箱 ：401205099@qq.com
* 功能描述 ：
*
*/

public class AndroidTools {


    /**
     * 根据传入控件的坐标和用户的焦点坐标，判断是否隐藏键盘，如果点击的位置在控件内，则不隐藏键盘
     *
     * @param view
     *            控件view
     * @param event
     *            焦点位置
     * @return 是否隐藏
     */
    public static void hideKeyboard(MotionEvent event, View view,
                                    Activity activity) {
        try {
            if (view != null && view instanceof EditText
                    ) {
                int[] location = { 0, 0 };
                view.getLocationInWindow(location);
                int left = location[0], top = location[1], right = left
                        + view.getWidth(), bootom = top + view.getHeight();
                // 判断焦点位置坐标是否在空间内，如果位置在控件外，则隐藏键盘
                if (event.getRawX() < left || event.getRawX() > right
                        || event.getY() < top || event.getRawY() > bootom) {
                    // 隐藏键盘
                    IBinder token = view.getWindowToken();
                    InputMethodManager inputMethodManager = (InputMethodManager) activity
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(token,
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**隐藏输入法
     * @param activity
     */
    public static void closeInputMethod(Activity activity) {
        try {
            InputMethodManager manager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(manager.isActive()) {
                manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**隐藏输入法
     * @param activity
     */
    public static void closeInputMethod(Activity activity, View myView) {
        try {
            InputMethodManager manager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(myView.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**显示输入法
     * @param activity
     * @param myView
     */
    public static void showInputMethod(Activity activity, View myView) {
        try {
            InputMethodManager manager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.showSoftInput(myView, InputMethodManager.SHOW_FORCED);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static int getWindowWidth(Context context){
        // 获取屏幕分辨率
        WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int mScreenWidth = dm.widthPixels;
        return mScreenWidth;
    }
    public static int getWindowHeigh(Context context){
        // 获取屏幕分辨率
        WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int mScreenHeigh = dm.heightPixels;
        return mScreenHeigh;
    }

}
