package com.nguyenvanminhnhat.projectcakeapp.pojo.firebase

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference

class Firebase {
    private var firebaseUserLiveData: MutableLiveData<FirebaseUser> = MutableLiveData<FirebaseUser>()
    var firebaseAuth: FirebaseAuth? = null
    var isLogin = false

    fun getFirebaseUserMutableLiveData(): MutableLiveData<FirebaseUser> {
        return firebaseUserLiveData
    }


    init {
        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth!!.currentUser != null){
            firebaseUserLiveData.postValue(firebaseAuth!!.currentUser)
        }
    }

    fun login(email : String, password: String){
        firebaseAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener { p0 ->
            isLogin = if (p0.isSuccessful) {
                firebaseUserLiveData.postValue(firebaseAuth?.currentUser)
                true
            } else {
                false
            }
        }
    }


}