package com.stopmeifyoucan.makneya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class AddBujangName : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addbujangname)

        val btnBujangName = findViewById<Button>(R.id.btn_add_bujangdrink)
        val bujang_nickname = findViewById<TextView>(R.id.bujang_nickname)

        btnBujangName.setOnClickListener {
            InDB.prefs.setString("bujang_name", bujang_nickname.text.toString())
            //Log.d("bujang name is", InDB.prefs.getString("bujang_name", ""))
            val intent = Intent(this, AddBujangGgondae::class.java)
            startActivity(intent)
        }
    }
}