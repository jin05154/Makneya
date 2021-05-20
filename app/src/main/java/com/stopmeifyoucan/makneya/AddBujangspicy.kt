package com.stopmeifyoucan.makneya

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar

class AddBujangspicy : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_bujangspicy, container, false)

        val seekbar : SeekBar = view.findViewById(R.id.spicybar)
        val btn_save = view.findViewById<Button>(R.id.btn_firstuser)

        btn_save.setOnClickListener {
            InDB.prefs.setString("spicy", (seekbar.progress+1).toString())
            btn_save.text = "저장완료"

            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.changeview, AddBujangdrink())
                .commit()
        }
        return view
    }
}