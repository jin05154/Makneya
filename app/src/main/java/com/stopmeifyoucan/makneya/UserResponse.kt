package com.stopmeifyoucan.makneya

import com.google.gson.annotations.SerializedName

class UserResponse {
    @SerializedName("body")
    var Body: UID? = null
}

class UID{
    @SerializedName("uid")
    var realuid: String? = null
}