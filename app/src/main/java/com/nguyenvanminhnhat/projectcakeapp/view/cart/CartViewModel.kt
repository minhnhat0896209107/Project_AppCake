package com.nguyenvanminhnhat.projectcakeapp.view.cart

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.nguyenvanminhnhat.projectcakeapp.usecase.CartUseCase

class CartViewModel @ViewModelInject constructor(
    private val cartUseCase: CartUseCase
) : ViewModel(){
    fun getAllCart() = cartUseCase.getAllCart()

    fun deleteAllCart() =cartUseCase.deleteCart()
}