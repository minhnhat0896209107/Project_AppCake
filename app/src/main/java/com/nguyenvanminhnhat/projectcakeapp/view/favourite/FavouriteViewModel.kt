package com.nguyenvanminhnhat.projectcakeapp.view.favourite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.FirebaseFavourite
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.FirebaseHome
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.FavouriteModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.ImageModel


class FavouriteViewModel : ViewModel(), FirebaseFavourite.IResultFav {
    private var repo : FirebaseFavourite = FirebaseFavourite()
    private var _favLiveData = MutableLiveData<List<FavouriteModel>>()
    var listFav = _favLiveData
    init {
        repo.firebaseFavourite(this)
    }

    fun removeFav(nameCake: String){
        repo.removeFav(nameCake)
    }

    fun getListFav(){
        repo.getListFav()
    }

    override fun onSuccess(success: List<FavouriteModel>) {
        _favLiveData.postValue(success)
    }

    override fun onError(error: String) {
        TODO("Not yet implemented")
    }

}