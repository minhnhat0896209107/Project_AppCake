package com.nguyenvanminhnhat.projectcakeapp.pojo.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.MessageModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.UserModel

class FirebaseMessage {
    private var root: DatabaseReference? = null
    private var chatRoot : DatabaseReference?= null
    private var chatQuery: Query?= null
    private lateinit var iListUser: IListUser
    private var firebaseUser: FirebaseUser? = null
    private var listUser = mutableListOf<UserModel>()

    private var listChat = mutableListOf<MessageModel>()
    private lateinit var iMessage: IMessage
    private lateinit var iUser: IUser

    fun firebaseMessage(iListUser: IListUser, iMessage: IMessage, iUser: IUser) {
        this.iListUser = iListUser
        this.iMessage = iMessage
        this.iUser = iUser
        firebaseUser = FirebaseAuth.getInstance().currentUser
        root = FirebaseDatabase.getInstance().reference.child("Users")
        chatRoot = FirebaseDatabase.getInstance().getReference("Chat")
        chatQuery = chatRoot?.child("message")
    }

    fun getUser(){
        root?.child(firebaseUser!!.uid)?.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    val userModel = snapshot.getValue(UserModel::class.java)
                    if (userModel != null) {
                        iUser.onSuccessUser(userModel)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getAllUser() {
        listUser.clear()
        root?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val userModel: UserModel = dataSnapshot.getValue(UserModel::class.java)!!
                    if ((userModel.uid) != firebaseUser?.uid!!) {
                        listUser.add(userModel)
                    }
                }
                iListUser.onSuccess(listUser)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    fun sendMessage(senderId: String, receiverId: String, message: String) {
        var reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference()

        var hashMap: HashMap<String, String> = HashMap()
        hashMap.put("senderId", senderId)
        hashMap.put("receiverId", receiverId)
        hashMap.put("message", message)

        reference!!.child("Chat").push().setValue(hashMap)

    }

    fun readMessage(idSender: String, idReceiver: String) {
        listChat.clear()
        chatRoot?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                listChat.clear()
                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val chat = dataSnapShot.getValue(MessageModel::class.java)!!
                    if (chat.senderId.equals(idSender)  && chat.receiverId.equals(idReceiver)  ||
                        chat.senderId.equals(idReceiver) && chat.receiverId.equals(idSender)
                    ) {
                        listChat.add(chat)
                    }
                }
                iMessage.onSuccessChat(listChat)
            }
        })
    }

    fun removeMessage(message: String){
        listChat.clear()
        chatQuery?.equalTo(message)?.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot: DataSnapshot in snapshot.children){
                    dataSnapshot.ref.removeValue()
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

}

interface IListUser {
    fun onSuccess(success: List<UserModel>)
}

interface IMessage {
    fun onSuccessChat(successes: List<MessageModel>)
}

interface IUser{
    fun onSuccessUser(success: UserModel)
}