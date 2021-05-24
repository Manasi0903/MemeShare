package com.example.memeshare

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"

class MainActivity : AppCompatActivity() {

    var currentImageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar: ActionBar?
        actionBar = supportActionBar
        val colorDrawable = ColorDrawable(Color.parseColor("#B19E9E"))
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(colorDrawable)
        };

        load()
    }

    private fun load() {
        val find = findViewById<ProgressBar>(R.id.progressBar)
        find.visibility = View.VISIBLE

        // Instantiate the RequestQueue.
        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                currentImageUrl = response.getString("url")
                val name = findViewById<ImageView>(R.id.memeImageView)
                Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable> {


                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        find.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        find.visibility = View.GONE
                        return false
                    }
                }).into(name)
            },
            {
            })

// Add the request to the RequestQueue.

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun shareMeme(view: View) {
        val mp = MediaPlayer.create(this, R.raw.click)
        mp.start()
        val intnet = Intent(Intent.ACTION_SEND).apply {
            type = "image/plain"
        }
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "Hey,Checkout this cool meme I got from Reddit $currentImageUrl"
        )
        val chooser = Intent.createChooser(intnet, "Share this meme using...")
        startActivity(chooser)
    }

        fun nextMeme(view: View) {
            val mp = MediaPlayer.create(this, R.raw.click)
            mp.start()
            load()
        }
    }