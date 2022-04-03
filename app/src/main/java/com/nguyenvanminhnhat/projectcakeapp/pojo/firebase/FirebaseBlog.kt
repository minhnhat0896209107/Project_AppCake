package com.nguyenvanminhnhat.projectcakeapp.pojo.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.nguyenvanminhnhat.projectcakeapp.itf.IResultBlog
import com.nguyenvanminhnhat.projectcakeapp.itf.IResultProfile
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.BlogModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.UserModel

class FirebaseBlog {
    private var rootBlog : DatabaseReference ?= null
    private var listBlog = mutableListOf<BlogModel>()
    private lateinit var iResultBlog: IResultBlog
    fun firebaseBlog(iResultBlog: IResultBlog){
        this.iResultBlog = iResultBlog
        rootBlog = FirebaseDatabase.getInstance().reference.child("Blog")
    }


    fun getAllBlog(){
        listBlog.clear()
        rootBlog?.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children){
                    var blogModel = BlogModel()
                    var img = dataSnapshot.child("imageUrl").value.toString()
                    var title = dataSnapshot.child("title").value.toString()
                    var des = dataSnapshot.child("description").value.toString()
                    var un = dataSnapshot.child("userName").value.toString()
                    var cl = dataSnapshot.child("countLike").value.toString()

                    blogModel.apply {
                        titleBlog = title
                        descriptionBlog = des
                        imgBlog = img
                        nameUser = un
                        countLike = cl
                    }
                    listBlog.add(blogModel)
                }
                iResultBlog.onSuccess(listBlog)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}