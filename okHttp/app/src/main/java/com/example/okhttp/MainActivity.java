package com.example.okhttp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.okhttp.beans.commentItem;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;

public class MainActivity extends AppCompatActivity {

	public static final String BASE_URL = "http://10.0.2.2:9102";
	private static final int PERMISSION_CODE =  1;
	private final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//读取权限
		int result = 0;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			result = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
		}
		if (result != PackageManager.PERMISSION_DENIED) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_CODE);
			}
		}
	}

	public void getRequest(View view){
		//创建客户端
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.connectTimeout(10000, TimeUnit.MILLISECONDS)
				.build();
		//创建请求内容
		Request request = new Request.Builder()
				.get()
				.url(BASE_URL + "/get/text")
				.build();
		//用OkHttpClient创建请求任务
		Call task = okHttpClient.newCall(request);

		task.enqueue(new Callback() {
			@Override
			public void onFailure(@NotNull Call call, @NotNull IOException e) {
				Log.d(TAG,"onFailure -- > " + e.toString());
			}

			@Override
			public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
				int code = response.code();
				Log.d(TAG,"response code -- > " + code);
				if (code == HttpURLConnection.HTTP_OK) {
					ResponseBody body = response.body();
					Log.d(TAG,"body -- > " + body);
				}
			}
		});
	}

	public void postComment(View view){
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.connectTimeout(10000,TimeUnit.MILLISECONDS)
				.build();

		//需要提交的内容
		commentItem item = new commentItem("51549552","这是一条评论");
		Gson gson = new Gson();
		String jsonStr = gson.toJson(item);
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody requestBody = RequestBody.create(jsonStr,mediaType);
		Request request = new Request.Builder()
				.post(requestBody)
				.url(BASE_URL + "/post/text")
				.build();
		Call task = okHttpClient.newCall(request);
		task.enqueue(new Callback() {
			@Override
			public void onFailure(@NotNull Call call, @NotNull IOException e) {
				Log.d(TAG,"onFailure -- > " + e.toString());
			}

			@Override
			public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
				int code = response.code();
				Log.d(TAG,"response code -- > " + code);
				if (code == HttpURLConnection.HTTP_OK) {
					ResponseBody body = response.body();
					Log.d(TAG,"body -- > " + body);
				}
			}
		});
	}
}
