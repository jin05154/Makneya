package com.stopmeifyoucan.makneya.Navigation
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stopmeifyoucan.makneya.R

class TabCommunity : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.tab_community, container, false)

    companion object {
        fun newInstance(): TabCommunity =
            TabCommunity()
    }

}