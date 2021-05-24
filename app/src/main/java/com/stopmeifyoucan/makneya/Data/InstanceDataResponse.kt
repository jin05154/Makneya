package com.stopmeifyoucan.makneya.Data

import com.google.gson.annotations.SerializedName

class InstanceDataResponse {
    @SerializedName("recomend_menu")
    var RecommendMenu = ArrayList<menu>()
}

class menu{
    @SerializedName("menu_name")
    var menuname : String? = null
    @SerializedName("total")
    var total : String? = null
}


