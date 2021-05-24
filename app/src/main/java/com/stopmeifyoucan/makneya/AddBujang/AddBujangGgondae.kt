package com.stopmeifyoucan.makneya.AddBujang

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import com.stopmeifyoucan.makneya.Data.InDB
import com.stopmeifyoucan.makneya.R

class AddBujangGgondae : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_bujangggondae, container, false)

        val seekbar : SeekBar = view.findViewById(R.id.ggonbar)
        val btn_save = view.findViewById<Button>(R.id.btn_firstuser)

        btn_save.setOnClickListener {
            InDB.prefs.setString("ggondae", (seekbar.progress+1).toString())
            Log.d("이후 부장이름", InDB.prefs.getString("bujang_name", ""))
            //InDB.prefs.remove("bujang_name") //삭제 테스트용

            Log.d("이후 부장이름", InDB.prefs.getString("bujang_name", ""))

            btn_save.text = "저장완료"

            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.changeview, AddBujangHurry())
                .commit()
        }
        return view
    }
}