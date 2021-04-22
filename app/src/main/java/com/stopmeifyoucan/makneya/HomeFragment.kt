package com.stopmeifyoucan.makneya
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_menusuggestion.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONObject
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import android.os.Handler;


class HomeFragment : Fragment() {

    //var handler:DisplayHandler? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val gobutton = view.findViewById<Button>(R.id.btnApproval)
        val wow = view.findViewById<Button>(R.id.Plus)

        /* handler = DisplayHandler()

        var thread = NetworkThread()
        thread.start() */

        gobutton.setOnClickListener(object :View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, MenuApproval::class.java)
                startActivity(intent)
            }
        })

        wow.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?){
                val intent = Intent(context, Bujangplus1::class.java)
                startActivity(intent)
            }
        })

        return view


    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    /* inner class NetworkThread: Thread(){
        override fun run(){

            var site = "https://texenjloz9.execute-api.ap-northeast-2.amazonaws.com/testmakneAPI/test/asdf1234"
            var url = URL(site)
            var conn = url.openConnection()
            var input = conn.getInputStream()
            var isr = InputStreamReader(input)
            var br = BufferedReader(isr)

            var str: String? = null
            var buf = StringBuffer()

            do {
                str = br.readLine()

                if(str!=null){
                    buf.append(str)
                }
            }while (str!=null)

            var root = JSONObject(buf.toString())
            var hisname: String = root.getString("body")
            //Bujangname.text = hisname
            //handler?.sendEmptyMessage(0)
            var msg = Message()
            msg.what = 0
            msg.obj = hisname
            handler?.sendMessage(msg)

        }
    }

    inner class DisplayHandler : Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Bujangname.text = "${msg.obj}"
        }
    } */
}

