package com.example.demoapp_working_all_third_party_api_services.presenter

import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import com.example.demoapp_working_all_third_party_api_services.model.Model
import com.example.demoapp_working_all_third_party_api_services.view.MainActivity
import com.example.demoapp_working_all_third_party_api_services.view.MainView
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.concurrent.thread

class Presenter {
    val model = Model()
    private var mainView: MainView? = null
    fun getMembers(){
        val disposable = model.getListPicturesRetrofitRequest("https://api.github.com/")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {members ->
                Log.d("GettingListPicUsingRetrofitMVP", "avatarUrl: $members thread ${Thread.currentThread().name}")
                mainView?.showMembers(members)
            }
    }

    fun getHttpRequest(){
        thread{model.httpRequest("https://api.github.com/orgs/google")}
    }

    fun sendActivity(mainView: MainView){
        this.mainView = mainView
    }
}