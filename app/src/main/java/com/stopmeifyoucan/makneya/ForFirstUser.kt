package com.stopmeifyoucan.makneya

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.stopmeifyoucan.makneya.Data.InDB
import kotlinx.android.synthetic.main.activity_forfirstuser.*

class ForFirstUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forfirstuser)

        //  외부 터치시 키보드 내리기
        outerFirstUserTextLayout.setOnClickListener {
            hideKeyboard()
        }

        myBirth.setOnKeyListener(View.OnKeyListener { textView, keyCode, event ->
            // 화면상의 enter키는 안 먹히고 컴퓨터 자판의 enter키만 먹히는 듯? 다시 확인 필요.
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                saveFirstUser()
                return@OnKeyListener true
            }
            false
        })

        btn_firstuser.setOnClickListener {
            if (myNickname.text.toString() == "" || myBirth.text.toString() == "") {
                Toast.makeText(this, "이름과 생일 정보를 정확하게 입력해주세요!", Toast.LENGTH_SHORT).show()
            } else {
                saveFirstUser()
            }
        }
    }

    private fun saveFirstUser() {
        InDB.prefs.setString("currentstate", "1")
        InDB.prefs.setString("name", myNickname.text.toString())
        InDB.prefs.setString("b_date", myBirth.text.toString())
        //Log.d("bujang name is", InDB.prefs.getString("bujang_name", ""))
        val intent = Intent(this, AddBujang::class.java)
        startActivity(intent)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(myBirth.windowToken, 0)
    }
}