package com.nguyenvanminhnhat.projectcakeapp.pojo.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.nguyenvanminhnhat.projectcakeapp.itf.IResultProfile
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.UserModel

class FirebaseBlog {
    private var rootBlog : DatabaseReference ?= null
    private lateinit var iResultProfile: IResultProfile

    fun firebaseBlog(iResultProfile: IResultProfile){
        this.iResultProfile = iResultProfile
        rootBlog = FirebaseDatabase.getInstance().reference.child("Blog")
    }


    fun getAllBlog(){
        rootBlog?.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}