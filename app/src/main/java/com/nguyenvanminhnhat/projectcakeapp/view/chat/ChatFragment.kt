package com.nguyenvanminhnhat.projectcakeapp.view.chat

import android.os.Bundle
import android.view.*
import androidx.fragment.app.*

import androidx.navigation.fragment.findNavController
import com.nguyenvanminhnhat.projectcakeapp.R

import kotlinx.android.synthetic.main.fragment_chat.*


class ChatFragment : Fragment() {
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
    }


    fun init() {
        linearMessage.setOnClickListener {
            findNavController().navigate(R.id.message)
        }
        linearSearch.setOnClickListener {
            findNavController().navigate(R.id.search)
        }

    }


}