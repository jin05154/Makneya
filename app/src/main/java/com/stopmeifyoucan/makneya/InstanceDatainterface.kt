package com.stopmeifyoucan.makneya

import com.google.gson.annotations.SerializedName
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface InstanceDatainterface {
    @POST("makneyaserver/searching")
    fun postinstanceData(@Body requestBody: RequestBody): Call<InstanceDataResponse>
}