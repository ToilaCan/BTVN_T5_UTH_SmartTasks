package com.example.btvn_uth_smarttasks

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.btvn_uth_smarttasks.screens.SignInScreen
import com.example.btvn_uth_smarttasks.ui.theme.SignUpStep1Activity
import com.example.btvn_uth_smarttasks.ui.theme.BTVN_UTH_SmartTasksTheme
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setContent {
            BTVN_UTH_SmartTasksTheme {
                SignInScreen(
                    onLoginClick = { email, password ->
                        signInWithEmail(email, password)
                    },
                    onSignUpClick = {
                        val intent = Intent(this, SignUpStep1Activity::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }

    private fun signInWithEmail(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val userEmail = user?.email ?: "Không rõ"

                    // 👉 Truyền dữ liệu đến ProfileActivity
                    val intent = Intent(this, ProfileActivity::class.java).apply {
                        putExtra("name", "Chưa cập nhật")           // Vì đăng nhập Google chưa có tên
                        putExtra("email", userEmail)
                        putExtra("birthDate", "Chưa cập nhật")     // Mặc định
                    }
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
