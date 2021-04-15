package com.stopmeifyoucan.makneya
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val gobutton = view.findViewById<Button>(R.id.btnApproval)

        gobutton.setOnClickListener(object :View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, MenuApproval::class.java)
                startActivity(intent)
            }

        })
        return view
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

}