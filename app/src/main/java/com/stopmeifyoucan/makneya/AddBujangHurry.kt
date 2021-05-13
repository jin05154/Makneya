package com.stopmeifyoucan.makneya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar

class AddBujangHurry : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addbujanghurry)

        val seekbar : SeekBar = findViewById(R.id.drinkbar)
        val btnBujanghurry = findViewById<Button>(R.id.btn_add_bujangdrink)

        btnBujanghurry.setOnClickListener {
            InDB.prefs.setString("hurry", (seekbar.progress+1).toString())
            //Log.d("bujang hurry", InDB.prefs.getString("hurry", ""))
            val intent = Intent(this, AddBujangSpicy::class.java)
            startActivity(intent)
        }

    }
}