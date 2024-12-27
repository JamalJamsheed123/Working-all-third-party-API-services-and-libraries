package com.example.demoapp_working_all_third_party_api_services

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Glide
            .with(this)
            .load("https://avatars.githubusercontent.com/u/1342004?v=4")
            .into(findViewById(R.id.imageView))
    }
}