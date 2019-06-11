package com.example.service_demo.servicedemo;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;


public class BindServiceDemo extends Service {
    private static final String TAG = "BindServiceDemo";
    private IBinder binder = new LocalBinder();
    private IPushMessageListener iPushMessageListener;
    private static final int KEY_PUSH = 10;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: thread -> " + Thread.currentThread().getName());
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind: ");
        return binder;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }

    public class LocalBinder extends Binder {
        public BindServiceDemo getService() {
            return BindServiceDemo.this;
        }
    }


    public void setPushMessageListener(IPushMessageListener pushMessageListener) {
        this.iPushMessageListener = pushMessageListener;
    }

    public interface IPushMessageListener {
        void updateUI(String msg);
    }


    //如果耗时操作，需要开线程执行！
    public int sum(int a, int b) {
        Log.i(TAG, "sum: method " + Thread.currentThread().getName());
        return a + b;
    }

    public void testGetPushMessage() {
        //这个获取消息是异步的，需要很长的时间的
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //模拟耗时操作，在子线程执行
                    Thread.sleep(3000);
                    handler.sendEmptyMessage(KEY_PUSH);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case KEY_PUSH:
                    if (iPushMessageListener != null) {
                        iPushMessageListener.updateUI("这是推送消息~");
                    }
                    break;
            }

        }
    };
}
