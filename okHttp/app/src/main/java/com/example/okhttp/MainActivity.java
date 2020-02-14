package com.example.okhttp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.okhttp.beans.commentItem;
import com.example.okhttp.utils.IOUtils;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import kotlin.Pair;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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

	public void postFile(View view){
		OkHttpClient client = new OkHttpClient.Builder()
				.connectTimeout(10000,TimeUnit.MILLISECONDS)
				.build();

		File file = new File("storage/emulated/0/Android/date/com.example.okhttp/files/Picture/11.png");

		MediaType mediaType = MediaType.parse("image/png");
		RequestBody fileBody = RequestBody.create(file,mediaType);

		RequestBody requestBody = new MultipartBody.Builder()
				.addFormDataPart("file",file.getName(),fileBody)
				.build();

		final Request request = new Request.Builder()
				.url(BASE_URL + "/file/upload")
				.post(requestBody)
				.build();

		Call task = client.newCall(request);
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
					if (body != null) {
						String result = body.string();
						Log.d(TAG,"result -- > "+ result);
					}
				}
			}
		});
	}

	public void postFiles(View view){
		OkHttpClient client = new OkHttpClient.Builder()
				.connectTimeout(1000,TimeUnit.MILLISECONDS)
				.build();

		File fileOne = new File("storage/emulated/0/Android/date/com.example.okhttp/files/Picture/11.png");
		File fileTwo = new File("storage/emulated/0/Android/date/com.example.okhttp/files/Picture/10.png");

		MediaType mediaType = MediaType.parse("image/png");
		RequestBody fileOneBody = RequestBody.create(fileOne,mediaType);
		RequestBody fileTwoBody = RequestBody.create(fileTwo,mediaType);

		RequestBody requestBody = new MultipartBody.Builder()
				.addFormDataPart("fileOne",fileOne.getName(),fileOneBody)
				.addFormDataPart("fileTwo",fileTwo.getName(),fileTwoBody)
				.build();

		Request request = new Request.Builder()
				.url(BASE_URL + "/files/upload")
				.post(requestBody)
				.build();

		Call task = client.newCall(request);
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
					if (body != null) {
						String result = body.string();
						Log.d(TAG,"result -- > "+ result);
					}
				}
			}
		});
	}

	public void downloadFile(View view){
		OkHttpClient client = new OkHttpClient.Builder()
				.connectTimeout(1000,TimeUnit.MILLISECONDS)
				.build();

		final Request request = new Request.Builder()
				.url(BASE_URL + "/download/10")
				.get()
				.build();

		final Call task = client.newCall(request);
	/**	//异步执行
		task.enqueue(new Callback() {
			@Override
			public void onFailure(@NotNull Call call, @NotNull IOException e) {
				Log.d(TAG,"onFailure -- > " + e.toString());
			}

			@Override
			public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
				ResponseBody body = response.body();
				if (body != null) {
					Log.d(TAG,"onResponse -- > " + response.);
				}
			}
		});*/

	//同步执行
		new Thread(new Runnable() {

			private InputStream mInputStream = null;
			private FileOutputStream mFos = null;

			@Override
			public void run() {
				try {
					Response execute = task.execute();
					Headers headers = execute.headers();
					for (int i = 0; i < headers.size(); i++) {
						String key = headers.name(i);
						String value = headers.value(i);
						Log.d(TAG,"key -- > " + key);
						Log.d(TAG,"values -- > " + value );
					}
					String contentType = headers.get("Content-Type");
					String fileName = contentType.replace("attachment; filename=", "");
					File outFile = new File(MainActivity.this.getExternalCacheDir(Environment.DIRECTORY_PICTURES) + File.separator + fileName);
					if (!outFile.getParentFile().exists()) {
						outFile.mkdir();
					}

					if (!outFile.exists()) {
						outFile.createNewFile();
					}
					mFos = new FileOutputStream(outFile);
					if (execute.body() != null) {
						mInputStream = execute.body().byteStream();
						byte[] buffer = new byte[1024];
						int len;
						while ((len = mInputStream.read(buffer, 0, buffer.length)) != -1) {
							mFos.write(buffer,0,buffer.length);
						}
						mFos.flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
					Log.d(TAG,"IOException -- > " + e.toString());
				} finally {
					IOUtils.isClose(mFos);
					IOUtils.isClose(mInputStream);
				}
			}
		}).start();
	}

	private String getExternalCacheDir(String directoryPictures) {
		return null;
	}

}
