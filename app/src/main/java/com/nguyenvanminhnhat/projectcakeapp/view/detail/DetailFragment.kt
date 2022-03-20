package com.nguyenvanminhnhat.projectcakeapp.view.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.adapter.ReviewAdapter
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.PopularCakeModel
import kotlinx.android.synthetic.main.fragment_detail.*
import java.text.DecimalFormat

class DetailFragment : Fragment() {
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var popular: PopularCakeModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments

        if (bundle != null){
            popular = bundle.getSerializable("popular") as PopularCakeModel
        }

        init()
        setDataDetail()
        ivBackHome.setOnClickListener {
            findNavController().navigate(R.id.action_detail_to_home)
        }
    }

    private fun setDataDetail(){
        Glide.with(this)
            .load(popular.imageCake)
            .error(R.mipmap.ic_launcher)
            .into(ivDetail)

        tvNameDetail.text = popular.nameCake
        tvDescription.text = popular.descripCake

        val dec = DecimalFormat("###,###.###")
        val number = dec.format(popular.priceCake)
        tvPrice.text = "$number VNĐ"

//        popular.reviewCake?.let { reviewAdapter.setData(it) }

        popular.reviewCake?.let { reviewAdapter.setData(it) }

        Log.d("AAA", "${popular.reviewCake}")
        Log.d("CCC", "${popular}")

    }
    private fun init(){
        reviewAdapter = ReviewAdapter()
        rcvReview.apply {
            adapter = reviewAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        }
    }

}