package com.stopmeifyoucan.makneya

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons
import kotlinx.android.synthetic.main.activity_menuapproval.*


class MenuApproval : AppCompatActivity(), OnMapReadyCallback {

    var foodnameList = mutableListOf<String>()
    var foodphoneList = mutableListOf<String>()
    var foodaddressList = mutableListOf<String>()
    var foodlinkList = mutableListOf<String>()
    var foodxList = mutableListOf<String>()
    var foodyList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menuapproval)

        getinfo()

        var fm = supportFragmentManager
        val options = NaverMapOptions()
            .camera(CameraPosition(LatLng(37.594497, 127.129796), 13.0))

        val mapFragment = MapFragment.newInstance(options)
        fm.beginTransaction().add(R.id.map, mapFragment).commit()

        mapFragment.getMapAsync(this)

        place1Name.text = foodnameList[0]
        place1Number.text = foodaddressList[0]
        place1Address.text = foodphoneList[0]
        place2Name.text = foodnameList[1]
        place2Number.text = foodaddressList[1]
        place2Address.text = foodphoneList[1]
        place3Name.text = foodnameList[2]
        place3Number.text = foodaddressList[2]
        place3Address.text = foodphoneList[2]
        place4Name.text = foodnameList[3]
        place4Number.text = foodaddressList[3]
        place4Address.text = foodphoneList[3]
        place5Name.text = foodnameList[4]
        place5Number.text = foodaddressList[4]
        place5Address.text = foodphoneList[4]

        val builder = AlertDialog.Builder(this)
        builder.setMessage("이 식당으로 결재 올리시겠어요?")
        builder.setPositiveButton("네!") { dialogInterface: DialogInterface, i: Int ->
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        builder.setNegativeButton("아니요") { dialogInterface: DialogInterface, i: Int ->
            //
        }

        place1ViewMore.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(foodlinkList[0]))
            startActivity(intent)
        }
        place1SelectBtn.setOnClickListener{
            builder.show()
        }
        place2ViewMore.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(foodlinkList[1]))
            startActivity(intent)
        }
        place2SelectBtn.setOnClickListener{
            builder.show()
        }
        place3ViewMore.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(foodlinkList[2]))
            startActivity(intent)
        }
        place3SelectBtn.setOnClickListener{
            builder.show()
        }
        place4ViewMore.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(foodlinkList[3]))
            startActivity(intent)
        }
        place4SelectBtn.setOnClickListener{
            builder.show()
        }
        place5ViewMore.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(foodlinkList[4]))
            startActivity(intent)
        }
        place5SelectBtn.setOnClickListener{
            builder.show()
        }
    }


    private fun getinfo(){
        for(i in 1..5!!){
            foodnameList.add(intent.getStringExtra("foodname" + i).toString())
        }
        for(i in 1..5!!){
            foodphoneList.add(intent.getStringExtra("foodphone" + i).toString())
        }
        for(i in 1..5!!){
            foodaddressList.add(intent.getStringExtra("foodaddress" + i).toString())
        }
        for(i in 1..5!!){
            foodlinkList.add(intent.getStringExtra("foodlink" + i).toString())
        }
        for(i in 1..5!!){
            foodxList.add(intent.getStringExtra("foodX" + i).toString())
        }
        for(i in 1..5!!){
            foodyList.add(intent.getStringExtra("foodY" + i).toString())
        }
        Log.d("식당이름", foodnameList.toString())
    }

    override fun onMapReady(p0: NaverMap) {
        val mainmarker = Marker()
        val firstmarker = Marker()
        val secondmarker = Marker()
        val thirdmarker = Marker()
        val forthmarker = Marker()
        val fifthmarker = Marker()
        mainmarker.position = LatLng(37.594497, 127.129796)
        mainmarker.map = p0
        mainmarker.icon = MarkerIcons.BLACK
        mainmarker.iconTintColor = Color.RED
        firstmarker.position = LatLng(foodyList[0].toDouble(), foodxList[0].toDouble())
        firstmarker.captionText = foodnameList[0]
        firstmarker.map = p0
        secondmarker.position = LatLng(foodyList[1].toDouble(), foodxList[1].toDouble())
        secondmarker.captionText = foodnameList[1]
        secondmarker.map = p0
        thirdmarker.position = LatLng(foodyList[2].toDouble(), foodxList[2].toDouble())
        thirdmarker.captionText = foodnameList[2]
        thirdmarker.map = p0
        forthmarker.position = LatLng(foodyList[3].toDouble(), foodxList[3].toDouble())
        forthmarker.captionText = foodnameList[3]
        forthmarker.map = p0
        fifthmarker.position = LatLng(foodyList[4].toDouble(), foodxList[4].toDouble())
        fifthmarker.captionText = foodnameList[4]
        fifthmarker.map = p0
    }

}