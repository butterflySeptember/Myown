package com.example.retrofitdemo.utils;

import retrofit2.Retrofit;

public class RetrofitManager {

	private static final String BASE_URL = "http://10.0.2.2:9102";

	private static Retrofit retrofit = new Retrofit.Builder()
			.baseUrl(BASE_URL)
			.build();

	public static Retrofit getRetrofit(){
		return retrofit;
	}
}
