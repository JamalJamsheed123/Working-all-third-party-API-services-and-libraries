package com.example.demoapp_working_all_third_party_api_services.Services

import com.example.demoapp_working_all_third_party_api_services.GSONModel.Company
import com.example.demoapp_working_all_third_party_api_services.GSONModel.Member
import io.reactivex.Single
import retrofit2.http.GET

interface RetrofitServices {

    /*@get:GET("/orgs/google")
    val company: Single<Company>*/

    @get:GET("/orgs/google/members")
    val members: Single<List<Member>>
}