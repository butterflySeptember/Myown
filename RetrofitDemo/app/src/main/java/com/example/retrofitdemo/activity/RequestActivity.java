package com.example.retrofitdemo.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;

import com.example.retrofitdemo.API;
import com.example.retrofitdemo.R;
import com.example.retrofitdemo.domain.CommentItem;
import com.example.retrofitdemo.domain.GetWithParamsResult;
import com.example.retrofitdemo.domain.JsonResult;
import com.example.retrofitdemo.domain.LoginResult;
import com.example.retrofitdemo.domain.PostFileResult;
import com.example.retrofitdemo.domain.postWithParamsResult;
import com.example.retrofitdemo.utils.RetrofitManager;

import java.io.File;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RequestActivity extends AppCompatActivity {

	private static final String TAG = "RequestActivity";
	private static final int REQUEST_CODE = 1;
	private API mApi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request);
		mApi = RetrofitManager.getRetrofit().create(API.class);
		//动态申请权限
		int permissionResult = checkCallingPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
		if (permissionResult != PackageManager.PERMISSION_GRANTED) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE);
			}
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		//权限结果返回处理
		if (requestCode == REQUEST_CODE) {
			//todo:
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	public void getRequest(View view){
		Call<GetWithParamsResult> task = mApi.getWithParamsJson("关键字",10,"1");
		task.enqueue(new Callback<GetWithParamsResult>() {
			@Override
			public void onResponse(Call<GetWithParamsResult> call, Response<GetWithParamsResult> response) {

				if (response.code() == HttpURLConnection.HTTP_OK) {
					Log.d(TAG,"response -- > " + response.body());
				}
			}

			@Override
			public void onFailure(Call<GetWithParamsResult> call, Throwable t) {
				Log.d(TAG,"onFailure ...");
			}
		});
	}

	public void getParamsQueryMap(View view){
		Map<String,Object> params = new HashMap<>();
		params.put("keyword","关键字");
		params.put("page",10);
		params.put("order","1");
		Call<GetWithParamsResult> task = mApi.getWithParamsJson(params);
		task.enqueue(new Callback<GetWithParamsResult>() {
			@Override
			public void onResponse(Call<GetWithParamsResult> call, Response<GetWithParamsResult> response) {
				if (response.code() == HttpURLConnection.HTTP_OK) {
					Log.d(TAG,"Response -- > " + response.toString());
				}
			}

			@Override
			public void onFailure(Call<GetWithParamsResult> call, Throwable t) {
				Log.d(TAG,"onFailure -- > " + t.toString());
			}
		});
	}

	public void postWithParams(View view){

		Call<postWithParamsResult> task = mApi.postWithParams("我是内容");
		task.enqueue(new Callback<postWithParamsResult>() {
			@Override
			public void onResponse(Call<postWithParamsResult> call, Response<postWithParamsResult> response) {
				if (response.code() == HttpURLConnection.HTTP_OK) {
					Log.d(TAG,"onResponse -- > " + response.toString());
				}
			}

			@Override
			public void onFailure(Call<postWithParamsResult> call, Throwable t) {
				Log.d(TAG,"onFailure -- > " + t.toString());
			}
		});
	}

	public void postWithUrl(View view){
		String url = "/post/string?string=内容";
		Call<postWithParamsResult> task = mApi.postWithUrl(url);
		task.enqueue(new Callback<postWithParamsResult>() {
			@Override
			public void onResponse(Call<postWithParamsResult> call, Response<postWithParamsResult> response) {
				if (response.code() == HttpURLConnection.HTTP_OK) {
					Log.d(TAG,"onResponse -- > " + response.toString());
				}
			}

			@Override
			public void onFailure(Call<postWithParamsResult> call, Throwable t) {
				Log.d(TAG,"onFailure -- > " + t.toString());
			}
		});
	}

	public void postWithBody(View view){
		CommentItem commentItem = new CommentItem("234123","评论");
		Call<postWithParamsResult> task = mApi.postWithBody(commentItem);
		task.enqueue(new Callback<postWithParamsResult>() {
			@Override
			public void onResponse(Call<postWithParamsResult> call, Response<postWithParamsResult> response) {
				if (response.code() == HttpURLConnection.HTTP_OK) {
					Log.d(TAG,"onResponse -- > " + response.toString());
				}
			}

			@Override
			public void onFailure(Call<postWithParamsResult> call, Throwable t) {
				Log.d(TAG,"onFailure -- > " + t.toString());
			}
		});
	}

	public void postFile(View view){
		MultipartBody.Part part = createFileByPath("/storage/emulated/0/Download/1.jpg");
		Call<PostFileResult> task = mApi.postFile(part);
		task.enqueue(new Callback<PostFileResult>() {
			@Override
			public void onResponse(Call<PostFileResult> call, Response<PostFileResult> response) {
				if (response.code() == HttpURLConnection.HTTP_OK) {
					Log.d(TAG,"onResponse -- > " + response.toString());
				}
			}

			@Override
			public void onFailure(Call<PostFileResult> call, Throwable t) {
				Log.d(TAG,"onFailure -- > " + t.toString());
			}
		});
	}

	public void postFiles(View view){
		List<MultipartBody.Part> parts = new ArrayList<>();
		MultipartBody.Part partOne = createFileByPath("/storage/emulated/0/Download/1.jpg");
		MultipartBody.Part partTwo = createFileByPath("/storage/emulated/0/Download/11.jpg");
		parts.add(partOne);
		parts.add(partTwo);
		Call<PostFileResult> task = mApi.postFiles(parts);
		task.enqueue(new Callback<PostFileResult>() {
			@Override
			public void onResponse(Call<PostFileResult> call, Response<PostFileResult> response) {
				if (response.code() == HttpURLConnection.HTTP_OK) {
					Log.d(TAG,"onResponse -- > " + response.toString());
				}
			}

			@Override
			public void onFailure(Call<PostFileResult> call, Throwable t) {
				Log.d(TAG,"onFailure -- > " + t.toString());
			}
		});
	}

	public void postFileWithParams(View view){
		MultipartBody.Part part = createFileByPath("/storage/emulated/0/Download/1.jpg");
		Map<String,String> param = new HashMap<>();
		param.put("description","这是一张图片");
		Call<PostFileResult> task = mApi.postFileWithParams(part, param);
		task.enqueue(new Callback<PostFileResult>() {
			@Override
			public void onResponse(Call<PostFileResult> call, Response<PostFileResult> response) {
				if (response.code() == HttpURLConnection.HTTP_OK) {
					Log.d(TAG,"onResponse -- > " + response.toString());
				}
			}

			@Override
			public void onFailure(Call<PostFileResult> call, Throwable t) {
				Log.d(TAG,"onFailure -- > " + t.toString());
			}
		});
	}

	public void doLogin(View view){
		Call<LoginResult> task = mApi.doLogin("登录名", "密码");
		task.enqueue(new Callback<LoginResult>() {
			@Override
			public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
				if (response.code() == HttpURLConnection.HTTP_OK) {
					Log.d(TAG,"onResponse -- > " + response.toString());
				}
			}

			@Override
			public void onFailure(Call<LoginResult> call, Throwable t) {
				Log.d(TAG,"onFailure -- > " + t.toString());
			}
		});
	}

	private MultipartBody.Part createFileByPath(String s) {
		File fileTwo = new File(s);
		RequestBody bodyTwo = MultipartBody.create(MediaType.parse("image/jpg"), fileTwo);
		return MultipartBody.Part.createFormData("file", fileTwo.getName(), bodyTwo);
	}


}
