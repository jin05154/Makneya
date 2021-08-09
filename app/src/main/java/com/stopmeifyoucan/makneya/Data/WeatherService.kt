package com.stopmeifyoucan.makneya.Data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("getVilageFcst?serviceKey=fJHQarZZHDA44sSg6onUtCVNxg1%2BVn2Hhmng%2BnA8oHawn9bURtewIVsgBrFTGT%2FFZM7oWOzIzOZF0V664cYOWg%3D%3D")
    fun getCurrentWeatherData(
        @Query("dataType") data_type : String,
        @Query("numOfRows") num_of_rows : Int,
        @Query("pageNo") page_no : Int,
        @Query("base_date") base_date : Int,
        @Query("base_time") base_time : Int,
        @Query("nx") nx : String,
        @Query("ny") ny : String
    ): Call<WEATHER>
}