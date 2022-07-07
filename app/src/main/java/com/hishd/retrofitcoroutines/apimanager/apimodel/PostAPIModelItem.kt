package com.hishd.retrofitcoroutines.apimanager.apimodel

import com.google.gson.annotations.SerializedName

data class PostAPIModelItem(
    @SerializedName("body")
    val body: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: Int
)