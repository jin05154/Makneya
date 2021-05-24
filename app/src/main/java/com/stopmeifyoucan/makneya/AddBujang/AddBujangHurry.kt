package com.stopmeifyoucan.makneya.AddBujang

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import com.stopmeifyoucan.makneya.Data.InDB
import com.stopmeifyoucan.makneya.R

class AddBujangHurry : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_bujanghurry, container, false)

        val seekbar : SeekBar = view.findViewById(R.id.hurrybar)
        val btn_save = view.findViewById<Button>(R.id.btn_firstuser)

        btn_save.setOnClickListener {
            InDB.prefs.setString("hurry", (seekbar.progress+1).toString())
            btn_save.text = "저장완료"

            activity?.supportFragmentManager!!.beginTransaction()
                .replace(
                    R.id.changeview,
                    AddBujangSpicy()
                )
                .commit()
        }
        return view
    }
}