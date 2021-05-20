package com.stopmeifyoucan.makneya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Forfirstuser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forfirstuser)

        val btnfirstuser = findViewById<Button>(R.id.btn_firstuser)
        val Mynickname = findViewById<TextView>(R.id.mynickname)
        val Mybirth = findViewById<TextView>(R.id.mybirth)

        btnfirstuser.setOnClickListener {
            InDB.prefs.setString("name", Mynickname.text.toString())
            InDB.prefs.setString("b_date", Mybirth.text.toString())
            //Log.d("bujang name is", InDB.prefs.getString("bujang_name", ""))
            val intent = Intent(this, AddBujang::class.java)
            startActivity(intent)
        }
    }
}