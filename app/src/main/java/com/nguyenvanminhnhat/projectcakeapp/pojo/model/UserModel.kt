package com.nguyenvanminhnhat.projectcakeapp.pojo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    var email: String?= null,
    var phone : String?= null,
    var search: String?= null,
    var status: String?= null,
    var uid: String?= null,
    var username: String ?= null
) : Parcelable