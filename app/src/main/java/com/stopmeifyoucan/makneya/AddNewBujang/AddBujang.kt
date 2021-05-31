package com.stopmeifyoucan.makneya.AddNewBujang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.stopmeifyoucan.makneya.*

class AddBujang : AppCompatActivity() {

    val model = AddBujangModel.getinstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addbujang)

        val btn_name = findViewById<Button>(R.id.btn_name)
        val btn_ggon = findViewById<Button>(R.id.btn_ggon)
        val btn_hurry = findViewById<Button>(R.id.btn_hurry)
        val btn_spicy = findViewById<Button>(R.id.btn_spicy)
        val btn_drink = findViewById<Button>(R.id.btn_drink)

        val save_bujang_name = "Kim"
        val save_ggondae = 3
        val save_hurry = 3
        val save_spicy = 3
        val save_drink = 3

        supportFragmentManager.beginTransaction().add(R.id.changeview, AddBujangName()).commit()

        btn_name.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.changeview, AddBujangName())
                .commit()

        }

        btn_ggon.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.changeview, AddBujangGgondae())
                .commit()
        }

        btn_hurry.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.changeview, AddBujangHurry())
                .commit()
        }

        btn_spicy.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.changeview, AddBujangSpicy())
                .commit()
        }

        btn_drink.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.changeview, AddBujangDrink())
                .commit()
        }
    }
}