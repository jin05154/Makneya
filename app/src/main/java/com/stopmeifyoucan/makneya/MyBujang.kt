package com.stopmeifyoucan.makneya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.stopmeifyoucan.makneya.Data.InDB

class MyBujang : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_bujang)
        val bujang1 = findViewById<TextView>(R.id.mybujang1)
        val bujang2 = findViewById<TextView>(R.id.mybujang2)
        val bujang3 = findViewById<TextView>(R.id.mybujang3)
        val bujang4 = findViewById<TextView>(R.id.mybujang4)
        val bujang5 = findViewById<TextView>(R.id.mybujang5)

        val bujangcount = InDB.prefs.getString("bujangcount", "").toInt()

        for(i in 1..bujangcount!!){
            if( i == 1 ){
                bujang1.text = InDB.prefs.getString("bujangname1", "")
            }
            if( i == 2 ){
                bujang2.text = InDB.prefs.getString("bujangname2", "")
            }
            if( i == 3 ){
                bujang3.text = InDB.prefs.getString("bujangname3", "")
            }
            if( i == 4 ){
                bujang4.text = InDB.prefs.getString("bujangname4", "")
            }
            if( i == 5 ) {
                bujang5.text = InDB.prefs.getString("bujangname5", "")
            }
        }
        bujang1.setOnClickListener {
            InDB.prefs.setString("currentstate", "2")
            InDB.prefs.setString("currentcode", InDB.prefs.getString("bujangcode1", ""))
            val Intent= Intent(this, AddBujang::class.java)
            startActivity(Intent)
        }
        if (bujangcount > 1){
            bujang2.setOnClickListener {
                InDB.prefs.setString("currentstate", "2")
                InDB.prefs.setString("currentcode", InDB.prefs.getString("bujangcode2", ""))
                val Intent= Intent(this, AddBujang::class.java)
                startActivity(Intent)
            }
            if (bujangcount > 2){
                bujang3.setOnClickListener {
                    InDB.prefs.setString("currentstate", "2")
                    InDB.prefs.setString("currentcode", InDB.prefs.getString("bujangcode3", ""))
                    val Intent= Intent(this, AddBujang::class.java)
                    startActivity(Intent)
                }
                if(bujangcount > 3){
                    bujang4.setOnClickListener {
                        InDB.prefs.setString("currentstate", "2")
                        InDB.prefs.setString("currentcode", InDB.prefs.getString("bujangcode4", ""))
                        val Intent= Intent(this, AddBujang::class.java)
                        startActivity(Intent)
                    }
                    if(bujangcount > 4){
                        bujang5.setOnClickListener {
                            InDB.prefs.setString("currentstate", "2")
                            InDB.prefs.setString("currentcode", InDB.prefs.getString("bujangcode5", ""))
                            val Intent= Intent(this, AddBujang::class.java)
                            startActivity(Intent)
                        }
                    }
                }
            }
        }
    }
}