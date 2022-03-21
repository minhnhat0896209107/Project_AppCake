package com.nguyenvanminhnhat.projectcakeapp.view.favourite

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.adapter.FavouriteAdapter
import kotlinx.android.synthetic.main.fragment_favourite.*
import java.util.*


class FavouriteFragment : Fragment() {
    private val viewModel: FavouriteViewModel by viewModels()

    private val favouriteAdapter: FavouriteAdapter by lazy {
        FavouriteAdapter(onLongClickFav = onLongClickFav())
    }

    private fun onLongClickFav(): (String) -> Unit = {
        val dialogBuilder = AlertDialog.Builder(context)

        dialogBuilder.setMessage("Bạn có muốn xoá thông tin này không?")
            .setCancelable(false)
            .setPositiveButton("Có", DialogInterface.OnClickListener { dialog, _ ->
                viewModel.removeFav(it)
                swiperRefresh.isEnabled = true
                Toast.makeText(context, "Xoá thành công $it", Toast.LENGTH_SHORT).show()
            })
            .setNegativeButton("Không", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
                Toast.makeText(context, "Xoá thất bại", Toast.LENGTH_SHORT).show()
            })

        val alert = dialogBuilder.create()
        alert.setTitle("Xoá Item yêu thích")
        alert.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        obsListen()
        viewModel.getListFav()
        onRefresh(false)
    }

    private fun obsListen() {
        viewModel.listFav.observe(viewLifecycleOwner) { t ->
            swiperRefresh.isEnabled = true
            favouriteAdapter.setData(t)
        }
    }

    private fun init() {
        rcvFavourite.apply {
            adapter = favouriteAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    fun onRefresh(isLoading: Boolean){
        swiperRefresh?.setOnRefreshListener {
            viewModel.getListFav()
            Handler().postDelayed({
                swiperRefresh.isRefreshing = isLoading
            }, 1000)
        }
    }

}