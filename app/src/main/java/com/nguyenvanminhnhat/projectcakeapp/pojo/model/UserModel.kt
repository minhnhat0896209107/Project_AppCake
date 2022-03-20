package com.nguyenvanminhnhat.projectcakeapp.pojo.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class UserModel(
    @SerializedName("email")
    var email: String?= null,
    @SerializedName("phone")
    var phone : String?= null,
    @SerializedName("search")
    var search: String?= null,
    @SerializedName("status")
    var status: String?= null,
    @SerializedName("uid")
    var uid: String?= null,
    @SerializedName("username")
    var username: String ?= null
) : Serializable