package com.example.btvn_uth_smarttasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.btvn_uth_smarttasks.screens.ProfileScreen
import com.example.btvn_uth_smarttasks.ui.theme.BTVN_UTH_SmartTasksTheme
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser

        val email = user?.email ?: "Không rõ"
        val name = "Chưa cập nhật"
        val birthDate = "Chưa cập nhật"

        setContent {
            BTVN_UTH_SmartTasksTheme {
                ProfileScreen(
                    name = name,
                    email = email,
                    birthDate = birthDate,
                    onBack = { finish() }
                )
            }
        }
    }
}
