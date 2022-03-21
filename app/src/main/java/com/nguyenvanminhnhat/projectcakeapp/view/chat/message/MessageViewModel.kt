package com.nguyenvanminhnhat.projectcakeapp.view.chat.message

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.*
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.MessageModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.UserModel

class MessageViewModel : ViewModel(), IListUser, IMessage, IUser {
    private var repo = FirebaseMessage()

    private var chatLiveData = MutableLiveData<List<MessageModel>>()
    var chat = chatLiveData

    private var userLiveData = MutableLiveData<UserModel>()
    var user = userLiveData


    init{
        repo.firebaseMessage(this, this,this)
    }
    fun getUser(){
        repo.getUser()
    }

    fun removeMessage(message: String){
        repo.removeMessage(message)
    }

    fun readMessage(senderId: String, receiverId: String){
        repo.readMessage(senderId, receiverId)
    }

    fun sendMessage(senderId: String, receiverId: String, message: String){
        repo.sendMessage(senderId, receiverId, message)
    }

    override fun onSuccess(success: List<UserModel>) {
        TODO("Not yet implemented")
    }

    override fun onSuccessChat(successes: List<MessageModel>) {
        chatLiveData.postValue(successes)
    }

    override fun onSuccessUser(success: UserModel) {
        userLiveData.postValue(success)
    }


}