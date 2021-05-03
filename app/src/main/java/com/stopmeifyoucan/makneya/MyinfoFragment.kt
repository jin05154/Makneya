package com.stopmeifyoucan.makneya
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class MyinfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_myinfo, container, false)

    companion object {
        fun newInstance(): MyinfoFragment = MyinfoFragment()
    }

}