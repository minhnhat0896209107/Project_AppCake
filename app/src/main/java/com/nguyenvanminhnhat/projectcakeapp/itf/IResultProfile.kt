package com.nguyenvanminhnhat.projectcakeapp.itf

import com.nguyenvanminhnhat.projectcakeapp.pojo.model.UserModel

interface IResultProfile {
    fun onSuccess(success: UserModel)
}