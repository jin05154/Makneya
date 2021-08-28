package com.stopmeifyoucan.makneya.common

import android.app.Application
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDialog
import com.stopmeifyoucan.makneya.R

// 이렇게 해야 함수를 매 class마다 정의할 필요 없이 상속으로 함수 재사용 가능.
class BaseApplication : Application() {

    private lateinit var progressDialog: AppCompatDialog

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun progressON() {
        progressDialog = AppCompatDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setContentView(R.layout.loading_dialog_custom)
        progressDialog.show()
        val imgLoadingFrame = progressDialog.findViewById<ImageView>(R.id.iv_frame_loading)
        val frameAnimation = imgLoadingFrame?.background as AnimationDrawable
        imgLoadingFrame.post { frameAnimation.start() }
    }
    fun progressOFF() {
        if(progressDialog != null && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    companion object {
        var instance: BaseApplication? = null
    }
}