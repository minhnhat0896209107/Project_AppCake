package com.nguyenvanminhnhat.projectcakeapp.pojo.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartModel")
data class CartModel(
    @PrimaryKey
    @NonNull
    var idCart: String,
    var imageCake : String ?= null,
    var nameCake: String ?= null,
    var priceCake: Int = 0,
    var countQuantity: Int ?= 0
) {
    constructor() : this("abc", null, null, 0, 0)
}
