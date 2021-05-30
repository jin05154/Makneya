package com.stopmeifyoucan.makneya.AddBujang

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import com.google.firebase.auth.FirebaseAuth
import com.stopmeifyoucan.makneya.*
import com.stopmeifyoucan.makneya.Data.InDB
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddBujangDrink : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_bujangdrink, container, false)

        val seekbar : SeekBar = view.findViewById(R.id.drinkbar)
        val btn_save = view.findViewById<Button>(R.id.btn_firstuser)

        btn_save.setOnClickListener {
            InDB.prefs.setString("drink", (seekbar.progress+1).toString())
            btn_save.text = "잠시만요!"

            val retrofit = Retrofit.Builder()
                .baseUrl("https://l4uzx6dl7i.execute-api.ap-northeast-2.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(GetBujangInterface::class.java)

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
                                    val testtext = response.body()!!
                                    //Log.d("uidtest", testtext.Body.toString())

                                    if (testtext != null) {
                                        Log.d("test", testtext.bujangcount.toString())

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

            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        return view
    }
}