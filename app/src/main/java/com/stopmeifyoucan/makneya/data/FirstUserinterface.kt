package com.stopmeifyoucan.makneya.data

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface FirstUserinterface {
    @POST("makneyaserver/user/adduser")
    fun firstbujangadd(@Body requestBody: RequestBody): Call<FirstUserResponse>
}