package com.stopmeifyoucan.makneya

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class TabHome : Fragment() {

    //var handler:DisplayHandler? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.tab_home, container, false)
        val getApproval = view.findViewById<Button>(R.id.btn_approval)
        val addBujang = view.findViewById<Button>(R.id.Plus)

        /* handler = DisplayHandler()

        var thread = NetworkThread()
        thread.start() */

        getApproval.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, MenuApproval::class.java)
                startActivity(intent)
            }
        })

        addBujang.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?){
                val intent = Intent(context, AddBujangName::class.java)
                startActivity(intent)
            }
        })

        return view
    }

    companion object {
        fun newInstance(): TabHome = TabHome()
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

