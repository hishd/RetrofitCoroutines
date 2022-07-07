package com.hishd.retrofitcoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.hishd.retrofitcoroutines.apimanager.apimodel.PostAPIModel
import com.hishd.retrofitcoroutines.apimanager.apimodel.PostAPIModelItem
import com.hishd.retrofitcoroutines.apimanager.postmanager.PostManager
import com.hishd.retrofitcoroutines.databinding.ActivityMainBinding
import com.hishd.retrofitcoroutines.util.NotificationHandler
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var postManager: PostManager
    private val postData =  MutableLiveData<PostAPIModel>()
    private lateinit var binding: ActivityMainBinding

    private lateinit var notificationHandler: NotificationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupResources()
        setupObservers()
        getAllPosts()
    }

    private fun setupResources() {
        postManager = PostManager()
        notificationHandler = NotificationHandler(this, "com.hishd.retrofitcoroutines.channel1", "Post Data", "Post Data Channel")
    }

    private fun setupObservers() {
        this.postData.observe(this) {   postData ->
            var textData = ""
            for(post in postData) {
                textData += "Post ID : ${post.id} \n Post Title : ${post.title} \n User ID : ${post.userId} \n Body : ${post.body} \n\n\n"
            }
            binding.txtData.text = textData
        }
    }

    private fun getAllPosts() {
        lifecycleScope.launch() {
            // Get all Post Data
//            postManager.getAllPosts().observe(this@MainActivity) {
//                postData.value = it
//            }

            // Get Post Data related to userId 10
//            postManager.getAllPostsByUser(userId = 10).observe(this@MainActivity) {
//                postData.value = it
//            }

            // Get Post Data by Id 5
//            postManager.getPostById(5).observe(this@MainActivity) {
//                val data = PostAPIModel()
//                data.add(it)
//                postData.value = data
//            }

            // Create a new post
            val post = PostAPIModelItem(
                body = "Newly created post",
                id = 0,
                title = "New Post Title",
                userId = 10
            )
            postManager.createNewPost(post).observe(this@MainActivity) {    status ->
                Toast.makeText(this@MainActivity, "Post creation ${if(status) "Successful" else "Failed"}", Toast.LENGTH_SHORT).show()
                notificationHandler.displayNotification(
                    0,
                    "Post Creation",
                    "Post creation ${if(status) "Successful" else "Failed"}",
                    android.R.drawable.ic_dialog_info,
                    true,
                    NotificationCompat.PRIORITY_HIGH
                )
            }
        }
    }
}