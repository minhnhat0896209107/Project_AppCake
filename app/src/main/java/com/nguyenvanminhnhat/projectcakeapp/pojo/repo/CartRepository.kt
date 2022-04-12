package com.nguyenvanminhnhat.projectcakeapp.pojo.repo

import com.nguyenvanminhnhat.projectcakeapp.pojo.db.CartDao
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CartModel
import javax.inject.Inject

class CartRepository @Inject constructor(
    private var cartDao: CartDao
) {
    fun insertCart(cartModel: CartModel) = cartDao.insertCart(cartModel)
}