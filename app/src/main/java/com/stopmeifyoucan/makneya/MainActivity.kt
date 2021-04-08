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

        supportFragmentManager.beginTransaction().add(R.id.linearLayout, CommunityFragment()).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.page_community -> {
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout , CommunityFragment()).commitAllowingStateLoss()
                return true
            }
            R.id.page_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout, HomeFragment()).commitAllowingStateLoss()
                return true
            }
            R.id.page_myinfo -> {
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout, MyinfoFragment()).commitAllowingStateLoss()
                return true
            }
        }

        return false
    }
}