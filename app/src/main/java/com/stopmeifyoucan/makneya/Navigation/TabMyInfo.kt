package com.stopmeifyoucan.makneya.Navigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.stopmeifyoucan.makneya.Data.InDB
import com.stopmeifyoucan.makneya.LoginActivity
import com.stopmeifyoucan.makneya.MyBujang
import com.stopmeifyoucan.makneya.R

class TabMyInfo : Fragment() {

    private var googleSignInClient : GoogleSignInClient ?= null
    private lateinit var userNameEditText: TextView
    private lateinit var userImageEditImage : ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.tab_myinfo, container, false)
        userNameEditText = view.findViewById(R.id.userID)
        userImageEditImage = view.findViewById(R.id.profileCircleImageView)
        val myAddress = view.findViewById<TextView>(R.id.address)
        myAddress.text = "내 근무지역 : " + InDB.prefs.getString("myaddress", "")

        //checkCurrentUser()
        getUserProfile()

        // 구글 로그아웃을 위해 로그인 세션 가져오기
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.firebase_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(view.context, gso)

        val google_signout = view.findViewById<TextView>(R.id.myinfoLogout)
        val changebujanginfo = view.findViewById<TextView>(R.id.bujanginfo)

        google_signout.setOnClickListener {
            signOut()
            val logoutIntent= Intent(context, LoginActivity::class.java)
            logoutIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(logoutIntent)
        }

        changebujanginfo.setOnClickListener{
            val intent= Intent(context, MyBujang::class.java)
            startActivity(intent)
        }

        return view
    }
    private fun checkCurrentUser() {
        val user = Firebase.auth.currentUser
        if (user != null) {
            // User is signed in
        } else {
            // No user is signed in
        }
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
    companion object {
        fun newInstance(): TabMyInfo = TabMyInfo()
    }
}