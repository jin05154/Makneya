package com.stopmeifyoucan.makneya.AddBujang

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.stopmeifyoucan.makneya.Data.InDB
import com.stopmeifyoucan.makneya.R

class AddBujangName : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_bujangname, container, false)
        val Bujangname = view.findViewById<TextView>(R.id.bujang_nickname)
        val btn_save = view.findViewById<Button>(R.id.btn_firstuser)

        btn_save.setOnClickListener {

            InDB.prefs.setString("bujang_name", Bujangname.text.toString())
            Log.d("부장 이름은", InDB.prefs.getString("bujang_name", ""))

            btn_save.text = "저장완료"
            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.changeview, AddBujangGgondae())
                .commit()
        }
        return view
    }
}