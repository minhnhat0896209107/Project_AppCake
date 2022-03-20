package com.nguyenvanminhnhat.projectcakeapp.view.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.adapter.AdvertisePager
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nguyenvanminhnhat.projectcakeapp.adapter.CategoryAdapter
import com.nguyenvanminhnhat.projectcakeapp.adapter.PopularCakeAdapter
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.PopularCakeModel


class HomeFragment : Fragment() {
    var timer: Timer? = null
    var totalAd = 0
    var current = 0
    private lateinit var advertisePager: AdvertisePager
    private lateinit var popularAdapter: PopularCakeAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        autoSlideImageAd()
        homeViewModel.getImage()
        homeViewModel.getPopularCake()
        homeViewModel.getCategory()
        obsListen()

        tvSeeAll.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_listCategory)
        }

    }
    private fun init() {
        advertisePager = AdvertisePager()
        viewPagerAd.adapter = advertisePager
        tabLayoutAd.setupWithViewPager(viewPagerAd, true)

        popularAdapter = PopularCakeAdapter(onClickPopular = onClickPopular())
        rcvPopularCake.apply {
            adapter = popularAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }

        categoryAdapter = CategoryAdapter()
        rcvCategory.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    private fun onClickPopular(): (PopularCakeModel) -> Unit = {
        val bundle = Bundle().apply {
            putSerializable("popular", it)
        }
        findNavController().navigate(R.id.action_home_to_detail, bundle)
    }


    private fun obsListen() {
        homeViewModel.imageLive.observe(viewLifecycleOwner) { t ->
            advertisePager.setAdvertisePager(t)
            totalAd = t.size

        }

        homeViewModel.popularCake.observe(viewLifecycleOwner) { t ->
            popularAdapter.setPopularCake(t)
        }

        homeViewModel.category.observe(viewLifecycleOwner) { t ->
            categoryAdapter.setDataCategory(t)
        }
    }
    private fun autoSlideImageAd() {
        if (timer == null) {
            timer = Timer()
        }
        timer?.schedule(object : TimerTask() {
            override fun run() {
                Handler(Looper.getMainLooper()).post {
                    run {
                       try{
                           current = viewPagerAd.currentItem
                           if (current < totalAd - 1) {
                               current++
                               viewPagerAd.currentItem = current
                           } else {
                               viewPagerAd.currentItem = 0
                           }
                       }catch (e: Exception){
                           e.message
                       }
                    }
                }
            }

        }, 500, 5000)
    }

    override fun onStop() {
        super.onStop()

    }
}

