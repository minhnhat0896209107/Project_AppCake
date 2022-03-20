package com.nguyenvanminhnhat.projectcakeapp.view.chat.message

import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.adapter.MessageAdapter
import com.nguyenvanminhnhat.projectcakeapp.const.RetrofitInstance
import com.nguyenvanminhnhat.projectcakeapp.const.onHideKeyBoard
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.FirebaseProfile
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.NotificationData
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.PushNotification
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.UserModel
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MessageFragment : Fragment() {
    private val messageViewModel : MessageViewModel by viewModels()
    private lateinit var userModel: UserModel
    private var uid = ""
    private var userUid = ""
    private lateinit var messageAdapter: MessageAdapter
    var topic = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivBackChat.setOnClickListener{
            findNavController().navigate(R.id.action_message_to_chat)
        }
        init()
        obsListen()
        messageViewModel.getUser()
        val bundle = this.arguments
        if (bundle != null) {
            userModel = bundle.getSerializable("modelUser") as UserModel
        }

        tvUserName.text = userModel.username.toString()
        userUid = userModel.uid!!


        btnListen()
    }

    private fun init(){
        messageAdapter = MessageAdapter()
        rcvMessage.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
    private fun obsListen(){
        messageViewModel.user.observe(viewLifecycleOwner){
            uid = it.uid!!
            messageViewModel.readMessage(uid, userUid)
        }
        messageViewModel.chat.observe(viewLifecycleOwner){
            messageAdapter.setData(it)
        }
    }
    private fun btnListen(){

        btnSendMessage.setOnClickListener {
            val message: String = etMessage.text.toString()

            if (message.isEmpty()) {
                Toast.makeText(context, "message is empty", Toast.LENGTH_SHORT).show()
                etMessage.setText("")
            } else {
                messageViewModel.sendMessage(uid, userUid, message)
                etMessage.setText("")
                onHideKeyBoard.hideKeyboard(requireActivity())
                topic = "/topics/$userUid"
                PushNotification(
                    NotificationData( userModel.username!!,message),
                    topic).also {
                    sendNotification(it)
                }
            }
        }
    }

    private fun sendNotification(notification: PushNotification)
    = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful) {
                Log.d("TAG", "Response: ${Gson().toJson(response)}")
            } else {
                Log.e("TAG", response.errorBody()!!.string())
            }
        } catch(e: Exception) {
            Log.e("TAG", e.toString())
        }
    }

}