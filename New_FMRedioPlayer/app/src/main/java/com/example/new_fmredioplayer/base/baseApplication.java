package com.example.new_fmredioplayer.base;

import android.app.Application;
import android.os.Handler;

import com.example.new_fmredioplayer.utils.LogUtils;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;


public class baseApplication extends Application {

	private static Handler sHandler = null;

	@Override
	public void onCreate() {
		super.onCreate();
		initkey();
		sHandler = new Handler();
	}

	private void initkey() {
		CommonRequest mXimalaya = CommonRequest.getInstanse();
		if(DTransferConstants.isRelease) {
			String mAppSecret = "8646d66d6abe2efd14f2891f9fd1c8af";
			mXimalaya.setAppkey("9f9ef8f10bebeaa83e71e62f935bede8");
			mXimalaya.setPackid("com.app.test.android");
			mXimalaya.init(this ,mAppSecret);
		} else {
			String mAppSecret = "0a09d7093bff3d4947a5c4da0125972e";
			mXimalaya.setAppkey("f4d8f65918d9878e1702d49a8cdf0183");
			mXimalaya.setPackid("com.ximalaya.qunfeng");
			mXimalaya.init(this ,mAppSecret);
		}

		LogUtils.init(this.getPackageName(),false);
	}

	public  static Handler getHandler(){
		return sHandler;
	}
}
