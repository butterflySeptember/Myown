package com.example.taobaounion.View;

import com.example.taobaounion.domain.Catgories;

public interface IHomeCallback {

	void onCategoriesLoad(Catgories category);

	void onNetworkError();

	void onLoading();

	void onEmpty();
}
