package com.example.retrofitdemo;

import com.example.retrofitdemo.domain.CommentItem;
import com.example.retrofitdemo.domain.GetWithParamsResult;
import com.example.retrofitdemo.domain.JsonResult;
import com.example.retrofitdemo.domain.LoginResult;
import com.example.retrofitdemo.domain.PostFileResult;
import com.example.retrofitdemo.domain.postWithParamsResult;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface API {

	@GET("/get/text")
	Call<JsonResult> getJson();

	@GET("/get/text")
	Call<GetWithParamsResult> getWithParamsJson(@Query("keyword")String keyword ,
												@Query("page") int page ,
												@Query("order")String order );

	@GET("/get/text")
	Call<GetWithParamsResult> getWithParamsJson(@QueryMap Map<String,Object> params);

	@POST("/post/string")
	Call<postWithParamsResult> postWithParams(@Query("string")String content);

	@POST
	Call<postWithParamsResult> postWithUrl(@Url String url);

	@POST("/post/comment")
	Call<postWithParamsResult> postWithBody(@Body CommentItem commentItem);

	/**
	 * 文件提交
	 * @param part
	 * @return
	 */
	@Multipart
	@POST("/file/upload")
	Call<PostFileResult> postFile(@Part MultipartBody.Part part);

	@Multipart
	@POST("/files/upload")
	Call<PostFileResult> postFiles(@Part List<MultipartBody.Part> parts);

	/**
	 * 包含内容的文件提交
	 * @param part
	 * @param params
	 * @return
	 */
	@Multipart
	@POST("/file/params/upload")
	Call<PostFileResult> postFileWithParams(@Part MultipartBody.Part part,
											@PartMap Map<String,String> params);

	/**
	 * 表单提交
	 * @param userName
	 * @param password
	 * @return
	 */
	@FormUrlEncoded
	@POST("/login")
	Call<LoginResult> doLogin(@Field("userName") String userName,
							  @Field("password") String password);

	@FormUrlEncoded
	@POST("/login")
	Call<LoginResult> doLogin(@FieldMap Map<String,String> map);
}
