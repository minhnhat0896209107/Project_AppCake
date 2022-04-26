package com.nguyenvanminhnhat.projectcakeapp.view.list_category

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.FirebaseFavourite
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.FirebaseHome
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CartModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CategoryModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.ImageModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.PopularCakeModel
import com.nguyenvanminhnhat.projectcakeapp.usecase.CartUseCase

class ListCategoryViewModel @ViewModelInject constructor(
    private val cartUseCase: CartUseCase
): ViewModel(), FirebaseHome.IResultCategory,
    FirebaseHome.IResultPopularImage, FirebaseHome.IResultImage {
    private var repo : FirebaseHome = FirebaseHome()

    private val listCategoryLiveData = MutableLiveData<List<CategoryModel>>()
    var listCategory = listCategoryLiveData
    init {
        repo.firebaseHome(this, this, this)
    }

    fun getListCategory(){
        repo.getCategory()
    }

    fun insertCart(cartModel: CartModel){
        cartUseCase.excute(cartModel)
    }

    override fun onSuccessCategory(success: List<CategoryModel>) {
        listCategoryLiveData.postValue(success)
    }

    override fun onErrorCategory(error: String) {
        TODO("Not yet implemented")
    }

    override fun onSuccess(success: List<ImageModel>) {
        TODO("Not yet implemented")
    }

    override fun onError(error: String) {
        TODO("Not yet implemented")
    }

    override fun onSuccessPopular(success: List<PopularCakeModel>) {
        TODO("Not yet implemented")
    }

    override fun onErrorPopular(error: String) {
        TODO("Not yet implemented")
    }
}