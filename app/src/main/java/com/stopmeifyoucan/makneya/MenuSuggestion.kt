package com.stopmeifyoucan.makneya

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.stopmeifyoucan.makneya.Data.InDB
import com.stopmeifyoucan.makneya.Data.KakaoAPI
import com.stopmeifyoucan.makneya.Data.ResultSearchKeyword
import kotlinx.android.synthetic.main.activity_menu_suggestion.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MenuSuggestion : AppCompatActivity() {

    private lateinit var progressDialog: AppCompatDialog

    override fun onCreate(savedInstanceState: Bundle?) {

        progressON()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_suggestion)

        menu1.text = InDB.prefs.getString("menu1", "")
        menu2.text = InDB.prefs.getString("menu2", "")
        menu3.text = InDB.prefs.getString("menu3", "")
        menu4.text = InDB.prefs.getString("menu4", "")
        menu5.text = InDB.prefs.getString("menu5", "")

        progressOFF()

        menu1.setOnClickListener {
            intent.putExtra("food_name", menu1.text.toString())
            searchKeyword("구리 교문동" + menu1.text)
        }
        menu2.setOnClickListener {
            searchKeyword("구리 교문동" + menu2.text)
        }
        menu3.setOnClickListener {
            searchKeyword("구리 교문동" + menu3.text)
        }
        menu4.setOnClickListener {
            searchKeyword("구리 교문동" + menu4.text)
        }
        menu5.setOnClickListener {
            searchKeyword("구리 교문동" + menu5.text)
        }

    }

    companion object {
        const val BASE_URL = "https://dapi.kakao.com/"
        const val API_KEY = "KakaoAK a16aaf30cdca387012e19f35ffac4705"  // REST API 키
    }

    private fun searchKeyword(keyword: String) {
        val intent = Intent(this, PlaceSuggestion::class.java)
        val retrofit = Retrofit.Builder()   // Retrofit 구성
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(KakaoAPI::class.java)   // 통신 인터페이스를 객체로 생성
        val call = api.getSearchKeyword(API_KEY, keyword)   // 검색 조건 입력

        // API 서버에 요청
        call.enqueue(object: Callback<ResultSearchKeyword> {
            override fun onResponse(
                call: Call<ResultSearchKeyword>,
                response: Response<ResultSearchKeyword>
            ) {
                // 통신 성공 (검색 결과는 response.body()에 담겨있음)
                val kakaoResponse = response.body()!!
                if (kakaoResponse.meta.total_count < 5){
                    Toast.makeText(this@MenuSuggestion, "주변에 식당이 없습니다.", Toast.LENGTH_SHORT).show()
                }
                else {
                    for(i in 1..5!!){
                        intent.putExtra("foodname"+i , kakaoResponse.documents.get(i-1).place_name)
                        intent.putExtra("foodphone"+i , kakaoResponse.documents.get(i-1).phone)
                        intent.putExtra("foodaddress"+i , kakaoResponse.documents.get(i-1).address_name)
                        intent.putExtra("foodlink"+i , kakaoResponse.documents.get(i-1).place_url)
                        intent.putExtra("foodY"+i, kakaoResponse.documents.get(i-1).y)
                        intent.putExtra("foodX"+i, kakaoResponse.documents.get(i-1).x)
                    }
                    Log.d("Test", "선택한 음식: ${kakaoResponse.meta.same_name.keyword}")
                    Log.d("Test", "선택한 식당: ${kakaoResponse.documents.get(0).place_name}")
                    Log.d("Test", "Body: ${response.body()}")
                    intent.putExtra("food_type", kakaoResponse.meta.same_name.keyword)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<ResultSearchKeyword>, t: Throwable) {
                // 통신 실패
                Log.w("MainActivity", "통신 실패: ${t.message}")
            }
        })
    }

    fun progressON() {
        progressDialog = AppCompatDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setContentView(R.layout.loading_dialog_custom)
        progressDialog.show()
        var imgLoadingFrame = progressDialog.findViewById<ImageView>(R.id.iv_frame_loading)
        var frameAnimation = imgLoadingFrame?.background as AnimationDrawable
        imgLoadingFrame?.post { frameAnimation.start() }
    }
    fun progressOFF() {
        if(progressDialog != null && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }
}
