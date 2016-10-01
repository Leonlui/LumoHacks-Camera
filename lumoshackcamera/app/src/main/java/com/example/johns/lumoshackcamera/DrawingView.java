package com.example.johns.lumoshackcamera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by johns on 2016-09-18.
 */
public class DrawingView extends View{
    private Paint paint = new Paint();

    private float[] startX = {-1,-1};
    private float[] startY = {-1,-1};
    private float[] endX = {-1,-1};
    private float[] endY = {-1,-1};

    private int index=0;

    private boolean showResults;
    private double angle;


    public DrawingView(Context context) {
        super(context);

        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setColor(Color.CYAN);
        paint.setStyle(Paint.Style.STROKE);
        showResults=false;

    }

    public DrawingView(Context context, AttributeSet attrSet) {
        super(context,attrSet);

        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setColor(Color.CYAN);
        paint.setStyle(Paint.Style.STROKE);
        showResults=false;

    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvas.drawARGB(0,0,0,0);
        canvas.drawLine(startX[0],startY[0],endX[0],endY[0],paint);
        canvas.drawLine(startX[1],startY[1],endX[1],endY[1],paint);

        canvas.drawCircle(startX[0],startY[0],10,paint);
        for(int i=0;i<=index;i++){
            canvas.drawCircle(endX[i],endY[i],10,paint);
        }


        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        canvas.drawRect(0,getHeight()-280,getWidth(),getHeight(),blackPaint);
        Paint whitePaint=new Paint();
        whitePaint.setColor(Color.WHITE);
        whitePaint.setTextSize(70);
        canvas.drawText("Clear",getWidth()/2-45,getHeight()-80,whitePaint);

        whitePaint.setTextSize(100);
        whitePaint.setColor(Color.GREEN);
        if(angle<90)
            whitePaint.setColor(Color.YELLOW);
        if(angle<35)
            whitePaint.setColor(Color.RED);

        if(showResults) {
            String angle7Digits = (Double.toString(angle).length()>7)?Double.toString(angle).substring(0,7):Double.toString(angle);
            canvas.drawText(angle7Digits+"°", getWidth() / 2-150, getHeight() - 140, whitePaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchPress(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchRelease(x, y);
                invalidate();
                break;
        }
        return true;
    }

    private void touchPress(float x, float y){
        if(y>this.getHeight()-200) resetLineButton();
        else {
            startX[index] = x;
            startY[index] = y;
            endX[index] = x;
            endY[index] = y;
        }
        if(index>0){
            startX[index]=endX[index-1];
            startY[index]=endY[index-1];
        }
    }

    private void touchMove(float x, float y){

        if(y>this.getHeight()-200) return;
        endX[index]=x;
        endY[index]=y;
    }
    private void touchRelease(float x, float y){

        if(y<=this.getHeight()-200) index=1;
    }

    public void resetLineButton(){

        startX=new float[2];
        startY=new float[2];
        endX=new float[2];
        endY=new float[2];
        index=0;
    }

    public void calculateAngle(){
        PopupWindow popWind = new PopupWindow(this);
        TextView text = new TextView(getContext());
        float dx = endX[0]-startX[0];
        float dy = endY[0]-startY[0];
        angle = Math.toDegrees(Math.atan(dy/dx));



        dx = endX[1]-startX[1];
        dy = startY[1]-endY[1];
        angle+=Math.toDegrees(Math.atan(dy/dx));
        if (angle<0)
            angle=180+angle;

        Log.i("i", Double.toString(angle));
        Log.i("i", Double.toString(endX[1]));
        Log.i("i", Double.toString(startX[1]));
        Log.i("i", Double.toString(endY[1]));
        Log.i("i", Double.toString(startY[1]));
        //text.setText(Double.toString(angle));
        //popWind.setContentView(text);

        //popWind.showAtLocation(this, Gravity.CENTER,0,0);

        showResults=true;
        invalidate();
    }

    //@Override
    //public void onPause(){
    //    popWind.dismiss();;
    //}
}
