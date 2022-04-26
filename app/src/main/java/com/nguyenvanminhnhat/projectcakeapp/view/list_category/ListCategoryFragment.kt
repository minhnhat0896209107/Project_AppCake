package com.nguyenvanminhnhat.projectcakeapp.view.list_category


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.adapter.ListCategoryAdapter
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CartModel
import com.nguyenvanminhnhat.projectcakeapp.utils.Constant.Companion.BASE_FIREBASE_URL
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CategoryModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*

import kotlinx.android.synthetic.main.fragment_list_category.*
import kotlinx.android.synthetic.main.fragment_list_category.ivBackHome
import kotlinx.android.synthetic.main.item_list_category.*
import kotlinx.android.synthetic.main.item_list_category.tvAddCart

@AndroidEntryPoint
class ListCategoryFragment : Fragment() {
    private var cartModel : CartModel = CartModel()
    private val viewModel: ListCategoryViewModel by viewModels()
    lateinit var storage: StorageReference
    private val listCateAdapter: ListCategoryAdapter by lazy {
        ListCategoryAdapter(onClickFavourite = onClickFav(), onClickAddCart = onClickAddCart())
    }

    private fun onClickAddCart(): (CategoryModel) -> Unit = {
        cartModel.apply {
            idCart = "id${it.nameCategory}"
            imageCake = it.imageCategory.toString()
            nameCake = it.nameCategory.toString()
            priceCake = it.priceCategory!!
        }
        viewModel.insertCart(cartModel)
        Toast.makeText(context, "Thêm vào giỏ hàng thành công ${cartModel.idCart}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivBackHome.setOnClickListener {
            findNavController().navigate(R.id.action_listCategory_to_home)
        }

        init()
        viewModel.getListCategory()
        obsListen()
        searchCategory()

        storage = FirebaseStorage.getInstance().reference.child("ImageFavourite")


    }

    private fun init() {
        rcvListCategory.apply {
            adapter = listCateAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun obsListen() {
        viewModel.listCategory.observe(viewLifecycleOwner) { t ->
            listCateAdapter.setData(t)

        }
    }

    private fun searchCategory() {
        edtFind.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                listCateAdapter.filter.filter(p0)
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    private fun onClickFav(): (CategoryModel) -> Unit = {
        var imageData = it.imageCategory
        var nameData = it.nameCategory
        val dataBaseReference: DatabaseReference = FirebaseDatabase
            .getInstance()
            .getReferenceFromUrl(BASE_FIREBASE_URL)
            .child("ImageFavourite")
            .child("id$nameData")


        val hasMap: HashMap<String, String> = HashMap()
        hasMap["imageCake"] = imageData!!
        hasMap["nameCake"] = nameData!!
        dataBaseReference.setValue(hasMap)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onStart() {
        super.onStart()
        listCateAdapter.notifyDataSetChanged()
    }
}