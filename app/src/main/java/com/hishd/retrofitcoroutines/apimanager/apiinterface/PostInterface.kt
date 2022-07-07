package com.hishd.retrofitcoroutines.apimanager.apiinterface

import com.hishd.retrofitcoroutines.apimanager.apimodel.PostAPIModel
import com.hishd.retrofitcoroutines.apimanager.apimodel.PostAPIModelItem
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface PostInterface {
    @GET("/posts")
    suspend fun getPosts() : Response<PostAPIModel>

    @GET("/posts")
    suspend fun getPostsByUser(@Query("userId") userId: Int) : Response<PostAPIModel>

    @GET("/posts/{id}")
    suspend fun getPostById(@Path(value = "id") id: Int) : Response<PostAPIModelItem>

    @POST("/posts")
    suspend fun createPost(@Body post: PostAPIModelItem) : Response<ResponseBody>
}