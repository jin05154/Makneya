package com.stopmeifyoucan.makneya

import android.annotation.SuppressLint
import android.content.Context
import android.location.*
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.stopmeifyoucan.makneya.Data.InDB
import com.stopmeifyoucan.makneya.Navigation.TabCommunity
import com.stopmeifyoucan.makneya.Navigation.TabHome
import com.stopmeifyoucan.makneya.Navigation.TabMyInfo
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val locationManager by lazy {
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                location?.let {
                    val position = LatLng(it.latitude, it.longitude)
                    Log.e("lat and long", "${position.latitude} and ${position.longitude}")
                    getAddress(position)
                }
            }
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            10000,
            1f,
            locationListener
        )

        getUserProfile()

        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.selectedItemId = R.id.navigation_home

        supportFragmentManager.beginTransaction().add(R.id.mapLayout,
            TabHome()
        ).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.navigation_youtube -> {
                supportFragmentManager.beginTransaction().replace(R.id.mapLayout ,
                    TabCommunity()
                ).commitAllowingStateLoss()
                return true
            }
            R.id.navigation_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.mapLayout,
                    TabHome()
                ).commitAllowingStateLoss()
                return true
            }
            R.id.navigation_myinfo -> {
                supportFragmentManager.beginTransaction().replace(R.id.mapLayout,
                    TabMyInfo()
                ).commitAllowingStateLoss()
                //Log.d("뭐지?", InDB.prefs.getString("myaddress", ""))
                return true
            }
        }

        return false
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

            Log.d("Google user name", name)
            //var userName = findViewById<TextView>(R.id.userID)
            //userName.setText(name)
        }
    }
    private fun getAddress(position: LatLng) {
        val geoCoder = Geocoder(this@MainActivity, Locale.KOREAN)
        val address =
            geoCoder.getFromLocation(position.latitude, position.longitude, 1).first()
                .getAddressLine(0)
        InDB.prefs.setString("myaddress", address)
        Log.d("Address", address)
    }
}
