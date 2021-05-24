package com.stopmeifyoucan.makneya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.stopmeifyoucan.makneya.Data.InDB

class MenuSuggest : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menusuggestion)

        val menu1 = findViewById<TextView>(R.id.menu1)
        val menu2 = findViewById<TextView>(R.id.menu2)
        val menu3 = findViewById<TextView>(R.id.menu3)
        val menu4 = findViewById<TextView>(R.id.menu4)
        val menu5 = findViewById<TextView>(R.id.menu5)

        menu1.text = InDB.prefs.getString("menu1", "")
        menu2.text = InDB.prefs.getString("menu2", "")
        menu3.text = InDB.prefs.getString("menu3", "")
        menu4.text = InDB.prefs.getString("menu4", "")
        menu5.text = InDB.prefs.getString("menu5", "")

    }
}
