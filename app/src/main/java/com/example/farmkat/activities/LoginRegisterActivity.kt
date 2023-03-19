package com.example.farmkat.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.farmkat.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)
    }
}