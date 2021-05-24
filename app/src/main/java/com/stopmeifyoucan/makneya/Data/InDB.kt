package com.stopmeifyoucan.makneya.Data

import android.app.Application
import com.stopmeifyoucan.makneya.PreferenceUtil

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