package com.stopmeifyoucan.makneya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stopmeifyoucan.makneya.Data.InDB
import kotlinx.android.synthetic.main.activity_forfirstuser.*

class ForFirstUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forfirstuser)

        btn_firstuser.setOnClickListener {
            InDB.prefs.setString("currentstate", "1")
            InDB.prefs.setString("name", myNickname.text.toString())
            InDB.prefs.setString("b_date", myBirth.text.toString())
            //Log.d("bujang name is", InDB.prefs.getString("bujang_name", ""))
            val intent = Intent(this, AddBujang::class.java)
            startActivity(intent)
        }
    }
}