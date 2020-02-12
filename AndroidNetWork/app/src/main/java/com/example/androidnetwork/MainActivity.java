package com.example.androidnetwork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void loadJson(View view){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL("http://127.0.0.1:9102/get/text");
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setConnectTimeout(1000);
					connection.setRequestMethod("GET");
					connection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9");
					connection.setRequestProperty("Accept-Encoding","gzip, deflate");
					connection.setRequestProperty("Accept","*/*");
					connection.connect();
					//返回结果码
					int responseCode = connection.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) {
						Map<String, List<String>> headerFields = connection.getHeaderFields();
						Set<Map.Entry<String, List<String>>> entries = headerFields.entrySet();
						for (Map.Entry<String, List<String>> entry : entries) {
							Log.d(TAG,"entry -- > " + entry.getKey());
						}
						Object content = connection.getContent();
						Log.d(TAG,"content -- > " + content);
					}


				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
