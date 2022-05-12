package com.nguyenvanminhnhat.projectcakeapp.view.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.adapter.HistoryAdapter
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment() {
    private val historyAdapter : HistoryAdapter by lazy {
        HistoryAdapter()
    }
    private val viewModelHistory : HistoryViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        ivBackProfile.setOnClickListener {
            activity?.onBackPressed()
        }
        viewModelHistory.getHistory()
        viewModelHistory.history.observe(viewLifecycleOwner) {
            t ->
            historyAdapter.setData(t)
        }
    }

    private fun init(){
        rcvHistory.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)
        }
    }

}