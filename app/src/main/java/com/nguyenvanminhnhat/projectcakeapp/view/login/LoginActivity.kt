package com.nguyenvanminhnhat.projectcakeapp.view.login

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.nguyenvanminhnhat.projectcakeapp.MainActivity
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.view.AuthViewModel
import com.nguyenvanminhnhat.projectcakeapp.view.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val viewModel : AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        viewModel.getUserData().observe(this
        ) { firebaseUser ->
            if (firebaseUser != null) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
        btnSignIn.setOnClickListener {
            val email: String = edtEmail.text.toString()
            val password : String = edtPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Email hoặc mật khẩu không được null", Toast.LENGTH_SHORT).show()
            }else{
                viewModel.signIn(email, password)

                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
            }
        }
    }
}