package com.nguyenvanminhnhat.projectcakeapp.pojo.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartModel")
data class CartModel(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var idCart: Int ?= 0,
    var imageCake : String ?= null,
    var nameCake: String ?= null,
    var priceCake: Int ?= 0,
    var countQuantity: Int ?= 0
)
