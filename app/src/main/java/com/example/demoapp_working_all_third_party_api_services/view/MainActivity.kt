package com.example.demoapp_working_all_third_party_api_services.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import com.example.demoapp_working_all_third_party_api_services.model.Company
import com.example.demoapp_working_all_third_party_api_services.model.Member
import com.example.demoapp_working_all_third_party_api_services.model.RetrofitServices
import com.example.demoapp_working_all_third_party_api_services.presenter.Presenter
import com.google.gson.Gson
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), MainView {

    private val presenter by lazy { Presenter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        presenter.sendActivity(this)
        presenter.getMembers()
        presenter.getHttpRequest()



        /*Glide
            .with(this)
            .load("https://avatars.githubusercontent.com/u/1342004?v=4")
            .into(findViewById(R.id.imageView))*/
/*
        Picasso.get()
            .load("Image Loader APP")
            .into(findViewById<ImageView>(R.id.imageView))*/

/*

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
*/


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


    }

    override fun showMembers(members: List<Member>) {

        // Getting a List of pictures using with Retrofit
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