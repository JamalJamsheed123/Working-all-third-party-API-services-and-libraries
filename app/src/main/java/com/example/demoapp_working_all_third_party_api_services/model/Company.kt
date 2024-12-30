package com.example.demoapp_working_all_third_party_api_services.model

import com.google.gson.annotations.SerializedName

data class Company(
    @SerializedName("avatar_url")
    val avatarUrl: String ,

    @SerializedName("public_members_url")
    val publicMemberUrl: String
)
