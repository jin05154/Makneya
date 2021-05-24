package com.stopmeifyoucan.makneya

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.stopmeifyoucan.makneya.Navigation.TabCommunity
import com.stopmeifyoucan.makneya.Navigation.TabHome
import com.stopmeifyoucan.makneya.Navigation.TabMyInfo

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkCurrentUser()
        getUserProfile()

        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnNavigationItemSelectedListener(this)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.navigation_home

        supportFragmentManager.beginTransaction().add(R.id.linearLayout,
            TabHome()
        ).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.navigation_community -> {
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout ,
                    TabCommunity()
                ).commitAllowingStateLoss()
                return true
            }
            R.id.navigation_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout,
                    TabHome()
                ).commitAllowingStateLoss()
                return true
            }
            R.id.navigation_myinfo -> {
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout,
                    TabMyInfo()
                ).commitAllowingStateLoss()
                return true
            }
        }

        return false
    }

    private fun checkCurrentUser() {
        val user = Firebase.auth.currentUser
        if (user != null) {
            // User is signed in
        } else {
            // No user is signed in
        }
    }

    private fun getUserProfile() {
        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid

            Log.d("test log", name)
            //var userName = findViewById<TextView>(R.id.userID)
            //userName.setText(name)
        }
    }


}
