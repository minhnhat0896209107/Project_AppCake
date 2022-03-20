package com.nguyenvanminhnhat.projectcakeapp.pojo.firebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.UserModel

class FirebaseProfile {
    private var firebaseUserLogoutLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var firebaseAuth: FirebaseAuth? = null
    private var root: DatabaseReference? = null
    private lateinit var iResultProfile: IResultProfile
    private var firebaseUser : FirebaseUser? = null

    fun firebaseProfile(iResultProfile: IResultProfile){
        this.iResultProfile = iResultProfile
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser
        root = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
    }

    fun getUserLoggedMutableLiveData(): MutableLiveData<Boolean> {
        return firebaseUserLogoutLiveData
    }

    fun getUser(){
        root?.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    val userModel = snapshot.getValue(UserModel::class.java)
                    if (userModel != null) {
                        iResultProfile.onSuccess(userModel)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    fun logOut(){
        firebaseAuth?.signOut()
        firebaseUserLogoutLiveData.postValue(true)
    }

    interface IResultProfile {
        fun onSuccess(success: UserModel)
        fun onError(error: String)
    }
}