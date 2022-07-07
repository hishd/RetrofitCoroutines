package com.hishd.retrofitcoroutines.apimanager.apiinterface

import com.hishd.retrofitcoroutines.apimanager.apimodel.PostAPIModel
import retrofit2.Response
import retrofit2.http.GET

interface PostInterface {
    @GET("/posts")
    suspend fun getPosts() : Response<PostAPIModel>
}