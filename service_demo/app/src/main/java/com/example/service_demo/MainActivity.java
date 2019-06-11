package com.example.service_demo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.service_demo.servicedemo.BindServiceDemo;
import com.example.service_demo.servicedemo.IntentServiceDemo;
import com.example.service_demo.servicedemo.StartServiceDemo;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Intent startServiceIntent;
    private Intent bindServiceIntent;
    private BindServiceDemo bindServiceDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startServiceIntent = new Intent(this, StartServiceDemo.class);
        bindServiceIntent = new Intent(this, BindServiceDemo.class);

    }

    /**
     * start 方式启动service
     *
     * @param view
     */
    public void start(View view) {
        startService(startServiceIntent);
    }

    public void stop(View view) {
        stopService(startServiceIntent);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BindServiceDemo.LocalBinder binder = (BindServiceDemo.LocalBinder) service;
            bindServiceDemo = binder.getService();
            Log.i(TAG, "onServiceConnected: succeed");
            registerCallback();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //service崩溃了或被强杀,当客户端解除绑定时，这个方法不会被调用
            Log.e(TAG, "onServiceDisconnected: " + name);
        }
    };

    private void registerCallback() {
        if (bindServiceDemo != null) {
            bindServiceDemo.setPushMessageListener(new BindServiceDemo.IPushMessageListener() {
                @Override
                public void updateUI(String msg) {
                    Toast.makeText(MainActivity.this, "push :" + msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //只有activities,services,和content providers可以绑定到一个service—
    // 你不能从一个broadcast receiver绑定到service
    public void bind(View view) {
        //绑定是异步的
        bindService(bindServiceIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    public void sum(View view) {
        if (bindServiceDemo != null) {
            int sum = bindServiceDemo.sum(1, 1);
            Toast.makeText(this, "sum " + sum, Toast.LENGTH_SHORT).show();
        }
    }

    public void getPush(View view) {
        if (bindServiceDemo != null) {
            bindServiceDemo.testGetPushMessage();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    public void startIntentService(View view) {
        startService(new Intent(this, IntentServiceDemo.class));
    }
}
