package com.kang.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * @author ：kangjinlingon 2018/3/7.
 *         邮箱 ： 401205099@qq.com
 *         功能描述 ：
 */

public class MimageButton extends android.support.v7.widget.AppCompatImageButton {

    private String text = null;  //要显示的文字
    private int color;               //文字的颜色

    public MimageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public  void setText(String text){
        this.text = text;
    }

    public void setColor(int color){
        this.color = color;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(color);
        canvas.drawText(text, 15, 40, paint);  //绘制文字
    }
}
