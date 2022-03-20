package com.nguyenvanminhnhat.projectcakeapp.view.chat

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.*

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.adapter.UserAdapter
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.UserModel
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment() {
    private val userAdapter: UserAdapter by lazy {
        UserAdapter(isChatCheck = false, onClickUser = onClickUser())
    }

    private fun onClickUser(): (UserModel) -> Unit = {
        val bundle = Bundle().apply {
            putSerializable("modelUser", it)
        }
        findNavController().navigate(
            R.id.action_chat_to_message,
            bundle
        )
    }
    private val chatViewModel: ChatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        obsListen()
        chatViewModel.getAllUser()
        searchUser()
    }


    fun init() {
        rcvListUser.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun obsListen() {
        chatViewModel.listUser.observe(viewLifecycleOwner) {
            userAdapter.setData(it)
        }
    }

    private fun searchUser() {
        edtSearchUser.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                userAdapter.filter.filter(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }


}