package com.nguyenvanminhnhat.projectcakeapp.itf

import com.nguyenvanminhnhat.projectcakeapp.pojo.model.BlogModel

interface IResultBlog {
    fun onSuccess(success: List<BlogModel>)
}