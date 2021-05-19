package com.stopmeifyoucan.makneya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import com.google.firebase.auth.FirebaseAuth
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddBujangDrink : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addbujangdrink)

        val seekbar : SeekBar = findViewById(R.id.drinkbar)
        val btnBujangdrink = findViewById<Button>(R.id.btn_firstuser)

        btnBujangdrink.setOnClickListener {
            InDB.prefs.setString("drink", (seekbar.progress+1).toString())
            //Log.d("bujang ggondae", InDB.prefs.getString("drink", ""))

            val retrofit = Retrofit.Builder()
                .baseUrl("https://l4uzx6dl7i.execute-api.ap-northeast-2.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(Getbujanginterface::class.java)

            val User = FirebaseAuth.getInstance().currentUser

            User.getIdToken(true)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val idToken = task.result!!.token
                        //Log.d("token: ", idToken.toString())

                        // Send token to your backend via HTTPS
                        val json = JSONObject()
                        json.put("state", 1)
                        json.put("idToken", idToken)
                        json.put("bujang_code", "")
                        json.put("bujang_name", InDB.prefs.getString(("bujang_name"), ""))
                        json.put("ggondae", InDB.prefs.getString(("ggondae"), ""))
                        json.put("drink", InDB.prefs.getString(("drink"), ""))
                        json.put("spicy", InDB.prefs.getString(("spicy"), ""))
                        json.put("hurry", InDB.prefs.getString(("hurry"), ""))
                        val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())
                        val call = service.postbujangplus(requestBody)
                        Log.d("json", json.toString())

                        call.enqueue(object : Callback<GetbujangResponse> {

                            override fun onResponse(
                                call: Call<GetbujangResponse>,
                                response: Response<GetbujangResponse>
                            ){
                                if (response.code() == 200){
                                    Log.d("restest", response.toString())
                                    val testtext = response.body()!!
                                    //Log.d("uidtest", testtext.Body.toString())

                                    if (testtext != null){
                                        Log.d("test", testtext.body.toString())

                                    }
                                    else{
                                        Log.d("test", "uid is null")
                                        //Log.d("token: ", idToken.toString())
                                    }


                                }
                                val testtext = response.body()
                                if (testtext != null) {
                                    Log.d("errortest", testtext.body.toString())
                                }
                                //Log.d("errortest", idToken.toString())
                            }

                            override fun onFailure(call: Call<GetbujangResponse>, t: Throwable) {
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
    }
}