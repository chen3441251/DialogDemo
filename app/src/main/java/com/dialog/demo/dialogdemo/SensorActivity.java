package com.dialog.demo.dialogdemo;

import android.app.Service;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.HashMap;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private final int DURATION_TIME = 600;
    SensorManager mSensorManager;
    private Vibrator mVibrator;
    private RelativeLayout mImgUp;
    private RelativeLayout mImgDn;
    private SoundPool      mSndPool;
    private HashMap<Integer, Integer> mSoundPoolMap = new HashMap<Integer, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        // 初始化控件
        mImgUp = (RelativeLayout) findViewById(R.id.shakeImgUp);
        mImgDn = (RelativeLayout) findViewById(R.id.shakeImgDown);
        // 获取传感器管理服务
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // 获取震动服务
        mVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        // 获取加速度传感器服务
        Sensor mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // 注册监听，获取传感器的变化值
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
        // 加载音效
        loadSound();
    }

    // 加载音效
    @SuppressWarnings("deprecation")
    private void loadSound() {
        mSndPool = new SoundPool(2, AudioManager.STREAM_SYSTEM, 5);
        new Thread() {
            @Override
            public void run() {
                mSoundPoolMap.put(0, mSndPool.load(SensorActivity.this, R.raw.avchat_connecting, 1));
                mSoundPoolMap.put(1, mSndPool.load(SensorActivity.this, R.raw.sound, 1));
                //                mSoundPoolMap.put(1, mSndPool.load(SensorActivity.this, R.raw.avchat_no_response, 1));
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消注册监听
        mSensorManager.unregisterListener(this);
    }

    //传感器的值变化
    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float[] val = event.values;
        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            if ((Math.abs(val[0]) > 17 || Math.abs(val[1]) > 17 || Math.abs(val[2]) > 17)) {
                mVibrator.vibrate(500);// 震动
                startAnim();// 执行动画
            }
        }
    }

    // 开启动画
    public void startAnim() {
        AnimationSet animup = new AnimationSet(true);
        TranslateAnimation mytranslateanimup0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                -0.5f);
        mytranslateanimup0.setDuration(DURATION_TIME);
        TranslateAnimation mytranslateanimup1 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                +0.5f);
        mytranslateanimup1.setDuration(DURATION_TIME);
        mytranslateanimup1.setStartOffset(DURATION_TIME);
        animup.addAnimation(mytranslateanimup0);
        animup.addAnimation(mytranslateanimup1);
        mImgUp.startAnimation(animup);

        AnimationSet animdn = new AnimationSet(true);
        TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                +0.5f);
        mytranslateanimdn0.setDuration(DURATION_TIME);
        TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                -0.5f);
        mytranslateanimdn1.setDuration(DURATION_TIME);
        mytranslateanimdn1.setStartOffset(DURATION_TIME);
        animdn.addAnimation(mytranslateanimdn0);
        animdn.addAnimation(mytranslateanimdn1);
        mImgDn.startAnimation(animdn);

        // 动画监听，开始时显示加载状态，
        mytranslateanimdn0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // 播放音效
                mSndPool.play(mSoundPoolMap.get(0), (float) 0.2, (float) 0.2, 0, 0, (float) 0.6);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mSndPool.play(mSoundPoolMap.get(1), (float) 0.2, (float) 0.2, 0, 0, (float) 0.6);
                Toast.makeText(getBaseContext(), "摇一摇结束", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    //传感器的进度变化
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

