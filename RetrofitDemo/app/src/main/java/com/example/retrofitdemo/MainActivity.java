package com.example.retrofitdemo;

import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitdemo.adapters.itemAdapter;
import com.example.retrofitdemo.domain.JsonResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

	public static final String BASE_URL = "http://10.0.2.2:9102";
	private final static String TAG = "MainActivity";
	private itemAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		RecyclerView recyclerView = findViewById(R.id.recycler_view_item);
		mAdapter = new itemAdapter();
		recyclerView.setAdapter(mAdapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
				super.onDraw(c, parent, state);
			}
		});
	}

	public void getRequest(View view){
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.build();
		API api = retrofit.create(API.class);
		Call<ResponseBody> task = api.getJson();
		task.enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
				Log.d(TAG,"Response code -- >" + response.code());
				if (response.code() == HttpURLConnection.HTTP_OK) {
					ResponseBody body = response.body();
					String result = body.toString();
					try {
						Log.d(TAG,"Response body -- > " + body.string());
						Gson gson = new Gson();
						JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
						updateList(jsonResult);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				Log.d(TAG,"onFailure -- > " + t.toString());
			}
		});
	}

	private void updateList(JsonResult jsonResult) {
		mAdapter.setData(jsonResult);
	}

	public void postRequest(View view){
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.build();
		API api = retrofit.create(API.class);

	}
}
