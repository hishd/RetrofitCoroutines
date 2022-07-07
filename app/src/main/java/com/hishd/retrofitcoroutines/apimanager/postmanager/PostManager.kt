package com.hishd.retrofitcoroutines.apimanager.postmanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.hishd.retrofitcoroutines.apimanager.RetrofitInstance
import com.hishd.retrofitcoroutines.apimanager.apiinterface.PostInterface
import com.hishd.retrofitcoroutines.apimanager.apimodel.PostAPIModel

class PostManager {
    private var retInstance: PostInterface = RetrofitInstance.getRetrofitInstance().create(PostInterface::class.java)

    suspend fun getAllPosts(): LiveData<PostAPIModel> {
//        val responseData: LiveData<Response<PostAPIModel>> = liveData {
//            val response = retInstance.getPosts()
//            emit(response)
//        }
        val postData: LiveData<PostAPIModel> = liveData {
            retInstance.getPosts().body()?.let { emit(it) }
        }
        return postData
    }
}