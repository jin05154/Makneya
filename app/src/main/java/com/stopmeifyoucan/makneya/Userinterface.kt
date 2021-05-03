package com.stopmeifyoucan.makneya

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface Userinterface {
    @GET("dev/userinfo/users?")
    fun getCurrentUserData(@Query("idToken") idToken: String?): Call<UserResponse>
}