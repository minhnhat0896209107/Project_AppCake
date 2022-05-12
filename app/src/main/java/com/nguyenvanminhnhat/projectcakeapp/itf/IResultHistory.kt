package com.nguyenvanminhnhat.projectcakeapp.itf

import com.nguyenvanminhnhat.projectcakeapp.pojo.model.HistoryModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.UserModel

interface IResultHistory {
    fun onSuccess(success: List<HistoryModel>)
}