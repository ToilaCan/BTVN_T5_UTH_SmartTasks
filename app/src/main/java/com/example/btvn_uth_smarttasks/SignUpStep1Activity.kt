package com.example.btvn_uth_smarttasks.ui.theme

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.btvn_uth_smarttasks.screens.SignUpStep1Screen
import com.example.btvn_uth_smarttasks.ui.theme.BTVN_UTH_SmartTasksTheme

class SignUpStep1Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BTVN_UTH_SmartTasksTheme {
                SignUpStep1Screen(
                    onNextClick = { username, email, phone ->
                        // 👉 Điều hướng sang SignUpStep2Activity
                        val intent = Intent(this, SignUpStep2Activity::class.java).apply {
                            putExtra("username", username)
                            putExtra("email", email)
                            putExtra("phone", phone)
                        }
                        startActivity(intent)
                    }
                )
            }
        }
    }
}
