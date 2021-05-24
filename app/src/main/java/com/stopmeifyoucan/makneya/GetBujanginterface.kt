package com.stopmeifyoucan.makneya

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface GetBujanginterface {
    @POST("makneyaserver/user/bujang")
    fun postbujangplus(@Body requestBody: RequestBody): Call<GetBujangResponse>
}