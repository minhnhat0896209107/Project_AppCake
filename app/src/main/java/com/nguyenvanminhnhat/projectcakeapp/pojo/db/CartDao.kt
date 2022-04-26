package com.nguyenvanminhnhat.projectcakeapp.pojo.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CartModel

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCart(users: CartModel) : Long

    @Query("SELECT * FROM cartModel")
    fun getAll(): List<CartModel>

    @Query("DELETE FROM cartModel")
    fun deleteAll()
}