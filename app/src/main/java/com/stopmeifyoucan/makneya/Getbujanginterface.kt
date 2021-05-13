package com.stopmeifyoucan.makneya

import com.google.gson.annotations.SerializedName
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Getbujanginterface {
    @POST("makneyaserver/user/bujang")
    fun postbujangplus(@Body requestBody: RequestBody): Call<GetbujangResponse>
}