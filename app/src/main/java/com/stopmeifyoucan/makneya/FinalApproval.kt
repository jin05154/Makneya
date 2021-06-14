package com.stopmeifyoucan.makneya

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_final_approval.*
import java.text.SimpleDateFormat
import java.util.*

class FinalApproval : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_approval)

        //finalName.text = InDB.prefs.getString("name", "")
        //consultTextName2.text = InDB.prefs.getString("name", "")
        getUserProfile()

        val currentDateTime = Calendar.getInstance().time
        var dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).format(currentDateTime)
        finalDate.text = dateFormat

        if (intent.hasExtra("place_name") && intent.hasExtra("food_name")) {
            consultTextPlace2.text = intent.getStringExtra("place_name")
            consultTextPlace4.text = intent.getStringExtra("food_name")
        }

        finalApproveBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    private fun getUserProfile() {
        val user = Firebase.auth.currentUser
        user?.let {
            val name = user.displayName

            finalName.text = name
            consultTextName2.text = name
        }
    }
}