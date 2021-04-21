package com.stopmeifyoucan.makneya

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.stopmeifyoucan.makneya.CommunityFragment
import com.stopmeifyoucan.makneya.HomeFragment
import com.stopmeifyoucan.makneya.MyinfoFragment
import com.stopmeifyoucan.makneya.R
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//interface WeatherInterface {
    //@GET("getUltraSrtFcst" +
            //"?serviceKey=fJHQarZZHDA44sSg6onUtCVNxg1%2BVn2Hhmng%2BnA8oHawn9bURtewIVsgBrFTGT%2FFZM7oWOzIzOZF0V664cYOWg%3D%3D&numOfRows=10&pageNo=1" +
            //"&base_date=20210418&base_time=0630&nx=55&ny=127")
    //fun GetWeather(): Call<WEATHER> // DATA CLASS
//}  // 이렇게 날짜,시간 고정되면 안 되니까 데이터 동적으로 받아야함. encoded 인증키


//object ApiObject {
    //val retrofitService: WeatherInterface by lazy {
        //retrofit.create(WeatherInterface::class.java)
    //}
//}

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
