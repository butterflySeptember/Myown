package com.example.service_demo.servicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class IntentServiceDemo extends IntentService {
    private static final String TAG = "IntentServiceDemo";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public IntentServiceDemo(String name) {
        super(name);
    }

    public IntentServiceDemo() {
        super("my-service");
        Log.i(TAG, "IntentServiceDemo: ");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //这个方法，在服务启动时候，创建handler，在onStart方法中，线程执行onHandleIntent、
        //这个方法执行完成之后，系统管理回收，我们不需要额外的处理，直接在这个子线程处理我们的业务
        Log.e(TAG, "onHandleIntent: " + Thread.currentThread().getName());

        //工作完之后如果需要UI交互,传递信息,可以用eventbus更新,或者其他的跨线程传递信息的方案也行
    }
}
