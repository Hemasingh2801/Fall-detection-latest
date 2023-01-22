package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class positioncap extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float acceleration;
    private float currentAcceleration;
    private float lastAcceleration;
//    LocationManager locationManager;
//    Sensor sensor,sensor_main;
//    SensorManager sm;
//    TextView xText,yText,zText,total,svalue,samples,help_text,heading;
//    double sum,check_sum,value;
//    boolean min,max,flag;
//    int i,count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_positioncap);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        acceleration = 0.0f;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        lastAcceleration = SensorManager.GRAVITY_EARTH;
//        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
//        sensor=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        sensor_main=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        count = 0;
    }
    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    public void GotoUserprofile(View view){
        Intent intent=new Intent(this,User.class);
        this.startActivity(intent);
    }
    public void SwitchOnOff(View view){
        TextView tv1 = (TextView)findViewById(R.id.textonoff);
        String onoff=tv1.getText().toString();
        if(onoff=="Detection Status OFF"){
//            FallDetection();
            tv1.setText("Detection Status ON");
            tv1.setTextColor(getResources().getColor(R.color.SuccessGreen));
        }
        else{
            tv1.setText("Detection Status OFF");
            tv1.setTextColor(getResources().getColor(R.color.Red));
        }
    }
//    public void FallDetection(){
//        Intent intent=new Intent(this,xyzValues.class);
//        this.startActivity(intent);
//    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        sum = Math.round(Math.sqrt(Math.pow(event.values[0], 2)
//                + Math.pow(event.values[1], 2)
//                + Math.pow(event.values[2], 2)));
//        if (sum <= 5.0) {
//            min = true;
//
//        }
//
//        if (min == true) {
//            i++;
//            if (sum >= 16.5) {
//                max = true;
//            }
//        }
//
//        if (min == true && max == true) {
//            sm.unregisterListener(this);
//            Toast.makeText(this, "Suspected Fall", Toast.LENGTH_SHORT).show();
//            Intent test= new Intent(this,xyzValues.class);
//            startActivity(test);
//
//            min = false;
//            max = false;
//            if (count > 45) {
//                Toast.makeText(this,"Fall Confirmed",Toast.LENGTH_LONG).show();
//                i = 0;
//                count=0;
//                min = false;
//                max = false;
//            }
//        }
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        lastAcceleration = currentAcceleration;
        currentAcceleration = (float) Math.sqrt(x * x + y * y + z * z);
        float delta = currentAcceleration - lastAcceleration;
        acceleration = acceleration * 0.9f + delta;

        if (acceleration > 12) {
            Intent intent=new Intent(this,xyzValues.class);
            startActivity(intent);
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}