package com.nguyenvanminhnhat.projectcakeapp.view.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.FirebaseMessage
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.IListUser
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.IMessage
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.IUser
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.MessageModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.UserModel

class ChatViewModel : ViewModel() , IListUser, IMessage, IUser {
    private var repo = FirebaseMessage()
    private var listUserLiveData = MutableLiveData<List<UserModel>>()
    var listUser = listUserLiveData

    init {
        repo.firebaseMessage(this, this, this)
    }

    fun getAllUser(){
        repo.getAllUser()
    }

    override fun onSuccess(success: List<UserModel>) {
        listUser.postValue(success)
    }

    override fun onSuccessChat(successes: List<MessageModel>) {
        TODO("Not yet implemented")
    }

    override fun onSuccessUser(success: UserModel) {
        TODO("Not yet implemented")
    }

}