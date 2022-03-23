package com.nguyenvanminhnhat.projectcakeapp.pojo.model

data class BlogModel(
    var nameUser : String ?= null,
    var imgBlog : String ?= null,
    var countLike : Int ?= 0,
    var titleBlog : String ?= null,
    var descriptionBlog: String ?= null
)