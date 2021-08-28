package com.stopmeifyoucan.makneya

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface Userinterface {
    @GET("makneyaserver/user/userinfo/{idToken}")
    fun getCurrentUserData(@Path("idToken") idToken: String?): Call<UserResponse>
}