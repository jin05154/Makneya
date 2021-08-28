package com.stopmeifyoucan.makneya.common

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.stopmeifyoucan.makneya.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({ //delay를 위한 handler
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, DURATION)
    }
    companion object {
        private const val DURATION : Long = 2000  //2초간 스플래시 화면을 보여줌 (ms)
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}