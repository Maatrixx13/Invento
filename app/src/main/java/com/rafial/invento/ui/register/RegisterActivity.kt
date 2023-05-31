package com.rafial.invento.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.storyapp.ui.register.RegisterViewModel
import com.rafial.invento.R
import com.rafial.invento.databinding.ActivityRegisterBinding
import com.rafial.invento.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
     private lateinit var binding: ActivityRegisterBinding
    private val registerVM: RegisterViewModel by viewModels ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.register_act)
        playAnimation()
        actionClick()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivLogo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 4000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val edtName = ObjectAnimator.ofFloat(binding.edtName, View.ALPHA, 1f).setDuration(300)
        val edtEmail = ObjectAnimator.ofFloat(binding.edtEmail, View.ALPHA, 1f).setDuration(300)
        val edtPassword =
            ObjectAnimator.ofFloat(binding.edtPassword, View.ALPHA, 1f).setDuration(300)
        val btnRegister = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(300)
        val tvSignup = ObjectAnimator.ofFloat(binding.tvAlready, View.ALPHA, 1f).setDuration(300)
        val signupBtn = ObjectAnimator.ofFloat(binding.btnSignin, View.ALPHA, 1f).setDuration(300)

        AnimatorSet().apply {
            playSequentially(edtName, edtEmail, edtPassword, btnRegister, tvSignup, signupBtn)
            start()
        }
    }


//     private fun actionSignUp(){
//         val name = binding.edtName.text.toString()
//         val email = binding.edtEmail.text.toString()
//         val password = binding.edtPassword.text.toString()
//         if (name.isEmpty()&&email.isEmpty()&&password.isEmpty()) {
//             binding.apply {
//                 edtName.error =  resources.getString(R.string.name_empty)
//                 edtEmail.error =  resources.getString(R.string.email_empty)
//                 edtPassword.error =  resources.getString(R.string.pass_empty)
//             }
//             Toast.makeText(this@RegisterActivity,"Please fill the empty field",Toast.LENGTH_SHORT).show()
//         }else{
//             when{
//                 name.isEmpty() ->{
//                     binding.edtName.error = getString(R.string.name_empty)
//                 }
//                 email.isEmpty() -> {
//                     binding.edtEmail.error = getString(R.string.email_empty)
//                 }
//                 password.isEmpty() -> {
//                     binding.edtPassword.error = getString(R.string.pass_empty)
//                 }
//                 else  -> {
//                     registerViewModel.signUpUser(name, email, password).observe(this@RegisterActivity){
//                         if(it!=null) {
//                             showLoading(true)
//                             when (it) {
//                                 is ResultProgress.Loading -> {
//                                     showLoading(true)
//                                 }
//                                 is ResultProgress.Success -> {
//                                         showLoading(false)
//                                         val data = it.data
//                                         if (data.error!!) {
//                                             Toast.makeText(this@RegisterActivity,data.message, Toast.LENGTH_SHORT).show()
//                                         }
//                                        else{
//                                             Toast.makeText(this@RegisterActivity,resources.getString(R.string.account_created), Toast.LENGTH_SHORT).show()
//                                             val intent = Intent(this, LoginActivity::class.java)
//                                             startActivity(intent)
//                                             finish()
//                                        }
//
//                                 }
//                                 is ResultProgress.Error -> {
//                                     showLoading(false)
//                                     val error = it.error
//                                     Toast.makeText(
//                                         this@RegisterActivity,
//                                         "$error",
//                                         Toast.LENGTH_LONG
//                                     ).show()
//                                 }
//                             }
//                         }
//                         else{showLoading(false)
//                             Toast.makeText(this@RegisterActivity,getString(R.string.account_not_created),Toast.LENGTH_SHORT).show()
//                         }
//                     }
//                 }
//             }
//         }
//
//    }
    private fun actionClick() {
    binding.btnRegister.setOnClickListener {

        val back = Intent(this, LoginActivity::class.java)
        startActivity(back)
//            actionSignUp()
    }
        binding.btnSignin.setOnClickListener {
            val goBackToLogin = Intent(this, LoginActivity::class.java)
            startActivity(goBackToLogin)

        }
    }
    private fun showLoading(isLoading: Boolean) {
        val loading = binding.pbLoading
        binding.apply {
            edtName.isEnabled = !isLoading
            edtEmail.isEnabled = !isLoading
            edtPassword.isEnabled = !isLoading
            btnRegister.isEnabled = !isLoading
            loading.visibility = if (isLoading) View.VISIBLE else View.GONE

        }
    }
}