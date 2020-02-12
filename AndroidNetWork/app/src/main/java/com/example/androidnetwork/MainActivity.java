package com.example.androidnetwork;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidnetwork.adapters.GetResultListAdapter;
import com.example.androidnetwork.domain.GetTextItem;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";
	private GetResultListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		RecyclerView recyclerView = this.findViewById(R.id.recycle_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
				outRect.top = 5;
				outRect.bottom = 5;
			}
		});
		mAdapter = new GetResultListAdapter();
		recyclerView.setAdapter(mAdapter);
	}

	public void loadJson(View view){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
//					URL url = new URL("http://127.0.0.1:9102/get/text");
					URL url = new URL("https://www.sunofbeach.net/shop/api");
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
						InputStream inputStream = connection.getInputStream();
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
						String json = bufferedReader.readLine();
//						Object content = connection.getContent();
//						Log.d(TAG,"content -- > " + content);
						Gson gson = new Gson();
						GetTextItem getTextItem = gson.fromJson(json,GetTextItem.class);
						updateUI(getTextItem);
					}


				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void updateUI(final GetTextItem getTextItem) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mAdapter.setData(getTextItem);
			}
		});
	}
}
