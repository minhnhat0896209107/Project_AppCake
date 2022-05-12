package com.nguyenvanminhnhat.projectcakeapp.view.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.UserModel
import com.nguyenvanminhnhat.projectcakeapp.view.chat.message.MessageFragment
import com.nguyenvanminhnhat.projectcakeapp.view.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        obsListen()

        btnSignOut.setOnClickListener {
            viewModel.logout()
        }
        viewModel.getUser()
        tvWebView.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_webView)
        }
        ivHistory.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_history)
        }
    }

    private fun obsListen() {
        viewModel.getUserLogout().observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                startActivity(Intent(context, LoginActivity::class.java))
            }
        })

        viewModel.user.observe(viewLifecycleOwner) { t ->
            tvUser.text = t.username
            tvEmail.text = t.email
            tvPhone.text = t.phone
        }
    }
}

