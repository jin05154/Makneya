package com.stopmeifyoucan.makneya

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TabHome : Fragment() {

    private lateinit var BujangnameSpace: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.tab_home, container, false)
        val getApproval = view.findViewById<Button>(R.id.btn_approval)
        val addBujang = view.findViewById<Button>(R.id.Plus)
        val seekbarGa : SeekBar = view.findViewById(R.id.SeekbarGa)
        val seekbarNa : SeekBar = view.findViewById(R.id.SeekbarNA)
        BujangnameSpace = view.findViewById(R.id.Bujangname)

        BujangnameSpace.text = InDB.prefs.getString("bujangname1", "")
        InDB.prefs.setString("feeling", (seekbarGa.progress+1).toString())
        InDB.prefs.setString("haejang", (seekbarNa.progress+1).toString())

        getApproval.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://l4uzx6dl7i.execute-api.ap-northeast-2.amazonaws.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(InstanceDatainterface::class.java)

                val json = JSONObject()
                json.put("bujang_code", InDB.prefs.getString("bujangcode1", ""))
                json.put("feeling", InDB.prefs.getString("feeling", ""))
                json.put("haejang", InDB.prefs.getString("haejang", ""))
                json.put("weather", weather)
                val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())
                val call = service.postinstanceData(requestBody)


                call.enqueue(object : Callback<InstanceDataResponse> {

                    override fun onResponse(
                        call: Call<InstanceDataResponse>,
                        response: Response<InstanceDataResponse>
                    ){
                        Log.d("alltext", response.toString())
                        if (response.code() == 200){
                            Log.d("restest", response.toString())
                            val testtext = response.body()!!
                            if (testtext != null){
                                Log.d("test", testtext.RecommendMenu.get(0).menuname.toString())
                                InDB.prefs.setString("menu1", testtext.RecommendMenu.get(0).menuname.toString())
                                InDB.prefs.setString("menu2", testtext.RecommendMenu.get(1).menuname.toString())
                                InDB.prefs.setString("menu3", testtext.RecommendMenu.get(2).menuname.toString())
                                InDB.prefs.setString("menu4", testtext.RecommendMenu.get(3).menuname.toString())
                                InDB.prefs.setString("menu5", testtext.RecommendMenu.get(4).menuname.toString())

                                val intent = Intent(context, MenuSuggest::class.java)
                                startActivity(intent)
                            }
                            else{
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
        })

        addBujang.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?){
                val intent = Intent(context, AddBujang::class.java)
                startActivity(intent)
            }
        })

        return view
    }

    companion object {
        fun newInstance(): TabHome = TabHome()
        val weather = "1"
    }
}

