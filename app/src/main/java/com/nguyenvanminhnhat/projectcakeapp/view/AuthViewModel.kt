package com.nguyenvanminhnhat.projectcakeapp.view

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.Firebase

class AuthViewModel : ViewModel() {
    private var repo: Firebase = Firebase()
    private var userData = MutableLiveData<FirebaseUser>()


    init {
        userData = repo.getFirebaseUserMutableLiveData()
    }

    fun getUserData(): MutableLiveData<FirebaseUser> {
        return userData
    }

    fun signIn(email: String, password: String) {
        repo.login(email, password)
    }


}
