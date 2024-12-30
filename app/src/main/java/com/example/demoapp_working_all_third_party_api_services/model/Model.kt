package com.example.demoapp_working_all_third_party_api_services.model

import android.util.Log
import com.google.gson.Gson
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL

class Model {

    // Send the request to network and receive the result using HttpURLConnection Using Log message
    fun httpRequest(urlStr: String){
        val connection = URL(urlStr).openConnection() as HttpURLConnection
        val response = connection.inputStream.bufferedReader().use(BufferedReader::readLine)
        Log.d("HttpExample", "request: $response")
    }

    // Send the request to network and receive the result using OkHttp Using Log message
    fun okhttpRequest(urlStr: String){
        val request = okhttp3.Request.Builder().url(urlStr).build()
        val response = OkHttpClient().newCall(request).execute().body?.string()
        Log.d("OKHttpExample", "request: $response")
    }

    // Retreive Particular Data from network using GSON Library
    fun gsonRequest1(urlStr: String): String{
        val request = okhttp3.Request.Builder().url(urlStr).build()
        val response = OkHttpClient().newCall(request).execute().body?.string()
        val company = Gson().fromJson(response, Company::class.java)
        return company.avatarUrl
    }
    fun gsonRequest2(urlStr: String): String{
        val request = okhttp3.Request.Builder().url(urlStr).build()
        val response = OkHttpClient().newCall(request).execute().body?.string()
        val company = Gson().fromJson(response, Company::class.java)
        return company.publicMemberUrl
    }

    // Using Rxjava to handle multi-threading when receiving requests
    fun rxjavaRequest(urlStr: String): Single<String> {
        return Single.fromCallable{
            val request = okhttp3.Request.Builder().url(urlStr).build()
            val response = OkHttpClient().newCall(request).execute().body?.string()
            val company = Gson().fromJson(response, Company::class.java)
            company.avatarUrl
        }
    }

    // Using Kotlin Coroutines to handle multi-threading when receiving requests
    suspend fun coroutinesRequest(urlStr: String) = withContext(Dispatchers.IO) {
        val request = Request.Builder().url(urlStr).build()
        val response = OkHttpClient().newCall(request).execute().body?.string()
        val company = Gson().fromJson(response, Company::class.java)
        company.avatarUrl

    }

    // Using Kotlin Flow to handle multi-threading when receiving requests
    fun kotlinFlowRequest(urlStr: String): Flow<String> = flow {
        val request = Request.Builder().url(urlStr).build()
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
    fun getListPicturesRetrofitRequest(urlStr: String): Single<List<Member>> {
        return Retrofit.Builder()
            .baseUrl(urlStr)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(RetrofitServices::class.java)
            .members
    }
}