package com.example.johns.lumoshackcamera;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class DrawOnActivity extends Activity {

    private DrawingView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_on);

        drawingView =  (DrawingView) findViewById(R.id.drawingView);

        Bitmap b = (Bitmap) getIntent().getParcelableExtra("BitmapImage");

        Drawable drawable = new BitmapDrawable(getResources(),b);

        drawingView.setBackground(drawable);

        Button drawingViewButton = (Button)findViewById(R.id.button);
        drawingViewButton.setText("See Results");
        drawingViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.calculateAngle();
            }
        });
//
//        DrawingView dv = new DrawingView(this);
//        addContentView(dv, new RelativeLayout.LayoutParams(RelativeLayout.ALIGN_RIGHT, RelativeLayout.TRUE));
    }

}
