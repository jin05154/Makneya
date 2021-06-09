package com.stopmeifyoucan.makneya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.firebase.auth.FirebaseAuth
import com.stopmeifyoucan.makneya.Data.FirstUserResponse
import com.stopmeifyoucan.makneya.Data.FirstUserinterface
import com.stopmeifyoucan.makneya.Data.InDB
import kotlinx.android.synthetic.main.activity_addbujang.*
import kotlinx.android.synthetic.main.layout_add_bujangdrink.*
import kotlinx.android.synthetic.main.layout_add_bujangggondae.*
import kotlinx.android.synthetic.main.layout_add_bujanghurry.*
import kotlinx.android.synthetic.main.layout_add_bujangname.*
import kotlinx.android.synthetic.main.layout_add_bujangspicy.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddBujang : AppCompatActivity() {

    var viewList = ArrayList<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addbujang)

        viewList.add(layoutInflater.inflate(R.layout.layout_add_bujangname, null))
        viewList.add(layoutInflater.inflate(R.layout.layout_add_bujangggondae, null))
        viewList.add(layoutInflater.inflate(R.layout.layout_add_bujanghurry, null))
        viewList.add(layoutInflater.inflate(R.layout.layout_add_bujangspicy, null))
        viewList.add(layoutInflater.inflate(R.layout.layout_add_bujangdrink, null))

        mViewPager.adapter = CustomPagerAdapter()
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            // This method will be invoked when the current page is scrolled, either as part of
            // a programmatically initiated smooth scroll or a user initiated touch scroll.
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val positionPageNum = position + 1
                addBujangTitle.text = "부장님 정보 등록 $positionPageNum"

                btn_save_name.setOnClickListener {
                    Log.d("부장 이름은", bujang_nickname.text.toString())
                    mViewPager.setCurrentItem(position + 1, true)
                }
                btn_before_ggon.setOnClickListener {
                    mViewPager.setCurrentItem(position - 1, true)
                }
                btn_save_ggon.setOnClickListener {
                    Log.d("꼰대력은", (ggonbar.progress + 1).toString())
                    mViewPager.setCurrentItem(position + 1, true)
                }
                if (position == 2) {
                    btn_before_hurry.setOnClickListener {
                        mViewPager.setCurrentItem( position - 1, true)
                    }
                    btn_save_hurry.setOnClickListener {
                        Log.d("성질머리는", (hurrybar.progress + 1).toString())
                        mViewPager.setCurrentItem(position + 1, true)
                    }
                }
                if (position == 3) {
                    btn_before_spicy.setOnClickListener {
                        mViewPager.setCurrentItem(position - 1, true)
                    }
                    btn_save_spicy.setOnClickListener {
                        Log.d("매운음식 선호도는", (spicybar.progress + 1).toString())
                        mViewPager.setCurrentItem(position + 1, true)
                    }
                }
                if (position == 4) {
                    btn_before_drink.setOnClickListener {
                        mViewPager.setCurrentItem(position - 1, true)
                    }
                    btn_save_drink.setOnClickListener {
                        Log.d("주량은", (drinkbar.progress + 1).toString())

                        if (InDB.prefs.getString("new_user", " ").toInt() == 1){
                            val retrofit = Retrofit.Builder()
                                .baseUrl("https://l4uzx6dl7i.execute-api.ap-northeast-2.amazonaws.com/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()

                            val service = retrofit.create(GetBujangInterface::class.java)

                            val user = FirebaseAuth.getInstance().currentUser

                            user.getIdToken(true)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val idToken = task.result!!.token
                                        Log.d("상태 ", InDB.prefs.getString("currentstate", ""))

                                        // Send token to your backend via HTTPS
                                        val json = JSONObject()
                                        json.put("state", InDB.prefs.getString("currentstate", "").toInt() )
                                        json.put("idToken", idToken)
                                        json.put("bujang_code", InDB.prefs.getString("currentcode", ""))
                                        json.put("bujang_name", bujang_nickname.text.toString())
                                        json.put("ggondae", (ggonbar.progress+1).toString())
                                        json.put("drink", (drinkbar.progress+1).toString())
                                        json.put("spicy", (spicybar.progress+1).toString())
                                        json.put("hurry", (hurrybar.progress+1).toString())

                                        val requestBody: RequestBody =
                                            RequestBody.create(MediaType.parse("application/json"), json.toString())
                                        val call = service.postbujangplus(requestBody)
                                        Log.d("json", json.toString())

                                        call.enqueue(object : Callback<GetBujangResponse> {

                                            override fun onResponse(
                                                call: Call<GetBujangResponse>,
                                                response: Response<GetBujangResponse>
                                            ) {
                                                if (response.code() == 200) {
                                                    Log.d("restest", response.toString())
                                                    val Plusresponse = response.body()!!
                                                    //Log.d("uidtest", testtext.Body.toString())

                                                    if (Plusresponse != null) {
                                                        InDB.prefs.setString("bujangcount", Plusresponse.bujangcount.toString())
                                                        //Log.d("new_user", InDB.prefs.getString("new_user", ""))
                                                        val bCount = Plusresponse.bujangcount?.toInt()
                                                        for(i in 1..bCount!!){
                                                            InDB.prefs.setString(("bujangname"+i), Plusresponse.bujangdata.get(i-1).bujangname.toString())
                                                            InDB.prefs.setString(("bujangcode"+i), Plusresponse.bujangdata.get(i-1).bujangcode.toString())
                                                            Log.d("실험 그자체", InDB.prefs.getString(("bujangcode"+i), ""))
                                                        }
                                                        InDB.prefs.remove("currentcode")

                                                    } else {
                                                        Log.d("test", "uid is null")
                                                        //Log.d("token: ", idToken.toString())
                                                    }


                                                }
                                                val testtext = response.body()
                                                if (testtext != null) {
                                                    //Log.d("errortest", testtext.body.toString())
                                                }
                                                //Log.d("errortest", idToken.toString())
                                            }

                                            override fun onFailure(call: Call<GetBujangResponse>, t: Throwable) {
                                                Log.d("test", "실패")
                                                Log.d("error: ", t.message.toString())
                                                //Log.d("token: ", idToken.toString())
                                            }
                                        })

                                    } else {
                                        //Handle error -> task.getException();
                                    }
                                }
                        }
                        else{
                            Log.d("확인용", "제대로 들어옴")
                            val retrofit = Retrofit.Builder()
                                .baseUrl("https://l4uzx6dl7i.execute-api.ap-northeast-2.amazonaws.com/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()

                            val service = retrofit.create(FirstUserinterface::class.java)

                            val user = FirebaseAuth.getInstance().currentUser

                            user.getIdToken(true)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val idToken = task.result!!.token
                                        Log.d("상태 ", InDB.prefs.getString("new_user", ""))

                                        // Send token to your backend via HTTPS
                                        val json = JSONObject()
                                        json.put("idToken", idToken)
                                        json.put("name", InDB.prefs.getString("name", ""))
                                        json.put("b_date", InDB.prefs.getString("b_date", ""))
                                        json.put("bujang_name", bujang_nickname.text.toString())
                                        json.put("ggondae", (ggonbar.progress+1).toString())
                                        json.put("drink", (drinkbar.progress+1).toString())
                                        json.put("spicy", (spicybar.progress+1).toString())
                                        json.put("hurry", (hurrybar.progress+1).toString())

                                        val requestBody: RequestBody =
                                            RequestBody.create(MediaType.parse("application/json"), json.toString())
                                        val call = service.firstbujangadd(requestBody)
                                        Log.d("json", json.toString())

                                        call.enqueue(object : Callback<FirstUserResponse> {

                                            override fun onResponse(
                                                call: Call<FirstUserResponse>,
                                                response: Response<FirstUserResponse>
                                            ) {
                                                if (response.code() == 200) {
                                                    Log.d("restest", response.toString())
                                                    val Plusresponse = response.body()!!
                                                    //Log.d("uidtest", testtext.Body.toString())

                                                    if (Plusresponse != null) {
                                                        InDB.prefs.setString("bujangcount", Plusresponse.bujang_count.toString())
                                                        InDB.prefs.setString("new_user", Plusresponse.new_user.toString())
                                                        //Log.d("new_user", InDB.prefs.getString("new_user", ""))
                                                        val bCount = Plusresponse.bujang_count?.toInt()
                                                        for(i in 1..bCount!!){
                                                            InDB.prefs.setString(("bujangname"+i), Plusresponse.bujangData.get(i-1).bujang_name.toString())
                                                            InDB.prefs.setString(("bujangcode"+i), Plusresponse.bujangData.get(i-1).bujang_code.toString())
                                                            Log.d("실험 그자체", InDB.prefs.getString(("bujangcode"+i), ""))
                                                        }
                                                        InDB.prefs.remove("currentcode")

                                                    } else {
                                                        Log.d("test", "uid is null")
                                                        //Log.d("token: ", idToken.toString())
                                                    }


                                                }
                                            }

                                            override fun onFailure(call: Call<FirstUserResponse>, t: Throwable) {
                                                Log.d("test", "실패")
                                                Log.d("error: ", t.message.toString())
                                                //Log.d("token: ", idToken.toString())
                                            }
                                        })

                                    } else {
                                        //Handle error -> task.getException();
                                    }
                                }

                        }
                        val intent = Intent(this@AddBujang, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)

                    }
                }
            }

            // This method will be invoked when a new page becomes selected.
            override fun onPageSelected(position: Int) {
                indicator0_iv_main.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.shape_circle_gray, null))
                indicator1_iv_main.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.shape_circle_gray, null))
                indicator2_iv_main.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.shape_circle_gray, null))
                indicator3_iv_main.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.shape_circle_gray, null))
                indicator4_iv_main.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.shape_circle_gray, null))

                when(position) {
                    0 -> indicator0_iv_main.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.shape_circle_purple, null))
                    1 -> indicator1_iv_main.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.shape_circle_purple, null))
                    2 -> indicator2_iv_main.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.shape_circle_purple, null))
                    3 -> indicator3_iv_main.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.shape_circle_purple, null))
                    4 -> indicator4_iv_main.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.shape_circle_purple, null))
                }
            }

            // Called when the scroll state changes. Useful for discovering when the user begins
            // dragging, when the pager is automatically settling to the current page,
            // or when it is fully stopped/idle.
            override fun onPageScrollStateChanged(state: Int) {
                //Log.d("TAG", "onPageScrollStateChanged : $state")
            }
        })
    }

    inner class CustomPagerAdapter : PagerAdapter() {
        // 사용 가능한 뷰의 갯수 리턴
        override fun getCount(): Int {
            return viewList.size
        }

        // instantiateItem에서 만든 객체를 사용할지 판단
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        // position에 해당하는 페이지 생성
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            mViewPager.addView(viewList[position])
            return viewList[position]
        }

        // position에 해당하는 페이지 제거
        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            mViewPager.removeView(`object` as View)
        }
    }
}