package com.nguyenvanminhnhat.projectcakeapp.view.payment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.nguyenvanminhnhat.projectcakeapp.usecase.CartUseCase

class PaymentViewModel @ViewModelInject constructor(
    private val cartUseCase: CartUseCase
): ViewModel(){

    fun deleteAllCart() =cartUseCase.deleteCart()
}