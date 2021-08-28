package com.stopmeifyoucan.makneya.data

import com.stopmeifyoucan.makneya.GetBujangResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface GetBujangInterface {
    @POST("makneyaserver/user/bujang")
    fun postbujangplus(@Body requestBody: RequestBody): Call<GetBujangResponse>
}