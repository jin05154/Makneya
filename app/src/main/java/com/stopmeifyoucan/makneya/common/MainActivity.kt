package com.stopmeifyoucan.makneya.common

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.room.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.stopmeifyoucan.makneya.R
import com.stopmeifyoucan.makneya.data.InDB
import com.stopmeifyoucan.makneya.navigation.TabRecipe
import com.stopmeifyoucan.makneya.navigation.TabHome
import com.stopmeifyoucan.makneya.navigation.TabMyInfo
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

@Entity
data class NationalWeatherTable(
    @PrimaryKey val code: String,
    val name1: String,
    val name2: String,
    val name3: String,
    val x: Int,
    val y: Int
)

@Dao
interface NationalWeatherInterface {
    @Query("SELECT * FROM NationalWeatherTable")
    suspend fun getAll(): List<NationalWeatherTable>

    @Insert
    suspend fun insert(nationalWeatherTable: NationalWeatherTable)

    @Query("DELETE FROM NationalWeatherTable")
    suspend fun deleteAll()
}

@Database(entities = [NationalWeatherTable::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun nationalWeatherInterface(): NationalWeatherInterface
}

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()

        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.selectedItemId = R.id.navigation_home

        supportFragmentManager.beginTransaction().add(R.id.mapLayout, TabHome()).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            // 간편요리는 fragment가 아닌 activity로 처리
            R.id.navigation_recipe -> {
                val intent = Intent(this, TabRecipe::class.java)
                startActivity(intent)
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
                return true
            }
        }

        return false
    }

    private fun getUserProfile() {
        val user = Firebase.auth.currentUser
        user?.let {
            val name = user.displayName

            Log.d("Google user name", name)
        }
    }

    private fun getAddress(position: LatLng) {
        val geoCoder = Geocoder(this@MainActivity, Locale.KOREA)
        val addressInfo = geoCoder.getFromLocation(position.latitude, position.longitude, 1)
        val fullAddress = addressInfo[0].getAddressLine(0).toString()
        val city = addressInfo[0].adminArea
        var district = addressInfo[0].locality
        if (district == null) district = addressInfo[0].subLocality
        if (district == null) district = addressInfo[0].subAdminArea
        // var dong = addressInfo[0].thoroughfare
        val shortAddress = "$city $district"
        InDB.prefs.setString("myaddress", shortAddress)
        Log.e("Address", fullAddress)
        Log.e("Address", shortAddress)
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        val position = LatLng(location.latitude, location.longitude)
                        Log.e("last location", "${location.latitude} and ${location.longitude}")
                        getAddress(position)
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest.create().apply {
            interval = 0
            fastestInterval = 0
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            numUpdates = 1
        }
        var mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult?.let {
                    for ((i, location) in it.locations.withIndex()) {
                        Log.e("lat and long","#$i ${location.latitude} , ${location.longitude}")
                    }
                }
            }
        }
        Looper.myLooper()?.let {
            mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                it
            )
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }
}
