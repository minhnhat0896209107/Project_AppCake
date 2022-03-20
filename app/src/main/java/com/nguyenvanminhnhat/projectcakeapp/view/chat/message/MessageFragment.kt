package com.nguyenvanminhnhat.projectcakeapp.view.chat.message

import android.content.Intent.getIntent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.FirebaseProfile
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.UserModel
import kotlinx.android.synthetic.main.fragment_message.*


class MessageFragment : Fragment() {
    private val messageViewModel : MessageViewModel by viewModels()
    private var userModel = UserModel()
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

        obsListen()
        messageViewModel.getUser()
    }

    fun obsListen(){
        messageViewModel.user.observe(viewLifecycleOwner){
                t-> tvUserName.text = t.username
        }
    }


}