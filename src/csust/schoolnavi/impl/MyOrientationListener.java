package csust.schoolnavi.impl;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by CXY on 2015/8/13.
 */
public class MyOrientationListener implements SensorEventListener {

    private Context context;
    private SensorManager sensorManager;
    private Sensor sensor;

    private float lastx;
    private OnOrientationListener onOrientationListener;

    public MyOrientationListener(Context context) {
        this.context = context;
    }

    //开始监听传感器
    public void start() {
        //获得传感器管理器
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            //获得方向传感器
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        }
        //注册到传感器
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);//SENSOR_DELAY_UI用传感器来更新UI
        }
    }

    //停止监听
    public void stop() {
        sensorManager.unregisterListener(this);
    }

    /*
    传感器方向发生改变时
    在此处需要对手机朝向进行检测，所以只需要重写此方向，对精度无要求
    * */
    @Override
    public void onSensorChanged(SensorEvent event) {

        //检测是否方向传感器的类型
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {

            float x = event.values[SensorManager.DATA_X];

            //检测方向改变的角度
            if (Math.abs(x - lastx) > 0.01) {
                onOrientationListener.onOrientationChanged(x);
            }

            lastx = x;
        }
    }


    public void setOnOrientationListener(OnOrientationListener onOrientationListener) {
        this.onOrientationListener = onOrientationListener;
    }

    public interface OnOrientationListener {
        void onOrientationChanged(float x);
    }

    //传感器精度发生改变时
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
