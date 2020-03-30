package com.example.taobaounion.model.domain.domain;

import com.example.taobaounion.domain.Catgories;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

	@GET("discover/categories")
	Call<Catgories> getCatgories();
}
