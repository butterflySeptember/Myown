package com.example.retrofitdemo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {

	@GET("/get/text")
	Call<ResponseBody> getJson();

	@POST("/post/comment")
	Call<ResponseBody> po();
}
