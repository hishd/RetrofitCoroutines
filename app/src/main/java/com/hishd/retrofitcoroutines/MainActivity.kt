package com.hishd.retrofitcoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.hishd.retrofitcoroutines.apimanager.apimodel.PostAPIModel
import com.hishd.retrofitcoroutines.apimanager.postmanager.PostManager
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var postManager: PostManager
    private val postData =  MutableLiveData<PostAPIModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupResources()
        setupObservers()
        getAllPosts()
    }

    private fun setupResources() {
        postManager = PostManager()
    }

    private fun setupObservers() {
        this.postData.observe(this) {   postData ->
            for(post in postData) {
                Log.d("POST", post.toString())
            }
        }
    }

    private fun getAllPosts() {
        lifecycleScope.launch() {
            postManager.getAllPosts().observe(this@MainActivity) {
                postData.value = it
            }
        }
    }
}