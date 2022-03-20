package com.nguyenvanminhnhat.projectcakeapp.view.chat.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.FirebaseMessage
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.FirebaseProfile
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.IListUser
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.UserModel

class SearchViewModel : ViewModel(), FirebaseProfile.IResultProfile, IListUser {

    private var repo = FirebaseMessage()
    private var listUserLiveData = MutableLiveData<List<UserModel>>()
    var listUser = listUserLiveData

    init {
        repo.firebaseMessage(this, this)
    }

    fun getAllUser(){
        repo.getAllUser()
    }

    override fun onSuccess(success: List<UserModel>) {
        listUser.postValue(success)
    }

    override fun onSuccess(success: UserModel) {
        TODO("Not yet implemented")
    }

    override fun onError(error: String) {
        TODO("Not yet implemented")
    }


}