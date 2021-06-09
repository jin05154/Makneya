package com.stopmeifyoucan.makneya

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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

        val builder = AlertDialog.Builder(this)
        builder.setMessage("ㅇㅇㅇㅇ 식당으로 결재 올리시겠어요?")
        builder.setPositiveButton("네!") { dialogInterface: DialogInterface, i: Int ->
            // 최종 결정 완료 , 최종 보고서 보여주기?? "맛있게 먹고 나중에 리뷰 남겨주세요~"
        }
        builder.setNegativeButton("아뇨") { dialogInterface: DialogInterface, i: Int ->
            //
        }

        place1ViewMore.setOnClickListener{
            Toast.makeText(
                applicationContext,
                "식당1 정보 보여주기",
                Toast.LENGTH_SHORT).show()
        }
        place1SelectBtn.setOnClickListener{
            builder.show()
        }


    }
}