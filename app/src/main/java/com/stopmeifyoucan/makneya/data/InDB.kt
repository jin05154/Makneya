package com.stopmeifyoucan.makneya.data

import android.app.Application
import com.stopmeifyoucan.makneya.common.PreferenceUtil

class InDB: Application() {
    companion object{
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        prefs =
            PreferenceUtil(applicationContext)
        super.onCreate()
    }
}