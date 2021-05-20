package com.stopmeifyoucan.makneya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AddBujang : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addbujang)

        val btn_name = findViewById<Button>(R.id.btn_name)
        val btn_ggon = findViewById<Button>(R.id.btn_ggon)
        val btn_hurry = findViewById<Button>(R.id.btn_hurry)
        val btn_spicy = findViewById<Button>(R.id.btn_spicy)
        val btn_drink = findViewById<Button>(R.id.btn_drink)

        supportFragmentManager.beginTransaction().add(R.id.changeview, AddBujangname()).commit()

        btn_name.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.changeview, AddBujangname())
                .commit()
        }
        btn_ggon.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.changeview, AddBujangggondae())
                .commit()
        }

        btn_hurry.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.changeview, AddBujanghurry())
                .commit()
        }

        btn_spicy.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.changeview, AddBujangspicy())
                .commit()
        }
        btn_drink.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.changeview, AddBujangdrink())
                .commit()
        }


    }
}