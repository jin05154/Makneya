package com.stopmeifyoucan.makneya

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMapOptions
import kotlinx.android.synthetic.main.activity_menuapproval.*

class MenuApproval : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menuapproval)

        var fm = supportFragmentManager

        val options = NaverMapOptions()
            .camera(CameraPosition(LatLng(37.594497, 127.129796), 15.0))

        val mapFragment = MapFragment.newInstance(options)
        fm.beginTransaction().add(R.id.map, mapFragment).commit()

        for(i: Int in 1..5) {
            // Initialize a new LayoutParams instance, CardView width and height
            val layoutParams = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, // CardView width
                ViewGroup.LayoutParams.WRAP_CONTENT // CardView height
            )

            layoutParams.setMargins(16,16,16,50)

            // Initialize a new CardView instance
            val menuCardView = CardView(this)

            // Set bottom margin for card view
            //layoutParams.bottomMargin = 50

            // Set the card view layout params
            menuCardView.layoutParams = layoutParams

            // Set the card view corner radius
            menuCardView.radius = 12F

            // Set the card view content padding
            menuCardView.setContentPadding(25,25,25,25)

            // Set the card view background color
            menuCardView.setCardBackgroundColor(Color.LTGRAY)

            // Set card view elevation
            menuCardView.cardElevation = 8F

            // Set card view maximum elevation
            menuCardView.maxCardElevation = 12F

            // Set a click listener for card view
            menuCardView.setOnClickListener{
                Toast.makeText(
                    applicationContext,
                    "식당 상세보기",
                    Toast.LENGTH_SHORT).show()
            }

            // Add LinearLayout to the CardView
            menuCardView.addView(generateCardView())

            // Finally, add the CardView in root layout
            root_layout.addView(menuCardView)
        }
    }

    // Custom method to generate an image view
    private fun generateCardView(): LinearLayout {

        val cardLinearLayout = LinearLayout(this)
        cardLinearLayout.orientation = LinearLayout.VERTICAL

        val restaurantName = TextView(this)
        restaurantName.text = "파스쿠찌 잠실역점"
        restaurantName.textSize = 24f
        restaurantName.setTextColor(Color.WHITE)

        val restaurantNumber = TextView(this)
        restaurantNumber.text = "02-000-0000"
        restaurantNumber.textSize = 17f
        restaurantNumber.setTextColor(Color.WHITE)

        val restaurantAddress = TextView(this)
        restaurantAddress.text = "잠실역 7번출구 300m"
        restaurantAddress.textSize = 17f
        restaurantAddress.setTextColor(Color.WHITE)

        cardLinearLayout.addView(restaurantName)
        cardLinearLayout.addView(restaurantNumber)
        cardLinearLayout.addView(restaurantAddress)

        return cardLinearLayout
    }
}