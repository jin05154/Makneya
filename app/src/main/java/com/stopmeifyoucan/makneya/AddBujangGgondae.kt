package com.stopmeifyoucan.makneya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar

class AddBujangGgondae : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addbujangggondae)

        val seekbar : SeekBar = findViewById(R.id.drinkbar)
        val btnBujangggondae = findViewById<Button>(R.id.btn_add_bujangdrink)

        btnBujangggondae.setOnClickListener {
            InDB.prefs.setString("ggondae", (seekbar.progress+1).toString())
            Log.d("bujang ggondae", InDB.prefs.getString("ggondae", ""))
            val intent = Intent(this, AddBujangHurry::class.java)
            startActivity(intent)
        }

    }
}