package com.stopmeifyoucan.makneya

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons
import kotlinx.android.synthetic.main.activity_place_suggestion.*

class PlaceSuggestion : AppCompatActivity(), OnMapReadyCallback {

    private val TAG: String = "PlaceSuggestion"

    var foodNameList = mutableListOf<String>()
    var foodPhoneList = mutableListOf<String>()
    var foodAddressList = mutableListOf<String>()
    var foodLinkList = mutableListOf<String>()
    var foodXList = mutableListOf<String>()
    var foodYList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_suggestion)

        menuSelected.text = intent.getStringExtra("food_type")

        getInfo()

        var fm = supportFragmentManager
        val options = NaverMapOptions()
            .camera(CameraPosition(LatLng(37.594497, 127.129796), 13.0))

        val mapFragment = MapFragment.newInstance(options)
        fm.beginTransaction().add(R.id.map, mapFragment).commit()

        mapFragment.getMapAsync(this)

        place1Name.text = foodNameList[0]
        place1Number.text = foodPhoneList[0]
        place1Address.text = foodAddressList[0]
        place2Name.text = foodNameList[1]
        place2Number.text = foodPhoneList[1]
        place2Address.text = foodAddressList[1]
        place3Name.text = foodNameList[2]
        place3Number.text = foodPhoneList[2]
        place3Address.text = foodAddressList[2]
        place4Name.text = foodNameList[3]
        place4Number.text = foodPhoneList[3]
        place4Address.text = foodAddressList[3]
        place5Name.text = foodNameList[4]
        place5Number.text = foodPhoneList[4]
        place5Address.text = foodAddressList[4]

        val builder = AlertDialog.Builder(this)
        val finalIntent = Intent(this, FinalApproval::class.java)
        finalIntent.putExtra("food_name", intent.getStringExtra("food_type"))

        place1ViewMore.setOnClickListener{
            var viewMoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse(foodLinkList[0]))
            startActivity(viewMoreIntent)
        }
        place1SelectBtn.setOnClickListener{
            builder.setMessage("\""+foodNameList[0]+"\" 식당으로 결재 올리시겠어요?")
            builder.setPositiveButton("네!") { dialogInterface: DialogInterface, i: Int ->
                finalIntent.putExtra("place_name", foodNameList[0])
                finalIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(finalIntent)
            }
            builder.setNegativeButton("아니요") { dialogInterface: DialogInterface, i: Int ->
                //
            }
            builder.show()
        }
        place2ViewMore.setOnClickListener{
            var viewMoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse(foodLinkList[1]))
            startActivity(viewMoreIntent)
        }
        place2SelectBtn.setOnClickListener{
            builder.setMessage("\""+foodNameList[1]+"\" 식당으로 결재 올리시겠어요?")
            builder.setPositiveButton("네!") { dialogInterface: DialogInterface, i: Int ->
                finalIntent.putExtra("place_name", foodNameList[1])
                finalIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(finalIntent)
            }
            builder.setNegativeButton("아니요") { dialogInterface: DialogInterface, i: Int ->
                //
            }
            builder.show()
        }
        place3ViewMore.setOnClickListener{
            var viewMoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse(foodLinkList[2]))
            startActivity(viewMoreIntent)
        }
        place3SelectBtn.setOnClickListener{
            builder.setMessage("\""+foodNameList[2]+"\" 식당으로 결재 올리시겠어요?")
            builder.setPositiveButton("네!") { dialogInterface: DialogInterface, i: Int ->
                finalIntent.putExtra("place_name", foodNameList[2])
                finalIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(finalIntent)
            }
            builder.setNegativeButton("아니요") { dialogInterface: DialogInterface, i: Int ->
                //
            }
            builder.show()
        }
        place4ViewMore.setOnClickListener{
            var viewMoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse(foodLinkList[3]))
            startActivity(viewMoreIntent)
        }
        place4SelectBtn.setOnClickListener{
            builder.setMessage("\""+foodNameList[3]+"\" 식당으로 결재 올리시겠어요?")
            builder.setPositiveButton("네!") { dialogInterface: DialogInterface, i: Int ->
                finalIntent.putExtra("place_name", foodNameList[3])
                finalIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(finalIntent)
            }
            builder.setNegativeButton("아니요") { dialogInterface: DialogInterface, i: Int ->
                //
            }
            builder.show()
        }
        place5ViewMore.setOnClickListener{
            var viewMoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse(foodLinkList[4]))
            startActivity(viewMoreIntent)
        }
        place5SelectBtn.setOnClickListener{
            builder.setMessage("\""+foodNameList[4]+"\" 식당으로 결재 올리시겠어요?")
            builder.setPositiveButton("네!") { dialogInterface: DialogInterface, i: Int ->
                finalIntent.putExtra("place_name", foodNameList[4])
                finalIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(finalIntent)
            }
            builder.setNegativeButton("아니요") { dialogInterface: DialogInterface, i: Int ->
                //
            }
            builder.show()
        }
    }

    private fun getInfo(){
        for(i in 1..5!!){
            foodNameList.add(intent.getStringExtra("foodname" + i).toString())
        }
        for(i in 1..5!!){
            foodPhoneList.add(intent.getStringExtra("foodphone" + i).toString())
        }
        for(i in 1..5!!){
            foodAddressList.add(intent.getStringExtra("foodaddress" + i).toString())
        }
        for(i in 1..5!!){
            foodLinkList.add(intent.getStringExtra("foodlink" + i).toString())
        }
        for(i in 1..5!!){
            foodXList.add(intent.getStringExtra("foodX" + i).toString())
        }
        for(i in 1..5!!){
            foodYList.add(intent.getStringExtra("foodY" + i).toString())
        }
        Log.d("식당이름", foodNameList.toString())
    }

    override fun onMapReady(p0: NaverMap) {
        val mainMarker = Marker()
        val firstMarker = Marker()
        val secondMarker = Marker()
        val thirdMarker = Marker()
        val fourthMarker = Marker()
        val fifthMarker = Marker()
        mainMarker.position = LatLng(37.594497, 127.129796)
        mainMarker.map = p0
        mainMarker.icon = MarkerIcons.BLACK
        mainMarker.iconTintColor = Color.RED
        firstMarker.position = LatLng(foodYList[0].toDouble(), foodXList[0].toDouble())
        firstMarker.captionText = foodNameList[0]
        firstMarker.map = p0
        secondMarker.position = LatLng(foodYList[1].toDouble(), foodXList[1].toDouble())
        secondMarker.captionText = foodNameList[1]
        secondMarker.map = p0
        thirdMarker.position = LatLng(foodYList[2].toDouble(), foodXList[2].toDouble())
        thirdMarker.captionText = foodNameList[2]
        thirdMarker.map = p0
        fourthMarker.position = LatLng(foodYList[3].toDouble(), foodXList[3].toDouble())
        fourthMarker.captionText = foodNameList[3]
        fourthMarker.map = p0
        fifthMarker.position = LatLng(foodYList[4].toDouble(), foodXList[4].toDouble())
        fifthMarker.captionText = foodNameList[4]
        fifthMarker.map = p0
    }

}