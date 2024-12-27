package com.example.demoapp_working_all_third_party_api_services

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.Glide
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.squareup.picasso.Picasso
import com.squareup.picasso.Request
import okhttp3.OkHttpClient
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        /*Glide
            .with(this)
            .load("https://avatars.githubusercontent.com/u/1342004?v=4")
            .into(findViewById(R.id.imageView))*/
/*
        Picasso.get()
            .load("Image Loader APP")
            .into(findViewById<ImageView>(R.id.imageView))*/

        //Jetpack Compose
        setContent {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Yellow)
            )

            GlideImage (
                imageModel = "https://avatars.githubusercontent.com/u/1342004?v=4",
                imageOptions = ImageOptions(
                    contentScale = ContentScale.FillBounds,
                    alignment = Alignment.Center
                )
            )
        }

        thread{httpRequest("https://api.github.com/orgs/google")}
        thread{okhttpRequest("https://api.github.com/orgs/google")}

    }

    // Send the request to network and receive the result using HttpURLConnection Using Log message
    private fun httpRequest(urlStr: String){
        val connection = URL(urlStr).openConnection() as HttpURLConnection
        val response = connection.inputStream.bufferedReader().use(BufferedReader::readLine)
        Log.d("HttpExample", "request: $response")
       }

    // Send the request to network and receive the result using OkHttp Using Log message
    private fun okhttpRequest(urlStr: String){
        val request = okhttp3.Request.Builder().url(urlStr).build()
        val response = OkHttpClient().newCall(request).execute().body?.string()
        Log.d("OKHttpExample", "request: $response")
       }

    // Retreive Particular Data from network using GSON Library
    private fun gsonRequest(urlStr: String){
        val request = okhttp3.Request.Builder().url(urlStr).build()
        val response = OkHttpClient().newCall(request).execute().body?.string()
        Log.d("gsonExample", "request: $response")
    }
    }