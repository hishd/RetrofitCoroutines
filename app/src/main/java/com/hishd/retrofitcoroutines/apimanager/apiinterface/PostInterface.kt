package com.hishd.retrofitcoroutines.apimanager.apiinterface

import com.hishd.retrofitcoroutines.apimanager.apimodel.PostAPIModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostInterface {
    @GET("/posts")
    suspend fun getPosts() : Response<PostAPIModel>

    @GET("/posts")
    suspend fun getPostsByUser(@Query("userId") userId: Int) : Response<PostAPIModel>
}