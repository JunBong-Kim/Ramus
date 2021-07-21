package com.hackathon.ramus;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by shh on 2016-04-19.
 */
public class MyProgressBar extends View {
    private int curValue; //현재값 저장
    private int maxValue; //최대값 저장
    private int lineColor; //진행바 색 저장


    public MyProgressBar(Context context) {
        super(context);
    }

    public MyProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.MyProgressBar,0,0);

        try{
            this.curValue = a.getInteger(R.styleable.MyProgressBar_curValue,50);
            this.maxValue = a.getInteger(R.styleable.MyProgressBar_maxValue,100);
            this.lineColor = a.getColor(R.styleable.MyProgressBar_lineColor,0xff000000);

        }finally {

        }

    }


    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int color) {
        this.lineColor = color;
        invalidate();
        requestLayout();
    }

    public int getCurValue() {
        return curValue;
    }

    public void setCurValue(int curValue) {
        this.curValue = curValue;
        invalidate();
        requestLayout();
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        invalidate();
        requestLayout();
    }


    Handler mHandler = new Handler();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width  =  120;
        int height = 120;






        // 진행바 그리기 속성
        Paint circle = new Paint();
        circle.setColor(this.lineColor);
        circle.setStrokeWidth(10);
        circle.setAntiAlias(false);
        circle.setStyle(Paint.Style.STROKE);


        // 진행바 그리기
        canvas.drawArc(new RectF(0,0,width,height),-90,((float)this.curValue/(float)this.maxValue*360),false,circle);


        // 텍스트 그리기 속성
        Paint textp = new Paint();
        textp.setColor(Color.BLACK);
        textp.setTextSize(20);
        textp.setTextAlign(Paint.Align.CENTER);



            canvas.drawText(this.curValue + " / " + this.maxValue, width / 2, height / 2, textp);



        // 1초후에 invalidate()호출을 통해 해당 View를 다시 그리게 한다.
        // 깜빡깜빡거리는 효과를 위해서 아래 코드 사용
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                invalidate();
            }
        },1000);


    }
}
