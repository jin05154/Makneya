package com.stopmeifyoucan.makneya.Data

import com.google.gson.annotations.SerializedName

class FirstUserResponse {
    @SerializedName("name")
    var name: String? = null
    @SerializedName("b_date")
    var b_date: String? = null
    @SerializedName("bujang_count")
    var bujang_count: String? = null
    @SerializedName("new_user")
    var new_user: String? = null
    @SerializedName("bujangData")
    var bujangData = ArrayList<Bujangdataagain>()
}

class Bujangdataagain{
    @SerializedName("bujang_name")
    var bujang_name: String? = null
    @SerializedName("bujang_code")
    var bujang_code: String? = null
}