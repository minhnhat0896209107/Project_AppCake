package com.nguyenvanminhnhat.projectcakeapp.view.blog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nguyenvanminhnhat.projectcakeapp.itf.IResultBlog
import com.nguyenvanminhnhat.projectcakeapp.itf.IResultProfile
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.FirebaseBlog
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.BlogModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.UserModel

class BlogViewModel : ViewModel(), IResultBlog{

    private var repo = FirebaseBlog()
    private var listBlogLiveData = MutableLiveData<List<BlogModel>>()
    var listBlog = listBlogLiveData

    init {
        repo.firebaseBlog(this)
    }

    fun getListBlog(){
        repo.getAllBlog()
    }

    override fun onSuccess(success: List<BlogModel>) {
        listBlogLiveData.postValue(success)
    }

}