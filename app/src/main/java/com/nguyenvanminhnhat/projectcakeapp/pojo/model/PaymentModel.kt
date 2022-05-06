package com.nguyenvanminhnhat.projectcakeapp.pojo.model

import androidx.annotation.NonNull
import androidx.room.PrimaryKey
import com.google.type.DateTime
import java.io.Serializable

data class PaymentModel(
    @PrimaryKey
    @NonNull
    var idPayment : String,
    var listCart : List<CartModel> ?= null,
    var totalPrice: Long ?= 0,
    var date : String ?= null
) : Serializable
