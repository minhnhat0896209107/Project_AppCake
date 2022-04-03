package com.nguyenvanminhnhat.projectcakeapp.view.blog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.adapter.BlogAdapter
import kotlinx.android.synthetic.main.fragment_blog.*

class BlogFragment : Fragment() {

    private val blogViewModel : BlogViewModel by viewModels()
    private val blogAdapter : BlogAdapter by lazy {
        BlogAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_blog_to_add)
        }

        init()
        obsListen()
        blogViewModel.getListBlog()
    }

    private fun obsListen(){
        blogViewModel.listBlog.observe(viewLifecycleOwner){
            t->
            blogAdapter.setData(t)
        }
    }

    private fun init(){
        rcvBlog.apply {
            adapter = blogAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
}