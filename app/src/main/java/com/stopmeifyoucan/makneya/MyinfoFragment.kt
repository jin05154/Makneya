package com.stopmeifyoucan.makneya

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MyinfoFragment : Fragment() {
    private var googleSignInClient : GoogleSignInClient ?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_myinfo, container, false)

        // 구글 로그아웃을 위해 로그인 세션 가져오기
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(view.context, gso)

        val google_signout = view.findViewById<Button>(R.id.btn_googleLogout)

        google_signout.setOnClickListener {
            signOut()
            val logoutIntent= Intent(context, LoginActivity::class.java)
            logoutIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(logoutIntent)
        }

        return view
    }
    private fun signOut() {
        Firebase.auth.signOut()
        googleSignInClient?.signOut()
    }
    companion object {
        fun newInstance(): MyinfoFragment = MyinfoFragment()
    }

}