package com.stopmeifyoucan.makneya

import android.Manifest
import android.content.Intent
import android.location.Address
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.stopmeifyoucan.makneya.Data.InDB
import com.stopmeifyoucan.makneya.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tedPermission()

        // Configure Google Sign In
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.firebase_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Initialize Firebase Auth
        auth = Firebase.auth

        btn_googleSignIn.setOnClickListener {
            signIn()
        }
    }

    // [START on_start_check_user]
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    // [END on_start_check_user]

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(this, "구글 로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    loginSuccess()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun loginSuccess() {  // 이거를 updateUI에 활용하기

        val retrofit = Retrofit.Builder()
            .baseUrl("https://l4uzx6dl7i.execute-api.ap-northeast-2.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(Userinterface::class.java)

        val mUser = FirebaseAuth.getInstance().currentUser
        mUser.getIdToken(true)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val idToken = task.result!!.token
                    //Log.d("token: ", idToken.toString())

                    // Send token to your backend via HTTPS
                    val call = service.getCurrentUserData(idToken.toString())

                    call.enqueue(object: Callback<UserResponse>{

                        override fun onResponse(
                            call: Call<UserResponse>,
                            response: Response<UserResponse>) {
                            if (response.code() == 200) {
                                Log.d("alltext", response.toString())
                                val testtext = response.body()!!
                                //Log.d("uidtest", testtext.Body.toString())

                                if (testtext != null) {
                                    Log.d("uidtest", testtext.new_user.toString())
                                    Log.d("bujangcount", testtext.bujangcount.toString())
                                    Log.d("name", testtext.Name.toString())
                                    //Log.d("dtest", testtext.bujangdata.get(0).bujangcode.toString())
                                    InDB.prefs.setString("new_user", testtext.new_user.toString())
                                    InDB.prefs.setString("bujangname1", testtext.bujangdata.get(0).bujangname.toString())
                                    InDB.prefs.setString("bujangcode1", testtext.bujangdata.get(0).bujangcode.toString())
                                    Log.d("new_user", InDB.prefs.getString("new_user", ""))
                                    moveActivity()

                                } else {
                                    Log.d("test", "uid is null")
                                    //Log.d("token: ", idToken.toString())
                                }
                            }
                        }

                        override fun onFailure(
                            call: Call<UserResponse>,
                            t: Throwable) {
                            Log.d("test", "실패")
                            Log.d("error: ", t.message.toString())
                            //Log.d("token: ", idToken.toString())
                        }
                    })

                } else {
                    //Handle error -> task.getException();
                }
            }
    }

    private fun moveActivity(){
        Log.d("new_user", "before ${InDB.prefs.getString("new_user", " ").toInt()}")
        if ((InDB.prefs.getString("new_user", "").toInt()) == 1){
            Log.d("new_user", "into if user ${InDB.prefs.getString("new_user", " ").toInt()}")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
        else{
            Log.d("new_user", "else user ${InDB.prefs.getString("new_user", " ").toInt()}")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_REQUEST_CODE)
    }

    private fun updateUI(user: FirebaseUser?) {

    }

    private fun tedPermission() {
        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {}
            override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
                finish()
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permissionListener)
            .setRationaleMessage("서비스 사용을 위해서 몇가지 권한이 필요합니다.")
            .setDeniedMessage("[설정] > [권한] 에서 권한을 설정할 수 있습니다.")
            .setPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .check()
    }

    companion object {
        val GOOGLE_REQUEST_CODE = 99
        val TAG = "googleLogin"
    }
}