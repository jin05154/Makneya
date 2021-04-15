package com.stopmeifyoucan.makneya

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.stopmeifyoucan.makneya.CommunityFragment
import com.stopmeifyoucan.makneya.HomeFragment
import com.stopmeifyoucan.makneya.MyinfoFragment
import com.stopmeifyoucan.makneya.R

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnNavigationItemSelectedListener(this)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.navigation_home

        supportFragmentManager.beginTransaction().add(R.id.linearLayout, HomeFragment()).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.navigation_community -> {
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout , CommunityFragment()).commitAllowingStateLoss()
                return true
            }
            R.id.navigation_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout, HomeFragment()).commitAllowingStateLoss()
                return true
            }
            R.id.navigation_myinfo -> {
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout, MyinfoFragment()).commitAllowingStateLoss()
                return true
            }
        }

        return false
    }
}