package com.example.myapplication.base;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * 全局初始化
 */

public class BaseApplication extends LitePalApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
    }
}
