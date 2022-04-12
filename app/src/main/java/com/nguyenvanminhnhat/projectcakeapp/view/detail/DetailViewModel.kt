package com.nguyenvanminhnhat.projectcakeapp.view.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CartModel
import com.nguyenvanminhnhat.projectcakeapp.usecase.CartUseCase

class DetailViewModel @ViewModelInject constructor(
    private val cartUseCase: CartUseCase
) : ViewModel(){
    fun insertCart(cartModel: CartModel){
        cartUseCase.excute(cartModel)
    }
}