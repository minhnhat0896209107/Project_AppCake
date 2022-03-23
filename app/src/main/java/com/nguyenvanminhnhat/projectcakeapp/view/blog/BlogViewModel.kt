package com.nguyenvanminhnhat.projectcakeapp.view.blog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nguyenvanminhnhat.projectcakeapp.itf.IResultProfile
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.FirebaseBlog
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.UserModel

class BlogViewModel : ViewModel(){

    private var repo = FirebaseBlog()


    init {
    }

}