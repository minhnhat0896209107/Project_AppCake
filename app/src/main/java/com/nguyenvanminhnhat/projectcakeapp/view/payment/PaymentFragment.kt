package com.nguyenvanminhnhat.projectcakeapp.view.payment

import android.app.AlertDialog
import android.content.DialogInterface
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
import com.google.firebase.database.*
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.adapter.PaymentAdapter
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.BlogModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CartModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.PaymentModel
import com.nguyenvanminhnhat.projectcakeapp.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favourite.*
import kotlinx.android.synthetic.main.fragment_payment.*


@AndroidEntryPoint
class PaymentFragment : Fragment() {
    var strUserName = ""
    var strAddress = ""
    var strPhone = ""
    private lateinit var paymentModel: PaymentModel
    private val paymentViewModel : PaymentViewModel by viewModels()
    private val paymentAdapter : PaymentAdapter by lazy {
        PaymentAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivBackCart.setOnClickListener {
            activity?.onBackPressed()
        }

        init()
        val bundle = this.arguments

        if (bundle != null){
            paymentModel = bundle.getSerializable("payment") as PaymentModel
        }
        paymentModel.listCart?.let { paymentAdapter.setData(it) }

        btnFinish.setOnClickListener {
            strUserName = edtUser.text.toString().trim()
            strAddress = edtAddress.text.toString().trim()
            strPhone = edtPhoneUser.text.toString().trim()

            if (strUserName.isEmpty() || strUserName == ""){
                Toast.makeText(context, "Vui lòng nhập họ và tên", Toast.LENGTH_SHORT).show()
            }else if (strAddress.isEmpty() || strAddress == ""){
                Toast.makeText(context, "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show()
            }else if (strPhone.isEmpty() || strPhone == ""){
                Toast.makeText(context, "Vui lòng nhập sđt", Toast.LENGTH_SHORT).show()
            }else{
                buyCake()
            }
        }
    }

    private fun init(){
        rcvPayment.apply {
            adapter = paymentAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun buyCake(){

        val dataBaseReference: DatabaseReference = FirebaseDatabase
            .getInstance()
            .getReferenceFromUrl(Constant.BASE_FIREBASE_URL)
            .child("HistoryBuy")
            .push()
        var arr = paymentModel.listCart!!
        val hashMap : HashMap<String, HashMap<String, String>> = HashMap()
        for ((i, item) in arr.withIndex()){
            val hash : HashMap<String, String> = HashMap()
            hash["nameCake"] = item.nameCake.toString()
            hash["price"] = item.priceCake.toString()
            hashMap["cake_$i"] = hash
            Log.d("SIZE", item.nameCake.toString())
        }

        val hasMap: HashMap<String, Object> = HashMap()
        hasMap["nameUser"] = strUserName as Object
        hasMap["address"] = strAddress  as Object
        hasMap["phone"] = strPhone as Object
        hasMap["cake"] = hashMap as Object
        hasMap["totalPrice"] = paymentModel.totalPrice as Object
        hasMap["time"] = paymentModel.date.toString()  as Object

        val dialogBuilder = AlertDialog.Builder(context)

        dialogBuilder.setMessage("Bạn có chắc mua sản phẩm này không?")
            .setCancelable(false)
            .setPositiveButton("Có", DialogInterface.OnClickListener { dialog, _ ->
                dataBaseReference.setValue(hasMap)
                paymentViewModel.deleteAllCart()
                Toast.makeText(context, "Mua thành công", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_payment_to_home)
            })
            .setNegativeButton("Không", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
                Toast.makeText(context, "Mua thất bại", Toast.LENGTH_SHORT).show()
            })

        val alert = dialogBuilder.create()
        alert.setTitle("Giỏ hàng")
        alert.show()
    }
}