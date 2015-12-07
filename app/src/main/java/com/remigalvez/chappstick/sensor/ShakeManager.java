package com.remigalvez.chappstick.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by jared on 4/15/15.
 */
public class ShakeManager implements SensorEventListener {
    private static final String TAG = "ShakeManager";
    private static final int SHAKE_THRESHOLD = 1000;

    private ShakeListener mShakeListener;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    //history
    private long mLastAccelerometerUpdate;
    private float mLastX;
    private float mLastY;
    private float mLastZ;

    public interface ShakeListener{
        void shakeDetected();
    }


    public ShakeManager(Context context) {

        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void setListener(ShakeListener listener){
        mShakeListener = listener;
    }

    public void turnOn(){
        mSensorManager.registerListener(this, mAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void turnOff(){
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        long curTime = System.currentTimeMillis();
        // only allow one update every 100ms.
        if ((curTime - mLastAccelerometerUpdate) > 100) {
            long diffTime = (curTime - mLastAccelerometerUpdate);
            mLastAccelerometerUpdate = curTime;

            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            float speed = Math.abs(x + y + z - mLastX - mLastY - mLastZ)
                    / diffTime * 10000;

            if (speed > SHAKE_THRESHOLD) {
                Log.d(TAG, "shake detected w/ speed: " + speed);
                if(mShakeListener != null) {
                    mShakeListener.shakeDetected();
                }
                else{
                    Log.d(TAG, "No Shake Listener defined!");
                }

            }
            mLastX = x;
            mLastY = y;
            mLastZ = z;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
