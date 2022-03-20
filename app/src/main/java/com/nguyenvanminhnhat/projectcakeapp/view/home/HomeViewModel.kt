package com.nguyenvanminhnhat.projectcakeapp.view.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.FirebaseHome
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.FirebaseHome.IResultImage
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CategoryModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.ImageModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.PopularCakeModel

class HomeViewModel: ViewModel(), IResultImage, FirebaseHome.IResultPopularImage, FirebaseHome.IResultCategory {
    private var repo : FirebaseHome = FirebaseHome()
    private val imageLiveData = MutableLiveData<List<ImageModel>>()
    var imageLive = imageLiveData

    private val popularCakeLiveData = MutableLiveData<List<PopularCakeModel>>()
    var popularCake = popularCakeLiveData

    private val categoryLiveData = MutableLiveData<List<CategoryModel>>()
    var category = categoryLiveData

    init{
        repo.firebaseHome(this, this, this)
    }
    fun getImage(){
        repo.getImage()
    }

    fun getPopularCake(){
        repo.getPopularCake()
    }

    fun getCategory(){
        repo.getCategory()
    }

    override fun onSuccess(success: List<ImageModel>) {
        imageLiveData.postValue(success)
    }

    override fun onError(error: String) {
        TODO("Not yet implemented")
    }

    override fun onSuccessPopular(success: List<PopularCakeModel>) {
        popularCakeLiveData.postValue(success)
    }

    override fun onErrorPopular(error: String) {
        TODO("Not yet implemented")
    }

    override fun onSuccessCategory(success: List<CategoryModel>) {
        categoryLiveData.postValue(success)
    }

    override fun onErrorCategory(error: String) {
        TODO("Not yet implemented")
    }


}