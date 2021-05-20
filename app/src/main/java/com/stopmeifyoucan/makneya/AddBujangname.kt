package com.stopmeifyoucan.makneya

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController

class AddBujangname : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_bujangname, container, false)

        val Bujangname = view.findViewById<TextView>(R.id.bujang_nickname)
        val btn_save = view.findViewById<Button>(R.id.btn_firstuser)

        btn_save.setOnClickListener {
            InDB.prefs.setString("bujang_name", Bujangname.text.toString())
            //Log.d("bujang name is", InDB.prefs.getString("bujang_name", ""))
            btn_save.text = "저장완료"
            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.changeview, AddBujangggondae())
                .commit()
        }

        return view

    }

}