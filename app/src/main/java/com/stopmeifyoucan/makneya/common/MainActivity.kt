package com.stopmeifyoucan.makneya

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.*
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.room.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
                    //getAddress(position)
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

//        val NationalWeatherDB = Room.databaseBuilder(this, AppDatabase::class.java,"db").build()
//        val assetManager: AssetManager = resources.assets
//        val inputStream: InputStream = assetManager.open("NationalWeatherDB.txt")
//
//        inputStream.bufferedReader().readLines().forEach {
//            var token = it.split("\t")
//            var input = NationalWeatherTable(token[0], token[1], token[2], token[3], token[4].toInt(), token[5].toInt())
//            CoroutineScope(Dispatchers.Main).launch {
//                NationalWeatherDB.nationalWeatherInterface().insert(input)
//            }
//        }

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
                //Log.d("뭐지?", InDB.prefs.getString("myaddress", ""))
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
        val geoCoder = Geocoder(this@MainActivity, Locale.KOREAN)
        val address =
            geoCoder.getFromLocation(position.latitude, position.longitude, 1).first()
                .getAddressLine(0)
        InDB.prefs.setString("myaddress", address)
        Log.d("Address", address)
    }
}
