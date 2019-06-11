package com.example.service_demo.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * ①启动服务第一种方式，组件（例如activity）直接启动
 *
 * ②多次启动service，onCreate只执行一次
 *
 * ③onStartCommand每次都执行，且他们都在主线程中，不能做耗时，否则阻塞主线程
 *
 * ④何时停止（销毁）？？某个组件调用stopService（intent），或者内部stopSelf()
 *
 * ⑤适合长时间为前台提供服务，音乐播放器，运动计数步数，长时间推送服务
 */
public class StartServiceDemo extends Service {
    private static final String TAG = "StartServiceDemo";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: thread -> " + Thread.currentThread().getName());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: " + "start id :" + startId +
                "thread -> " + Thread.currentThread().getName());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
