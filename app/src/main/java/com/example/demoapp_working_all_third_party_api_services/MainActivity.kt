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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.Glide
import com.example.demoapp_working_all_third_party_api_services.GSONModel.Company
import com.example.demoapp_working_all_third_party_api_services.GSONModel.Member
import com.example.demoapp_working_all_third_party_api_services.Services.RetrofitServices
import com.google.gson.Gson
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.squareup.picasso.Picasso
import com.squareup.picasso.Request
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
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


        thread{httpRequest("https://api.github.com/orgs/google")}

        thread{okhttpRequest("https://api.github.com/orgs/google")}

        Thread {
            val avatarUrl = gsonRequest1("https://api.github.com/orgs/google")
            Log.d("GsonExample1", "avatarUrl: $avatarUrl")
        }.start()

        Thread {
            val publicMemberUrl = gsonRequest2("https://api.github.com/orgs/google")
            Log.d("GsonExample2", "publicMemberUrl: $publicMemberUrl")
        }.start()


        //handle multi-threading using RX-java
       /* val disposable = rxjavaRequest("https://api.github.com/orgs/google")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {avatarUrl ->
                Log.d("RXJavaExample", "avatarUrl: $avatarUrl thread ${Thread.currentThread().name}")
                setContent {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Yellow)
                    )

                    GlideImage (
                        //imageModel = "https://avatars.githubusercontent.com/u/1342004?v=4",
                        avatarUrl,
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.FillBounds,
                            alignment = Alignment.Center
                        )
                    )
                }
            }*/

        //handle multi-threading using Kotlin Coroutines

       /* runBlocking {
            val avatarUrl = coroutinesRequest("https://api.github.com/orgs/google")
            Log.d("CoroutinesExample", "avatarUrl: $avatarUrl thread ${Thread.currentThread().name}")
            setContent {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Yellow)
                )

                GlideImage (
                    //imageModel = "https://avatars.githubusercontent.com/u/1342004?v=4",
                    avatarUrl,
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.FillBounds,
                        alignment = Alignment.Center
                    )
                )
            }
        }*/

        //handle multi-threading using Kotlin Flow

        /*runBlocking {

            val avatarUrl = kotlinFlowRequest("https://api.github.com/orgs/google")
                .flowOn(Dispatchers.IO)
                .collect{avatarUrl ->

                    setContent {

                        Log.d("KotlinFlowExample", "avatarUrl: $avatarUrl thread ${Thread.currentThread().name}")

                        Box(modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Yellow)
                        )

                        GlideImage (
                            //imageModel = "https://avatars.githubusercontent.com/u/1342004?v=4",
                            avatarUrl,
                            imageOptions = ImageOptions(
                                contentScale = ContentScale.FillBounds,
                                alignment = Alignment.Center
                            )
                        )
                    }
                }
        }*/

        // Sending a request to network and getting the result with Retrofit

       /* val disposable = kotlinRetrofitRequest("https://api.github.com")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {company ->
                Log.d("RetrofitExample", "avatarUrl: ${company.avatarUrl} thread ${Thread.currentThread().name}")
                setContent {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Yellow)
                    )

                    GlideImage (
                        //imageModel = "https://avatars.githubusercontent.com/u/1342004?v=4",
                        company.avatarUrl,
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.FillBounds,
                            alignment = Alignment.Center
                        )
                    )
                }
            }*/

        // Getting a List of pictures using with Retrofit

        val disposable = getListPicturesRetrofitRequest("https://api.github.com/")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {members ->
                Log.d("GettingListOfPicturesRetrofitExample", "avatarUrl: ${members} ")
                setContent {
/*                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Yellow)
                    )*/

                    LazyVerticalGrid(columns = GridCells.Fixed(3)){
                        itemsIndexed(members) { _,member ->
                            GlideImage (
                                //imageModel = "https://avatars.githubusercontent.com/u/1342004?v=4",
                                member.avatarUrl,
                                imageOptions = ImageOptions(
                                    contentScale = ContentScale.Fit ,
                                    alignment = Alignment.Center
                                )
                            )
                        }
                    }

                }
            }
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
    private fun gsonRequest1(urlStr: String): String{
        val request = okhttp3.Request.Builder().url(urlStr).build()
        val response = OkHttpClient().newCall(request).execute().body?.string()
        val company = Gson().fromJson(response, Company::class.java)
        return company.avatarUrl
    }

    private fun gsonRequest2(urlStr: String): String{
        val request = okhttp3.Request.Builder().url(urlStr).build()
        val response = OkHttpClient().newCall(request).execute().body?.string()
        val company = Gson().fromJson(response, Company::class.java)
        return company.publicMemberUrl
    }

    // Using Rxjava to handle multi-threading when receiving requests
    private fun rxjavaRequest(urlStr: String): Single<String> {
        return Single.fromCallable{
            val request = okhttp3.Request.Builder().url(urlStr).build()
            val response = OkHttpClient().newCall(request).execute().body?.string()
            val company = Gson().fromJson(response, Company::class.java)
            company.avatarUrl
        }
    }

    // Using Kotlin Coroutines to handle multi-threading when receiving requests
    private suspend fun coroutinesRequest(urlStr: String) = withContext(Dispatchers.IO) {
            val request = okhttp3.Request.Builder().url(urlStr).build()
            val response = OkHttpClient().newCall(request).execute().body?.string()
            val company = Gson().fromJson(response, Company::class.java)
            company.avatarUrl

    }

    // Using Kotlin Flow to handle multi-threading when receiving requests
    private fun kotlinFlowRequest(urlStr: String): Flow<String> = flow {
        val request = okhttp3.Request.Builder().url(urlStr).build()
        val response = OkHttpClient().newCall(request).execute().body?.string()
        val company = Gson().fromJson(response, Company::class.java)
        emit(company.avatarUrl)

    }

    // Sending a request to network and getting the result with Retrofit

   /* private fun kotlinRetrofitRequest(urlStr: String): Single<Company>{
        return Retrofit.Builder()
            .baseUrl(urlStr)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(RetrofitServices::class.java)
            .company
    }*/

    // Getting a List of pictures using with Retrofit
    private fun getListPicturesRetrofitRequest(urlStr: String): Single<List<Member>>{
        return Retrofit.Builder()
            .baseUrl(urlStr)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(RetrofitServices::class.java)
            .members
    }
}