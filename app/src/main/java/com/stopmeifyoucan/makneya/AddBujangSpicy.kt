package com.stopmeifyoucan.makneya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar

class AddBujangSpicy: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addbujangspicy)

        val seekbar : SeekBar = findViewById(R.id.drinkbar)
        val btnBujangspicy = findViewById<Button>(R.id.btn_firstuser)

        btnBujangspicy.setOnClickListener {
            InDB.prefs.setString("spicy", (seekbar.progress+1).toString())
            //Log.d("bujang ggondae", InDB.prefs.getString("spicy", ""))
            val intent = Intent(this, AddBujangDrink::class.java)
            startActivity(intent)
        }

    }
}