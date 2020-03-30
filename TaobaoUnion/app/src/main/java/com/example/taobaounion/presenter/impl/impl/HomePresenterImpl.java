package com.example.taobaounion.presenter.impl.impl;

import com.example.taobaounion.View.IHomeCallback;
import com.example.taobaounion.domain.Catgories;
import com.example.taobaounion.model.domain.domain.Api;
import com.example.taobaounion.presenter.impl.IHomePresenter;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.RetrofitManager;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomePresenterImpl implements IHomePresenter {


	private IHomeCallback mCallback = null;

	@Override
	public void getCategories() {
		Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
		Api api = retrofit.create(Api.class);
		Call<Catgories> task = api.getCatgories();
		task.enqueue(new Callback<Catgories>() {
			@Override
			public void onResponse(Call<Catgories> call, Response<Catgories> response) {
				//数据结果
				int code = response.code();
				Catgories catgories = response.body();
				if (code == HttpURLConnection.HTTP_OK) {
					LogUtils.d(HomePresenterImpl.class,"response.body -- > " + response.toString());
				}else {
					LogUtils.i(HomePresenterImpl.class,"response code -- > " + code);
				}
				if (mCallback != null) {
					mCallback.onCategoriesLoad(catgories);
				}
			}

			@Override
			public void onFailure(Call<Catgories> call, Throwable t) {
				LogUtils.i(HomePresenterImpl.class,"onFailure -- > " + t.toString());
			}
		});
	}

	@Override
	public void registerViewCallback(IHomeCallback callback) {
		this.mCallback = callback;
	}

	@Override
	public void unregisterViewCallback(IHomeCallback callback) {
		mCallback =	null;
	}
}
