// MainActivity.kt
package com.example.btvn_uth_smarttasks

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.btvn_uth_smarttasks.screens.LoginScreen
import com.example.btvn_uth_smarttasks.ui.theme.BTVN_UTH_SmartTasksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BTVN_UTH_SmartTasksTheme {
                Surface(
                    modifier = Modifier,
                    color = Color.White
                ) {
                    LoginScreen { navigateToProfile() }
                }
            }
        }
    }

    private fun navigateToProfile() {
        val intent = Intent(this, SignInActivity::class.java) // đúng là SignInActivity
        startActivity(intent)
    }

}
