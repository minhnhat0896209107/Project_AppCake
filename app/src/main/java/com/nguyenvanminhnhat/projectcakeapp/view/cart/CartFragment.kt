package com.nguyenvanminhnhat.projectcakeapp.view.cart

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.adapter.CartAdapter
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.PaymentModel
import com.nguyenvanminhnhat.projectcakeapp.view.detail.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.item_cart.view.*
import java.text.DecimalFormat
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class CartFragment : Fragment() {
    private val cartViewModel : CartViewModel by viewModels()
    private var paymentModel = PaymentModel("idPayment")
    private val cartAdapter : CartAdapter by lazy {
        CartAdapter(onClickQuantity = onClickQuantity())
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun onClickQuantity(): (Long) -> Unit = {
        paymentModel.apply {
            idPayment = LocalDateTime.now().toString()
            listCart = cartViewModel.getAllCart()
            totalPrice = it
            date = LocalDateTime.now().toString()
        }
        val dec = DecimalFormat("###,###.###")
        val number = dec.format(it)
        tvTotalPrice.text =  "Tổng tiền: $number VNĐ"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivBackHome.setOnClickListener {
            findNavController().navigate(R.id.action_cart_to_home)
        }
        cartAdapter.setData(cartViewModel.getAllCart())
        btnContinue.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("payment", paymentModel)
            }
            findNavController().navigate(R.id.action_cart_to_payment, bundle)

        }

        init()
    }

    private fun init(){
        rcvListCart.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
}