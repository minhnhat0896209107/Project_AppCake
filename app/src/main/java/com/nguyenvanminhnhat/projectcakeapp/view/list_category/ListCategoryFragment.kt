package com.nguyenvanminhnhat.projectcakeapp.view.list_category


import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.adapter.ListCategoryAdapter
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CategoryModel

import kotlinx.android.synthetic.main.fragment_list_category.*

class ListCategoryFragment : Fragment() {

    private val viewModel: ListCategoryViewModel by viewModels()
    lateinit var storage: StorageReference
    private val listCateAdapter: ListCategoryAdapter by lazy {
        ListCategoryAdapter(onClickFavourite = onClickFav())
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
                listCateAdapter.filter.filter(p0)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                listCateAdapter.filter.filter(p0)
            }

            override fun afterTextChanged(p0: Editable?) {
                listCateAdapter.filter.filter(p0)
            }

        })
    }

    private fun onClickFav(): (CategoryModel) -> Unit = {
        var imageData = it.imageCategory
        var nameData = it.nameCategory
        val dataBaseReference: DatabaseReference = FirebaseDatabase
            .getInstance()
            .getReferenceFromUrl("https://projectcake-b8a05-default-rtdb.firebaseio.com/")
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