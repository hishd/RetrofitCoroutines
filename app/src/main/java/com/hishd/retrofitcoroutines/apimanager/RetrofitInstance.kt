package com.hishd.retrofitcoroutines.apimanager

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val interceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
        //Connect with server timeout
        this.connectTimeout(30, TimeUnit.SECONDS)
        //Response from server after connecting timeout
        this.readTimeout(20, TimeUnit.SECONDS)
        //Timeout for sending data packets to server
        this.readTimeout(20, TimeUnit.SECONDS)
    }.build()

    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }
}