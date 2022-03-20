package com.nguyenvanminhnhat.projectcakeapp.view.register

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nguyenvanminhnhat.projectcakeapp.MainActivity
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.view.AuthViewModel
import com.nguyenvanminhnhat.projectcakeapp.view.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var userUid: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        init()

        btnSignUp.setOnClickListener {
            register()
        }

    }

    private fun register() {
        val email: String = edtEmail.text.toString()
        val password: String = edtPassword.text.toString()
        val userName: String = edtUserName.text.toString()
        val repassword: String = edtRePassword.text.toString()
        val phone: String = edtPhone.text.toString()
        validate(userName, password, email, repassword, phone)
    }

    fun init() {
        firebaseAuth = FirebaseAuth.getInstance()
    }

    fun validate(userName: String, password: String, email: String, repassword: String, phone: String) {
        if (userName == "" || userName.isEmpty()) {
            Toast.makeText(this, "Tên người dùng không được trống", Toast.LENGTH_SHORT).show()
        } else if (email == "" || userName.isEmpty()) {
            Toast.makeText(this, "Email không được trống", Toast.LENGTH_SHORT).show()
        } else if (password == "" || userName.isEmpty()) {
            Toast.makeText(this, "Mật khẩu không được trống", Toast.LENGTH_SHORT).show()
        } else if (!email.contains("@gmail.com")) {
            Toast.makeText(this, "Email phải có @gmail.com", Toast.LENGTH_SHORT).show()
        } else if (password.length < 6) {
            Toast.makeText(this, "Mật khẩu phải lớn hơn 5 ký tự", Toast.LENGTH_SHORT).show()
        } else if (repassword == "" || userName.isEmpty()) {
            Toast.makeText(this, "Nhập lại mật khẩu không được trống", Toast.LENGTH_SHORT)
                .show()
        } else if (!password.equals(repassword)) {
            Toast.makeText(
                this,
                "Mật khẩu và nhập lại mật khẩu không trùng nhau",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            firebaseAuth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener { p0 ->
                    if (p0.isSuccessful) {
                        userUid = firebaseAuth.currentUser!!.uid
                        refUsers =
                            FirebaseDatabase.getInstance().reference.child("Users").child(userUid)

                        val userHasMap = HashMap<String, Any>()
                        userHasMap["uid"] = userUid
                        userHasMap["username"] = userName
                        userHasMap["email"] = email
                        userHasMap["status"] = "offline"
                        userHasMap["phone"] = phone
                        userHasMap["search"] = userName.toLowerCase()

                        refUsers.updateChildren(userHasMap).addOnCompleteListener {
                            startActivity(Intent(this, MainActivity::class.java))
                        }

                    } else {
                        Toast.makeText(application, p0.exception?.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }
}