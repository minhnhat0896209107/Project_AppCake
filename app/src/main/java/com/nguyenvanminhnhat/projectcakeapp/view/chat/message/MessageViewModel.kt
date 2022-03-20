package com.nguyenvanminhnhat.projectcakeapp.view.chat.message

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.FirebaseMessage
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.FirebaseProfile
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.IListUser
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.UserModel

class MessageViewModel : ViewModel(), FirebaseProfile.IResultProfile, IListUser {
    private var repo = FirebaseMessage()

    private var userLiveData = MutableLiveData<UserModel>()
    var user = userLiveData


    init{
        repo.firebaseMessage(this, this)
    }

    fun getUser(){
        repo.getUser()

    }
    override fun onSuccess(success: UserModel) {
        userLiveData.postValue(success)
    }

    override fun onError(error: String) {
        TODO("Not yet implemented")
    }

    override fun onSuccess(success: List<UserModel>) {
        TODO("Not yet implemented")
    }

}