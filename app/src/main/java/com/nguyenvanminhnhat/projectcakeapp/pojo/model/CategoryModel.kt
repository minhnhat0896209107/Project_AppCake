package com.nguyenvanminhnhat.projectcakeapp.pojo.model

import androidx.room.PrimaryKey

data class CategoryModel(

    var nameCategory: String?=null,
    var imageCategory: String?= null,
    var priceCategory: Int?= 0
)