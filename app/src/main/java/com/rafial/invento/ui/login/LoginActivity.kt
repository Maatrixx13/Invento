package com.rafial.invento.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.storyapp.ui.register.RegisterViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rafial.invento.HomeActivity
import com.rafial.invento.R
import com.rafial.invento.databinding.ActivityLoginBinding
import com.rafial.invento.ui.register.RegisterActivity
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginVM: RegisterViewModel by viewModels ()
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var authFireBase: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.login_act)
//====================GOOGLE SIGN IN SERVICE===================
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // Initialize Firebase Auth
        authFireBase = Firebase.auth

        binding.signInButton.setOnClickListener {
            signIn()
        }
//=====================================================
        setupView()
        actionClick()


    }
// ================   FUNCTION SIGN IN GOOGLE  ================
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        authFireBase.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = authFireBase.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }
//update UI when login success
    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null){
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = authFireBase.currentUser
        updateUI(currentUser)
    }
//=============================================================================

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivLogo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 4000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val tvSlogan = ObjectAnimator.ofFloat(binding.tvSlogan, View.ALPHA, 1f).setDuration(300)
        val edtEmail = ObjectAnimator.ofFloat(binding.edtEmail, View.ALPHA, 1f).setDuration(300)
        val edtPassword = ObjectAnimator.ofFloat(binding.edtPassword, View.ALPHA, 1f).setDuration(300)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnSignIn, View.ALPHA, 1f).setDuration(300)
        val signup = ObjectAnimator.ofFloat(binding.tvKet, View.ALPHA, 1f).setDuration(300)
        val signupBtn = ObjectAnimator.ofFloat(binding.btnSignUp, View.ALPHA, 1f).setDuration(300)

        AnimatorSet().apply {
            playSequentially(tvSlogan, edtEmail, edtPassword, btnLogin, signup, signupBtn)
            start()
        }
    }
//    private val loginViewModel: LoginViewModel by viewModels {
//        LoginVMFactory.getInstance(AuthPrefs.getInstance(dataStore))
//    }
    private fun setupView() {
        supportActionBar?.title = "Sign In"
        playAnimation()
    }



//    private fun checkLogin() {
//        loginViewModel.isLoginState().observe(this) {
//            if (it) {
//                val intent = Intent(this, HomeActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(intent)
//                finish()
//            }
//            else{
//                Toast.makeText(this@LoginActivity,getString(R.string.login_first),Toast.LENGTH_SHORT).show()
//            }
//        }
//    }



    private fun actionClick() {
        binding.btnSignUp.setOnClickListener {
            showLoading(true)
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)

            startActivity(intent)
            showLoading(false)

        }

        binding.btnSignIn.setOnClickListener {
            showLoading(true)
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)
            showLoading(false)
        }


    }
    private fun showLoading(isLoading: Boolean) {
        val loading = binding.pbLoading
        binding.apply {
            edtEmail.isEnabled = !isLoading
            edtPassword.isEnabled = !isLoading
            btnSignIn.isEnabled = !isLoading
            loading.visibility = if (isLoading) View.VISIBLE else View.GONE

        }
    }
    companion object {
        private const val TAG = "LoginActivity"
    }

}



