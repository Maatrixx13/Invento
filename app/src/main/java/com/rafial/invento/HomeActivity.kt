package com.rafial.invento

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rafial.invento.databinding.ActivityHomeBinding
import com.rafial.invento.databinding.ActivityRegisterBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}