package com.nguyenvanminhnhat.projectcakeapp.view.detail

import android.content.Intent.getIntent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.adapter.ReviewAdapter
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CartModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.PopularCakeModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*
import java.text.DecimalFormat


@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var cartModel : CartModel = CartModel()
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var popular: PopularCakeModel
    private val detailViewModel : DetailViewModel by viewModels()
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
        tvAddCart.setOnClickListener {
            cartModel.apply {
                idCart = "id${popular.nameCake}"
                nameCake = popular.nameCake
                priceCake = popular.priceCake!!
                imageCake = popular.imageCake
            }
            tvAddCart.setBackgroundColor(Color.GRAY)
            detailViewModel.insertCart(cartModel)
            Log.d("TAG", cartModel.toString())
            Toast.makeText(context, "Th??m v??o gi??? h??ng th??nh c??ng", Toast.LENGTH_SHORT).show()
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
        tvPrice.text = "$number VN??"

        popular.reviewCake?.let { reviewAdapter.setData(it) }

    }
    private fun init(){
        reviewAdapter = ReviewAdapter()
        rcvReview.apply {
            adapter = reviewAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        }
    }

}