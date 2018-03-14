package com.kang.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
* @author ：kangjinling
* 邮箱 ：401205099@qq.com
* 功能描述 ：不能左右滑动的ViewPager
*
*/
public class NoScrollViewPager extends ViewPager {

	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollViewPager(Context context) {
		super(context);
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return false;
	}
	
	/**
	 * 不拦截事件
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return false;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}
}
