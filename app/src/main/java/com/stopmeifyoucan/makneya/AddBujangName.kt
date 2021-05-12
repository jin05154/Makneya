package com.stopmeifyoucan.makneya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class AddBujangName : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addbujangname)
        val btnBujangName = findViewById<Button>(R.id.btn_add_bujangname)
        val bujang_nickname = findViewById<TextView>(R.id.bujang_nickname)

        btnBujangName.setOnClickListener {
            bujang_name = bujang_nickname.toString()
            Log.d("bujang name is", bujang_name)
            val intent = Intent(this, AddBujangGgondae::class.java)
            startActivity(intent)
        }
    }
    companion object {
        var state = 1
        var bujang_name = "부장님"
        var bujang_code = "  "
    }
}