// File: SignUpStep2Screen.kt
package com.example.btvn_uth_smarttasks.screens

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

@Composable
fun SignUpStep2Screen(
    username: String,
    email: String,
    phone: String,
    onOtpVerified: () -> Unit
) {
    val context = LocalContext.current as Activity
    var otpCode by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var verificationId by remember { mutableStateOf<String?>(null) }
    var otpSent by remember { mutableStateOf(false) }

    LaunchedEffect(phone) {
        val phoneNumber = if (phone.startsWith("+")) phone else "+84${phone.trimStart('0')}"

        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(context)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    otpCode = credential.smsCode ?: ""
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) onOtpVerified()
                        }
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Toast.makeText(context, "Gửi OTP thất bại: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                    Log.e("OTP", "Verification failed", e)
                }

                override fun onCodeSent(
                    verificationIdParam: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    verificationId = verificationIdParam
                    otpSent = true
                    Toast.makeText(context, "Mã OTP đã được gửi đến $phoneNumber", Toast.LENGTH_SHORT).show()
                }
            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Create Account", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Update Info", fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = otpCode,
            onValueChange = { otpCode = it },
            label = { Text("OTP") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector = if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Re Password") },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (verificationId != null && otpCode.isNotEmpty()) {
                    if (password != confirmPassword) {
                        Toast.makeText(context, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val credential = PhoneAuthProvider.getCredential(verificationId!!, otpCode)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // 👉 Lưu thông tin người dùng vào SharedPreferences
                                val prefs = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
                                prefs.edit()
                                    .putString("username", username)
                                    .putString("email", email)
                                    .putString("password", password)
                                    .apply()

                                Toast.makeText(context, "Xác thực thành công!", Toast.LENGTH_SHORT).show()
                                onOtpVerified()
                            } else {
                                Toast.makeText(context, "Mã OTP không đúng hoặc hết hạn", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up")
        }
    }
}