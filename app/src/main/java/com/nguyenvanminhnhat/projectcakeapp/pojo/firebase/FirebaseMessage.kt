package com.nguyenvanminhnhat.projectcakeapp.pojo.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.UserModel

class FirebaseMessage {
    private var root: DatabaseReference? = null
    private lateinit var iResultProfile: FirebaseProfile.IResultProfile
    private lateinit var iListUser: IListUser
    private var firebaseUser : FirebaseUser? = null
    private var listUser = mutableListOf<UserModel>()

    fun firebaseMessage(iResultProfile: FirebaseProfile.IResultProfile, iListUser: IListUser){
        this.iResultProfile = iResultProfile
        this.iListUser = iListUser
        firebaseUser = FirebaseAuth.getInstance().currentUser
        root = FirebaseDatabase.getInstance().reference.child("Users")
    }

    fun getUser(){
        root?.child(firebaseUser!!.uid)?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val userModel = snapshot.getValue(UserModel::class.java)
                    if (userModel != null) {
                        iResultProfile.onSuccess(userModel)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    fun getAllUser(){
        listUser.clear()
        root?.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children){
                    val userModel : UserModel = dataSnapshot.getValue(UserModel::class.java)!!
                    if ((userModel.uid) != firebaseUser?.uid!!){
                        listUser.add(userModel)
                    }
                }
                iListUser.onSuccess(listUser)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}

interface IListUser{
    fun onSuccess(success: List<UserModel>)
}