package com.example.btvn_uth_smarttasks.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.btvn_uth_smarttasks.screens.SignUpStep2Screen
import com.example.btvn_uth_smarttasks.ui.theme.BTVN_UTH_SmartTasksTheme

class SignUpStep2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 👉 Lấy dữ liệu từ SignUpStep1
        val username = intent.getStringExtra("username") ?: ""
        val email = intent.getStringExtra("email") ?: ""
        val phone = intent.getStringExtra("phone") ?: ""

        setContent {
            BTVN_UTH_SmartTasksTheme {
                // 👉 Truyền vào nếu cần
                SignUpStep2Screen(
                    username = username,
                    email = email,
                    phone = phone,
                    onOtpVerified = {
                        // TODO: Điều hướng đến SignUpStep3Activity nếu cần
                    }
                )
            }
        }
    }
}
