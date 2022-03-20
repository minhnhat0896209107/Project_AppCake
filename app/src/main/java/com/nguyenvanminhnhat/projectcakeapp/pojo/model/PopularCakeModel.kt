package com.nguyenvanminhnhat.projectcakeapp.pojo.model

import androidx.room.PrimaryKey
import java.io.Serializable

data class PopularCakeModel(

    var nameCake: String ?= null,

    var descripCake: String ?= null,

    var imageCake: String ?= null,

    var priceCake: Int ?= null,

    var reviewCake: Review ?= null
): Serializable

