package com.stopmeifyoucan.makneya.navigation

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.GsonBuilder
import com.stopmeifyoucan.makneya.user.AddBujang
import com.stopmeifyoucan.makneya.data.*
import com.stopmeifyoucan.makneya.MenuSuggestion
import com.stopmeifyoucan.makneya.R
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TabHome : Fragment() {

    private lateinit var progressDialog: AppCompatDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.tab_home, container, false)
        val spinnerBujang = view.findViewById<Spinner>(R.id.spinnerBujang)
        val addBujang = view.findViewById<Button>(R.id.plusBtn)
        val seekbarGa : SeekBar = view.findViewById(R.id.seekbarGA)
        val seekbarNa : SeekBar = view.findViewById(R.id.seekbarNA)
        val spinnerWeather  = view.findViewById<Spinner>(R.id.spinnerWeather)
        val getSuggestions = view.findViewById<Button>(R.id.btn_suggestions)

        val weatherList = listOf("날씨를 선택해 주세요", "맑음", "흐림", "비", "눈")
        var bujangList = mutableListOf<String>()

        val bCount = InDB.prefs.getString("bujangcount", "").toInt()
        for(i in 1..bCount!!) {
            bujangList.add(InDB.prefs.getString("bujangname"+i, ""))
        }

        val adapterBujang = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, bujangList)
        val adapterWeather = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, weatherList)
        var indexBujang : Int? = null

        spinnerBujang.adapter = adapterBujang
        spinnerWeather.adapter = adapterWeather

        spinnerBujang.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //if(position != 0) Toast.makeText(requireContext(), weatherList[position], Toast.LENGTH_SHORT).show()
                indexBujang = position + 1
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }

        spinnerWeather.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //if(position != 0) Toast.makeText(requireContext(), weatherList[position], Toast.LENGTH_SHORT).show()
                InDB.prefs.setString("currentweather", (position-1).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }

        getWeatherData()

        getSuggestions.setOnClickListener {
            // 버튼 클릭하면 로딩 애니메이션 보이기
            progressON()

            val json = JSONObject()

            val retrofit = Retrofit.Builder()
                .baseUrl(awsBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(InstanceDatainterface::class.java)

            InDB.prefs.setString("haejang", (seekbarGa.progress+1).toString())
            InDB.prefs.setString("feeling", (seekbarNa.progress+1).toString())
            Log.d("해장", (seekbarGa.progress+1).toString())
            Log.d("감정", (seekbarNa.progress+1).toString())

            json.put("bujang_code", InDB.prefs.getString(("bujangcode" + indexBujang), ""))
            Log.d("부장코드", InDB.prefs.getString(("bujangcode" + indexBujang), ""))
            json.put("feeling", InDB.prefs.getString("feeling", ""))
            json.put("haejang", InDB.prefs.getString("haejang", ""))
            json.put("weather", InDB.prefs.getString("currentweather", ""))

            val user = FirebaseAuth.getInstance().currentUser
            user.getIdToken(true)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val idToken = task.result!!.token
                        json.put("idToken", idToken.toString())
                        val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())
                        val call = service.postinstanceData(requestBody)

                        call.enqueue(object : Callback<InstanceDataResponse> {
                            override fun onResponse(
                                call: Call<InstanceDataResponse>,
                                response: Response<InstanceDataResponse>
                            ) {
                                Log.d("alltext", response.toString())
                                if (response.code() == 200){
                                    Log.d("json", json.toString())
                                    Log.d("restest", response.toString())
                                    val testtext = response.body()!!
                                    if (testtext != null) {
                                        //Log.d("test", testtext.RecommendMenu.get(0).menuname.toString())
                                        InDB.prefs.setString("menu1", testtext.RecommendMenu.get(0).menuname.toString())
                                        InDB.prefs.setString("menu2", testtext.RecommendMenu.get(1).menuname.toString())
                                        InDB.prefs.setString("menu3", testtext.RecommendMenu.get(2).menuname.toString())
                                        InDB.prefs.setString("menu4", testtext.RecommendMenu.get(3).menuname.toString())
                                        InDB.prefs.setString("menu5", testtext.RecommendMenu.get(4).menuname.toString())

                                        if (InDB.prefs.getString("currentweather", "").toInt() == -1) {
                                            Toast.makeText(requireContext(), "날씨를 선택해 주세요!", Toast.LENGTH_SHORT).show()
                                            progressOFF()
                                        } else {
                                            val intent = Intent(context, MenuSuggestion::class.java)
                                            startActivity(intent)
                                            progressOFF()
                                        }
                                    } else {
                                        Log.d("test", "uid is null")
                                    }
                                }
                            }

                            override fun onFailure(call: Call<InstanceDataResponse>, t: Throwable) {
                                Log.d("test", "실패")
                                Log.d("error: ", t.message.toString())
                                //Log.d("token: ", idToken.toString())
                            }
                        })
                    }
                }
        }

        addBujang.setOnClickListener {
            InDB.prefs.setString("currentstate", "1")
            val intent = Intent(context, AddBujang::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun getWeatherData() {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(weatherBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(WeatherService::class.java)
        val call = service.getCurrentWeatherData(data_type, num_of_rows, page_no, base_data, base_time, nx, ny)
        call.enqueue(object : Callback<WEATHER> {
            override fun onResponse(call: Call<WEATHER>, response: Response<WEATHER>) {
                if (response.isSuccessful) {
                    Log.d("api", response.body().toString())
                    //Log.d("api", response.body()!!.response.body.items.item.toString())
                    //Log.d("api", response.body()!!.response.body.items.item[0].category)
                }
            }
            override fun onFailure(call: Call<WEATHER>, t: Throwable) {
                t.message?.let { Log.d("api", it) }
            }
        })
    }

    private fun progressON() {
        progressDialog = AppCompatDialog(context)
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

    companion object {
        fun newInstance(): TabHome = TabHome()
        const val awsBaseUrl = "https://l4uzx6dl7i.execute-api.ap-northeast-2.amazonaws.com/"
        const val weatherBaseUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService/"
        const val num_of_rows = 10
        const val page_no = 1
        const val data_type = "JSON"
        var base_time = 1100
        var base_data = 20210629
        var nx = "55"
        var ny = "127"
    }
}