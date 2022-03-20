package com.nguyenvanminhnhat.projectcakeapp.view.chat.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.adapter.UserAdapter
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {
    private val userAdapter: UserAdapter by lazy {
        UserAdapter(isChatCheck = false)
    }
    private val searchViewModel: SearchViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivBackChat.setOnClickListener {
            findNavController().navigate(R.id.action_search_to_chat)
        }

        init()
        obsListen()
        searchViewModel.getAllUser()
        searchUser()
    }

    fun init() {
        rcvListUser.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun obsListen() {
        searchViewModel.listUser.observe(viewLifecycleOwner) {
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