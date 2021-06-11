package com.stopmeifyoucan.makneya.Navigation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.stopmeifyoucan.makneya.Data.InDB
import com.stopmeifyoucan.makneya.LoginActivity
import com.stopmeifyoucan.makneya.LoginActivity.Companion.TAG
import com.stopmeifyoucan.makneya.MyBujang
import com.stopmeifyoucan.makneya.R

class TabMyInfo : Fragment() {

    private var googleSignInClient : GoogleSignInClient ?= null
    // getUserProfile()에서 불러와야 돼서 전역변수로 선언해야함
    private lateinit var userNameEditText: TextView
    private lateinit var userImageEditImage : ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.tab_myinfo, container, false)
        userImageEditImage = view.findViewById(R.id.profileCircleImageView)
        userNameEditText = view.findViewById(R.id.userID)
        val myAddress = view.findViewById<TextView>(R.id.address)
        val changeBujangInfo = view.findViewById<TextView>(R.id.changeBujangInfo)
        val googleSignout = view.findViewById<TextView>(R.id.myinfoLogout)
        val googleDeleteAccount = view.findViewById<TextView>(R.id.myinfoDeleteAccount)

        getUserProfile()
        myAddress.text = InDB.prefs.getString("myaddress", "")

        changeBujangInfo.setOnClickListener{
            val intent= Intent(context, MyBujang::class.java)
            startActivity(intent)
        }

        // 완벽한(?) 구글 로그아웃을 위해 로그인 세션 가져오기
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.firebase_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(view.context, gso)

        googleSignout.setOnClickListener {
            signOut()
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        googleDeleteAccount.setOnClickListener {
            deleteAccount()
            val intent= Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        return view
    }
    private fun getUserProfile() {
        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid

            userNameEditText.text = name
            Picasso.get()
                .load(photoUrl)
                .into(userImageEditImage)
        }
    }
    private fun signOut() {
        Firebase.auth.signOut()
        googleSignInClient?.signOut()
    }
    private fun deleteAccount() {
        // currentUser가 nullable. null이면 뒤 작업을 안 한다는 뜻.
        Firebase.auth.currentUser?.delete()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "회원탈퇴 완료.")
                }
            }
    }
    companion object {
        fun newInstance(): TabMyInfo = TabMyInfo()
    }
}