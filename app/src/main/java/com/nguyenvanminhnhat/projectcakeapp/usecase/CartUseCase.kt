package com.nguyenvanminhnhat.projectcakeapp.usecase

import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CartModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.repo.CartRepository
import javax.inject.Inject

class CartUseCase @Inject constructor(
    private var cartRepo : CartRepository
) {
    fun excute( cartModel: CartModel): Boolean {
        val cart = cartRepo.insertCart(cartModel)
        return cart != 0L
    }
}