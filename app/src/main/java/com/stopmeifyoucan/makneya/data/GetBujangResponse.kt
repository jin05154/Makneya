package com.stopmeifyoucan.makneya

import com.google.gson.annotations.SerializedName

class GetBujangResponse {
    @SerializedName("name")
    var Name: String? = null
    @SerializedName("b_date")
    var Bdate: String? = null
    @SerializedName("bujang_count")
    var bujangcount: String? = null
    @SerializedName("new_user")
    var new_user: String? = null
    @SerializedName("bujangData")
    var bujangdata = ArrayList<Bujangdataagain>()
}

class Bujangdataagain{
    @SerializedName("bujang_name")
    var bujangname: String? = null
    @SerializedName("bujang_code")
    var bujangcode: String? = null
}