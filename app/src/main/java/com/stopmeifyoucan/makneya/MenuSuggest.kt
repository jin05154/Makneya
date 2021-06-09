package com.stopmeifyoucan.makneya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.stopmeifyoucan.makneya.Data.InDB
import com.stopmeifyoucan.makneya.Data.KakaoAPI
import com.stopmeifyoucan.makneya.Data.ResultSearchKeyword
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MenuSuggest : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menusuggestion)

        val menu1 = findViewById<TextView>(R.id.menu1)
        val menu2 = findViewById<TextView>(R.id.menu2)
        val menu3 = findViewById<TextView>(R.id.menu3)
        val menu4 = findViewById<TextView>(R.id.menu4)
        val menu5 = findViewById<TextView>(R.id.menu5)

        menu1.text = InDB.prefs.getString("menu1", "")
        menu2.text = InDB.prefs.getString("menu2", "")
        menu3.text = InDB.prefs.getString("menu3", "")
        menu4.text = InDB.prefs.getString("menu4", "")
        menu5.text = InDB.prefs.getString("menu5", "")

        menu1.setOnClickListener {
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
        val Intent= Intent(this, MenuApproval::class.java)
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
                val Kakaoresponse = response.body()!!
                if (Kakaoresponse.meta.total_count < 5){
                    Toast.makeText(this@MenuSuggest, "주변에 식당이 없습니다", Toast.LENGTH_SHORT).show()
                }
                else {
                    for(i in 1..5!!){
                        Intent.putExtra("foodname"+i , Kakaoresponse.documents.get(i-1).place_name)
                        Intent.putExtra("foodphone"+i , Kakaoresponse.documents.get(i-1).phone)
                        Intent.putExtra("foodaddress"+i , Kakaoresponse.documents.get(i-1).address_name)
                        Intent.putExtra("foodlink"+i , Kakaoresponse.documents.get(i-1).place_url)
                        Intent.putExtra("foodY"+i, Kakaoresponse.documents.get(i-1).y)
                        Intent.putExtra("foodX"+i, Kakaoresponse.documents.get(i-1).x)
                    }
                    Log.d("Test", "특정 정보: ${Kakaoresponse.documents.get(0).place_name}")
                    Log.d("Test", "Body: ${response.body()}")
                    startActivity(Intent)
                }
            }

            override fun onFailure(call: Call<ResultSearchKeyword>, t: Throwable) {
                // 통신 실패
                Log.w("MainActivity", "통신 실패: ${t.message}")
            }
        })
    }
}
