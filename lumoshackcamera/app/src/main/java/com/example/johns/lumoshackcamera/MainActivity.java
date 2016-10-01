package com.example.johns.lumoshackcamera;

import android.Manifest;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor gyroSensor;
    private TextView tv_output;
    private ImageView mImageView;

    private Bundle extras;

    private static final int IMAGE_CAPTURE = 1888;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int CAMERA_IMAGE_REQUEST = 3;
    private boolean isTaken = false;

    private ImageView imageView;

    private Bitmap bit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions(new String[]{Manifest.permission.CAMERA},
                24);

        this.imageView = (ImageView)this.findViewById(R.id.imageView1);

        tv_output=(TextView)findViewById(R.id.tempGyroOutput);
        sensorManager = (SensorManager)this.getSystemService (SENSOR_SERVICE);
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        Button button = (Button)this.findViewById(R.id.openCamera);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button n1 = (Button)findViewById(R.id.contin);
                n1.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                //if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, IMAGE_CAPTURE);
                //}
            }
        });


        Button button2 = (Button)this.findViewById(R.id.contin);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToDrawing = new Intent(getApplicationContext(), DrawOnActivity.class);

                goToDrawing.putExtra("BitmapImage", bit);
                //((DrawingView) findViewById(R.id.drawingView)).bitmap = bit;
                startActivity(goToDrawing);
            }
        });


    }

    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    //    getMenuInflater().inflate(R.menu.menu_main, menu);
    //    return true;
    //}
    @Override
    protected void onResume(){
        sensorManager.registerListener (this, gyroSensor, SensorManager.SENSOR_DELAY_GAME);
        super.onResume();
    }
    @Override
    protected void onPause(){
        sensorManager.unregisterListener(this, gyroSensor);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (!isTaken) {
            if (event.values[2] < 1.0 && event.values[1] > 9.0) {
                tv_output.setText("TAKE PICTURE");
                findViewById(R.id.openCamera).setEnabled(true);
            } else {
                tv_output.setText("LINE UP PHONE PERPENDICULAR TO GROUND");
                findViewById(R.id.openCamera).setEnabled(false);
            }
            //tv_output.setText(tv_output.getText()+)
        } else {
            tv_output.setText("Image taken Below. Click Continue or Re-take Image");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       // if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
        isTaken=true;

        Button n1 = (Button)findViewById(R.id.contin);
        n1.setVisibility(View.VISIBLE);
        findViewById(R.id.openCamera).setEnabled(true);

            bit = (Bitmap) data.getExtras().get("data");

            imageView.setImageBitmap(bit);
    }
}
